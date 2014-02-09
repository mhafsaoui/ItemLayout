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
package org.vaadin.addon.itemlayout.horizontal;

import org.vaadin.addon.itemlayout.list.AbstractListLayout;
import org.vaadin.addon.itemlayout.widgetset.client.horizontal.ItemHorizontalState;

/**
 * @author Guillaume Lamirand
 */
public class ItemHorizontal extends AbstractListLayout
{

  /**
   * Serial version id
   */
  private static final long serialVersionUID = 5432040675249120672L;

  /**
   * Default constructor
   */
  public ItemHorizontal()
  {
    super();
    setWidth(100, Unit.PERCENTAGE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ItemHorizontalState getState()
  {
    return (ItemHorizontalState) super.getState();
  }

}