package lib.node;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NodeTest {
    @Test
    public void testEquals() {
        Assertions.assertEquals(new Point(1, 1), new Point(1, 1));
        Assertions.assertNotEquals(new Point(10, 1), new Point(2, 1));
    }
}