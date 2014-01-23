/**
 * NovaForge(TM) is a web-based forge offering a Collaborative Development and 
 * Project Management Environment.
 *
 * Copyright (C) 2007-2014  BULL SAS
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
package org.vaadin.addon.itemlayout.layout;

import org.vaadin.addon.itemlayout.widgetset.client.layout.ItemLayoutConstant;

import com.vaadin.server.PaintException;
import com.vaadin.server.PaintTarget;

/**
 * @author Jeremy Casery
 */
public abstract class AbstractListLayout extends AbstractItemLayout
{

  /**
   * Serial version id
   */
  private static final long serialVersionUID = 7856191817150158454L;
  /**
   * Default scroll interval
   */
  private int               scrollInterval   = 1;
  /**
   * Default scroller index
   */
  private int               scrollerIndex    = 0;

  /**
   * Default constructor.
   */
  public AbstractListLayout()
  {
    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void paintLayoutAttributes(final PaintTarget pTarget) throws PaintException
  {
    pTarget.addAttribute(ItemLayoutConstant.ATTRIBUTE_SCROLLINTERVAL, getScrollInterval());
    pTarget.addAttribute(ItemLayoutConstant.ATTRIBUTE_SCROLLERINDEX, getScrollerIndex());
  }

  /**
   * Get the scroll interval
   * 
   * @return the scrollInterval
   */
  public int getScrollInterval()
  {
    return scrollInterval;
  }

  /**
   * Set the scroll interval
   * 
   * @param scrollInterval
   *          the scrollInterval to set
   */
  public void setScrollInterval(final int scrollInterval)
  {
    this.scrollInterval = scrollInterval;
  }

  /**
   * Get the scroller index
   * 
   * @return the scrollerIndex
   */
  public int getScrollerIndex()
  {
    return scrollerIndex;
  }

  /**
   * Set the scroller index
   * 
   * @param scrollerIndex
   *          the scrollerIndex to set
   */
  public void setScrollerIndex(final int scrollerIndex)
  {
    this.scrollerIndex = scrollerIndex;
  }

}
