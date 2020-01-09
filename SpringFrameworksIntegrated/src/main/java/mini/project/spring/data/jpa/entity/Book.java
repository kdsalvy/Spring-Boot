package mini.project.spring.data.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // lombok annotation to generate getters, setters, equals and hashcode
		// method
@NoArgsConstructor // lombok annotation to generate constructor with no
					// arguments
@AllArgsConstructor // lombok annotation to generate constructor with all
					// attributes as arguments
@Entity
@Table(name = "book", schema = "kdlib")
public class Book {

	@Id
	@Column(updatable = false, nullable = false)
	private String isbn;
	private String name;
	private Double price;
	@Temporal(TemporalType.DATE)
	@Column(name = "published_date")
	private Date publishedDate;
	@Column(name = "total_quantity")
	private int totalQuantity;
	@Column(name = "rented_quantity")
	private int rentedQuantity;

}
