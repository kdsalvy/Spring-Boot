package mini.project.spring.config.db;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@Configuration
public class PersistenceConfig {

	// This bean will help to auto-populate the columns annotated with
	// @CreatedBy or @LastModifiedBy in Entity classes by getting the user name
	// from security context, else it will be empty
	@Bean
	public AuditorAware<String> auditorProvider() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		return () -> Optional.of(auth.getName());
	}
}
