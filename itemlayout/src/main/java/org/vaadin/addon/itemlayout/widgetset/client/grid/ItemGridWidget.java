package org.vaadin.addon.itemlayout.widgetset.client.grid;

import com.google.gwt.user.client.ui.Grid;

/**
 * @author Guillaume Lamirand
 */
public class ItemGridWidget extends Grid
{

	public static final String CLASSNAME = "v-itemlayout-grid";

	public ItemGridWidget()
	{
		super();
		setStyleName(CLASSNAME);
	}
}
