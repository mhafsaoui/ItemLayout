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
  public static final String ATTRIBUTE_ISSCROLLABLE           = "isScrollable";
  public static final String ATTRIBUTE_SCROLLINTERVAL         = "scrollInterval";
  public static final String ATTRIBUTE_SCROLLERINDEX          = "scrollerIndex";
  public static final String ATTRIBUTE_SCROLLPREVELEMENT      = "scrollPrevElement";
  public static final String ATTRIBUTE_SCROLLNEXTELEMENT      = "scrollNextElement";

  public static final String CLASSNAME                        = "v-itemlayout";
  public static final String CLASS_SLOT                       = CLASSNAME + "-slot";
  public static final String CLASS_SCROLL_LAYOUT              = CLASSNAME + "-scroll";
  public static final String CLASS_SLOT_SELECTED              = CLASS_SLOT + "-selected";
  public static final String CLASS_SCROLL_HIDDEN              = CLASS_SCROLL_LAYOUT + "-hidden";
  public static final String CLASS_SLOT_REMOVED               = CLASS_SLOT + "-removed";
  public static final String CLASS_SCROLL_PREV                = CLASS_SCROLL_LAYOUT + "-prev";
  public static final String CLASS_SCROLL_NEXT                = CLASS_SCROLL_LAYOUT + "-next";
  public static final String CLASS_LIST_VISIBLE               = CLASS_SCROLL_LAYOUT + "-visiblelist";

}
