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
package org.vaadin.addon.itemlayout.vertical;

import org.vaadin.addon.itemlayout.list.AbstractListLayout;
import org.vaadin.addon.itemlayout.widgetset.client.vertical.ItemVerticalState;

/**
 * @author Jeremy Casery
 */
public class ItemVertical extends AbstractListLayout
{

  /**
   * Serial version id
   */
  private static final long serialVersionUID = 7904026403680498379L;

  /**
   * Default constructor
   */
  public ItemVertical()
  {
    super();
    setHeight(100, Unit.PERCENTAGE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ItemVerticalState getState()
  {
    return (ItemVerticalState) super.getState();
  }

}