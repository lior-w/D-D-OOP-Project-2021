package Frontend;

import Backend.*;
import Frontend.EnemyDeathCallback;
import Frontend.MessageCallback;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TileFactory {

    private final List<Supplier<Player>> playersList;
    private final Map<Object, Supplier<Enemy>> enemiesMap;

    public TileFactory() {
        playersList = initPlayers();
        enemiesMap = initEnemies();
    }

    private Map<Object, Supplier<Enemy>> initEnemies() {
        List<Supplier<Enemy>> enemies = Arrays.asList(
                () -> new Monster('s', "Lannister Solider", 80, 8, 3,3, 25),
                () -> new Monster('k', "Lannister Knight", 200, 14, 8, 4,   50),
                () -> new Monster('q', "Queen's Guard", 400, 20, 15, 5,  100),
                () -> new Monster('z', "Wright", 600, 30, 15,3, 100),
                () -> new Monster('b', "Bear-Wright", 1000, 75, 30, 4,  250),
                () -> new Monster('g', "Giant-Wright",1500, 100, 40,5,   500),
                () -> new Monster('w', "White Walker", 2000, 150, 50, 6, 1000),
                () -> new Monster('M', "The Mountain", 1000, 60, 25, 6, 500),
                () -> new Monster('C', "Queen Cersei", 100, 10, 10, 1, 1000),
                () -> new Monster('K', "Night's King", 5000, 300, 150, 8, 5000),
                () -> new Trap('B', "Bonus Trap", 1, 1, 1, 250,  1, 5),
                () -> new Trap('Q', "Queen's Trap", 250, 50, 10, 100, 3, 7),
                () -> new Trap('D', "Death Trap", 500, 100, 20, 250, 1, 10)
        );

        return enemies.stream().collect(Collectors.toMap(s -> s.get().getTile(), Function.identity()));
    }

    private List<Supplier<Player>> initPlayers() {
        return Arrays.asList(
                () -> new Warrior("Jon Snow", 300, 30, 4, 3),
                () -> new Warrior("The Hound", 400, 20, 6, 5),
                () -> new Mage("Melisandre", 100, 5, 1, 300, 30, 15, 5, 6),
                () -> new Mage("Thoros of Myr", 250, 25, 4, 150, 20, 20, 3, 4),
                () -> new Rogue("Arya Stark", 150, 40, 2, 20),
                () -> new Rogue("Bronn", 250, 35, 3, 50)
        );
    }

    public List<Player> listPlayers() {
        return playersList.stream().map(Supplier::get).collect(Collectors.toList());
    }

    public Enemy produceEnemy(char tile, Position position) {
        Enemy e = enemiesMap.get(tile).get();
        e.initialize(position);
        return e;
    }

    public Player producePlayer(int idx) {
        Player p = playersList.get(idx).get();
        return p;
    }

    public Empty produceEmpty(Position position) {
        Empty e = new Empty();
        e.initialize(position);
        return e;
    }

    public Wall produceWall(Position position) {
        Wall w = new Wall();
        w.initialize(position);
        return w;
    }
}
