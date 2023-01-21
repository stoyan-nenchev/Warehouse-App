package bg.warehouse_app.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ItemDTO {

    private UUID id;
    private String name;
    private String description;
    //TODO: Implement object file - jpeg, jpg & png only
    private double purchasePrice;
    private double sellingPrice;
    private int quantity;
    private String category;
    private String code;
}
