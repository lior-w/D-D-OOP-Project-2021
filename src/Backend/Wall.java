package Backend;

import Backend.Tile;
import Backend.Unit;

public class Wall extends Tile {

    public Wall() {
        super('#');
    }

    public void accept(Unit u) {
        u.visit(this);
    }
}
