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
import GoodsTransfer from '@/components/repositoryAdmin/GoodsTransfer'
import BossHomePage from '@/components/boss/bossHomePage'
import ProductList from '@/components/boss/ProductList'
import StaffList from '@/components/boss/StaffList'
import Profit from '@/components/boss/Profit'
import TransferLog from '@/components/repositoryAdmin/TransferLog'
import RepoOutLog from '@/components/repositoryAdmin/RepoOutLog'
import Stock from '@/components/repositoryAdmin/Stock'
import CustomerInfo from '@/components/boss/CustomerInfo'

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
      path: '/goodsTransfer',
      name: GoodsTransfer,
      component: GoodsTransfer
    },
    {
      path: '/transferLog',
      name: TransferLog,
      component: TransferLog
    },
    {
      path: '/repoOutLog',
      name: RepoOutLog,
      component: RepoOutLog
    },
    {
      path: '/stock',
      name: Stock,
      component: Stock
    },
    {
      path: '/bossHomePage',
      name: BossHomePage,
      component: BossHomePage
    },
    {
      path: '/productList',
      name: ProductList,
      component: ProductList
    },
    {
      path: '/staffList',
      name: StaffList,
      component: StaffList
    },
    {
      path: '/customerInfo',
      name: CustomerInfo,
      component: CustomerInfo
    },
    {
      path: '/profit',
      name: Profit,
      component: Profit
    }
  ]
})
