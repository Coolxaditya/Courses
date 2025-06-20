package com.branchcourse.courses.services;

import com.branchcourse.courses.entity.InstanceEntry;
import com.branchcourse.courses.repository.InstanceEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InstanceEntryService {
    @Autowired
    private InstanceEntryRepository instanceEntryRepository;

    public InstanceEntry saveInstance(InstanceEntry instanceEntry) {
        return instanceEntryRepository.save(instanceEntry);
    }

    public List<InstanceEntry> getInstancesByYearAndSemester(Integer year, Integer semester) {
        return instanceEntryRepository.findByYearAndSemester(year, semester);
    }

    public InstanceEntry getInstance(Integer year, Integer semester, String courseId) {
        return instanceEntryRepository.findByYearAndSemesterAndCourseId(year, semester, courseId);
    }

    public void deleteInstance(String id) {
        instanceEntryRepository.deleteById(id);
    }
}