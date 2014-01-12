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

import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinRequest;
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
		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(new Label("Demo for ItemGrid"));
		// Layout to show examples
		HorizontalLayout example = new HorizontalLayout();
		layout.addComponent(example);

		// No selection example
		VerticalLayout noSelectionLayout = new VerticalLayout();
		noSelectionLayout.setMargin(true);
		example.addComponent(noSelectionLayout);
		noSelectionLayout.addComponent(new Label("No selection"));
		ItemGrid noSelection = buildDefaultItemGrid();
		noSelectionLayout.addComponent(noSelection);

		// Single selection example
		VerticalLayout singleSelectionLayout = new VerticalLayout();
		singleSelectionLayout.setMargin(true);
		example.addComponent(singleSelectionLayout);
		singleSelectionLayout.addComponent(new Label("Single selection"));
		ItemGrid singleSelection = buildDefaultItemGrid();
		singleSelection.setSelectable(true);
		singleSelectionLayout.addComponent(singleSelection);

		// Multi selection example
		VerticalLayout multiSelectionLayout = new VerticalLayout();
		multiSelectionLayout.setMargin(true);
		example.addComponent(multiSelectionLayout);
		multiSelectionLayout.addComponent(new Label("Multi selection"));
		ItemGrid multiSelection = buildDefaultItemGrid();
		multiSelection.setSelectable(true);
		multiSelection.setMultiSelect(true);
		multiSelectionLayout.addComponent(multiSelection);

		setContent(layout);
	}

	private ItemGrid buildDefaultItemGrid()
	{
		ItemGrid singleSelection = new ItemGrid();
		IndexedContainer container = new IndexedContainer();
		container.addContainerProperty("caption", String.class, null);
		container.addContainerProperty("description", String.class, null);
		singleSelection.setContainerDataSource(container);
		for (int i = 0; i < 25; i++)
		{
			Object itemId = container.addItem();
			container.getContainerProperty(itemId, "caption").setValue("Item " + i);
			container.getContainerProperty(itemId, "description").setValue("Item at index " + i);
		}
		return singleSelection;
	}
}