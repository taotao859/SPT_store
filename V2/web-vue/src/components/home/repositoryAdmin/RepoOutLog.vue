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
          <el-col :span="16" style="text-align: left">
            <el-button @click="enquery">查询</el-button>
          </el-col>
        </el-row>
        <br><br>
        <el-table :data="repoOutData" height="600px" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
          <el-table-column prop="index" label="序" width="66px"></el-table-column>
          <el-table-column prop="productName" label="商品名称" width="350px"></el-table-column>
          <el-table-column prop="time" label="操作时间" width="350px"></el-table-column>
          <el-table-column prop="repoName" label="仓库号" width="230px"></el-table-column>
          <el-table-column prop="quantity" label="数量" width="300px"></el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'RepoOutLog',
  data () {
    return {
      pageName: '出库记录',
      adminName: this.$store.state.staff_name,
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
      productNameInput: '',
      // repoOutData: Array(15).fill(repoOut),
      repoOutData: [],
      orderDetailVisible: false,
      // orderDetail: Array(10).fill(detail)
      orderDetail: []
    }
  },
  methods: {
    handle (row) {
      this.$store.commit('saveStaff_init_order_id', row['orderId'])
    },
    // 查询某一订单
    enquery () {
      this.axios.get('/repositorymanange/searchrecord?repositoryName=' + this.repositorySelect + '&productName=' + this.productNameInput).then(Response => {
        this.repoOutData = Response.data

        this.repositorySelect = ''
        this.productNameInput = ''
      })
    },
    // 查看出库订单明细详情
    check () {
      this.$axios.post('/examine', {order: this.$store.state.init_order_id}).then(Response => {
        this.orderDetail = Response.data
      })
      this.orderDetailVisible = true
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
