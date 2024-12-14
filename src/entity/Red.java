package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Red extends Entity {
    GamePanel gp;

    // Red sprite images
    private BufferedImage up1, up2, up3;
    private BufferedImage down1, down2, down3;
    private BufferedImage left1, left2, left3;
    private BufferedImage right1, right2, right3;

    public Red(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;

        // Initialize collision box
        solidArea = new Rectangle(8, 16, 32, 32);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        // Initial position in the world
        worldX = gp.tileSize * 25; // Starting at a world coordinate
        worldY = gp.tileSize * 25;
        speed = 2; // Red moves slower than the player
        direction = "down"; // Default direction
    }

    public void getPlayerImage() {
        try {
            // Load sprites for all directions
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/red/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/red/up2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/res/red/up3.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/red/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/red/down2.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/res/red/down3.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/red/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/red/left2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/res/red/left3.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/red/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/red/right2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/res/red/right3.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: Could not load red entity images.");
        }
    }

    public void update() {
        // Add simple AI for movement or interaction logic
        // Example: Random or simple chase behavior (optional)
        // For now, Red remains static.

        // Sprite animation logic
        spriteCounter++;
        if (spriteCounter > 10) { // Adjust the value for animation speed
            spriteNumber = (spriteNumber % 3) + 1; // Toggle between 1, 2, and 3
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // Select the current frame of animation based on direction and spriteNumber
        switch (direction) {
            case "up":
                if (spriteNumber == 1) image = up1;
                if (spriteNumber == 2) image = up2;
                if (spriteNumber == 3) image = up3;
                break;
            case "down":
                if (spriteNumber == 1) image = down1;
                if (spriteNumber == 2) image = down2;
                if (spriteNumber == 3) image = down3;
                break;
            case "left":
                if (spriteNumber == 1) image = left1;
                if (spriteNumber == 2) image = left2;
                if (spriteNumber == 3) image = left3;
                break;
            case "right":
                if (spriteNumber == 1) image = right1;
                if (spriteNumber == 2) image = right2;
                if (spriteNumber == 3) image = right3;
                break;
        }

        // Calculate screen position relative to the player's camera
        int screenX = worldX - gp.boi.worldX + gp.boi.screenX;
        int screenY = worldY - gp.boi.worldY + gp.boi.screenY;

        // Only draw if Red is within the visible screen
        if (screenX + gp.tileSize > 0 && screenX < gp.screenWidth &&
            screenY + gp.tileSize > 0 && screenY < gp.screenHeight) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
