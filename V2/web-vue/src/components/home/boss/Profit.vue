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
          <el-col :span="7">
<!--            日期对应的值为数组-->
            <el-date-picker  v-model="dateValue" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期">
            </el-date-picker>
          </el-col>
          <el-col :span="2">
            <el-button @click="query">查询</el-button>
          </el-col>
        </el-row>
        <br><br>
        <el-table :data="profitTable" height="600px" :header-cell-style="{background:'#eef1f6',color:'#606266'}"@cell-click="handle">
          <el-table-column prop="index" label="序" width="96px"></el-table-column>
          <el-table-column prop="orderId" label="订单编号" width="300px"></el-table-column>
          <el-table-column prop="orderTime" label="订单时间" width="300px"></el-table-column>
          <el-table-column prop="orderCategory" label="订单类型" width="300px"></el-table-column>
          <el-table-column prop="orderProfit" label="订单收益" width="300px"></el-table-column>
        </el-table>
        <el-dialog title="订单明细" :visible.sync="detailVisible" width="40%">
          <el-table :data="orderInfo" style="width: 100%" height="400px" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
            <el-table-column prop="id" label="序" width="100px"></el-table-column>
            <el-table-column prop="productId" label="商品编号" width="200px"></el-table-column>
            <el-table-column prop="productName" label="商品名称" width="200px"></el-table-column>
            <el-table-column prop="productQuantity" label="数量" width="100px"></el-table-column>
            <el-table-column prop="productRetailPrice" label="单价" width="100px"></el-table-column>
            <el-table-column prop="singlePrice" label="总价" width="150px"></el-table-column>
          </el-table>
        </el-dialog>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  mounted: function() {
    this.$axios.get("/account/sum").then(Response=> {
      this.profitTable = Response.data.orders_list
    })
  },
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
      bossName: this.$store.state.staff_name,
      dateValue: '',
      //profitTable: Array(15).fill(profit),
      profitTable: [],
      detailVisible: false,
      //订单明细详情
      //orderInfo: Array(15).fill(orderDetail),
      orderInfo:[]
    }
  },
  methods:{
    //查询
    query(){
      this.$axios.get('account/sum?startDate='+this.dateValue[0] + '&endDate=' + this.dateValue[1]).then(Response=>{
        this.profitTable = Response.data.orders_list
      })
    },
    //查看具体信息
    check(){
      this.axios.post('/retail/examine',{ordersId : this.$store.state.init_order_id}).then(Response => {
        this.orderInfo = Response.data.orderItemList
      }),
      this.detailVisible = true
    },
    handle (row, column, cell, event) {
      this.$store.commit('saveStaff_init_order_id',row['orderId'])
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
