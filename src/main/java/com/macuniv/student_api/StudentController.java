package com.macuniv.student_api;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<ApiResponse<Page<StudentDTO>>> getStudents(@RequestParam(defaultValue = "0") int pageNo,
                                                                     @RequestParam(defaultValue = "10") int size,
                                                                     @RequestParam(defaultValue = "id") String sortBy)
    {
        Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(pageNo,size,sort);

        Page<Student> allStudents =  studentService.getAllStudents(pageable);
        Page<StudentDTO> allDTOStudents = allStudents.map(mapper::toStudentDTO);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(allDTOStudents,"All Students retrieved successfully"));
    }

    @GetMapping(value = "/students/search")
    public ResponseEntity<ApiResponse<List<StudentDTO>>> getStudentsWithName(@RequestParam(value = "name") String studentName)
    {
        List<Student> students = studentService.getStudentsByName(studentName);
        List<StudentDTO> allDTOStudents = students.stream().map(mapper::toStudentDTO).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(allDTOStudents,"Successfully Retrieved Students With Given Name"));
    }

    @GetMapping(value = "/students/{student_id}")
    public ResponseEntity<ApiResponse<StudentDTO>> getStudentById(@PathVariable long student_id)
    {
        Student student = studentService.getStudentById(student_id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(mapper.toStudentDTO(student),"Student Retrieved Successfully"));
    }

    @PostMapping(value = "/students")
    public ResponseEntity<ApiResponse<StudentDTO>> createStudent(@Valid @RequestBody StudentDTO studentDTO)
    {
        Student student = studentService.createStudent(studentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(mapper.toStudentDTO(student),"Student Created Successfully"));
    }

    @PutMapping(value = "/students/{student_id}")
    public ResponseEntity<ApiResponse<StudentDTO>> updateStudent(@Valid @RequestBody StudentDTO studentDTO,@PathVariable long student_id)
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
