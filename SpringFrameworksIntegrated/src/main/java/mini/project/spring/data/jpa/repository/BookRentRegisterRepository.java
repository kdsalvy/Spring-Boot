package mini.project.spring.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import mini.project.spring.data.jpa.entity.BookRentRegister;

@Repository
public interface BookRentRegisterRepository extends JpaRepository<BookRentRegister, Long>, JpaSpecificationExecutor<BookRentRegister> {
}
