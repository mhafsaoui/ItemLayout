ItemLayout Vaadin Addon
==========

ItemLayout offers a way to generate components from data container. Generated components will be displayed in custom layouts such as Grid, Vertical or horizontal.

To achieve that three custom components are available :

 - ItemGrid 
        - use a gwt Grid to display items
        - column number can be specify

 - ItemHorizontal 
        - use a gwt HorizonalPanel to display items
        - use a custom scroll bar
        - scroll interval can be specify
        - first index to display can be specify 

 - ItemVertical (same as Horizontal but on vertical way)
        - use a gwt VerticalPanel to display items
        - use a custom scroll bar
        - scroll interval can be specify
        - first index to display can be specify 

Each component handle single or multi-select item, to do so you just add an ItemClickHandler.

USE: You have to define two dependencies in your pom, one for addon jar and one for addon source (see screenshot bellow)ies
