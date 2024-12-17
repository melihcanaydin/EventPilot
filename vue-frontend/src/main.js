import { createApp } from 'vue';
import App from './App.vue';
import activityStore from './store/activities/activity-store.js';

const app = createApp(App);

app.use(activityStore);

app.mount('#app');