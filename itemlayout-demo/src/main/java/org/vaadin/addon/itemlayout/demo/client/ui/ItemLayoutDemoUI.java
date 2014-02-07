/**
 * NovaForge(TM) is a web-based forge offering a Collaborative Development and
 * Project Management Environment.
 *
 * Copyright (C) 2007-2012  BULL SAS
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
package org.vaadin.addon.itemlayout.demo.client.ui;

import org.vaadin.addon.itemlayout.grid.ItemGrid;
import org.vaadin.addon.itemlayout.horizontal.ItemHorizontal;
import org.vaadin.addon.itemlayout.vertical.ItemVertical;

import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * *
 * 
 * @author Guillaume Lamirand
 */
public class ItemLayoutDemoUI extends UI
{

  /**
   * Serial version id
   */
  private static final long serialVersionUID = -7764280046700991233L;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void init(final VaadinRequest request)
  {
    // Main layout
    final VerticalLayout layout = new VerticalLayout();
    layout.addComponent(new Label("Demo for ItemLayout addon"));
    // final Component itemGrid = initItemGridExamples();
    // layout.addComponent(itemGrid);
    final Component itemHorizontal = initItemHorizontalExamples();
    layout.addComponent(itemHorizontal);
    // final Component itemVertical = initItemVerticalExamples();
    // layout.addComponent(itemVertical);

    setContent(layout);
  }

  private Component initItemGridExamples()
  {
    // Layout to show examples
    final HorizontalLayout example = new HorizontalLayout();
    example.addComponent(new Label("Demo for ItemGrid"));

    // No selection example
    final VerticalLayout noSelectionLayout = new VerticalLayout();
    noSelectionLayout.setMargin(true);
    example.addComponent(noSelectionLayout);
    noSelectionLayout.addComponent(new Label("No selection"));
    final ItemGrid noSelection = buildDefaultItemGrid();
    noSelectionLayout.addComponent(noSelection);

    // Single selection example
    final VerticalLayout singleSelectionLayout = new VerticalLayout();
    singleSelectionLayout.setMargin(true);
    example.addComponent(singleSelectionLayout);
    singleSelectionLayout.addComponent(new Label("Single selection"));
    final ItemGrid singleSelection = buildDefaultItemGrid();
    singleSelection.setSelectable(true);
    singleSelectionLayout.addComponent(singleSelection);

    // Multi selection example
    final VerticalLayout multiSelectionLayout = new VerticalLayout();
    multiSelectionLayout.setMargin(true);
    example.addComponent(multiSelectionLayout);
    multiSelectionLayout.addComponent(new Label("Multi selection"));
    final ItemGrid multiSelection = buildDefaultItemGrid();
    multiSelection.setSelectable(true);
    multiSelection.setMultiSelect(true);
    multiSelectionLayout.addComponent(multiSelection);
    return example;
  }

  private ItemGrid buildDefaultItemGrid()
  {
    final ItemGrid item = new ItemGrid();
    final IndexedContainer container = buildDefaultContainer();
    item.setContainerDataSource(container);
    return item;
  }

  private Component initItemVerticalExamples()
  {
    // Layout to show examples
    final VerticalLayout example = new VerticalLayout();
    example.addComponent(new Label("Demo for ItemVertical"));
    final HorizontalLayout exampleLayout = new HorizontalLayout();
    example.addComponent(exampleLayout);

    // No selection example
    final VerticalLayout noSelectionLayout = new VerticalLayout();
    noSelectionLayout.setMargin(true);
    exampleLayout.addComponent(noSelectionLayout);
    noSelectionLayout.addComponent(new Label("No selection"));
    final ItemVertical noSelection = buildDefaultItemVertical();
    noSelectionLayout.addComponent(noSelection);

    // Single selection example
    final VerticalLayout singleSelectionLayout = new VerticalLayout();
    singleSelectionLayout.setMargin(true);
    exampleLayout.addComponent(singleSelectionLayout);
    singleSelectionLayout.addComponent(new Label("Single selection"));
    final ItemVertical singleSelection = buildDefaultItemVertical();
    singleSelection.setSelectable(true);
    singleSelectionLayout.addComponent(singleSelection);

    // Multi selection example
    final VerticalLayout multiSelectionLayout = new VerticalLayout();
    multiSelectionLayout.setMargin(true);
    exampleLayout.addComponent(multiSelectionLayout);
    multiSelectionLayout.addComponent(new Label("Multi selection"));
    final ItemVertical multiSelection = buildDefaultItemVertical();
    multiSelection.setSelectable(true);
    multiSelection.setMultiSelect(true);
    multiSelectionLayout.addComponent(multiSelection);
    return example;
  }

  private Component initItemHorizontalExamples()
  {
    // Layout to show examples
    final VerticalLayout example = new VerticalLayout();
    example.addComponent(new Label("Demo for ItemHorizontal"));

    // No selection example
    final VerticalLayout noSelectionLayout = new VerticalLayout();
    noSelectionLayout.setMargin(true);
    example.addComponent(noSelectionLayout);
    noSelectionLayout.addComponent(new Label("No selection"));
    final ItemHorizontal noSelection = buildDefaultItemHorizontal();
    noSelection.setWidth(100, Unit.PERCENTAGE);
    noSelectionLayout.addComponent(noSelection);

    // Single selection example
    final VerticalLayout singleSelectionLayout = new VerticalLayout();
    singleSelectionLayout.setMargin(true);
    example.addComponent(singleSelectionLayout);
    singleSelectionLayout.addComponent(new Label("Single selection"));
    final ItemHorizontal singleSelection = buildDefaultItemHorizontal();
    singleSelection.setSelectable(true);
    singleSelectionLayout.addComponent(singleSelection);
    //
    // // Multi selection example
    // final VerticalLayout multiSelectionLayout = new VerticalLayout();
    // multiSelectionLayout.setMargin(true);
    // example.addComponent(multiSelectionLayout);
    // multiSelectionLayout.addComponent(new Label("Multi selection"));
    // final ItemHorizontal multiSelection = buildDefaultItemHorizontal();
    // multiSelection.setSelectable(true);
    // multiSelection.setMultiSelect(true);
    // multiSelectionLayout.addComponent(multiSelection);
    return example;
  }

  private ItemHorizontal buildDefaultItemHorizontal()
  {
    final ItemHorizontal item = new ItemHorizontal();
    item.setWidth(600, Unit.PIXELS);
    item.setScrollerIndex(10);
    item.setScrollInterval(3);
    final IndexedContainer container = buildDefaultContainer();
    item.setContainerDataSource(container);
    return item;
  }

  private ItemVertical buildDefaultItemVertical()
  {
    final ItemVertical item = new ItemVertical();
    item.setHeight(300, Unit.PIXELS);
    final IndexedContainer container = buildDefaultContainer();
    item.setContainerDataSource(container);
    return item;
  }

  private IndexedContainer buildDefaultContainer()
  {
    final IndexedContainer container = new IndexedContainer();
    container.addContainerProperty("caption", String.class, null);
    container.addContainerProperty("description", String.class, null);
    for (int i = 0; i < 25; i++)
    {
      final Object itemId = container.addItem();
      container.getContainerProperty(itemId, "caption").setValue("Item " + i);
      container.getContainerProperty(itemId, "description").setValue("Item at index " + i);
    }
    return container;
  }

}