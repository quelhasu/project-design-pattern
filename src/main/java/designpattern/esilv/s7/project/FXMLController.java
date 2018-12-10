package designpattern.esilv.s7.project;

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
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FXMLController implements Initializable {

    @FXML
    private TableView itemTable;
    @FXML
    private Button updateBtn;
    Inventory inv;
    
    @FXML
    private PieChart pieChart; 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inv = new Inventory();
        ObservableList<Item> data = FXCollections.observableArrayList(inv.getItems());

        TableColumn nameCol = new TableColumn();
        nameCol.setText("Name");
        nameCol.setMinWidth(300);
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn sellInCol = new TableColumn();
        sellInCol.setText("Sell In");
        sellInCol.setCellValueFactory(new PropertyValueFactory("sellIn"));

        TableColumn qualityCol = new TableColumn();
        qualityCol.setText("Quality");
        qualityCol.setCellValueFactory(new PropertyValueFactory("quality"));

        itemTable.getColumns().addAll(nameCol, sellInCol, qualityCol);

        itemTable.setItems(data);
        
        
        
    }

    @FXML
    public void loadData() {
        String itemJson = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(new Stage());
//        String dataFileName = file.getName();
//         System.out.println("File URI : " + file.getAbsolutePath());
        
        inv.loadData(file, itemJson);
        refreshItems();
        refreshStock();
    }

    private void refreshStock() {
    	ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList();
        
        for(Map.Entry<String, Integer> entry : inv.getStock().entrySet()) {
        	String key = entry.getKey();
        	Integer value = entry.getValue();
        	pieChartData.add(new PieChart.Data(key, value));
        }
        
        pieChart.setData(pieChartData);
//        pieChart.setTitle("Imported Fruits");
		
	}

	@FXML
    public void update() {
        inv.updateQuality();
        
        refreshItems();
    }

    private void refreshItems() {
        ObservableList<Item> data = FXCollections.observableArrayList(inv.getItems());
        itemTable.getItems().clear();
        itemTable.setItems(data);
    }
    
   

    


}