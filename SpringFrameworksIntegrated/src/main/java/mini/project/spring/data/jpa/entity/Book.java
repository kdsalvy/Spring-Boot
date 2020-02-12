package mini.project.spring.data.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data // lombok annotation to generate getters, setters, equals and hashcode
		// method
@NoArgsConstructor // lombok annotation to generate constructor with no
					// arguments
@RequiredArgsConstructor() // lombok annotation to generate constructor with
							// required
// attributes as arguments (whichever is marked as NonNull)
@Entity
@Table(name = "book", schema = "kdlib")
public class Book {

	@NonNull
	@Id
	@Column(updatable = false, nullable = false)
	private String isbn;
	
	@NonNull
	private String name;
	
	@NonNull
	private Double price;
	
	@NonNull
	@Temporal(TemporalType.DATE)
	@Column(name = "published_date")
	private Date publishedDate;
	
	@NonNull
	@Column(name = "total_quantity")
	private Integer totalQuantity;
	
	@NonNull
	@Column(name = "rented_quantity")
	private Integer rentedQuantity;
	
	@CreatedDate
	@Column(name = "created_on")
	private Date createdOn;
	
	@LastModifiedDate
	@Column(name = "last_modified_on")
	private Date lastModifiedOn;
	
	@CreatedBy
	@Column(name = "created_by")
	private String createdBy;
	
	@LastModifiedBy
	@Column(name = "last_modified_by")
	private String lastModifiedBy;

}
