package Backend;

public class Health {

    private int healthPool;
    private int healthAmount;

    public Health(int pool, int amount) {
        this.healthPool = pool;
        this.healthAmount = amount;
    }

    public int getHealthPool() {
        return healthPool;
    }

    public int getHealthAmount() {
        return healthAmount;
    }

    public void setHealthAmount(int amount) {
        this.healthAmount = Math.min(amount, healthPool);
    }

    public void setHealthPool(int pool) {
        this.healthPool = pool;
    }
}
