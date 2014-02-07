package org.vaadin.addon.itemlayout.widgetset.client.layout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.TooltipInfo;
import com.vaadin.client.Util;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractLayoutConnector;
import com.vaadin.client.ui.orderedlayout.Slot;

/**
 * @author Guillaume Lamirand
 */
public abstract class AbstractItemLayoutConnector extends AbstractLayoutConnector
{

  /**
   * Serial version id
   */
  private static final long           serialVersionUID = -3757721567173540353L;

  private final Map<Object, ItemSlot> slotById         = new HashMap<Object, ItemSlot>();

  private ApplicationConnection       client;

  /**
   * Flag for notifying when the selection has changed and should be sent to
   * the server
   */
  private boolean                     selectionChanged;

  /*
   * (non-Javadoc)
   * @see
   * com.vaadin.client.ui.AbstractComponentConnector#onStateChanged(com.vaadin
   * .client.communication.StateChangeEvent)
   */
  @Override
  public void onStateChanged(final StateChangeEvent stateChangeEvent)
  {
    super.onStateChanged(stateChangeEvent);

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onConnectorHierarchyChange(final ConnectorHierarchyChangeEvent connectorHierarchyChangeEvent)
  {
    slotById.clear();
    removeAllItem();
    final List<ComponentConnector> childComponents = getChildComponents();
    if (childComponents != null)
    {
      for (final ComponentConnector componentConnector : childComponents)
      {
        final ItemSlot slot = prepareItemSlot(componentConnector);
        addItemSlot(slot);
      }
    }

  }

  /**
   * This method should remove all {@link ItemSlot} from the layout
   */
  protected abstract void removeAllItem();

  /**
   * This method should add item to to the layout
   */
  protected abstract void addItemSlot(final ItemSlot pSlot);

  /**
   * This method is called after components have been rendered and added
   */
  protected abstract void postItemsRendered();

  /**
   * Build a {@link ItemSlot} with the given widget
   * 
   * @param pItemIndex
   *          the item index
   * @param pWidget
   *          the widget to attach
   * @return {@link ItemSlot} built
   */
  protected ItemSlot prepareItemSlot(final ComponentConnector pConnector)
  {
    final String itemId = getState().items.get(pConnector);
    final ItemSlot slot = new ItemSlot();
    slot.add(pConnector.getWidget());
    slot.addClickHandler(new ClickHandler()
    {

      /**
       * {@inheritDoc}
       */
      @Override
      public void onClick(final ClickEvent event)
      {
        if (getState().selectable)
        {
          // This slot has been clicked and a item click handler has been setup
          if (getState().hasItemsClick)
          {
            // TODO Handle this with rpc
            // client.updateVariable(paintableId, ItemLayoutConstant.ATTRIBUTE_ITEM_CLICKED_KEY, pItemKey,
            // immediate);
          }
          // Handle select style for the click
          handleSelectSlot(itemId);
        }
      }

    });
    if ((getState().selectedItems != null) && (getState().selectable)
        && (getState().selectedItems.contains(itemId)))
    {
      slot.select();
    }
    slotById.put(itemId, slot);
    return slot;
  }

  /**
   * This method will handle click event on {@link Slot}
   * 
   * @param pItemId
   *          the item id associated to the slot
   */
  private void handleSelectSlot(final String pItemId)
  {
    if (getState().selectedItems != null)
    {
      if (getState().selectedItems.isEmpty())
      {
        selectSlot(pItemId);
      }
      else if ((((getState().selectedItems.size() == 1) && (getState().nullSelectionAllowed)) || (getState().selectedItems
          .size() != 1)) && (getState().selectedItems.contains(pItemId)))
      {
        unselectSlot(pItemId);
      }
      else if (getState().selectedItems.contains(pItemId) == false)
      {
        if ((getState().selectable) && (getState().multiSelectable == false))
        {
          unselectAll();
        }
        selectSlot(pItemId);
      }
    }

  }

  /**
   * Unselect all selected {@link Slot}
   */
  private void unselectAll()
  {
    for (final Object selectItemId : getState().selectedItems)
    {
      if (slotById.containsKey(selectItemId))
      {
        slotById.get(selectItemId).unselect();
      }
    }
    getState().selectedItems.clear();
    selectionChanged = true;
  }

  /**
   * Unselect the {@link Slot} associated to the given Item Key
   * 
   * @param pItemKey
   *          the item key
   */
  private void unselectSlot(final Object pItemKey)
  {
    if (slotById.containsKey(pItemKey))
    {
      slotById.get(pItemKey).unselect();
    }
    getState().selectedItems.remove(pItemKey);
    selectionChanged = true;
  }

  /**
   * Select the {@link Slot} associated to the given Item Key
   * 
   * @param pItemKey
   *          the item key
   */
  private void selectSlot(final String pItemKey)
  {
    if (slotById.containsKey(pItemKey))
    {
      getState().selectedItems.add(pItemKey);
      slotById.get(pItemKey).select();
      selectionChanged = true;
    }
  }

  /**
   * Sends the selection to the server if it has been changed since the last
   * update/visit.
   */
  protected void sendSelectedRows()
  {
    // Don't send anything if selection has not changed
    if (selectionChanged)
    {
      // Reset selection changed flag
      selectionChanged = false;

      // Send the selected items
      // TODO Handle this with rpc
      // client.updateVariable(paintableId, ItemLayoutConstant.ATTRIBUTE_SELECTED,
      // selectedKeys.toArray(new String[selectedKeys.size()]), immediate);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ItemLayoutState getState()
  {
    return (ItemLayoutState) super.getState();
  }

  /*
   * (non-Javadoc)
   * @see
   * com.vaadin.client.ui.AbstractComponentConnector#getTooltipInfo(com.google
   * .gwt.dom.client.Element)
   */
  @Override
  public TooltipInfo getTooltipInfo(final com.google.gwt.dom.client.Element element)
  {
    if (element != getWidget().getElement())
    {
      final ItemSlot itemSlot = Util.findWidget((com.google.gwt.user.client.Element) element, ItemSlot.class);
      if ((itemSlot != null) && (itemSlot.getParent() == getWidget()))
      {
        final ComponentConnector connector = Util.findConnectorFor(itemSlot.getWidget());
        if (connector != null)
        {
          return connector.getTooltipInfo(element);
        }
      }
    }
    return super.getTooltipInfo(element);
  }

  /*
   * (non-Javadoc)
   * @see com.vaadin.client.ui.AbstractComponentConnector#hasTooltip()
   */
  @Override
  public boolean hasTooltip()
  {
    /*
     * Tooltips are fetched from child connectors -> there's no quick way of
     * checking whether there might a tooltip hiding somewhere
     */
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateCaption(final ComponentConnector pConnector)
  {
    // Do not handle children caption or icon
  }

  /**
   * @return the client
   */
  public ApplicationConnection getClient()
  {
    return client;
  }

}
