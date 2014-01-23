package org.vaadin.addon.itemlayout.widgetset.client.vertical;

import org.vaadin.addon.itemlayout.widgetset.client.layout.ItemLayoutConstant;
import org.vaadin.addon.itemlayout.widgetset.client.resources.ResourceBundle;

import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Guillaume Lamirand
 */
public class ItemVerticalWidget extends VerticalPanel
{

  /**
   * The scroll previous button image
   */
  private final Image         scrollPrevButtonElement;
  /**
   * The scroll next button image
   */
  private final Image         scrollNextButtonElement;
  /**
   * The scroll previous panel layout
   */
  private final FocusPanel    prevLayout;
  /**
   * The scroll next panel layout
   */
  private final FocusPanel    nextLayout;
  /**
   * The visible elements list layout
   */
  private final FocusPanel    elemVisibleListLayout;
  /**
   * The elements list layout
   */
  private final VerticalPanel elemsListLayout;

  /**
   * Default constructor
   */
  public ItemVerticalWidget()
  {
    super();
    setStyleName(ItemVerticalConstant.CLASSNAME);
    // Scroll Previous widget
    prevLayout = new FocusPanel();
    prevLayout.setStyleName(ItemLayoutConstant.CLASS_SCROLL_LAYOUT);
    scrollPrevButtonElement = new Image(ResourceBundle.INSTANCE.verticalPrev());
    scrollPrevButtonElement.setStyleName(ItemLayoutConstant.CLASS_SCROLL_PREV);
    prevLayout.add(scrollPrevButtonElement);
    // Scroll Next widget
    nextLayout = new FocusPanel();
    nextLayout.setStyleName(ItemLayoutConstant.CLASS_SCROLL_LAYOUT);
    scrollNextButtonElement = new Image(ResourceBundle.INSTANCE.verticalNext());
    scrollNextButtonElement.setStyleName(ItemLayoutConstant.CLASS_SCROLL_NEXT);
    nextLayout.add(scrollNextButtonElement);
    // Elements list layout
    elemVisibleListLayout = new FocusPanel();
    elemVisibleListLayout.setStyleName(ItemLayoutConstant.CLASS_LIST_VISIBLE);
    elemsListLayout = new VerticalPanel();
    elemVisibleListLayout.add(elemsListLayout);

    super.add(prevLayout);
    super.add(elemVisibleListLayout);
    super.add(nextLayout);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void add(final Widget w)
  {
    elemsListLayout.add(w);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean remove(final Widget w)
  {
    return elemsListLayout.remove(w);
  }

  /**
   * @return the prevLayout
   */
  public FocusPanel getPrevLayout()
  {
    return prevLayout;
  }

  /**
   * @return the nextLayout
   */
  public FocusPanel getNextLayout()
  {
    return nextLayout;
  }

  /**
   * @return the elemVisibleListLayout
   */
  public FocusPanel getElemVisibleListLayout()
  {
    return elemVisibleListLayout;
  }

  /**
   * @return the elemsListLayout
   */
  public VerticalPanel getElemsListLayout()
  {
    return elemsListLayout;
  }

}
