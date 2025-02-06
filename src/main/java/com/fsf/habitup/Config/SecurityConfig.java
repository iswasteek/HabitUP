package com.fsf.habitup.Config;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.fsf.habitup.Security.KeyGenerator;
//import com.fsf.habitup.entity.User;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	 private final KeyGenerator keyGenerator;

	    // Spring will inject the KeyGenerator bean here
	    public SecurityConfig(KeyGenerator keyGenerator) {
	        this.keyGenerator = keyGenerator;
	    }
	    
	    
	    

	    // Use keyGenerator in your security configuration setup
	    @Bean
	    String configure() {
	        // You can now use the keyGenerator instance here
	       // System.out.println("Key Generator: " + keyGenerator.generate256BitKeyBase64());
			return keyGenerator.generate256BitKeyBase64();
	    }
	    
	    @Bean
	    PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder(); // You can use other encoders like Argon2PasswordEncoder as well
	    }
	    
	    @Bean
	    UserDetailsService userDetailsService(PasswordEncoder encoder) {
	        //PasswordEncoder encoder = new BCryptPasswordEncoder();

	        UserDetails user = User.withUsername("user") // Removed incorrect casting
	                .password(encoder.encode("password")) // Encode password properly
	                .roles("USER")
	                .build();

	        return new InMemoryUserDetailsManager(user);
	    }
	    @Bean
	    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests(auth -> auth
                            .anyRequest().authenticated())  // Ensure all other requests are authenticated
                    .formLogin(login -> login  // Explicitly configure form login
                            .permitAll());  // Allow everyone to access the login page
	        return http.build();
	    }

		private Customizer<FormLoginConfigurer<HttpSecurity>> withDefaults() {
			// TODO Auto-generated method stub
			return null;
		}
		
		

}
