package com.mongo_db.connect.Service;

import com.mongo_db.connect.Entity.StudentEntity;
import com.mongo_db.connect.Repository.StudentRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@AllArgsConstructor
public class StudentService_Impl implements StudentService_Interface{
    private final StudentRepo studentRepo;
    private final RedisTemplate<String,Object> redisTemplate;

//    student:gautam
//    all_student [ { stunden1 },{student 2 }]

    @Override
    public StudentEntity saveStudent(StudentEntity studentEntity) {

        StudentEntity savedStudent=studentRepo.save(studentEntity);
        String key="USER_CACHE";
        StudentEntity value=studentEntity;
        String hash_key=studentEntity.getName();

        redisTemplate.opsForHash().put(key,hash_key,value);
        return savedStudent;
    }
    @Override


    public StudentEntity findByStudentName(String name) {
        StudentEntity studentEntity=(StudentEntity) redisTemplate.opsForHash().get("USER_CACHE",name);
        if(studentEntity!=null){
            log.info("student found in redis");
            return studentEntity;
        }

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
