package bg.warehouse_app.data.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class CreateItemDTO {

    @Length(max = 50, message = "The name cannot exceed more than 50 characters.")
    @NotBlank(message = "The name cannot be blank.")
    @NotNull(message = "The name cannot be empty.")
    private String name;

    @Length(max = 2000, message = "The description cannot exceed more than 2000 characters.")
    private String description;

    //TODO: Implement object file - jpeg, jpg & png only

    @NotNull(message = "The purchase price cannot be empty.")
    private double purchasePrice;

    @NotNull(message = "The selling price cannot be empty.")
    private double sellingPrice;

    @PositiveOrZero(message = "The quantity must be a positive number or zero.")
    @NotNull(message = "The quantity cannot be empty.")
    private int quantity;

    @NotNull(message = "The category cannot be null.")
    private String category;

    @NotBlank(message = "The code cannot be blank.")
    @NotNull(message = "The code cannot be empty.")
    private String code;

}
