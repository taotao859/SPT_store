<template>
  <el-container>
    <el-aside width="200px">
      <el-menu :default-active="this.$router.path" router >
        <el-submenu index="1">
          <template slot="title">零售</template>
          <el-menu-item index="/retail" @click="init_retail">零售订单</el-menu-item>
          <el-menu-item index="/saleLog">订单详情</el-menu-item>
        </el-submenu>
        <el-submenu index="2">
          <template slot="title">批发</template>
          <el-menu-item index="/wholeSaleOrder" @click="init_wholeSaleOrder">批发订单</el-menu-item>
          <el-menu-item index="/wholeSaleOrderInfo">订单详情</el-menu-item>
        </el-submenu>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        Hello, <span id="salesman" v-text="this.$store.state.staff_name"></span>
        <el-button icon="el-icon-s-home" circle style="border: none;background: none" @click="$router.push({path: '/salesperson'})"></el-button>
        <el-button id="log-out-button" icon="el-icon-switch-button" @click="$router.push({path: '/login'})"></el-button>
      </el-header>
      <el-main>
        <div style="font-size: 70px; line-height: 500pt">WELCOME</div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>

export default {
  name: 'SaleHomePage',
  data () {
    return {
    }
  },
  methods: {
    init_retail () {
      this.$axios.get('/retail/init?staffId=' + this.$store.state.staff_id + '&repositoryId=1').then(response => {
        this.$store.commit('saveStaff_init_order_id',response.data.ordersId)
        this.$router.replace({path: '/retail'})
      })

    },
    handleSelect2 (key, keyPath) {
      console.log(key, keyPath)
    },
    init_wholeSaleOrder () {
      this.$axios.get('/wholesale/init?staffId=' + this.$store.state.staff_id + '&repositoryId=1').then(response => {
        this.$store.commit('saveStaff_init_order_id',response.data.ordersId)
      })

    },
  }
}
</script>

<style>
.el-container {
  height: 100%;
}
.el-header {
  background-color: #75d9d9;
  color: black;
  font-size: large;
  font-weight: bold;
  line-height: 60px;
  text-align: right;
}
.el-aside {
  background: #f0ffff;
  text-align: center;
  line-height: 60px;
}
.el-main {
  background-color: beige;
  color: black;
  text-align: center;
}
#log-out-button {
  background: none;
  border: none;
}
.el-menu-item {
  background-color: #f0ffff;
}
.el-submenu {
  background-color: #f0ffff;
}
</style>
