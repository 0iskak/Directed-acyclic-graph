package lib;

import lib.node.Origin;
import lib.node.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

class DAGUtilsTest {
    Origin origin;
    Origin origin1;
    Origin origin2;

    @BeforeEach()
    public void init() {
        origin = new Origin(0, 0);
        Space.get().setRoot(origin);

        origin1 = new Origin(1, 0);
        origin2 = new Origin(1, 1);
        origin.getChildren().addAll(Set.of(
                origin1,
                origin2,
                new Point(0, 1)
        ));
    }

    @Test
    public void testSerialization() {
        var json = DAGUtils.exportAsString(Space.get());
        Space.get().setRoot(null);
        Assertions.assertDoesNotThrow(() -> DAGUtils.importFromString(json));
        Assertions.assertEquals(json, DAGUtils.exportAsString(Space.get()));
    }
}