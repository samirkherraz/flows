
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.globals;

import java.awt.Point;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 *
 * @author freder
 */
public abstract class GView {

    private int WIDTH = 700;
    private int HEIGHT = 650;
    protected Scene scene;
    protected BorderPane border;

    protected Canvas[][] grid;
    protected String btn_left_title;
    protected String btn_right_title;

    protected boolean btnLeftVisible = true;
    protected boolean btnRightVisible = true;

    public boolean isBtnLeftVisible() {
        return btnLeftVisible;
    }

    public void setBtnLeftVisible(boolean btnLeftVisible) {
        this.btnLeftVisible = btnLeftVisible;
    }

    public boolean isBtnRightVisible() {
        return btnRightVisible;
    }

    public void setBtnRightVisible(boolean btnRightVisible) {
        this.btnRightVisible = btnRightVisible;
    }

    protected Canvas btnLeft;
    protected Canvas btnRight;
    protected int gridSize;

    //protected String btnTopLeft;
    //protected String btnTopRight;
    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Canvas getBtnLeft() {
        return btnLeft;
    }

    public Canvas getBtnRight() {
        return btnRight;
    }

    public Canvas[][] getGrid() {
        return grid;
    }

    public void setGrid(Canvas[][] grid) {
        this.grid = grid;
    }

    public void setBtnLeftTitle(String title) {
        this.btn_left_title = title;
    }

    public void setBtnRightTitle(String title) {
        this.btn_right_title = title;
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public void build() {
        GridPane gPane = new GridPane();
        gPane.setAlignment(Pos.CENTER);
        border = new BorderPane();
        BorderPane hbox = new BorderPane();

        Reflection r = new Reflection();
        r.setFraction(0.5f);
        btnLeft = new Canvas(140, 70);

        if (isBtnLeftVisible()) {
            btnLeft.setEffect(r);
            GraphicsContext gcleft = btnLeft.getGraphicsContext2D();
            gcleft.setFill(Color.WHITE);
            gcleft.fillArc(0, -75, 140, 140, 180, 180, ArcType.OPEN);
            gcleft.setFill(Color.BLACK);
            gcleft.setTextAlign(TextAlignment.CENTER);

            gcleft.fillText(btn_left_title.toUpperCase(), 70, 35);
            hbox.setLeft(btnLeft);
        }
        btnRight = new Canvas(140, 70);

        if (isBtnRightVisible()) {

            btnRight.setEffect(r);
            GraphicsContext gcright = btnRight.getGraphicsContext2D();

            gcright.setFill(Color.WHITE);
            gcright.fillArc(0, -75, 140, 140, 180, 180, ArcType.OPEN);
            gcright.setFill(Color.BLACK);
            gcright.setTextAlign(TextAlignment.CENTER);
            gcright.fillText(btn_right_title.toUpperCase(), 70, 35);

            hbox.setRight(btnRight);

        }

        grid = new Canvas[gridSize][gridSize];

        border.setTop(hbox);
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {

                final int fCol = col;
                final int fRow = row;

                final Canvas t = new Canvas(50, 50);
                grid[fRow][fCol] = t;
                gPane.add(grid[fRow][fCol], fCol, fRow);

            }
        }
        border.setCenter(gPane);
        gPane.setOpacity(
                0.9);

        String file = getClass().getClassLoader().getResource("resources/index.jpg").toString();

        border.setStyle(
                "-fx-background-image: url('" + file + "')");
        scene = new Scene(border, WIDTH, HEIGHT, Color.WHITE);

    }

    public void fadeout() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {

                FadeTransition ft = new FadeTransition(Duration.millis(1000), grid[row][col]);
                ft.setFromValue(1.0);
                ft.setToValue(0);
                ft.play();

            }
        }

    }

    public void fadein() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {

                FadeTransition ft = new FadeTransition(Duration.millis(1000), grid[row][col]);
                ft.setFromValue(0);
                ft.setToValue(1);
                ft.play();

            }
        }

    }

    abstract public void gridRedraw(Point pos, Object model);

}
