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
package org.vaadin.addon.itemlayout.widgetset.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * @author Jeremy Casery
 */
public interface ResourceBundle extends ClientBundle
{
  /**
   * Instance of resource bundle
   */
  public static final ResourceBundle INSTANCE = GWT.create(ResourceBundle.class);

  /**
   * The horizontal next image
   * 
   * @return {@link ImageResource} the horizontal next image
   */
  @Source("horizontal-next.png")
  ImageResource horizontalNext();

  /**
   * The horizontal prev image
   * 
   * @return {@link ImageResource} the horizontal prev image
   */
  @Source("horizontal-prev.png")
  ImageResource horizontalPrev();

  /**
   * The vertical next image
   * 
   * @return {@link ImageResource} the vertical next image
   */
  @Source("vertical-next.png")
  ImageResource verticalNext();

  /**
   * The vertical prev image
   * 
   * @return {@link ImageResource} the vertical prev image
   */
  @Source("vertical-prev.png")
  ImageResource verticalPrev();
}
