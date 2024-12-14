package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
   public int mapTileNum[][]; // Map layout for background


    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];
        mapTileNum= new int[gp.maxWorldCol][gp.maxWorldRow];

        
        getTileImage();
        loadMap("/res/maps/world1.txt");
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass.png"));
            tile[0].name="Grass";

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/tree.png"));
            tile[1].collision =true;
            tile[1].name="Tree";

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/water.png"));
            tile[2].collision =true;
            tile[2].name="Water";

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/moly.png"));
            tile[3].collision =true;
            tile[3].name="Moly";

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/waterPierre.png"));
            tile[4].collision =true;
            tile[4].name="WaterPierre";

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/treeAbandonned.png"));
            tile[5].collision =true;
            tile[5].name="treeAbandonned";

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/waterfall.png"));
            tile[6].collision =true;
            tile[6].name="waterFall";

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/floor.png"));
            tile[7].name="Floor";

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /* 
    public void draw(Graphics2D g2) {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                int tileType = map[row][col];
                g2.drawImage(tile[tileType].image, col * gp.tileSize, row * gp.tileSize, gp.tileSize, gp.tileSize, null);
            }
        }
    }
*/
public void loadMap(String filePath) {
    try (
        InputStream is = getClass().getResourceAsStream(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

        for (int row = 0; row < gp.maxWorldRow; row++) {
            String line = br.readLine();
            
            if (line == null) {
                throw new IllegalArgumentException("Input file does not have enough rows for the map.");
            }
            
            String[] numbers = line.split(" ");
            if (numbers.length != gp.maxWorldCol) {
                throw new IllegalArgumentException("Row " + row + " does not have the expected " + gp.maxWorldCol + " columns.");
            }

            for (int col = 0; col < gp.maxWorldCol; col++) {
                mapTileNum[row][col] = Integer.parseInt(numbers[col]);
            }
        }

    } catch (Exception e) {
        e.printStackTrace(); // For debugging; 
        System.out.println("Error loading map: " + e.getMessage());
    }
}

/* 
    public void draw(Graphics2D g2){

        for (int row = 0; row< gp.maxWorldRow; row++){
            for(int col=0; col <gp.maxWorldCol; col++){
                int tileType=mapTileNum[row][col];
                System.out.println(tileType);
                g2.drawImage(tile[0].image,col * gp.tileSize, row * gp.tileSize, gp.tileSize,gp.tileSize,null);
                g2.drawImage(tile[tileType].image,col * gp.tileSize, row * gp.tileSize, gp.tileSize,gp.tileSize,null);

            }
        }
    }
*/
public void draw(Graphics2D g2) {
    // Determine visible area based on the player's position
    int worldStartCol = Math.max(0, (gp.boi.worldX - gp.boi.screenX) / gp.tileSize);
    int worldStartRow = Math.max(0, (gp.boi.worldY - gp.boi.screenY) / gp.tileSize);
    int worldEndCol = Math.min(gp.maxWorldCol - 1, (gp.boi.worldX + gp.screenWidth) / gp.tileSize);
    int worldEndRow = Math.min(gp.maxWorldRow - 1, (gp.boi.worldY + gp.screenHeight) / gp.tileSize);

    // Loop through the visible tiles
    for (int worldRow = worldStartRow; worldRow <= worldEndRow; worldRow++) {
        for (int worldCol = worldStartCol; worldCol <= worldEndCol; worldCol++) {
            int tileNum = mapTileNum[worldRow][worldCol];

            // Handle out-of-bounds tiles or invalid values
            if (tileNum < 0 || tileNum >= tile.length || tile[tileNum] == null) {
                tileNum = 0; 
            }


            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.boi.worldX + gp.boi.screenX;
            int screenY = worldY - gp.boi.worldY + gp.boi.screenY;

            
            g2.drawImage(tile[0].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}


}