import Vue from 'vue'
import VueRouter from 'vue-router'

import Login from '../admin/login'
import AdminIndex from '../admin/index'
import Home from '../admin/components/home'
import blogEditor from '../admin/components/blog-editor'
import Types from '../admin/components/types'
import typesAdd from "../admin/components/types-add";
import Tags from '../admin/components/tags'
import tagsAdd from '../admin/components/tags-add'
import Blogs from '../admin/components/blogs'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/admin',
    name: 'admin',
    component: AdminIndex,
    children: [
      {
        path: 'home',
        name: 'home',
        component: Home
      },
      {
        path: 'blog-editor/:id',
        name: 'blog-editor',
        component: blogEditor
      },
      {
        path: 'types',
        name: 'types',
        component: Types
      },
      {
        path: 'types-add/:id/:name',
        name: 'types-add',
        component: typesAdd
      },
      {
        path: 'tags',
        name: 'tags',
        component: Tags
      },
      {
        path: 'tags-add/:id/:name',
        name: 'tags-add',
        component: tagsAdd
      },
      {
        path: 'blogs',
        name: 'blogs',
        component: Blogs
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
