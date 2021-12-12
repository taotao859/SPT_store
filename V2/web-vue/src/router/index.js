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
      component: Boss,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/salesperson',
      name: 'salesperson',
      component: salesperson,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/retail',
      name: 'Retail',
      component: Retail,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/wholeSaleOrder',
      name: 'WholeSaleOrder',
      component: WholeSaleOrder,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/saleLog',
      name: 'SaleLog',
      component: SaleLog,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/wholeSaleOrderInfo',
      name: 'WholeSaleOrderInfo',
      component: WholeSaleOrderInfo,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/saleHomePage',
      name: 'SaleHomePage',
      component: SaleHomePage,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/repoHomePage',
      name: 'RepoHomePage',
      component: RepoHomePage,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/repoPurchase',
      name: RepoPurchase,
      component: RepoPurchase,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/repoPurchaseLog',
      name: 'RepoPurchaseLog',
      component: RepoPurchaseLog,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/stockTrack',
      name: StockTrack,
      component: StockTrack,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/statisticalQuery',
      name: StatisticalQuery,
      component: StatisticalQuery,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/goodsTransfer',
      name: GoodsTransfer,
      component: GoodsTransfer,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/bossHomePage',
      name: 'BossHomePage',
      component: Boss,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/productList',
      name: Productlist,
      component: Productlist,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/staffList',
      name: StaffList,
      component: StaffList,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/profit',
      name: Profit,
      component: Profit,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/afterSale',
      name: AfterSale,
      component: AfterSale,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/stock',
      name: Stock,
      component: Stock,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/repoOutLog',
      name: RepoOutLog,
      component: RepoOutLog,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/customerInfo',
      name: CustomerInfo,
      component: CustomerInfo,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/saleInfo',
      name: SaleInfo,
      component: SaleInfo,
      meta: {
        requireAuth: true
      }
    }
  ]
})
