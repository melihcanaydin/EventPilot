package com.eventpilot.repository;

import com.eventpilot.model.Activity;
import com.eventpilot.repository.ActivityRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ActivityRepositoryTest {

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    void shouldFindActivitiesByTitle() {
        Activity activity1 = new Activity(null, "Berlin Tour", 50, "$", 4.5, false);
        Activity activity2 = new Activity(null, "Berlin Museum Visit", 30, "$", 4.0, true);
        activityRepository.saveAll(List.of(activity1, activity2));
        
        Page<Activity> results = activityRepository.findByTitleContainingIgnoreCase("Berlin", PageRequest.of(0, 10));
        
        assertEquals(2, results.getTotalElements());
        assertEquals("Berlin Tour", results.getContent().get(0).getTitle());
    }
}