package org.vaadin.addon.itemlayout.widgetset.client.layout;

import com.google.gwt.user.client.ui.FocusPanel;

public class ItemSlot extends FocusPanel
{
  public static final String CLASSNAME = "v-itemlayout-slot";

  public ItemSlot()
  {
    super();
    setStyleName(CLASSNAME);
  }

  public void select()
  {
    addStyleName("v-itemlayout-slot-selected");
  }

  public void unselect()
  {
    removeStyleName("v-itemlayout-slot-selected");
  }
}
