package dev.altr.collinear.service;

import dev.altr.collinear.model.Line;
import dev.altr.collinear.model.Point;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by alessandrotravi on 25/03/2019.
 */
@Service
@Slf4j
public class CollinearService {

    private List<Point> points;
    private Map<Integer, List<Line>> lines;

    @Autowired
    public CollinearService() {
        flush();
    }

    public List<Point> getAllPoints() {
        log.debug("returning all points saved");
        return points;
    }

    public void deleteAllPoints() {
        log.debug("deleting all points saved");
        flush();
    }

    public Point setPoint(Point point) throws ValidationException {
        if (points.contains(point)) {
            throw new ValidationException(String.format("Point x:%s y:%s already exists", point.getX(), point.getY()));
        }

        composeLines(point);
        this.points.add(point);
        log.debug("new point saved");

        return point;
    }

    public List<List<Point>> getLinesWithCollinearPoints(int number) {

        log.debug("Incoming getLinesWithCollinearPoints {}", number);

        return getLinesList(number)
                .stream()
                .map(l -> new ArrayList<>(l.getAllPoints()))
                .collect(Collectors.toList());
    }

    private List<Line> getLinesList(int n) {
        return this.lines.entrySet()
                .stream()
                .filter(v -> v.getKey() >= n)
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     * Logic for calculate collinear
     */
    private boolean isPointCollinearToLine(final Point p, final Line line) {
        final List<Point> linePoints = line.getAllPoints();
        final Point p1 = linePoints.get(0);
        final Point p2 = linePoints.get(1);

        double result = (p2.getY() - p1.getY()) * p.getX();
        result += (p1.getX() - p2.getX()) * p.getY();
        result += (p2.getX() * p1.getY() - p1.getX() * p2.getY());

        return result == 0d;
    }


    /**
     * When called, it checks if any existing Line can be extended with point,
     * after that it compose any new line obtained with the new point.
     */
    private void composeLines(final Point point) {
        Map<Integer, List<Line>> lines = new HashMap<>();

        this.lines.values()
                .stream()
                .flatMap(Collection::stream)
                .forEach(line -> {
                    if (isPointCollinearToLine(point, line)) {
                        line.addPoint(point);
                    }
                    List<Line> lineList = lines.get(line.getAllPoints().size());
                    if (lineList == null) {
                        lineList = new ArrayList<>();
                    }
                    lineList.add(line);

                    log.info("Recalc line {}", line);
                    lines.put(line.getAllPoints().size(), lineList);
                });

        this.lines = lines;
        composeNewLines(point);
    }

    /**
     * Composing new lines, always with two points
     */
    private void composeNewLines(final Point point) {
        final List<Line> twoPointsLine;
        if (this.lines.get(2) != null) {
            twoPointsLine = this.lines.get(2);
        } else {
            twoPointsLine = new ArrayList<>();
        }

        this.points
                .forEach(p -> {
                    if (!alreadyInLine(point, p)) {
                        log.debug("Compose new line between point {} and {}", point, p);
                        twoPointsLine.add(Line.of(point, p));
                    }
                });

        if (!twoPointsLine.isEmpty()) {
            this.lines.put(2, twoPointsLine);
        }
    }

    private boolean alreadyInLine(Point p1, Point p2) {
        List<Point> points = new ArrayList<>();
        points.add(p1);
        points.add(p2);

        return this.lines.values()
                .stream()
                .flatMap(Collection::stream)
                .anyMatch(line -> line.getAllPoints().containsAll(points));
    }

    public void flush() {
        this.points = new LinkedList<>();
        this.lines = new HashMap<>();
    }
}
