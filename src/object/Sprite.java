package object;

import java.awt.*;

public class Sprite {
    public float posX = 0;
    public float posY = 0;
    public boolean isEnemy = false;
    public Graphics g = null;
    public int eventualHp = 100;
    public Sprite(float posX, float posY){
        this.posX = posX;
        this.posY = posY;
    }
    public void update(float deltaTime){
    }
    public void render(Graphics g){
        this.g = g;
    }
}
