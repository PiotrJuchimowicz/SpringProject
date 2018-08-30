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
        String bossPassword = encoder.encode("boss");
        auth
                /* .jdbcAuthentication()
                 .dataSource(dataSource)
                 .usersByUsernameQuery("SELECT email,password_hash,enabled FROM EMPLOYEE WHERE email=?")
                 .authoritiesByUsernameQuery("SELECT email,position FROM EMPLOYEE WHERE email=?")*/
                /*.groupAuthoritiesByUsername("")*/
                /*.passwordEncoder(new BCryptPasswordEncoder())*/
                /*.and()*/
                .inMemoryAuthentication().passwordEncoder(encoder)
                .withUser("admin").password(adminPassword).roles("ADMIN")//lead admin in memory
                .and()
                .withUser("boss").password(bossPassword).roles("BOSS");

    }
/*
https://stackoverflow.com/questions/30819337/multiple-antmatchers-in-spring-security
Kolejnosc jest tu mega wazna na poczatku wchodzi przez filtr ze kazdy moze wejsc w index,potem ze
admin moze w "/boss/employee/addForm" i boss moze w  "/boss/employee/searchForm")
a reszta URL wymaga bycia zalogowanym
 */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/boss/index").permitAll()
                .antMatchers("/boss/employee/addForm").hasRole("ADMIN")
                .antMatchers("/boss/employee/searchForm").hasRole("BOSS")
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/authenticateUser")
                .permitAll();



    }

    //ignoring static resources by Spring Security
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.
                ignoring()
                .antMatchers("/static/**");
    }
}
