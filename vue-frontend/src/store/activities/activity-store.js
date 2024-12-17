import { createStore } from 'vuex';
import { getActivities, searchActivitiesByTitle } from '@/services/activity-service.js';

const activityStore = createStore({
    state: {
        activities: [],
        isLoading: false,
        errorMessage: '',
    },
    mutations: {
        setActivities(state, activities) {
            state.activities = activities;
        },

        setLoading(state, isLoading) {
            state.isLoading = isLoading;
        },

        setError(state, message) {
            state.errorMessage = message;
        },
    },
    actions: {
        async fetchAllActivities({ commit }) {
            commit('setLoading', true);
            commit('setError', '');

            try {
                const activities = await getActivities(0);
                commit('setActivities', activities);
            } catch (error) {
                commit('setError', 'Failed to load activities. Please try again.');
            } finally {
                commit('setLoading', false);
            }
        },

        async fetchActivitiesByTitle({ commit }, searchQuery) {
            if (!searchQuery.trim()) {
                return await this.dispatch('fetchAllActivities');
            }

            commit('setLoading', true);
            commit('setError', '');

            try {
                const activities = await searchActivitiesByTitle(searchQuery);
                commit('setActivities', activities);
            } catch (error) {
                commit('setError', 'Failed to load activities. Please try again.');
            } finally {
                commit('setLoading', false);
            }
        },
    },
    getters: {
        allActivities(state) {
            return state.activities;
        },

        isLoading(state) {
            return state.isLoading;
        },

        errorMessage(state) {
            return state.errorMessage;
        },
    },
});

export default activityStore;