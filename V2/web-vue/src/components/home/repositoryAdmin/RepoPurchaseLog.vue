<template>
  <el-container>
    <el-aside width="200px">
      <el-menu :default-active="this.$router.path" router :default-openeds="['1']">
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
          <el-col :span="4">
            <el-select v-model="repository" placeholder="仓库">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-col>
          <el-col :span="3">
            <el-input v-model="productName" placeholder="商品名称"></el-input>
          </el-col>
          <el-col :span="17" style="text-align: left">
            <el-button @click="enquery">查询</el-button>
          </el-col>
        </el-row>
        <br><br>
        <el-table :data="purchaseDataLog" height="600px" :header-cell-style="{background:'#eef1f6',color:'#606266'}" @cell-click="handle">
          <el-table-column prop="index" label="序" width="46px"></el-table-column>
          <el-table-column prop="productId" label="货品ID" width="250px"></el-table-column>
          <el-table-column prop="productName" label="商品名称" width="250px"></el-table-column>
          <el-table-column prop="price" label="进货价格" width="200px"></el-table-column>
          <el-table-column prop="quantity" label="进货数量" width="200px"></el-table-column>
          <el-table-column prop="time" label="入库时间" width="200px"></el-table-column>
          <el-table-column prop="repository" label="仓库" width="200px"></el-table-column>
          <el-table-column prop="operator" label="执行者" width="200px"></el-table-column>
        </el-table>
        <el-dialog title="入库明细" :visible.sync="purchaseDetailVisible" width="40%">
          <el-table :data="purchaseData" height="430px" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
            <el-table-column prop="index" label="序" width="40px"></el-table-column>
            <el-table-column prop="productId" label="商品编号" width="150px"></el-table-column>
            <el-table-column prop="productName" label="商品名称" width="150px"></el-table-column>
            <el-table-column prop="quantity" label="数量" width="104px"></el-table-column>
            <el-table-column prop="purchasePrice" label="进货价" width="130px"></el-table-column>
          </el-table>
        </el-dialog>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'repoPurchaseLog',
  data () {
    return {
      pageName: '进货记录',
      adminName: this.$store.state.staff_name,
      options: [
        {
          value: '门店',
          label: '门店'
        },
        {
          value: '仓库1',
          label: '仓库1'
        },
        {
          value: '仓库2',
          label: '仓库2'
        }
      ],
      repository: '',
      repoStaff: [{
        value: '选项1',
        label: 'Joe'
      }, {
        value: '选项2',
        label: 'Sandy'
      }, {
        value: '选项3',
        label: 'Edmund'
      }],
      repostaff: '',
      productName: '',
      // purchaseDataLog: Array(10).fill(purchaseOrder),
      purchaseDetailVisible: false,
      purchaseDataLog: []
      // purchaseData: Array(15).fill(purchaseDetail)
    }
  },
  mounted: function () {
    this.$axios.get('/repositorymanange/all').then(Response => {
      this.purchaseDataLog = Response.data
    })
  },
  methods: {
    // 查询
    enquery () {
      this.$axios.get('/repositorymanange/searchpurchase?repositoryName=' + this.repository + '&productName=' + this.productName).then(Response => {
        this.purchaseDataLog = Response.data

        this.repository = ''
        this.productName = ''
      })
    },
    handle (row, column, cell, event) {
      this.$store.commit('savePurchaseOrder_id', row['purchaseId'])
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
