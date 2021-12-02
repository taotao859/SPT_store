<template>
  <el-container>
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
        <el-row :gutter="40">
          <el-col :span="14" :offset="1">
            <el-table :data="tableData" style="width: 850px" height="600px" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
              <el-table-column prop="index" label="序" width="100px"></el-table-column>
              <el-table-column prop="productId" label="商品编号" width="200px"></el-table-column>
              <el-table-column prop="productName" label="商品名称" width="200px"></el-table-column>
              <el-table-column prop="quantity" label="数量" width="100px"></el-table-column>
              <el-table-column prop="price" label="单价" width="100px"></el-table-column>
              <el-table-column prop="totalPrice" label="总价" width="150px"></el-table-column>
            </el-table>
          </el-col>
          <el-col :span="8">
            <div class="grid-content" style="height:600px; width:460px; background: white">
              <br><br>
              <el-row>
                订单编号: <span v-text="salesNumber"></span>
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
            <el-input v-model="input" style="width: 350px" placeholder="请输入商品编号"></el-input>
          </el-col>
          <el-col :span="2">
            <el-input v-model="itemQuantity" style="width:100px" placeholder="数量"></el-input>
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
    const item = {
      index: '1',
      productId: '12345678',
      productName: '饼干',
      quantity: '2',
      price: '4.00',
      totalPrice: '8.00'
    }
    return {
      pageName: '零售',
      salesName: 'Joe',
      tableData: Array(1).fill(item),
      input: '',
      itemQuantity: '',
      salesNumber: '0000000000001',
      wholePrice: '100.00'
    }
  },
  methods: {
    addItem () {
    },
    deleteItem () {
    },
    check () {
      this.$alert('收款成功', '收款确认', {
        confirmButtonText: '确认',
        callback: action => {
        }
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
