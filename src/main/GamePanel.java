package main;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import entity.Boi;
import entity.Red;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    // Screen and tile setup
    final int originalTileSize = 16; // 16x16 tiles
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 12;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768
    public final int screenHeight = tileSize * maxScreenRow; // 576

    // World size
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    
    private Thread gameThread;
    private KeyHandler keyHandler = new KeyHandler();
    public TileManager tileManager= new TileManager(this);
    public Boi boi;
    private Red red;

    private int FPS = 60;

    // Collision Effect
    private boolean collisionEffect = false;
    private int collisionEffectCounter = 0;
    private int collisionX, collisionY; // Collision position
    private ArrayList<Particle> particles = new ArrayList<>();

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        // Initialize Boi and Red
        boi = new Boi(this, keyHandler);
        red = new Red(this, keyHandler);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS; // 0.016 seconds per frame
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        
        boi.update();
        red.update();

        // Collision detection
        if (Math.abs(boi.worldX - red.worldX) < tileSize && Math.abs(boi.worldY - red.worldY) < tileSize) {
            if (!collisionEffect) {
                collisionEffect = true;
                collisionEffectCounter = 120; 
                collisionX = (boi.worldX + red.worldX) / 2; 
                collisionY = (boi.worldY + red.worldY) / 2;

                // Create particles
                for (int i = 0; i < 20; i++) {
                    particles.add(new Particle(collisionX, collisionY, tileSize, tileSize));
                }
            }
        }

        // Update collision effect counter
        if (collisionEffect) {
            collisionEffectCounter--;
            if (collisionEffectCounter <= 0) {
                collisionEffect = false;
                particles.clear(); // Remove particles after effect ends
            }
        }

        // Update particles
        Iterator<Particle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            Particle particle = iterator.next();
            particle.update();
            if (particle.life <= 0) {
                iterator.remove();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
       

        // Draw the world, Boi, and Red
        tileManager.draw(g2);
        boi.draw(g2);
        red.draw(g2);

     

        g2.dispose();
    }


   
}

// Particle class for animation
class Particle {
    int x, y;
    int dx, dy;
    int life;
    Color color;
    Random random = new Random();

    public Particle(int startX, int startY, int maxDx, int maxDy) {
        this.x = startX;
        this.y = startY;
        this.dx = random.nextInt(maxDx * 2) - maxDx; 
        this.dy = random.nextInt(maxDy * 2) - maxDy;
        this.life = random.nextInt(60) + 30; 
        this.color = new Color(255, 105, 180, 150); 
    }

    public void update() {
        x += dx;
        y += dy;
        life--;
    }

    public void draw(Graphics2D g2, int boiWorldX, int boiWorldY, int boiScreenX, int boiScreenY) {
        int screenX = x - boiWorldX + boiScreenX;
        int screenY = y - boiWorldY + boiScreenY;
        g2.setColor(color);
        g2.fillOval(screenX, screenY, 10, 10); // Draw particle
    }
}
