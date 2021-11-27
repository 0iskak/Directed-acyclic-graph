package lib;

import lib.node.Origin;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Space {
    private static Space space = new Space();
    private Origin root;

    public static Space get() {
        return space;
    }
}
