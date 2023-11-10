import { createApp } from 'vue'
import touch from 'vue3-hand-mobile'
import App from './App.vue'
import router from './router'
import store from './store'

const app =createApp(App)
app.use(store)
store.dispatch('fetchUserInfo');
app.use(router).use(touch).mount('#app');
