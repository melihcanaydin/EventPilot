package com.eventpilot.controller;

import com.eventpilot.model.Activity;
import com.eventpilot.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes APIs for managing activities.
 */
@RestController
@RequestMapping("/activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Operation(summary = "Get all activities", description = "Fetch all activities with pagination and sorting.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of activities", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Activity.class), examples = @ExampleObject(value = """
                        {
                            "content": [
                                {
                                    "id": 25651,
                                    "title": "German Tour: Parliament Quarter & Reichstag glass dome",
                                    "price": 14,
                                    "currency": "$",
                                    "rating": 4.8,
                                    "specialOffer": false
                                }
                            ],
                            "pageable": {
                                "pageNumber": 0,
                                "pageSize": 10
                            },
                            "totalPages": 1,
                            "totalElements": 1
                        }
                    """)))
    })
    @GetMapping
    public Page<Activity> getAllActivities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String orderBy) {
        return activityService.getAllActivities(page, size, orderBy);
    }

    @Operation(summary = "Search activities by title", description = "Search for activities using a title keyword.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Matching activities", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Activity.class), examples = @ExampleObject(value = """
                        {
                            "content": [
                                {
                                    "id": 25651,
                                    "title": "German Tour: Parliament Quarter & Reichstag glass dome",
                                    "price": 14,
                                    "currency": "$",
                                    "rating": 4.8,
                                    "specialOffer": false
                                }
                            ],
                            "pageable": {
                                "pageNumber": 0,
                                "pageSize": 10
                            },
                            "totalPages": 1,
                            "totalElements": 1
                        }
                    """))),
            @ApiResponse(responseCode = "400", description = "Invalid title", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                        {
                            "error": "Title parameter cannot be empty"
                        }
                    """)))
    })
    @GetMapping("/search")
    public Page<Activity> searchActivities(
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String orderBy) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title parameter cannot be empty");
        }
        return activityService.searchActivities(title, page, size, orderBy);
    }
}