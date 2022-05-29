package Backend;

import java.util.LinkedList;
import java.util.List;

public class Mage extends Player {
    protected int manaPool;
    protected int currentMana;
    protected int manaCost;
    protected int spellPower;
    protected int hitsCount;
    protected int abilityRange;

    public Mage(String name, int healthCapacity, int attack, int defense, int manaPool, int manaCost, int spellPower, int hitsCount, int abilityRange) {
        super(name, healthCapacity, attack, defense);
        this.manaPool = manaPool;
        this.currentMana = manaPool / 4;
        this.manaCost = manaCost;
        this.spellPower = spellPower;
        this.hitsCount = hitsCount;
        this.abilityRange = abilityRange;
    }

    @Override
    public void processStep() {
        super.processStep();
        this.currentMana = Math.min(manaPool, currentMana + level);
    }

    public void levelUp() {
        super.levelUp();
        this.manaPool = manaPool + (25 * level);
        this.currentMana = Math.min(currentMana + (manaPool / 4), manaPool);
        this.spellPower = spellPower + (10 * level);
    }

    public void specialAbility() {
        if (currentMana >= manaCost) {
            messageCallback.send(String.format("%s used Blizzard", getName()));
            this.currentMana = currentMana - manaCost;
            int hits = 0;
            Enemy e = getRandomEnemy(getCloseEnemies(abilityRange));
            while (hits < hitsCount & e != null) {
                abilityDamage(e);
                hits++;
                if (! e.alive()) onKill(e);
                else messageCallback.send(e.describe());
                e = getRandomEnemy(getCloseEnemies(abilityRange));
            }
        } else messageCallback.send(String.format("%s doesn't have enough Mana to cast ability", getName()));
    }

    @Override
    public String describe() {
        return String.format("%s\t\tMana: %d/%d\t\tSpellPower: %d", super.describe(), currentMana, manaPool, spellPower);
    }

    private void abilityDamage(Enemy e) {
        int damage = spellPower - e.defend();
        if (damage > 0 & e.alive()) {
            e.decreaseHealth(damage);
            messageCallback.send(String.format("%s dealt %d damage to %s", getName(), damage, e.getName()));
        }
    }
}





