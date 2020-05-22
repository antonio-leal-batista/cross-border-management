package us.lacchain.crossborder.management.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import us.lacchain.crossborder.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import us.lacchain.crossborder.management.model.UserAuthenticated;
import us.lacchain.crossborder.management.model.UserLogin;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserRepository userRepository;

    public CustomAuthenticationProvider() {
        super();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            final String username = authentication.getName();
            final String password = authentication.getCredentials().toString();

            UserLogin user = userRepository.getUserLogin(username,password);

            System.out.println("username:"+user.getEmail());
            System.out.println("password:"+user.getPassword());

            final List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority(user.getRole()));
            
            Map<String,Object> additionalInfo = new HashMap<>();
            additionalInfo.put("email",user.getEmail());
            additionalInfo.put("dltAddress",user.getDltAddress());
        
            final User userLogged = new User(username, (String) authentication.getCredentials(), grantedAuths);
            final UserAuthenticated userAuth = new UserAuthenticated(userLogged, additionalInfo);

            return new UsernamePasswordAuthenticationToken(userAuth, password, grantedAuths);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}