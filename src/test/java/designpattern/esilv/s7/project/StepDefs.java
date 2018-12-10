package designpattern.esilv.s7.project;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.java.en.*;
import org.junit.runner.RunWith;
import static org.hamcrest.core.Is.is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;



public class StepDefs {
	private Inventory inventory;
	private Item selectedItem;
	
	@Given("^I have an AgedBrie$")
public void iHaveAnAgedBrie() throws Throwable {
	selectedItem = new Item("AgedBrie",0,0);
	}
	
	
	
	@Then("^I set its quality as (\\d+)$")
	public void itsQualityIs(int quality) throws Throwable {
	assertThat(selectedItem.getQuality(), is(quality));
	}

	@And("^I set its sellIn as (\\d+)$")
	public void itsSellInIs(int sellIn) throws Throwable {
	assertThat(selectedItem.getQuality(), is(sellIn));
	}
	
	@When("^I update the inventory$")
	public void iUpdateTheInventory() throws Throwable {
	inventory.updateQuality();
	}

	@Then("^the quality of the conjured item is (\\d+)$")
	public void theQualityOfTheConjuredItemIs(int conjuredQuality) throws Throwable {
	assertThat(selectedItem.getQuality(), is(conjuredQuality));
	}
	}
	
	
}
