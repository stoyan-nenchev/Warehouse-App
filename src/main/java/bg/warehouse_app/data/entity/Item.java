package bg.warehouse_app.data.entity;

import bg.warehouse_app.data.enums.ItemCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
public class Item extends AbstractEntity {

    private String name;

    private String description;

    //TODO: Implement object file - jpeg, jpg & png only

    private double purchasePrice;

    private double sellingPrice;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private ItemCategory category;

    @Column(unique = true)
    private String code;
}
