package npcAttack;

public abstract class Attack {

    private int range;
    private int damage;

    public Attack(int range, int damage) {
        this.range = range;
        this.damage = damage;
    }

    public abstract boolean tick(int direction);
}
