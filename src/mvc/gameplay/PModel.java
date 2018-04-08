/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.gameplay;

import java.awt.Point;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import mvc.globals.Grid;

/**
 *
 * @author fred
 */
public final class PModel extends mvc.globals.GModel {

    public Point start = new Point(0, 0);
    private ArrayList<Point> path = new ArrayList<Point>();
    private Grid<PNode> grid;

    private boolean dragDrop;
    private ArrayList<Color> userColors = new ArrayList<Color>();
    private ArrayList<Color> colors = new ArrayList<Color>();

    public PModel(int size) {
        super(size);
        this.genColors();
        grid = new Grid<PNode>(gridSize, new PNode());
        this.generate();

    }

    @Override
    public Grid getGrid() {

        return grid;

    }

    public Point getPathInversed(int i) {
        int index = (path.size() - 1 - i) % path.size();
        return path.get(index);

    }

    public PNode getPathInversedNode(int i) {
        return grid.get(getPathInversed(i));
    }

    public void commit() {
        path.clear();
    }

    public void stash() {
        for (Point p : path) {
            grid.get(p).setPassed(false);
            grid.get(p).setNext(null);
            grid.get(p).setOrigin(null);
            addUpdate(p);
        }
        path.clear();
    }

    public void addPath(Point pos) {
        path.add(pos);
        getPathInversedNode(1).setNext(pos);

        getPathInversedNode(0).setOrigin(getPathInversed(1));
        getPathInversedNode(0).setPassed(true);
        getPathInversedNode(0).setColor(getPathInversedNode(1).getColor());
        addUpdate(getPathInversed(0));
        addUpdate(getPathInversed(1));

    }

    public void removeLastPath() {
        getPathInversedNode(0).setOrigin(null);
        getPathInversedNode(0).setPassed(false);
        getPathInversedNode(0).setColor(null);
        getPathInversedNode(1).setNext(null);
        addUpdate(getPathInversed(0));
        addUpdate(getPathInversed(1));

        path.remove(getPathInversed(0));

    }

    public boolean isDragDrop() {
        return dragDrop;
    }

    public void setDragDrop(boolean dragDrop) {
        this.dragDrop = dragDrop;
    }

    public boolean isTargetReached(Point pos) {
        return (((pos.x != start.x) || (pos.y != start.y))
                && (grid.get(pos).isTarget())
                && (grid.get(pos).getColor() == grid.get(start).getColor()));

    }

    public boolean isValidStep(Point pos) {

        return (!isFinished() && dragDrop
                && ((!grid.get(pos).isPassed()) || (isBackStep(pos)))
                && (grid.neighbours(getPathInversed(0)).contains(pos)));

    }

    public void clearPath(Point pos) {

        Point next = grid.get(pos).getNext();
        Point origin = grid.get(pos).getOrigin();

        while (origin != null) {
            grid.get(origin).setPassed(false);
            Point ln = grid.get(origin).getOrigin();
            grid.get(origin).setNext(null);
            grid.get(origin).setOrigin(null);
            this.addUpdate(origin);
            origin = ln;

        }
        while (next != null) {
            grid.get(next).setPassed(false);
            Point ln = grid.get(next).getNext();
            grid.get(next).setNext(null);
            grid.get(next).setOrigin(null);
            this.addUpdate(next);
            next = ln;

        }
        this.addUpdate(pos);
        grid.get(pos).setPassed(false);

    }

    public void start(Point pos) {
        if (!isFinished()) {
            clearPath(pos);

            if (grid.get(pos).isTarget()) {
                path.add(pos);

                grid.get(pos).setPassed(true);

                start = pos;
                dragDrop = true;

                setChanged();
                notifyObservers();
            }
        }
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public void stop(Point pos) {
        dragDrop = false;
        if (!isFinished()) {

            stash();

            setChanged();
            notifyObservers();
        }
    }

    public boolean isBackStep(Point pos) {

        return ((getPathInversed(1).x == pos.x) && (getPathInversed(1).y == pos.y));

    }

    public void step(Point pos) {

        if (isValidStep(pos)) {

            if (isTargetReached(pos)) {
                addPath(pos);
                commit();
                stop(pos);

            } else if (isBackStep(pos)) {
                removeLastPath();
            } else if (!(grid.get(pos).isTarget())) {
                addPath(pos);
            }
            setChanged();
            notifyObservers();

        }

        // TODO
    }

    public void generate() {
        Random rand = new Random();

        PGenerator g = new PGenerator();
        int[][] board = g.GenerateBoard(gridSize).clone();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid.set(new Point(j, i), new PNode());

                if (board[i][j] != 0) {
                    int darker = 0;
                    int index = 0;
                    grid.get(new Point(j, i)).setTarget(true);
                    index = board[i][j] % colors.size();

                    darker = board[i][j] / colors.size();
                  
                    Color c = colors.get(index);
                    for (int k = 0; k < darker; k++) {
                        c.darker();
                    }
                    grid.get(new Point(j, i)).setColor(c);
                }

            }
        }

    }

    public boolean isFinished() {
        boolean finished = true;

        for (int i = 0; i < gridSize; i++) {

            for (int j = 0; j < gridSize; j++) {

                if (!grid.get(new Point(j, i)).isPassed()) {
                    finished = false;
                }

            }

        }

        return finished;

    }

    public void genColors() {
        try {
            Class clazz = Class.forName("javafx.scene.paint.Color");
            if (clazz != null) {
                Field[] field = clazz.getFields();
                for (int i = 0; i < field.length; i++) {
                    Field f = field[i];
                    if (f.getName().startsWith("DARK")) {
                        Object obj = f.get(null);
                        if (obj instanceof Color) {
                            colors.add((Color) obj);
                        }
                    }

                }

            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void newGrid() {
        grid = new Grid<PNode>(grid.getSize(), new PNode());
        this.generate();

        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                addUpdate(new Point(j, i));
            }

        }
        setChanged();
        notifyObservers();
    }

}
