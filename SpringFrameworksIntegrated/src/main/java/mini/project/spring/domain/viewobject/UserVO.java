package mini.project.spring.domain.viewobject;

import org.springframework.hateoas.ResourceSupport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UserVO extends ResourceSupport {
	private long userId;
	private String name;
	private String address;
	private String phNo;
	private int rentPass;
}
