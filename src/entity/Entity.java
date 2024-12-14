package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    public int worldX, worldY;
    public int speed;
    

    public BufferedImage up1, up2, up3, down1, down2, dow3, left1,left2,left3,right1,right2,right3;
    public String direction;

    public int spriteCounter =0;
    public int spriteNumber =1;

    public Rectangle solidArea;
    public boolean collisionEntity = false;


}