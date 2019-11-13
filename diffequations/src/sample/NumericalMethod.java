package sample;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public abstract class NumericalMethod {
    OriginalEquation originalEquation = new OriginalEquation();
    ExactEquation exactEquation = new ExactEquation();

    abstract ArrayList<XYChart.Series> calculate(double x0, double y0, double X, int N, int evol);
}
