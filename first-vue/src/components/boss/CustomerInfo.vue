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
        <el-menu-item index="/profit">
          <template slot="title">收益信息</template>
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
            <el-button icon="el-icon-s-home" circle style="border: none;background: none" @click="$router.push({path: '/bossHomePage'})"></el-button>
            <el-button id="log-out-button" icon="el-icon-switch-button" @click="$router.push({path: '/login'})"></el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-row :gutter="20">
          <el-col :span="4">
            <el-input v-model="customerName" placeholder="客户姓名"></el-input>
          </el-col>
          <el-col :span="4">
            <el-select v-model="customerTag" placeholder="用户类型">
              <el-option value="零售客户"></el-option>
              <el-option value="批发客户"></el-option>
            </el-select>
          </el-col>
          <el-col :span="4">
            <el-input v-model="customerContact" placeholder="客户联系方式"></el-input>
          </el-col>
          <el-col :span="2">
            <el-button>添加</el-button>
          </el-col>
        </el-row>
        <br><br>
        <el-table :data="customerList" style="width: 100%" height="600px" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
          <el-table-column prop="index" label="序" width="76px"></el-table-column>
          <el-table-column prop="id" label="客户编号" width="300px"></el-table-column>
          <el-table-column prop="name" label="客户姓名" width="300px"></el-table-column>
          <el-table-column prop="tag" label="客户类型" width="320px"></el-table-column>
          <el-table-column prop="customerInfo" label="客户信息" width="300px">
            <el-link @click="customerinfo = true">查看</el-link>
          </el-table-column>
        </el-table>
        <el-dialog title="客户信息" :visible.sync="customerinfo" width="40%">
          <div v-text="customerList[0].name" style="line-height: 40px; font-size: 20px"></div>
          <div v-text="customerList[0].tag" style="line-height: 40px; font-size: 20px"></div>
          <div style="line-height: 40px; font-size: 20px">
            <el-row :gutter="20">
              <el-col :span="12" style="text-align: right">
                <span>联系方式:</span>
              </el-col>
              <el-col :span="12" style="text-align: left">
                <span v-text="customerList[0].contact"></span>
              </el-col>
            </el-row>
          </div>
          <div style="line-height: 40px; font-size: 20px">
            <el-row :gutter="20">
              <el-col :span="12" style="text-align: right">
                <span>积分:</span>
              </el-col>
              <el-col :span="12" style="text-align: left">
                <span v-text="customerList[0].score"></span>
              </el-col>
            </el-row>
          </div>
        </el-dialog>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'CustomerInfo',
  data () {
    const customer = {
      index: '1',
      id: '12344',
      name: 'Joe',
      tag: '零售客户',
      contact: '123-45-678',
      score: '123'
    }
    return {
      pageName: '用户信息',
      salesName: 'Joe',
      customerName: '',
      customerTag: '',
      customerContact: '',
      customerinfo: false,
      customerList: Array(15).fill(customer),
      editInfo: false
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
