package com.example.springwebmvctest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "task")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "grade")
    private int grade;

    public Task(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }
}
