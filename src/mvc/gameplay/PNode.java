/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.gameplay;

import java.awt.Point;
import javafx.scene.paint.Color;
import mvc.globals.GNode;

/**
 *
 * @author samir
 */
public class PNode extends GNode {

    private boolean passed = false;
    private boolean target = false;
    private Point origin;
    private Point next;
    private int index;
    private Color color = Color.WHITE;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Point getNext() {
        return next;
    }

    public void setNext(Point next) {
        this.next = next;
    }

    public Point getOrigin() {
        return origin;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {

        this.color = color;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public boolean isTarget() {
        return target;
    }

    public void setTarget(boolean target) {
        this.target = target;
    }

    public PNode clone() {

        PNode c = new PNode();
        c.color = color;
        c.index = index;
        c.next = next;
        c.origin = origin;
        c.passed = passed;
        c.target = target;

        return c;
    }

}
