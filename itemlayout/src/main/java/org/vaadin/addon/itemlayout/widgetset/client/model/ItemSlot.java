package org.vaadin.addon.itemlayout.widgetset.client.model;

import org.vaadin.addon.itemlayout.widgetset.client.layout.ItemLayoutConstant;

import com.google.gwt.user.client.ui.FocusPanel;

/**
 * A component used to display each item component
 * 
 * @author Guillaume Lamirand
 */
public class ItemSlot extends FocusPanel
{

  private boolean selected;

  public ItemSlot()
  {
    super();
    setStyleName(ItemLayoutConstant.CLASS_SLOT);
  }

  public boolean isSelected()
  {
    return selected;
  }

  public void select()
  {
    if (selected == false)
    {
      addStyleName(ItemLayoutConstant.CLASS_SLOT_SELECTED);
      selected = true;
    }
  }

  public void unselect()
  {
    if (selected)
    {
      removeStyleName(ItemLayoutConstant.CLASS_SLOT_SELECTED);
      selected = false;
    }
  }

}
