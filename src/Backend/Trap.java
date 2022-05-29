package Backend;

public class Trap extends Enemy {

    private int visibilityTime;
    private int invisibilityTime;
    private int ticksCount;
    private boolean visible;

    public Trap(char tile, String name, int healthCapacity, int attack, int defense, int experienceValue, int visibilityTime, int invisibilityTime) {
        super(tile, name, healthCapacity, attack, defense, experienceValue);
        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        this.ticksCount = 0;
        this.visible = true;
    }

    @Override
    public void processStep() {
        if (nextAction != null) interact(player);
        updateVisibility();
        updateTickCount();
    }

    @Override
    public void setNextAction() {
        if (getPosition().rangeFrom(player.getPosition()) < 2) nextAction = Action.ATTACK;
        else nextAction = null;
    }

    @Override
    public char getTile() {
        return visible ? super.getTile() : '.';
    }

    private void updateVisibility() {
        visible = ticksCount < visibilityTime;
    }

    private void updateTickCount() {
        if (ticksCount == (visibilityTime + invisibilityTime)) ticksCount = 0;
        else ticksCount++;
    }


}
