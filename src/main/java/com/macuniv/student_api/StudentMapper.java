package com.macuniv.student_api;


import org.springframework.stereotype.Component;

@Component
public class StudentMapper
{
    public StudentDTO toStudentDTO(Student student)
    {
        StudentDTO dto = new StudentDTO();
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setAge(student.getAge());
        return dto;
    }

    public Student toStudent(CreateStudentRequestDTO createStudentRequestDTO)
    {
        Student student = new Student();
        student.setName(createStudentRequestDTO.getName());
        student.setEmail(createStudentRequestDTO.getEmail());
        student.setAge(createStudentRequestDTO.getAge());
        student.setBranch(createStudentRequestDTO.getBranch());
        return student;
    }
}
