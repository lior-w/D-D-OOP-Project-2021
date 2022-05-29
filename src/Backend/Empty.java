package Backend;

public class Empty extends Tile {

    public Empty() {
        super('.');
    }

    public void accept(Unit u) {
        u.visit(this);
    }
}
