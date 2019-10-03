package npcAttack;

import com.game.Ammunittion.Ammo;
import com.game.Statistics.PlayerStats;
import com.game.graphics.GameCamera;
import com.game.weapons.Weapon;

import java.util.ArrayList;

public class PlayerAttack extends Attack {

    private ArrayList<Ammo> ammo;
    private Weapon weapon;
    private PlayerStats playerStats;

    public PlayerAttack(PlayerStats stats, Weapon weapon, int damage) {
        super(weapon.getRange(), damage);
        ammo = new ArrayList<>();
        this.weapon = weapon;
        playerStats = stats;
    }

    @Override
    public boolean tick(int direction) {
        return false;
    }

    public void shoot(GameCamera camera, int direction, int startX, int startY, int mX, int mY) {
        ammo.add(weapon.shoot(camera, direction, startX, startY, mX, mY));
        playerStats.AddBulletsShot();
    }

    public ArrayList<Ammo> getAmmo() {
        return ammo;
    }


}
