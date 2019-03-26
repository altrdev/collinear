package dev.altr.collinear.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by alessandrotravi on 25/03/2019.
 */
public class Line {

    private List<Point> points;

    private Line(List<Point> points) {
        this.points = points;
    }

    public static Line of(Point... points) throws RuntimeException {

        final List<Point> pointSet = Stream.of(points)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (pointSet.size() < 2) {
            throw new RuntimeException("At least two points are mandatory to evaluate a line");
        }

        return new Line(pointSet);
    }

    public List<Point> getAllPoints() {
        return new ArrayList<>(points);
    }

    public void addPoint(Point p) {
        points.add(p);
    }

    @Override
    public String toString() {
        return "Line{" +
                "points=" + points +
                '}';
    }
}

