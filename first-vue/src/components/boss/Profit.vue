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
        <el-menu-item index="/profit">
          <template slot="title">收益信息</template>
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
          <el-col :span="7">
            <el-date-picker  v-model="dateValue" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期">
            </el-date-picker>
          </el-col>
          <el-col :span="2">
            <el-button>查询</el-button>
          </el-col>
        </el-row>
        <br><br>
        <el-table :data="profitTable" height="600px" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
          <el-table-column prop="index" label="序" width="46px"></el-table-column>
          <el-table-column prop="orderId" label="订单编号" width="300px"></el-table-column>
          <el-table-column prop="orderTime" label="订单时间" width="300px"></el-table-column>
          <el-table-column prop="orderCategory" label="订单类型" width="250px"></el-table-column>
          <el-table-column prop="orderProfit" label="订单收益" width="200px"></el-table-column>
          <el-table-column prop="operation" label="操作" width="200px">
            <el-link @click="detailVisible = true">查看</el-link>
          </el-table-column>
        </el-table>
        <el-dialog title="订单明细" :visible.sync="detailVisible" width="40%">
          <el-table :data="orderInfo" style="width: 100%" height="400px" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
            <el-table-column prop="index" label="序" width="40px"></el-table-column>
            <el-table-column prop="ItemId" label="商品编号" width="150px"></el-table-column>
            <el-table-column prop="ItemName" label="商品名称" width="150px"></el-table-column>
            <el-table-column prop="ItemQuantity" label="数量" width="60px"></el-table-column>
            <el-table-column prop="ItemPrice" label="单价" width="75px"></el-table-column>
            <el-table-column prop="ItemTotalPrice" label="合计" width="99px"></el-table-column>
          </el-table>
        </el-dialog>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'Profit',
  data () {
    const profit = {
      index: '1',
      orderId: '43532124',
      orderTime: '2021-12-01 14:23',
      orderCategory: '零售',
      orderProfit: '123.01'
    }
    const orderDetail = {
      index: '1',
      ItemId: '123455',
      ItemName: '面包',
      ItemQuantity: '2',
      ItemPrice: '3.00',
      ItemTotalPrice: '6.00'
    }
    return {
      pageName: '收益信息',
      bossName: 'Joe',
      dateValue: '',
      profitTable: Array(15).fill(profit),
      detailVisible: false,
      orderInfo: Array(15).fill(orderDetail)
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
