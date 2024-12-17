package com.eventpilot.repository;

import com.eventpilot.model.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Handles database queries for activities.
 */
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    /**
     * Searches for activities by keyword in their title.
     *
     * @param keyword  Word to search in activity titles.
     * @param pageable Pagination and sorting info.
     * @return Paginated list of matching activities.
     */
    Page<Activity> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
}