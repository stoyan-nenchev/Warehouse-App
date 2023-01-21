package bg.warehouse_app.data;

import bg.warehouse_app.data.entity.Item;
import bg.warehouse_app.data.entity.Role;
import bg.warehouse_app.data.enums.ItemCategory;
import bg.warehouse_app.data.enums.UserRole;
import bg.warehouse_app.data.repository.ItemRepository;
import bg.warehouse_app.data.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataGenerator implements ApplicationRunner {

    private final RoleRepository roleRepository;

    private final ItemRepository itemRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createDatabaseRecords();
    }

    private void createDatabaseRecords() {
        generateUserRoles();
        generateItems();
    }

    private void generateItems() {
        if (itemRepository.findAll().isEmpty()) {
            createItem("Beef", "Grounded beef with spices.", 9.22, 12.50, 20, ItemCategory.FOOD_STOCK, "b44fs");
            createItem("Beef", "Grounded beef without spices.", 7.50, 10.00, 25, ItemCategory.FOOD_STOCK, "b44f");
            createItem("Turkey", "Turkey meet without spices.", 6.00, 8.00, 34, ItemCategory.FOOD_STOCK, "turk3y");
            createItem("Turkey", "Turkey meet with spices.", 8.00, 10.00, 23, ItemCategory.FOOD_STOCK, "turk3ys");
            createItem("Bread", "Freshly baked bread.", 1.00, 1.50, 40, ItemCategory.FOOD_STOCK, "br33d");
            createItem("Salt", "Salt - 1kg units.", 0.50, 0.70, 54, ItemCategory.FOOD_STOCK, "s4lt");
            createItem("Pork", "Pork-chops with seasoning.", 8.50, 11.20, 30, ItemCategory.FOOD_STOCK, "p0rks");
            createItem("Salmon", "Salmon fish - catch of the day.", 7.00, 10.00, 24, ItemCategory.FOOD_STOCK, "f1sh");
            createItem("Chocolate", "Chocolate bars.", 1.20, 1.70, 70, ItemCategory.FOOD_STOCK, "ch0c0");
            createItem("Paper", "Sheets of paper, suitable for printing.", 0.10, 0.20, 1000, ItemCategory.OFFICE_MATERIALS, "p4p3r");
            createItem("Printer cartridges", "Standard cartridges for most printers.", 8.00, 10.50, 32, ItemCategory.OFFICE_MATERIALS, "c4rtr1dg");
            createItem("Pen", "Pencil with blue ink.", 0.20, 0.50, 26, ItemCategory.OFFICE_MATERIALS, "p3n");
            createItem("Coffee", "Grounded coffee - price per 1kg..", 12.00, 19.00, 43, ItemCategory.FOOD_STOCK, "c0ff33");
            createItem("Plastic cup", "Plastic cup which can hold up to 500ml liquid.", 0.15, 0.20, 43, ItemCategory.OFFICE_MATERIALS, "cUp");
            createItem("Carton cup", "Carton cup which can hold up to 500ml liquid.", 0.18, 0.23, 41, ItemCategory.OFFICE_MATERIALS, "c4rtcUp");
            createItem("Office chair", "Office chair without arm rests.", 130, 160, 18, ItemCategory.OFFICE_MATERIALS, "0fch41r");
            createItem("Office chair", "Office chair with arm rests.", 150, 199, 12, ItemCategory.OFFICE_MATERIALS, "0fch41rw4rm");
            createItem("Desk", "Office desk with: height - 74cm, width - 152cm and depth 76cm.", 80, 100, 53, ItemCategory.OFFICE_MATERIALS, "d3sk");
            createItem("Mouse", "Normal office mouse for a pc and/or laptop.", 20, 25, 16, ItemCategory.OFFICE_MATERIALS, "m0us");
            createItem("Wireless Mouse", "Wireless office mouse for a pc and/or laptop.", 28, 35, 12, ItemCategory.OFFICE_MATERIALS, "w1f1m0us");
            createItem("Brick", "Bricks suitable for building houses.", 4.50, 6.00, 67, ItemCategory.BUILDING_MATERIALS, "br4ks");
            createItem("Concrete", "1kg of concrete.", 6.00, 9.00, 87, ItemCategory.BUILDING_MATERIALS, "c0ncr33t");
            createItem("Wood", "Wood logs for building.", 16, 19, 125, ItemCategory.BUILDING_MATERIALS, "w00d");
            createItem("Dark Wood", "Dark wood logs for building from oak trees.", 19, 25, 115, ItemCategory.BUILDING_MATERIALS, "d4rkw00d");
            createItem("Light Wood", "Light wood logs for building from tulip trees.", 25, 31, 53, ItemCategory.BUILDING_MATERIALS, "l1ghtw00d");
        }
    }

    private void generateUserRoles() {
        Optional<Role> adminRole = roleRepository.findByName(UserRole.ROLE_ADMIN);
        if (adminRole.isEmpty()) {
            roleRepository.save(new Role(UserRole.ROLE_ADMIN));
        }

        Optional<Role> moderatorRole = roleRepository.findByName(UserRole.ROLE_MODERATOR);
        if (moderatorRole.isEmpty()) {
            roleRepository.save(new Role(UserRole.ROLE_MODERATOR));
        }
    }

    private void createItem(String name,
                            String description,
                            double purchasePrice,
                            double sellingPrice,
                            int quantity,
                            ItemCategory category,
                            String code
    ) {
        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setPurchasePrice(purchasePrice);
        item.setSellingPrice(sellingPrice);
        item.setQuantity(quantity);
        item.setCategory(category);
        item.setCode(code);
        itemRepository.save(item);
    }
}
