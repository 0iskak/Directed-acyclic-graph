package lib.node;

import lombok.*;

@Data
@AllArgsConstructor
public abstract class Node {
    private Coord2D position;

    public Node(double x, double y) {
        position = new Coord2D(x, y);
    }
}
