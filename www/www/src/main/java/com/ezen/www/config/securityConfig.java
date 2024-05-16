package com.ezen.www.config;

import com.ezen.www.security.CustomUserService;
import com.ezen.www.security.LoginFailHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class securityConfig {
    /* springSecurity6 => createDelgationPasswordEncoder */

    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler authFailHandler(){
        return new LoginFailHandler();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(authorize->authorize
                        .requestMatchers("/index", "/", "/js/**", "/dist/**", "/board/list", "/member/login",
                                "/member/register", "/upload/**", "/comment/**").permitAll()
                        .requestMatchers("/member/list").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .usernameParameter("email")
                        .passwordParameter("pwd")
                        .loginPage("/member/login")
                        .defaultSuccessUrl("/board/list")
                        .failureHandler(authFailHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/member/logout")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/")
                ).build();
//        return http.build();
    }

    /* userDetailService : spring에서 만든 클래스와 같은 객체 */
    @Bean
    UserDetailsService userDetailsService(){
        return new CustomUserService(); //생성 > security 패키지에
    }
    /* AuthenticationManager 객체*/
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
