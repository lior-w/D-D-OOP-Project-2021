package Backend;

import Frontend.TileFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class EnemyTest {
    TileFactory tileFactory;
    Warrior w;
    Mage m;
    Rogue r;
    Enemy e;
    Position position;
    boolean playerAlive;
    boolean enemyAlive;
    String message;


    @BeforeEach
    void setUp() {
        tileFactory = new TileFactory();
        // Every player start with level: 1, experience: 0
        w = (Warrior) tileFactory.producePlayer(0); // name: Jon Snow, health: 300, attack: 30, defense: 4, abilityCooldown: 3
        w.setDeathCallback(this::killPlayer);
        w.setMessageCallback(this::sendMessage);
        e = tileFactory.produceEnemy('s', new Position(0,0));
        e.setDeathCallback(this::killEnemy);
        e.setMessageCallback(this::sendMessage);

        playerAlive = true;
        enemyAlive = true;
    }
    private void killPlayer() {
        playerAlive = false;
    }

    private void killEnemy() {
        enemyAlive = false;
    }

    private void sendMessage(String msg) {
        message = msg;
    }

    @Test
    public void onDeath() {
        e.onDeath();

        assertFalse(enemyAlive);
        //assertEquals(String.format("%s died, %s gained %d experience", e.getName(), w.getName(), e.getExperience()), message);
    }

    @Test
    public void onKill() {
        e.initialize(new Position(0,1));
        e.onKill(w);

        assertFalse(playerAlive);
        assertEquals("You lost.", message);
    }

    //@Test
    //void getExperience() {
    //    assertEquals(4, e.getExperience());
    //}

    //@Test
    //void setPlayer() {
    //    e.setPlayer(new Warrior("Jon Snow", 300, 30, 4, 3));
    //    assertEquals(e.player, new Warrior("Jon Snow", 300, 30, 4, 3));
    //}

}