package com.macuniv.student_api;



import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentService
{
      private final StudentRepository studentRepo;
      private final UserRepository userRepo;
      private final UserMapper userMapper;
      private final StudentMapper studentMapper;
      private final PasswordEncoder encoder;
      public Page<Student> getAllStudents(Pageable pageable)
      {
        return studentRepo.findAll(pageable);
      }

      public Student getStudentById(long id)
      {
          Student student =  studentRepo.findById(id).orElseThrow(() -> new StudentNotFoundException("Student with that ID does not exist"));

          Authentication auth = SecurityContextHolder.getContext().getAuthentication();
          boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
          User user = student.getUser();

          if(user == null) throw new UnauthorizedAccessException("Access Denied");

          boolean isOwner = auth.getName().equals(user.getUsername());

          if(isAdmin || isOwner)
          {
              return student;
          }
          else
          {
              throw new UnauthorizedAccessException("Access Denied");
          }

      }

      @Transactional
      public Student createStudent(CreateStudentRequestDTO createStudentRequestDTO)
      {
          User user = new User();
          user.setUsername(createStudentRequestDTO.getUsername());
          user.setPassword(encoder.encode(createStudentRequestDTO.getPassword()));
          user.setRole("ROLE_STUDENT");
          userRepo.save(user);

          Student newStudent = studentMapper.toStudent(createStudentRequestDTO);
          newStudent.setUser(user);
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
