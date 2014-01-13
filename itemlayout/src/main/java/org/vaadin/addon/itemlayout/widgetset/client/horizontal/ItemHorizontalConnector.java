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
package org.vaadin.addon.itemlayout.widgetset.client.horizontal;

import java.util.Iterator;

import org.vaadin.addon.itemlayout.horizontal.ItemHorizontal;
import org.vaadin.addon.itemlayout.widgetset.client.layout.AbstractItemLayoutConnector;
import org.vaadin.addon.itemlayout.widgetset.client.layout.ItemLayoutConstant;
import org.vaadin.addon.itemlayout.widgetset.client.layout.ItemSlot;

import com.vaadin.client.ComponentConnector;
import com.vaadin.client.UIDL;
import com.vaadin.shared.ui.Connect;

/**
 * @author Guillaume Lamirand
 */
@Connect(ItemHorizontal.class)
public class ItemHorizontalConnector extends AbstractItemLayoutConnector

{

  /**
   * Serial version id
   */
  private static final long serialVersionUID = 5919752655203388362L;

  /**
   * {@inheritDoc}
   */
  @Override
  public ItemHorizontalState getState()
  {
    return (ItemHorizontalState) super.getState();
  }

  @Override
  public ItemHorizontalWidget getWidget()
  {
    return (ItemHorizontalWidget) super.getWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void renderedItems(final UIDL pUidl)
  {
    final UIDL itemsData = pUidl.getChildByTagName(ItemLayoutConstant.ATTRIBUTE_ITEMS);
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
  }

}
