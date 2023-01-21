package bg.warehouse_app.views;

import bg.warehouse_app.security.AuthenticationService;
import bg.warehouse_app.views.dashboard.WarehouseDashboardView;
import bg.warehouse_app.views.item_creation.AddItemView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

    private final AuthenticationService authenticationService;

    public MainLayout(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Warehouse App");
        logo.addClassNames("text-l", "m-m");

        Button logout = new Button("Log out", e -> authenticationService.logout());

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);

    }

    private void createDrawer() {
        RouterLink warehouseDashboardLink = new RouterLink("Warehouse Dashboard", WarehouseDashboardView.class);
        warehouseDashboardLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                warehouseDashboardLink,
                new RouterLink("Add Item", AddItemView.class)
        ));
    }
}
