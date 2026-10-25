package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed
            , checkDrawTime
            , enterPressed
            , nextTalkPressed;
    GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(gp.gameState == GameStateType.Title){
            if(gp.ui.titleStateSubType == TitleStateSubType.FirstScreen){
                if(code == KeyEvent.VK_W) {
                    if(gp.ui.commandType == CommandType.NewGame || gp.ui.commandType == CommandType.LoadGame){
                        gp.ui.commandType = CommandType.NewGame;
                    }else if(gp.ui.commandType == CommandType.Quit){
                        gp.ui.commandType = CommandType.LoadGame;
                    }
                }
                if(code == KeyEvent.VK_S) {
                    if(gp.ui.commandType == CommandType.NewGame){
                        gp.ui.commandType = CommandType.LoadGame;
                    }else if(gp.ui.commandType == CommandType.Quit || gp.ui.commandType == CommandType.LoadGame){
                        gp.ui.commandType = CommandType.Quit;
                    }
                }
                if(code == KeyEvent.VK_ENTER) {
                    if(gp.ui.commandType == CommandType.NewGame){
                        gp.ui.titleStateSubType = TitleStateSubType.SecondScreen;
                    }else if(gp.ui.commandType == CommandType.LoadGame){
                        // Add Later

                    }else if(gp.ui.commandType == CommandType.Quit){
                        gp.ui.commandType = CommandType.Quit;
                        System.exit(0);
                    }
                }
            }else if(gp.ui.titleStateSubType == TitleStateSubType.SecondScreen){
                if(code == KeyEvent.VK_W) {
                    if(gp.ui.secondScreenType == SecondScreenType.THIEF || gp.ui.secondScreenType == SecondScreenType.FIGHTER){
                        gp.ui.secondScreenType = SecondScreenType.FIGHTER;
                    }else if(gp.ui.secondScreenType == SecondScreenType.SORCERER){
                        gp.ui.secondScreenType = SecondScreenType.THIEF;
                    }else if(gp.ui.secondScreenType == SecondScreenType.BACK){
                        gp.ui.secondScreenType = SecondScreenType.SORCERER;
                    }
                }
                if(code == KeyEvent.VK_S) {
                    if( gp.ui.secondScreenType == SecondScreenType.FIGHTER){
                        gp.ui.secondScreenType = SecondScreenType.THIEF;
                    }else if(gp.ui.secondScreenType == SecondScreenType.THIEF){
                        gp.ui.secondScreenType = SecondScreenType.SORCERER;
                    }else if(gp.ui.secondScreenType == SecondScreenType.BACK || gp.ui.secondScreenType == SecondScreenType.SORCERER){
                        gp.ui.secondScreenType = SecondScreenType.BACK;
                    }
                }
                if(code == KeyEvent.VK_ENTER) {
                    if( gp.ui.secondScreenType == SecondScreenType.FIGHTER){
                        gp.gameState = GameStateType.Play;
                        gp.playMusic(0);
                    }else if(gp.ui.secondScreenType == SecondScreenType.THIEF){

                    }else if( gp.ui.secondScreenType == SecondScreenType.SORCERER){

                    }else if(gp.ui.secondScreenType == SecondScreenType.BACK){
                        gp.ui.titleStateSubType = TitleStateSubType.FirstScreen;
                        gp.ui.secondScreenType = SecondScreenType.FIGHTER;

                    }
                }

            }

        }

        if(gp.gameState == GameStateType.Play){
            if(code == KeyEvent.VK_W) {
                upPressed = true;

            }
            if(code == KeyEvent.VK_S) {
                downPressed = true;

            }
            if(code == KeyEvent.VK_A) {
                leftPressed = true;

            }
            if(code == KeyEvent.VK_D) {
                rightPressed = true;

            }
            if(code == KeyEvent.VK_P) {
                gp.gameState = GameStateType.Pause;
            }
            if(code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }

        }else if(gp.gameState == GameStateType.Pause){
            if(code == KeyEvent.VK_P) {
                gp.gameState = GameStateType.Play;
            }

        }else if(gp.gameState == GameStateType.Dialog){
            if(code == KeyEvent.VK_ENTER) {
                gp.gameState = GameStateType.Play;
                nextTalkPressed = false;
            }
            if(code == KeyEvent.VK_SPACE) {
                nextTalkPressed = true;
            }
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W) {
            upPressed = false;

        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;

        }
        if(code == KeyEvent.VK_A) {
            leftPressed = false;

        }
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }

        if(code == KeyEvent.VK_T) {
            if(checkDrawTime == false) {
                checkDrawTime = true;
            }else {
                checkDrawTime = false;
            }

        }

    }
}
