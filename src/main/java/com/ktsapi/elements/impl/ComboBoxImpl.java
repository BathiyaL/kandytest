package com.ktsapi.elements.impl;

import static com.ktsapi.actions.core.ActionsLogger.getElementLog;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.ktsapi.WebActons;
import com.ktsapi.actions.core.ActionsLogger;
import com.ktsapi.actions.core.KandyTestWebDriverActionsImpl;
import com.ktsapi.actions.log.ActionLog;
import com.ktsapi.elements.BaseWebElement;
import com.ktsapi.elements.ComboBox;
import com.ktsapi.enums.ABotActions;

public class ComboBoxImpl extends BaseWebElementImpl implements ComboBox{

	//protected WebElement element;
	protected Select selectElement = null;
	protected BaseWebElement parentBaseWebElement = null;
	
	public ComboBoxImpl(BaseWebElement baseWebElement) {
		super(baseWebElement.asWebelement(), baseWebElement.getByLocator(), baseWebElement.getFieldName());
		parentBaseWebElement = baseWebElement;
		KandyTestWebDriverActionsImpl actionImpl = new KandyTestWebDriverActionsImpl();
		//selectElement = new Select(WebActons.$$(baseWebElement.getByLocator()));		
		selectElement = new Select(actionImpl.$$(baseWebElement));		
	}

	@Override
	public void selectByVisibleText(String text) {		
		try {
			selectElement.selectByVisibleText(text);			
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_selectByVisibleText,getElementLog(parentBaseWebElement,null),text,null));
		} catch (Exception e){
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_selectByVisibleText,getElementLog(parentBaseWebElement,e.getMessage()),null,e));
			throw e;
		}
	}

	@Override
	public boolean isMultiple() {
		boolean isMultiple;
		try {
			isMultiple = selectElement.isMultiple();			
			ActionsLogger.logAction(ActionLog.ActionLogWithReturnValue(ABotActions.ToComboBox_isMultiple,getElementLog(parentBaseWebElement,null),null,null,Boolean.toString(isMultiple)));
			return isMultiple;
		} catch (Exception e){
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_isMultiple,getElementLog(parentBaseWebElement,e.getMessage()),null,e));
			throw e;
		}
	}

	@Override
	public List<WebElement> getOptions() {
		List<WebElement> webElm;
		try {			
			webElm = selectElement.getAllSelectedOptions() ;
			ActionsLogger.logAction(ActionLog.ActionLogWithReturnValue(ABotActions.ToComboBox_getOptions,getElementLog(parentBaseWebElement,null),null,null,"@List<WebElement>"));
			return webElm;
		} catch (Exception e){
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_getOptions,getElementLog(parentBaseWebElement,e.getMessage()),null,e));
			throw e;
		}
	}

	@Override
	public List<WebElement> getAllSelectedOptions() {
		List<WebElement> webElm;
		try {			
			webElm = selectElement.getAllSelectedOptions() ;
			ActionsLogger.logAction(ActionLog.ActionLogWithReturnValue(ABotActions.ToComboBox_getAllSelectedOptions,getElementLog(parentBaseWebElement,null),null,null,"@List<WebElement>"));
			return webElm;
		} catch (Exception e){
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_getAllSelectedOptions,getElementLog(parentBaseWebElement,e.getMessage()),null,e));
			throw e;
		}
	}

	@Override
	public WebElement getFirstSelectedOption() {
		WebElement webElm;
		try {			
			webElm = selectElement.getFirstSelectedOption() ;
			ActionsLogger.logAction(ActionLog.ActionLogWithReturnValue(ABotActions.ToComboBox_getFirstSelectedOption,getElementLog(parentBaseWebElement,null),null,null,"@WebElement"));
			return webElm;
		} catch (Exception e){
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_getFirstSelectedOption,getElementLog(parentBaseWebElement,e.getMessage()),null,e));
			throw e;
		}		
	}

	@Override
	public void selectByIndex(int index) {
		try {
			selectElement.selectByIndex(index);			
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_selectByIndex,getElementLog(parentBaseWebElement,null),Integer.toString(index),null));
		} catch (Exception e){
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_selectByIndex,getElementLog(parentBaseWebElement,e.getMessage()),null,e));
			throw e;
		}		
	}

	@Override
	public void selectByValue(String value) {
		try {
			selectElement.selectByValue(value);			
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_selectByValue,getElementLog(parentBaseWebElement,null),value,null));
		} catch (Exception e){
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_selectByValue,getElementLog(parentBaseWebElement,e.getMessage()),null,e));
			throw e;
		}		
	}

	@Override
	public void deselectAll() {
		try {
			selectElement.deselectAll();			
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_deselectAll,getElementLog(parentBaseWebElement,null),null,null));
		} catch (Exception e){
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_deselectAll,getElementLog(parentBaseWebElement,e.getMessage()),null,e));
			throw e;
		}		
	}

	@Override
	public void deselectByValue(String value) {
		try {
			selectElement.deselectByValue(value);			
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_deselectByValue,getElementLog(parentBaseWebElement,null),value,null));
		} catch (Exception e){
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_deselectByValue,getElementLog(parentBaseWebElement,e.getMessage()),null,e));
			throw e;
		}		
	}

	@Override
	public void deselectByIndex(int index) {
		try {
			selectElement.deselectByIndex(index);			
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_deselectByIndex,getElementLog(parentBaseWebElement,null),Integer.toString(index),null));
		} catch (Exception e){
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_deselectByIndex,getElementLog(parentBaseWebElement,e.getMessage()),null,e));
			throw e;
		}		
	}

	@Override
	public void deselectByVisibleText(String text) {
		try {
			selectElement.deselectByVisibleText(text);			
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_deselectByIndex,getElementLog(parentBaseWebElement,null),text,null));
		} catch (Exception e){
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToComboBox_deselectByIndex,getElementLog(parentBaseWebElement,e.getMessage()),null,e));
			throw e;
		}		
	}
	
}
