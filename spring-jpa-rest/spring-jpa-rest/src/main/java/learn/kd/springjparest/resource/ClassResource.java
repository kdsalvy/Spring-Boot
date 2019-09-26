package learn.kd.springjparest.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import learn.kd.springjparest.entity.ClassEntity;
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
    public ClassEntity createClasses(@RequestBody ClassEntity c1) {
        return classRepo.save(c1);
    }

}
