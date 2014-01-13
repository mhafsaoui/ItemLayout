package org.vaadin.addon.itemlayout.widgetset.client.layout;

import org.vaadin.addon.itemlayout.layout.AbstractItemLayout;

import com.vaadin.shared.communication.ServerRpc;

/**
 * Interface used to communicate with server side of {@link AbstractItemLayout}
 * 
 * @author Guillaume Lamirand
 */
public interface ItemLayoutServerRpc extends ServerRpc
{

  /**
   * Selects item with given id.
   * 
   * @param pItemIndex
   *          Item index to select at. Selecting the same item again and again has no effect.
   */
  void selectItemAtIndex(final Integer pItemIndex);

}
