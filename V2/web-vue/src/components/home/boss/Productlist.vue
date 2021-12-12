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
            <el-input v-model="productName" placeholder="商品名称"></el-input>
          </el-col>
          <el-col :span="3">
            <el-input v-model="productCost" placeholder="成本价"></el-input>
          </el-col>
          <el-col :span="3">
            <el-input v-model="retailPrice" placeholder="零售价"></el-input>
          </el-col>
          <el-col :span="2">
            <el-button @click = "addItem">添加</el-button>
          </el-col>
        </el-row>
        <br><br>
        <el-table :data="productList" style="width: 100%" height="600px" :header-cell-style="{background:'#eef1f6',color:'#606266'}" @cell-click="handle">
          <el-table-column prop="index" label="序" width="50px"></el-table-column>
          <el-table-column prop="productId" label="商品编号" width="300px"></el-table-column>
          <el-table-column prop="productName" label="商品名称" width="300px"></el-table-column>
          <el-table-column prop="productCost" label="成本价" width="200px"></el-table-column>
          <el-table-column prop="retailPrice" label="零售价" width="200px"></el-table-column>
          <el-table-column prop="operation" label="操作" width="246px">
            <el-col :span="12">
              <el-link @click="modifyVisible = true">编辑</el-link>
            </el-col>
            <el-col :span="12">
              <el-link @click="deleteProduct">删除</el-link>
            </el-col>
          </el-table-column>
        </el-table>
        <el-dialog title="商品编辑" :visible.sync="modifyVisible" width="25%">
          <el-input v-model="updatePrice" placeholder="零售价" style="width: 70%"></el-input>
          <span slot="footer">
            <el-button @click="modifyVisible = false">取消</el-button>
            <el-button @click="examine">保存</el-button>
          </span>
        </el-dialog>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'ProductList',
  data () {
    const product = {
      index: '1',
      productId: '123456',
      productName: '鼠标',
      productCost: '3.00',
      retailPrice: '10.00'
    }
    return {
      pageName: '商品清单',
      bossName: this.$store.state.staff_name,
      productCost: '',
      productName: '',
      retailPrice: '',
      productList: [],
     // productList: Array(20).fill(product),
      //productList: this.product,
      modifyVisible: false,
      updatePrice: ''
    }
  },
  mounted: function() {
    this.$axios.get("/product/all").then(Response=> {
      this.productList = Response.data
    })
  },
  methods: {
    //添加商品
    addItem(){
      this.$axios.post('/product/add',{productName:this.productName,productCost: this.productCost,productRetailPrice:this.retailPrice})
      .then(Response=>{
        if( Response.data.code === 200)
        {
          this.$axios.get("/product/all").then(Response=> {
            this.productList = Response.data
          })
        }
        this.productName=''
        this.productCost=''
        this.retailPrice=''
      })
    },
    //删除商品
    deleteProduct () {
      this.$axios.get("/product/delete?productId=" + this.$store.state.product_id).then(Response=> {
        if(Response.data.code === 200)
        {
          this.$message({
            message: '商品删除成功',
            type: 'success'
          },
            this.$axios.get("/product/all").then(Response=> {
              this.productList = Response.data
            })
          )
        }
      })
    },
    //更改零售价
    examine () {
      this.$axios.post('/product/update',{productId: this.$store.state.product_id, productRetailPrice: this.updatePrice}).then(Response=>{
        if( Response.data.code === 200)
        {
          this.$axios.get("/product/all").then(Response=> {
            this.productList = Response.data
          })
        }
        this.updatePrice=''
      })
      this.modifyVisible = false
    },
    handle (row, column, cell, event) {
      this.$store.commit('saveProduct_id',row['productId'])
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
