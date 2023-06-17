import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: () => import('../views/index.vue'),
    meta: {
      keepAlive: true
    }
  },
  {
    path: '/manage_student',
    name: 'manage_student',
    component: () => import('../views/manage_student.vue'),
    meta: {
      keepAlive: true
    }
  },
  {
    path: '/manage_teacher',
    name: 'manage_teacher',
    component: () => import('../views/manage_teacher.vue'),
    meta: {
      keepAlive: true
    }
  },
  {
    path: '/manage_course',
    name: 'manage_course',
    component: () => import('../views/manage_course.vue'),
    meta: {
      keepAlive: true
    }
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/login.vue'),
    meta: {
      keepAlive: false
    }
  },
  {
    path: '/forget',
    name: 'forget',
    component: () => import('../views/newcode.vue'),
    meta: {
      keepAlive: false
    }
  },
  {
    path: '/modify',
    name: 'modify',
    component: () => import('../views/newcode.vue'),
    meta: {
      keepAlive: false
    }
  },
  {
    path: '/CourseTable',
    name: 'coursetable',
    component: () => import('../views/CourseTable.vue'),
    meta: {
      keepAlive: true
    }
  },
  {
    path: '/AllCourse',
    name: 'allcourse',
    component: () => import('../views/AllCourse.vue'),
    meta: {
      keepAlive: true
    }
  },
  {
    path: '/SelectCourse',
    name: 'selectcourse',
    component: () => import('../views/SelectCourse.vue'),
    meta: {
      keepAlive: true
    }
  },
  {
    path: '/messageCenter',
    name: 'messagecenter',
    component: () => import('../views/message_center.vue'),
    meta: {
      keepAlive: true
    }
  },
  {
    path: '/get_score',
    name: 'get_score',
    component: () => import('../views/get_score.vue'),
    meta: {
      keepAlive: true
    }
  },
  {
    path: '/view_score',
    name: 'view_score',
    component: () => import('../views/view_score.vue'),
    meta: {
      keepAlive: true
    }
  },
  {
    path: '/forum',
    name: 'forum',
    component: () => import('../views/forum.vue'),
    meta: {
      keepAlive: true
    }
  },
  {
    path: '/check',
    name: 'check',
    component: () => import('../views/manager.vue'),
    meta: {
      keepAlive: true
    }
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  if (to.path !== '/login' && to.path !== '/forget' && to.path !== '/modify')
  {
    console.log("to.path")
    console.log(to.path)
    const token = JSON.parse(localStorage.getItem('token'))
    console.log("token")
    console.log(token)
    if (token) {
      if (token.userName.length===1&&(to.path === '/manage_student' || to.path === '/manage_teacher'
          || to.path === '/manage_course' || to.path === '/' || to.path === '/forum' || to.path === '/check'))
        next()
      //学生
      else if (token.userName.length === 7 && (to.path === '/CourseTable' || to.path === '/SelectCourse'
          || to.path === '/forget' || to.path === '/modify' || to.path === '/AllCourse'
          || to.path === '/messageCenter' || to.path === '/' || to.path === '/forum' || to.path === '/view_score'))
        next()
      //老师
      else if (token.userName.length === 5 && (to.path === '/get_score' || to.path === '/messageCenter'
          || to.path === '/' || to.path === '/forum'))
        next()
      else
        next(from.path)
    }
    else {
      next('/login')
    }
  }
  else {
    next()
  }
})

export default router
