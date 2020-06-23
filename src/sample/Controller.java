package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Controller {

    @FXML
    private TableView<PointModel> pointTable;
    @FXML
    private TableColumn<PointModel, String> XColumn;
    @FXML
    private TableColumn<PointModel, String> YColumn;

    @FXML
    private Button addBut;
    @FXML
    private Button removeBut;
    @FXML
    private TextField textField;


    @FXML
    private LineChart <Double,Double> lineChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    private ObservableList<PointModel> pointList = FXCollections.observableArrayList();



    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
            //инициализация массива по умолчанию
            for (int i=0;i<=8;i+=2){
                GeneralModel.addPoint(new PointModel(i));
                GeneralModel.addPoint(new PointModel(-i));
            }
        update();
        refreshGraph();

        // Инициализация таблицы адресатов с двумя столбцами.
        XColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(Double.toString(cellData.getValue().getX())));
        YColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(Double.toString(cellData.getValue().getY())));

        //разрешение редактирования
        XColumn.setCellFactory(TextFieldTableCell.<PointModel>forTableColumn());
    }

    public void addBut (){
            if (textField.getText().equals("")) {
                handleError("добавлении");
            }
            else {
                try {
                    double x = Double.parseDouble(textField.getText());
                    PointModel newPoint = new PointModel(x);
                    GeneralModel.addPoint(newPoint);
                    textField.clear();
                    update();
                    refreshGraph();
                }
                catch (Exception e) {
                    handleError("добавлении");
                }
            }
    }

    public void removeBut(){
        PointModel remPoint = (PointModel) pointTable.getSelectionModel().getSelectedItem();
        GeneralModel.removePoint(remPoint);
        pointTable.getItems().removeAll(remPoint);
        update();
        refreshGraph();
    }


    public void editX (CellEditEvent <PointModel, String> event){
            try {
                TablePosition<PointModel, String> position = event.getTablePosition();
                double newValue = Double.parseDouble(event.getNewValue());
                int row = position.getRow();
                PointModel point = event.getTableView().getItems().get(row);
                GeneralModel.editPoint(point,new PointModel(newValue));
                point.setX(newValue);
                //pointTable.refresh();
                update();
                refreshGraph();
            }
            catch (Exception e){
                handleError("изменении");
            }
    }

    public void handleError(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка при " + msg + " точки");
        alert.setContentText("Введите корректное число");
        alert.show();
    }


    public void update() {
        pointTable.getItems().removeAll(); //удаляем все из таблицы
        pointList.clear(); //очищаем обсервебл лист

        GeneralModel.sortArray();
        for (PointModel p:  GeneralModel.getPointModels()) {
            pointList.add(p);
        } //добавляем заново в обсервебл лист

        // Добавление в таблицу данных из наблюдаемого списка
        pointTable.setItems(pointList); //заполняем таблицу
    }//апдейт таблицы значений


    public void refreshGraph(){
        update();
        pr();

        lineChart.getData().clear();
        lineChart.layout();
        //xAxis.getCategories().clear();
        XYChart.Series series = new XYChart.Series();
        for (int i = 0; i < pointList.size(); i++) {
            series.getData().add(new XYChart.Data<String ,Double>(Double.toString(pointList.get(i).getX()), pointList.get(i).getY()));
        }
        lineChart.getData().addAll(series);
    }


    public void pr (){
        for (PointModel pm: GeneralModel.getPointModels()){
            System.out.println("x: "+pm.getX() + "y: "+pm.getY() );
        }
        System.out.println("\n");
    }
}