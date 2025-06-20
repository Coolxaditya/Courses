package com.branchcourse.courses.repository;

import com.branchcourse.courses.entity.InstanceEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InstanceEntryRepository extends MongoRepository<InstanceEntry,String> {
    List<InstanceEntry> findByYearAndSemester(Integer year, Integer semester);
    InstanceEntry findByYearAndSemesterAndCourseId(Integer year, Integer semester, String courseId);

}
