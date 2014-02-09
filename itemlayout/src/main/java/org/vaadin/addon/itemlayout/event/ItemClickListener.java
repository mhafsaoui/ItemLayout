package org.vaadin.addon.itemlayout.event;

/**
 * Interface for listening for a {@link ItemClickEvent} fired by item click
 * 
 * @author Guillaume Lamirand
 */
public interface ItemClickListener
{

  /**
   * Called when a item has been clicked.
   * 
   * @param pEvent
   *          An event containing information about the click.
   */
  void onItemClick(final ItemClickEvent pEvent);
};