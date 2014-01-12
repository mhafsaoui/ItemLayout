package org.vaadin.addon.itemlayout.widgetset.client.layout;

import java.io.Serializable;

/**
 * @author Guillaume Lamirand
 */
public class ItemLayoutConstant implements Serializable
{

	/**
	 * Serial version id
	 */
	private static final long  serialVersionUID                 = -4263180961727132242L;

	public static final String ATTRIBUTE_ITEMS                  = "items";
	public static final String ATTRIBUTE_ITEM                   = "item";
	public static final String ATTRIBUTE_ITEM_CLICK_HANDLER     = "itemClickHandler";
	public static final String ATTRIBUTE_ITEM_CLICKED_KEY       = "clickedItemKey";

	public static final String ATTRIBUTE_ITEM_KEY               = "key";
	public static final String ATTRIBUTE_NULL_SELECTION_ALLOWED = "nsa";
	public static final String ATTRIBUTE_SELECTMODE             = "selectmode";
	public static final String ATTRIBUTE_SELECTMODE_MULTI       = "multi";
	public static final String ATTRIBUTE_SELECTMODE_SINGLE      = "single";
	public static final String ATTRIBUTE_SELECTMODE_NONE        = "none";
	public static final String ATTRIBUTE_SELECTED               = "selected";
	/**
	 * Tell the client that old keys are no longer valid because the server has
	 * cleared its key map.
	 */
	@Deprecated
	public static final String ATTRIBUTE_KEY_MAPPER_RESET       = "clearKeyMap";

}
