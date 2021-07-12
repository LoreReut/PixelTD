package object.bullet;

import object.enemy.Enemy;
import world.World;

import java.awt.*;
import java.awt.geom.Point2D;

public class BulletNormal extends Bullet{
    public BulletNormal(float posX, float posY, Enemy target) {
        super(posX, posY, target);
    }
    public void update(float deltaTime){
        double distance = Point2D.distance(posX, posY, target.posX, target.posY);

        // Check if it's 4 pixels away from the objective, if so, deal dmg and blow up. Else, keep going towards it.
        if (distance <= 4) {
            if (!hasCollided) {
                target.takeDamage(damage);
            }
            hasCollided = true;
            // Avoid NullPointer and check that the target is alive to do the animation
            if (g != null && !target.isDead) {
                deathAnimation(g);
            }
        } else {
            // Calculate a straight line to the target
            angle = (float) Math.toDegrees(Math.atan2(target.posX - posX, target.posY - posY));

            if (angle < 0 ) { angle += 360; }
            posX += speed * Math.sin(angle) * deltaTime;
            posY += speed * Math.cos(angle) * deltaTime;
        }
    }
    public void render(Graphics g){
        this.g = g;
        g.setColor(Color.RED);
        if (!hasCollided) {
            g.drawRect((int) this.posX - 1, (int) this.posY - 1, 2, 2);
        }
    }
    void deathAnimation(Graphics g){
        g.setColor(Color.YELLOW);
        switch (deathAnimationSteps){
            case 1:
                g.drawRect((int) posX + 3, (int) posY + 3, 1, 1);
                g.drawRect((int) posX - 3, (int) posY - 3, 1, 1);
                g.drawRect((int) posX + 3, (int) posY - 3, 1, 1);
                g.drawRect((int) posX - 3, (int) posY + 3, 1, 1);
                break;
            case 2:
                g.setColor(new Color(199, 93, 0));
                g.drawRect((int) posX + 6, (int) posY + 6, 1, 1);
                g.drawRect((int) posX - 6, (int) posY - 6, 1, 1);
                g.drawRect((int) posX + 6, (int) posY - 6, 1, 1);
                g.drawRect((int) posX - 6, (int) posY + 6, 1, 1);
                break;
            case 3:
                g.setColor(new Color(128, 0, 0));
                g.drawRect((int) posX + 7, (int) posY + 7, 1, 1);
                g.drawRect((int) posX - 7, (int) posY - 7, 1, 1);
                g.drawRect((int) posX + 7, (int) posY - 7, 1, 1);
                g.drawRect((int) posX - 7, (int) posY + 7, 1, 1);
                break;
            case 4:
                g.setColor(new Color(90, 0, 0));
                g.drawRect((int) posX + 9, (int) posY + 9, 1, 1);
                g.drawRect((int) posX - 9, (int) posY - 9, 1, 1);
                g.drawRect((int) posX + 9, (int) posY - 9, 1, 1);
                g.drawRect((int) posX - 9, (int) posY + 9, 1, 1);
                break;
            case 5: World.currentWorld.sprites.remove(this); break;
        }
        if (System.nanoTime() - lastAnimationTime > 250000000){
            deathAnimationSteps++;
            lastAnimationTime = System.nanoTime();
        }
    }
}
