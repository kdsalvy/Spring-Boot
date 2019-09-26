package learn.kd.springjparest.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "class")
public class ClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "classs", cascade = CascadeType.ALL, targetEntity = StudentEntity.class)
    private List<StudentEntity> students = new ArrayList<>();;

    @OneToMany(mappedBy = "classs", cascade = CascadeType.ALL, targetEntity = TeacherEntity.class)
    private List<TeacherEntity> teachers = new ArrayList<>();;

    public ClassEntity() {
       
    }

    public ClassEntity(Long id, String name) {
        this();
        this.id = id;
        this.name = name;
    }

}
