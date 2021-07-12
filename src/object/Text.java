package object;

import java.awt.*;

public class Text extends Sprite{
    String text;
    public Text(float posX, float posY, String text) {
        super(posX, posY);
        this.text = text;
    }
    public void update(float deltaTime){

    }
    public void render(Graphics g){
        g.setColor(Color.DARK_GRAY);
        g.drawString(text, (int) posX, (int) posY);
    }
}
