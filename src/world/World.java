package world;

import object.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

public class World {
    private static long lastTime = System.nanoTime();
    public static World currentWorld = null;
    public CopyOnWriteArrayList<Sprite> sprites = new CopyOnWriteArrayList<>();

    public static void update(){
        float deltaTime = (System.nanoTime() - lastTime) / 1000000000.0f;
        lastTime = System.nanoTime();

        for (Sprite sprite : currentWorld.sprites){
            sprite.update(deltaTime);
        }
    }
    public static void render (Graphics g){
        for (Sprite sprite : currentWorld.sprites){
            sprite.render(g);
        }
    }
    public void addSprite(Sprite sprite){
        List<Sprite> list = Collections.synchronizedList(currentWorld.sprites);

        synchronized (list){
            list.add(sprite);
        }
    }
}
