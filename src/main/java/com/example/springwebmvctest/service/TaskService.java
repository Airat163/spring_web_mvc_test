package com.example.springwebmvctest.service;

import com.example.springwebmvctest.model.Task;
import com.example.springwebmvctest.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskService {
    private final TaskRepository taskRepository;

    public Task save(Task task) {
       return taskRepository.save(task);
    }

    public void delete(int id) {
        taskRepository.deleteById(id);
    }

    public Task update(int id, Task task) {
        Task taskById = findById(id);
        taskById.setName(task.getName());
        taskById.setGrade(task.getGrade());
       return taskRepository.save(taskById);
    }

   /* public Task update(int id, Task task) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
         return taskRepository.save(task);
        } else {
            throw new RuntimeException();
        }
    }*/

    public Task findById(int id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            return taskOptional.get();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Integer findSumByGrades() {
        return taskRepository.findSumByGrade();
    }

    public Task findMaxGrade() {
        return taskRepository.findTopByGrade();
    }

    public List<Task> findByNameStartsWith(String name) {
        return taskRepository.findByNameStartingWith(name);
    }
}
