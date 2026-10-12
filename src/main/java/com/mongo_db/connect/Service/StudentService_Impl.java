package com.mongo_db.connect.Service;

import com.mongo_db.connect.Entity.StudentEntity;
import com.mongo_db.connect.Repository.StudentRepo;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class StudentService_Impl implements StudentService_Interface{
    private final RedisTemplate<String,Object> redisTemplate;
    private final StudentRepo studentRepo;


    @Override
    public StudentEntity saveStudent(StudentEntity studentEntity) {
        StudentEntity savedStudent=studentRepo.save(studentEntity);
        String id=savedStudent.getId();

        String hash_key="USER_CACHE";
        StudentEntity value=savedStudent;
        redisTemplate.opsForHash().put(hash_key,id,value);

        return savedStudent;
    }

//    "USERCACHE",{"1:name=Gautam ,1:add=Botanical"}
    @Override
    public StudentEntity findStudentById(String studentId){
        StudentEntity studentEntity=(StudentEntity) redisTemplate.opsForHash().get("USER_CACHE",studentId);
        if(studentEntity!=null){
            return studentEntity;
        }
        System.out.println("Cache miss ");

        return studentRepo.findById(studentId).orElseThrow(()->new RuntimeException("No user found with id:"+studentId));
    }
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
