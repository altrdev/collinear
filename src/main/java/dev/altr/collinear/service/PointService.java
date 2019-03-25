package dev.altr.collinear.service;

import dev.altr.collinear.model.Point;
import dev.altr.collinear.repository.PointRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by alessandrotravi on 25/03/2019.
 */
@Service
@AllArgsConstructor
@Slf4j
public class PointService {

    private final PointRepository pointRepository;

    public Optional<Point> setPoint(Point point) {
        Optional<Point> alreadyExist = pointRepository.findOneByXAndY(point.getX(), point.getY());
        if (alreadyExist.isPresent())
            return Optional.empty();

        point = pointRepository.save(point);
        log.debug("new point saved");
        return Optional.of(point);
    }

    public List<Point> getAllPoints() {
        log.debug("returning all points saved");
        return pointRepository.findAll();
    }

    public void deleteAllPoints() {
        log.debug("deleting all points saved");
        pointRepository.deleteAll();
    }

    public List<Point> getCollinearLines(Integer number) {
        return null;
    }

}
