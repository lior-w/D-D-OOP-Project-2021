package Backend;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Rogue extends Player {

    private int MAX_ENERGY = 100;
    protected int cost;
    protected int currentEnergy;

    public Rogue(String name, int healthCapacity, int attack, int defense, int cost) {
        super(name, healthCapacity, attack, defense);
        this.currentEnergy = 100;
        this.cost = cost;
    }

    @Override
    public void processStep() {
        super.processStep();
        this.currentEnergy = Math.min(currentEnergy + 10, MAX_ENERGY);
    }

    public void levelUp() {
        super.levelUp();
        this.currentEnergy = MAX_ENERGY;
        this.attack = attack + (3 * level);
    }

    public void specialAbility() {
        if (currentEnergy >= cost) {
            messageCallback.send(String.format("%s used Fan of Knives", getName()));
            this.currentEnergy = currentEnergy - cost;
            for (Enemy e : getCloseEnemies(2)) {
                int damage = getAttack() - e.defend();
                if (damage > 0) {
                    e.decreaseHealth(damage);
                    messageCallback.send(String.format("%s dealt %d damage to %s", getName(), damage, e.getName()));
                    if (! e.alive()) onKill(e);
                    else messageCallback.send(e.describe());
                }
            }
        } else messageCallback.send(String.format("%s doesn't have enough energy to use special ability", getName()));
    }

    @Override
    public String describe() {
        return String.format("%s\t\tEnergy: %d/%d", super.describe(), currentEnergy, MAX_ENERGY);
    }
}
