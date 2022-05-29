package Backend;
import Frontend.EnemyDeathCallback;

public abstract class Enemy extends Unit {

    protected char enemyTile;
    protected int experienceValue;
    protected Action nextAction;
    protected EnemyDeathCallback enemyDeathCallback;
    protected Player player;

    protected Enemy(char tile, String name, int healthCapacity, int attack, int defense, int experienceValue) {
        super(tile, name, healthCapacity, attack, defense);
        this.enemyTile = tile;
        this.experienceValue = experienceValue;
    }

    public void setPlayer(Player player) { this.player = player; }

    public void setDeathCallback(EnemyDeathCallback deathCallback) {
        this.enemyDeathCallback = deathCallback;
    }

    public int getExperience() {
        return experienceValue;
    }

    public void accept(Unit u) {
        u.visit(this);
    }

    public void visit(Enemy enemy) { }

    public void visit(Player player) {
        this.battle(player);
    }

    public void onDeath() {
        enemyDeathCallback.call();
    }

    public void onKill(Unit u) {
        u.onDeath();
    }

    public String describe() {
        return String.format("%s\t\tExperience Value: %d", super.describe(), getExperience());
    }

    public abstract void processStep();

    public abstract void setNextAction();
}
