package dev.altr.collinear.repository;

import dev.altr.collinear.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by alessandrotravi on 25/03/2019.
 */
@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

    Optional<Point> findOneByXAndY(Double x, Double y);
}
