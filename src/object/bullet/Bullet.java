package object.bullet;

import object.Sprite;
import object.enemy.Enemy;
import world.World;

import java.awt.*;
import java.awt.geom.Point2D;

public class Bullet extends Sprite {
    float speed = 100.0f;
    int damage = 5;
    Enemy target = null;
    // Angle towards the target
    float angle = 0;
    // Time to know when to update the death animation
    long lastAnimationTime = System.nanoTime();
    int deathAnimationSteps = 0;
    boolean hasCollided = false;
    public Bullet(float posX, float posY, Enemy target) {
        super(posX, posY);
        this.target = target;

        ((Sprite) target).eventualHp -= this.damage;
    }
    public void update(float deltaTime){
    }
    public void render(Graphics g){
    }
    void deathAnimation(Graphics g){
    }
}
