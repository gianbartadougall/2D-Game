package npcAttack;

import com.game.Entity.creature.Creature;
import com.game.Entity.creature.Slime;
import com.game.tile.Tile;
import com.game.utilities.Utils;
import com.game.world.World;

import java.awt.*;
import java.util.ArrayList;

public class DefaultEnemyDetection extends Attack {

    private World world;
    private ArrayList<Creature> enemies;
    private Slime slime;

    public DefaultEnemyDetection(int range, int damage, Slime slime, ArrayList<Creature> enemies, World world) {
        super(range, damage);
        this.slime = slime;
        this.enemies = enemies;
        this.world = world;
    }

    @Override
    public boolean tick(int direction) {
        return checkForEnemy(direction);
    }

    private boolean checkForEnemy(int direction) {
        Rectangle range;
        if (direction == Utils.LEFT) {
            range = findRange(direction, (int) slime.getX(), (int) slime.getY(), slime.getBounds());
            if (slime.getX() > World.MIN_WORLD_WIDTH) {
                if (tileContainsEnemy(enemies, range)) {
                    return true;
                }
            }
        }
        if (direction == Utils.RIGHT) {
            range = findRange(direction, (int) slime.getX(), (int) slime.getY(), slime.getBounds());
            if (slime.getXRight() <= World.MAX_FLOAT_WORLD_WIDTH) {
                if (tileContainsEnemy(enemies, range)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean tileContainsEnemy(ArrayList<Creature> enemies, Rectangle rectangle) {
        for (Creature enemy : enemies) {
            Rectangle enemyHitBox = new Rectangle(enemy.getCollisionBox());
            if (rectangle.intersects(enemyHitBox) || rectangle.contains(enemyHitBox)) {
                return true;
            }
        }
        return false;
    }

    public Rectangle findRange(int direction, int x, int y, Rectangle bounds) {

        if (direction == Utils.LEFT) {
            int startX = x+bounds.x;
            int width = startX;
            while (width > World.MIN_WORLD_WIDTH) {
                width--;
                if (collision(width/Tile.TILE_WIDTH, y/Tile.TILE_HEIGHT)) {
                    break;
                }
            }
            return new Rectangle(width, (int) slime.getY(), startX-width, Tile.TILE_HEIGHT-1);
        } else {
            int startX = x+bounds.x+bounds.width;
            int width = startX;
            while (width < World.MAX_FLOAT_WORLD_WIDTH-1) {
                width++;
                if (collision(width/Tile.TILE_WIDTH, y/Tile.TILE_HEIGHT)) {
                    break;
                }
            }
            return new Rectangle(startX, (int) slime.getY(), width-startX, Tile.TILE_HEIGHT-1);
        }

    }

    public boolean collision(int x, int y) {
        Tile tile = world.getTile(x, y);
        return tile.isSolid() || tile.isSlope();
    }
}
