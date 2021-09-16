package game;
import graphics.Renderer;
import object.Text;
import object.enemy.Enemy;
import object.turret.Turret;
import object.turret.TurretSpear;
import world.World;

public class Game {

    public static void main(String[] args){
        Renderer renderer = new Renderer();
        renderer.init(new String[0]);

        World.currentWorld = new World();

        //Test code
        World.currentWorld.sprites.add(new Text(54, 60, "Ferran subnormal."));
        for (int i = 0; i < 100; i++){
            World.currentWorld.sprites.add(new Enemy(1 + i * 10, 400));
        }
        for (int i = 0; i < 20; i++){
            World.currentWorld.sprites.add(new TurretSpear(100 + i * 15, 200));
        }
        for (int i = 0; i < 20; i++){
            World.currentWorld.sprites.add(new Turret(100 + i * 10, 600));
        }

        //End of test code

    }

    public static void quit() {
        System.exit(0);
    }
}
