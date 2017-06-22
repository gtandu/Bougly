package fr.diptrack.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.diptrack.model.Course;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

	public Course findByName(String name);

}
