package Backend;

import Frontend.TileFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HealthTest {
    private Health health;

    @BeforeEach
    void setUp() {
        health=new Health(200,100);
    }

    @Test
    void getHealthPool() {
        assertEquals(200,health.getHealthPool());
    }

    @Test
    void getHealthAmount() {
        assertEquals(100,health.getHealthAmount());
    }

    @Test
    void setHealthAmount() {
        health.setHealthAmount(300);
        assertEquals(200,health.getHealthAmount());
    }

    @Test
    void setHealthPool() {
        health.setHealthPool(100);
        assertEquals(100,health.getHealthPool());
    }
}