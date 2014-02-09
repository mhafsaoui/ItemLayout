package org.vaadin.addon.itemlayout.widgetset.client.layout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.vaadin.addon.itemlayout.widgetset.client.model.ItemSlot;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.TooltipInfo;
import com.vaadin.client.Util;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractLayoutConnector;

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

  ItemLayoutServerRpc                 serverRpc        = RpcProxy.create(ItemLayoutServerRpc.class, this);

  public AbstractItemLayoutConnector()
  {
    super();

  }

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

    boolean hasSelectedItemsChanged = stateChangeEvent.hasPropertyChanged(ItemLayoutState.SELECTED_ITEMS);
    if (hasSelectedItemsChanged)
    {
      Set<Entry<Object, ItemSlot>> entrySet = slotById.entrySet();
      for (Entry<Object, ItemSlot> entry : entrySet)
      {
        entry.getValue().unselect();
      }
      for (String itemId : getState().selectedItems)
      {
        if (slotById.containsKey(itemId))
        {
          slotById.get(itemId).select();
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onConnectorHierarchyChange(final ConnectorHierarchyChangeEvent connectorHierarchyChangeEvent)
  {
    slotById.clear();
    initLayout();
    final List<ComponentConnector> childComponents = getChildComponents();
    if (childComponents != null)
    {
      for (final ComponentConnector componentConnector : childComponents)
      {
        final ItemSlot slot = prepareItemSlot(componentConnector);
        addItemSlot(componentConnector, slot);
      }
    }
    postItemsRendered();

  }

  /**
   * This method is called to init the layout
   */
  protected abstract void initLayout();

  /**
   * This method should add item to to the layout
   * 
   * @param pConnector
   *          the child {@link ComponentConnector}
   * @param pSlot
   *          the {@link ItemSlot} associated to the child connector
   */
  protected abstract void addItemSlot(final ComponentConnector pConnector, final ItemSlot pSlot);

  /**
   * This method is called after components have been rendered and added
   */
  protected abstract void postItemsRendered();

  /**
   * Build a {@link ItemSlot} with the given widget
   * 
   * @param pConnector
   *          the child {@link ComponentConnector}
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
          // Send event to server side
          serverRpc.onSelectItem(itemId);
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

}
