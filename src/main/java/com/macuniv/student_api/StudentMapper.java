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

    public Student toStudent(StudentDTO dto)
    {
        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setAge(dto.getAge());
        return student;
    }
}
