package object.turret;

import object.Sprite;
import object.bullet.Bullet;
import object.bullet.BulletSpear;
import object.enemy.Enemy;
import world.World;

import java.awt.*;

public class TurretSpear extends Turret {
    double fireRate = 0.5f;
    public TurretSpear(float posX, float posY) {
        super(posX, posY);
    }
    public void update(float deltaTime){
        for (Sprite sprite : World.currentWorld.sprites){
            if (sprite.isEnemy && sprite.eventualHp > 0){
                if (System.nanoTime() - currentTime > fireRate * 1000000000) {
                    World.currentWorld.sprites.add(new BulletSpear(this.posX,this.posY,(Enemy) sprite));
                    currentTime = System.nanoTime();
                }
            }
        }
    }
    public void render(Graphics g){
        g.setColor(Color.DARK_GRAY);
        g.drawRect((int) posX, (int) posY + 2, 1, 3);
        g.drawRect((int) posX, (int) posY, 1, 1);
        g.drawRect((int) posX - 1, (int) posY + 1, 3, 1);
    }
}
