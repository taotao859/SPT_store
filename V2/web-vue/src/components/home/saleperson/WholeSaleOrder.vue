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
        <el-row :gutter="20">
          <el-col :span="4" style="text-align: left">
            <div>批发订单</div>
          </el-col>
          <el-col :span="20" style="text-align: right">
            Hello, <span id="salesman" v-text="this.$store.state.staff_name"></span>
            <el-button icon="el-icon-s-home" circle style="border: none;background: none" @click="$router.push({path: '/salesperson'})"></el-button>
            <el-button id="log-out-button" icon="el-icon-switch-button" @click="$router.push({path: '/login'})"></el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-row :gutter="40">
          <el-col :span="14">
            <el-table :data="tableData"  height="600px" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
              <el-table-column prop="id" label="序" width="50px"></el-table-column>
              <el-table-column prop="productId" label="商品编号" width="199px"></el-table-column>
              <el-table-column prop="productName" label="商品名称" width="190px"></el-table-column>
              <el-table-column prop="productQuantity" label="数量" width="100px"></el-table-column>
              <el-table-column prop="productRetailPrice" label="单价" width="100px"></el-table-column>
              <el-table-column prop="singlePrice" label="总价" width="100px"></el-table-column>
            </el-table>
          </el-col>
          <el-col :span="8">
            <div class="grid-content" style="height:600px; width:500px; background: white">
              <br><br>
              <el-row>
                批发订单编号: <span v-text="this.$store.state.init_order_id"></span>
              </el-row>
              <br><br>
              <el-row>
                <div style="line-height: 100px; font-size: 50px">总 计</div>
              </el-row>
              <el-row v-text="wholePrice" style="font-size: 80px"></el-row>
            </div>
          </el-col>
        </el-row>
        <br><br>
        <el-row :gutter="20">
          <el-col :span="5">
            <el-input v-model="input" placeholder="请输入商品编号" type="text" oninput="this.value = this.value.replace(/[^0-9]/g, '');"></el-input>
          </el-col>
          <el-col :span="2">
            <el-input v-model="itemQuantity" placeholder="数量" type="text" oninput="this.value = this.value.replace(/[^0-9]/g, '');"></el-input>
          </el-col>
          <el-col :span="3">
            <el-input v-model="itemPrice" placeholder="批发价" type="text" oninput="this.value = this.value.replace(/[^0-9.]/g, '');"></el-input>
          </el-col>
          <el-col :span="2">
            <el-button @click="addItem">添加</el-button>
          </el-col>
          <el-col :span="2">
            <el-button @click="deleteItem">删除</el-button>
          </el-col>
          <el-col :span="5">
            <el-button style="width: 100%" @click="save">保存</el-button>
          </el-col>
          <el-col :span="5">
            <el-button style="width: 100%" @click="verify">提交</el-button>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'WholeSaleOrder',
  data () {
    return {
      Orders: {
        ordersId: this.$store.state.init_order_id
      },
      salesName: this.$store.state.staff_name,
      tableData: [],
      input: '',
      itemQuantity: '',
      salesNumber: '',
      wholePrice: '',
      itemPrice: ''// 批发价
    }
  },
  methods: {
    addItem () {
      this.$axios.get('/wholesale/add?productId=' + this.input + '&productQuantity=' + this.itemQuantity + '&productPrice=' + this.itemPrice + '&ordersId=' + this.$store.state.init_order_id)
        .then(Response => {
          this.wholePrice = Response.data.ordersTotalPrice
          this.tableData = Response.data.orderitemViewList

          this.input = ''
          this.itemQuantity = ''
          this.itemPrice = ''
        })
      this.$alert('添加成功', '添加确认', {
        confirmButtonText: '确认',
        callback: action => {
        }
      })
    },
    deleteItem () {
      this.$axios.get('/wholesale/delete?productId=' + this.input + '&productQuantity=' + this.itemQuantity + '&productPrice=' + this.itemPrice + '&ordersId=' + this.$store.state.init_order_id)
        .then(Response => {
          this.wholePrice = Response.data.ordersTotalPrice
          this.tableData = Response.data.orderitemViewList

          this.input = ''
          this.itemQuantity = ''
          this.itemPrice = ''
        })
    },
    verify () {
      this.$alert('提交成功', '订单确认', {
        confirmButtonText: '确认',
        callback: action => {
        }
      })
      this.tableData = null
      this.wholePrice = 0
      this.$axios.get('/wholesale/init?staffId=' + this.$store.state.staff_id + '&repositoryId=1').then(response => {
        this.$store.commit('saveStaff_init_order_id', response.data.ordersId)
      })
    },
    // 审核订单
    save () {
      this.$alert('保存成功', '订单确认', {
        confirmButtonText: '确认',
        callback: action => {
        }
      })
    },
    // 收款
    check () {
      this.$axios.post('/wholesale/pay', { Orders: this.Orders }).then(
        this.$alert('收款成功', '收款确认', {
          confirmButtonText: '确认',
          callback: action => {
          }
        }),
        this.tableData = null,
        this.wholePrice = 0

      )
      // eslint-disable-next-line standard/object-curly-even-spacing
      this.$axios.post('/wholesale/init', {staffId: this.$store.state.staff_id, repositoryId: null, customerId: null }).then(response => {
        this.$store.commit('saveStaff_init_order_id', response.data.ordersId)
      })
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
</style>
