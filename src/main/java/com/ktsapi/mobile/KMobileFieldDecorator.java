package com.ktsapi.mobile;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactoryFinder;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import com.ktsapi.annotation.LocateBy;
import com.ktsapi.elements.BaseWebElement;
import com.ktsapi.pagefactory.AvtomatElementFactory;
import com.ktsapi.pagefactory.AvtomatElementFactoryImpl;
import com.ktsapi.pagefactory.EnhancedWebElementLocator;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class KMobileFieldDecorator extends DefaultFieldDecorator{
	
	//DefaultElementLocatorFactory defaultElementLocatorFactory;
	AppiumFieldDecorator appiumFieldDecorator;
	public KMobileFieldDecorator(SearchContext context) {

		//super(context);
		//defaultElementLocatorFactory = new DefaultElementLocatorFactory(context);
		// TODO Auto-generated constructor stub

		super(new DefaultElementLocatorFactory(context));
		appiumFieldDecorator = new AppiumFieldDecorator(context);
	}
		

	//private AvtomatElementFactory elementFactory = new AvtomatElementFactoryImpl();
	private KMobileElementFactoryImpl elementFactory = new KMobileElementFactoryImpl();
	
	@Override
	public Object decorate(ClassLoader classLoader, Field field) {		
		ElementLocator locator = factory.createLocator(field);		
		WebElement wrappedElement = proxyForLocator(classLoader, locator);
		if (BaseMobileElement.class.isAssignableFrom(field.getType())) {
			try {
				return decorateElement(field, wrappedElement);
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//System.out.println(">>>>>>>>>>>>>1 :: " + field.toString());
		//System.out.println(">>>>>>>>>>>>>2 :: " + locator.toString());
//		Object obj = super.decorate(classLoader, field);
//		System.out.println(">>>>>>>>>>>>> :: " + obj.);
		return appiumFieldDecorator.decorate(classLoader, field);
	}

	/**
	 * @param field (e.g. Text.class interface this is the type of the element in PageObject)
	 * @param wrappedElement
	 * @return Element implement class
	 * 		   e.g : for Text type return TextImpl object  
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	private Object decorateElement(final Field field, final WebElement wrappedElement) throws NoSuchMethodException, SecurityException {		
		By byLocator = getByLocator(field);
		
//		System.out.println("Field Name : " + field.getDeclaringClass().getSimpleName() + "."+ field.getName() );
		String fieldName = field.getDeclaringClass() + "."+ field.getName();
//		System.out.println(">>>>>>>>>>>>>fieldName :: " + fieldName);
//		AndroidFindBy androidFindBy = field.getAnnotation(AndroidFindBy.class);
		
//		System.out.println(">>>>>>>>>>>>>androidFindBy.id :: " + androidFindBy.id());
//		System.out.println(">>>>>>>>>>>>>androidFindBy.xpath :: " + androidFindBy.xpath());
//		System.out.println(">>>>>>>>>>>>>androidFindBy.uiAutomator :: " + androidFindBy.uiAutomator());
//		// get all delcated methods of AndroidFindBy from refelction and chekc they whter they are empty or not
//		for(Annotation annotation : field.getDeclaredAnnotations() ) {
//			System.out.println(">>>>>>>>>>>>>annotation :: " + annotation.annotationType().getName()); // io.appium.java_client.pagefactory.AndroidFindBy
//			System.out.println(">>>>>>>>>>>>>annotation.getDeclaredConstructor :: " + annotation.toString()); // AndroidFindBy(xpath="", accessibility="", androidDataMatcher="", androidViewMatcher="", uiAutomator="com.androidsample.generalstore:id/nameField", className="", id="", priority=0, tagName="")
//			System.out.println(">>>>>>>>>>>>>annotation.getAnnotations() :: " + annotation.annotationType().getAnnotations().length);
//			System.out.println(">>>>>>>>>>>>>annotation.getAnnotations() :: " + annotation.annotationType().getAnnotations()[0].toString());
//			System.out.println(">>>>>>>>>>>>>annotation.getAnnotations() :: " + annotation.annotationType().getAnnotations()[1].toString());
//			System.out.println(">>>>>>>>>>>>>annotation.getAnnotations() :: " + annotation.annotationType().getAnnotations()[2].toString());
//		}
		
		EnhancedMobileElementLocator locator = new EnhancedMobileElementLocator();
		locator.setTarget(byLocator);
		locator.setFieldName(fieldName);
//				
//		LocateBy lBy = field.getAnnotation(LocateBy.class);
//		if(null != lBy) {
//			locator.setWindowHandles(lBy.windowHandles());
//			locator.setFrames(lBy.frames());
//			locator.setTargets(lBy.targets());	
//			locator.setLocateByExist(true);
//		}else {
//			locator.setLocateByExist(false);
//		}
		
//		BaseWebElement element = elementFactory.create((Class<? extends BaseWebElement>) field.getType(), wrappedElement,byLocator,fieldName);
		BaseMobileElement element = elementFactory.create((Class<? extends BaseMobileElement>) field.getType(), wrappedElement,locator);
		return element;
	}
	
	/**
	 * @param field
	 * @return By locator of the field
	 * TODO : when there is no FindBy annotaion defined for field this return field name as by locator this need to check
	 */
	private By getByLocator(Field field){
//		AppiumBy by = null;
//		Annotations annotations = new Annotations(field);
//		boolean shouldCache = annotations.isLookupCached();
		
		AndroidFindBy androidFindBy = field.getAnnotation(AndroidFindBy.class);
		//by = annotations.buildBy();
		// constructore from reflection method to get AppiumBy e.g AppiumBy.id("");
		// from field get locator and the associated value and construct the AppiumBy
		return buildBy(androidFindBy);
	}
	
	private By buildBy(AndroidFindBy androidFindBy) {

		if (!"".equals(androidFindBy.uiAutomator())) {
			return AppiumBy.androidUIAutomator(androidFindBy.uiAutomator());
		}
		if (!"".equals(androidFindBy.accessibility())) {
			return AppiumBy.accessibilityId(androidFindBy.accessibility());
		}
		if (!"".equals(androidFindBy.id())) {
			return AppiumBy.id(androidFindBy.id());
		}
		if (!"".equals(androidFindBy.className())) {
			return AppiumBy.className(androidFindBy.className());
		}
		if (!"".equals(androidFindBy.tagName())) {
			return AppiumBy.tagName(androidFindBy.tagName());
		}
		if (!"".equals(androidFindBy.androidDataMatcher())) {
			return AppiumBy.androidDataMatcher(androidFindBy.androidDataMatcher());
		}
		if (!"".equals(androidFindBy.androidViewMatcher())) {
			return AppiumBy.androidViewMatcher(androidFindBy.androidViewMatcher());
		}
		if (!"".equals(androidFindBy.xpath())) {
			return AppiumBy.xpath(androidFindBy.xpath());
		}
		
		// under this strategy location priority is not considered, since we are using only one locator options
//		if (!"".equals(androidFindBy.priority())) {
//			return AppiumBy.priority(androidFindBy.priority());
//		}


		return null;
	}
}
