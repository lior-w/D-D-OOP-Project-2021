package Backend;

import Frontend.TileFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitTest {
    TileFactory tileFactory;

    Unit unit1;
    Unit unit2;
    Position p1;
    Position p2;


    @BeforeEach
    void setUp() {
        tileFactory = new TileFactory();

        p1 = new Position(5,7);
        p2 = new Position(7,7);

        unit1 = (Warrior) tileFactory.producePlayer(0);
        unit1.initialize(p1);
        unit2 = (Monster) tileFactory.produceEnemy('s',p2);
    }


    @Test
    void swapPosition() {
        unit1.swapPosition(unit2);

        assertEquals(0,unit1.getPosition().compareTo(p2));
    }
}