package designpattern.esilv.s7.controller;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.google.gson.Gson;
import designpattern.esilv.s7.model.Inventory;
import designpattern.esilv.s7.model.Item;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXMLController implements Initializable {

    @FXML
    private TableView itemTable;
    @FXML
    private Button updateBtn;
    @FXML
    private Button buyBtn;
    @FXML
    private Button sellBtn;
    @FXML
    private Button clearBtn;
    @FXML
    private Label infoLbl;
    @FXML
    private TextField typeTf;
    @FXML
    private TextField sellInTf;
    @FXML
    private TextField qualityTf;
    @FXML
    private Label dateLbl;
    @FXML
    private Label IDLbl;

    @FXML
    private PieChart pieChart;

    Inventory inv;

    Item selectedItem;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inv = new Inventory();
        ObservableList<Item> data = FXCollections.observableArrayList(inv.getItems());

        TableColumn nameCol = new TableColumn();
        nameCol.setText("Type");
        nameCol.setMinWidth(270);
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn sellInCol = new TableColumn();
        sellInCol.setText("Sell In");
        sellInCol.setCellValueFactory(new PropertyValueFactory("sellIn"));

        TableColumn qualityCol = new TableColumn();
        qualityCol.setText("Quality");
        qualityCol.setCellValueFactory(new PropertyValueFactory("quality"));

        TableColumn dateCol = new TableColumn();
        dateCol.setText("Creation Date");
        dateCol.setMinWidth(100);
        dateCol.setCellValueFactory(new PropertyValueFactory("creationDate"));

        TableColumn idCol = new TableColumn();
        idCol.setText("ID");
        idCol.setCellValueFactory(new PropertyValueFactory("serialId"));

        itemTable.getColumns().addAll(idCol, dateCol, nameCol, sellInCol, qualityCol);
        itemTable.getSelectionModel().selectedItemProperty().addListener(e -> displayItemDetails((Item) itemTable.getSelectionModel().getSelectedItem()));
        itemTable.setItems(data);

    }

    private void displayItemDetails(Item item) {
        if (item != null) {
            selectedItem = item;
            activateSellItem();
            typeTf.setText(item.getName());
            sellInTf.setText(Integer.toString(item.getSellIn()));
            qualityTf.setText(Integer.toString(item.getQuality()));
            IDLbl.setText(Integer.toString(item.getSerialId()));
            dateLbl.setText(item.getCreationDate());
        }
    }

    private void activateSellItem() {
        sellBtn.setDisable(false);
        buyBtn.setDisable(true);
        clearBtn.setDisable(false);
        typeTf.setEditable(false);
        sellInTf.setEditable(false);
        qualityTf.setEditable(false);
    }

    @FXML
    private void activateBuyItem() {
        clearInput();
        sellBtn.setDisable(true);
        buyBtn.setDisable(false);
        typeTf.setEditable(true);
        sellInTf.setEditable(true);
        qualityTf.setEditable(true);
    }

    private void clearInput() {
        typeTf.setText("");
        sellInTf.setText("");
        qualityTf.setText("");
        IDLbl.setText("");
        dateLbl.setText("");
    }

    @FXML
    private void buyItem() {

    }

    @FXML
    private void sellItem() {
        clearInput();
        inv.deleteItem(selectedItem);
        refreshItemsView();
        refreshStockView();
        infoLbl.setText("Item " + selectedItem.getSerialId() + " correctly sold!");
    }

    @FXML
    public void loadData() {
        String itemJson = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(new Stage());
//        String dataFileName = file.getName();
//         System.out.println("File URI : " + file.toPath());

        inv.loadData(file, itemJson);
        refreshItemsView();
        refreshStockView();
    }

    private void refreshStockView() {
        ObservableList<PieChart.Data> pieChartData
            = FXCollections.observableArrayList();

        for (Map.Entry<String, Integer> entry : inv.getStock().entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            PieChart.Data d = new PieChart.Data(key, value);
            d.setName(key);
            pieChartData.add(d);
        }

        pieChart.setData(pieChartData);
        pieChart.setTitle("Inventory PieChart");

    }

    @FXML
    public void update() {
        inv.updateQuality();
        refreshItemsView();
    }

    private void refreshItemsView() {
        ObservableList<Item> data = FXCollections.observableArrayList(inv.getItems());
        itemTable.getItems().clear();
        itemTable.setItems(data);

    }
}
