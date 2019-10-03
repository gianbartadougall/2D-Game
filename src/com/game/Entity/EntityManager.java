package com.game.Entity;

import com.game.Entity.creature.Creature;
import com.game.Entity.creature.Player;
import com.game.Entity.creature.Slime;
import com.game.Game;
import com.game.Settings.Settings;
import com.game.Statistics.GameStats;
import com.game.Statistics.PlayerStats;
import com.game.graphics.GameCamera;
import com.game.input.MouseManager;
import com.game.world.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class EntityManager {

    private Settings settings = new Settings();
    private World world;
    private GameCamera camera;
    private GameStats gameStats;
    private Player player;
    private ArrayList<Entity> entities;
    private Comparator<Entity> renderSorter;
    private ArrayList<Creature> slimeEnemies;

    public EntityManager(World world, Game game, GameStats gameStats, PlayerStats playerStats, GameCamera camera, MouseManager mouse) {
        this.camera = camera;
        this.world = world;
        this.gameStats = gameStats;
        entities = new ArrayList<>();
        slimeEnemies = new ArrayList<>();
        this.player = new Player(game, world, this, world.getSpawnX(), world.getSpawnY(), playerStats, camera, mouse);
        entities.add(0, player);
        slimeEnemies.add(player);
        entities.add(new Slime(world, this, 600, 150, slimeEnemies, camera));
        entities.add(new Slime(world, this, 700, 400, slimeEnemies, camera));
        entities.add(new Slime(world, this, 100, 300, slimeEnemies, camera));
        entities.add(new Slime(world, this, 100, 150, slimeEnemies, camera));
    }

    public void tick() {
        for (int i=0; i<entities.size(); i++) {
            Entity e = entities.get(i);
            if (inTickingBounds(e.getHitBox().x, e.getHitBox().y)) {
                if (e.isDead()) {
                    if (e != player) {
                        entities.remove(e);
                    }
                }
                e.tick();
            }
        }
    }

    public void render(Graphics graphics) {
        for (Entity e : entities) {
            if (inRenderBounds(e.getHitBox().x, e.getHitBox().y)) {
                e.render(graphics);
            }
        }
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    private boolean inRenderBounds(int x, int y) {
        return (x > world.getMinXRender() && x < world.getMaxXRender() &&
                y > world.getMinYRender() && y < world.getMaxYRender());
    }

    private boolean inTickingBounds(int x, int y) {
        int renderDistance = settings.renderDistance();
        return (x > world.getMinXRender() - renderDistance && x < world.getMaxXRender() + renderDistance &&
                y > world.getMinYRender() - renderDistance && y < world.getMaxYRender() + renderDistance &&
                x >= World.MIN_WORLD_WIDTH && x <= World.MAX_FLOAT_WORLD_WIDTH &&
                y >= World.MIN_WORLD_HEIGHT && y <= World.MAX_FLOAT_WORLD_HEIGHT);
    }

    public void resetPlayer() {
        player.x = 800;
        player.y = 100;
    }

}
