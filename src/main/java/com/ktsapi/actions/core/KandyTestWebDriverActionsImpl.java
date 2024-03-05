package com.ktsapi.actions.core;

import static com.ktsapi.actions.core.ActionsLogger.getElementLog;
import static com.ktsapi.actions.core.ActionsLogger.logAction;
import static com.ktsapi.actions.core.ActionsLogger.systemLogsWarn;
import static com.ktsapi.actions.core.ActionsLogger.userLogs;

import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Size;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ktsapi.actions.KandyTestWebDriverActions;
import com.ktsapi.actions.core.api.AlertImpl;
import com.ktsapi.actions.core.api.DriverTimeOutsImpl;
import com.ktsapi.actions.log.ActionLog;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.elements.BaseWebElement;
import com.ktsapi.elements.BrowserNavigation;
import com.ktsapi.elements.BrowserWindow;
import com.ktsapi.elements.ComboBox;
import com.ktsapi.elements.EnhancedWebElement;
import com.ktsapi.elements.ExpectedConditions;
import com.ktsapi.elements.FrameElement;
import com.ktsapi.elements.TargetLocatorFrame;
import com.ktsapi.elements.impl.BaseWebElementImpl;
import com.ktsapi.elements.impl.BrowserNavigationImpl;
import com.ktsapi.elements.impl.ComboBoxImpl;
import com.ktsapi.elements.impl.ExpectedConditionsImpl;
import com.ktsapi.elements.impl.FrameElementImpl;
import com.ktsapi.elements.impl.TargetLocatorImpl;
import com.ktsapi.enums.ABotActions;
import com.ktsapi.exceptions.InvalidLocatorException;
import com.ktsapi.exceptions.SwitchToFrameException;
import com.ktsapi.exceptions.TimeOutException;
import com.ktsapi.exceptions.UnexpectedWebElementException;
import com.ktsapi.pagefactory.AvtomatElementFactory;
import com.ktsapi.pagefactory.AvtomatElementFactoryImpl;
import com.ktsapi.pagefactory.AvtomatPageFactory;

public class KandyTestWebDriverActionsImpl implements KandyTestWebDriverActions {
	//public static WebDriver driver;
	private static Class<?> LAST_RUNNING_PAGE_OBJECT = null;

	public WebDriver driver(){			
		return TestInitializr.getWebDriver();
	}
	
	@Override
	public void OpenBrowser() {
		String launchedBrowser = TestInitializr.getDesiredCapabilities().getBrowserName();
		try {
			driver();
			launchedBrowser = launchedBrowser + " " + TestInitializr.getRunningBrowserVersion();
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.OpenBrowser, launchedBrowser, null));		
		} catch (Exception e) {
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.OpenBrowser, launchedBrowser, e));		
			throw e;
		}
	}

	@Override
	public String GoTo(String url) {
		String logMessage = "GoTo "+url;
		String logMessage2 = " " + url;
		String windowHandle = null;
		try {
			driver().get(url);
			windowHandle =  driver().getWindowHandle();
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.GoTo,logMessage,null));	
		}catch(Exception e) {			
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.GoTo,logMessage2,e));
			throw e;
		}	
		return windowHandle;
	}

	@Override
	public String GetNewWindow(String url) {
		
		String logMessage = "GetNewWindow "+url;
		String logMessage2 = " " + url;
		String windowHandle = null;
		try {
			driver().switchTo().newWindow(WindowType.WINDOW);
			driver().get(url);
			windowHandle =  driver().getWindowHandle();	
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.GetNewWindow,logMessage,null));	
		}catch(Exception e) {			
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.GetNewWindow,logMessage2,e));
			throw e;
		}
		return windowHandle;
	}
	
	@Override
	public String GetNewTab(String url) {
		
		String logMessage = "GetNewTab "+url;
		String logMessage2 = " " + url;
		String windowHandle = null;
		try {
			driver().switchTo().newWindow(WindowType.TAB);
			driver().get(url);
			windowHandle =  driver().getWindowHandle();
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.GetNewTab,logMessage,null));	
		}catch(Exception e) {			
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.GetNewTab,logMessage2,e));
			throw e;
		}
		return windowHandle;
	}

	// @Override
	public <C> C GetWebPage(Class<C> page) {
		return (C) AvtomatPageFactory.getWebPage(page);
	}
	
//SeleniumWebElement.........................................................................................
	private final String EXPECTED_EROR_MESSAGE_WHEN_ELEMENT_NOT_CICKABLE = "is not clickable at point";
	@Override
	public void Click(BaseWebElement element) {
		String logMessage = "Click on {%s}";
		try {
			final int noOfTries = 5; // TODO : should be change by config file
			for(int i=1 ; i<=noOfTries ; i++) {
				try {				
					$$(element).click();
					break;
				}
				catch(StaleElementReferenceException e) {
					systemLogsWarn("[Try-"+i+" : "+element.getByLocator()+"] " + "Click failed with StaleElementReferenceException" );
				}
			}
			
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.Click,getElementLog(element,null),null,null,logMessage));
		}catch (ElementClickInterceptedException e){
			String warning = "ElementClickInterceptedException occuerd and click action perform with java script";
			try {
				if(e.getMessage().contains(EXPECTED_EROR_MESSAGE_WHEN_ELEMENT_NOT_CICKABLE)) {
					jsClick(element.asWebelement());
					systemLogsWarn(warning);					
					logAction(ActionLog.ActionLogWithWarningsAndWithoutReturnValue(ABotActions.Click,getElementLog(element,null),null,null,warning));
				}
			}catch (Exception ee){
				logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.Click,getElementLog(element,ee.getMessage()),null,ee,logMessage));
				throw e;
			}
		}
		catch (Exception e){
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.Click,getElementLog(element,e.getMessage()),null,e,logMessage));
			throw e;
		}
	}
	
	@Override
	public void SendKeys(BaseWebElement element, CharSequence... keysToSend){	
		try {			
			$$(element).sendKeys(keysToSend);
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.SendKeys,getElementLog(element,null),Arrays.toString(keysToSend),null,null));
		} catch (Exception e){			
			String value = "";
			if(keysToSend==null){
				value = "null"; 
			}else {
				value = Arrays.toString(keysToSend);
			}
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.SendKeys,getElementLog(element,e.getMessage()),value,e,null));
			throw e;
		}
	}
	
	
	@Override
	public void Clear(BaseWebElement element) {
		try {			
			$$(element).clear();
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.Clear,getElementLog(element,null),null,null,null));
		} catch (Exception e){
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.Clear,getElementLog(element,e.getMessage()),null,e,null));
			throw e;
		}		
	}
	
	@Override
	public void Submit(BaseWebElement element) {
		try {		
			$$(element).submit();
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.Submit,getElementLog(element,null),null,null,null));
		} catch (Exception e){
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.Submit,getElementLog(element,e.getMessage()),null,e,null));
			throw e;
		}		
	}
	
	@Override
	public String GetTagName(BaseWebElement element) {
		String tagName="";
		try {		
			tagName = $$(element).getTagName();
			//logAction(new ActionLog(ABotActions.GetTagName,getElementLog(element,null),null,null));
			logAction(ActionLog.ActionLogWithReturnValue(ABotActions.GetTagName,getElementLog(element,null),null,null,tagName,null));
		} catch (Exception e){
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.GetTagName,getElementLog(element,e.getMessage()),null,e,null));
			throw e;
		}
		return tagName;
	}
	
	@Override
	public String GetAttribute(BaseWebElement element, String attributeName) {
		String attributeValue="";
		try {		
			attributeValue = $$(element).getAttribute(attributeName);
			//logAction(new ActionLog(ABotActions.GetAttribute,getElementLog(element,null),attributeName,null));
			String logMessage = "GetAttribute of {%s} return {"+attributeValue+"}";
			logAction(ActionLog.ActionLogWithReturnValue(ABotActions.GetAttribute,getElementLog(element,null),attributeName,null,attributeValue,logMessage));
		} catch (Exception e){
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.GetAttribute,getElementLog(element,e.getMessage()),null,e,null));
			throw e;
		}
		return attributeValue;
	}
	
	@Override
	public boolean IsSelected(BaseWebElement element) {
		String logMessage = "IsSelected on {%s} ";
		boolean isSelected;
		try {		
			isSelected = $$(element).isSelected();
			logMessage = logMessage.concat("returns "+Boolean.toString(isSelected));
			logAction(ActionLog.ActionLogWithReturnValue(ABotActions.IsSelected,getElementLog(element,null),null,null,Boolean.toString(isSelected),logMessage));
		} catch (Exception e){
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.IsSelected,getElementLog(element,e.getMessage()),null,e,logMessage));
			throw e;
		}
		return isSelected;
	}
	
	@Override
	public boolean IsEnabled(BaseWebElement element) {
		boolean isEnabled;
		try {		
			isEnabled = $$(element).isEnabled();	
			logAction(ActionLog.ActionLogWithReturnValue(ABotActions.IsEnabled,getElementLog(element,null),null,null,Boolean.toString(isEnabled),null));
		} catch (Exception e){
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.IsEnabled,getElementLog(element,e.getMessage()),null,e,null));
			throw e;
		}
		return isEnabled;
	}
	
	@Override
	public String GetText(BaseWebElement element) {
		String logMessage = "GetText of {%s} ";
		String getText="";
		try {		
			getText = $$(element).getText();
			logMessage=logMessage.concat("returns {"+getText+"}");
			logAction(ActionLog.ActionLogWithReturnValue(ABotActions.GetText,getElementLog(element,null),null,null,getText,logMessage));
		} catch (Exception e){
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.GetText,getElementLog(element,e.getMessage()),null,e,logMessage));
			throw e;
		}
		return getText;
	}
	
//	@Override
//	public EnhancedWebElement FindElement(BaseWebElement element,By by) {
//		WebElement returnElement = null;
//		try {		
//			
//			$$(element).findElement(by);
//			logAction(ActionLog.ActionLogWithReturnValue(ABotActions.FindElement,getElementLog(element,null),null,null,"ABotWebElement"));
//		} catch (Exception e){
//			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.FindElement,getElementLog(element,e.getMessage()),null,e));
//			throw e;
//		}
//		return getABotWebElement(returnElement);
//	}
	
	@Override
	public boolean IsDisplayed(BaseWebElement element) {
		boolean isDisplayed;
		try {		
			isDisplayed = $$(element).isDisplayed();	
			logAction(ActionLog.ActionLogWithReturnValue(ABotActions.IsDisplayed,getElementLog(element,null),null,null,Boolean.toString(isDisplayed),null));
		} catch (Exception e){
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.IsDisplayed,getElementLog(element,e.getMessage()),null,e,null));
			throw e;
		}
		return isDisplayed;
	}
	
	// TODO, check for null and default value
	@Override
	public Point GetLocation(BaseWebElement element) {
		Point getLocation =null;
		try {		
			getLocation = $$(element).getLocation();
			logAction(ActionLog.ActionLogWithReturnValue(ABotActions.GetLocation,getElementLog(element,null),null,null,getLocation.toString(),null)); 
		} catch (Exception e){
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.GetLocation,getElementLog(element,e.getMessage()),null,e,null));
			throw e;
		}
		return getLocation;
	}
	
	// TODO, check for null and default value
	@Override
	public Dimension GetSize(BaseWebElement element) {
		Dimension dimension =null;
		try {		
			dimension = $$(element).getSize();
			logAction(ActionLog.ActionLogWithReturnValue(ABotActions.GetSize,getElementLog(element,null),null,null,dimension.toString(),null)); 
		} catch (Exception e){
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.GetSize,getElementLog(element,e.getMessage()),null,e,null));
			throw e;
		}
		return dimension;
	}
	
	// TODO, check for null and default value
	@Override
	public Rectangle GetRect(BaseWebElement element) {
		Rectangle rectangle =null;
		try {		
			rectangle = $$(element).getRect();
			logAction(ActionLog.ActionLogWithReturnValue(ABotActions.GetRect,getElementLog(element,null),null,null,rectangle.toString(),null)); 
		} catch (Exception e){
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.GetRect,getElementLog(element,e.getMessage()),null,e,null));
			throw e;
		}
		return rectangle;
	}
	
	@Override
	public String GetCssValue(BaseWebElement element,String propertyName) {
		String cssValue="";
		try {		
			cssValue = $$(element).getCssValue(propertyName);
			logAction(ActionLog.ActionLogWithReturnValue(ABotActions.GetText,getElementLog(element,null),propertyName,null,cssValue,null));
		} catch (Exception e){
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.GetText,getElementLog(element,e.getMessage()),null,e,null));
			throw e;
		}
		return cssValue;
	}
	
//End of SeleniumWebElement.........................................................................................
	
	
// ABot defined actions ............................................................................................ 

	@Override
	public void Type(BaseWebElement element, CharSequence... textValue) {
		String typeValue = "";
		String delimeter = "";
		if(textValue.length>1)
			delimeter = "|";
		for(CharSequence c : textValue){
			typeValue = typeValue + String.valueOf(c) + delimeter;
		}
		
		try {
			WebElement elm = $$(element);
			elm.clear();
			elm.sendKeys(textValue);
			
			String logMessage = "Type {"+typeValue+"} in the {%s}";

			logAction(new ActionLog(ABotActions.Type,getElementLog(element,null),typeValue,logMessage, null));
		} catch (Exception e) {
			logAction(new ActionLog(ABotActions.Type,getElementLog(element,e.getMessage()),typeValue,null,e));
			throw e;
		}
	}
	
	
	@Override
	public void NativeClick(BaseWebElement element) {		
		try {
			if (element.asWebelement() != null) { // TOTO: what is the need for if ??
				//$$(element).click();
				element.asWebelement().click();				
			} else if (element.getByLocator() != null) {				
				$$(element).click();		
			}			
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.NativeClick,getElementLog(element,null),null,null,null));
		} catch (Exception e){
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.NativeClick,getElementLog(element,e.getMessage()),null,e,null));
			throw e;
		}
		
	}
	
	private void jsClick(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor)driver();
		js.executeScript("arguments[0].click();", element);
	}
	
	@Override
	public void Check(BaseWebElement element) {
		String logMessage = "Check on {%s} ";
		try {
			if(!$$(element).isSelected()) {
				$$(element).click();				
			}
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.Check,getElementLog(element,null),null,null,logMessage));
		}catch(Exception e) {
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.Check,getElementLog(element,e.getMessage()),null,e,logMessage));
			throw e;
		}
	}
	
	@Override
	public void UnCheck(BaseWebElement element) {
		
		try {
			if($$(element).isSelected()) {
				$$(element).click();				
			}
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.UnCheck,getElementLog(element,null),null,null,null));
		}catch(Exception e) {
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.UnCheck,getElementLog(element,e.getMessage()),null,e,null));
			throw e;
		}
	}
	
//	@Override
//	public ABotWebElement FindElement(BaseWebElement element, By by) {
//		WebElement returnElement = null;
//		try {
//			if (element.asWebelement() != null) {				
//				returnElement =  element.asWebelement().findElement(by);
//			} else if (element.asBy() != null) {				
//				returnElement =  findWebElement(element.asBy() );
//			}	
//			logAction(new ActionLog(ABotActions.FindElement,getElementLog(element,null),null,null));			
//		} catch (NoSuchElementException e) {			
//			logAction(new ActionLog(ABotActions.FindElement,getElementLog(element,e.getMessage()),null,e));
//			throw e;
//		} catch (Exception e) {			
//			logAction(new ActionLog(ABotActions.FindElement,getElementLog(element,e.getMessage()),null,e));
//			throw e;
//		}
//		return FindABotWebElement(returnElement);
//		//return returnElement;
//	}	

	private WebElement findAlternativeElementFromTargets(BaseWebElement baseElement) {
		if (null != baseElement && baseElement.getFieldName() != null) { // if element comes from page object then
			// If frame has defined under @LocateBy then only switch			
			if (baseElement.getEnhancedWebElementLocator().getTargets() != null
					&& baseElement.getEnhancedWebElementLocator().getTargets().length > 0) {
				int noOfSupportTargets = 5; //TODO :  this can be taken from config, for now lets support upto 5 targets
				if(baseElement.getEnhancedWebElementLocator().getTargets().length<5) {
					noOfSupportTargets = baseElement.getEnhancedWebElementLocator().getTargets().length;
				}
				
				for(int i=0 ; i<noOfSupportTargets ; i++) {
					String target=null;
					try {
						autoSwitchToGivenFrames(baseElement);
						target = baseElement.getEnhancedWebElementLocator().getTargets()[i];
						String[] locator = vlidateElementLocator(target);
						systemLogsWarn("Trying alternative target locator : "+target);					
						String key = locator[0];
						String value = locator[1];
						switch (key) {					
						case "name": return driver().findElement(By.name(value));
						case "id": return driver().findElement(By.id(value));
						case "xpath": return driver().findElement(By.xpath(value));
						case "className": return driver().findElement(By.className(value));
						case "css": return driver().findElement(By.cssSelector(value));
						case "linkText": return driver().findElement(By.linkText(value));
						case "partialLinkText": return driver().findElement(By.partialLinkText(value));
						case "tagName": return driver().findElement(By.tagName(value));
						default:
							break;
						}
					}catch(NoSuchElementException ex) {
						if(i==(noOfSupportTargets-1)) {
							throw ex;
						}
					}catch(InvalidLocatorException ex) {
						systemLogsWarn(ELEMENT_INVALID_LOCATOR_MSG + " ("+target+") defined in page object. Please correct ");
						if(i==(noOfSupportTargets-1)) {
							throw ex;
						}
					}
				}

			}
		}
		// TODO: generally this return should not reach. but is there better way to handle this
		return null;
	}
	@Override
	public WebElement $$(BaseWebElement baseElement) {
		// TODO
		try {
			return findWebElement(baseElement.getByLocator(),baseElement);
		} catch (NoSuchElementException e) {
			// Try alternative targets
			WebElement alternatieElm = findAlternativeElementFromTargets(baseElement);
			if(alternatieElm==null) {
				throw e;
			}
			return alternatieElm;
		}

		// this code should not reach handle it properly
		//return findWebElement(baseElement.getByLocator(),baseElement);
	}
	
	final static String ELEMENT_INVALID_LOCATOR_MSG = "Invalid element lcoator strategy";
	private String[] vlidateElementLocator(String elementLocator) {
		if(!elementLocator.contains("=")) {
			throw new SwitchToFrameException(ELEMENT_INVALID_LOCATOR_MSG + " ("+elementLocator+") defined in page object. Format must be locatorStrategy=locator (e.g. name=iframeName)");
		}
		String[] locator = elementLocator.split("=", 2);
		String key = locator[0].trim();
		String value = locator[1].trim();
		if(key.equals("")||value.equals("")) {
			throw new InvalidLocatorException(ELEMENT_INVALID_LOCATOR_MSG + " ("+elementLocator+") defined in page object. Locator cannot be empty");
		}
		if(! (key.equals("name") ||key.equals("id") || key.equals("xpath") || key.equals("className") || key.equals("css") || key.equals("linkText") || key.equals("partialLinkText") || key.equals("tagName"))) {		
			throw new InvalidLocatorException(ELEMENT_INVALID_LOCATOR_MSG + " ("+elementLocator+") defined in page object, frame locator strategy must be one of [name,id,xpath,index]");
		}
		
		String[] locatorAry = {key,value};
		return locatorAry;
	}
	
	final static String FRAME_INVALID_LOCATOR_MSG = "Invalid frame lcoator strategy";
	private String[] vlidateFrameLocator(String frameLocator) {
		if(!frameLocator.contains("=")) {
			throw new SwitchToFrameException(FRAME_INVALID_LOCATOR_MSG + " ("+frameLocator+") defined in page object. Format must be locatorStrategy=locator (e.g. name=iframeName)");
		}
		String[] locator = frameLocator.split("=", 2);
		String key = locator[0].trim();
		String value = locator[1].trim();
		if(key.equals("")||value.equals("")) {
			throw new SwitchToFrameException(FRAME_INVALID_LOCATOR_MSG + " ("+frameLocator+") defined in page object. Locator cannot be empty");
		}
		if(! (key.equals("name") ||key.equals("id") || key.equals("xpath") || key.equals("index"))) {		
			throw new SwitchToFrameException(FRAME_INVALID_LOCATOR_MSG + " ("+frameLocator+") defined in page object, frame locator strategy must be one of [name,id,xpath,index]");
		}
		
		String[] locatorAry = {key,value};
		return locatorAry;
	}
	
	/*
	 * this is use to switch to frames which user has defined in page objects under @LocateBy->frames
	 */
	private void switchToGivenFrames(String[] frames) {
		
		String currentFrame = "";
		try{
			driver().switchTo().defaultContent(); // Always starting to search from the default content
			for(String s : frames) {
				String[] locator = vlidateFrameLocator(s);
				String key = locator[0];
				String value = locator[1];
				currentFrame = s;	
				
				switch (key) {
					case "name":driver().switchTo().frame(value);					
						break;
					case "id":driver().switchTo().frame(value);	
						break;
					case "xpath":driver().switchTo().frame(driver().findElement(By.xpath(value)));
						break;
					case "index":driver().switchTo().frame(Integer.parseInt(value.trim()));	
					default:
						break;
				}
			}
		}catch (Exception e) {
			if(e.getClass().getSimpleName().contains("SwitchToFrameException")) {
				throw e;
			}
			throw new SwitchToFrameException("Finding associate frame("+currentFrame+") failed with " +  e.getClass().getSimpleName());
		}
	}
	
	private SearchContext getShadowRoot(BaseWebElement baseElement) {
		if(baseElement==null) {
			return null;
		}
		String[] shadowLocatores = baseElement.getEnhancedWebElementLocator().getShadowLocators();
		if(shadowLocatores!=null && shadowLocatores.length>0) {			
			String[] firstLocator = shadowLocatores[0].split("=");
			SearchContext searchContext = getSearchContextByLocaotrType(firstLocator[0],firstLocator[1]);
			for(int i=1; i<shadowLocatores.length; i++) {
				String lcoatorElm = shadowLocatores[i];
				String[] locator = lcoatorElm.split("=");
				String key = locator[0];
				String value = locator[1];
				searchContext = getSearchContextByLocaotrType(key,value,searchContext);
			}
			return searchContext;
		}
		return null;
	}
	
	private SearchContext getSearchContextByLocaotrType(String key, String value) {
		switch (key) {
		case "name":return driver().findElement(By.name(value)).getShadowRoot();		
		case "id":return driver().findElement(By.id(value)).getShadowRoot();	
		case "xpath": return driver().findElement(By.xpath(value)).getShadowRoot();
		case "cssSelector":return driver().findElement(By.cssSelector(value)).getShadowRoot();
		default:
			// TODO: handle when invalid locator
			break;
		}
		return null;
	}
	private SearchContext getSearchContextByLocaotrType(String key, String value,SearchContext parentSearchContext) {
		switch (key) {
		case "name":return parentSearchContext.findElement(By.name(value)).getShadowRoot();		
		case "id":return parentSearchContext.findElement(By.id(value)).getShadowRoot();	
		case "xpath": return parentSearchContext.findElement(By.xpath(value)).getShadowRoot();
		case "cssSelector":return parentSearchContext.findElement(By.cssSelector(value)).getShadowRoot();
		default:
			// TODO: handle when invalid locator
			break;
		}
		return null;
	}
	
	private void autoSwitchToGivenFrames(BaseWebElement baseElement) {
		boolean enableAutoSwitch = true; // TODO : should be change by config file
		if(enableAutoSwitch) {
			switchToFrames(baseElement);
		}
	}
	
	/** 
	 * @param seleniumSelector
	 * @return WebElement
	 * this will find element using selenium driver
	 */
	private WebElement findWebElement(By seleniumSelector,BaseWebElement baseElement) {
		// TODO : should be change by config file
		final int noOfTries = 5; 
		try {
			for(int i=1 ; i<=noOfTries ; i++) {
				try {
					SearchContext shadowSearchContext =  getShadowRoot(baseElement);
					if(shadowSearchContext!=null) {
						return shadowSearchContext.findElement(seleniumSelector);
					}
					autoSwitchToGivenFrames(baseElement); // null baseElement will handle inside the method
					return driver().findElement(seleniumSelector);
				}
				catch(StaleElementReferenceException e) {
					systemLogsWarn("[Try-"+i+" : "+seleniumSelector+"] " + "Finding element failed with StaleElementReferenceException" );
					if(i==noOfTries) {
						throw e;
					}
				}
				catch (NoSuchElementException e) {					
					systemLogsWarn("[Try-"+i+" : "+seleniumSelector+"] " + "Finding element failed with NoSuchElementException");
					if(i==noOfTries) {
						throw e;
					}
				}catch (SwitchToFrameException e) {
					/*
					 * if locator is wrong no point of try on finding element
					 */
					if(e.getMessage().contains(FRAME_INVALID_LOCATOR_MSG)) {
						throw e;
					}
					systemLogsWarn("[Try-"+i+" : "+seleniumSelector+"] " + e.getMessage() );
					if(i==noOfTries) {
						throw e;
					}
				}
				/*
				 * didn't catch general Exception means any exception other than NoSuchElementException, StaleElementReferenceException, SwitchToFrameException, this this should fail without try
				 * how ever there can be other possibilities that can capture and try finding element
				 */
				
			}
		}catch (Exception e) {
			throw e;
		}

		
		/*
		 * there is no possibility to reach below return
		 * 
		 */		
		return driver().findElement(seleniumSelector);
	}	
	
	private void getAllIframes() {
		final List<WebElement> iframes = driver().findElements(By.tagName("iframe"));
	}

	
	@Override
	public BaseWebElement $(WebElement webElement) {
		BaseWebElement baseElement = new BaseWebElementImpl(webElement, null, null);
		return baseElement;
	}
	
	/**
	 * This will first invoke findElement, hence this should only look for present elements
	 * @param By locator of the element
	 * @return BaseWebElement
	 * @throws NoSuchElementException If no matching elements are found
	 */
	@Override
	public BaseWebElement $(By seleniumSelector) {
		BaseWebElement baseElement = new BaseWebElementImpl(findWebElement(seleniumSelector,null), seleniumSelector, null);
		return baseElement;
	}
	
	
	public <T> void WaitUntil(ExpectedCondition<T> condition,long timeOutInSeconds){
		String msg = "WaitUntil("+ timeOutInSeconds + "s) -> ";
		
		try {
			//WebDriverWait wait = new WebDriverWait(driver(), timeOutInSeconds);
			WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(timeOutInSeconds));
			wait.until(condition);	
			msg = condition.toString();
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.WaitUntil,msg,null));		
		}catch(Exception ex) {			
			msg = condition.toString();
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.WaitUntil,msg,ex));	
			throw ex;
		}


	}
	
	
	
	
//	public void Find(WebElement webElement) {
//		
//		//return new Element(webElement, null);
//		//element.findElement
//		///return driver.findElement(seleniumSelector);
//	}

	public String GetUrl() {
		String url = null;
		url = driver().getCurrentUrl();
		//getActionsLoggerMsg("GoTo",url);
		return url;
	}

	public String GetTitle() {
		String title = null;
		title = driver().getTitle();
		String logMessage = "returns "+title;
		logAction(ActionLog.actionLogWithDirectMesage(ABotActions.GetTitle,logMessage,null,title));	
		return title;
	}

	public String GetPageSource() {
		String pageSource = null;
		pageSource = driver().getPageSource();
		//getActionsLoggerMsg("GetPageSource",pageSource);
		return pageSource;
	}

	public void CloseBrowserWindow() {
		driver().close();
		logAction(ActionLog.actionLogWithDirectMesage(ABotActions.CloseBrowserTab, TestInitializr.getDesiredCapabilities().getBrowserName(), null));
	}

	public void CloseBrowser() {	
		try {
			driver().quit();
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.CloseBrowser, TestInitializr.getDesiredCapabilities().getBrowserName(), null));
		}catch(Exception ex2) {
			// firefox gives exception when try to quite browser even a session id is exist after close the last tab
		}		
	}

//	// WebDriver Navigation Actions
//	public void Back() {
//		driver.navigate().back();
//	}
	
	@Override
	public String CurrentWindowHandle() {
		String handle = driver().getWindowHandle();
		//getActionsLoggerMsg("CurrentWindowHandle",handle);
		return handle;
	}
	
	@Override
	public Set<String> AllWindowHandles() {
		Set<String> handles = driver().getWindowHandles();
		//getActionsLoggerMsg("AllWindowHandles",handles.toString());
		return handles;
	}
	
	@Override
	public String ParentWindowHandle() {		
		return TestInitializr.getParentWindowHandle();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	// -------------------------------------

	private static String getLastRunningPageObject() {
		String lastPageObject = null;
		if (LAST_RUNNING_PAGE_OBJECT != null) {
			String s = LAST_RUNNING_PAGE_OBJECT.toString();
			lastPageObject = s.substring(s.lastIndexOf(".") + 1).trim();

			System.out.println(LAST_RUNNING_PAGE_OBJECT.getAnnotations());
			// System.out.println(LAST_RUNNING_PAGE_OBJECT.getDeclaringClass());
			// System.out.println(LAST_RUNNING_PAGE_OBJECT.getSimpleName());
			// System.out.println(LAST_RUNNING_PAGE_OBJECT.getCanonicalName());
			// System.out.println(LAST_RUNNING_PAGE_OBJECT.getDeclaredClasses());
		}

		return lastPageObject;
	}

//	public TextImpl Text(WebElement webElement) {		
//		return ElementFactory.getTextActions(webElement, null);
//	}


	
//	public DropDown DropDown(WebElement webElement) {
//		return ElementFactory.getDropDown(webElement, null);
//	}
	
//	@Override
//	public ListBox ListBox(WebElement webElement) {
//		return ElementFactory.getListBox(webElement, null);
//	}
//	
//	@Override
//	public Element Element(WebElement webElement) {
//		return ElementFactory.getElement(webElement, null);
//	}

	public void Log(String loggerMessage) {
		userLogs(loggerMessage);		
	}

//	public Text FindText(By locator) {		
//		AvtomatElementFactory elementFactory = new AvtomatElementFactoryImpl();
//		return elementFactory.create(Text.class, $$(locator), locator, locator.toString());
//	}

//	@Override
//	public Button FindButton(By locator) {
//		AvtomatElementFactory elementFactory = new AvtomatElementFactoryImpl();
//		return elementFactory.create(Button.class, $$(locator), locator, locator.toString());
//	}


	/*
	 * we find element using locator only cannot do the switching to frame or window handles
	 */
	@Override
	public EnhancedWebElement FindEnhancedWebElement(By locator) {
		AvtomatElementFactory elementFactory = new AvtomatElementFactoryImpl();
		return elementFactory.create(EnhancedWebElement.class, findWebElement(locator,null), locator, null);
	}
	

//	private EnhancedWebElement getABotWebElement(WebElement webElement) {
//		AvtomatElementFactory elementFactory = new AvtomatElementFactoryImpl();
//		return elementFactory.create(EnhancedWebElement.class, webElement, null, null);
//	}
	
	@Override
	public ComboBox ToComboBox(BaseWebElement element) {	    
		try {
			WebElement webElement = $$(element);
			String tagName = webElement.getTagName();

		    if (null == tagName || !"select".equals(tagName.toLowerCase())) {		      
		      throw new UnexpectedWebElementException("select", tagName);
		    }
		    /*
		     * ToCombobox() also need to return the same element 
		     */
			return new ComboBoxImpl(element);
		}catch(Exception e ) {
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToCombobox,getElementLog(element,e.getMessage()),null,e,null));
			throw e;
		}
	}
	
	@Override
	public ExpectedConditions WaitUntil(BaseWebElement element, long timeOutInSeconds) { 
		return new ExpectedConditionsImpl(element,timeOutInSeconds);
	}
	
	
	@Override
	public BrowserNavigation BrowserNavigate() {
		return new BrowserNavigationImpl(driver());
	}
	
	@Override
	public TargetLocatorFrame SwitchTo() {
		return new TargetLocatorImpl(driver());
	}
	
	@Override
	public void SwitchToWindowOrTab(String windowHandle) {
		String logMessage = "Switch To Window Or Tab by handle " + windowHandle;
		driver().switchTo().window(windowHandle);
		logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.SwitchToWindowOrTab,logMessage,null));
	}
	
	/*
	 * switch to given iframes of the element in the page object
	 */
	public void switchToFrames(BaseWebElement baseElement) {
			if(null!=baseElement && baseElement.getFieldName()!=null) { // if element comes from page object then only switch
				// If frame has defined under @LocateBy then only switch
				if(baseElement.getEnhancedWebElementLocator().getFrames()!=null && baseElement.getEnhancedWebElementLocator().getFrames().length>0) {
					switchToGivenFrames(baseElement.getEnhancedWebElementLocator().getFrames());
				}else {					
					// if element comes from page object and @LocateBy is there without frames defined then switch to default content
					if(baseElement.getEnhancedWebElementLocator().isLocateByExist()) {
						driver().switchTo().defaultContent();
					}					
				}				
			}
			//TODO : Add logs, specially when iframes not available under element
	}
	
	@Override
	public FrameElement ToFrameElement(BaseWebElement element) {
		try {
			return new FrameElementImpl(element,driver());
		}catch(Exception e ) {
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToFrameElement,getElementLog(element,e.getMessage()),null,e,null));
			throw e;
		}
	}
	@Override
	public void HandleBrowserWindow(BrowserWindow browserWindow, long timeOutInSeconds) {

		try {
			ExpectedCondition<Boolean> mutipleWindowsToPresent = new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver input) {
					return AllWindowHandles().size() > 1;
				}
			};
			//WebDriverWait wait = new WebDriverWait(driver(), timeOutInSeconds);
			WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(timeOutInSeconds));
			wait.until(mutipleWindowsToPresent);
		} catch (TimeoutException e) {			
			String errMsg = "Timed out after "+timeOutInSeconds + " seconds waiting for a child window(popup,tab..) to be present";
			TimeOutException temuoutExp = new TimeOutException(errMsg);
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.HandleBrowserWindow,errMsg,temuoutExp));
			
			throw temuoutExp;
		}
		String parentWindow = CurrentWindowHandle();
		String parentWindowTitle = driver().getTitle();

		Set<String> s1 = AllWindowHandles();

		Iterator<String> i1 = s1.iterator();
		String childWindow = null;
		while (i1.hasNext()) {
			childWindow = i1.next();
			if (!childWindow.equalsIgnoreCase(parentWindow)) {
				break;
			}
		}
		driver().switchTo().window(childWindow);
		String childWindowTitle = driver().getTitle();
		String msg1 = "Switched to [" + childWindowTitle + "] from [" + parentWindowTitle + "]";
		logAction(ActionLog.actionLogWithDirectMesage(ABotActions.HandleBrowserWindow,msg1,null));		
		browserWindow.handle();
		driver().switchTo().window(parentWindow);
		String msg2 = "Switched back to parent window [" + parentWindowTitle + "]";
		logAction(ActionLog.actionLogWithDirectMesage(ABotActions.HandleBrowserWindow,msg2,null));
	}
	
	@Override
	public DriverTimeOuts ManageTimeouts() {		
		return new DriverTimeOutsImpl(driver());
	}
	@Override
	public org.openqa.selenium.Alert Alert() {
		return new AlertImpl(driver());
	}
	@Override
	public void DragAndDropTo(BaseWebElement fromElement,BaseWebElement toElement) {
		String msg = fromElement.getFieldName() + "->" + toElement.getFieldName(); // this can be null if not used from page object, need to handle this
		try {
			WebElement from_element = $$(fromElement); // this will invoke kandy find element strategy
			WebElement to_element = $$(toElement);
			
			Actions builder = new Actions(driver());
			Action dragAndDrop = builder.clickAndHold(from_element)
			.moveToElement(to_element)
			.release(to_element)
			.build();
			dragAndDrop.perform();			
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.DragAndDropTo,msg,null));
		}catch (Exception ex) {
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.DragAndDropTo,msg,ex));	
			throw ex;
		}
	}
	@Override
	public int getElementCount(BaseWebElement element) {
		int elementCount = 0;
		try {		
			switchToFrames(element);
			elementCount = driver().findElements(element.getByLocator()).size();
			logAction(ActionLog.ActionLogWithReturnValue(ABotActions.GetElementCount,getElementLog(element,null),null,null,String.valueOf(elementCount),null));
		}catch (NoSuchElementException e) {
			elementCount = 0;
		}
		catch (Exception e){
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.GetElementCount,getElementLog(element,e.getMessage()),null,e,null));
			throw e;
		}
		return elementCount;
	}
	


}
