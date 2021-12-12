import Vue from 'vue'
import Router from 'vue-router'
// 导入刚才编写的组件
import Login from '@/components/Login'
// eslint-disable-next-line camelcase
import salesperson from '../components/home/saleperson/salesperson'
import Boss from '../components/home/boss/Boss'
import Retail from '../components/home/saleperson/Retail'
import WholeSaleOrder from '../components/home/saleperson/WholeSaleOrder'
import SaleLog from '../components/home/saleperson/SaleLog'
import WholeSaleOrderInfo from '../components/home/saleperson/WholeSaleOrderInfo'
import SaleHomePage from '../components/home/saleperson/SaleHomePage'
import RepoPurchase from '../components/home/repositoryAdmin/RepoPurchase'
import RepoPurchaseLog from '../components/home/repositoryAdmin/RepoPurchaseLog'
import StockTrack from '../components/home/repositoryAdmin/StockTrack'
import StatisticalQuery from '../components/home/repositoryAdmin/StatisticalQuery'
import GoodsTransfer from '../components/home/repositoryAdmin/GoodsTransfer'
import Profit from '../components/home/boss/Profit'
import StaffList from '../components/home/boss/StaffList'
import RepoHomePage from '../components/home/repositoryAdmin/RepoHomePage'
import Productlist from '../components/home/boss/Productlist'
import AfterSale from '../components/home/boss/AfterSale'
import CustomerInfo from '../components/home/boss/CustomerInfo'
import Stock from '../components/home/repositoryAdmin/Stock'
import RepoOutLog from '../components/home/repositoryAdmin/RepoOutLog'
import SaleInfo from '../components/home/boss/SaleInfo'

Vue.use(Router)

export default new Router({
  routes: [
    // 下面都是固定的写法
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/Boss',
      name: 'Boss',
      component: Boss
    },
    {
      path: '/salesperson',
      name: 'salesperson',
      component: salesperson
    },
    {
      path: '/retail',
      name: 'Retail',
      component: Retail
    },
    {
      path: '/wholeSaleOrder',
      name: 'WholeSaleOrder',
      component: WholeSaleOrder
    },
    {
      path: '/saleLog',
      name: 'SaleLog',
      component: SaleLog
    },
    {
      path: '/wholeSaleOrderInfo',
      name: 'WholeSaleOrderInfo',
      component: WholeSaleOrderInfo
    },
    {
      path: '/saleHomePage',
      name: 'SaleHomePage',
      component: SaleHomePage
    },
    {
      path: '/repoHomePage',
      name: 'RepoHomePage',
      component: RepoHomePage
    },
    {
      path: '/repoPurchase',
      name: RepoPurchase,
      component: RepoPurchase
    },
    {
      path: '/repoPurchaseLog',
      name: 'RepoPurchaseLog',
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
    },
    {
      path: '/bossHomePage',
      name: 'BossHomePage',
      component: Boss
    },
    {
      path: '/productList',
      name: Productlist,
      component: Productlist
    },
    {
      path: '/staffList',
      name: StaffList,
      component: StaffList
    },
    {
      path: '/profit',
      name: Profit,
      component: Profit
    },
    {
      path: '/afterSale',
      name: AfterSale,
      component: AfterSale
    },
    {
      path: '/stock',
      name: Stock,
      component: Stock
    },
    {
      path: '/repoOutLog',
      name: RepoOutLog,
      component: RepoOutLog
    },
    {
      path: '/customerInfo',
      name: CustomerInfo,
      component: CustomerInfo
    },
    {
      path: '/saleInfo',
      name: SaleInfo,
      component: SaleInfo
    }
  ]
})
