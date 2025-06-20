package com.branchcourse.courses.services;

import com.branchcourse.courses.entity.CourseEntry;
import com.branchcourse.courses.exception.CourseDependencyException;
import com.branchcourse.courses.exception.InvalidPrerequisitesException;
import com.branchcourse.courses.repository.CourseEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseEntryService {
    @Autowired
    private CourseEntryRepository courseEntryRepository;

    public CourseEntry saveCourse(CourseEntry courseEntry) {
        // Get all prerequisite IDs
        List<String> prerequisiteIds = courseEntry.getPrerequisites().stream()
                .map(CourseEntry::getId)
                .collect(Collectors.toList());

        // Check if all prerequisites exist
        if (!prerequisiteIds.isEmpty()) {
            long existingCount = courseEntryRepository.countByIdIn(prerequisiteIds);
            if (existingCount != prerequisiteIds.size()) {
                throw new InvalidPrerequisitesException("One or more prerequisites don't exist");
            }

            // Fetch complete prerequisite objects
            List<CourseEntry> completePrerequisites = prerequisiteIds.stream()
                    .map(courseEntryRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();

            courseEntry.setPrerequisites(new HashSet<>(completePrerequisites));
        }

        return courseEntryRepository.save(courseEntry);
    }

    public List<CourseEntry> getAll() {
        return courseEntryRepository.findAll();
    }

    public Optional<Optional<CourseEntry>> findById(String id) {
        return Optional.of(courseEntryRepository.findById(id));
    }

    public void deleteById(String id) {
        // Check if course is a prerequisite for others
        List<CourseEntry> dependentCourses = courseEntryRepository.findByPrerequisitesId(id);
        if (!dependentCourses.isEmpty()) {
            throw new CourseDependencyException(
                    "Course cannot be deleted as it's a prerequisite for: " +
                            dependentCourses.stream()
                                    .map(CourseEntry::getId)
                                    .collect(Collectors.joining(", "))
            );
        }
        courseEntryRepository.deleteById(id);
    }
}