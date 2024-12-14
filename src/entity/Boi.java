package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Boi extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    // Player images
    private BufferedImage up1, up2, up3;
    private BufferedImage down1, down2, down3;
    private BufferedImage left1, left2, left3;
    private BufferedImage right1, right2, right3;

    public Boi(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        // Center the player on the screen
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // Initialize collision box
        solidArea = new Rectangle(8, 16, 32, 32);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down"; // Default direction
    }

    public void getPlayerImage() {
        try {
            // Load sprites for all directions
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/boi/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/boi/up2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/res/boi/up3.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/boi/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/boi/down2.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/res/boi/down3.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/boi/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/boi/left2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/res/boi/left3.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/boi/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/boi/right2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/res/boi/right3.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: Could not load player images.");
        }
    }

    public void update() {
        // Handle key input and set direction
        if (!keyH.upPressed && !keyH.downPressed && !keyH.leftPressed && !keyH.rightPressed) {
            return; // Early exit if no input
        }

        if (keyH.upPressed) {
            direction = "up";
        } else if (keyH.downPressed) {
            direction = "down";
        } else if (keyH.leftPressed) {
            direction = "left";
        } else if (keyH.rightPressed) {
            direction = "right";
        }

        // Check tile collision
        collisionEntity = false;
        //gp.collisionChecker.checkTile(this);

        // Move player if no collision
        if (!collisionEntity) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }

        // Sprite animation logic
        spriteCounter++;
        if (spriteCounter > 10) { 
            spriteNumber = (spriteNumber % 3) + 1; 
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

    
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

        // Draw the selected image on the screen
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        // Optional: Debugging information (draw collision box)
        g2.setColor(Color.RED);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }
}
