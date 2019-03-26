package dev.altr.collinear.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by alessandrotravi on 25/03/2019.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Point implements Serializable {
    private static final long serialVersionUID = -2857947990787058926L;

    @NotNull
    private double x;
    @NotNull
    private double y;

}
