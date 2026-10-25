package main;

import object.OBJ_Heart;
import object.OBJ_Key;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Graphics2D g2d;
    Font purisaB;

    BufferedImage heart_full, heart_half, heart_blank;

    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialog = "";
    public CommandType commandType = CommandType.NewGame;

    public TitleStateSubType titleStateSubType = TitleStateSubType.FirstScreen;
    public SecondScreenType secondScreenType = SecondScreenType.FIGHTER;



    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        }
        SuperObject heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;

    }
    public void showMessage(String text) {
        message = text;
        messageOn = true;

    }

    public void draw(Graphics2D g2d) {


        this.g2d = g2d;

        g2d.setFont(purisaB);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setColor(Color.white);

        if(gp.gameState == GameStateType.Play){
            // Later
            drawPlayerLife();
        }
        else if(gp.gameState == GameStateType.Pause){
            drawPlayerLife();
            pauseScreen();
        }
        else if(gp.gameState == GameStateType.Dialog){
            drawPlayerLife();
            drawDialogScreen();
        }else if (gp.gameState == GameStateType.Title){
            drawTitleScreen();
        }

    }
    public void drawPlayerLife(){
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        // Draw blank heart
        while(i < gp.player.maxLife / 2){
            g2d.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;

        // Draw current heart
        while(i < gp.player.life / 2){
            g2d.drawImage(heart_full, x, y, null);
            i++;
            x += gp.tileSize;
        }
        if(gp.player.life % 2 == 1){
            g2d.drawImage(heart_half, x, y, null);
        }

    }
    public void drawTitleScreen() {
        if(titleStateSubType == TitleStateSubType.FirstScreen){
            g2d.setColor(new Color(120, 100, 80));
            g2d.fillRect(0, 0, gp.getWidth(), gp.getHeight());
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 90F));
            String text = "Sigma Fighter";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;

            //Shadow
            g2d.setColor(Color.BLACK);
            g2d.drawString(text, x + 5, y + 5);

            //Main Color
            g2d.setColor(Color.WHITE);
            g2d.drawString(text, x, y);

            //Player image
            x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
            y += gp.tileSize * 2;
            g2d.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

            //Menu
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 48F));

            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += (int)(gp.tileSize * 3.5);
            g2d.drawString(text, x, y);
            if(commandType == CommandType.NewGame){
                g2d.drawString(">", x - gp.tileSize, y);
            }

            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2d.drawString(text, x, y);
            if(commandType == CommandType.LoadGame){
                g2d.drawString(">", x - gp.tileSize, y);
            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2d.drawString(text, x, y);
            if(commandType == CommandType.Quit){
                g2d.drawString(">", x - gp.tileSize, y);
            }
        } else if (titleStateSubType == TitleStateSubType.SecondScreen) {
            g2d.setColor(Color.WHITE);
            g2d.setFont(g2d.getFont().deriveFont(42F));

            String text = "Select your class!";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;
            g2d.drawString(text, x, y);

            text = "Fighter";
            x = getXforCenteredText(text);
            y += gp.tileSize * 2;
            g2d.drawString(text, x, y);
            if(secondScreenType == SecondScreenType.FIGHTER){
                g2d.drawString(">", x - gp.tileSize, y);
            }

            text = "Thief";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2d.drawString(text, x, y);
            if(secondScreenType == SecondScreenType.THIEF){
                g2d.drawString(">", x - gp.tileSize, y);
            }

            text = "Sorcerer";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2d.drawString(text, x, y);
            if(secondScreenType == SecondScreenType.SORCERER){
                g2d.drawString(">", x - gp.tileSize, y);
            }

            text = "Back";
            x = getXforCenteredText(text);
            y += gp.tileSize * 2;
            g2d.drawString(text, x, y);
            if(secondScreenType == SecondScreenType.BACK){
                g2d.drawString(">", x - gp.tileSize, y);
            }

        }
    }

    public void drawDialogScreen() {
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = (gp.tileSize * 4);
        drawSubWindow(x, y, width, height);

        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 32));
        x += gp.tileSize;
        y += gp.tileSize;
        for(String line : currentDialog.split("\n")){
            g2d.drawString(line, x, y);
            y += gp.tileSize;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 210);
        g2d.setColor(c);
        g2d.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2d.setColor(c);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }

    public void pauseScreen() {
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";

        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;

        g2d.drawString(text, x, y);
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
}
