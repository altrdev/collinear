package dev.altr.collinear.controller;

import dev.altr.collinear.model.Point;
import dev.altr.collinear.service.PointService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by alessandrotravi on 25/03/2019.
 */
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class CollinearController {

    private final PointService pointService;

    /**
     * Api for set a point
     *
     * @return response json
     */
    @RequestMapping(value = "/point", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Point> setPoint(@RequestBody @Valid Point point) {
        log.info("Incoming setPoint request");
        try {
            Optional<Point> resultPoint = pointService.setPoint(point);
            if (!resultPoint.isPresent())
                return ResponseEntity.status(HttpStatus.CONFLICT).body(point);

            return ResponseEntity.ok(resultPoint.get());
        } catch (Exception e) {
            log.error("Error setPoint: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Api that returns collinear segments
     *
     * @return response json
     */
    @RequestMapping(value = "/lines/{number}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Point>> getLines(@PathVariable @Valid Integer number) {
        log.info("Incoming getLines request");
        try {
            return ResponseEntity.ok(pointService.getCollinearLines(number));
        } catch (Exception e) {
            log.error("Error getLines: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Api that returns all points saved
     *
     * @return response json
     */
    @RequestMapping(value = "/space", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Point>> getAllPoints() {
        log.info("Incoming getAllPoints request");
        try {
            return ResponseEntity.ok(pointService.getAllPoints());
        } catch (Exception e) {
            log.error("Error getAllPoints: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Api for delete all points
     */
    @RequestMapping(value = "/space", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteAllPoints() {
        log.info("Incoming deleteAllPoints request");
        try {
            pointService.deleteAllPoints();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleteAllPoints: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
