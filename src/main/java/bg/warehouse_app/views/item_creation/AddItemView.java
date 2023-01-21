package bg.warehouse_app.views.item_creation;

import bg.warehouse_app.data.dto.CreateItemDTO;
import bg.warehouse_app.data.enums.ItemCategory;
import bg.warehouse_app.data.service.ItemService;
import bg.warehouse_app.views.MainLayout;
import bg.warehouse_app.views.form.ItemForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.EnumSet;

@PageTitle("Add Item")
@Route(value = "add-item", layout = MainLayout.class)
@PermitAll
public class AddItemView extends VerticalLayout {

    private ItemForm itemForm;
    private final ItemService itemService;
    private final Binder<CreateItemDTO> binder = new BeanValidationBinder<>(CreateItemDTO.class);

    public AddItemView(ItemService itemService) {
        this.itemService = itemService;
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        add(configureItemForm(), configureItemFormButtons());
    }

    private Component configureItemFormButtons() {
        Button addButton = new Button("Add");
        Button cancelButton = new Button("Cancel");

        addButton.addClickListener(event -> addItemToWarehouse());
        cancelButton.addClickListener(event -> cancelButton.getUI().ifPresent(
                ui -> ui.navigate("warehouse-dashboard")));
        return new HorizontalLayout(addButton, cancelButton);
    }

    private Component configureItemForm() {
        itemForm = new ItemForm(new ArrayList<>(EnumSet.allOf(ItemCategory.class)));
        binder.bindInstanceFields(itemForm);

        return new HorizontalLayout(itemForm);
    }

    private void addItemToWarehouse() {
        try {
            CreateItemDTO createItemDTO = new CreateItemDTO();
            binder.writeBean(createItemDTO);
            itemService.create(createItemDTO);
            Notification notification = Notification
                    .show("Item added successfully to warehouse!");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } catch (ValidationException e) {
            e.printStackTrace();
            Notification notification = Notification
                    .show("Something went wrong and the item was not added to warehouse!");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

}
