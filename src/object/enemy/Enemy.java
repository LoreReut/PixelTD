package object.enemy;

import object.Sprite;
import world.World;

import java.awt.*;

public class Enemy extends Sprite {
    float speed = 50.0f;
    Color color = Color.WHITE;
    int hp = 100;
    // The hp it will have when all the bullets have reached it
    public int eventualHp = 100;
    // Time to know how long to turn red when recieving dmg
    long lastDamageTime = System.nanoTime();
    // Time to know when to update the death animation
    long lastAnimationTime = System.nanoTime();
    int deathAnimationSteps = 0;
    public boolean isDead = false;
    public Enemy(float posX, float posY) {
        super(posX, posY);
        this.isEnemy = true;
    }
    public void update(float deltaTime){
        if (hp <= 0 && g != null){
            isDead = true;
            deathAnimation(g);
        }
        posX += speed * deltaTime;
    }
    public void render(Graphics g){
        this.g = g;
        // Check if a millisecond (I think?) has passed since the last time it took dmg, if not change it back to white
        if (System.nanoTime() - lastDamageTime <= (0.1 * 1000000000)){
        } else {color = (Color.WHITE);}
        g.setColor(color);
        if ( hp > 0 ) {
            g.drawRect((int) this.posX - 1, (int) this.posY - 1, 2, 2);
            g.drawRect((int) this.posX, (int) this.posY, 2, 2);
        }
    }
    public void takeDamage(int damage){
        lastDamageTime = System.nanoTime();
        color = Color.RED;
        hp -= damage;
    }
    void deathAnimation(Graphics g) {
        switch (deathAnimationSteps){
            case 1:
                g.setColor(new Color(255, 255, 255));
                g.drawOval((int) posX, (int) posY, 2, 2);
                break;
            case 2:
                g.setColor(new Color(245, 245, 245));
                g.drawOval((int) posX - 1, (int) posY - 1, 2, 2);
                break;
            case 3:
                g.setColor(new Color(235, 235, 235));
                g.drawOval((int) posX - 3, (int) posY - 3, 5, 5);
                break;
            case 4:
                g.setColor(new Color(210, 210, 210));
                g.drawOval((int) posX - 5, (int) posY - 5, 10, 10);
                break;
            case 5:
                g.setColor(new Color(190, 190, 190));
                g.drawOval((int) posX - 8, (int) posY - 8, 15, 15); break;
            case 6:
                g.setColor(new Color(150, 150, 150));
                g.drawOval((int) posX - 10, (int) posY - 10, 20, 20);
                break;
            case 7:
                g.setColor(new Color(120, 120, 120));
                g.drawOval((int) posX - 13, (int) posY - 13, 25, 25);
                break;
            case 8:
                g.setColor(new Color(90, 90, 90));
                g.drawOval((int) posX - 15, (int) posY - 15, 30, 30);
                break;
            case 9:
                g.setColor(new Color(40, 40, 40));
                g.drawOval((int) posX - 20, (int) posY - 20, 40, 40);
                break;
            case 10: World.currentWorld.sprites.remove(this);
        }
        if (System.nanoTime() - lastAnimationTime > 100000000){
            deathAnimationSteps++;
            lastAnimationTime = System.nanoTime();
        }
    }
}
