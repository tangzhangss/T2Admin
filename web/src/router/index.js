import { createRouter,createWebHistory} from 'vue-router'

/* Layout */
import Layout from '@/layout'

export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/home',
    hidden: true,
    children: [{
        path: 'home',
        name: 'Home',
        component: () => import('@/views/home/index'),
        meta: { title: '首页', icon: 'el-icon-s-home' },
      },
      {
        path: '404',
        name: 'Page404',
        component: () => import('@/views/404'),
      }
    ]
  },
  { path: '/:.*', redirect: '/404', hidden: true }
]
export const router404={ path: '/:.*', redirect: '/404', hidden: true }

export const router = createRouter({
  history: createWebHistory(), // require service_api support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})
