/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.application;

import java.util.Observable;
import java.util.Observer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.DoubleProperty;
import javafx.scene.effect.ColorAdjust;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author samir
 */
public class App extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }
    private Stage stage;
    private AModel app;
    private ColorAdjust grayscale = new ColorAdjust();
    private DoubleProperty saturation = grayscale.brightnessProperty();

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        Application m = this;

        int p = 3;
        stage.setTitle("Drag & Drop");
        stage.show();

        app = new AModel();
        app.addObserver(new Observer() {

            @Override
            public void update(Observable o, Object arg) {
                
                fadeout();

            }
        });

        app.setGamestate(mvc.application.AModel.STATE.MENU);

    }

    public void changeSceen() {
        switch (app.getGamestate()) {
            case MENU: {
                mvc.menu.MModel model = new mvc.menu.MModel(3);
                mvc.menu.MView view = new mvc.menu.MView();
                view.setGridSize(3);
                view.setBtnLeftTitle("Quit");
                view.setBtnRightVisible(false);
                view.build();
                mvc.menu.MControler controler = new mvc.menu.MControler();
                controler.setApp(app);
                controler.setModel(model);
                controler.setView(view);
                controler.setBackstate(AModel.STATE.QUIT);
                controler.setNextstate(AModel.STATE.NONE);
                controler.setUp();

                stage.setScene(view.getScene());
                stage.sizeToScene();

            }
            break;
            case GAMEPLAY: {

                mvc.gameplay.PModel model = new mvc.gameplay.PModel(app.getGameGridSize());
                mvc.gameplay.PView view = new mvc.gameplay.PView();
                view.setBtnLeftTitle("Back to Menu");
                view.setBtnRightTitle("Next Grid");
                view.setGridSize(app.getGameGridSize());
                view.build();

                mvc.gameplay.PControler controler = new mvc.gameplay.PControler();
                controler.setApp(app);
                controler.setModel(model);
                controler.setView(view);
                controler.setBackstate(AModel.STATE.MENU);
                controler.setNextstate(AModel.STATE.NONE);
                controler.setUp();

                stage.setScene(view.getScene());
                stage.sizeToScene();

            }
            break;
            case QUIT: {
                stage.close();
            }
            
            default:
                break;

        }
    }

    public void fadeout() {
        if (stage.getScene() != null) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(saturation, 0)),
                    new KeyFrame(Duration.seconds(0.5), new KeyValue(saturation, -1))
            );
            timeline.setOnFinished(evt -> {
                changeSceen();
                fadein();
            });

            stage.getScene().getRoot().setEffect(grayscale);
            timeline.play();
        } else {
            changeSceen();

        }

    }

    public void fadein() {
        if (stage.getScene() != null) {

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(saturation, -1)),
                    new KeyFrame(Duration.seconds(0.5), new KeyValue(saturation, 0))
            );

            stage.getScene().getRoot().setEffect(grayscale);
            timeline.play();
        }

    }

}
