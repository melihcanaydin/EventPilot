package com.eventpilot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.eventpilot.model.Activity;
import com.eventpilot.repository.ActivityRepository;
import com.eventpilot.service.ActivityService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActivityServiceTest {

    private ActivityService activityService;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        activityService = new ActivityService(activityRepository, objectMapper);
    }

    @Test
    void testGetAllActivities() {
        Activity activity = new Activity(1L, "Test Activity", 20, "$", 4.5, false);
        PageRequest pageable = PageRequest.of(0, 10, Sort.by("title"));
        Page<Activity> activityPage = new PageImpl<>(List.of(activity), pageable, 1);
        when(activityRepository.findAll(pageable)).thenReturn(activityPage);

        Page<Activity> result = activityService.getAllActivities(0, 10, "title");

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Test Activity", result.getContent().get(0).getTitle());
        verify(activityRepository, times(1)).findAll(pageable);
    }

    @Test
    void testSearchActivities() {
        String title = "Test";
        Activity activity = new Activity(1L, "Test Activity", 20, "$", 4.5, false);
        PageRequest pageable = PageRequest.of(0, 10, Sort.by("title"));
        Page<Activity> activityPage = new PageImpl<>(List.of(activity), pageable, 1);
        when(activityRepository.findByTitleContainingIgnoreCase(title, pageable)).thenReturn(activityPage);

        Page<Activity> result = activityService.searchActivities(title, 0, 10, "title");

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Test Activity", result.getContent().get(0).getTitle());
        verify(activityRepository, times(1)).findByTitleContainingIgnoreCase(title, pageable);
    }

    @Test
    void testSearchActivitiesThrowsExceptionForEmptyTitle() {
        String keyword = "";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> activityService.searchActivities(keyword, 0, 10, "title")
        );

        assertEquals("Title is required for searching.", exception.getMessage());
    }
}
