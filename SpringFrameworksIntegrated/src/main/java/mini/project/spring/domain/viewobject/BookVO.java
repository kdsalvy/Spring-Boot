package mini.project.spring.domain.viewobject;

import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BookVO extends ResourceSupport {
	private String isbn;
	private String name;
	private Double price;
	private Date publishedDate;
	private int totalQuantity;
	private int rentedQuantity;
}
