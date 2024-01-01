package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_key extends SuperObject {
    GamePanel gp;
    public OBJ_key(GamePanel gp){
        name = "Key";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/pics/objects/key.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
