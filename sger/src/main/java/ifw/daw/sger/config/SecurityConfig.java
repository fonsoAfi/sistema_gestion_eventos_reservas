package ifw.daw.sger.config;

import ifw.daw.sger.models.Usuarios;
import ifw.daw.sger.repositories.AuthRepository;
import ifw.daw.sger.repositories.OrganizadorRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	
	@Autowired
	AuthRepository authRepo;
	
	@Autowired
	OrganizadorRepository organizadorRepo;

	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) 
			throws Exception{
		httpSecurity.headers(
						headersConfigurer -> headersConfigurer
						.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
		
		httpSecurity.csrf(csrf -> csrf
	            	.ignoringRequestMatchers("/webhook/**", "/logout"));
		
		httpSecurity.anonymous(Customizer.withDefaults())
					.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
					
					.logout(logout -> logout
							.logoutUrl("/logout")
							.logoutSuccessUrl("/?logout=true")
							.invalidateHttpSession(true)
							.deleteCookies("JSESSIONID")
							.permitAll())
					
					.formLogin(formLogin -> formLogin
							.loginPage("/auth/login")
							.loginProcessingUrl("/auth/login")
							.usernameParameter("mail")
							.passwordParameter("clave")
							.successHandler(authenticationSuccessHandler())
							.failureHandler(authenticationFailureHandler())
							.permitAll()
					)
					
					.passwordManagement(passwordManagement -> passwordManagement
							.changePasswordPage("/profile/cambiar_clave")
					)
					
					.authorizeHttpRequests(
						auth -> auth
						.requestMatchers("/", "/auth/login", "/auth/login/**" , "/infoLegal", "/logout", "/fonts/**", "/images/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()	// Solo van las rutas que usan exclusivamente GET
						.requestMatchers("/auth/", "/auth/registrarse").permitAll()
						.requestMatchers("/webhook/**").permitAll()
						.requestMatchers("/profile/asistente", "/profile/asistente/**").hasRole("ASISTENTES")
						.requestMatchers("/profile/organizador", "/profile/organizador/**").hasRole("ORGANIZADORES")
						.requestMatchers("/profile/administrador", "/profile/administrador/**").hasRole("ADMINISTRADORES")
						.requestMatchers("/profile/**").authenticated()
						.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
						.anyRequest().authenticated()
						)
					
						.httpBasic(Customizer.withDefaults());
			
		httpSecurity.exceptionHandling(exceptions -> exceptions.accessDeniedPage("/accessError"));
		return httpSecurity.build();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		
		UserDetailsService userDetailsService = new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
				Usuarios usuario = authRepo.findCredsByMail(mail);
				
				if (usuario == null) {
					throw new UsernameNotFoundException("El usuario con el mail especificado no existe en la Base de Datos");
				} else {
					System.out.println("clave del usuario en la BD: " + usuario.getClave());
				}
				UserDetails userDetails = User.builder()
     		  			  .username(usuario.getMail())
     		  			  .password(usuario.getClave())
     		  			  .roles(usuario.getRol().toString())
     		  			  .build();
				for (GrantedAuthority auth : userDetails.getAuthorities()) {
					System.out.println("Rol de usuario: " + auth.toString());
				}
				
				return userDetails;
			}
		};
		
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
		
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	
	
	@Bean 
	PasswordEncoder passwordEncoder() {
		return new PasswordEncoder() {
			private final BCryptPasswordEncoder delegate = new BCryptPasswordEncoder();
			
			@Override
			public String encode(CharSequence claveSinEncriptar) {
				System.out.println("Clave sin encriptar: " + claveSinEncriptar);
				return delegate.encode(claveSinEncriptar);
			}
			
			@Override
			public boolean matches(CharSequence claveSinEncriptar, String claveEncriptada) {
				System.out.println("clave sin encriptar: " + claveSinEncriptar + " comparandose con clave encriptada " + claveEncriptada);
				boolean result = delegate.matches(claveSinEncriptar, claveEncriptada);
				System.out.println("Resultado: " + result);
				return result;
			}
		};
	}
	
	@Bean
	AuthenticationSuccessHandler authenticationSuccessHandler() {
		AuthenticationSuccessHandler authSuccessHandler = new AuthenticationSuccessHandler() {
			
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				
				Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
				String mail = authentication.getName();
				authRepo.updateUltimoAcceso(LocalDateTime.now(), mail);
				// Usuarios usuario = authRepo.findCredsByMail(mail);
				
				if (roles.contains("ROLE_ASISTENTES")) {
					response.sendRedirect("/profile");
					return;
				}
				
				if (roles.contains("ROLE_ORGANIZADORES")) {
					response.sendRedirect("/profile");
					return;
				}
				
				if (roles.contains("ROLE_ADMINISTRADORES")) {
					response.sendRedirect("/profile");
					return;
				}
				
				request.getSession().setAttribute("errorMessage", "El rol del usuario no es correcto");
				response.sendRedirect("/auth/login?error=1");
			}
		};
		
		return authSuccessHandler;
	}
	
	
	@Bean 
	AuthenticationFailureHandler authenticationFailureHandler() {
		AuthenticationFailureHandler authFailureHandler = new AuthenticationFailureHandler() {
			
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException exception) throws IOException, ServletException {
				
				String mailRequest = request.getParameter("mail");
				String claveRequest = request .getParameter("clave");
				String errorMsg = exception.getMessage();
				String errorCode = "0";
				
				Usuarios usuario = authRepo.findCredsByMail(mailRequest);
				
				System.out.println("mail usuario: " + mailRequest);
				
				System.out.println("El login fallo para el mail: " + mailRequest + ", Error: " + errorMsg);
				
				request.getSession().setAttribute("last_mail", mailRequest);
				
				if (usuario == null) {
					errorMsg = "*El usuario con email " + mailRequest +  " no esta registrado";
					errorCode = "2";
					System.out.println("Deberias rebisar que mail sea correcto");
				} else if (!passwordEncoder().matches(claveRequest, usuario.getClave())) {
					errorMsg = "*La clave no es correcta";
					System.out.println("Deberias rebisar que la clave sea correcta");
					errorCode = "3";
				} 
				
				request.getSession().setAttribute("errorMessage", errorMsg);			
				response.sendRedirect("/auth/login?error=" + errorCode);
			}
		};
	
		return authFailureHandler;	
	}
	
	@Bean
	CommandLineRunner testUser() {
		return args -> {
			
			System.out.println("Conectandose a la BD....");
			try {
				System.out.println("Conxion exitosa, Numero de usuarios totales en la tabla usuarios: " + authRepo.count());
				List<Usuarios> usuarios = authRepo.findAll();
				System.out.println("Lista de mails en la tabla usuarios:");
				for (Usuarios usuario : usuarios) {
					System.out.println(usuario.getMail());
				}
			} catch (Exception e) {
				System.out.println("Error accediendo a la BD:");
				e.printStackTrace();
			}
		};
	}
}
