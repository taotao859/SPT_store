<template>
  <el-container>
    <el-aside width="200px">
      <el-menu :default-active="this.$router.path" router>
        <el-menu-item index="/retail">
          <template slot="title">零售</template>
        </el-menu-item>
        <el-submenu index="2">
          <template slot="title">批发</template>
          <el-menu-item index="/wholeSaleOrder">批发订单</el-menu-item>
          <el-menu-item index="/wholeSaleOrderInfo">订单详情</el-menu-item>
        </el-submenu>
        <el-menu-item index="/saleLog">
          <template slot="title">销售记录</template>
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
            <el-button icon="el-icon-s-home" circle style="border: none;background: none" @click="$router.push({path: '/saleHomePage'})"></el-button>
            <el-button id="log-out-button" icon="el-icon-switch-button" @click="$router.push({path: '/login'})"></el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-row :gutter="5">
          <el-col :span="5">
            <el-input v-model="wholeSaleOrderId" placeholder="请输入订单编号"></el-input>
          </el-col>
          <el-col :span="3">
            <el-input v-model="operatorName" placeholder="执行者"></el-input>
          </el-col>
          <el-col :span="3" :offset="13">
            <el-button @click="search" style="width: 100px">查询</el-button>
          </el-col>
        </el-row>
        <br><br>
        <el-row>
          <el-col>
            <el-table :data="tableData" style="width: 1270px" height="600px" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
              <el-table-column prop="index" label="序" width="100px"></el-table-column>
              <el-table-column prop="retailOrderId" label="订单编号" width="350px"></el-table-column>
              <el-table-column prop="retailOrderDate" label="日期" width="350px"></el-table-column>
              <el-table-column prop="retailOrderOperator" label="执行者" width="250px"></el-table-column>
              <el-table-column label="操作" width="220px">
                <el-col style="text-align: center">
                  <el-link @click="dialogVisible = true">查看</el-link>
                </el-col>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
        <el-dialog title="订单明细" :visible.sync="dialogVisible" width="40%" :before-close="handleClose">
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
  name: 'SaleLog',
  data () {
    const item = {
      index: '1',
      retailOrderId: '12345678',
      retailOrderDate: '2021-11-29',
      retailOrderOperator: 'Joe'
    }
    const orderInfo = {
      index: '1',
      ItemId: '123455',
      ItemName: '面包',
      ItemQuantity: '2',
      ItemPrice: '3.00',
      ItemTotalPrice: '6.00'
    }
    return {
      pageName: '销售记录',
      salesName: 'Joe',
      date: '',
      tableData: Array(20).fill(item),
      orderInfo: Array(10).fill(orderInfo),
      dialogVisible: false
    }
  },
  methods: {
    search () {
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
  .el-table .cell {
    text-align: center;
  }
</style>
