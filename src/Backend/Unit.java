package Backend;
import Frontend.AroundMe;
import Frontend.MessageCallback;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Unit extends Tile {

    protected String name;
    protected Health health;
    protected int attack;
    protected int defense;
    protected MessageCallback messageCallback;
    protected AroundMe tilesAroundMe;

    protected Unit(char tile, String name, int healthCapacity, int attack, int defense) {
        super(tile);
        this.name = name;
        this.health = new Health(healthCapacity, healthCapacity);
        this.attack = attack;
        this.defense = defense;
    }

    public void setMessageCallback(MessageCallback messageCallback) {
        this.messageCallback = messageCallback;
    }

    public void setAroundMe(AroundMe tilesAroundMe) { this.tilesAroundMe = tilesAroundMe; }

    public String getName() { return name; }

    public int getHealthAmount() {
        return health.getHealthAmount();
    }

    public int getHealthPool() {
        return health.getHealthPool();
    }

    public void setHealthAmount(int amount) {
        this.health.setHealthAmount(amount);
    }

    public void decreaseHealth(int amount) {
        setHealthAmount(getHealthAmount() - amount);
    }

    public boolean alive() {
        return getHealthAmount() > 0;
    }

    public void setHealthPool(int pool) {
        this.health.setHealthPool(pool);
    }

    public int getAttack() { return attack; }

    public int getDefense() { return defense; }

    public void swapPosition(Tile t) {
        Position a = super.getPosition();
        Position b = t.getPosition();
        super.initialize(b);
        t.initialize(a);
    }

    public void visit(Empty empty) {
        swapPosition(empty);
    }

    public void visit(Wall wall) {

    }

    public int attack() {
        return (int) (Math.random() * attack);
    }

    public int defend() {
        return (int) (Math.random() * defense);
    }

    public void interact(Tile t) {
        t.accept(this);
    }

    public void battle(Unit u) {
        messageCallback.send(String.format("%s engaged in combat with %s.", getName(), u.getName()));
        messageCallback.send(describe());
        messageCallback.send(u.describe());
        int attackRoll = this.attack();
        messageCallback.send(String.format("%s rolled %d attack points", getName(), attackRoll));
        int defenseRoll = u.defend();
        messageCallback.send(String.format("%s rolled %d defense points", u.getName(), defenseRoll));
        int damage = attackRoll - defenseRoll;
        if (damage > 0) {
            u.decreaseHealth(damage);
            messageCallback.send(String.format("%s dealt %d damage to %s", getName(), damage, u.getName()));
        }
        if (! u.alive()) {
            onKill(u);
        }
    }

    public String describe() {
        return String.format("%s\t\tHealth: %d/%d\t\tAttack: %d\t\tDefense: %d",
                getName(), getHealthAmount(), getHealthPool(), getAttack(), getDefense());
    }

    public Tile tileToInteract(Action action, List<Tile> tiles) {
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        Tile tile = null;
        if (action == Action.MOVE_DOWN) {
            try {
                tile = tiles.stream().filter(t -> t.getPosition().getY() > y).collect(Collectors.toList()).get(0);
            } catch (Exception e) {
                return null;
            }
        }
        if (action == Action.MOVE_UP) {
            try {
                tile = tiles.stream().filter(t -> t.getPosition().getY() < y).collect(Collectors.toList()).get(0);
            } catch (Exception e) {
                return null;
            }
        }
        if (action == Action.MOVE_RIGHT) {
            try {
                tile = tiles.stream().filter(t -> t.getPosition().getX() > x).collect(Collectors.toList()).get(0);
            } catch (Exception e) {
                return null;
            }
        }
        if (action == Action.MOVE_LEFT) {
            try {
                tile = tiles.stream().filter(t -> t.getPosition().getX() < x).collect(Collectors.toList()).get(0);
            } catch (Exception e) {
                return null;
            }
        }
        return tile;
    }

    public abstract void processStep();

    public abstract void onDeath();

    public abstract void onKill(Unit u);

    public abstract void visit(Player player);

    public abstract void visit(Enemy enemy);

    public abstract int getExperience();
}
