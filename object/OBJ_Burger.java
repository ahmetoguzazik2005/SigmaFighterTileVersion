package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Burger extends SuperObject{
    GamePanel gp;
    public OBJ_Burger(GamePanel gp)  {
        this.gp = gp;
        type = ObjectType.Burger;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/burger.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}