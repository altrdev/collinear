package dev.altr.collinear.controller;

import dev.altr.collinear.model.Point;
import dev.altr.collinear.service.CollinearService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * Created by alessandrotravi on 25/03/2019.
 */
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*")
@Validated
@RestController
public class CollinearController {

    private final CollinearService collinearService;

    /**
     * Api for set a point
     *
     * @return response json
     */
    @RequestMapping(value = "/point", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Point> setPoint(@RequestBody @Valid Point point) {
        log.info("Incoming setPoint request");
        try {
            return ResponseEntity.ok(collinearService.setPoint(point));
        } catch (ValidationException e) {
            log.error("Error setPoint: {}", e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    /**
     * Api that returns collinear segments
     *
     * @return response json
     */
    @ResponseBody
    @RequestMapping(value = "/lines/{number}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<List<Point>>> getLines(@PathVariable @Valid @Min(2) Integer number) {
        log.info("Incoming getLines request");
        try {
            List<List<Point>> collinearLines = collinearService.getLinesWithCollinearPoints(number);
            if (collinearLines.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return ResponseEntity.ok(collinearLines);
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
            return ResponseEntity.ok(collinearService.getAllPoints());
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
            collinearService.deleteAllPoints();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleteAllPoints: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
