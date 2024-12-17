const API_BASE_URL = 'http://localhost:8080';

export async function getActivities(page = 0) {
    try {
        const response = await fetch(`${API_BASE_URL}/activities?page=${page}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            },
        });

        if (!response.ok) {
            throw new Error(`Failed to fetch activities. Status: ${response.status}`);
        }

        const data = await response.json();
        return data.content || [];
    } catch (error) {
        console.error('Error fetching activities:', error);
        throw error;
    }
}

export async function searchActivitiesByTitle(title) {
    try {
        const response = await fetch(`${API_BASE_URL}/activities/search?title=${encodeURIComponent(title)}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            },
        });

        if (!response.ok) {
            throw new Error(`Failed to search activities. Status: ${response.status}`);
        }

        const data = await response.json();
        return data.content || [];
    } catch (error) {
        console.error('Error searching activities:', error);
        throw error;
    }
}