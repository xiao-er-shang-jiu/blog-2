import Vue from 'vue'
import VueRouter from 'vue-router'

import AdminIndex from '../admin/index'
import Editor from '../admin/components/editor'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'admin',
    component: AdminIndex,
    children: [
      {
        path: 'editor',
        name: 'editor',
        component: Editor
      }
    ]
  }
]

// eslint-disable-next-line no-new
const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
