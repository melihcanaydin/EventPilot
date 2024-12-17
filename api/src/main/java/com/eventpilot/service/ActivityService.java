package com.eventpilot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.eventpilot.model.Activity;
import com.eventpilot.repository.ActivityRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.List;

/**
 * Service to handle all activity-related logic.
 *
 * This includes loading activities into the database from a file
 * and providing methods to fetch or search activities.
 */
@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ObjectMapper objectMapper;

    public ActivityService(ActivityRepository activityRepository, ObjectMapper objectMapper) {
        this.activityRepository = activityRepository;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void initializeActivities() {
        // TODO: Move data initialization to a separate migration script for production
        // environments.
        // If activities.json grows too large, this method might impact startup time.
        List<Activity> activities = readActivitiesFromFile();
        activityRepository.saveAll(activities);
    }

    private List<Activity> readActivitiesFromFile() {
        // TODO: Make file path configurable via application properties for flexibility.
        // If this file is moved or renamed, the path must be updated here.
        try (InputStream inputStream = getClass().getResourceAsStream("/static/activities.json")) {
            if (inputStream == null) {
                throw new IllegalStateException("activities.json file not found in /static directory");
            }
            return objectMapper.readValue(inputStream, new TypeReference<List<Activity>>() {
            });
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load activities data", e);
        }
    }

    public Page<Activity> getAllActivities(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return activityRepository.findAll(pageable);
    }

    public Page<Activity> searchActivities(String title, int page, int size, String orderBy) {
        // Caching could be added to reduce database load
        if (!StringUtils.hasText(title)) {
            throw new IllegalArgumentException("Title is required for searching.");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return activityRepository.findByTitleContainingIgnoreCase(title, pageable);
    }
}
