import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import VueAxios from 'vue-axios'
import axios from 'axios'
// 引入vuex-store
import store from './store/index'

axios.defaults.baseURL = 'http://localhost:8443/api'
Vue.prototype.$axios = axios
Vue.config.productionTip = false

Vue.use(ElementUI)
Vue.use(VueAxios, axios)

// 判断访问路径是否需要登录
router.beforeEach((to, from, next) => {
    if (to.meta.requireAuth) {
      // 判断store中是否存储了staff的信息，若存在则放行，否则跳转到登录界面
      if (store.state.staff_name) {
        next()
      } else {
        next({
          path: '/login',
          query: {redirect: to.fullPath}
        })
      }
    } else {
      next()
    }
  }
)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  render: h => h(App),
  router,
  store,
  components: { App },
  template: '<App/>'
})


