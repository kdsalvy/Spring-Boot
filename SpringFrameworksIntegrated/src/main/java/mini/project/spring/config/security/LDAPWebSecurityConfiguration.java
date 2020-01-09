package mini.project.spring.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Order(3)
public class LDAPWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Value("${library.management.system.ldap-server.url}")
	private String ldapServerUrl;

	@Value("${library.management.system.user.dn.patterns}")
	private String userDnPatterns;

	@Value("${library.management.system.search.base.group}")
	private String baseGroup;

	@Value("${library.management.system.attribute.password}")
	private String passwordAttribute;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests().antMatchers("/user", "/user/**")
				.fullyAuthenticated().and().formLogin();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.ldapAuthentication().userDnPatterns(userDnPatterns)
				.groupSearchBase(baseGroup).contextSource().url(ldapServerUrl)
				.and().passwordCompare().passwordEncoder(passwordEncoder)
				.passwordAttribute(passwordAttribute);
	}
}