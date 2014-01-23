package org.vaadin.addon.itemlayout.widgetset.client.layout;

import com.google.gwt.user.client.ui.FocusPanel;

public class ItemSlot extends FocusPanel
{

  public ItemSlot()
  {
    super();
    setStyleName(ItemLayoutConstant.CLASS_SLOT);
  }

  public void select()
  {
    addStyleName(ItemLayoutConstant.CLASS_SLOT_SELECTED);
  }

  public void unselect()
  {
    removeStyleName(ItemLayoutConstant.CLASS_SLOT_SELECTED);
  }
}
