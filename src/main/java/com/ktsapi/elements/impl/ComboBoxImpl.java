package com.ktsapi.elements.impl;

import static com.ktsapi.actions.core.ActionsLogger.getElementLog;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import com.ktsapi.actions.core.ActionsLogger;
import com.ktsapi.actions.core.KandyTestWebDriverActionsImpl;
import com.ktsapi.actions.log.ActionLog;
import com.ktsapi.elements.BaseWebElement;
import com.ktsapi.elements.ComboBox;
import com.ktsapi.enums.ABotActions;

public class ComboBoxImpl extends BaseWebElementImpl implements ComboBox{

	protected Select selectElement = null;
	protected BaseWebElement parentBaseWebElement = null;
	
	public ComboBoxImpl(BaseWebElement baseWebElement) {
		super(baseWebElement.asWebelement(), baseWebElement.getByLocator(), baseWebElement.getFieldName());
		parentBaseWebElement = baseWebElement;
		KandyTestWebDriverActionsImpl actionImpl = new KandyTestWebDriverActionsImpl();
		selectElement = new Select(actionImpl.$$(baseWebElement));		
	}

	@Override
	public void selectByVisibleText(String text) {	
		String logMessage = "ComboBox selectByVisibleText {"+text+"} of {%s} ";
		try {
			selectElement.selectByVisibleText(text);			
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_selectByVisibleText,getElementLog(parentBaseWebElement,null),text,null,logMessage));
		} catch (Exception e){
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_selectByVisibleText,getElementLog(parentBaseWebElement,e.getMessage()),null,e,logMessage));
			throw e;
		}
	}

	@Override
	public boolean isMultiple() {
		String logMessage = "ComboBox IsMultiple of {%s} ";
		boolean isMultiple;
		try {
			isMultiple = selectElement.isMultiple();			
			logMessage = logMessage.concat("returns ").concat(Boolean.toString(isMultiple));
			ActionsLogger.logAction(ActionLog.ActionLogWithReturnValue(ABotActions.ToComboBox_isMultiple,getElementLog(parentBaseWebElement,null),null,null,Boolean.toString(isMultiple),logMessage));
			return isMultiple;
		} catch (Exception e){
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_isMultiple,getElementLog(parentBaseWebElement,e.getMessage()),null,e,logMessage));
			throw e;
		}
	}

	@Override
	public List<WebElement> getOptions() {
		String logMessage = "ComboBox GetOptions of {%s} ";
		List<WebElement> webElm;
		try {			
			webElm = selectElement.getOptions() ;
			logMessage = logMessage.concat("returns ").concat(webElm.size() + " WebElement");
			ActionsLogger.logAction(ActionLog.ActionLogWithReturnValue(ABotActions.ToComboBox_getOptions,getElementLog(parentBaseWebElement,null),null,null,"@List<WebElement>",logMessage));
			return webElm;
		} catch (Exception e){
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_getOptions,getElementLog(parentBaseWebElement,e.getMessage()),null,e,logMessage));
			throw e;
		}
	}

	@Override
	public List<WebElement> getAllSelectedOptions() {
		String logMessage = "ComboBox GetAllSelectedOptions of {%s} ";
		List<WebElement> webElm;
		try {			
			webElm = selectElement.getAllSelectedOptions() ;
			logMessage = logMessage.concat("returns ").concat(webElm.size() + " WebElement");
			ActionsLogger.logAction(ActionLog.ActionLogWithReturnValue(ABotActions.ToComboBox_getAllSelectedOptions,getElementLog(parentBaseWebElement,null),null,null,"@List<WebElement>",logMessage));
			return webElm;
		} catch (Exception e){
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_getAllSelectedOptions,getElementLog(parentBaseWebElement,e.getMessage()),null,e,logMessage));
			throw e;
		}
	}

	@Override
	public WebElement getFirstSelectedOption() {
		String logMessage = "ComboBox GetFirstSelectedOption of {%s} ";
		WebElement webElm;
		try {			
			webElm = selectElement.getFirstSelectedOption() ;
			String returnValue = "WebElement object";
			logMessage = logMessage.concat("returns ").concat(returnValue);
			ActionsLogger.logAction(ActionLog.ActionLogWithReturnValue(ABotActions.ToComboBox_getFirstSelectedOption,getElementLog(parentBaseWebElement,null),null,null,"@WebElement",logMessage));
			return webElm;
		} catch (Exception e){
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_getFirstSelectedOption,getElementLog(parentBaseWebElement,e.getMessage()),null,e,logMessage));
			throw e;
		}		
	}

	@Override
	public void selectByIndex(int index) {
		String logMessage = "ComboBox SelectByIndex {"+index+"} of {%s} ";
		try {
			selectElement.selectByIndex(index);			
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_selectByIndex,getElementLog(parentBaseWebElement,null),Integer.toString(index),null,logMessage));
		} catch (Exception e){
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_selectByIndex,getElementLog(parentBaseWebElement,e.getMessage()),null,e,logMessage));
			throw e;
		}		
	}

	@Override
	public void selectByValue(String value) {
		String logMessage = "ComboBox SelectByValue {"+value+"} of {%s}";
		try {
			selectElement.selectByValue(value);			
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_selectByValue,getElementLog(parentBaseWebElement,null),value,null,logMessage));
		} catch (Exception e){
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_selectByValue,getElementLog(parentBaseWebElement,e.getMessage()),null,e,logMessage));
			throw e;
		}		
	}

	@Override
	public void deselectAll() {
		String logMessage = "ComboBox DeselectAll of {%s}";
		try {
			selectElement.deselectAll();			
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_deselectAll,getElementLog(parentBaseWebElement,null),null,null,logMessage));
		} catch (Exception e){
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_deselectAll,getElementLog(parentBaseWebElement,e.getMessage()),null,e,logMessage));
			throw e;
		}		
	}

	@Override
	public void deselectByValue(String value) {
		String logMessage = "ComboBox DeselectByValue {"+value+"} of {%s}";
		try {
			selectElement.deselectByValue(value);			
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_deselectByValue,getElementLog(parentBaseWebElement,null),value,null,logMessage));
		} catch (Exception e){
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_deselectByValue,getElementLog(parentBaseWebElement,e.getMessage()),null,e,logMessage));
			throw e;
		}		
	}

	@Override
	public void deselectByIndex(int index) {
		String logMessage = "ComboBox DeselectByIndex {"+index+"} of {%s}";
		try {
			selectElement.deselectByIndex(index);			
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_deselectByIndex,getElementLog(parentBaseWebElement,null),Integer.toString(index),null,logMessage));
		} catch (Exception e){
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_deselectByIndex,getElementLog(parentBaseWebElement,e.getMessage()),null,e,logMessage));
			throw e;
		}		
	}

	@Override
	public void deselectByVisibleText(String text) {
		String logMessage = "ComboBox DeselectByVisibleText {"+text+"} of {%s}";
		try {
			selectElement.deselectByVisibleText(text);			
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_deselectByIndex,getElementLog(parentBaseWebElement,null),text,null,logMessage));
		} catch (Exception e){
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_deselectByIndex,getElementLog(parentBaseWebElement,e.getMessage()),null,e,logMessage));
			throw e;
		}		
	}
	
}
