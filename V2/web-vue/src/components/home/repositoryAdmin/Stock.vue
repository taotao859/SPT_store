<template>
  <el-container>
    <el-aside width="200px">
      <el-menu :default-active="this.$router.path" router :default-openeds="['3']">
        <el-submenu index="1">
          <template slot="title">入库</template>
          <el-menu-item index="/repoPurchase">进货</el-menu-item>
          <el-menu-item index="/repoPurchaseLog">进货记录</el-menu-item>
        </el-submenu>
        <el-menu-item index="/stockTrack">
          <template slot="title">库存盘点</template>
        </el-menu-item>
        <el-menu-item index="/goodsTransfer">
          <template slot="title">商品调货</template>
        </el-menu-item>
        <el-submenu index="3">
          <template slot="title">统计查询</template>
          <el-menu-item index="/repoOutLog">出库记录</el-menu-item>
          <el-menu-item index="/stock">库存查询</el-menu-item>
        </el-submenu>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <el-row :gutter="20">
          <el-col :span="4" style="text-align: left">
            <div v-text="pageName"></div>
          </el-col>
          <el-col :span="20" style="text-align: right">
            Hello, <span id="salesman" v-text="adminName"></span>
            <el-button icon="el-icon-s-home" circle style="border: none;background: none" @click="$router.push({path: '/repoHomePage'})"></el-button>
            <el-button id="log-out-button" icon="el-icon-switch-button" @click="$router.push({path: '/login'})"></el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-row :gutter="20">
          <el-col :span="3">
            <el-select v-model="repositorySelect" placeholder="仓库">
              <el-option v-for="item in repo" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-col>
          <el-col :span="5">
            <el-input v-model="productNameInput" placeholder="商品名称"></el-input>
          </el-col>
          <el-col :span="4" style="text-align: left">
            <el-button @click="check">查询</el-button>
          </el-col>
          <el-col :span="11" style="text-align: right; line-height: 40px; font-size: 20px">
            积压总金额：<span v-text="stockMoney"></span>
          </el-col>
        </el-row>
        <br><br>
        <el-table :data="stockData" height="600px" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
          <el-table-column prop="index" label="序" width="49px"></el-table-column>
          <el-table-column prop="productId" label="商品编号" width="350px"></el-table-column>
          <el-table-column prop="productName" label="商品名称" width="300px"></el-table-column>
          <el-table-column prop="quantity" label="数量" width="200px"></el-table-column>
          <el-table-column prop="repository" label="仓库" width="200px"></el-table-column>
          <el-table-column prop="productStockMoney" label="积压金额" width="180px"></el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {

  name: 'Stock',
  data () {
    return {
      pageName: '库存查询',
      adminName: this.$store.state.staff_name,
      productNameInput: '',
      repo: [{
        value: '门店',
        label: '门店'
      }, {
        value: '仓库1',
        label: '仓库1'
      }, {
        value: '仓库2',
        label: '仓库2'
      }],
      repositorySelect: '',
      // stockData: Array(15).fill(product),
      stockData: [],
      stockMoney: ''
    }
  },
  mounted: function () {
    this.$axios.get('/repositorymanange/searchinventory?repositoryName=&productName=').then(Response => {
      this.stockData = Response.data
    })
  },
  methods: {
    check () {
      this.$axios.get('/repositorymanange/searchinventory?repositoryName=' + this.repositorySelect + '&productName=' + this.productNameInput).then(Response => {
        this.stockData = Response.data

        this.repositorySelect = ''
        this.productNameInput = ''
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
</style>
