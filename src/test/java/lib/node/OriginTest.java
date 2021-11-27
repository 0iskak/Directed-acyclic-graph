package lib.node;

import lib.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OriginTest {
    Origin origin;
    Origin origin1;
    Origin origin2;

    @BeforeEach()
    public void init() {
        origin = new Origin(0, 0);
        Space.get().setRoot(origin);

        origin1 = new Origin(1, 0);
        origin2 = new Origin(1, 1);
    }

    @Test
    public void testAdd() {
        Assertions.assertDoesNotThrow(() -> origin.addChild(origin1));
        Assertions.assertThrows(DAGConstraintException.class,
                () -> origin1.addChild(new Point(0, 0)));
    }

    @Test
    public void testBounds() {
        Assertions.assertDoesNotThrow(() -> origin.addChild(origin1));
        Assertions.assertDoesNotThrow(() -> origin.addChild(origin2));
        var bound = new BoundBox(new Coord2D(0, 0), new Coord2D(1, 1));
        Assertions.assertEquals(origin.getBounds(), bound);
    }
}