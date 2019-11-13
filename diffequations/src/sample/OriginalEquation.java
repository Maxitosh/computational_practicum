package sample;

import javafx.scene.chart.XYChart;

public class OriginalEquation extends Equation {
    @Override
    double getValue(double x, double y, double xInit, double yInit) {
        return 5 - Math.pow(x, 2) - Math.pow(y, 2) + 2 * x * y;
    }
}
