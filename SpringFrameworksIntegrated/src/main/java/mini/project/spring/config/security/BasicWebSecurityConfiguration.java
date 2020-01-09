package mini.project.spring.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Order(2)
public class BasicWebSecurityConfiguration
		extends
			WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.httpBasic().and().authorizeRequests()
				.antMatchers("/book", "/book/**").hasRole("user")
				.antMatchers("/actuator", "/actuator/**", "/h2", "/h2/**")
				.hasRole("admin").and().csrf().disable();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication().withUser("user")
				.password(passwordEncoder.encode("password")).roles("user")
				.and().withUser("admin")
				.password(passwordEncoder.encode("password"))
				.roles("user", "admin");
	}
}
