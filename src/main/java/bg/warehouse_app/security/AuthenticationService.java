package bg.warehouse_app.security;

import bg.warehouse_app.data.dto.CreateUserDTO;
import bg.warehouse_app.data.entity.Role;
import bg.warehouse_app.data.entity.User;
import bg.warehouse_app.data.enums.UserRole;
import bg.warehouse_app.data.mapper.UserMapper;
import bg.warehouse_app.data.repository.RoleRepository;
import bg.warehouse_app.data.repository.UserRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private static final String LOGOUT_SUCCESS_URL = "login";

    public void register(CreateUserDTO createUserDTO) {
        if (userRepository.findByUsername(createUserDTO.getUsername()).isPresent()) {
            throw new RuntimeException("User with the given email already exists.");
        }
        Optional<Role> adminRole = roleRepository.findByName(UserRole.ROLE_ADMIN);

        User user = userMapper.toEntity(createUserDTO, passwordEncoder);
        user.getRoles().add(adminRole.get());

        userRepository.save(user);
    }

    private Optional<Authentication> getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        return Optional.ofNullable(context.getAuthentication())
                .filter(authentication -> !(authentication instanceof AnonymousAuthenticationToken));
    }

    public Optional<User> get() {
        return getAuthentication().map(authentication -> userRepository.findByUsername(authentication.getName()).get());
    }

    public void logout() {
        UI.getCurrent().getPage().setLocation(LOGOUT_SUCCESS_URL);
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
    }
}
