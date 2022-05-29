package Backend;

import java.util.List;

public class Position {
    private int x;
    private int y;
    public Position(int x,int y)
    {
        this.x=x;
        this.y=y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double rangeFrom (Position position)
    {
        return Math.sqrt(Math.pow((this.x- position.x),2)+Math.pow((this.y- position.y),2));
    }
    public int compareTo (Position position)
    {
        if(getY() > position.getY())
            return 1;
        if(getY() < position.getY())
            return -1;
        if(getX() > position.getX())
            return 1;
        if (getX() < position.getX())
            return -1;
        return 0;
    }

}
