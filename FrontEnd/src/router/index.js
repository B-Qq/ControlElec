import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/login'
import Main from '@/views/control_elec'
import Offline from '@/views/offline_record'
import 'element-ui/lib/theme-chalk/index.css'
import ElementUI from 'element-ui'

Vue.use(Router)
Vue.use(ElementUI)

const route = new Router({
  routes: [
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/',
      name: 'index',
      component: Login
    },
    {
      path: '/main',
      name: 'main',
      component: Main
    },
    {
      path: '/offline',
      name: 'offline',
      component: Offline
    }
  ],
  mode: 'history'
})

export default route

route.beforeEach((to, from, next) => {
  if (to.path === '/login') { // 当路由为login时就直接下一步操作
    next()
  } else { // 否则就需要判断
    if (sessionStorage.username) { // 如果有用户名就进行下一步操作
      next()
    } else {
      next({path: '/login'}) // 没有用户名就跳转到login页面
    }
  }
})
