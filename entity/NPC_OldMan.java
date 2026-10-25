package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_OldMan extends Entity{
    GamePanel gp;
    Random random = new Random();
    int k = 120;
    public  NPC_OldMan(GamePanel gp){
        super(gp);
        this.gp = gp;
        direction = Direction.RIGHT;
        speed = 2;
        baseSpeed = 2;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 48;
        solidArea.height = 48;
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
        getImage();

        setDialog();
    }
    public void speak(){
        super.speak();
    }

    public void getImage(){

        up1 = setUp("/npc/oldman_up_1");
        up2 = setUp("/npc/oldman_up_2");
        down1 = setUp("/npc/oldman_down_1");
        down2 = setUp("/npc/oldman_down_2");
        right1 = setUp("/npc/oldman_right_1");
        right2 = setUp("/npc/oldman_right_2");
        left1 = setUp("/npc/oldman_left_1");
        left2 = setUp("/npc/oldman_left_2");

    }
    public void setDialog(){
        dialogues[0] = "Hello, lad.";
        dialogues[1] = "I assume you want to \nfind the mystic treasure.";
        dialogues[2] = "Many died, along with \nthe memories.";
        dialogues[3] = "Be careful.";
        dialogTotalLine = 4;
    }


    public void setAction(){
        actionLogCounter++;


        if((actionLogCounter >= k) || (collisionX && direction == Direction.LEFT)
                || (collisionX && direction == Direction.RIGHT) || (collisionY && direction == Direction.UP)
        || (collisionY && direction == Direction.DOWN) || (collisionY && collisionX)){
            int i = random.nextInt(80) + 1;

            if (i > 70) {
                direction = Direction.UP;
            } else if (i > 60) {
                direction = Direction.DOWN;
            } else if (i > 50) {
                direction = Direction.LEFT;
            } else if (i > 40) {
                direction = Direction.RIGHT;
            }else if (i > 30) {
                direction = Direction.DOWNLEFT;
            }else if (i > 20) {
                direction = Direction.UPRIGHT;
            }else if (i > 10) {
                direction = Direction.DOWNRIGHT;
            }else  {
                direction = Direction.UPLEFT;
            }
            actionLogCounter = 0;
            k = random.nextInt(100) + 90;
        }
    }


    /*public void setAction() {
        int tol = gp.tileSize / 2;

        if (gp.player.worldX > worldX + tol) {
            if (gp.player.worldY > worldY + tol) {
                direction = Direction.DOWNRIGHT;
            } else if (gp.player.worldY < worldY - tol) {
                direction = Direction.UPRIGHT;
            } else {
                direction = Direction.RIGHT;
            }

        } else if (gp.player.worldX < worldX - tol) {
            if (gp.player.worldY > worldY + tol) {
                direction = Direction.DOWNLEFT;
            } else if (gp.player.worldY < worldY - tol) {
                direction = Direction.UPLEFT;
            } else {
                direction = Direction.LEFT;
            }

        } else {
            if (gp.player.worldY > worldY + tol) {
                direction = Direction.DOWN;
            } else if (gp.player.worldY < worldY - tol) {
                direction = Direction.UP;
            }
        }
    }*/

}
