package org.vaadin.addon.itemlayout.layout;

import java.io.Serializable;

import com.vaadin.data.Container;
import com.vaadin.ui.Component;

/**
 * Used to create generated components according item property defined in the underlying {@link Container}.
 * Implement this interface and pass it to {@link AbstractItemLayout#setItemGenerator(ItemGenerator)} .
 * 
 * @author Guillaume Lamirand
 */
public interface ItemGenerator extends Serializable
{

  /**
   * Called by {@link AbstractItemLayout} when a item needs to be
   * generated.
   * 
   * @param pSource
   *          the source layout
   * @param pItemId
   *          the itemId set up in associated {@link Container} for the item to be generated
   * @return A {@link Component} that should be rendered
   */
  Component generateItem(final AbstractItemLayout pSource, final Object pItemId);
}
