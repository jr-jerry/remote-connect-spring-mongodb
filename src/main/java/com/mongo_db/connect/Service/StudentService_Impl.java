package com.mongo_db.connect.Service;

import com.mongo_db.connect.Entity.StudentEntity;
import com.mongo_db.connect.Repository.StudentRepo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class StudentService_Impl implements StudentService_Interface{
    private final StudentRepo studentRepo;
    public StudentService_Impl(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }
//    student:gautam
//    all_student [ { stunden1 },{student 2 }]
    @Caching(
            put = @CachePut(value = "students",key = "#studentEntity.name"),
            evict = @CacheEvict(value = "all_students",allEntries = true)
    )
    @Override
    public StudentEntity saveStudent(StudentEntity studentEntity) {
        StudentEntity savedStudent=studentRepo.save(studentEntity);
        return savedStudent;
    }
    @Override

    @Cacheable(value = "students",key = "#name")
    public StudentEntity findByStudentName(String name) {
        Optional<StudentEntity> box= studentRepo.findByName(name);
        if(box.isPresent()){
            return box.get();
        }else{
            throw new RuntimeException("Student Not Found ! ");
        }
    }
    @Cacheable(value = "all_students")
    @Override
    public List<StudentEntity> findAllStudents() {
        return studentRepo.findAll();
    }
    @Caching(
            evict = {
                    @CacheEvict(value="all_students",allEntries = true),
                    @CacheEvict(value = "students",key="#name")
            }
    )
    @Override
    public void removeStudent(String name) {
         StudentEntity existsStudent=findByStudentName(name);
         String studentId=existsStudent.getId();
         studentRepo.deleteById(studentId);
    }
}
