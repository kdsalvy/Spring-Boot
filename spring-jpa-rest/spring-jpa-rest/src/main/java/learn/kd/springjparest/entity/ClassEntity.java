package learn.kd.springjparest.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
    
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "classs", cascade = CascadeType.ALL)
    private List<StudentEntity> students;

    @ManyToMany(mappedBy = "classs", cascade = CascadeType.ALL)
    private List<TeacherEntity> teachers;

    public ClassEntity() {
        students = new ArrayList<>();
        teachers = new ArrayList<>();
    }

    public ClassEntity(String name) {
        this();
        this.name = name;
    }

}
