package mini.project.spring.data.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user", schema = "kdlib")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uid_gen_seq")
    @SequenceGenerator(name = "uid_gen_seq", schema = "kdlib", allocationSize = 1)
    @Column(name = "user_id", updatable = false, nullable = false)
    private long userId;
    private String name;
    private String address;
    private String phNo;
    @Column(name = "rent_pass")
    private int rentPass;
}
