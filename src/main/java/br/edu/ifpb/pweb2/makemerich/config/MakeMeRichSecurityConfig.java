package br.edu.ifpb.pweb2.makemerich.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MakeMeRichSecurityConfig {
    @Autowired
    private DataSource dataSource;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/imagens/**", "/auth/**").permitAll()
                        .requestMatchers("/correntistas/**").hasRole("ADMIN")
                        .requestMatchers("/categorias/**").hasRole("ADMIN")
                        .requestMatchers("/contas/form").hasAnyRole("ADMIN", "CLIENTE")
                        .requestMatchers("/contas/**").hasAnyRole("ADMIN", "CLIENTE")
                        .anyRequest().authenticated())
                .formLogin((form) -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll())
                .logout((logout) -> logout.logoutUrl("/auth/logout"))
                .exceptionHandling(ex -> ex.accessDeniedPage("/auth/acesso-negado"));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public UserDetailsService userDetailsService() {
    //     // Alguns usuários básicos, criados quando da 1a. execução da aplicaçao
    //     UserDetails sagan = User.withUsername("sagan").password(passwordEncoder().encode("cosmos")).roles("CLIENTE").build();
    //     UserDetails turing = User.withUsername("turing").password(passwordEncoder().encode("enignma")).roles("CLIENTE").build();
    //     UserDetails ada = User.withUsername("ada").password(passwordEncoder().encode("firstcoder")).roles("CLIENTE").build();
    //     UserDetails admin = User.withUsername("admin").password(passwordEncoder().encode("admin")).roles("CLIENTE", "ADMIN").build();

    //     // Evita duplicação dos usuários no banco
    //     JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
    //     if (!users.userExists(admin.getUsername())) {
    //         users.createUser(sagan);
    //         users.createUser(turing);
    //         users.createUser(ada);
    //         users.createUser(admin);
    //     }
    //     return users;
    // }

    @Bean
public UserDetailsService userDetailsService() {
    JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

    // Lista de usuários iniciais
    UserDetails[] initialUsers = new UserDetails[] {
        User.withUsername("sagan").password(passwordEncoder().encode("cosmos")).roles("CLIENTE").build(),
        User.withUsername("turing").password(passwordEncoder().encode("enigma")).roles("CLIENTE").build(),
        User.withUsername("ada").password(passwordEncoder().encode("firstcoder")).roles("CLIENTE").build(),
        User.withUsername("admin").password(passwordEncoder().encode("admin")).roles("CLIENTE", "ADMIN").build(),

        // ---- novos usuários ----
        User.withUsername("user1").password(passwordEncoder().encode("123")).roles("CLIENTE").build(),
        User.withUsername("user2").password(passwordEncoder().encode("123")).roles("CLIENTE").build(),
        User.withUsername("user3").password(passwordEncoder().encode("123")).roles("CLIENTE").build(),
        User.withUsername("user4").password(passwordEncoder().encode("123")).roles("CLIENTE").build(),
        User.withUsername("user5").password(passwordEncoder().encode("123")).roles("CLIENTE").build(),
        User.withUsername("user6").password(passwordEncoder().encode("123")).roles("CLIENTE").build(),
        User.withUsername("user7").password(passwordEncoder().encode("123")).roles("CLIENTE").build(),
        User.withUsername("user8").password(passwordEncoder().encode("123")).roles("CLIENTE").build(),
        User.withUsername("user9").password(passwordEncoder().encode("123")).roles("CLIENTE").build(),
        User.withUsername("user10").password(passwordEncoder().encode("123")).roles("CLIENTE").build(),
        User.withUsername("user11").password(passwordEncoder().encode("123")).roles("CLIENTE").build(),
        User.withUsername("user12").password(passwordEncoder().encode("123")).roles("CLIENTE").build(),
        User.withUsername("user13").password(passwordEncoder().encode("123")).roles("CLIENTE").build(),
        User.withUsername("user14").password(passwordEncoder().encode("123")).roles("CLIENTE").build(),
        User.withUsername("user15").password(passwordEncoder().encode("123")).roles("CLIENTE").build(),
        User.withUsername("user16").password(passwordEncoder().encode("123")).roles("CLIENTE").build(),
        User.withUsername("user17").password(passwordEncoder().encode("123")).roles("CLIENTE").build(),
        User.withUsername("user18").password(passwordEncoder().encode("123")).roles("CLIENTE").build(),
        User.withUsername("user19").password(passwordEncoder().encode("123")).roles("CLIENTE").build(),
        User.withUsername("user20").password(passwordEncoder().encode("123")).roles("CLIENTE").build(),
        User.withUsername("user21").password(passwordEncoder().encode("123")).roles("CLIENTE").build()
    };

    // Criar apenas se ainda não existir
    for (UserDetails u : initialUsers) {
        if (!users.userExists(u.getUsername())) {
            users.createUser(u);
        }
    }

    return users;
}


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}