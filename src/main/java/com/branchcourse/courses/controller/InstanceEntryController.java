package com.branchcourse.courses.controller;

import com.branchcourse.courses.entity.InstanceEntry;
import com.branchcourse.courses.services.InstanceEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/instances")
public class InstanceEntryController {
    @Autowired
    private InstanceEntryService instanceEntryService;

    @PostMapping
    public ResponseEntity<InstanceEntry> addInstanceEntry(@RequestBody InstanceEntry instanceEntry) {
        return ResponseEntity.ok(instanceEntryService.saveInstance(instanceEntry));
    }

    @GetMapping("/{year}/{semester}")
    public ResponseEntity<List<InstanceEntry>> getInstancesByYearAndSemester(
            @PathVariable Integer year, @PathVariable Integer semester) {
        return ResponseEntity.ok(instanceEntryService.getInstancesByYearAndSemester(year, semester));
    }

    @GetMapping("/{year}/{semester}/{courseId}")
    public ResponseEntity<InstanceEntry> getInstance(
            @PathVariable Integer year,
            @PathVariable Integer semester,
            @PathVariable String courseId) {
        InstanceEntry instance = instanceEntryService.getInstance(year, semester, courseId);
        return instance != null ? ResponseEntity.ok(instance) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstance(@PathVariable String id) {
        instanceEntryService.deleteInstance(id);
        return ResponseEntity.noContent().build();
    }
}