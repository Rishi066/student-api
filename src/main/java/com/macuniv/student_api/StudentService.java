package com.macuniv.student_api;



import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentService
{
      private final StudentRepository studentRepo;
      private final StudentMapper mapper;

      public Page<Student> getAllStudents(Pageable pageable)
      {
        return studentRepo.findAll(pageable);
      }

      public Student getStudentById(long id)
      {
        return studentRepo.findById(id).orElseThrow(() -> new StudentNotFoundException("Student with that ID does not exist"));
      }

      @Transactional
      public Student createStudent(StudentDTO studentDTO)
      {
        Student newStudent = mapper.toStudent(studentDTO);
        return studentRepo.save(newStudent);
      }

      @Transactional
      public Student updateStudent(StudentDTO studentDTO,long id)
      {
        Student student = studentRepo.findById(id).orElseThrow(() -> new StudentNotFoundException("Student with that ID does not exist"));
        student.setName(studentDTO.getName());
        student.setAge(studentDTO.getAge());
        student.setEmail(studentDTO.getEmail());
        return studentRepo.save(student);
      }

      @Transactional
      public void deleteStudent(long id)
      {
         studentRepo.deleteById(id);
      }

      public List<Student> getStudentsByName(String name)
      {
        return studentRepo.findByNameIgnoreCase(name);
      }

}
