package comcompany.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String adminPassword = encoder.encode("admin");
        auth

                .inMemoryAuthentication().passwordEncoder(encoder)
                .withUser("admin").password(adminPassword).authorities("ADMIN");//lead admin in memory

        auth.    jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT email,password,enabled FROM EMPLOYEE WHERE email=?")
                .authoritiesByUsernameQuery("SELECT email,position FROM EMPLOYEE WHERE email=?")
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/commonOperations/**").hasAnyAuthority("ADMIN","BOSS")
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/boss/**").hasAuthority("BOSS")
                .antMatchers("/employee/**").hasAuthority("EMPLOYEE")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/homepage", true)
                .failureUrl("/badLogin")
                .loginPage("/loginForm")
                .permitAll()
                .loginProcessingUrl("/loginProcessing")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/loginForm")
                .permitAll();


    }

    //ignore static resources by Spring Security
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.
                ignoring()
                .antMatchers("/static/**");
    }
}
