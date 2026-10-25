package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {

    GamePanel gp;
    public double worldX, worldY;
    public double speed, baseSpeed;


    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public Direction direction;

    public int spiritCounter = 0;
    public int spiritNumb = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionX = false;
    public boolean collisionY = false;
    public int actionLogCounter = 0;
    public boolean collision = true;
    int crashedToAn = 0;
    boolean doesCrashedToAn = false;
    String [] dialogues = new String[20];
    int dialogIndex = 0;
    int dialogTotalLine = 0;

    public int maxLife = 0;
    public int life = 0;

    public Entity (GamePanel gp){
        this.gp = gp;
    }

    public BufferedImage setUp(String imagePath){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
    public void setAction(){}

    public void speak(){


        if(dialogues[dialogIndex] == null){
            dialogIndex = 0;
        }
        gp.ui.currentDialog = dialogues[dialogIndex];

        switch (gp.player.direction){
            case LEFT, DOWNLEFT, UPLEFT:
                direction = Direction.RIGHT;
                break;
            case RIGHT, DOWNRIGHT, UPRIGHT:
                direction = Direction.LEFT;
                break;
            case UP:
                direction = Direction.DOWN;
                break;
            case DOWN:
                direction = Direction.UP;
                break;

        }
    }
    public void refreshDialog(){
        if(gp.keyH.nextTalkPressed){
            dialogIndex++;
            if(dialogues[dialogIndex] == null){
                dialogIndex = 0;
            }
            gp.ui.currentDialog = dialogues[dialogIndex];
            gp.keyH.nextTalkPressed = false;

        }

    }
    public void update(){

        if(doesCrashedToAn){
            crashedToAn++;
            if(crashedToAn >= 0){
                doesCrashedToAn = false;
                crashedToAn = 0;
            }
        }else{
            setAction();

            boolean diagonal = false;

            if(direction == Direction.DOWNRIGHT || direction == Direction.UPRIGHT || direction == Direction.DOWNLEFT || direction == Direction.UPLEFT){
                diagonal = true;
            }

            if (diagonal) {
                speed =  baseSpeed / Math.sqrt( 2.0 );
            } else {
                speed = baseSpeed;
            }

            collisionX = false;
            collisionY = false;
            gp.cChecker.checkTile(this);
            gp.cChecker.checkObject(this, false);
            int x = gp.cChecker.checkPOWNpc(this, gp.npc, gp.player);
            if(x != -1){
                doesCrashedToAn = true;
            }
            switch (direction) {
                case UP:    if (!collisionY) worldY -= speed; break;
                case DOWN:  if (!collisionY) worldY += speed; break;
                case LEFT:  if (!collisionX) worldX -= speed; break;
                case RIGHT: if (!collisionX) worldX += speed; break;
                case UPRIGHT:   if (!collisionY) worldY -= speed; if (!collisionX) worldX += speed; break;
                case UPLEFT:    if (!collisionY) worldY -= speed; if (!collisionX) worldX -= speed; break;
                case DOWNRIGHT: if (!collisionY) worldY += speed; if (!collisionX) worldX += speed; break;
                case DOWNLEFT:  if (!collisionY) worldY += speed; if (!collisionX) worldX -= speed; break;
            }

            spiritCounter++;

            if (spiritCounter == 12) {
                spiritNumb = (spiritNumb == 1) ? 2 : 1;
                spiritCounter = 0;
            }
        }



    }
    public void draw(Graphics2D g2d){

        BufferedImage image = null;

        int screenX = (int)(worldX - gp.player.worldX + gp.player.screenX);
        int screenY = (int)(worldY - gp.player.worldY + gp.player.screenY);

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch (direction) {
                case UP:
                    if (spiritNumb == 1) {
                        image = up1;
                    } else if (spiritNumb == 2) {
                        image = up2;
                    }
                    break;
                case DOWN:
                    if (spiritNumb == 1) {
                        image = down1;
                    } else if (spiritNumb == 2) {
                        image = down2;
                    }
                    break;
                case LEFT, UPLEFT, DOWNLEFT:
                    if (spiritNumb == 1) {
                        image = left1;
                    } else if (spiritNumb == 2) {
                        image = left2;
                    }
                    break;
                case RIGHT, UPRIGHT, DOWNRIGHT:
                    if (spiritNumb == 1) {
                        image = right1;
                    } else if (spiritNumb == 2) {
                        image = right2;
                    }
                    break;

            }
            g2d.drawImage(image,screenX,screenY,null);
            // For debug
            /*g2d.setColor(Color.red);
            g2d.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
            g2d.setColor(Color.blue);
            g2d.drawRect(screenX, screenY, gp.tileSize, gp.tileSize);*/
        }
    }
}
