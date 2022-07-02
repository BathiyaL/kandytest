package com.ktsapi.elements;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver.TargetLocator;

import com.ktsapi.annotation.ActionImplements;


/**
 * 
 * @author bladduwahetty
 * Original TargetLocator frame methods returns webdriver, to avoid this we shadow the interface
 */
@ActionImplements(name=TargetLocator.class)
public interface TargetLocatorFrame {
	   /**
     * Select a frame by its (zero-based) index. Selecting a frame by index is equivalent to the
     * JS expression window.frames[index] where "window" is the DOM window represented by the
     * current context. Once the frame has been selected, all subsequent calls on the WebDriver
     * interface are made to that frame.
     *
     * @param index (zero-based) index
     * @throws NoSuchFrameException If the frame cannot be found
     */
    void frame(int index);

    /**
     * Select a frame by its name or ID. Frames located by matching name attributes are always given
     * precedence over those matched by ID.
     *
     * @param nameOrId the name of the frame window, the id of the &lt;frame&gt; or &lt;iframe&gt;
     *        element, or the (zero-based) index   
     * @throws NoSuchFrameException If the frame cannot be found
     */
    void frame(String nameOrId);

    /**
     * Select a frame using its previously located {@link WebElement}.
     *
     * @param frameElement The frame element to switch to.     
     * @throws NoSuchFrameException If the given element is neither an IFRAME nor a FRAME element.
     * @throws StaleElementReferenceException If the WebElement has gone stale.
     * @see WebDriver#findElement(By)
     */
    void frame(BaseWebElement frameElement);

    /**
     * Change focus to the parent context. If the current context is the top level browsing context,
     * the context remains unchanged.
     *
     * @return This driver focused on the parent frame
     */
    void parentFrame();

    /**
     * Switch the focus of future commands for this driver to the window with the given name/handle.
     *
     * @param nameOrHandle The name of the window or the handle as returned by
     *        {@link WebDriver#getWindowHandle()}    
     * @throws NoSuchWindowException If the window cannot be found
     */
    void window(String nameOrHandle);

    /**
     * Selects either the first frame on the page, or the main document when a page contains
     * iframes.
    
     */
    void defaultContent();

    /**
     * Switches to the element that currently has focus within the document currently "switched to",
     * or the body element if this cannot be detected. This matches the semantics of calling
     * "document.activeElement" in Javascript.

     */
    WebElement activeElement();

    /**
     * Switches to the currently active modal dialog for this particular driver instance.
     * @throws NoAlertPresentException If the dialog cannot be found
     */
    Alert alert();

}
