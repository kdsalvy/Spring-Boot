package learn.kd.springjparest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import learn.kd.springjparest.entity.StudentEntity;

@RepositoryRestResource(path = "student")
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}
