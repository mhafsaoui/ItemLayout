package org.vaadin.addon.itemlayout.layout;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

/**
 * Default component generator. Contains a label with item id and a list of all
 * properties.
 * 
 * @author Guillaume Lamirand
 */
public final class DefaultItemGenerator implements ItemGenerator
{

  /**
   * Default item property id for caption
   */
  private static final String CAPTION          = "caption";
  /**
   * Default item property id for description
   */
  private static final String DESCRIPTION      = "description";
  /**
   * Serail version id
   */
  private static final long   serialVersionUID = 8610707033723162234L;

  /**
   * {@inheritDoc}
   */
  @Override
  public Component generateItem(final AbstractItemLayout pSource, final Object pItemId)
  {
    final Label defaultLabel = new Label(pItemId.toString());
    final Item item = pSource.getContainerDataSource().getItem(pItemId);
    final Property<?> captionProperty = item.getItemProperty(CAPTION);
    if ((captionProperty != null) && (captionProperty.getValue() != null))
    {
      defaultLabel.setValue((String) captionProperty.getValue());
    }
    final Property<?> descriptionProperty = item.getItemProperty(DESCRIPTION);
    if ((descriptionProperty != null) && (descriptionProperty.getValue() != null))
    {
      defaultLabel.setDescription((String) descriptionProperty.getValue());
    }
    return defaultLabel;
  }
}