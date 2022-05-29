package Backend;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Warrior extends Player {

    protected int abilityCooldown;
    protected int remainingCooldown;

    public Warrior(String name, int healthCapacity, int attack, int defense, int abilityCooldown) {
        super(name, healthCapacity, attack, defense);
        this.abilityCooldown = abilityCooldown;
        this.remainingCooldown = 0;
    }

    public void levelUp() {
        super.levelUp();
        this.remainingCooldown = 0;
        setHealthPool(getHealthPool() + (5 * level));
        this.attack = attack + (2 * level);
        this.defense = defense + level;
    }

    @Override
    public void processStep() {
        super.processStep();
        if (remainingCooldown > 0) remainingCooldown--;
    }

    public void specialAbility() {
        if (remainingCooldown == 0) {
            messageCallback.send(String.format("%s used Avenger's Shield", getName()));
            this.remainingCooldown = abilityCooldown;
            this.health.setHealthAmount(abilityHealth());
            Enemy e = getRandomEnemy(getCloseEnemies(3));
            if (e != null) {
                e.decreaseHealth(abilityDamage());
                messageCallback.send(String.format("%s dealt %d damage to %s", getName(), abilityDamage(), e.getName()));
                if (! e.alive()) onKill(e);
                else messageCallback.send(e.describe());
            }
        } else messageCallback.send(String.format("%s can't use special ability for another %d turns",getName(),remainingCooldown));
    }

    @Override
    public String describe() {
        return String.format("%s\t\tCooldown: %d/%d", super.describe(), remainingCooldown, abilityCooldown);
    }

    private int abilityDamage() {
        return (int) (0.1 * this.health.getHealthPool());
    }

    private int abilityHealth() {
        return Math.min((health.getHealthAmount() + (10 * defense)), health.getHealthPool());
    }
}
