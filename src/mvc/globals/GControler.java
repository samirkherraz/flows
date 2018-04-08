
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.globals;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import mvc.application.AModel;

/**
 *
 * @author freder
 */
public abstract class GControler {

    protected mvc.application.AModel.STATE backstate;
    protected mvc.application.AModel.STATE nextstate;

    public AModel.STATE getBackstate() {
        return backstate;
    }

    public void setBackstate(AModel.STATE backstate) {
        this.backstate = backstate;
    }

    public AModel.STATE getNextstate() {
        return nextstate;
    }

    public void setNextstate(AModel.STATE backstate) {
        this.nextstate = backstate;
    }

    public void setUp() {

        getModel().addObserver(new Observer() {

            @Override
            public void update(Observable o, Object arg
            ) {
                for (Point p : getModel().getUpdate()) {
                    getView().gridRedraw(p, getModel().getGrid().get(p));
                }
                getModel().clearUpdate();

            }
        }
        );

        getView().getBtnLeft().setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                getApp().setGamestate(backstate);
            }
        });

        getView().getBtnRight().setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                getApp().setGamestate(nextstate);
            }
        });

        for (int row = 0;
                row < getView().getGridSize();
                row++) {
            for (int col = 0; col < getView().getGridSize(); col++) {

                final Canvas t = getView().getGrid()[row][col];
                Point p = new Point(col, row);
                
                getView().gridRedraw(p, getModel().getGrid().get(p));

                this.addEvent(t, new Point(col, row));

            }
        }
    }

    abstract public void addEvent(Canvas t, Point p);

    abstract public GView getView();

    abstract public GModel getModel();

    abstract public mvc.application.AModel getApp();

    abstract public void setView(GView v);

    abstract public void setModel(GModel model);

    abstract public void setApp(mvc.application.AModel app);

}
