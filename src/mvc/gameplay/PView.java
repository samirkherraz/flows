
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.gameplay;

import java.awt.Point;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.beans.property.DoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *
 * @author freder
 */
public class PView extends mvc.globals.GView {

    private Point current = null;

    public void setCurrent(Point p) {
        current = p;
    }

    public void gridRedraw(Point pcase, Object obj) {
        PNode c = (PNode) obj;
        Point origin = c.getOrigin();
        Point next = c.getNext();

        GraphicsContext gc = grid[pcase.y][pcase.x].getGraphicsContext2D();

        if (c.isPassed()) {
            Color color = c.getColor().darker().darker().darker();
            gc.setFill(color);

        } else {
            gc.setFill(Color.BLACK);
        }
        gc.fillRect(0, 0, 50, 50);

        if (c.isTarget()) {
            gc.setFill(c.getColor());
            gc.fillOval(5, 5, 40, 40);
        } else if (c.isPassed()) {
            gc.setFill(c.getColor());

            if (c.getNext() == null) {

                if (origin.x < pcase.x) {
                    gc.fillRect(0, 20, 25, 10);
                } else if (origin.x > pcase.x) {
                    gc.fillRect(25, 20, 25, 10);
                }

                if (origin.y < pcase.y) {
                    gc.fillRect(20, 0, 10, 30);
                } else if (origin.y > pcase.y) {
                    gc.fillRect(20, 20, 10, 50);
                }

                gc.fillOval(15, 15, 20, 20);

            } else {

                if (origin.x > next.x && origin.x == pcase.x) {
                    gc.fillRect(0, 20, 25, 10);
                } else if (origin.x < next.x && origin.x == pcase.x) {
                    gc.fillRect(25, 20, 25, 10);
                } else if (origin.x < next.x && origin.x != pcase.x) {
                    gc.fillRect(0, 20, 25, 10);
                } else if (origin.x > next.x && origin.x != pcase.x) {
                    gc.fillRect(25, 20, 25, 10);
                } else {
                    gc.fillRect(20, 0, 10, 50);
                }

                if (origin.y > next.y && origin.y == pcase.y) {
                    gc.fillRect(20, 0, 10, 25);
                } else if (origin.y < next.y && origin.y == pcase.y) {
                    gc.fillRect(20, 25, 10, 50);
                } else if (origin.y < next.y && origin.y != pcase.y) {
                    gc.fillRect(20, 0, 10, 25);
                } else if (origin.y > next.y && origin.y != pcase.y) {
                    gc.fillRect(20, 25, 10, 50);
                } else {
                    gc.fillRect(0, 20, 50, 10);
                }
                gc.fillOval(20, 20, 10, 10);

            }

        }

    }

    public void gridGrayScale() {

        ColorAdjust grayscale = new ColorAdjust();
        DoubleProperty saturation = grayscale.saturationProperty();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(saturation, saturation.get())),
                new KeyFrame(Duration.seconds(1), new KeyValue(saturation, -1))
        );
        timeline.setOnFinished(evt -> timeline.setRate(-timeline.getRate()));

        border.setEffect(grayscale);
        timeline.play();
    }
    
    
     public void gridColorfull() {

        ColorAdjust grayscale = new ColorAdjust();
        DoubleProperty saturation = grayscale.saturationProperty();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(saturation, saturation.get())),
                new KeyFrame(Duration.seconds(1), new KeyValue(saturation, 0))
        );
        timeline.setOnFinished(evt -> timeline.setRate(-timeline.getRate()));

        border.setEffect(grayscale);
        timeline.play();
    }

}
