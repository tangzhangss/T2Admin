import { createRouter,createWebHashHistory} from 'vue-router'

/* Layout */
import Layout from '@/layout'

export const router404={ path: '/:pathMatch(.*)', redirect: '/404', hidden: true }

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
      meta: { title: '首页', icon: 'el-icon-s-home',noCache:false},
    },
      {
        path: '404',
        name: 'Page404',
        component: () => import('@/views/404'),
      },
      {
        path: 'refresh',
        name: 'Refresh',
        component: () => import('@/views/refresh'),
      },
      {
        path: 'iFrame',
        component: () => import('@/views/iFrame'),
        hidden: true
      },
    ]
  },
  router404
]

export const router = createRouter({
  history: createWebHashHistory(), // require service_api support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

