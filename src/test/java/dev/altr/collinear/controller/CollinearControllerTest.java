package dev.altr.collinear.controller;

import dev.altr.collinear.model.Point;
import dev.altr.collinear.repository.PointRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by alessandrotravi on 25/03/2019.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CollinearControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PointRepository pointRepository;

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
        HttpHeaders headers = new HttpHeaders();
        Point point = new Point(100L, 10d, 0.0d);
        HttpEntity<Point> entity = new HttpEntity<>(point, headers);
        ResponseEntity<Point> response = this.restTemplate.exchange("/point", HttpMethod.POST, entity, new ParameterizedTypeReference<Point>() {
        });
        assertEquals("HttpStatus", HttpStatus.CONFLICT, response.getStatusCode());

        Point pointResult = response.getBody();
        assert pointResult != null;

        assertEquals("Response x", 10, pointResult.getX(), 0);
        assertEquals("Response y", 0.0, pointResult.getY(), 0);
    }

    @Test
    public void setPoint() {
        HttpHeaders headers = new HttpHeaders();
        Point point = new Point(1000L, 0.0d, 10d);
        HttpEntity<Point> entity = new HttpEntity<>(point, headers);
        ResponseEntity<Point> response = this.restTemplate.exchange("/point", HttpMethod.POST, entity, new ParameterizedTypeReference<Point>() {
        });
        assertEquals("HttpStatus", HttpStatus.OK, response.getStatusCode());

        Point pointResult = response.getBody();
        assert pointResult != null;

        assertEquals("Response x", 0.0, pointResult.getX(), 0);
        assertEquals("Response y", 10, pointResult.getY(), 0);
    }

    @Test
    public void getLines() {
    }

    @Test
    public void getAllPoints() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<List<Point>> response = this.restTemplate.exchange("/space", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Point>>() {
        });
        assertEquals("HttpStatus", HttpStatus.OK, response.getStatusCode());

        List<Point> pointsResult = response.getBody();
        assert pointsResult != null;

        assertEquals("Response Total", 3, pointsResult.size());

        assertEquals("Response1 x", 10, pointsResult.get(0).getX(), 0);
        assertEquals("Response1 y", 0.0, pointsResult.get(0).getY(), 0);

        assertEquals("Response2 x", 20, pointsResult.get(1).getX(), 0);
        assertEquals("Response2 y", 2.0, pointsResult.get(1).getY(), 0);

        assertEquals("Response3 x", 30, pointsResult.get(2).getX(), 0);
        assertEquals("Response3 y", 4.0, pointsResult.get(2).getY(), 0);
    }

    @Test
    public void deleteAllPoints() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Void> response = this.restTemplate.exchange("/space", HttpMethod.DELETE, entity, new ParameterizedTypeReference<Void>() {
        });
        assertEquals("HttpStatus", HttpStatus.OK, response.getStatusCode());
    }
}