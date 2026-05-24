package com.macuniv.student_api;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/")
public class StudentController
{
    private final StudentService studentService;

    @GetMapping(value = "/students")
    public List<Student> getStudents()
    {
        return studentService.getAllStudents();
    }

    @GetMapping(value = "/students/{student_id}")
    public Student getStudentById(@PathVariable long student_id)
    {
        return studentService.getStudentById(student_id);
    }

    @PostMapping(value = "/students")
    public Student createStudent(@RequestBody StudentDTO studentDTO)
    {
        return studentService.createStudent(studentDTO);
    }

    @PutMapping(value = "/students/{student_id}")
    public Student updateStudent(@RequestBody StudentDTO studentDTO,@PathVariable long student_id)
    {
        return studentService.updateStudent(studentDTO,student_id);
    }

    @DeleteMapping(value = "/students/{student_id}")
    public void deleteStudent(@PathVariable long student_id)
    {
        studentService.deleteStudent(student_id);
    }


}
