package lib;

import com.google.gson.*;
import lib.node.*;

import java.util.ArrayList;

public class DAGUtils {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static String exportAsString (Space o) {
        return gson.toJson(o);
    }

    public static void importFromString (String s) throws DAGConstraintException {
        var json = gson.fromJson(s, JsonObject.class);

        var jsonRoot = json.get("root").getAsJsonObject();
        var jsonPosition = jsonRoot.get("position").getAsJsonObject();

        var root = new Origin(jsonPosition.get("x").getAsDouble(), jsonPosition.get("y").getAsDouble());
        Space.get().setRoot(root);

        var jsonChildren = jsonRoot.get("children").getAsJsonArray();
        var tree = getTree(jsonChildren);

        for (Node node : tree) {
            root.addChild(node);
        }
    }

    private static ArrayList<Node> getTree(JsonArray array) throws DAGConstraintException {
        var tree = new ArrayList<Node>();
        for (JsonElement child : array) {
            var jsonPosition = child.getAsJsonObject().get("position").getAsJsonObject();
            var position = new Coord2D(jsonPosition.get("x").getAsDouble(), jsonPosition.get("y").getAsDouble());
            var jsonChildren = child.getAsJsonObject().get("children");
            if (jsonChildren == null)
                tree.add(new Point(position));
            else {
                var origin = new Origin(position);
                for (Node node : getTree(jsonChildren.getAsJsonArray())) {
                    origin.addChild(node);
                }
                tree.add(origin);
            }
        }

        return tree;
    }
}
