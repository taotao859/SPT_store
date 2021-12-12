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
        <el-menu-item index="/saleInfo">
          <template slot="title">销售详情</template>
        </el-menu-item>
        <el-menu-item index="/profit">
          <template slot="title">收益信息</template>
        </el-menu-item>
        <el-menu-item index="/afterSale">
          <template slot="title">售后服务</template>
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
            Hello, <span id="salesman" v-text="bossName"></span>
            <el-button icon="el-icon-s-home" circle style="border: none;background: none" @click="$router.push({path: '/bossHomePage'})"></el-button>
            <el-button id="log-out-button" icon="el-icon-switch-button" @click="$router.push({path: '/login'})"></el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-row :gutter="20">
          <el-col :span="4">
            <el-input v-model="productNameInput" placeholder="商品名"></el-input>
          </el-col>
          <el-col :span="4">
            <el-input v-model="customerNameInput" placeholder="客户名"></el-input>
          </el-col>
          <el-col :span="2">
            <el-button @click="query">查询</el-button>
          </el-col>
        </el-row>
        <br><br>
        <el-table :data="saleInfo" height="600px" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
          <el-table-column prop="index" label="序" width="96px"></el-table-column>
          <el-table-column prop="orderId" label="订单编号" width="250px"></el-table-column>
          <el-table-column prop="productName" label="商品名" width="250px"></el-table-column>
          <el-table-column prop="customerName" label="客户名" width="250px"></el-table-column>
          <el-table-column prop="orderState" label="订单状态" width="250px"></el-table-column>
          <el-table-column prop="orderPrice" label="金额" width="250px"></el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'SaleInfo',
  data () {
    return {
      pageName: '销售详情',
      bossName: this.$store.state.staff_name,
      productNameInput: '',
      customerNameInput: '',
      saleInfo: []
    }
  },
  methods: {
    query () {
    }
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
