package mini.project.spring.domain.viewobject;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieVO {

	@NotNull
	private String movieName;
	
	private String movieDescription;
}
