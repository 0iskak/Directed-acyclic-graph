package lib.node;

import lombok.*;

@Data
@AllArgsConstructor
public class Coord2D {
    private double x;
    private double y;

    public Coord2D(Coord2D position) {
        x = position.getX();
        y = position.getY();
    }

}
