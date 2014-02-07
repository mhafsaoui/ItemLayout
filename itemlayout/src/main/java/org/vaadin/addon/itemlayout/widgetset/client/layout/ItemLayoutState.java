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
package org.vaadin.addon.itemlayout.widgetset.client.layout;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.vaadin.addon.itemlayout.layout.AbstractItemLayout;

import com.vaadin.shared.Connector;
import com.vaadin.shared.ui.AbstractLayoutState;

/**
 * @author Guillaume Lamirand
 */
public class ItemLayoutState extends AbstractLayoutState
{

  /**
   * Serial version id
   */
  private static final long     serialVersionUID     = 6420700012643864994L;
  {
    primaryStyleName = "v-itemlayout";
  }

  /**
   * Contains {@link Connector} draw according container items and {@link AbstractItemLayout#generator}
   */
  public Map<Connector, String> items                = new HashMap<Connector, String>();
  /**
   * Selected items
   */
  public Set<String>            selectedItems        = new HashSet<String>();
  /**
   * Allow user to select at least one item
   */
  public boolean                selectable           = false;
  /**
   * Allow user to select many items
   */
  public boolean                multiSelectable      = false;
  /**
   * Allow user to select nothing
   */
  public boolean                nullSelectionAllowed = true;
  /**
   * Defined if at least one listenner has been setted
   */
  public boolean                hasItemsClick        = false;
}
