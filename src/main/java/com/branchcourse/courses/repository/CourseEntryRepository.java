package com.branchcourse.courses.repository;

import com.branchcourse.courses.entity.CourseEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CourseEntryRepository extends MongoRepository<CourseEntry,String>{
    // Custom query to check if all prerequisite IDs exist
    @Query(value = "{'_id': {$in: ?0}}", count = true)
    long countByIdIn(List<String> ids);

    // Custom query to find courses that have the given course as a prerequisite
    @Query(value = "{'prerequisites.$id': ?0}")
    List<CourseEntry> findByPrerequisitesId(String courseId);
}
