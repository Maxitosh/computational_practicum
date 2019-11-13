package sample;

import javafx.scene.chart.XYChart;

public class ExactEquation extends Equation {

    @Override
    double getValue(double x, double y, double xInit, double yInit) {
        return 1 / (getConstValue(xInit, yInit) * Math.exp(4 * x) - (double)1 / 4) + x + 2;
    }

    //ivp values x and y
    private double getConstValue(double xInit, double yInit) {
        return (yInit - xInit + 2) / (4 * Math.exp(4 * xInit) * (yInit - xInit - 2));
    }

    //get series of exact value
    public XYChart.Series getExactSeries(double x0, double y0, double X, int N) {
        XYChart.Series series = new XYChart.Series();
        double stepSize = (X - x0) / N;
        int steps = N;

        double currentX = x0;

        for (int i = 0; i < steps + 1; i++) {
            currentX = i * stepSize;
            double currentY = getValue(currentX, 0, x0, y0);
            series.getData().add(new XYChart.Data(currentX, currentY));
        }

        return series;
    }
}
