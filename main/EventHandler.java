package main;

import entity.Direction;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    Rectangle eventRect;
    int eventRectDefaultX, eventRectDefaultY;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new Rectangle();
        eventRect.x = gp.tileSize / 2;
        eventRect.y = gp.tileSize / 2;
        eventRect.width = 2;
        eventRect.height = 2;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;

    }
    public void checkEvent() {

    }
    public boolean hit(int eventCol, int eventRow, Direction reqDirection) {
        boolean hit = false;
        gp.player.solidArea.x = (int)gp.player.worldX + gp.player.solidArea.x;


        return hit;
    }
}
