package com.eventpilot.controller;

import com.eventpilot.model.Activity;
import com.eventpilot.repository.ActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ActivityControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ActivityRepository activityRepository;

    @BeforeEach
    void setUp() {
        activityRepository.deleteAll();

        Activity activity1 = new Activity(null, "Berlin Tour", 50, "$", 4.5, false);
        Activity activity2 = new Activity(null, "Museum Visit", 30, "$", 4.0, true);
        activityRepository.saveAll(List.of(activity1, activity2));
    }

    @Test
    void shouldReturnAllActivities() throws Exception {
        mockMvc.perform(get("/activities?page=0&size=10&sortBy=title"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].title").value("Berlin Tour"))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.totalPages").value(1));
    }

    @Test
    void shouldSearchActivitiesByTitle() throws Exception {
        mockMvc.perform(get("/activities/search?title=Berlin&page=0&size=10&sortBy=title"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].title").value("Berlin Tour"))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.totalPages").value(1));
    }

    @Test
    void shouldReturnBadRequestForEmptyTitle() throws Exception {
        mockMvc.perform(get("/activities/search?title=&page=0&size=10&sortBy=title"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Title parameter cannot be empty"));
    }
}
