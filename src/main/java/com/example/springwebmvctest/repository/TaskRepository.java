package com.example.springwebmvctest.repository;

import com.example.springwebmvctest.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {
    Optional<Task> findByName(String name);

    @Query(value = "select sum(grade) as sum_grade from task",nativeQuery = true)
    Integer findSumByGrade();

   // @Query(value = "select * from task order by grade desc limit 1",nativeQuery = true)
    @Query(value = "select * from task where grade = (select max(grade) from task) limit 1",nativeQuery = true)
    Task findTopByGrade();

    @Query(value = "select * from task where name like 'c%'",nativeQuery = true)
    List<Task> findByNameStartingWith(String s);

  //  List<Task> findByNameStartingWith(String s);
}
