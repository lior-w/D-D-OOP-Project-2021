package Backend;

import Frontend.*;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Player extends Unit {
    public static final char playerTile = '@';
    protected static final int REQ_EXP = 50;
    protected int level;
    protected int experience;
    protected PlayerDeathCallback deathCallback;
    protected InputProvider inputProvider;
    protected Enemies enemies;

    protected Player(String name, int healthCapacity, int attack, int defense) {
        super(playerTile, name, healthCapacity, attack, defense);
        this.level = 1;
        this.experience = 0;
    }

    public void setEnemies(Enemies enemies){ this.enemies=enemies; }

    public void setDeathCallback(PlayerDeathCallback deathCallback) {
        this.deathCallback = deathCallback;
    }

    public void setInputProvider(InputProvider inputProvider) { this.inputProvider = inputProvider; }

    public void accept(Unit u){
        u.visit(this);
    }

    public void visit(Enemy e){
        super.battle(e);
    }

    @Override
    public void processStep() {
        if (getAction() == Action.CAST_ABILITY) specialAbility();
        else interact(tileToInteract(getAction(), tilesAroundMe.get()));
        messageCallback.send(describe());
    }

    public Action getAction(){
        return inputProvider.getAction();
    }

    public void addExperience(int exp) {
        this.experience = experience + exp;
    }

    // When the player kills an enemy
    public void onKill(Unit e){
        int experienceGained = e.getExperience();
        messageCallback.send(String.format("%s died, %s gained %d experience", e.getName(), getName(), experienceGained));
        addExperience(experienceGained);
        if (getPosition().rangeFrom(e.getPosition()) == 1) swapPosition(e);
        e.onDeath();
        if (getExperience() >= levelUpRequirement()) levelUp();
    }

    // When the player dies
    @Override
    public void onDeath() {
        messageCallback.send("You lost.");

        // Use deathCallback to alert the level manager
        deathCallback.call();
    }

    // Backend.Player level up
    protected void levelUp(){
        this.experience = experience - (50 * level);
        this.level = level + 1;
        setHealthPool(getHealthPool() + (10 * level));
        setHealthAmount(getHealthPool());
        this.attack  = attack + (4 * level);
        this.defense = defense + level;
        messageCallback.send(String.format("%s got next level",getName()));
    }

    private int levelUpRequirement(){
        return REQ_EXP * level;
    }

    public int getLevel() {
        return level;
    }
    public int getExperience() {
        return experience;
    }

    public void visit(Player p){

    }

    public List<Enemy> getEnemies() {
        return enemies.getEnemies();
    }

    protected List<Enemy> getCloseEnemies(int n) {
        return getEnemies().stream()
                .filter(e -> e.getPosition().rangeFrom(this.getPosition()) <= n).collect(Collectors.toList());
    }
    protected Enemy getRandomEnemy(List<Enemy> enemiesList) {
        if (enemiesList.size() > 0) return enemiesList.get((int) (Math.random() * enemiesList.size()));
        return null;
    }

    public String describe() {
        return String.format("%s\t\tLevel: %d\t\tExperience: %d/%d", super.describe(), getLevel(), getExperience(), levelUpRequirement());
    }

    @Override
    public char getTile() {
        return alive() ? super.getTile() : 'X';
    }

    public abstract void specialAbility();

}

