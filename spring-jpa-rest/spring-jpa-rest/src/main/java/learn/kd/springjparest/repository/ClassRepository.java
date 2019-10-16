package learn.kd.springjparest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import learn.kd.springjparest.entity.ClassEntity;

@RepositoryRestResource(path = "class")
public interface ClassRepository extends JpaRepository<ClassEntity, Long> {

}
