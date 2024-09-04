package com.example.springwebmvctest.controller;

import com.example.springwebmvctest.model.Task;
import com.example.springwebmvctest.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TaskService taskService;
    Task task;
    Task updated;


    @BeforeEach
    public void initTask() {
        task = new Task("create_repo", 5);
        updated = new Task("create_service", 7);
    }

    @Test
    void shouldCreateTask() throws Exception {

        // when(taskService.save(task)).thenReturn(task);
        given(taskService.save(task)).willReturn(task);
        mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(content().string("Task успешно сохранен!"));
    }

    @Test
    void shouldReturnTaskById() throws Exception {
        int id = 5;
        given(taskService.findById(id)).willReturn(task);
        mockMvc.perform(get("/api/task/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(task)))
                .andExpect(jsonPath("$.grade").value(5))
                .andExpect(jsonPath("$.name").value("create_repo"));

    }

    @Test
    void shouldReturnListTasks() throws Exception {
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        taskList.add(updated);
        given(taskService.findAll()).willReturn(taskList);
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(objectMapper.writeValueAsString(taskList)))
                .andExpect(jsonPath("$[1].grade").value(7))
                .andExpect(jsonPath("$[0].name").value("create_repo"));
    }

    @Test
    void shouldReturnStatusCode202() throws Exception {
        int id = 5;
        // when(taskService.delete(id)).thenReturn(ResponseEntity.ok().build());
        Mockito.doNothing().when(taskService).delete(id);
        mockMvc.perform(delete("/api/task/" + id))
                .andExpect(status().is2xxSuccessful());

        verify(taskService, times(1)).delete(id);
    }

    //обязательно сделать
    @Test
    void shouldUpdateTask() throws Exception {
        int id = 1;
        Task updated = new Task("updated", 8);
        given(taskService.save(any(Task.class))).willReturn(updated);
        given(taskService.findById(anyInt())).willReturn(updated);
        given(taskService.update(anyInt(), any(Task.class))).willReturn(updated);
        mockMvc.perform(put("/api/task/{id}", id)
                        .content(objectMapper.writeValueAsString(updated))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grade").value("8"))
                .andExpect(jsonPath("$.name").value("updated"))
                .andExpect(content().json(objectMapper.writeValueAsString(updated)));

    }
   /* @Test
    void shouldUpdateTask() throws Exception {
        int id = 1;
        Task updated = new Task("updated", 8);
        Task task1 = new Task("task", 3);
        given(taskService.update(id, task1)).willReturn(updated);
        mockMvc.perform(put("/api/task/{id}", id)
                        .content(objectMapper.writeValueAsString(task1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grade").value("8"))
                .andExpect(jsonPath("$.name").value("updated"))
                .andExpect(content().json(objectMapper.writeValueAsString(updated)));

    }*/






    @Test
    void shouldReturnSumGrade() throws Exception {
        int sum = 25;
        given(taskService.findSumByGrades()).willReturn(sum);
        mockMvc.perform(get("/api/task/sum"))
                .andExpect(status().isOk())
                .andExpect(content().string("25"));
        // .andExpect(jsonPath("$.sum()").value(sum));
    }

    @Test
    void shouldReturnTaskByMaxGrade() throws Exception {
        Task maxGrade = new Task("maxGrade", 3);
        given(taskService.findMaxGrade()).willReturn(maxGrade);
        mockMvc.perform(get("/api/task/maxGrade"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("maxGrade"))
                .andExpect(jsonPath("$.grade").value("3"))
                .andExpect(content().json(objectMapper.writeValueAsString(maxGrade)));
    }

    @Test
    void shouldReturnTasksByNameStartingWith() throws Exception {
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        taskList.add(updated);
        String start = "c";
        given(taskService.findByNameStartsWith(start)).willReturn(taskList);

        mockMvc.perform(get("/api/task/starts?start=" + start))
                .andExpect(content().json(objectMapper.writeValueAsString(taskList)));
    }
}