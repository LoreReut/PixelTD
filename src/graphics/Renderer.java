package graphics;

import game.Game;
import world.World;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.VolatileImage;

public class Renderer {

    private static Frame frame;
    private static Canvas canvas;
    private static int canvasWidth = 0;
    private static int canvasHeight = 0;
    // Desired size of the game
    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 600;
    // Final size of the game
    private static int gameWidth = 0;
    private static int gameHeight = 0;
    // Variables for the FPS counter
    private static long lastCheckedFPS = 0;
    private static int currentFPS = 0;
    private static int totalFrames = 0;

    public static void init (String[] args) {
        setBestSize();
        frame = new Frame();
        canvas = new Canvas();

        canvas.setPreferredSize(new Dimension(canvasWidth, canvasHeight));
        frame.add(canvas);
        // It's important we do this one before pack(). Temporarily disabled due to debugging purposes
        //makeFullScreen();
        // Set the size of the frame to "pack" around the canvas, setting it basically to the preferredSize
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Game.quit();
            }
        });
        startRendering();
    }
    private static void setBestSize() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        boolean done = false;

        while (!done){
            canvasWidth += GAME_WIDTH;
            canvasHeight += GAME_HEIGHT;
            if (canvasWidth > screenSize.width || canvasHeight > screenSize.height) {
                done = true;
                canvasWidth -= GAME_WIDTH;
                canvasHeight -= GAME_HEIGHT;
            }
        }
        // Sets the canvas size so that it stays within the ratio as close as possible to the users resolution
        int xDiff = screenSize.width - canvasWidth;
        int yDiff = screenSize.height - canvasHeight;
        int factor = canvasWidth / GAME_WIDTH;
        gameWidth = canvasWidth / factor + xDiff / factor;
        gameHeight = canvasHeight / factor + yDiff / factor;
        canvasWidth = gameWidth * factor;
        canvasHeight = gameHeight * factor;
        System.out.println(canvasWidth + " " + canvasHeight);
    }
    private static void makeFullScreen(){
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = environment.getDefaultScreenDevice();

        if (graphicsDevice.isFullScreenSupported()){
            frame.setUndecorated(true);
            graphicsDevice.setFullScreenWindow(frame);
        }
    }
    private static void startRendering(){
        Thread thread = new Thread(){
            public void run(){
                GraphicsConfiguration gConfig = canvas.getGraphicsConfiguration();
                VolatileImage vImage = gConfig.createCompatibleVolatileImage(gameWidth, gameHeight);

                while (true){
                    // In case the gConfig is no longer compatible, generate again a compatible image
                    if (vImage.validate(gConfig) == VolatileImage.IMAGE_INCOMPATIBLE){
                        vImage = gConfig.createCompatibleVolatileImage(gameWidth, gameHeight);
                    }
                    Graphics g = vImage.getGraphics();

                    g.setColor(Color.BLACK);
                    g.fillRect(0, 0, gameWidth, gameHeight);
                    World.update();
                    World.render(g);
                    // Calculate the FPS and draw them on screen every second
                    totalFrames++;
                    if (System.nanoTime() > lastCheckedFPS + 1000000000){
                        lastCheckedFPS = System.nanoTime();
                        currentFPS = totalFrames;
                        totalFrames = 0;
                    }
                    g.setColor(Color.DARK_GRAY);
                    g.drawString(String.valueOf(currentFPS), 1, gameHeight - 4);
                    g = canvas.getGraphics();
                    g.drawImage(vImage, 0, 0, canvasWidth, canvasHeight, null);
                    g.dispose();
                }
            }
        };
        thread.setName("Rendering Thread");
        thread.start();
    }
}
