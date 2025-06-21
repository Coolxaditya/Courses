package com.branchcourse.courses.controller;

import ch.qos.logback.classic.Logger;
import com.branchcourse.courses.entity.CourseEntry;
import com.branchcourse.courses.services.CourseEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CoursesEntryController {
    @Autowired
    private CourseEntryService courseEntryService;

    @GetMapping
    public List<CourseEntry> getEntries() {
        return courseEntryService.getAll();
    }

    @PostMapping
    public ResponseEntity<CourseEntry> addCourseEntry(@RequestBody CourseEntry courseEntry) {
        return ResponseEntity.ok(courseEntryService.saveCourse(courseEntry));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CourseEntry>> getCourseEntryById(@PathVariable String id) {
        return courseEntryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseEntryById(@PathVariable String id) {
        courseEntryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}