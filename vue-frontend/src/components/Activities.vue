<template>
  <div class="activities__container">
    <h1 class="activities__title">Available Activities</h1>

    <!-- Search Input -->
    <div class="activities__search">
      <input v-model="searchQuery" placeholder="Search by title" type="text" class="activities__search-input"
        @input="handleSearch" />
    </div>

    <!-- Filters -->
    <div class="activities__filters">
      <input v-model.number="minPrice" placeholder="Min Price" type="number" @input="applyFilters" />
      <input v-model.number="maxPrice" placeholder="Max Price" type="number" @input="applyFilters" />
      <input v-model.number="minRating" placeholder="Min Rating" type="number" step="0.1" max="5"
        @input="applyFilters" />
      <label>
        <input type="checkbox" v-model="specialOffer" @change="applyFilters" />
        Special Offer
      </label>
    </div>

    <!-- Loading Indicator -->
    <div v-if="isLoading" class="activities__loader">
      <div class="loader"></div>
      <p>Loading activities...</p>
    </div>

    <!-- Error Message -->
    <div v-if="errorMessage" class="activities__error">
      <p>{{ errorMessage }}</p>
    </div>

    <!-- Activities Grid -->
    <div v-if="!isLoading && !errorMessage" class="activities__grid">
      <div v-for="activity in filteredActivities" :key="activity.id" class="activities__card">
        <h2 class="activities__card-title">{{ activity.title }}</h2>
        <p class="activities__card-price">
          Price: {{ activity.price }} {{ activity.currency }}
        </p>
        <p class="activities__card-rating">Rating: {{ activity.rating }}</p>
        <p class="activities__card-offer">
          Special Offer: {{ activity.specialOffer ? "Yes" : "No" }}
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex';
import "@/assets/activities.css";

export default {
  name: "ActivitiesList",
  data() {
    return {
      searchQuery: "",
      minPrice: "",
      maxPrice: "",
      minRating: "",
      specialOffer: false,
      filteredActivities: [],
    };
  },
  computed: {
    ...mapGetters(['allActivities', 'isLoading', 'errorMessage']),
  },
  methods: {
    ...mapActions(['fetchAllActivities', 'fetchActivitiesByTitle']),

    handleSearch() {
      this.fetchActivitiesByTitle(this.searchQuery);
    },

    applyFilters() {
      let filtered = this.allActivities;

      if (this.minPrice !== "") {
        filtered = filtered.filter(activity => activity.price >= parseInt(this.minPrice));
      }
      if (this.maxPrice !== "") {
        filtered = filtered.filter(activity => activity.price <= parseInt(this.maxPrice));
      }
      if (this.minRating !== "") {
        filtered = filtered.filter(activity => activity.rating >= parseFloat(this.minRating));
      }
      if (this.specialOffer) {
        filtered = filtered.filter(activity => activity.specialOffer);
      }

      this.filteredActivities = filtered;
    },
  },
  watch: {
    allActivities() {
      this.applyFilters();
    },
  },
  mounted() {
    this.fetchAllActivities();
  },
};
</script>