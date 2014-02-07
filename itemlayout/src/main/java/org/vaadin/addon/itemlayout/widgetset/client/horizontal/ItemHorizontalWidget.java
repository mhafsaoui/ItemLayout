package org.vaadin.addon.itemlayout.widgetset.client.horizontal;

import org.vaadin.addon.itemlayout.widgetset.client.layout.ItemLayoutConstant;
import org.vaadin.addon.itemlayout.widgetset.client.resources.ResourceBundle;

import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Guillaume Lamirand
 */
public class ItemHorizontalWidget extends HorizontalPanel
{
  /**
   * The scroll previous button image
   */
  private final Image           scrollPrevButtonElement;
  /**
   * The scroll next button image
   */
  private final Image           scrollNextButtonElement;
  /**
   * The scroll previous panel layout
   */
  private final FocusPanel      prevLayout;
  /**
   * The scroll next panel layout
   */
  private final FocusPanel      nextLayout;
  /**
   * The visible elements list layout
   */
  private final FocusPanel      elemVisibleListLayout;
  /**
   * The elements list layout
   */
  private final HorizontalPanel elemsListLayout;

  /**
   * Default constructor
   */
  public ItemHorizontalWidget()
  {
    super();
    setStyleName(ItemHorizontalConstant.CLASSNAME);
    // Scroll Previous widget
    prevLayout = new FocusPanel();
    prevLayout.setStyleName(ItemLayoutConstant.CLASS_SCROLL_LAYOUT);
    scrollPrevButtonElement = new Image(ResourceBundle.INSTANCE.horizontalPrev());
    scrollPrevButtonElement.setStyleName(ItemLayoutConstant.CLASS_SCROLL_PREV);
    prevLayout.add(scrollPrevButtonElement);
    // Scroll Next widget
    nextLayout = new FocusPanel();
    nextLayout.setStyleName(ItemLayoutConstant.CLASS_SCROLL_LAYOUT);
    scrollNextButtonElement = new Image(ResourceBundle.INSTANCE.horizontalNext());
    scrollNextButtonElement.setStyleName(ItemLayoutConstant.CLASS_SCROLL_NEXT);
    nextLayout.add(scrollNextButtonElement);
    // Elements list layout
    elemVisibleListLayout = new FocusPanel();
    elemVisibleListLayout.setStyleName(ItemLayoutConstant.CLASS_LIST_VISIBLE);
    elemsListLayout = new HorizontalPanel();
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

  public void removeAll()
  {
    for (int i = 0; i < elemsListLayout.getWidgetCount(); i++)
    {
      final Widget widget = getWidget(i);
      elemsListLayout.remove(widget);
    }
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
   * Get the scroll previous layout
   * 
   * @return {@link FocusPanel} the prevLayout
   */
  public FocusPanel getPrevLayout()
  {
    return prevLayout;
  }

  /**
   * Get the scroll next layout
   * 
   * @return {@link FocusPanel} the nextLayout
   */
  public FocusPanel getNextLayout()
  {
    return nextLayout;
  }

  /**
   * Get the visible elements list layout
   * 
   * @return {@link FocusPanel} the elemVisibleListLayout
   */
  public FocusPanel getElemVisibleListLayout()
  {
    return elemVisibleListLayout;
  }

  /**
   * Get the elements list layout
   * 
   * @return {@link FocusPanel} the elemsListLayout
   */
  public HorizontalPanel getElemsListLayout()
  {
    return elemsListLayout;
  }

}
