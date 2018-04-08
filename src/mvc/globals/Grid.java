/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.globals;

import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samir
 * @param <T>
 */
public class Grid<T> implements Cloneable {

    private ArrayList< ArrayList<T>> data;
    private int size;


    public Grid(int size, T e) {
        this.size = size;
        data = new ArrayList< ArrayList<T>>(size);
        for (int i = 0; i < size; i++) {
            ArrayList<T> arr = new ArrayList<T>(size);
            for (int j = 0; j < size; j++) {
                try {
                    arr.add((T) e.getClass().newInstance());
                } catch (InstantiationException ex) {
                    Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            data.add(arr);
        }
    }

    public Grid(int size) {
        this.size = size;
        data = new ArrayList< ArrayList<T>>(size);

    }
    public int getSize() {
        return size;
    }

    public T get(Point pos) {
        return data.get(pos.y).get(pos.x);
    }

    public void set(Point pos, T k) {

        data.get(pos.y).set(pos.x, k);

    }

    public ArrayList<Point> neighbours(Point pos) {

        ArrayList<Point> neighbours = new ArrayList<Point>();

        Point[] possible = {new Point(pos.x - 1, pos.y),
            new Point(pos.x, pos.y + 1),
            new Point(pos.x + 1, pos.y),
            new Point(pos.x, pos.y - 1)};

        for (Point p : possible) {
            if (p.x < 0 || p.y < 0 || p.x >= size || p.y >= size) {
            } else {
                neighbours.add(p);
            }

        }
        return neighbours;

    }

    

}
