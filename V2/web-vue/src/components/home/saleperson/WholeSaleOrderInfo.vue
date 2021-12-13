<template>
  <el-container>
    <el-aside width="200px">
      <el-menu :default-active="this.$router.path" :default-openeds="['2']" router @cell-click="handle">
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
            <div>订单详情</div>
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
            <el-table :data="tableData" style="width: 1270px" height="600px" :header-cell-style="{background:'#eef1f6',color:'#606266'}" @cell-click="handle">
              <el-table-column prop="index" label="序" width="80px"></el-table-column>
              <el-table-column prop="wholeSaleOrderId" label="批发订单编号" width="280px"></el-table-column>
              <el-table-column prop="wholeSaleOrderDate" label="日期" width="270px"></el-table-column>
              <el-table-column prop="wholeSaleOrderOperator" label="执行者" width="210px"></el-table-column>
              <el-table-column prop="wholeSaleOrderState" label="订单状态" width="180px"></el-table-column>
              <el-table-column label="操作" width="250px">

                <el-col :span="8" style="text-align: center">
                  <el-link @click="examine" >查看</el-link>
                </el-col>
                <el-col :span="8" style="text-align: center">
                  <el-link @click="check" >审核</el-link>
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
            <el-table-column prop="id" label="序" width="40px"></el-table-column>
            <el-table-column prop="productId" label="商品编号" width="150px"></el-table-column>
            <el-table-column prop="productName" label="商品名称" width="150px"></el-table-column>
            <el-table-column prop="productQuantity" label="数量" width="60px"></el-table-column>
            <el-table-column prop="productRetailPrice" label="单价" width="75px"></el-table-column>
            <el-table-column prop="singlePrice" label="合计" width="99px"></el-table-column>
          </el-table>
<!--          <div slot="footer">-->
<!--            <el-button @click="handInVisible = false">提交</el-button>-->
<!--          </div>-->
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
          <div style="line-height: 40px; font-size: 20px">
            <el-row :gutter="20">
              <el-col :span="12" style="text-align: right">
                <span>已支付：</span>
              </el-col>
              <el-col :span="12"  style="text-align: left">
                <span v-text="payStatisticalList.ordersPaidAmount"></span>
              </el-col>
            </el-row>
          </div>
          <div style="line-height: 40px; font-size: 20px">
            <el-row :gutter="20">
              <el-col :span="12" style="text-align: right">
                <span>待支付：</span>
              </el-col>
              <el-col :span="12"  style="text-align: left">
                <span v-text="payStatisticalList.ordersUnpaidAmount"></span>
              </el-col>
            </el-row>
          </div>
        </el-dialog>
        <el-dialog title="提交确认" :visible.sync="handInVisible" width="30%">
          <span>确认提交？</span>
          <span slot="footer" class="dialog-footer">
            <el-button @click="handInVisible=false;detailVisible=true">取消</el-button>
            <el-button @click="handInVisible=false;detailVisible=false">确认</el-button>
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
    // const item = {
    //   index: '1',
    //     wholeSaleOrderId: '12',
    //     wholeSaleOrderDate: '13',
    //     wholeSaleOrderOperator: '14',
    //     wholeSaleOrderState: '15'
    // }
    return {
      payStatistical: {
        time: '',
        totalPrice: '',
        payMoney: '',
        restMoney: '',
        operator: ''
      },
      item: {
        index: '1',
        wholeSaleOrderId: '1',
        wholeSaleOrderDate: '1',
        wholeSaleOrderOperator: '1',
        wholeSaleOrderState: '1'
      },
      orderItem: {
        index: '2',
        ItemId: '',
        ItemName: '',
        ItemQuantity: '',
        ItemPrice: '',
        ItemTotalPrice: ''
      },
      itemList: [
        // {
        //   index: '1',
        //   wholeSaleOrderId: '1',
        //   wholeSaleOrderDate: '1',
        //   wholeSaleOrderOperator: '1',
        //   wholeSaleOrderState: '1'
        // },
        // {
        //   index: '1',
        //   wholeSaleOrderId: '1',
        //   wholeSaleOrderDate: '1',
        //   wholeSaleOrderOperator: '1',
        //   wholeSaleOrderState: '1'
        // }
      ], // 订单列表
      orderItemList: [], // 订单详情列表
      payStatisticalList: {
        ordersPaidAmount: '',
        ordersUnpaidAmount: ''
      }, // 支付详情
      salesName: this.$store.state.staff_name,
      date: '',
      money: '',
      detailVisible: false,
      payVisible: false,
      handInVisible: false,
      orderId: '',
      operatorName: '',
      // tableData: Array(20).fill(this.item),
      // orderInfo: Array().fill(this.orderItem),
      // payInfo: Array().fill(this.payStatistical),
      // 订单列表
      tableData: [],
      // 订单详情列表
      orderInfo: []
    }
  },
  mounted: function () {
    this.$axios.get('/wholesale/filter').then(Response => {
      this.tableData = Response.data
    })
  },
  methods: {
    // 查询方法，传入为ID和执行者，都为可选项，如果为空展示所有订单
    search () {
      this.axios.get('/wholesale/filter?ordersId=' + this.orderId + '&staffName=' + this.operatorName).then(Response => {
        this.tableData = Response.data

        this.orderId = ''
        this.operatorName = ''
      })
    },
    // 返回收款明细
    pay () {
      this.axios.get('/wholesale/pay?ordersId=' + this.$store.state.init_order_id + '&paidMoney=' + this.money).then(Response => {
        // this.payInfo.ordersPaidAmount = Response.data.ordersPaidAmount
        // this.payInfo.ordersUnpaidAmount = Response.data.ordersUnpaidAmount
        this.payStatisticalList = Response.data
      })
    },
    // 查看订单明细方法
    examine () {
      this.detailVisible = true
      this.$axios.get('/wholesale/get?ordersId=' + this.$store.state.init_order_id).then(Response => {
        this.orderInfo = Response.data
      })
    },
    // 订单审核 传入的订单ID怎么获得还有待商榷，这里暂时先以输入查询的作为目标
    check () {
      this.axios.post('/wholesale/check', {ordersId: this.$store.state.init_order_id}).then(Response => {
        if (Response.code === 200) {
          this.$alert('审核通过', '审核确认', {
            confirmButtonText: '确认',
            callback: action => {
            }
          })
        }
        if (Response.code === 400) {
          this.$alert('审核未通过', '审核确认', {
            confirmButtonText: '确认',
            callback: action => {
            }
          })
        }
      })
    },
    handleClose (done) {
      this.$confirm('确认提交？')
        .then(_ => {
          done()
        })
        .catch(_ => {})
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
