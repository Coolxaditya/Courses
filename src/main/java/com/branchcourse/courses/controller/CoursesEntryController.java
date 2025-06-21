package com.branchcourse.courses.controller;

import ch.qos.logback.classic.Logger;
import com.branchcourse.courses.entity.CourseEntry;
import com.branchcourse.courses.services.CourseEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<?> deleteCourseEntryById(@PathVariable String id) {
        // First check if the course is a prerequisite for any other courses
        if (courseEntryService.isPrerequisite(id)) {
            // Get the list of courses that depend on this one
            List<String> dependentCourses = courseEntryService.getCoursesUsingAsPrerequisite(id);

            // Return 409 Conflict with error details
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Cannot delete course - it is a prerequisite for other courses");
            response.put("dependentCourses", dependentCourses);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        // If not a prerequisite, proceed with deletion
        courseEntryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}