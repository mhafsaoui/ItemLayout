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
   * @param pItemId
   *          Item id to select.
   */
  void onSelectItem(final String pItemId);

}
