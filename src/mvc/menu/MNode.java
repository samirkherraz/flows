/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.menu;

import mvc.globals.GNode;

/**
 *
 * @author samir
 */
public class MNode extends GNode{

    private boolean selected = false;
    private int size = 0;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public MNode clone() {

        MNode n = new MNode();
        n.selected = selected;
        n.size = size;
        return n;

    }

}
