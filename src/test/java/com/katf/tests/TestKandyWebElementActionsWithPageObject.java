package com.katf.tests;

import static com.ktsapi.CommonActions.*;
import static com.ktsapi.WebActons.GetTitle;
import static com.ktsapi.WebActons.GoTo;
import static com.ktsapi.WebActons.OpenBrowser;
import static com.ktsapi.WebActons.getWebPage;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.ArrayList;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.katf.pageobjects.WebElementPage4Page;
import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.TestDriver;

@TestConfiguration(
		testDriver = TestDriver.WEB, // Mandatory
		browser=Browsers.CHROME,
		implicitlyWaitTime = 15,
		baseUrl = "src/test/resources/demoapp/web-elements-page-4.html"
)
public class TestKandyWebElementActionsWithPageObject {
	
	@BeforeTest
	public void goSite() {	 	 
	      OpenBrowser();	      
	      File path =  new File(baseUrl());
	      GoTo("file:///"+path.getAbsolutePath());
	      print(GetTitle());
	 }
	
	@Test
	public void testMothod() {	
		
		String typeValue = "Test All The Things";
		WebElementPage4Page page = getWebPage(WebElementPage4Page.class);
		saveScreenshot("SC0");
		
		page.name.type("Test All The Things");
		print(page.name.getAttribute("value")); // TOODO : get action getValue()
		Assert.assertEquals(page.name.getAttribute("value"), typeValue,"Type action fail");

		page.button.click();
		Assert.assertEquals(page.lbltipAddedComment.getText(), "you just clicked me!","Button click action Fail");

		Assert.assertEquals(page.checkbox1.isSelected(), false,"Checkbox should not be checked at this point");
		page.checkbox1.check();
		Assert.assertEquals(page.checkbox1.isSelected(), true,"Check action fail on checkbox");
		Assert.assertEquals(page.checkbox1.getAttribute("checked"), "true","Check action fail"); // this returns null when not checked instead false
		saveScreenshot("SC1");
		
		Assert.assertEquals(page.radio1.isSelected(), false,"Radio button should not be checked at this point");
		page.radio1.check();
		Assert.assertEquals(page.radio1.isSelected(), true,"Check action fail on radio button");
		saveScreenshot("SC2");
		
		// assert ComboBox actions
		Assert.assertEquals(page.selectCars.toComboBox().getFirstSelectedOption().getText(), "Volvo","Default option should be Volvo");
		page.selectCars.toComboBox().selectByValue("saab");
		Assert.assertEquals(page.selectCars.toComboBox().getFirstSelectedOption().getText(), "Saab","toComboBox().selectByValue() action fail");
		
		page.selectCars.toComboBox().selectByVisibleText("Mercedes");
		Assert.assertEquals(page.selectCars.toComboBox().getFirstSelectedOption().getText(), "Mercedes","toComboBox().selectByVisibleText action fail");
		
		page.selectCars.toComboBox().selectByIndex(3);
		Assert.assertEquals(page.selectCars.toComboBox().getFirstSelectedOption().getText(), "Audi","toComboBox().selectByIndex action fail");
		saveScreenshot("SC3");
				
		Assert.assertEquals(page.multiSelect.toComboBox().getAllSelectedOptions().size(), 0,"Deafult multi selected values count should be 0");
		page.multiSelect.toComboBox().selectByIndex(0);
		page.multiSelect.toComboBox().selectByIndex(2);
		ArrayList<String> multiSelectValues = new ArrayList<>();
		
		for(WebElement elm  : page.multiSelect.toComboBox().getAllSelectedOptions()) {
			multiSelectValues.add(elm.getText());
		}
		Assert.assertEquals(multiSelectValues.size(), 2,"multi selected values count should be 2");
		assertThat(multiSelectValues).describedAs("Multi selected values are not same").containsExactly("Java","Python");
		
		page.multiSelect.toComboBox().deselectAll();
		Assert.assertEquals(page.multiSelect.toComboBox().getAllSelectedOptions().size(), 0,"toComboBox().deselectAll() action fail");
		saveScreenshot("SC4");
		
		// HTML drag and drop is not supported by selenium
		//page.drag1.dragAndDropTo(page.rectangle);

		
		
//		GoTo("C:/Users/stz/Downloads/js-sortable-list/sort-list.html");
//		pause(5);
//		page.source.dragAndDropTo(page.destination);
		pause(1);
		
	}

}

