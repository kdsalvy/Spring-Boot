package learn.kd.spring.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@ConditionalOnProperty(value = "kd.app.security.config", havingValue = "jdbc")
@Order(2)
@Configuration
public class JdbcWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
            // Point the JDBC Auth to the in mem H2 DB
            .dataSource(dataSource);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/admin/**")
            .hasRole("ADMIN")
            .antMatchers("/user/**")
            .hasAnyRole("USER", "ADMIN")
            .antMatchers("/**")
            .permitAll()
            .and()
            .formLogin();
    }
}
