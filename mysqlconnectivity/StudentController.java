package com.example.student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import org.springframework.web.bind.annotation.*;


@RestController 
@RequestMapping ("/students")
public class StudentController {

    
    @Autowired
    private StudentRepo studentRepo;

    
    @GetMapping()
    public List<Student> getAll() {
        return studentRepo.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getOne(@PathVariable Long id) {
        return studentRepo.findById(id)
                .map(student -> ResponseEntity.ok().body(student))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student){
        Student saved = studentRepo.save(student);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> putMethodName(@PathVariable Long id, @RequestBody Student student) {
        return studentRepo.findById(id).map(existing -> {
            existing.setCourse(student.getCourse());
            existing.setEmail(student.getEmail());
            existing.setName(student.getName());
            studentRepo.save(existing);
            return ResponseEntity.ok(existing);
        }
            
        ).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        
        if(studentRepo.existsById(id)){
            studentRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }

    }
    



}
