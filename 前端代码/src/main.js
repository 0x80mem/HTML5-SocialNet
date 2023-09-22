import { createApp } from 'vue'
import { Form } from 'vant';
import touch from 'vue3-hand-mobile'
import App from './App.vue'
import router from './router'
import store from './store'

createApp(App).use(store).use(router).use(touch).use(Form).mount('#app')
