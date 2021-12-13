<template>
  <el-container>
    <el-aside width="200px">
      <el-menu :default-active="this.$router.path" :default-openeds="['1']" router>
        <el-submenu index="1">
          <template slot="title">零售</template>
          <el-menu-item index="/retail">零售订单</el-menu-item>
          <el-menu-item index="/saleLog">订单详情</el-menu-item>
        </el-submenu>
        <el-submenu index="2">
          <template slot="title">批发</template>
          <el-menu-item index="/wholeSaleOrder">批发订单</el-menu-item>
          <el-menu-item index="/wholeSaleOrderInfo">订单详情</el-menu-item>
        </el-submenu>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <el-row>
          <el-col :span="4" style="text-align: left">
            <div v-text="pageName"></div>
          </el-col>
          <el-col :span="20" style="text-align: right">
            Hello, <span id="salesman" v-text="this.$store.state.staff_name"></span>
            <el-button icon="el-icon-s-home" circle style="border: none;background: none" @click="$router.push({path: '/salesperson'})"></el-button>
            <el-button id="log-out-button" icon="el-icon-switch-button" @click="$router.push({path: '/login'})"></el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-row :gutter="5">
          <el-col :span="5">
            <el-input v-model="orderId" placeholder="请输入订单编号" type="text" oninput="this.value = this.value.replace(/[^0-9]/g, '');"></el-input>
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
            <el-table :data="tableData" style="width: 1270px" height="600px" :header-cell-style="{background:'#eef1f6',color:'#606266'}" @cell-click="handle" >
              <el-table-column prop="index" label="序" width="80px"></el-table-column>
              <el-table-column prop="wholeSaleOrderId" label="零售订单编号" width="280px"></el-table-column>
              <el-table-column prop="wholeSaleOrderDate" label="日期" width="270px"></el-table-column>
              <el-table-column prop="wholeSaleOrderOperator" label="执行者" width="210px"></el-table-column>
              <el-table-column prop="wholeSaleOrderState" label="订单状态" width="180px"></el-table-column>
              <el-table-column label="操作" width="250px">
                <el-row>
                  <el-col :span="12">
                    <el-link @click = "examine">查看</el-link>
                  </el-col>
                  <el-col :span="12">
                    <el-link @click="refund">退货</el-link>
                  </el-col>
                </el-row>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
        <el-dialog title="订单明细" :visible.sync="dialogVisible" width="40%">
          <el-table :data="orderInfo" style="width: 100%" height="400px" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
            <el-table-column prop="id" label="序" width="40px"></el-table-column>
            <el-table-column prop="productId" label="商品编号" width="150px"></el-table-column>
            <el-table-column prop="productName" label="商品名称" width="150px"></el-table-column>
            <el-table-column prop="productQuantity" label="数量" width="60px"></el-table-column>
            <el-table-column prop="productRetailPrice" label="单价" width="75px"></el-table-column>
            <el-table-column prop="singlePrice" label="合计" width="99px"></el-table-column>
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
    // const item = {
    //   index: '1',
    //   retailOrderId: '12345678',
    //   retailOrderDate: '2021-11-29',
    //   orderState: '交易成功',
    //   retailOrderOperator: 'Joe'
    // }
    // const orderInfo = {
    //   index: '1',
    //   ItemId: '123455',
    //   ItemName: '面包',
    //   ItemQuantity: '2',
    //   ItemPrice: '3.00',
    //   ItemTotalPrice: '6.00'
    // }
    return {
      pageName: '销售记录',
      salesName: 'Joe',
      date: '',
      orderId: '',
      operatorName: '',
      // tableData: Array(20).fill(item),
      // orderInfo: Array(10).fill(orderInfo),
      // 订单列表
      tableData: [],
      // 订单明细列表
      orderInfo: [],
      dialogVisible: false
    }
  },
  mounted: function () {
    this.$axios.get('/retail/filter?ordersId=' + this.orderId + '&staffName=' + this.operatorName).then(Response => {
      this.tableData = Response.data
    })
  },
  methods: {
    // 查询订单
    search () {
      this.axios.get('/retail/filter?ordersId=' + this.orderId + '&staffName=' + this.operatorName).then(Response => {
        this.tableData = Response.data
      })
    },
    // 退货
    refund () {
      this.axios.get('/retail/refund?ordersId=' + this.$store.state.init_order_id).then(Response => {
        if (Response.data.code === 200) {
          this.$message({type: 'success', message: '退货成功'})
        }
      })
    },
    examine () {
      this.axios.get('/wholesale/get?ordersId=' + this.$store.state.init_order_id).then(Response => {
        this.orderInfo = Response.data
      })
      this.dialogVisible = true
    },
    handle (row, column, cell, event) {
      this.$store.commit('saveStaff_init_order_id', row['wholeSaleOrderId'])
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
