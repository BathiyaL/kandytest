package com.ktsapi.pagefactory;

import java.lang.reflect.Field;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import com.ktsapi.annotation.LocateBy;
import com.ktsapi.elements.BaseWebElement;


public class AvtomatSeleniumDecorator extends DefaultFieldDecorator {

	private AvtomatElementFactory elementFactory = new AvtomatElementFactoryImpl();

	public AvtomatSeleniumDecorator(SearchContext context) {
		super(new DefaultElementLocatorFactory(context));
	}

	@Override
	public Object decorate(ClassLoader classLoader, Field field) {		
		ElementLocator locator = factory.createLocator(field);		
		WebElement wrappedElement = proxyForLocator(classLoader, locator);
		if (BaseWebElement.class.isAssignableFrom(field.getType())) {
			return decorateElement(field, wrappedElement);
		}

		return super.decorate(classLoader, field);
	}

	/**
	 * @param field (e.g. Text.class interface this is the type of the element in PageObject)
	 * @param wrappedElement
	 * @return Element implement class
	 * 		   e.g : for Text type return TextImpl object
	 */
	private Object decorateElement(final Field field, final WebElement wrappedElement) {		
		By byLocator = getByLocator(field);
		String fieldName = field.getDeclaringClass() + "."+ field.getName();
		EnhancedWebElementLocator locator = new EnhancedWebElementLocator();
		locator.setTarget(byLocator);
		locator.setFieldName(fieldName);
				
		LocateBy lBy = field.getAnnotation(LocateBy.class);
		if(null != lBy) {
			locator.setWindowHandles(lBy.windowHandles());
			locator.setFrames(lBy.frames());
			locator.setTargets(lBy.targets());	
			locator.setLocateByExist(true);
			locator.setShadowLocators(lBy.shadowLocators());
		}else {
			locator.setLocateByExist(false);
		}

		BaseWebElement element = elementFactory.create((Class<? extends BaseWebElement>) field.getType(), wrappedElement,locator);
		return element;
	}
	
	
	
	/**
	 * @param field
	 * @return By locator of the field
	 * TODO : when there is no FindBy annotaion defined for field this return field name as by locator this need to check
	 */
	private By getByLocator(Field field){
		By by;
		Annotations annotations = new Annotations(field);
		boolean shouldCache = annotations.isLookupCached();
		by = annotations.buildBy();
		return by;
	}

}
