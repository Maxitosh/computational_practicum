package sample;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class RungeKuttaMethod extends NumericalMethod {


    @Override
    ArrayList<XYChart.Series> calculate(double x0, double y0, double X, int N, int evol) {
        //return data
        ArrayList<XYChart.Series> data = new ArrayList<>();

        XYChart.Series seriesXYPoints = new XYChart.Series();
        XYChart.Series seriesXYLTE = new XYChart.Series();
        XYChart.Series seriesXYGTE = new XYChart.Series();


        int steps = N;
        // calculate step size
        double stepSize = (X - x0) / N;
        double tPrev = x0, yPrev = y0;
        seriesXYPoints.getData().add(new XYChart.Data(x0, y0));
        seriesXYLTE.getData().add(new XYChart.Data(x0, 0));

        for (int i = 0; i < steps; i++) {
            double k1 = originalEquation.getValue(tPrev, yPrev, x0, y0);
            double k2 = originalEquation.getValue(tPrev + stepSize / 2, yPrev + stepSize * k1 / 2, x0, y0);
            double k3 = originalEquation.getValue(tPrev + stepSize / 2, yPrev + stepSize * k2 / 2, x0, y0);
            double k4 = originalEquation.getValue(tPrev + stepSize, yPrev + stepSize * k3, x0, y0);
            double yNext = yPrev + stepSize * (k1 + 2 * k2 + 2 * k3 + k4) / 6;
            double tNext = tPrev + stepSize;
            seriesXYPoints.getData().add(new XYChart.Data(tNext, yNext));

            //calculate errors
            double locError = Math.abs(yNext - exactEquation.getValue(tNext, yNext, x0, y0)); //yNext - yPrev;
            seriesXYLTE.getData().add(new XYChart.Data(tNext, locError));

            tPrev = tNext;
            yPrev = yNext;
        }

        //calculate global error
        for (int i = 0; i < evol; i++) {
            tPrev = x0;
            yPrev = y0;
            stepSize = (X - x0) / (i + 1);
            for (int j = 0; j < i + 1; j++) {
                double k1 = originalEquation.getValue(tPrev, yPrev, x0, y0);
                double k2 = originalEquation.getValue(tPrev + stepSize / 2, yPrev + stepSize * k1 / 2, x0, y0);
                double k3 = originalEquation.getValue(tPrev + stepSize / 2, yPrev + stepSize * k2 / 2, x0, y0);
                double k4 = originalEquation.getValue(tPrev + stepSize, yPrev + stepSize * k3, x0, y0);
                double yNext = yPrev + stepSize * (k1 + 2 * k2 + 2 * k3 + k4) / 6;
                double tNext = tPrev + stepSize;

                tPrev = tNext;
                yPrev = yNext;
            }
            //System.out.println("Step: " + i + " Error: " + Math.abs(yPrev - exactEquation.getValue(tPrev, yPrev, x0, y0)));
            if (i > 30)
                seriesXYGTE.getData().add(new XYChart.Data(i, Math.abs(yPrev - exactEquation.getValue(tPrev, yPrev, x0, y0))));
        }

        data.add(seriesXYPoints);
        data.add(seriesXYLTE);
        data.add(seriesXYGTE);

        return data;
    }
}
