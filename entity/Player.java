package entity;
import main.GamePanel;
import main.GameStateType;
import main.KeyHandler;
import main.UtilityTool;
import object.ObjectType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX, screenY;
    //public int hasKey = 0;
    int standCounter = 0;
    int crashedToAn = 0;
    boolean doesCrashedToAn = false;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2) ;

        setDefaultValues();
        getPlayerImage() ;

        solidArea.x = 12;
        solidArea.y = 16;
        solidArea.width = 24;
        solidArea.height = 20;
        solidAreaDefaultX = 12;
        solidAreaDefaultY = 16;

    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        baseSpeed = 5;
        speed = baseSpeed;
        direction = Direction.DOWN;

        maxLife = 6;
        life = 6;
    }

    public void getPlayerImage(){

        up1 = setUp("/player/boy_up_1");
        up2 = setUp("/player/boy_up_2");
        down1 = setUp("/player/boy_down_1");
        down2 = setUp("/player/boy_down_2");
        right1 = setUp("/player/boy_right_1");
        right2 = setUp("/player/boy_right_2");
        left1 = setUp("/player/boy_left_1");
        left2 = setUp("/player/boy_left_2");

    }

    public void update() {
        if(doesCrashedToAn){
            crashedToAn++;
            if(crashedToAn >= 20){
                doesCrashedToAn = false;
                crashedToAn = 0;
            }
        }
        if ((keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) && !doesCrashedToAn) {

            boolean diagonal = false;

            if (keyH.upPressed && keyH.rightPressed) {
                direction = Direction.UPRIGHT;   diagonal = true;
            } else if (keyH.upPressed && keyH.leftPressed) {
                direction = Direction.UPLEFT;    diagonal = true;
            } else if (keyH.downPressed && keyH.rightPressed) {
                direction = Direction.DOWNRIGHT; diagonal = true;
            } else if (keyH.downPressed && keyH.leftPressed) {
                direction = Direction.DOWNLEFT;  diagonal = true;
            } else if (keyH.upPressed) {
                direction = Direction.UP;
            } else if (keyH.downPressed) {
                direction = Direction.DOWN;
            } else if (keyH.leftPressed) {
                direction = Direction.LEFT;
            } else if (keyH.rightPressed) {
                direction = Direction.RIGHT;
            }

            if (diagonal) {
                speed =  baseSpeed / Math.sqrt( 2.0 );
            } else {
                speed = baseSpeed;
            }

            collisionX = false;
            collisionY = false;

            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            gp.cChecker.checkTile(this);

            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            if(npcIndex != -1){
                doesCrashedToAn = true;
            }
            interactNPC(npcIndex);
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

            if (!collisionX || !collisionY) {
                spiritCounter++;
                if (spiritCounter == 12) {
                    spiritNumb = (spiritNumb == 1) ? 2 : 1;
                    spiritCounter = 0;
                }
            }
        }else{
            standCounter++;

            if(standCounter == 20){
                spiritNumb = 1;
                standCounter = 0;
            }
        }
    }
    public void interactNPC(int npcIndex) {
        if(npcIndex != -1){
            gp.currentTalkingNpcIndex = npcIndex;
            if(gp.keyH.enterPressed){
                gp.gameState = GameStateType.Dialog;
                gp.npc[npcIndex].speak();
            }
            gp.keyH.enterPressed = false;
        }
    }
    public void pickUpObject(int i) {
        if (i != -1) {

        }

    }
        public void draw (Graphics2D g2d){
            BufferedImage image = null;

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
            g2d.drawImage(image, screenX, screenY, null);
             //For debug purposes
            /*g2d.setColor(Color.red);
            g2d.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
            g2d.setColor(Color.blue);
            g2d.drawRect(screenX, screenY, gp.tileSize, gp.tileSize);*/
        }
}
