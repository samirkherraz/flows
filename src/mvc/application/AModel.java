/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.application;

import java.util.Observable;

/**
 *
 * @author fred
 */
public class AModel extends Observable {


    private STATE gamestate = STATE.MENU;
    private int size;

    public void setGameGridSize(int size) {
        this.size = size;
    }

    public int getGameGridSize() {
        return this.size;
    }
    

    public STATE getGamestate() {
        return gamestate;
    }

    public void setGamestate(STATE gamestate) {
        this.gamestate = gamestate;
        setChanged();
        notifyObservers();
    }
    public enum STATE {
        MENU,
        GAMEPLAY,
        QUIT,
        NONE
    }

}
