package com.mongo_db.connect.Controller;

import com.mongo_db.connect.Entity.StudentEntity;
import com.mongo_db.connect.Service.StudentService_Interface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final StudentService_Interface studentServiceImpl;

    public StudentController(StudentService_Interface studentServiceImpl) {
        this.studentServiceImpl = studentServiceImpl;
    }
    @GetMapping()
    public ResponseEntity<?> findByName(@RequestParam String name){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(studentServiceImpl.findByStudentName(name));
    }
    @PostMapping("/create")
    public ResponseEntity<?> saveStudent(@RequestBody StudentEntity studentEntity){
       StudentEntity savedStudent= studentServiceImpl.saveStudent(studentEntity);
       return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }
    @DeleteMapping("/remove")
    public ResponseEntity<?> removeStudent(@RequestParam String name){
        studentServiceImpl.removeStudent(name);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Deleted student");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllStudents(){
        return ResponseEntity.status(200).body(studentServiceImpl.findAllStudents());
    }
}
