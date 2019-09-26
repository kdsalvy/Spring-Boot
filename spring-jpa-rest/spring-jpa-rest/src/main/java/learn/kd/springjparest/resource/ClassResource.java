package learn.kd.springjparest.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import learn.kd.springjparest.entity.ClassEntity;
import learn.kd.springjparest.entity.StudentEntity;
import learn.kd.springjparest.entity.TeacherEntity;
import learn.kd.springjparest.repository.ClassRepository;

@RestController("class")
public class ClassResource {

    @Autowired
    private ClassRepository classRepo;

    @GetMapping
    public List<ClassEntity> getClasses() {
        return classRepo.findAll();
    }

    @PostMapping
    public ClassEntity createClasses() {
        ClassEntity c1 = new ClassEntity("III");

        StudentEntity s1 = new StudentEntity("G");
        StudentEntity s2 = new StudentEntity("H");
        StudentEntity s3 = new StudentEntity("I");
        StudentEntity s4 = new StudentEntity("J");

        TeacherEntity t1 = new TeacherEntity("AFG");
        TeacherEntity t2 = new TeacherEntity("AGH");
        TeacherEntity t3 = new TeacherEntity("AHI");
        TeacherEntity t4 = new TeacherEntity("AIJ");

        s1.setClasss(c1);
        s2.setClasss(c1);
        s3.setClasss(c1);
        s4.setClasss(c1);

        t1.setClasss(c1);
        t2.setClasss(c1);
        t3.setClasss(c1);
        t4.setClasss(c1);

        c1.getStudents()
            .add(s1);
        c1.getStudents()
            .add(s2);
        c1.getStudents()
            .add(s3);
        c1.getStudents()
            .add(s4);
        c1.getTeachers()
            .add(t1);
        c1.getTeachers()
            .add(t2);
        c1.getTeachers()
            .add(t3);
        c1.getTeachers()
            .add(t4);

        return classRepo.save(c1);
    }

}
