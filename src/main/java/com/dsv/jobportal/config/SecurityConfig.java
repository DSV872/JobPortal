package com.dsv.jobportal.config;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilerChain(HttpSecurity http) {
        try {
            http.csrf(AbstractHttpConfigurer::disable) //to disable the csrf
                    .authorizeHttpRequests(request -> request
                            .requestMatchers("/user/register","/user/login") // to provide access to everyone to these apis calls
                            .permitAll() //giving permission to those who access the above apis calls
                            .anyRequest().authenticated()) //other than above apis calls remaining are need to be authenticated it means user need to login to access them
                    .httpBasic(Customizer.withDefaults())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Tells Spring Security not to use sessions at all
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //The following method uses how user credentials are validated
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); //As the user data is stored in the object of UserDetails these are load by using this object
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));

        return provider;
    }

    //It is used to get the object of AuthenticationManager which is used to validate the username and password are corrct or not
    @Bean
    public AuthenticationManager authenticationProviderObject(AuthenticationConfiguration authenticationConfiguration){
        try {
            return authenticationConfiguration.getAuthenticationManager();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public BCryptPasswordEncoder passwordEncrypter(){
        return new BCryptPasswordEncoder(12);
    }
}
