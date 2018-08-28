package comcompany.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                    .withUser("user").password("{noop}user").roles("USER")
                .and()
                    .withUser("admin").password("{noop}admin").roles("ADMIN")
                .and()
                    .withUser("boss").password("{noop}boss").roles("BOSS");

    }

    @Override
    protected  void configure(HttpSecurity http) throws  Exception
    {
        http.authorizeRequests()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                        .loginPage("/loginForm")
                        .loginProcessingUrl("/authenticateUser")
                        .permitAll();//gives access to listed views(loginForm,authenticateUser) to everyone

    }
   /* @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/loginForm")
                        .permitAll()
                    .loginProcessingUrl("/loginProcessing")
                        .permitAll()
                    .usernameParameter("username")
                    .passwordParameter("password");
    }*/

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.
                ignoring()
                    .antMatchers("/static/**");
    }
}
