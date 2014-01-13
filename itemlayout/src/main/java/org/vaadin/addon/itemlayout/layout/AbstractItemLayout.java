package org.vaadin.addon.itemlayout.layout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.vaadin.addon.itemlayout.event.ItemClickEvent;
import org.vaadin.addon.itemlayout.event.ItemClickListener;
import org.vaadin.addon.itemlayout.widgetset.client.layout.ItemLayoutConstant;
import org.vaadin.addon.itemlayout.widgetset.client.layout.ItemLayoutState;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.server.LegacyCommunicationManager;
import com.vaadin.server.LegacyPaint;
import com.vaadin.server.PaintException;
import com.vaadin.server.PaintTarget;
import com.vaadin.shared.ui.table.TableConstants;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractSingleComponentContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HasComponents;

/**
 * Defines a layout which generate {@link Component} based on {@link Container} and its {@link Item}
 * <p>
 * Components in a {@link AbstractItemLayout} will not have their caption nor icon rendered.
 * </p>
 * 
 * @author Guillaume Lamirand
 */
public abstract class AbstractItemLayout extends AbstractSelect implements HasComponents
{

  /**
   * Serial version id
   */
  private static final long             serialVersionUID   = -5457410734160515489L;

  /**
   * Contains the registered listened properties, so we can do proper cleanup to free
   * memory.
   */
  private final Set<Property<?>>        listenedProperties = new HashSet<Property<?>>();
  /**
   * Contains {@link Component} draw according container items and {@link AbstractItemLayout#generator}
   */
  private Map<Object, Component>        itemsComponent     = new LinkedHashMap<Object, Component>();
  /**
   * Contains the {@link ItemGenerator} used to handle item rendered
   */
  private ItemGenerator                 generator;
  /**
   * Used when layout is currently being painted
   */
  private boolean                       isBeingPainted;
  /**
   * Set to true if the client-side should be informed that the key mapper has
   * been reset so it can avoid sending back references to keys that are no
   * longer present.
   */
  private boolean                       keyMapperReset;
  /**
   * Defined if item component is selectable
   */
  private boolean                       selectable;
  /**
   * Contains list of listener for {@link org.vaadin.addon.itemlayout.event.ItemClickEvent}
   */
  private final List<ItemClickListener> clickListeners     = new ArrayList<ItemClickListener>();

  /**
   * Default constructor.
   * <p>
   * It will used defautl generator
   * </p>
   */
  public AbstractItemLayout()
  {
    super();
    generator = new DefaultItemGenerator();
  }

  /**
   * Notifies the component that it is connected to an application.
   * 
   * @see com.vaadin.ui.Component#attach()
   */

  @Override
  public void attach()
  {
    super.attach();

    updateItemComponents();
  }

  /**
   * Notifies the component that it is detached from the application
   * 
   * @see com.vaadin.ui.Component#detach()
   */

  @Override
  public void detach()
  {
    super.detach();
  }

  /**
   * Notifies this listener that the Property's value has changed.
   * Also listens changes in rendered items to refresh content area.
   * 
   * @see com.vaadin.data.Property.ValueChangeListener#valueChange(Property.ValueChangeEvent)
   */

  @Override
  public void valueChange(final Property.ValueChangeEvent event)
  {
    if ((event.getProperty() == this) || (event.getProperty() == getPropertyDataSource()))
    {
      super.valueChange(event);
    }
    else
    {
      updateItemComponents();
    }
    markAsDirty();
  }

  /**
   * {@inheritDoc}
   */
  @Deprecated
  @Override
  public void requestRepaint()
  {
    markAsDirty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void markAsDirtyRecursive()
  {
    super.markAsDirtyRecursive();

    // Avoid sending a partial repaint (#8714)
    updateItemComponents();
  }

  /**
   * Invoked when the value of a variable has changed.
   * 
   * @see com.vaadin.ui.Select#changeVariables(java.lang.Object, java.util.Map)
   */
  @Override
  public void changeVariables(final Object pSource, final Map<String, Object> pVariables)
  {
    // Item click event
    if (pVariables.containsKey(ItemLayoutConstant.ATTRIBUTE_ITEM_CLICKED_KEY))
    {
      final String key = (String) pVariables.get(ItemLayoutConstant.ATTRIBUTE_ITEM_CLICKED_KEY);
      final Object itemId = itemIdMapper.get(key);
      handleClickEvent(itemId);
    }

    super.changeVariables(pSource, pVariables);
  }

  /*
   * {@inheritDoc}
   * (non-Javadoc)
   * @see com.vaadin.ui.AbstractSelect#paintContent(com.vaadin.
   * terminal.PaintTarget)
   */
  @Override
  public void paintContent(final PaintTarget target) throws PaintException
  {
    isBeingPainted = true;
    try
    {
      doPaintContent(target);
    }
    finally
    {
      isBeingPainted = false;
    }
  }

  private void doPaintContent(final PaintTarget pTarget) throws PaintException
  {
    paintLayoutAttributes(pTarget);
    paintSelectMode(pTarget);
    paintItemClickHandler(pTarget);
    paintItems(pTarget);
    if (keyMapperReset)
    {
      keyMapperReset = false;
      pTarget.addAttribute(TableConstants.ATTRIBUTE_KEY_MAPPER_RESET, true);
    }
  }

  protected abstract void paintLayoutAttributes(final PaintTarget target) throws PaintException;

  private void paintSelectMode(final PaintTarget target) throws PaintException
  {
    if (isSelectable())
    {
      target.addAttribute(ItemLayoutConstant.ATTRIBUTE_SELECTMODE,
          (isMultiSelect() ? ItemLayoutConstant.ATTRIBUTE_SELECTMODE_MULTI
              : ItemLayoutConstant.ATTRIBUTE_SELECTMODE_SINGLE));
    }
    else
    {
      target.addAttribute(ItemLayoutConstant.ATTRIBUTE_SELECTMODE,
          ItemLayoutConstant.ATTRIBUTE_SELECTMODE_NONE);
    }
    if (!isNullSelectionAllowed())
    {
      target.addAttribute(ItemLayoutConstant.ATTRIBUTE_NULL_SELECTION_ALLOWED, false);
    }

    // selection support
    // The select variable is only enabled if selectable
    if (isSelectable())
    {
      target.addVariable(this, ItemLayoutConstant.ATTRIBUTE_SELECTED, findSelectedKeys());
    }
  }

  private void paintItemClickHandler(final PaintTarget pTarget) throws PaintException
  {
    pTarget.addVariable(this, ItemLayoutConstant.ATTRIBUTE_ITEM_CLICK_HANDLER, !clickListeners.isEmpty());
  }

  private String[] findSelectedKeys()
  {
    final LinkedList<String> selectedKeys = new LinkedList<String>();
    if (isMultiSelect())
    {
      final HashSet<?> sel = new HashSet<Object>((Set<?>) getValue());
      final Collection<?> vids = getVisibleItemIds();
      for (final Object id : vids)
      {
        if (sel.contains(id))
        {
          selectedKeys.add(itemIdMapper.key(id));
        }
      }
    }
    else
    {
      Object value = getValue();
      if (value == null)
      {
        value = getNullSelectionItemId();
      }
      if (value != null)
      {
        selectedKeys.add(itemIdMapper.key(value));
      }
    }
    return selectedKeys.toArray(new String[selectedKeys.size()]);
  }

  @SuppressWarnings("deprecation")
  private void paintItems(final PaintTarget target) throws PaintException
  {
    target.startTag(ItemLayoutConstant.ATTRIBUTE_ITEMS);
    for (final Entry<Object, Component> entry : itemsComponent.entrySet())
    {
      if (LegacyCommunicationManager.isComponentVisibleToClient(entry.getValue()))
      {
        target.startTag(ItemLayoutConstant.ATTRIBUTE_ITEM);
        target.addAttribute(ItemLayoutConstant.ATTRIBUTE_ITEM_KEY, itemIdMapper.key(entry.getKey()));
        LegacyPaint.paint(entry.getValue(), target);
        target.endTag(ItemLayoutConstant.ATTRIBUTE_ITEM);
      }
    }
    target.endTag(ItemLayoutConstant.ATTRIBUTE_ITEMS);

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void containerItemSetChange(final Container.ItemSetChangeEvent event)
  {
    if (isBeingPainted)
    {
      return;
    }

    super.containerItemSetChange(event);

    // super method clears the key map, must inform client about this to
    // avoid getting invalid keys back (#8584)
    keyMapperReset = true;

    updateItemComponents();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void containerPropertySetChange(final Container.PropertySetChangeEvent event)
  {
    if (isBeingPainted)
    {
      return;
    }
    super.containerPropertySetChange(event);
    updateItemComponents();

  }

  protected void updateItemComponents()
  {
    if (!isAttached())
    {
      return;
    }

    // Keep old reference
    final Set<Property<?>> oldListenedProperties = listenedProperties;
    final Set<Component> oldVisibleComponents = new HashSet<Component>(itemsComponent.values());
    itemsComponent = new HashMap<Object, Component>();

    // Atatch the new ones
    for (final Object itemId : getItemIds())
    {
      generateItemComponent(itemId);
    }

    // Detach existing component
    unregisterPropertiesAndComponents(oldListenedProperties, oldVisibleComponents);

    markAsDirty();
  }

  protected void generateItemComponent(final Object itemId)
  {
    final Component c = generator.generateItem(this, itemId);
    registerComponent(itemId, c);
    listenProperties(itemId);
  }

  protected void registerComponent(final Object pItemId, final Component pComponent)
  {
    if (pComponent instanceof ComponentContainer)
    {
      // Make sure we're not adding the component inside it's own content
      for (Component parent = this; parent != null; parent = parent.getParent())
      {
        if (parent == pComponent)
        {
          throw new IllegalArgumentException("Component cannot be added inside it's own content");
        }
      }
    }

    if (pComponent.getParent() != null)
    {
      // If the component already has a parent, try to remove it
      AbstractSingleComponentContainer.removeFromParent(pComponent);
    }

    pComponent.setParent(this);
    fireEvent(new ComponentAttachEvent(this, pComponent));
    itemsComponent.put(pItemId, pComponent);
  }

  private void listenProperties(final Object pItemId)
  {
    for (final Object propId : getContainerPropertyIds())
    {
      final Property<?> prop = getContainerProperty(pItemId, propId);
      if ((prop instanceof Property.ValueChangeNotifier) && !listenedProperties.contains(prop))
      {
        ((Property.ValueChangeNotifier) prop).addValueChangeListener(this);
        listenedProperties.add(prop);
      }
    }
  }

  /**
   * Helper method to remove listeners and maintain correct component
   * hierarchy. Detaches properties and components if those are no more
   * rendered in client.
   * 
   * @param pOldListenedProperties
   *          set of properties that where listened in last render
   * @param pOldVisibleComponents
   *          set of components that where attached in last render
   */
  private void unregisterPropertiesAndComponents(final Set<Property<?>> pOldListenedProperties,
      final Set<Component> pOldVisibleComponents)
  {
    if (pOldVisibleComponents != null)
    {
      final Set<Component> visibleComponents = new HashSet<Component>(itemsComponent.values());
      for (final Component c : pOldVisibleComponents)
      {
        if (!visibleComponents.contains(c))
        {
          unregisterComponent(c);
        }
      }
    }

    if (pOldListenedProperties != null)
    {
      for (final Property<?> property : pOldListenedProperties)
      {
        final Property.ValueChangeNotifier o = (ValueChangeNotifier) property;
        if (listenedProperties.contains(o) == false)
        {
          o.removeValueChangeListener(this);
        }
      }
    }
  }

  protected void unregisterComponent(final Component c)
  {
    if (c.getParent() == this)
    {
      c.setParent(null);
      fireEvent(new ComponentDetachEvent(this, c));
    }
  }

  /**
   * Adds an Item click listener for the object.
   */
  public void addItemClickListener(final ItemClickListener pListener)
  {
    clickListeners.add(pListener);
  }

  /**
   * Removes an Item click listener for the object.
   */
  public void removeItemClickListener(final ItemClickListener pListener)
  {
    if (clickListeners.contains(pListener))
    {
      clickListeners.remove(pListener);
    }
  }

  /**
   * Called when a item has been click
   * 
   * @param pEvent
   */
  protected void fireItemClick(final ItemClickEvent pEvent)
  {
    for (final ItemClickListener listener : clickListeners)
    {
      listener.itemClick(pEvent);
    }
    markAsDirty();
  }

  /**
   * Handles click event
   * 
   * @param item
   *          id
   *          item id selected
   */
  private void handleClickEvent(final Object pItemId)
  {
    if ((isSelectable()) && (pItemId != null))
    {
      final boolean isAlreadySelected = isSelected(pItemId);
      if (isAlreadySelected)
      {
        unselect(pItemId);
      }
      else
      {
        select(pItemId);
      }
      fireItemClick(new ItemClickEvent(pItemId, !isAlreadySelected));

    }
  }

  /**
   * Getter for property selectable.
   * <p>
   * The item layout is not selectable by default.
   * </p>
   * 
   * @return the Value of property selectable.
   */
  public boolean isSelectable()
  {
    return selectable;
  }

  /**
   * Setter for property selectable.
   * <p>
   * The item layout is not selectable by default.
   * </p>
   * 
   * @param pSelectable
   *          the New value of property selectable.
   */
  public void setSelectable(final boolean pSelectable)
  {
    if (selectable != pSelectable)
    {
      selectable = pSelectable;
      markAsDirty();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ItemLayoutState getState()
  {
    return (ItemLayoutState) super.getState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<Component> iterator()
  {
    return itemsComponent.values().iterator();
  }

  /**
   * Returns item identifiers of the items which are currently rendered on the
   * client.
   * 
   * @see com.vaadin.ui.Select#getVisibleItemIds()
   */
  @Override
  public Collection<?> getVisibleItemIds()
  {

    final LinkedList<Object> visible = new LinkedList<Object>();

    final Collection<?> itemIds = super.getItemIds();
    for (final Object itemId : itemIds)
    {
      if (((itemsComponent.containsKey(itemId)) && (itemsComponent.get(itemId).isVisible())))
      {
        visible.add(itemId);
      }
    }
    return visible;
  }

  /**
   * Set the item component generator to the {@link AbstractItemLayout}.
   * 
   * @param pItemGenerator
   *          the {@link ItemGenerator} to use
   */
  public void setItemGenerator(final ItemGenerator pItemGenerator)
  {
    if (pItemGenerator == null)
    {
      throw new IllegalArgumentException("Can not add null as a ItemGenerator");
    }
    generator = pItemGenerator;
  }

  /**
   * Adding new items is not supported.
   * 
   * @throws UnsupportedOperationException
   *           if set to true.
   * @see com.vaadin.ui.Select#setNewItemsAllowed(boolean)
   */

  @Override
  public void setNewItemsAllowed(final boolean allowNewOptions) throws UnsupportedOperationException
  {
    if (allowNewOptions)
    {
      throw new UnsupportedOperationException();
    }
  }

}
