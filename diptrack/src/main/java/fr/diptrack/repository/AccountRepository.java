package fr.diptrack.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import fr.diptrack.model.Student;
import fr.diptrack.model.UserAccount;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<UserAccount, Long>{
	
	public UserAccount findByMail(String mail);
	public Student findByStudentNumber(String studentNumber);
	public List<UserAccount> findAll();

}