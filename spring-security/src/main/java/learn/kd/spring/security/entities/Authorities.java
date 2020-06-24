package learn.kd.spring.security.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(Grants.class)
public class Authorities implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    private String authority;

    @Id
    @ManyToOne
    @JoinColumn(name = "username")
    private Users user;

    @Override
    public String getAuthority() {
        return this.authority;
    }

}
