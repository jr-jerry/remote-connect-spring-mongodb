package com.mongo_db.connect.Service;

import com.mongo_db.connect.Entity.StudentEntity;

public interface StudentService_Interface {
    StudentEntity saveStudent(StudentEntity studentEntity);
    StudentEntity findByStudentName(String name);
    void removeStudent(String name);
}
