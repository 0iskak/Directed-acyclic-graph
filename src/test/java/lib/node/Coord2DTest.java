package lib.node;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Coord2DTest {

    @Test
    public void testEquals() {
        Assertions.assertEquals(new Coord2D(0, 0), new Coord2D(0, 0));
        Assertions.assertNotEquals(new Coord2D(0, 0), new Coord2D(0, 1));
    }
}