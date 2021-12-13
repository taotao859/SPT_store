<template>
  <el-container>
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
        <el-row :gutter="40">
          <el-col :span="14" :offset="1">
            <el-table :data="tableData" style="width: 850px" height="600px" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
              <el-table-column prop="id" label="序" width="100px"></el-table-column>
              <el-table-column prop="productId" label="商品编号" width="200px"></el-table-column>
              <el-table-column prop="productName" label="商品名称" width="200px"></el-table-column>
              <el-table-column prop="productQuantity" label="数量" width="100px"></el-table-column>
              <el-table-column prop="productRetailPrice" label="单价" width="100px"></el-table-column>
              <el-table-column prop="singlePrice" label="总价" width="150px"></el-table-column>
            </el-table>
          </el-col>
          <el-col :span="8">
            <div class="grid-content" style="height:600px; width:460px; background: white">
              <br><br>
              <el-row>
                订单编号: <span v-text="this.$store.state.init_order_id"></span>
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
          <el-col :span="6" :offset="1" style="text-align: left">
            <el-input v-model="input" style="width: 350px" placeholder="请输入商品编号" type="text" oninput="this.value = this.value.replace(/[^0-9]/g, '');"></el-input>
          </el-col>
          <el-col :span="2">
            <el-input v-model="itemQuantity" style="width:100px" placeholder="数量" type="text" oninput="this.value = this.value.replace(/[^0-9]/g, '');"></el-input>
          </el-col>
          <el-col :span="2">
            <el-button @click="addItem" style="width: 100px">添加</el-button>
          </el-col>
          <el-col :span="2">
            <el-button @click="deleteItem" style="width: 100px">删除</el-button>
          </el-col>
          <el-col :span="10" :offset="1">
            <el-button id="check-button" @click="check">收银</el-button>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>

export default {

  name: 'Retail',
  data () {
    return {
      Orders: {
        ordersId: this.$store.state.init_order_id
      },
      list: [],
      pageName: '零售',
      tableData: [],
      input: '',
      itemQuantity: '',
      salesNumber: this.$store.state.init_order_id,
      wholePrice: ''
    }
  },
  methods: {
    addItem () {
      this.$axios.get('/retail/add?productId=' + this.input + '&productQuantity=' + this.itemQuantity + '&ordersId=' + this.$store.state.init_order_id)
        .then(Response => {
          // addOrderitem(this.input,this.itemQuantity,this.$store.state.init_order_id).then(successResponse =>{
          this.wholePrice = Response.data.ordersTotalPrice
          this.tableData = Response.data.orderitemViewList

          this.input = ''
          this.itemQuantity = ''
        //  for temp in Response.data.orderitemviewList
        // this.list.id = Response.data.orderitemviewList.id
        //  this.list.productId = Response.data.orderitemviewList.productId
        //  this.list.product__name = Response.data.orderitemviewList.productName
        //  this.list.productQuantity = Response.data.orderitemviewList.productQuantity
        //  this.list.productRetailPrice = Response.data.orderitemviewList.productRetailPrice
        //  this.list.singleprice = Response.data.orderitemviewList.singlePrice
        })
      this.$alert('添加成功', '添加确认', {
        confirmButtonText: '确认',
        callback: action => {
        }
      })
    },
    deleteItem () {
      this.$axios.get('/retail/delete?productId=' + this.input + '&productQuantity=' + this.itemQuantity + '&ordersId=' + this.$store.state.init_order_id)
        .then(Response => {
          this.wholePrice = Response.data.ordersTotalPrice
          this.tableData = Response.data.orderitemViewList

          this.input = ''
          this.itemQuantity = ''
        })
      this.$alert('删除成功', '删除确认', {
        confirmButtonText: '确认',
        callback: action => {
        }
      })
    },
    check () {
      this.$axios.post('/retail/pay', {ordersId: this.$store.state.init_order_id}).then(

        this.$alert('收款成功', '收款确认', {
          confirmButtonText: '确认',
          callback: action => {
          }

        }),
        this.tableData = null,
        this.wholePrice = 0
      )
      this.$axios.get('/retail/init?staffId=' + this.$store.state.staff_id + '&repositoryId=1').then(response => {
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
  color: black;
}
#col1 {
  text-align: left;
}
#col2 {
  text-align: right;
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
#check-button {
  width: 465px;
  border-radius: 5px;
}
</style>
