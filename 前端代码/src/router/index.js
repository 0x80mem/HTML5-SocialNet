import { createRouter, createWebHistory } from 'vue-router'
import DecodeView from '../views/DecodeView.vue'

const routes = [
  {
    path: '/login',
    name: 'login',
    component: () => import(/* webpackChunkName: "about" */ '../views/LoginView.vue')
  },
  {
    path: '/',
    name: 'decode',
    component: DecodeView
    //component: () => import(/* webpackChunkName: "about" */ '../views/testView/ProfileEditView.vue')
  },
  {
    path: '/infoEdit',
    name: 'infoEdit',
    component: () => import(/* webpackChunkName: "about" */ '../views/ProfileEditView.vue')
  },
  // {
  //   path: '/register',
  //   name: 'register',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/RegisterView.vue')
  // }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
