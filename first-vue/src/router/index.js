import Vue from 'vue'
import Router from 'vue-router'
import AppIndex from '@/components/home/AppIndex'
import Login from '@/components/Login'
import Retail from '@/components/salesman/Retail'
import SaleHomePage from '@/components/salesman/SaleHomePage'
import WholeSaleOrder from '@/components/salesman/WholeSaleOrder'
import WholeSaleOrderInfo from '@/components/salesman/WholeSaleOrderInfo'
import SaleLog from '@/components/salesman/SaleLog'
import RepoHomePage from '@/components/repositoryAdmin/RepoHomePage'
import RepoPurchase from '@/components/repositoryAdmin/RepoPurchase'
import RepoPurchaseLog from '@/components/repositoryAdmin/RepoPurchaseLog'
import StockTrack from '@/components/repositoryAdmin/StockTrack'
import StatisticalQuery from '@/components/repositoryAdmin/StatisticalQuery'
import GoodsTransfer from '@/components/repositoryAdmin/GoodsTransfer'

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
    },
    {
      path: '/repoHomePage',
      name: RepoHomePage,
      component: RepoHomePage
    },
    {
      path: '/repoPurchase',
      name: RepoPurchase,
      component: RepoPurchase
    },
    {
      path: '/repoPurchaseLog',
      name: RepoPurchaseLog,
      component: RepoPurchaseLog
    },
    {
      path: '/stockTrack',
      name: StockTrack,
      component: StockTrack
    },
    {
      path: '/statisticalQuery',
      name: StatisticalQuery,
      component: StatisticalQuery
    },
    {
      path: '/goodsTransfer',
      name: GoodsTransfer,
      component: GoodsTransfer
    }
  ]
})
