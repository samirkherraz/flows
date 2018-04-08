/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.menu;

import java.awt.Point;
import mvc.globals.Grid;

/**
 *
 * @author fred
 */
public class MModel extends mvc.globals.GModel {
    
    private Point DEFAULT = new Point(0, 0);
    private Point selected;
    private Grid<MNode> grid;
    
    public MModel(int size) {
        super(size);
        grid = new Grid<MNode>(size, new MNode());
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid.get(new Point(j, i)).setSize(3 +i*size+j);
            }
        }
        
    }
    
    void select(Point p) {
        
        selected = p;
        grid.get(p).setSelected(true);
        addUpdate(p);
        setChanged();
        notifyObservers();
    }
    
    void unselect(Point p) {
        
        selected = DEFAULT;
        grid.get(p).setSelected(false);
        
        addUpdate(p);
        setChanged();
        notifyObservers();
    }
    
    public int getGameGridSize() {
        
        return ((MNode) getGrid().get(selected)).getSize();
        
    }
    
    @Override
    public Grid getGrid() {
        return grid;
    }
    
}
