package org.vaadin.addon.itemlayout.widgetset.client.layout;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.Paintable;
import com.vaadin.client.TooltipInfo;
import com.vaadin.client.UIDL;
import com.vaadin.client.Util;
import com.vaadin.client.ui.AbstractHasComponentsConnector;
import com.vaadin.client.ui.orderedlayout.Slot;

/**
 * @author Guillaume Lamirand
 */
/**
 * @author lamirand-g
 */
public abstract class AbstractItemLayoutConnector extends AbstractHasComponentsConnector implements Paintable
{

	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = -3757721567173540353L;

	public enum SelectMode
	{
		NONE(0), SINGLE(1), MULTI(2);
		private final int id;

		private SelectMode(final int id)
		{
			this.id = id;
		}

		public int getId()
		{
			return id;
		}
	}

	private boolean                     hasItemClick         = false;
	private boolean                     nullSelectionAllowed = true;

	private SelectMode                  selectMode           = SelectMode.NONE;

	private Set<String>                 selectedKeys         = new HashSet<String>();
	private final Map<String, ItemSlot> slotByKey            = new HashMap<String, ItemSlot>();

	private ApplicationConnection       client;

	private String                      paintableId;
	/**
	 * Contains value used to send immediatly client events
	 */
	private boolean                     immediate;
	/**
	 * Flag for notifying when the selection has changed and should be sent to
	 * the server
	 */
	private boolean                     selectionChanged;

	/*
	 * (non-Javadoc)
	 * @see com.vaadin.client.Paintable#updateFromUIDL(com.vaadin.client.UIDL,
	 * com.vaadin.client.ApplicationConnection)
	 */
	@Override
	public void updateFromUIDL(final UIDL pUidl, final ApplicationConnection pClient)
	{
		// Update main properties
		client = pClient;
		if (!isRealUpdate(pUidl))
		{
			return;
		}
		paintableId = pUidl.getStringAttribute("id");
		immediate = getState().immediate;

		// Update selection properties
		updateSelectionProperties(pUidl);
		updateSelectedItems(pUidl);
		updateItemClickHandler(pUidl);

		// Update rendered items
		slotByKey.clear();
		renderedItems(pUidl);

	}

	private void updateSelectionProperties(final UIDL uidl)
	{

		nullSelectionAllowed = uidl.hasAttribute(ItemLayoutConstant.ATTRIBUTE_NULL_SELECTION_ALLOWED) ? uidl
		    .getBooleanAttribute(ItemLayoutConstant.ATTRIBUTE_NULL_SELECTION_ALLOWED) : true;

		if (uidl.hasAttribute(ItemLayoutConstant.ATTRIBUTE_SELECTMODE))
		{
			if (isReadOnly())
			{
				selectMode = SelectMode.NONE;
			}
			else if (uidl.getStringAttribute(ItemLayoutConstant.ATTRIBUTE_SELECTMODE).equals(
			    ItemLayoutConstant.ATTRIBUTE_SELECTMODE_MULTI))
			{
				selectMode = SelectMode.MULTI;
			}
			else if (uidl.getStringAttribute(ItemLayoutConstant.ATTRIBUTE_SELECTMODE).equals(
			    ItemLayoutConstant.ATTRIBUTE_SELECTMODE_SINGLE))
			{
				selectMode = SelectMode.SINGLE;
			}
			else
			{
				selectMode = SelectMode.NONE;
			}
		}
	}

	private void updateSelectedItems(final UIDL pUidl)
	{
		selectedKeys.clear();
		if (pUidl.hasVariable(ItemLayoutConstant.ATTRIBUTE_SELECTED))
		{
			selectedKeys = pUidl.getStringArrayVariableAsSet(ItemLayoutConstant.ATTRIBUTE_SELECTED);
		}
	}

	private void updateItemClickHandler(final UIDL pUidl)
	{
		if (pUidl.hasVariable(ItemLayoutConstant.ATTRIBUTE_ITEM_CLICK_HANDLER))
		{
			hasItemClick = pUidl.getBooleanAttribute(ItemLayoutConstant.ATTRIBUTE_ITEM_CLICK_HANDLER);
		}

	}

	protected abstract void renderedItems(final UIDL pUidl);

	/**
	 * Build a {@link ItemSlot} with the given widget
	 * 
	 * @param pItemIndex
	 *          the item index
	 * @param pWidget
	 *          the widget to attach
	 * @return {@link ItemSlot} built
	 */
	protected ItemSlot prepareItemSlot(final String pItemKey, final Widget pWidget)
	{
		final ItemSlot slot = new ItemSlot();
		slot.add(pWidget);
		slot.addClickHandler(new ClickHandler()
		{

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void onClick(final ClickEvent event)
			{
				if (SelectMode.NONE.equals(selectMode) == false)
				{
					// This slot has been clicked and a item click handler has been setup
					if (hasItemClick)
					{
						client.updateVariable(paintableId, ItemLayoutConstant.ATTRIBUTE_ITEM_CLICKED_KEY, pItemKey,
						    immediate);
					}
					// Handle select style for the click
					handleSelectSlot(pItemKey);
				}
			}

		});
		if ((selectedKeys != null) && (SelectMode.NONE.equals(selectMode) == false)
		    && (selectedKeys.contains(pItemKey)))
		{
			slot.select();
		}
		slotByKey.put(pItemKey, slot);
		return slot;
	}

	/**
	 * This method will handle click event on {@link Slot}
	 * 
	 * @param pItemKey
	 *          the item key associated to the slot
	 */
	private void handleSelectSlot(final String pItemKey)
	{
		if (selectedKeys != null)
		{
			if (selectedKeys.size() == 0)
			{
				selectSlot(pItemKey);
			}
			else if ((((selectedKeys.size() == 1) && (nullSelectionAllowed)) || (selectedKeys.size() != 1))
			    && (selectedKeys.contains(pItemKey)))
			{
				unselectSlot(pItemKey);
			}
			else if (selectedKeys.contains(pItemKey) == false)
			{
				if (SelectMode.SINGLE.equals(selectMode))
				{
					unselectAll();
				}
				selectSlot(pItemKey);
			}
		}

	}

	/**
	 * Unselect all selected {@link Slot}
	 */
	private void unselectAll()
	{
		for (String selectKey : selectedKeys)
		{
			if (slotByKey.containsKey(selectKey))
			{
				slotByKey.get(selectKey).unselect();
			}
		}
		selectedKeys.clear();
		selectionChanged = true;
	}

	/**
	 * Unselect the {@link Slot} associated to the given Item Key
	 * 
	 * @param pItemKey
	 *          the item key
	 */
	private void unselectSlot(final String pItemKey)
	{
		if (slotByKey.containsKey(pItemKey))
		{
			slotByKey.get(pItemKey).unselect();
		}
		selectedKeys.remove(pItemKey);
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
		if (slotByKey.containsKey(pItemKey))
		{
			selectedKeys.add(pItemKey);
			slotByKey.get(pItemKey).select();
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
			client.updateVariable(paintableId, ItemLayoutConstant.ATTRIBUTE_SELECTED,
			    selectedKeys.toArray(new String[selectedKeys.size()]), immediate);
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isReadOnly()
	{
		return super.isReadOnly() || getState().propertyReadOnly;
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
			ItemSlot itemSlot = Util.findWidget((com.google.gwt.user.client.Element) element, ItemSlot.class);
			if ((itemSlot != null) && (itemSlot.getParent() == getWidget()))
			{
				ComponentConnector connector = Util.findConnectorFor(itemSlot.getWidget());
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
	 * {@inheritDoc}
	 */
	@Override
	public void onConnectorHierarchyChange(final ConnectorHierarchyChangeEvent connectorHierarchyChangeEvent)
	{
		// This cannot be used at this moment because of legacy compatibily

	}

	/**
	 * @return the client
	 */
	public ApplicationConnection getClient()
	{
		return client;
	}

	/**
	 * @return the paintableId
	 */
	public String getPaintableId()
	{
		return paintableId;
	}

}
