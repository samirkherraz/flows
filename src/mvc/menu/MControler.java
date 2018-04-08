
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.menu;

import java.awt.Point;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import mvc.globals.GControler;
import mvc.globals.GModel;
import mvc.globals.GView;

/**
 *
 * @author freder
 */
public class MControler extends GControler {

    protected MView view;
    protected MModel model;
    protected mvc.application.AModel app;

    public void addEvent(Canvas t, Point p) {

        t.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {

                event.consume();

                model.select(p);

            }
        });

        t.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {

                event.consume();
                model.unselect(p);

            }
        });

        t.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {

                event.consume();
                app.setGameGridSize(model.getGameGridSize());
                app.setGamestate(mvc.application.AModel.STATE.GAMEPLAY);

            }
        });

    }

    @Override
    public GView getView() {
        return (GView) this.view;
    }

    @Override
    public GModel getModel() {
        return (GModel) this.model;
    }

    @Override
    public mvc.application.AModel getApp() {
        return (mvc.application.AModel) this.app;
    }

    @Override
    public void setView(GView v) {
        this.view = (MView) v;
    }

    @Override
    public void setModel(GModel model) {
        this.model = (MModel) model;
    }

    @Override
    public void setApp(mvc.application.AModel app) {
        this.app = app;
    }

}
