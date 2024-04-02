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
import com.ktsapi.core.Const;
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

	public WebDriver WebDriver(){			
		return TestInitializr.getWebDriver();
	}
	
	@Override
	public void openBrowser() {
		String launchedBrowser = TestInitializr.getDesiredCapabilities().getBrowserName();
		try {
			WebDriver();
			launchedBrowser = launchedBrowser + " " + TestInitializr.getRunningBrowserVersion();
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.OpenBrowser, launchedBrowser, null));		
		} catch (Exception e) {
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.OpenBrowser, launchedBrowser, e));		
			throw e;
		}
	}

	@Override
	public String goTo(String url) {
		String logMessage = "GoTo "+url;
		String logMessage2 = " " + url;
		String windowHandle = null;
		try {
			WebDriver().get(url);
			windowHandle =  WebDriver().getWindowHandle();
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.GoTo,logMessage,null));	
		}catch(Exception e) {			
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.GoTo,logMessage2,e));
			throw e;
		}	
		return windowHandle;
	}

	@Override
	public String getNewWindow(String url) {
		
		String logMessage = "GetNewWindow "+url;
		String logMessage2 = " " + url;
		String windowHandle = null;
		try {
			WebDriver().switchTo().newWindow(WindowType.WINDOW);
			WebDriver().get(url);
			windowHandle =  WebDriver().getWindowHandle();	
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.GetNewWindow,logMessage,null));	
		}catch(Exception e) {			
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.GetNewWindow,logMessage2,e));
			throw e;
		}
		return windowHandle;
	}
	
	@Override
	public String getNewTab(String url) {
		
		String logMessage = "GetNewTab "+url;
		String logMessage2 = " " + url;
		String windowHandle = null;
		try {
			WebDriver().switchTo().newWindow(WindowType.TAB);
			WebDriver().get(url);
			windowHandle =  WebDriver().getWindowHandle();
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.GetNewTab,logMessage,null));	
		}catch(Exception e) {			
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.GetNewTab,logMessage2,e));
			throw e;
		}
		return windowHandle;
	}

	// @Override
	public <C> C getWebPage(Class<C> page) {
		return (C) AvtomatPageFactory.getWebPage(page);
	}

	
//SeleniumWebElement.........................................................................................
	private final String EXPECTED_EROR_MESSAGE_WHEN_ELEMENT_NOT_CICKABLE = "is not clickable at point";
	@Override
	public void click(BaseWebElement element) {
		String logMessage = "Click on {%s}";
		try {
			final int noOfTries = getFindElementTries();
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
	public void sendKeys(BaseWebElement element, CharSequence... keysToSend){	
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
	public void clear(BaseWebElement element) {
		try {			
			$$(element).clear();
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.Clear,getElementLog(element,null),null,null,null));
		} catch (Exception e){
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.Clear,getElementLog(element,e.getMessage()),null,e,null));
			throw e;
		}		
	}
	
	@Override
	public void submit(BaseWebElement element) {
		try {		
			$$(element).submit();
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.Submit,getElementLog(element,null),null,null,null));
		} catch (Exception e){
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.Submit,getElementLog(element,e.getMessage()),null,e,null));
			throw e;
		}		
	}
	
	@Override
	public String getTagName(BaseWebElement element) {
		String tagName="";
		try {		
			tagName = $$(element).getTagName();
			logAction(ActionLog.ActionLogWithReturnValue(ABotActions.GetTagName,getElementLog(element,null),null,null,tagName,null));
		} catch (Exception e){
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.GetTagName,getElementLog(element,e.getMessage()),null,e,null));
			throw e;
		}
		return tagName;
	}
	
	@Override
	public String getAttribute(BaseWebElement element, String attributeName) {
		String attributeValue="";
		try {		
			attributeValue = $$(element).getAttribute(attributeName);
			String logMessage = "GetAttribute of {%s} return {"+attributeValue+"}";
			logAction(ActionLog.ActionLogWithReturnValue(ABotActions.GetAttribute,getElementLog(element,null),attributeName,null,attributeValue,logMessage));
		} catch (Exception e){
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.GetAttribute,getElementLog(element,e.getMessage()),null,e,null));
			throw e;
		}
		return attributeValue;
	}
	
	@Override
	public boolean isSelected(BaseWebElement element) {
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
	public boolean isEnabled(BaseWebElement element) {
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
	public String getText(BaseWebElement element) {
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
	
	@Override
	public boolean isDisplayed(BaseWebElement element) {
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
	public Point getLocation(BaseWebElement element) {
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
	public Dimension getSize(BaseWebElement element) {
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
	public Rectangle getRect(BaseWebElement element) {
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
	public String getCssValue(BaseWebElement element,String propertyName) {
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
	
	
//Kandy framework defined actions ............................................................................................ 
	
	@Override
	public void type(BaseWebElement element, CharSequence... textValue) {
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
	public void nativeClick(BaseWebElement element) {		
		try {
			if (element.asWebelement() != null) { // TOTO: what is the need for if ??
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
		JavascriptExecutor js = (JavascriptExecutor)WebDriver();
		js.executeScript("arguments[0].click();", element);
	}
	
	@Override
	public void check(BaseWebElement element) {
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
	public void unCheck(BaseWebElement element) {
		
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
						case "name": return WebDriver().findElement(By.name(value));
						case "id": return WebDriver().findElement(By.id(value));
						case "xpath": return WebDriver().findElement(By.xpath(value));
						case "className": return WebDriver().findElement(By.className(value));
						case "css": return WebDriver().findElement(By.cssSelector(value));
						case "linkText": return WebDriver().findElement(By.linkText(value));
						case "partialLinkText": return WebDriver().findElement(By.partialLinkText(value));
						case "tagName": return WebDriver().findElement(By.tagName(value));
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
		return null;
	}
	@Override
	public WebElement $$(BaseWebElement baseElement) {
		try {
			return findWebElement(baseElement.getByLocator(),baseElement);
		} catch (NoSuchElementException e) {
			// ToDo : Try alternative targets
			WebElement alternatieElm = findAlternativeElementFromTargets(baseElement);
			if(alternatieElm==null) {
				throw e;
			}
			return alternatieElm;
		}
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
			WebDriver().switchTo().defaultContent(); // Always starting to search from the default content
			for(String s : frames) {
				String[] locator = vlidateFrameLocator(s);
				String key = locator[0];
				String value = locator[1];
				currentFrame = s;	
				
				switch (key) {
					case "name":WebDriver().switchTo().frame(value);					
						break;
					case "id":WebDriver().switchTo().frame(value);	
						break;
					case "xpath":WebDriver().switchTo().frame(WebDriver().findElement(By.xpath(value)));
						break;
					case "index":WebDriver().switchTo().frame(Integer.parseInt(value.trim()));	
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
	
	/*
	 * NOTE: currently there is a limitation in selenium that locators other than css not working in nested shadow dom
	 */
	private SearchContext getShadowRoot(BaseWebElement baseElement) {
		if(baseElement==null) {
			return null;
		}
		String[] shadowLocatores = baseElement.getEnhancedWebElementLocator().getShadowLocators();
		if(shadowLocatores!=null && shadowLocatores.length>0) {			
			String[] firstLocator = vlidateElementLocator(shadowLocatores[0]);
			SearchContext searchContext = getSearchContextByLocaotrType(firstLocator[0],firstLocator[1]);
			for(int i=1; i<shadowLocatores.length; i++) {
				String[] locator = vlidateElementLocator(shadowLocatores[i]);
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
		case "name":return WebDriver().findElement(By.name(value)).getShadowRoot();		
		case "id":return WebDriver().findElement(By.id(value)).getShadowRoot();	
		case "xpath": return WebDriver().findElement(By.xpath(value)).getShadowRoot();
		case "css":return WebDriver().findElement(By.cssSelector(value)).getShadowRoot();
		default:
			throw new InvalidLocatorException(ELEMENT_INVALID_LOCATOR_MSG + " ("+key+") defined in page object, shadowLocators strategy must be one of [name,id,xpath,css]");
		}
	}
	private SearchContext getSearchContextByLocaotrType(String key, String value,SearchContext parentSearchContext) {
		switch (key) {
		case "name":return parentSearchContext.findElement(By.name(value)).getShadowRoot();		
		case "id":return parentSearchContext.findElement(By.id(value)).getShadowRoot();	
		case "xpath": return parentSearchContext.findElement(By.xpath(value)).getShadowRoot();
		case "css":return parentSearchContext.findElement(By.cssSelector(value)).getShadowRoot();
		default:
			throw new InvalidLocatorException(ELEMENT_INVALID_LOCATOR_MSG + " ("+key+") defined in page object, shadowLocators strategy must be one of [name,id,xpath,css]");
		}
	}
	
	private void autoSwitchToGivenFrames(BaseWebElement baseElement) {
		boolean enableAutoSwitch = TestInitializr.getTestConfigObj().getWebDrivers().isEnabledAutoFrameSwitch();
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
		final int noOfTries = getFindElementTries(); 
		try {
			for(int i=1 ; i<=noOfTries ; i++) {
				try {
					autoSwitchToGivenFrames(baseElement); // null baseElement will handle inside the method
					SearchContext shadowSearchContext =  getShadowRoot(baseElement); // limitation:Iframes inside shadow dom is not supported
					if(shadowSearchContext!=null) {
						return shadowSearchContext.findElement(seleniumSelector);
					}
					return WebDriver().findElement(seleniumSelector);
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
		return WebDriver().findElement(seleniumSelector);
	}	
	
	private void getAllIframes() {
		final List<WebElement> iframes = WebDriver().findElements(By.tagName("iframe"));
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
	
	
	public <T> void waitUntil(ExpectedCondition<T> condition,long timeOutInSeconds){
		String msg = "WaitUntil("+ timeOutInSeconds + "s) -> ";
		
		try {
			WebDriverWait wait = new WebDriverWait(WebDriver(), Duration.ofSeconds(timeOutInSeconds));
			wait.until(condition);	
			msg = condition.toString();
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.WaitUntil,msg,null));		
		}catch(Exception ex) {			
			msg = condition.toString();
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.WaitUntil,msg,ex));	
			throw ex;
		}
	}

	public String getUrl() {
		String url = null;
		url = WebDriver().getCurrentUrl();
		return url;
	}

	public String getTitle() {
		String title = null;
		title = WebDriver().getTitle();
		String logMessage = "returns "+title;
		logAction(ActionLog.actionLogWithDirectMesage(ABotActions.GetTitle,logMessage,null,title));	
		return title;
	}

	public String getPageSource() {
		String pageSource = null;
		pageSource = WebDriver().getPageSource();
		return pageSource;
	}

	public void closeBrowserWindow() {
		WebDriver().close();
		logAction(ActionLog.actionLogWithDirectMesage(ABotActions.CloseBrowserTab, TestInitializr.getDesiredCapabilities().getBrowserName(), null));
	}

	public void closeBrowser() {	
		try {
			WebDriver().quit();
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.CloseBrowser, TestInitializr.getDesiredCapabilities().getBrowserName(), null));
		}catch(Exception ex2) {
			// firefox gives exception when try to quite browser even a session id is exist after close the last tab
		}		
	}

	@Override
	public String currentWindowHandle() {
		String handle = WebDriver().getWindowHandle();
		return handle;
	}
	
	@Override
	public Set<String> allWindowHandles() {
		Set<String> handles = WebDriver().getWindowHandles();
		return handles;
	}
	
	@Override
	public String parentWindowHandle() {		
		return TestInitializr.getParentWindowHandle();
	}

	
	// -------------------------------------

	public void log(String loggerMessage) {
		userLogs(loggerMessage);		
	}

	/*
	 * we find element using locator only cannot do the switching to frame or window handles
	 */
	@Override
	public EnhancedWebElement findEnhancedWebElement(By locator) {
		AvtomatElementFactory elementFactory = new AvtomatElementFactoryImpl();
		return elementFactory.create(EnhancedWebElement.class, findWebElement(locator,null), locator, null);
	}

	@Override
	public ComboBox toComboBox(BaseWebElement element) {	    
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
	public ExpectedConditions waitUntil(BaseWebElement element, long timeOutInSeconds) { 
		return new ExpectedConditionsImpl(element,timeOutInSeconds);
	}
	
	
	@Override
	public BrowserNavigation browserNavigate() {
		return new BrowserNavigationImpl(WebDriver());
	}
	
	@Override
	public TargetLocatorFrame switchTo() {
		return new TargetLocatorImpl(WebDriver());
	}
	
	@Override
	public void switchToWindowOrTab(String windowHandle) {
		String logMessage = "Switch To Window Or Tab by handle " + windowHandle;
		WebDriver().switchTo().window(windowHandle);
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
						WebDriver().switchTo().defaultContent();
					}					
				}				
			}
			//TODO : Add logs, specially when iframes not available under element
	}
	
	@Override
	public FrameElement toFrameElement(BaseWebElement element) {
		try {
			return new FrameElementImpl(element,WebDriver());
		}catch(Exception e ) {
			logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToFrameElement,getElementLog(element,e.getMessage()),null,e,null));
			throw e;
		}
	}
	@Override
	public void handleBrowserWindow(BrowserWindow browserWindow, long timeOutInSeconds) {

		try {
			ExpectedCondition<Boolean> mutipleWindowsToPresent = new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver input) {
					return allWindowHandles().size() > 1;
				}
			};
			WebDriverWait wait = new WebDriverWait(WebDriver(), Duration.ofSeconds(timeOutInSeconds));
			wait.until(mutipleWindowsToPresent);
		} catch (TimeoutException e) {			
			String errMsg = "Timed out after "+timeOutInSeconds + " seconds waiting for a child window(popup,tab..) to be present";
			TimeOutException temuoutExp = new TimeOutException(errMsg);
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.HandleBrowserWindow,errMsg,temuoutExp));
			
			throw temuoutExp;
		}
		String parentWindow = currentWindowHandle();
		String parentWindowTitle = WebDriver().getTitle();

		Set<String> s1 = allWindowHandles();

		Iterator<String> i1 = s1.iterator();
		String childWindow = null;
		while (i1.hasNext()) {
			childWindow = i1.next();
			if (!childWindow.equalsIgnoreCase(parentWindow)) {
				break;
			}
		}
		WebDriver().switchTo().window(childWindow);
		String childWindowTitle = WebDriver().getTitle();
		String msg1 = "Switched to [" + childWindowTitle + "] from [" + parentWindowTitle + "]";
		logAction(ActionLog.actionLogWithDirectMesage(ABotActions.HandleBrowserWindow,msg1,null));		
		browserWindow.handle();
		WebDriver().switchTo().window(parentWindow);
		String msg2 = "Switched back to parent window [" + parentWindowTitle + "]";
		logAction(ActionLog.actionLogWithDirectMesage(ABotActions.HandleBrowserWindow,msg2,null));
	}
	
	@Override
	public DriverTimeOuts manageTimeouts() {		
		return new DriverTimeOutsImpl(WebDriver());
	}
	@Override
	public org.openqa.selenium.Alert alert() {
		return new AlertImpl(WebDriver());
	}
	@Override
	public void dragAndDropTo(BaseWebElement fromElement,BaseWebElement toElement) {
		String msg = fromElement.getFieldName() + "->" + toElement.getFieldName(); // this can be null if not used from page object, need to handle this
		try {
			WebElement from_element = $$(fromElement); // this will invoke kandy find element strategy
			WebElement to_element = $$(toElement);
			
			Actions builder = new Actions(WebDriver());
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
			elementCount = WebDriver().findElements(element.getByLocator()).size();
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
	

	private int getFindElementTries() {
		int tries = TestInitializr.getTestConfigObj().getWebDrivers().getFindElementTries();
		if(tries==0) return Const.Default_Webdriver_FindElement_Tries;
		
		return tries;
	}

}
