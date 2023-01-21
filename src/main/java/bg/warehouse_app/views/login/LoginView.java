package bg.warehouse_app.views.login;

import bg.warehouse_app.security.AuthenticationService;
import bg.warehouse_app.views.dashboard.WarehouseDashboardView;
import bg.warehouse_app.views.register.RegisterView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Login")
@Route("login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm login = new LoginForm();
    @Autowired
    private AuthenticationService authenticationService;

    public LoginView() {
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");

        add(new H1("Warehouse App"),
                login,
                new Button("Register", click -> UI.getCurrent().navigate(RegisterView.class)));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (authenticationService.get().isPresent()) {
            event.forwardTo(WarehouseDashboardView.class);
        }
        if (event.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }
}
