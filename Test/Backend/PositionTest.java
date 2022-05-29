package Backend;

import Frontend.TileFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    private  Position p;
    private  Position p1;
    private  Position p2;
    private  Position p3;
    private  Position p4;
    private  Position p5;

    @BeforeEach
    void setUp()
    {
        p=new Position(0,0);
        p1=new Position(-1,-2);
        p2=new Position(20,40);
        p3=new Position(5,7);
        p4=new Position(7,7);
        p5=new Position(7,7);
    }
    @Test
    void getX() {
        assertEquals(0,p.getX());
    }

    @Test
    void getY() {
        assertEquals(0,p.getY());
    }

    @Test
    void rangeFrom() {
    }

    @Test
    void compareTo() {
        assertEquals(-1,p.compareTo(p2));
        assertEquals(1,p2.compareTo(p3));
        assertEquals(-1,p3.compareTo(p4));
        assertEquals(0,p4.compareTo(p5));
    }
}