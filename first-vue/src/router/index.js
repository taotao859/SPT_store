import Vue from 'vue'
import Router from 'vue-router'
import AppIndex from '@/components/home/AppIndex'
import Login from '@/components/Login'
import Retail from '@/components/Retail'
import SaleHomePage from '@/components/SaleHomePage'
import WholeSaleOrder from '@/components/WholeSaleOrder'
import WholeSaleOrderInfo from '@/components/WholeSaleOrderInfo'
import SaleLog from '@/components/SaleLog'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/Login',
      name: 'Login',
      component: Login
    },
    {
      path: '/index',
      name: 'AppIndex',
      component: AppIndex
    },
    {
      path: '/retail',
      name: 'Retail',
      component: Retail
    },
    {
      path: '/saleHomePage',
      name: 'SaleHomePage',
      component: SaleHomePage
    },
    {
      path: '/wholeSaleOrder',
      name: 'WholeSaleOrder',
      component: WholeSaleOrder
    },
    {
      path: '/wholeSaleOrderInfo',
      name: WholeSaleOrderInfo,
      component: WholeSaleOrderInfo
    },
    {
      path: '/saleLog',
      name: SaleLog,
      component: SaleLog
    }
  ]
})
