import Vue from 'vue'
import VueRouter from 'vue-router'

// back
import Login from '../admin/login'
import AdminIndex from '../admin/index'
import Home from '../admin/components/home'
import blogEditor from '../admin/components/blog-editor'
import Types from '../admin/components/types'
import Tags from '../admin/components/tags'
import Blogs from '../admin/components/blogs'

// front
import Index from '../display/index'
import blogsIndex from '../display/components/blogs'
import searchResult from '../display/components/search-result'
import blogDetail from '../display/components/blog-detail'
import typesIndex from '../display/components/types'
import tagIndex from '../display/components/tags'
import Archives from '../display/components/archives'
import About from '../display/components/about'

Vue.use(VueRouter);

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
    redirect: '/admin/home',
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
        path: 'tags',
        name: 'tags',
        component: Tags
      },
      {
        path: 'blogs',
        name: 'blogs',
        component: Blogs
      }
    ]
  },
  {
    path: '/',
    name: 'index',
    component: Index,
    redirect: '/blogs',
    children: [
      {
        path: 'blogs',
        name: 'blogsIndex',
        component: blogsIndex
      },
      {
        path: 'search/:text',
        name: 'search',
        component: searchResult
      },
      {
        path: 'blog-detail/:id',
        name: 'blog-detail',
        component: blogDetail
      },
      {
        path: 'types/:id',
        name: 'typesIndex',
        component: typesIndex
      },
      {
        path: 'tags/:id',
        name: 'tagIndex',
        component: tagIndex
      },
      {
        path: 'archives',
        name: 'archives',
        component: Archives
      },
      {
        path: 'about',
        name: 'about',
        component: About
      }
    ]
  }
]

// eslint-disable-next-line no-new
const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

export default router
