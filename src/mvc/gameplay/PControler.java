
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.gameplay;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import mvc.globals.GModel;
import mvc.globals.GView;

/**
 *
 * @author freder
 */
public class PControler extends mvc.globals.GControler {

    protected PView view;
    protected PModel model;
    protected mvc.application.AModel app;

    public void addEvent(Canvas t, Point p) {

        getView().getBtnRight().setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                getView().fadeout();
                model.newGrid();
                getView().fadein();

            }
        });

        model.addObserver(new Observer() {

            @Override
            public void update(Observable o, Object arg
            ) {
                if (model.isFinished()) {
                    view.gridGrayScale();
                } else {
                    view.gridColorfull();

                }

            }
        }
        );

        t.setOnDragDetected(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {

                Dragboard db = t.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString(""); // non utilisé actuellement
                db.setContent(content);
                event.consume();
                model.start(p);

            }
        }
        );

        t.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                view.setCurrent(p);
                model.step(p);
                event.consume();
            }
        });

        t.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                // attention, le setOnDragDone est déclenché par la source du Drag&Drop
                model.stop(p);
                event.consume();

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
        this.view = (PView) v;
    }

    @Override
    public void setModel(GModel model) {
        this.model = (PModel) model;
    }

    @Override
    public void setApp(mvc.application.AModel app) {
        this.app = app;
    }

}
