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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
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
	private TextField IdTf;

	@FXML
	private BarChart<String, Integer> barChart;
	@FXML
	private CategoryAxis xAxis;

	@FXML
	private NumberAxis yAxis;

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
		itemTable.getSelectionModel().selectedItemProperty()
				.addListener(e -> displayItemDetails((Item) itemTable.getSelectionModel().getSelectedItem()));
		itemTable.setItems(data);

	}

	private void displayItemDetails(Item item) {
		if (item != null) {
			selectedItem = item;
			activateSellItem();
			typeTf.setText(item.getName());
			sellInTf.setText(Integer.toString(item.getSellIn()));
			qualityTf.setText(Integer.toString(item.getQuality()));
			IdTf.setText(Integer.toString(item.getSerialId()));
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
		IdTf.setEditable(false);
	}

	@FXML
	private void activateBuyItem() {
		clearInput();
		sellBtn.setDisable(true);
		buyBtn.setDisable(false);
		typeTf.setEditable(true);
		sellInTf.setEditable(true);
		qualityTf.setEditable(true);
		IdTf.setEditable(true);
	}

	private void clearInput() {
		typeTf.setText("");
		sellInTf.setText("");
		qualityTf.setText("");
		IdTf.setText("");
		dateLbl.setText("");
	}

	@FXML
	private void buyItem() {
		Item newItem = null;
		try {
			newItem = new Item(Integer.parseInt(IdTf.getText()), typeTf.getText(), Integer.parseInt(sellInTf.getText()),
					Integer.parseInt(qualityTf.getText()));
			if (inv.notContains(Integer.parseInt(IdTf.getText()))) {
				inv.buyItem(newItem);
				infoLbl.setText("Item " + newItem.getSerialId() + " correctly bought!");
				refreshItemsView();
				refreshStockView();
				clearInput();

			} else {
				infoLbl.setText("Item " + newItem.getSerialId() + " can't be buy, ID already exist!");
			}

		} catch (NumberFormatException e) {
			System.out.println(e);
		}

	}

	@FXML
	private void sellItem() {
		clearInput();
		inv.sellItem(selectedItem);
		refreshItemsView();
		refreshStockView();
		infoLbl.setText("Item " + selectedItem.getSerialId() + " correctly sold!");
		activateBuyItem();

	}

	@FXML
	public void loadData() throws IOException {
		String itemJson = null;
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog(new Stage());
//        String dataFileName = file.getName();
//         System.out.println("File URI : " + file.toPath());
		itemJson = new String(Files.readAllBytes(file.toPath()));
		inv.loadData(itemJson);
		refreshItemsView();
		refreshStockView();
		updateBtn.setDisable(false);
		activateBuyItem();

	}

	private void refreshStockView() {
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

		for (Map.Entry<String, Integer> entry : inv.getStock().entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			PieChart.Data d = new PieChart.Data(key, value);
			d.setName(key);
			pieChartData.add(d);

		}

		pieChart.setData(pieChartData);
		
		
		// barchart
		
		xAxis.setLabel("Time");

		yAxis.setLabel("Number of items");

		XYChart.Series<String, Integer> dataSeries1 = boughtItems();
		XYChart.Series<String, Integer> dataSeries2 = soldItems();
		barChart.getData().clear();
		barChart.getData().add(dataSeries1);
		barChart.getData().add(dataSeries2);

	}

	public XYChart.Series<String, Integer> boughtItems() {
		XYChart.Series<String, Integer> dataSeries1 = new XYChart.Series<>();
		dataSeries1.setName("Number of bought");
		for (Map.Entry<String, Integer> entry : inv.getBoughtItems().entrySet()) {
			String date = entry.getKey();
			Integer value = entry.getValue();
			dataSeries1.getData().add(new XYChart.Data(date, value));
			
		}
		return dataSeries1;
	}
	
	public XYChart.Series<String, Integer> soldItems() {
		XYChart.Series<String, Integer> dataSeries1 = new XYChart.Series<>();
		dataSeries1.setName("Number of sold");
		for (Map.Entry<String, Integer> entry : inv.getSoldItems().entrySet()) {
			String date = entry.getKey();
			Integer value = entry.getValue();
			dataSeries1.getData().add(new XYChart.Data(date, value));
			System.out.println(date + " hihi " + value);
		}
		return dataSeries1;
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
