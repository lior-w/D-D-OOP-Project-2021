package Backend;

import java.util.List;
import java.util.stream.Collectors;

public class Monster extends Enemy {

    private int visionRange;

    public Monster(char tile, String name, int healthCapacity, int attack, int defense, int visionRange, int experienceValue) {
        super(tile, name, healthCapacity, attack, defense, experienceValue);
        this.visionRange = visionRange;
    }

    @Override
    public void processStep() {
        setNextAction();
        Tile t = tileToInteract(getNextAction(), tilesAroundMe.get());
        if (t != null) interact(t);
    }

    public void setNextAction() {
        if (this.getPosition().rangeFrom(player.getPosition()) < visionRange) this.nextAction = chaseAction();
        else this.nextAction = randomAction();
    }

    public Action getNextAction() {
        return nextAction;
    }

    private Action randomAction() {
        int x = (int) (Math.random() * 4);
        if (x == 0) return Action.MOVE_LEFT;
        if (x  == 1) return Action.MOVE_RIGHT;
        if (x == 2) return Action.MOVE_UP;
        if (x == 3) return Action.MOVE_DOWN;
        else return Action.DO_NOTHING;
    }

    private Action chaseAction() {
        int dx = this.getPosition().getX() - player.getPosition().getX();
        int dy = this.getPosition().getY() - player.getPosition().getY();
        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) return Action.MOVE_LEFT;
            return Action.MOVE_RIGHT;
        } else {
            if (dy > 0) return Action.MOVE_UP;
            return Action.MOVE_DOWN;
        }
    }

    public String describe() {
        return String.format("%s\t\tVision Range: %d", super.describe(), visionRange);
    }
}
