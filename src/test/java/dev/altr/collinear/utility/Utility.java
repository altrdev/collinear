package dev.altr.collinear.utility;

import dev.altr.collinear.model.Point;
import dev.altr.collinear.service.CollinearService;

/**
 * Created by alessandrotravi on 25/03/2019.
 */
public class Utility {
    public static void setPoints(CollinearService collinearService) {
        Point point1 = new Point(10d, 0.0d);
        Point point2 = new Point(20d, 2.0d);
        Point point3 = new Point(30d, 4.0d);
        collinearService.setPoint(point1);
        collinearService.setPoint(point2);
        collinearService.setPoint(point3);
    }
}
