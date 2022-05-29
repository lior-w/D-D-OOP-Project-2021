package Backend;

public abstract class Tile implements Comparable{
    protected char c;
    protected Position position;

    public Tile(char c) {
        this.c = c;
        // position= new Backend.Position(0,0);
    }

    public void initialize(Position position) {
        this.position = position;
    }

    public char getTile() {
        return this.c;
    }

    public Position getPosition() {
        return this.position;
    }

    public abstract void accept(Unit unit) ;


    public int compareTo(Object tile) {
        return this.compareTo((Tile)tile);
    }

    public int compareTo(Tile tile) {
        return getPosition().compareTo(tile.getPosition());
    }

    public String toString() {
        return String.valueOf(getTile());
    }
}
