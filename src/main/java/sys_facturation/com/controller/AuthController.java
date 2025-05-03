package sys_facturation.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sys_facturation.com.dto.AuthRequest;
import sys_facturation.com.dto.AuthResponse;
import sys_facturation.com.security.JwtUtil;
import sys_facturation.com.security.UserDetailsServiceImpl;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            // Intentar autenticar al usuario
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsuario(),
                            request.getPassword()
                    )
            );

            // Si la autenticación es exitosa, cargar los detalles del usuario
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsuario());
            String token = jwtUtil.generateToken(userDetails);

            // Devolver el token
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException ex) {
            // Si las credenciales son incorrectas, podemos suponer que es la contraseña incorrecta
            return ResponseEntity.status(401).body("Contraseña incorrecta.");
        } catch (UsernameNotFoundException ex) {
            // Si el usuario no es encontrado, devolver un mensaje específico
            return ResponseEntity.status(401).body("Usuario no encontrado.");
        } catch (Exception ex) {
            // Captura cualquier otro tipo de excepción
            return ResponseEntity.status(500).body("Error de autenticación. Intente nuevamente.");
        }
    }

    @GetMapping("/generate-temp-password")
    public ResponseEntity<?> generateTempPassword() {
        // 1. Nombre de usuario ficticio
        String username = "admin";

        // 2. Generar contraseña aleatoria (texto plano)
        String rawPassword = "admin"; // Ejemplo: "A1b2C3d4"

        // 3. Encriptar la contraseña (la que guardarás en la BD)
        String encodedPassword = new BCryptPasswordEncoder().encode(rawPassword);

        // 4. Construir un UserDetails para pasarlo a JwtUtil
        UserDetails userDetails = User.withUsername(username)
                .password(encodedPassword)
                .roles("Administrador")
                .build();

        // 5. Usar JwtUtil para generar el token
        String token = jwtUtil.generateToken(userDetails);

        // 6. Mostrar info (puedes loguearlo si no quieres devolver raw password)
        Map<String, String> response = new HashMap<>();
        response.put("usuario", username);
        response.put("password_raw", rawPassword);
        response.put("password_encriptada", encodedPassword);
        response.put("token", token);

        return ResponseEntity.ok(response);
    }
}
