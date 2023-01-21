package bg.warehouse_app.data.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateUserDTO {

    @NotBlank(message = "The username field must be populated.")
    @Pattern(regexp = "^[A-Za-z_]+$", message = "The username can only contain letters and '_'.")
    @Size(min = 5, max = 15, message = "The username must be between 5 and 15 characters.")
    private String username;
    @NotBlank(message = "The password field must be populated.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@\\-_~|])([a-zA-Z0-9@\\-_~|]+)$",
            message = "The password must contain one uppercase and lowercase letter, a number and" +
                    " a symbol of the following: @,-,_,~ and |.")
    @Size(min = 6, max = 20, message = "The password must be between 6 and 20 characters.")
    private String password;
    @NotBlank(message = "You must enter a email.")
    @Email(message = "You must enter an email with a valid domain.")
    private String email;
    private String phoneNumber;
}
