<template>
  <el-container>
    <el-aside width="200px">
      <el-menu :default-active="this.$router.path" router>
        <el-menu-item index="/productList">
          <template slot="title">商品清单</template>
        </el-menu-item>
        <el-menu-item index="/staffList">
          <template slot="title">员工列表</template>
        </el-menu-item>
        <el-menu-item index="/customerInfo">
          <template slot="title">客户信息</template>
        </el-menu-item>
        <el-menu-item index="/profit">
          <template slot="title">收益信息</template>
        </el-menu-item>
        <el-menu-item index="/afterSale">
          <template slot="title">售后服务</template>
        </el-menu-item>
        <el-menu-item index="/saleInfo">
          <template slot="title">销售详情</template>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <el-row>
          <el-col :span="4" style="text-align: left">
            <div v-text="pageName"></div>
          </el-col>
          <el-col :span="20" style="text-align: right">
            Hello, <span id="salesman" v-text="salesName"></span>
            <el-button icon="el-icon-s-home" circle style="border: none;background: none" @click="$router.push({path: '/bossHomePage'})"></el-button>
            <el-button id="log-out-button" icon="el-icon-switch-button" @click="$router.push({path: '/login'})"></el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-table :data="afterSaleTable" height="700px" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
          <el-table-column prop="index" label="序" width="79px"></el-table-column>
          <el-table-column prop="ordersId" label="订单编号" width="400px"></el-table-column>
          <el-table-column prop="ordersTime" label="订单时间" width="400px"></el-table-column>
          <el-table-column prop="operator" label="执行者" width="400px"></el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {

  name: 'AfterSale',
  data () {
    const afterSaleDetail = {
      index: '1',
      orderId: '123432',
      orderTime: '2021-12-4',
      operator: 'Joe'
    }
    return {
      pageName: '售后服务',
      salesName: this.$store.state.staff_name,
      //afterSaleTable: Array(15).fill(afterSaleDetail)
      afterSaleTable:[]
    }

  },
  mounted: function() {
    this.$axios.get("/aftersale/all").then(Response=> {
      this.afterSaleTable = Response.data
    })
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
.el-table__body-wrapper::-webkit-scrollbar {
  width: 10px;
  height: 10px;
}
.el-table__body-wrapper::-webkit-scrollbar-thumb {
  background-color: #bdbdbd;
  border-radius: 5px;
}
.el-table__body-wrapper::-webkit-scrollbar-track {
  background-color: #e3e3e3;
}
.el-table, .cell {
  text-align: center;
}
</style>
