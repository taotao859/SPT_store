<template>
  <el-container>
    <el-aside width="200px">
      <el-menu :default-active="this.$router.path" router :default-openeds="['2']">
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
            <el-input v-model="orderId" placeholder="请输入订单编号"></el-input>
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
              <el-table-column prop="index" label="序" width="80px"></el-table-column>
              <el-table-column prop="wholeSaleOrderId" label="批发订单编号" width="280px"></el-table-column>
              <el-table-column prop="wholeSaleOrderDate" label="日期" width="270px"></el-table-column>
              <el-table-column prop="wholeSaleOrderOperator" label="执行者" width="210px"></el-table-column>
              <el-table-column prop="wholeSaleOrderState" label="订单状态" width="180px"></el-table-column>
              <el-table-column label="操作" width="250px">
                <el-col :span="8" style="text-align: center">
                  <el-link @click="detailVisible = true">查看</el-link>
                </el-col>
                <el-col :span="8" style="text-align: center">
                  <el-link>审核</el-link>
                </el-col>
                <el-col :span="8" style="text-align: center">
                  <el-link @click="payVisible = true">收款</el-link>
                </el-col>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
        <el-dialog title="订单明细" :visible.sync="detailVisible" width="40%">
          <el-table :data="orderInfo" style="width: 100%" height="400px" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
            <el-table-column prop="index" label="序" width="40px"></el-table-column>
            <el-table-column prop="ItemId" label="商品编号" width="150px"></el-table-column>
            <el-table-column prop="ItemName" label="商品名称" width="150px"></el-table-column>
            <el-table-column prop="ItemQuantity" label="数量" width="60px"></el-table-column>
            <el-table-column prop="ItemPrice" label="单价" width="75px"></el-table-column>
            <el-table-column prop="ItemTotalPrice" label="合计" width="99px"></el-table-column>
          </el-table>
          <div slot="footer">
            <el-button @click="handinVisible = true">提交</el-button>
          </div>
        </el-dialog>
        <el-dialog title="款项明细" :visible.sync="payVisible" width="40%">
          <el-row :gutter="20">
            <el-col :span="12">
              <el-input v-model="money" placeholder="请输入支付金额"></el-input>
            </el-col>
            <el-col :span="12" style="text-align: right">
              <el-button @click="pay">确认</el-button>
            </el-col>
          </el-row>
          <br><br>
          <el-table :data="payInfo" style="width: 100%" height="300px" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
            <el-table-column prop="time" label="时间" width="170px"></el-table-column>
            <el-table-column prop="totalPrice" label="总金额" width="100px"></el-table-column>
            <el-table-column prop="payMoney" label="实付" width="100px"></el-table-column>
            <el-table-column prop="restMoney" label="结余" width="100px"></el-table-column>
            <el-table-column prop="operator" label="执行者" width="100px"></el-table-column>
          </el-table>
        </el-dialog>
        <el-dialog title="提交确认" :visible.sync="handinVisible" width="30%">
          <span>确认提交？</span>
          <span slot="footer" class="dialog-footer">
            <el-button @click="handinVisible=false;detailVisible=true">取消</el-button>
            <el-button @click="handinVisible=false;detailVisible=false">确认</el-button>
          </span>
        </el-dialog>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'WholeSaleOrderInfo',
  data () {
    const item = {
      index: '1',
      wholeSaleOrderId: '12345678',
      wholeSaleOrderDate: '2021-11-29',
      wholeSaleOrderOperator: 'Joe',
      wholeSaleOrderState: '待审核'
    }
    const orderItem = {
      index: '1',
      ItemId: '1234567',
      ItemName: '苹果',
      ItemQuantity: '3',
      ItemPrice: '5.20',
      ItemTotalPrice: '15.60'
    }
    const payStatistical = {
      time: '2021-12-1 16:00',
      totalPrice: '5000.00',
      payMoney: '1000.00',
      restMoney: '2000.00',
      operator: 'Joe'
    }
    return {
      pageName: '订单详情',
      salesName: 'Joe',
      date: '',
      money: '',
      detailVisible: false,
      payVisible: false,
      handinVisible: false,
      orderId: '',
      operatorName: '',
      tableData: Array(20).fill(item),
      orderInfo: Array(8).fill(orderItem),
      payInfo: Array(10).fill(payStatistical)
    }
  },
  methods: {
    search () {
    },
    pay () {
    },
    handleClose (done) {
      this.$confirm('确认提交？')
        .then(_ => {
          done()
        })
        .catch(_ => {})
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
