package com.example.springwebmvctest.controller;

import com.example.springwebmvctest.model.Task;
import com.example.springwebmvctest.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/task")
    public ResponseEntity<String> save(@RequestBody Task task) {
        taskService.save(task);
        return new ResponseEntity<>("Task успешно сохранен!",
                HttpStatusCode.valueOf(200));
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<Task> findById(@PathVariable int id) {
        return new ResponseEntity<>(taskService.findById(id),
                HttpStatusCode.valueOf(200));
    }

    @GetMapping("/tasks")
    public List<Task> findAll() {
        return taskService.findAll();
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable int id) {
        taskService.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<Task> update(@PathVariable int id, @RequestBody Task task) {
        return ResponseEntity.ok(taskService.update(id,task));
    }


    @GetMapping("/task/sum")
    public ResponseEntity<Integer> findSumByGrades() {
        return new ResponseEntity<>(taskService.findSumByGrades(), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/task/maxGrade")
    public ResponseEntity<Task> findMaxGrade() {
        return new ResponseEntity<>(taskService.findMaxGrade(), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/task/starts")
    public List<Task> findByNameStartingWith(@RequestParam String start) {
        return taskService.findByNameStartsWith(start);
    }
}
