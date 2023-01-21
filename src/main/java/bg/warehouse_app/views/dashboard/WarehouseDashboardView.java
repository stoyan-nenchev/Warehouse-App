package bg.warehouse_app.views.dashboard;

import bg.warehouse_app.common.util.StringUtil;
import bg.warehouse_app.data.dto.ItemDTO;
import bg.warehouse_app.data.enums.ItemCategory;
import bg.warehouse_app.data.service.ItemService;
import bg.warehouse_app.views.MainLayout;
import bg.warehouse_app.views.form.ItemForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.vaadin.klaudeta.PaginatedGrid;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

@PageTitle("Warehouse Dashboard")
@Route(value = "warehouse-dashboard", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class WarehouseDashboardView extends VerticalLayout implements BeforeEnterObserver {

    private final ItemService itemService;
    private final PaginatedGrid<ItemDTO, String> grid = new PaginatedGrid<>(ItemDTO.class);
    private final TextField searchByName = new TextField();
    private final TextField searchByCode = new TextField();
    private final ComboBox<ItemCategory> searchByCategory = new ComboBox<>();
    private final Button searchButton = new Button("Search");
    private ItemForm editItemForm;

    public WarehouseDashboardView(ItemService itemService) {
        this.itemService = itemService;
        addClassName("warehouse-dashboard-view");
        setSizeFull();

        configureGrid();
        configureEditItemForm();

        add(
                getToolbar(),
                getContent()
        );

        refreshItems();
        closeEditItemForm();
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.addClassName("item-grid");
        grid.setColumns("name", "description", "purchasePrice", "sellingPrice", "quantity", "category", "code");

        grid.asSingleSelect().addValueChangeListener(event -> editItem(event.getValue()));
        grid.setPageSize(18);
        grid.setPaginatorSize(5);
    }

    private void refreshItems() {
        grid.setItems(itemService.findAllItems());
    }

    private void editItem(ItemDTO value) {
        value = itemService.getItemByCode(value);
        if (Objects.isNull(value)) {
            closeEditItemForm();
        } else {
            editItemForm.setItem(value);
            editItemForm.setVisible(true);
            addClassName("editingItem");
        }
    }

    private void closeEditItemForm() {
        editItemForm.setItem(null);
        editItemForm.setVisible(false);
        removeClassName("editingItem");
    }

    private Component getContent() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(new VerticalLayout(grid), editItemForm);
        horizontalLayout.setFlexGrow(2, grid);
        horizontalLayout.setFlexGrow(1, editItemForm);
        horizontalLayout.setSizeFull();

        return horizontalLayout;
    }

    private Component getToolbar() {
        searchByName.setPlaceholder("Search by name...");
        searchByName.setClearButtonVisible(true);

        searchByCode.setPlaceholder("Search by code...");

        List<ItemCategory> itemCategories = new ArrayList<>(EnumSet.allOf(ItemCategory.class));
        searchByCategory.setItems(itemCategories);
        searchByCategory.setItemLabelGenerator(ItemCategory::getLabel);
        searchByCategory.setValue(ItemCategory.ALL);

        searchButton.addClickListener(click ->
                searchItems(searchByName.getValue(), searchByCode.getValue(), searchByCategory.getValue().getLabel()));

        HorizontalLayout toolbar = new HorizontalLayout(searchByName, searchByCode, searchByCategory, searchButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void searchItems(String name, String code, String category) {
        grid.setItems(itemService.search(name, code, category));
    }

    private void configureEditItemForm() {
        editItemForm = new ItemForm(new ArrayList<>(EnumSet.allOf(ItemCategory.class)));
        editItemForm.setWidth("25em");
        editItemForm.setClassName("editItemForm");

        Button updateButton = new Button("Edit", click -> updateItem(editItemForm));
        Button deleteButton = new Button("Delete", click -> deleteItem(editItemForm));
        Button cancelButton = new Button("Cancel", click -> closeEditItemForm());
        editItemForm.add(new HorizontalLayout(updateButton, deleteButton, cancelButton));
    }

    private void updateItem(ItemForm editItemForm) {
        try {
            ItemDTO itemDTO = editItemForm.getItem();
            ConfirmDialog confirmDialog = new ConfirmDialog();
            confirmDialog.setText("Are you sure you want to update this item?");
            confirmDialog.setCancelable(true);
            confirmDialog.addConfirmListener(confirm -> {
                itemService.update(itemDTO);
                Notification notification = Notification
                        .show("Item was successfully updated!");
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                updateItemResults();
            });
            confirmDialog.open();
        } catch (RuntimeException e) {
            Notification notification = Notification
                    .show(e.getMessage());
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }

    }

    private void deleteItem(ItemForm editItemForm) {
        ItemDTO itemDTO = editItemForm.getItem();
        ConfirmDialog confirmDialog = new ConfirmDialog();
        confirmDialog.setText(
                String.format("Are you sure you want to delete the item %s with code: %s.",
                        itemDTO.getName(), itemDTO.getCode()));
        confirmDialog.setCancelable(true);
        confirmDialog.addConfirmListener(confirm -> {
            itemService.delete(itemDTO);
            updateItemResults();
            closeEditItemForm();
            Notification notification = Notification
                    .show("Item was successfully deleted!");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });
        confirmDialog.open();
    }

    private void updateItemResults() {
        if (StringUtil.isNullOrBlank(searchByName.getValue())
                && StringUtil.isNullOrBlank(searchByCode.getValue())
                && searchByCategory.getValue().getLabel().equals("All")) {
            refreshItems();
        } else {
            searchItems(searchByName.getValue(), searchByCode.getValue(), searchByCategory.getValue().getLabel());
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        UI.getCurrent().navigate("warehouse-dashboard");
    }
}
