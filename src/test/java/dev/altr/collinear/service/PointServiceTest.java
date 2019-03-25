package dev.altr.collinear.service;

import dev.altr.collinear.model.Point;
import dev.altr.collinear.repository.PointRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by alessandrotravi on 25/03/2019.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PointServiceTest {

    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private PointService pointService;

    @Before
    public void setUp() {
        Point point1 = new Point(1L, 10d, 0.0d);
        Point point2 = new Point(2L, 20d, 2.0d);
        Point point3 = new Point(3L, 30d, 4.0d);
        List<Point> points = new ArrayList<>(Arrays.asList(point1, point2, point3));
        pointRepository.saveAll(points);
    }

    @After
    public void removeFromRepo() {
        pointRepository.deleteAll();
    }

    @Test
    public void setPointAlreadyExists() {
        Point point = new Point(100L, 10d, 0.0d);
        Optional<Point> pointResult = pointService.setPoint(point);

        assertFalse(pointResult.isPresent());
    }

    @Test
    public void setPoint() {
        Point point = new Point(100L, 11d, 0.5d);
        Optional<Point> pointResult = pointService.setPoint(point);

        assertTrue(pointResult.isPresent());
        assertEquals("X cord", 11d, pointResult.get().getX(), 0);
        assertEquals("Y cord", 0.5d, pointResult.get().getY(), 0);

        assertEquals("Total", 4, pointRepository.findAll().size());
    }

    @Test
    public void getAllPoints() {
        List<Point> points = pointService.getAllPoints();
        assertEquals("Total", 3, points.size());
    }

    @Test
    public void deleteAllPoints() {
        pointService.deleteAllPoints();
        assertEquals("Total", 0, pointRepository.findAll().size());
    }

    @Test
    public void getCollinearLines() {
    }
}