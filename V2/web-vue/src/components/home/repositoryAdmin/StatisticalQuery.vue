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
        <el-menu-item index="/goodsTransfer">
          <template slot="title">仓库调货</template>
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
            Hello, <span id="salesman" v-text="this.$store.state.staff_name"></span>
            <el-button icon="el-icon-s-home" circle style="border: none;background: none" @click="$router.push({path: '/repoHomePage'})"></el-button>
            <el-button id="log-out-button" icon="el-icon-switch-button" @click="$router.push({path: '/login'})"></el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-row :gutter="20">
          <el-col :span="4">
            <el-select v-model="repository" placeholder="仓库">
              <el-option v-for="item in repo" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-col>
          <el-col :span="4">
            <el-select v-model="category" placeholder="查询类型">
              <el-option v-for="item in cate" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-col>
          <el-col :span="16" style="text-align: left">
            <el-button>查询</el-button>
          </el-col>
        </el-row>
        <br><br>
        <el-table :data="repoDataCheck" height="600px" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
          <el-table-column prop="index" label="序" width="46px"></el-table-column>
          <el-table-column prop="id" label="编号" width="300px"></el-table-column>
          <el-table-column prop="date" label="日期" width="300px"></el-table-column>
          <el-table-column prop="operator" label="执行者" width="250px"></el-table-column>
          <el-table-column prop="category" label="类型" width="200px"></el-table-column>
          <el-table-column prop="operation" label="操作" width="200px"></el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'statisticalQuery',
  data () {
    return {
      pageName: '统计查询',
      adminName: 'Joe',
      repo: [{
        value: '选项1',
        label: '仓库1'
      }, {
        value: '选项2',
        label: '仓库2'
      }, {
        value: '选项3',
        label: '仓库3'
      }],
      repository: '',
      cate: [{
        value: '选项1',
        label: '盘点记录'
      }, {
        value: '选项2',
        label: '入库记录'
      }, {
        value: '选项3',
        label: '出库记录'
      }, {
        value: '选项4',
        label: '调货记录'
      }, {
        value: '选项5',
        label: '库存记录'
      }],
      category: ''
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
.el-table, .cell {
  text-align: center;
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
