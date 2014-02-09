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
package org.vaadin.addon.itemlayout.widgetset.client.grid;

import org.vaadin.addon.itemlayout.grid.ItemGrid;
import org.vaadin.addon.itemlayout.widgetset.client.layout.AbstractItemLayoutConnector;
import org.vaadin.addon.itemlayout.widgetset.client.model.ItemSlot;

import com.vaadin.client.ComponentConnector;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.shared.ui.Connect;

/**
 * @author Guillaume Lamirand
 */
@Connect(ItemGrid.class)
public class ItemGridConnector extends AbstractItemLayoutConnector

{

  /**
   * Serial version id
   */
  private static final long serialVersionUID = 5919752655203388362L;

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

    boolean hasColumnsChanged = stateChangeEvent.hasPropertyChanged(ItemGridState.COLUMNS);
    if (hasColumnsChanged)
    {
      int rowCount = getWidget().getRowCount();
      getWidget().resize(rowCount, getState().columns);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initLayout()
  {
    int childrenCount = getChildComponents().size();
    final int rows = childrenCount == 0 ? 0 : ((childrenCount - 1) / getState().columns) + 1;
    getWidget().resize(rows, getState().columns);
    getWidget().clear(true);

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void addItemSlot(final ComponentConnector pConnector, final ItemSlot pSlot)
  {
    int childIndex = getChildComponents().indexOf(pConnector);
    final int column = childIndex % getState().columns;
    final int row = childIndex / getState().columns;
    getWidget().setWidget(row, column, pSlot);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void postItemsRendered()
  {
    // Nothing to do

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ItemGridState getState()
  {
    return (ItemGridState) super.getState();
  }

  @Override
  public ItemGridWidget getWidget()
  {
    return (ItemGridWidget) super.getWidget();
  }

}
