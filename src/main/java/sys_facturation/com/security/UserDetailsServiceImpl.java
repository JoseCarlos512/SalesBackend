package sys_facturation.com.security;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sys_facturation.com.entity.User;
import sys_facturation.com.repository.UserDao;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userRepository;

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);


    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("🧪 Buscando usuario con username: {}", username);

        User user = userRepository.findByUsuario(username)
                .orElseThrow(() -> {
                    log.error("❌ Usuario no encontrado: {}", username);
                    return new UsernameNotFoundException("Usuario no encontrado");
                });

        log.info("✅ Usuario encontrado: {}", user.getUsuario());
        log.info("🔐 Contraseña codificada: {}", user.getPassword());
        log.info("🛡 Rol: {}", user.getRol().getNombre());

        return new org.springframework.security.core.userdetails.User(
                user.getUsuario(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRol().getNombre()))
        );
    }
}
