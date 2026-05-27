package com.macuniv.student_api;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/")
public class StudentController
{
    private final StudentService studentService;
    private final StudentMapper mapper;

    @GetMapping(value = "/students")
    public ResponseEntity<ApiResponse<List<StudentDTO>>> getStudents()
    {
        List<Student> allStudents =  studentService.getAllStudents();
        List<StudentDTO> allDTOStudents = new ArrayList<>();
//        for(Student student : allStudents) allDTOStudents.add(mapper.toStudentDTO(student));
        // Using Streams
        allDTOStudents = allStudents.stream().map(mapper::toStudentDTO).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(allDTOStudents,"All Students retrieved successfully"));
    }

    @GetMapping(value = "/students/{student_id}")
    public ResponseEntity<ApiResponse<StudentDTO>> getStudentById(@PathVariable long student_id)
    {

        Student student = studentService.getStudentById(student_id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(mapper.toStudentDTO(student),"Student Retrieved Successfully"));
    }

    @PostMapping(value = "/students")
    public ResponseEntity<ApiResponse<StudentDTO>> createStudent(@RequestBody StudentDTO studentDTO)
    {
        Student student = studentService.createStudent(studentDTO);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(mapper.toStudentDTO(student),"Student Created Successfully"));
    }

    @PutMapping(value = "/students/{student_id}")
    public ResponseEntity<ApiResponse<StudentDTO>> updateStudent(@RequestBody StudentDTO studentDTO,@PathVariable long student_id)
    {
        Student student = studentService.updateStudent(studentDTO,student_id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(mapper.toStudentDTO(student),"Student Updated Successfully"));
    }

    @DeleteMapping(value = "/students/{student_id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable long student_id)
    {
        studentService.deleteStudent(student_id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(null,"Student Deleted Successfully"));
    }


}
