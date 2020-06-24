package learn.kd.spring.security.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Grants implements Serializable {

    private static final long serialVersionUID = 1L;

    private String authority;

    private Users user;

}
