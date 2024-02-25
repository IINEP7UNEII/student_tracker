package assignment2.student_tracker.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer>
{
    List<Student> findByName(String name);
    Student findByUid(int uid);
}
