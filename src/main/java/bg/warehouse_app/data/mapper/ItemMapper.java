package bg.warehouse_app.data.mapper;

import bg.warehouse_app.common.exception.InvalidItemCategoryException;
import bg.warehouse_app.data.dto.CreateItemDTO;
import bg.warehouse_app.data.dto.ItemDTO;
import bg.warehouse_app.data.entity.Item;
import bg.warehouse_app.data.enums.ItemCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import static bg.warehouse_app.data.enums.ItemCategory.BUILDING_MATERIALS;
import static bg.warehouse_app.data.enums.ItemCategory.FOOD_STOCK;
import static bg.warehouse_app.data.enums.ItemCategory.OFFICE_MATERIALS;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "category", qualifiedByName = "mapItemCategory")
    Item toEntity(CreateItemDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "category", qualifiedByName = "mapItemCategory")
    void intoEntity(ItemDTO itemDTO, @MappingTarget Item item);

    @Mapping(target = "category", expression = "java(item.getCategory().getLabel())")
    ItemDTO toDTO(Item item);

    @Named("mapItemCategory")
    default ItemCategory mapItemCategory(String category) {
        if (FOOD_STOCK.getLabel().equals(category)) {
            return FOOD_STOCK;
        } else if (OFFICE_MATERIALS.getLabel().equals(category)) {
            return OFFICE_MATERIALS;
        } else if (BUILDING_MATERIALS.getLabel().equals(category)) {
            return BUILDING_MATERIALS;
        }
        throw new InvalidItemCategoryException(String.format("The category %s is not valid in our system.", category));
    }
}
