package com.catinthedark.game.level;

import entity.Bullet;
import entity.Entity;
import entity.MushroomedCrab;

import java.util.List;

/**
 * Created by Ilya on 24.08.2014.
 */
public class AIManager {
    public void update(Level level) {
        List<MushroomedCrab> entities = level.getCrabs();
        for (Entity entity : entities) {
            Bullet bullet = entity.shot();
            if (bullet != null) {
                level.addBullet(bullet);
            }
        }
    }
}
