package com.mongo_db.connect.Service;

import com.mongo_db.connect.Entity.StudentEntity;
import com.mongo_db.connect.Repository.StudentRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class StudentService_Impl implements StudentService_Interface{
    private final StudentRepo studentRepo;
    public StudentService_Impl(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }
    @Override
    public StudentEntity saveStudent(StudentEntity studentEntity) {
        StudentEntity savedStudent=studentRepo.save(studentEntity);
        return savedStudent;
    }
    @Override
    public StudentEntity findByStudentName(String name) {
        Optional<StudentEntity> box= studentRepo.findByName(name);
        if(box.isPresent()){
            return box.get();
        }else{
            throw new RuntimeException("Student Not Found ! ");
        }
    }

    @Override
    public List<StudentEntity> findAllStudents() {
        return studentRepo.findAll();
    }

    @Override
    public void removeStudent(String name) {
         StudentEntity existsStudent=findByStudentName(name);
         String studentId=existsStudent.getId();
         studentRepo.deleteById(studentId);
    }
}
