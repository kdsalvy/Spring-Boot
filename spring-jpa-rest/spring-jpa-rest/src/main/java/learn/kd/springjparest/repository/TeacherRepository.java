package learn.kd.springjparest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import learn.kd.springjparest.entity.TeacherEntity;

@RepositoryRestResource(path = "teacher")
public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {

}
