package bg.warehouse_app.views.register;

import bg.warehouse_app.data.dto.CreateUserDTO;
import bg.warehouse_app.security.AuthenticationService;
import bg.warehouse_app.views.dashboard.WarehouseDashboardView;
import bg.warehouse_app.views.form.RegisterForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Register")
@Route("register")
@AnonymousAllowed
public class RegisterView extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    private AuthenticationService authenticationService;
    private final Binder<CreateUserDTO> createUserBinder = new BeanValidationBinder<>(CreateUserDTO.class);
    
    public RegisterView(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        add(configureUserRegisterForm(), configureUserRegisterFormButtons());
    }

    private Component configureUserRegisterForm() {
        RegisterForm registerForm = new RegisterForm();
        createUserBinder.bindInstanceFields(registerForm);
        return new HorizontalLayout(registerForm);
    }

    private Component configureUserRegisterFormButtons() {
        Button registerButton = new Button("Register");
        Button cancelButton = new Button("Go back");

        registerButton.addClickListener(event -> registerUser());
        cancelButton.addClickListener(event -> cancelButton.getUI().ifPresent(
                ui -> ui.navigate("login")));
        return new HorizontalLayout(registerButton, cancelButton);
    }

    private void registerUser() {
        try {
            CreateUserDTO createUserDTO = new CreateUserDTO();
            createUserBinder.writeBean(createUserDTO);
            authenticationService.register(createUserDTO);
            Notification notification = Notification
                    .show("Your account was successfully created!");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            UI.getCurrent().navigate("login");
        } catch (ValidationException e) {
            Notification notification = Notification
                    .show(e.getMessage());
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (authenticationService.get().isPresent()) {
            event.forwardTo(WarehouseDashboardView.class);
        }
    }
}
