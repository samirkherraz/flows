/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.globals;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author samir
 */
public abstract class GModel extends Observable {

    protected int gridSize;


    protected ArrayList<Point> update = new ArrayList<Point>();
    public GModel(int size) {

        gridSize = size;

    }
    abstract public Grid getGrid();

    public ArrayList<Point> getUpdate() {
        return update;
    }

    public void addUpdate(Point p) {
        update.add(p);
    }

    public void clearUpdate() {
        update.clear();
    }

 
}
