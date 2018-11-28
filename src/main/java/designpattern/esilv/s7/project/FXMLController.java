package designpattern.esilv.s7.project;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLController implements Initializable {

    @FXML
    private TableView itemTable;
    @FXML
    private Button updateBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Inventory inv = new Inventory();
        ObservableList<Item> data = FXCollections.observableArrayList(inv.getItems());

        TableColumn nameCol = new TableColumn();
        nameCol.setText("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        
        TableColumn sellInCol = new TableColumn();
        sellInCol.setText("Sell In");
        sellInCol.setCellValueFactory(new PropertyValueFactory("sellIn"));
        
        TableColumn qualityCol = new TableColumn();
        qualityCol.setText("Quality");
//        qualityCol.setMinWidth(200);
        qualityCol.setCellValueFactory(new PropertyValueFactory("quality"));
        
        itemTable.setItems(data);
        itemTable.getColumns().addAll(nameCol, sellInCol, qualityCol);

        itemTable.setItems(data);

    }

}
