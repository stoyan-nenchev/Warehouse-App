package bg.warehouse_app.common.util;

import org.apache.commons.lang3.StringUtils;

import static bg.warehouse_app.data.enums.ItemCategory.ALL;
import static bg.warehouse_app.data.enums.ItemCategory.BUILDING_MATERIALS;
import static bg.warehouse_app.data.enums.ItemCategory.FOOD_STOCK;
import static bg.warehouse_app.data.enums.ItemCategory.OFFICE_MATERIALS;

public class StringUtil extends StringUtils {

    protected StringUtil(){}

    public static boolean isNullOrBlank(String string) {
        return string == null || string.isBlank();
    }

    public static String matchCategoryByLabel(String label) {
        if (FOOD_STOCK.getLabel().equals(label)) {
            return FOOD_STOCK.toString();
        } else if (OFFICE_MATERIALS.getLabel().equals(label)) {
            return OFFICE_MATERIALS.toString();
        } else if (BUILDING_MATERIALS.getLabel().equals(label)) {
            return BUILDING_MATERIALS.toString();
        } else {
            return ALL.getLabel();
        }
    }
}
