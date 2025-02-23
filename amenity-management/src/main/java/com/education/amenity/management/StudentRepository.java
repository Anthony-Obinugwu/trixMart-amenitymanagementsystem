package com.education.amenity.management;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findById(long id);

    Student findByStudentName(String studentName);

}
