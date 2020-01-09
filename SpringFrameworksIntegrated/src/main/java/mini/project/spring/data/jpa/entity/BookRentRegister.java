package mini.project.spring.data.jpa.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_rent_register", schema = "kdlib")
public class BookRentRegister {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_rent_reg_gen_seq")
	@SequenceGenerator(name = "book_rent_reg_gen_seq", schema = "kdlib", allocationSize = 1)
	@Column(name = "rent_id", updatable = false, nullable = false)
	private long rentId;
	@Temporal(TemporalType.DATE)
	@Column(name = "rented_date")
	private Date rentedDate;
	@Temporal(TemporalType.DATE)
	@Column(name = "return_date")
	private Date returnDate;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "book_rent", schema = "kdlib", joinColumns = @JoinColumn(name = "rent_id"), inverseJoinColumns = @JoinColumn(name = "book_isbn"))
	private Set<Book> books;
}
