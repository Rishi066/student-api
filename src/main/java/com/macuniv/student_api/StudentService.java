package com.macuniv.student_api;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StudentService
{
    private final StudentRepository studentRepo;
    private final StudentMapper mapper;
//    public StudentService(StudentRepository studentRepo)
//    {
//        this.studentRepo = studentRepo;
//    }

      public List<Student> getAllStudents()
      {
        return studentRepo.findAll();
      }

      public Student getStudentById(long id)
      {
        return studentRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Student with that ID does not exist"));
      }

      public Student createStudent(StudentDTO studentDTO)
      {
        Student newStudent = mapper.toStudent(studentDTO);
        return studentRepo.save(newStudent);
      }

      public Student updateStudent(StudentDTO studentDTO,long id)
      {
        Student student = studentRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Student with that ID does not exist"));
        student.setName(studentDTO.getName());
        student.setAge(studentDTO.getAge());
        student.setEmail(studentDTO.getEmail());
        return studentRepo.save(student);
      }

      public void deleteStudent(long id)
      {
         studentRepo.deleteById(id);
      }


}
