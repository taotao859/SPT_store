import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import VueAxios from 'vue-axios'
import axios from 'axios'
import echarts from 'echarts'

// 引入vuex-store
import store from './store/index'

axios.defaults.baseURL = 'http://localhost:8443/api'
Vue.prototype.$axios = axios
Vue.config.productionTip = false
Vue.prototype.$echarts = echarts

Vue.use(ElementUI)
Vue.use(VueAxios, axios)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  render: h => h(App),
  router,
  store,
  components: { App },
  template: '<App/>'
})
