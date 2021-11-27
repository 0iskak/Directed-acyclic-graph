package lib.node;

import lib.DAGConstraintException;
import lib.Space;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class Origin extends Node {
    private final Set<Node> children = new HashSet<>();

    public Origin(Coord2D position) {
        super(position);
    }

    public Origin(double x, double y) {
        super(x, y);
    }

    public void addChild(Node child) throws DAGConstraintException {
        children.add(child);

        var whiteSet = new HashSet<>(Space.get().getRoot().getTree());
        var graySet = new HashSet<Node>();
        var blackSet = new HashSet<Node>();

        while (whiteSet.size() > 0) {
            var node = whiteSet.iterator().next();
            if (checkDFS(node, whiteSet, graySet, blackSet)) {
                children.remove(child);
                throw new DAGConstraintException();
            }
        }
    }

    public BoundBox getBounds() {
        var coords = getTree().stream().map(Node::getPosition).collect(Collectors.toList());
        var min = new Coord2D(coords.get(0));
        var max = new Coord2D(coords.get(0));

        coords.forEach(coord -> {
            var x = coord.getX();
            var y = coord.getY();

            if (x < min.getX())
                min.setX(x);
            if (x > max.getX())
                max.setX(x);

            if (y < min.getY())
                min.setY(y);
            if (y > max.getY())
                max.setY(y);
        });

        return new BoundBox(min, max);
    }

    private boolean checkDFS(Node node, HashSet<Node> whiteSet, HashSet<Node> graySet, HashSet<Node> blackSet) {
        changeSet(node, whiteSet, graySet);

        if (node instanceof Origin origin) {
            for (Node child : origin.getChildren()) {
                if (blackSet.contains(child))
                    continue;

                if (graySet.contains(child))
                    return true;

                if (checkDFS(child, whiteSet, graySet, blackSet))
                    return true;
            }
        }

        changeSet(node, graySet, blackSet);
        return false;
    }

    private void changeSet(Node node, HashSet<Node> sourceSet, HashSet<Node> destSet) {
        sourceSet.remove(node);
        destSet.add(node);
    }

    private Set<Node> getTree() {
        var tree = new HashSet<Node>();

        tree.add(this);
        getChildren().forEach(child -> {
            tree.add(child);
            if (child instanceof Origin origin)
                tree.addAll(origin.getTree());
        });

        return tree;
    }
}
