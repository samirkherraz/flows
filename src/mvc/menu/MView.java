
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.menu;

import java.awt.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author freder
 */
public class MView extends mvc.globals.GView {

    @Override
    public void gridRedraw(Point pos, Object obj) {
        MNode model = (MNode) obj;
        GraphicsContext gc = grid[pos.y][pos.x].getGraphicsContext2D();
        if (model.isSelected()) {

            gc.setFill(Color.CORNFLOWERBLUE);
        } else {
            gc.setFill(Color.BLACK);

        }
        gc.fillRect(5, 5, 40, 40);
        gc.setFill(Color.WHITE);
        gc.fillText("" + model.getSize(), 20, 30);
    }

}
