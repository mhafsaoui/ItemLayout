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
package org.vaadin.addon.itemlayout.layout.model;

import java.io.Serializable;
import java.util.EventObject;

import com.vaadin.data.Container;

/**
 * Implementation of property set change event.
 */
public class DefaultPropertySetChangeEvent extends EventObject implements Container.PropertySetChangeEvent,
    Serializable
{

  public DefaultPropertySetChangeEvent(final Container source)
  {
    super(source);
  }

  /**
   * Retrieves the Container whose contents have been modified.
   * 
   * @see com.vaadin.data.Container.PropertySetChangeEvent#getContainer()
   */
  @Override
  public Container getContainer()
  {
    return (Container) getSource();
  }

}