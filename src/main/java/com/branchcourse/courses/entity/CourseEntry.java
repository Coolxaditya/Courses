package com.branchcourse.courses.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;
@Document(collection = "courses")
public class CourseEntry {

    @Id
    private String id;
    private String title;
    private String description;

    @DBRef
    private Set<CourseEntry> prerequisites = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CourseEntry> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(Set<CourseEntry> prerequisites) {
        this.prerequisites = prerequisites;
    }

}
