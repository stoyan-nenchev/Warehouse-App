package bg.warehouse_app.views.form;

import bg.warehouse_app.data.dto.ItemDTO;
import bg.warehouse_app.data.enums.ItemCategory;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

import java.util.List;

public class ItemForm extends FormLayout {

    private ItemDTO itemDTO;
    private final Binder<ItemDTO> binder = new BeanValidationBinder<>(ItemDTO.class);
    private TextField name = new TextField("Name");
    private TextArea description = new TextArea("Description");
    private NumberField purchasePrice = new NumberField("Purchase price");
    private NumberField sellingPrice = new NumberField("Selling price");
    private IntegerField quantity = new IntegerField("Quantity");
    private ComboBox<String> category = new ComboBox<>("Category");
    private TextField code = new TextField("Code");

    public ItemForm(List<ItemCategory> itemCategories) {
        setResponsiveSteps(
                // Use one column by default
                new ResponsiveStep("0", 1));

        itemCategories.remove(ItemCategory.ALL);

        category.setItems(itemCategories.stream().map(ItemCategory::getLabel).toList());

        binder.bindInstanceFields(this);

        add(
                name,
                description,
                purchasePrice,
                sellingPrice,
                quantity,
                category,
                code
        );
    }

    public void setItem(ItemDTO itemDTO) {
        this.itemDTO = itemDTO;
        binder.readBean(itemDTO);
    }

    public ItemDTO getItem() {
        return this.itemDTO;
    }
}

