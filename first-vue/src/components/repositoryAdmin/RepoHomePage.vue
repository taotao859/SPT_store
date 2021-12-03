<template>
  <el-container>
    <el-aside width="200px">
      <el-menu :default-active="this.$router.path" router>
        <el-submenu index="1">
          <template slot="title">入库</template>
          <el-menu-item index="/repoPurchase">进货</el-menu-item>
          <el-menu-item index="/repoPurchaseLog">进货记录</el-menu-item>
        </el-submenu>
        <el-menu-item index="/stockTrack">
          <template slot="title">库存盘点</template>
        </el-menu-item>
        <el-menu-item index="/statisticalQuery">
          <template slot="title">统计查询</template>
        </el-menu-item>
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
        <div style="font-size: 70px; line-height: 500pt">WELCOME</div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'RepoHomePage',
  data () {
    return {
      pageName: '首页',
      adminName: 'Joe'
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
