package dev.altr.collinear.service;

import dev.altr.collinear.model.Point;
import dev.altr.collinear.utility.Utility;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ValidationException;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by alessandrotravi on 25/03/2019.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CollinearServiceTest {

    @Autowired
    private CollinearService collinearService;

    @Before
    public void setUp() {
        Utility.setPoints(collinearService);
    }

    @After
    public void removeFromRepo() {
        collinearService.flush();
    }

    @Test(expected = ValidationException.class)
    public void setPointAlreadyExists() {
        Point point = new Point(10d, 0.0d);
        Point pointResult = collinearService.setPoint(point);
    }

    @Test
    public void setPoint() {
        Point point = new Point(11d, 0.5d);
        Point pointResult = collinearService.setPoint(point);

        assertEquals("X cord", 11d, pointResult.getX(), 0);
        assertEquals("Y cord", 0.5d, pointResult.getY(), 0);

        assertEquals("Total", 4, collinearService.getAllPoints().size());
    }

    @Test
    public void getAllPoints() {
        List<Point> points = collinearService.getAllPoints();
        assertEquals("Total", 3, points.size());
    }

    @Test
    public void deleteAllPoints() {
        collinearService.deleteAllPoints();
        assertEquals("Total", 0, collinearService.getAllPoints().size());
    }

    @Test
    public void getCollinearLinesSucceeded() {
        List<List<Point>> collinearLines = collinearService.getLinesWithCollinearPoints(3);
        assertFalse(collinearLines.isEmpty());
        assertEquals(1, collinearLines.size());
        assertEquals("Item1 X", 20.0, collinearLines.get(0).get(0).getX(), 0);
        assertEquals("Item1 Y", 2, collinearLines.get(0).get(0).getY(), 0);
        assertEquals("Item2 X", 10.0, collinearLines.get(0).get(1).getX(), 0);
        assertEquals("Item2 Y", 0, collinearLines.get(0).get(1).getY(), 0);
        assertEquals("Item3 X", 30.0, collinearLines.get(0).get(2).getX(), 0);
        assertEquals("Item3 Y", 4, collinearLines.get(0).get(2).getY(), 0);
    }

    @Test
    public void getCollinearLinesFailed() {
        collinearService.flush();

        Point point1 = new Point(10d, 0.0d);
        Point point2 = new Point(20d, 2.0d);
        Point point3 = new Point(30d, 5.0d);
        collinearService.setPoint(point1);
        collinearService.setPoint(point2);
        collinearService.setPoint(point3);

        List<List<Point>> collinearLines = collinearService.getLinesWithCollinearPoints(3);
        assertTrue(collinearLines.isEmpty());
    }
}