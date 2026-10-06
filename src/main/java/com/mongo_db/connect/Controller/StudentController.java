package com.mongo_db.connect.Controller;

import com.mongo_db.connect.Entity.StudentEntity;
import com.mongo_db.connect.Service.StudentService_Interface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final StudentService_Interface studentServiceImpl;

    public StudentController(StudentService_Interface studentServiceImpl) {
        this.studentServiceImpl = studentServiceImpl;
    }
    @PostMapping("/create")
    public ResponseEntity<?> saveStudent(@RequestBody StudentEntity studentEntity){
       StudentEntity savedStudent= studentServiceImpl.saveStudent(studentEntity);
       return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }
}
