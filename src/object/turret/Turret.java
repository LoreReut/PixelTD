package object.turret;

import object.Sprite;
import object.bullet.Bullet;
import object.bullet.BulletNormal;
import object.enemy.Enemy;
import world.World;

import java.awt.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Turret extends Sprite {
    //debug variable
    int bulletCount = 0;
    // Fire rate in seconds
    double fireRate = 0.5;
    long currentTime = System.nanoTime();
    public Turret(float posX, float posY) {
        super(posX, posY);
    }
    public void update(float deltaTime){
        for (Sprite sprite : World.currentWorld.sprites){
            if (sprite.isEnemy && sprite.eventualHp > 0){
                if (System.nanoTime() - currentTime > fireRate * 1000000000) {
                    World.currentWorld.sprites.add(new BulletNormal(this.posX,this.posY,(Enemy) sprite));
                    currentTime = System.nanoTime();
                }
            }
        }
    }
    public void render(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.drawRect((int) posX - 2, (int) posY, 3, 1);
        g.drawRect((int) posX - 1, (int) posY - 1, 1, 2);
    }
}
