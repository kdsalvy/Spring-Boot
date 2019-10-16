package learn.kd.springjparest.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "teacher")
public class TeacherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "teacher_class_map", joinColumns = { @JoinColumn(name = "teacher_id") }, inverseJoinColumns = { @JoinColumn(name = "class_id") })
    private List<ClassEntity> classs;

    public TeacherEntity() {
        classs = new ArrayList<>();
    }
    
    public TeacherEntity(String name) {
        this();
        this.name = name;
    }

}
