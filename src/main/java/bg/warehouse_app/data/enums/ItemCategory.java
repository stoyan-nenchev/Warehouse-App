package bg.warehouse_app.data.enums;

public enum ItemCategory {
    FOOD_STOCK("Food stock"),
    OFFICE_MATERIALS("Office materials"),
    BUILDING_MATERIALS("Building materials"),
    ALL("All");

    private final String label;

    ItemCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
