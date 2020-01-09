package mini.project.spring.domain.viewobject;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class BookRentRegisterVO extends ResourceSupport {
	private long rentId;
	private Date rentedDate;
	private Date returnDate;
	@Exclude
	private UserVO user;
	@Exclude
	private List<BookVO> books;
}
