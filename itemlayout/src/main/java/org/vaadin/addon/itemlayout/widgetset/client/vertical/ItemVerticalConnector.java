/**
 * NovaForge(TM) is a web-based forge offering a Collaborative Development and 
 * Project Management Environment.
 *
 * Copyright (C) 2007-2013  BULL SAS
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package org.vaadin.addon.itemlayout.widgetset.client.vertical;

import java.util.Iterator;

import org.vaadin.addon.itemlayout.vertical.ItemVertical;
import org.vaadin.addon.itemlayout.widgetset.client.layout.AbstractListLayoutConnector;
import org.vaadin.addon.itemlayout.widgetset.client.layout.ItemLayoutConstant;
import org.vaadin.addon.itemlayout.widgetset.client.layout.ItemSlot;

import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.UIDL;
import com.vaadin.shared.ui.Connect;

/**
 * @author Jeremy Casery
 */
@Connect(ItemVertical.class)
public class ItemVerticalConnector extends AbstractListLayoutConnector

{

  /**
   * Serial version id
   */
  private static final long serialVersionUID = 8548319579823380003L;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void renderedItems(final UIDL pUidl)
  {
    final UIDL itemsData = pUidl.getChildByTagName(ItemLayoutConstant.ATTRIBUTE_ITEMS);
    final int attributeScrollerIndex = pUidl.getIntAttribute(ItemLayoutConstant.ATTRIBUTE_SCROLLERINDEX);
    final int attributeScrollInterval = pUidl.getIntAttribute(ItemLayoutConstant.ATTRIBUTE_SCROLLINTERVAL);
    setScrollerIndex(attributeScrollerIndex);
    setScrollInterval(attributeScrollInterval);
    if (itemsData != null)
    {
      final Iterator<Object> items = itemsData.getChildIterator();
      while (items.hasNext())
      {
        final UIDL item = (UIDL) items.next();
        final String key = item.getStringAttribute(ItemLayoutConstant.ATTRIBUTE_ITEM_KEY);
        final ComponentConnector cellContent = getClient().getPaintable(item.getChildUIDL(0));

        final ItemSlot slot = prepareItemSlot(key, cellContent.getWidget());
        getWidget().add(slot);
      }
    }
    getWidget().getElemVisibleListLayout().setHeight(
        Integer.toString(getWidget().getOffsetHeight() - (48 * 2)) + "px");
    initScroller();

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ItemVerticalState getState()
  {
    return (ItemVerticalState) super.getState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ItemVerticalWidget getWidget()
  {
    return (ItemVerticalWidget) super.getWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean isClippedElems()
  {
    return getElemsListLayout().getElement().getAbsoluteBottom() > getElemVisibleListLayout().getElement()
        .getAbsoluteBottom();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected FocusPanel getNextLayout()
  {
    return getWidget().getNextLayout();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected FocusPanel getPrevLayout()
  {
    return getWidget().getPrevLayout();
  }

  /**
   * Get the elements list layout
   * 
   * @return {@link Widget} the elements list layout
   */
  private Widget getElemsListLayout()
  {
    return getWidget().getElemsListLayout();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected FocusPanel getElemVisibleListLayout()
  {
    return getWidget().getElemVisibleListLayout();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Widget getWidget(final int pIndex)
  {
    if ((pIndex < 0) || (pIndex >= getWidgetCount()) || getWidgetCount() < 1)
    {
      return null;
    }
    else
    {
      return getWidget().getElemsListLayout().getWidget(pIndex);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected int getWidgetCount()
  {
    return getWidget().getElemsListLayout().getWidgetCount();
  }

}
