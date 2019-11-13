package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Controller {

    //METHODS
    NumericalMethod eulerMethod = new EulerMethod();
    NumericalMethod eulerMethodImproved = new EulerMethodImproved();
    NumericalMethod rungeKuttaMethod = new RungeKuttaMethod();

    //EQUATIONS
    ExactEquation exactEquation = new ExactEquation();

    Stage stagePoints;
    Scene scenePoints;

    XYChart.Series dataExactMethod;
    ArrayList<XYChart.Series> dataEulerMethod;
    ArrayList<XYChart.Series> dataEulerMethodImproved;
    ArrayList<XYChart.Series> dataRungeKutta;

    @FXML
    private TextField textFieldx0;
    @FXML
    private TextField textFieldy0;
    @FXML
    private TextField textFieldX;
    @FXML
    private TextField textFieldSteps;
    @FXML
    private TextField textFieldEvolution;

    @FXML
    private void handleOnButtonAction() {
        double x0 = 0, y0 = 0, X = 0;
        int N = 0, evol = 0;
        try {


            x0 = Double.parseDouble(textFieldx0.getText());
            y0 = Double.parseDouble(textFieldy0.getText());
            X = Double.parseDouble(textFieldX.getText());
            N = Integer.parseInt(textFieldSteps.getText());
            evol = Integer.parseInt(textFieldEvolution.getText());
        } catch (Exception ex){
            System.out.println("Wrong input, Exception message: " + ex.getMessage());
            return;
        }



        if (!checkInput(x0, y0, X, N, evol)){
            return;
        }
        calculate_data(x0, y0, X, N, evol);
        drawPlots(x0, y0, X, N);
    }


    private boolean checkInput(double x0, double y0, double X, int N, int evol){
        if (X < x0){
            System.out.println("Wrong range");
            return false;
        }

        if (evol < 30){
            System.out.println("Step number for global error should be greater or equal than 30, see report");
            return false;
        }

        if (N < 30){
            System.out.println("Step number for plotting and local error should be greater or equal than 30, see report");
            return false;
        }

        return true;
    }

    private void calculate_data(double x0, double y0, double X, int N, int evol){
        dataExactMethod = exactEquation.getExactSeries(x0, y0, X, N);
        dataEulerMethod = eulerMethod.calculate(x0, y0, X, N, evol);
        dataEulerMethodImproved = eulerMethodImproved.calculate(x0, y0, X, N, evol);
        dataRungeKutta = rungeKuttaMethod.calculate(x0, y0, X, N, evol);
    }

    private void drawPlots(double x0, double y0, double X, int N) {
        drawLTEPlot(x0, y0, X, N);
        drawGTEPlot(x0, y0, X, N);
        drawPointsPlot(x0, y0, X, N);

    }

    private void drawPointsPlot(double x0, double y0, double X, int N) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("x");


        //POINTS
        final LineChart<Number, Number> pointsChart =
                new LineChart<Number, Number>(xAxis, yAxis);
        pointsChart.setCreateSymbols(false);
        pointsChart.setTitle("Plots of equation exact and approximated values");

        //EXACT POINTS
        XYChart.Series pointsExact = dataExactMethod;
        pointsExact.setName("Exact");

        //EULER POINTS
        XYChart.Series pointsEulerMethod = dataEulerMethod.get(0);
        pointsEulerMethod.setName("Euler");

        //EULER IMPROVED POINTS
        XYChart.Series pointsEulerMethodImproved = dataEulerMethodImproved.get(0);
        pointsEulerMethodImproved.setName("Euler Impr");

        //RUNGE KUTTA
        XYChart.Series pointsRungeKutta = dataRungeKutta.get(0);
        pointsRungeKutta.setName("RK");

        // --- //

        //SHOW POINTS
        stagePoints = new Stage();
        scenePoints = new Scene(pointsChart, 600, 400);
        pointsChart.getData().addAll(pointsExact, pointsEulerMethod, pointsEulerMethodImproved, pointsRungeKutta);
        stagePoints.setScene(scenePoints);
        stagePoints.show();
    }

    private void drawLTEPlot(double x0, double y0, double X, int N) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("x");

        //LTE ERRORS
        final LineChart<Number, Number> lteChart =
                new LineChart<Number, Number>(xAxis, yAxis);
        lteChart.setCreateSymbols(false);
        lteChart.setTitle("Plots of LTE");

        //EULER LTE ERRORS
        XYChart.Series lteEulerMethod = dataEulerMethod.get(1);
        lteEulerMethod.setName("Euler");

        //EULER IMPROVED LTE ERRORS
        XYChart.Series lteEulerMethodImproved = dataEulerMethodImproved.get(1);
        lteEulerMethodImproved.setName("Euler Impr");

        //RUNGE KUTTA LTE ERRORS
        XYChart.Series lteRungeKutta = dataRungeKutta.get(1);
        lteRungeKutta.setName("RK");

        //SHOW LTE
        Stage stageLTE = new Stage();
        XYChart.Series empty = new XYChart.Series();
        Scene sceneLTE = new Scene(lteChart, 600, 400);
        lteChart.getData().addAll(empty, lteEulerMethod, lteEulerMethodImproved, lteRungeKutta);
        stageLTE.setScene(sceneLTE);
        stageLTE.show();
    }

    private void drawGTEPlot(double x0, double y0, double X, int N) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("x");

        //GTE ERRORS
        final LineChart<Number, Number> gteChart =
                new LineChart<Number, Number>(xAxis, yAxis);
        gteChart.setCreateSymbols(false);
        gteChart.setTitle("Plots of GTE");

        //EULER GTE ERRORS
        XYChart.Series gteEulerMethod = dataEulerMethod.get(2);
        gteEulerMethod.setName("Euler");

        //EULER GTE ERRORS
        XYChart.Series gteEulerMethodImproved = dataEulerMethodImproved.get(2);
        gteEulerMethodImproved.setName("Euler Imp");

        //EULER GTE ERRORS
        XYChart.Series gteRungeKutta = dataRungeKutta.get(2);
        gteRungeKutta.setName("RK");

        //SHOW GTE
        Stage stageGTE = new Stage();
        XYChart.Series empty = new XYChart.Series();
        Scene sceneGTE = new Scene(gteChart, 600, 400);
        gteChart.getData().addAll(empty, gteEulerMethod, gteEulerMethodImproved, gteRungeKutta);
        stageGTE.setScene(sceneGTE);
        stageGTE.show();
    }

}
