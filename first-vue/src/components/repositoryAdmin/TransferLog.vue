<template>
  <el-container>
    <el-aside width="200px">
      <el-menu :default-active="this.$router.path" router :default-openeds="['2']">
        <el-submenu index="1">
          <template slot="title">入库</template>
          <el-menu-item index="/repoPurchase">进货</el-menu-item>
          <el-menu-item index="/repoPurchaseLog">进货记录</el-menu-item>
        </el-submenu>
        <el-menu-item index="/stockTrack">
          <template slot="title">库存盘点</template>
        </el-menu-item>
        <el-submenu index="2">
          <template slot="title">调货</template>
          <el-menu-item index="/goodsTransfer">商品调货</el-menu-item>
          <el-menu-item index="/transferLog">调货记录</el-menu-item>
        </el-submenu>
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
        <el-table :data="transferData" height="680px" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
          <el-table-column prop="index" label="序" width="46px"></el-table-column>
          <el-table-column prop="transferId" label="调货编号" width="200px"></el-table-column>
          <el-table-column prop="transferDate" label="调货日期" width="250px"></el-table-column>
          <el-table-column prop="productName" label="商品名称" width="200px"></el-table-column>
          <el-table-column prop="quantity" label="数量" width="200px"></el-table-column>
          <el-table-column prop="sourceRepository" label="原始仓库" width="200px"></el-table-column>
          <el-table-column prop="destinationRepository" label="目的仓库" width="200px"></el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'TransferLog',
  data () {
    const transferItem = {
      index: '1',
      transferId: '12345',
      transferDate: '2021-12-1',
      productName: '手机',
      quantity: '100',
      sourceRepository: '1',
      destinationRepository: '3'
    }
    return {
      pageName: '调货记录',
      adminName: 'Joe',
      transferData: Array(15).fill(transferItem)
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
