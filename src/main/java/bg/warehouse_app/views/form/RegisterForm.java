package bg.warehouse_app.views.form;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

public class RegisterForm extends FormLayout {

    private TextField username = new TextField("Username");
    private PasswordField password = new PasswordField("Password");
    private EmailField email = new EmailField("Email");
    private TextField phoneNumber = new TextField("Phone Number");

    public RegisterForm() {
        setResponsiveSteps(
                // Use one column by default
                new ResponsiveStep("0", 1));

        add(username, password, email, phoneNumber);
    }

}
