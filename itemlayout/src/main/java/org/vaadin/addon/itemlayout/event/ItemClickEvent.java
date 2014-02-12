package org.vaadin.addon.itemlayout.event;

/**
 * Thrown when an item received click event
 * 
 * @author Guillaume Lamirand
 */
public class ItemClickEvent
{
  /**
   * Contains the item id
   */
  private final String  itemId;
  /**
   * Contains the item id
   */
  private final boolean selected;

  /**
   * Default constructor
   * 
   * @param pItemId
   *          the item id
   * @param pIsSelected
   *          true for selected item
   */
  public ItemClickEvent(final String pItemId, final boolean pIsSelected)
  {
    itemId = pItemId;
    selected = pIsSelected;

  }

  /**
   * Returns the id of the item
   * 
   * @return the itemId
   */
  public String getItemId()
  {
    return itemId;
  }

  /**
   * Returns true if the item is selected or false otherwise
   * 
   * @return true if the item is selected or false otherwise
   */
  public boolean isSelected()
  {
    return selected;
  }

};