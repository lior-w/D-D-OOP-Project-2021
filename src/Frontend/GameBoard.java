package Frontend;

import Backend.*;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class GameBoard {

    private List<Tile> tiles;
    private List<Enemy> enemies;
    private TileFactory tileFactory;
    private int h;
    private int w;

    public GameBoard(Tile[][] board, List<Enemy> enemies, TileFactory tileFactory){
        h = board.length;
        w = board[0].length;
        tiles = Arrays.stream(board).flatMap(Arrays::stream).collect(Collectors.toList());
        this.enemies = enemies;
        this.tileFactory = tileFactory;
    }

    public void removeEnemy(Enemy e) {
        Empty empty = tileFactory.produceEmpty(e.getPosition());
        tiles.remove(e);
        enemies.remove(e);
        tiles.add(empty);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Tile> getTilesAroundMe(Position position) {
        return tiles.stream().filter(t -> t.getPosition().rangeFrom(position) == 1).collect(Collectors.toList());
    }

    public String toString() {
        tiles.sort(Tile::compareTo);
        //String s =tiles.stream().sorted().map(t-> t.getPosition().getX() == w-1 ? t.getTile() + "\n" : t.getTile() + "")
                //.collect(Collectors.joining(""));
        String s = "";
        for (Tile t: tiles) {
            s += t.getTile();
            if (t.getPosition().getX() == w - 1) s += "\n";
        }
        return s;
    }




}
