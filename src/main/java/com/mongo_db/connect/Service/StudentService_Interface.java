package com.mongo_db.connect.Service;

import com.mongo_db.connect.Entity.StudentEntity;

import java.util.List;

public interface StudentService_Interface {
    StudentEntity saveStudent(StudentEntity studentEntity);
    StudentEntity findByStudentName(String name);
    List<StudentEntity> findAllStudents();
    void removeStudent(String name);
}
