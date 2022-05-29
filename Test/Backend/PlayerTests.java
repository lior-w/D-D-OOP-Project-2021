package Backend;

import Frontend.TileFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTests {

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
    public void setUp() {
        tileFactory = new TileFactory();
        // Every player start with level: 1, experience: 0
        w = (Warrior) tileFactory.producePlayer(0); // name: Jon Snow, health: 300, attack: 30, defense: 4, abilityCooldown: 3
        w.setDeathCallback(this::killPlayer);
        w.setMessageCallback(this::sendMessage);

        m = (Mage) tileFactory.producePlayer(2); // name: Melisandre, health: 100, attack: 5, defense: 1, manaPool: 300, manaCost: 30, spellPower: 15, hitsCount: 5, abilityRange: 6
        m.setDeathCallback(this::killPlayer);
        m.setMessageCallback(this::sendMessage);

        r = (Rogue) tileFactory.producePlayer(4); // name: Arya Stark, health: 150, attack: 40, defense: 2, cost: 20
        r.setDeathCallback(this::killPlayer);
        r.setMessageCallback(this::sendMessage);

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
        w.onDeath();

        assertFalse(playerAlive);
        assertEquals("You lost.", message);
    }

    @Test
    public void onKill() {
        w.initialize(new Position(0,1));

        w.onKill(e);

        assertFalse(enemyAlive);
        assertEquals(0, w.getPosition().getX());
        assertEquals(0, w.getPosition().getY());
        assertEquals(String.format("%s died, %s gained %d experience", e.getName(), w.getName(), e.getExperience()), message);
    }

    // Player.levelUp() ->
    // experience += 50 * level
    // level += 1
    // healthPool += 10 * level
    // currentHealth = healthPool
    // attack += 4 * level
    // defense += level

    // Warrior.levelUp() ->
    // remainingCooldown = 0
    // healthPool += 5 * level
    // attack += 2 * level
    // defense += level
    @Test
    public void warriorLevelUp() {
        w.remainingCooldown++;
        w.experience = 100;
        w.decreaseHealth(10);
        w.levelUp();

        assertEquals(String.format("%s got next level", w.getName()), message);

        assertEquals(50, w.getExperience());
        assertEquals(2, w.getLevel());
        assertEquals(300 + (10 * 2) + (5 * 2), w.getHealthPool());
        assertEquals(w.getHealthPool() - (5 * 2), w.getHealthAmount());
        assertEquals(30 + 4 * 2 + 2 * 2, w.getAttack());
        assertEquals(4 + 2 + 2, w.getDefense());

        assertEquals(0, w.remainingCooldown);
    }

    // Player.levelUp() ->
    // experience += 50 * level
    // level += 1
    // healthPool += 10 * level
    // currentHealth = healthPool
    // attack += 4 * level
    // defense += level

    // Mage.levelUp() ->
    // manaPool += 25 * level
    // currentMana = min(currentMana + manaPool/4, manaPool)
    // spellPower += 10 * level
    @Test
    public void MageLevelUp() {
        m.decreaseHealth(10);
        m.experience = 100;
        m.levelUp();

        assertEquals(String.format("%s got next level", m.getName()), message);

        assertEquals(50, m.getExperience());
        assertEquals(2, m.getLevel());
        assertEquals(100 + 10 * 2, m.getHealthPool());
        assertEquals(m.getHealthPool(), m.getHealthAmount());
        assertEquals(5 + 4 * 2, m.getAttack());
        assertEquals(1 + 2, m.getDefense());

        assertEquals(300 + 25 * 2, m.manaPool);
        assertEquals(162, m.currentMana);
        assertEquals(15 + 10 * 2, m.spellPower);
    }

    // Player.levelUp() ->
    // experience += 50 * level
    // level += 1
    // healthPool += 10 * level
    // currentHealth = healthPool
    // attack += 4 * level
    // defense += level

    // Rogue.levelUp() ->
    // currentEnergy = 100
    // attack += 3 * level
    @Test
    public void RogueLevelUp() {
        r.currentEnergy = 50;
        r.experience = 100;
        r.decreaseHealth(10);
        r.levelUp();

        assertEquals(String.format("%s got next level", r.getName()), message);

        assertEquals(50, r.getExperience());
        assertEquals(2, r.getLevel());
        assertEquals(150 + 10 * 2, r.getHealthPool());
        assertEquals(r.getHealthPool(), r.getHealthAmount());
        assertEquals(40 + 4 * 2 + 3 * 2, r.getAttack());
        assertEquals(2 + 2, r.getDefense());

        assertEquals(100, r.currentEnergy);
    }


}
