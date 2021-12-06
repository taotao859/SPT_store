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
        <el-row :gutter="20">
          <el-col :span="6">
            <el-input v-model="productId" placeholder="商品编号"></el-input>
          </el-col>
          <el-col :span="4">
            <el-input-number v-model="productQuantity" @change="handleChange" :min="0" label="商品数量" style="width: 100%"></el-input-number>
          </el-col>
          <el-col :span="3">
            <el-input v-model="productPrice" placeholder="进货价"></el-input>
          </el-col>
          <el-col :span="4">
            <el-select v-model="repository" placeholder="仓库">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-col>
          <el-col :span="3" style="text-align: left">
            <el-button>入库</el-button>
          </el-col>
          <el-col :span="4" style="text-align: right">
            <span style="line-height: 40px">入库编号：</span>
            <span v-text="purchaseId" style="line-height: 40px"></span>
          </el-col>
        </el-row>
        <br><br>
        <el-table :data="purchaseData" height="530px" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
          <el-table-column prop="index" label="序" width="46px"></el-table-column>
          <el-table-column prop="productId" label="商品编号" width="300px"></el-table-column>
          <el-table-column prop="productName" label="商品名称" width="300px"></el-table-column>
          <el-table-column prop="quantity" label="数量" width="250px"></el-table-column>
          <el-table-column prop="price" label="进货价" width="200px"></el-table-column>
          <el-table-column prop="operator" label="执行者" width="200px"></el-table-column>
        </el-table>
        <br><br>
        <el-row :gutter="20">
          <el-col :span="6" style="text-align: left">
            <el-date-picker v-model="dateValue" type="date" placeholder="选择日期"></el-date-picker>
          </el-col>
          <el-col :span="18" style="text-align: right">
            <el-button>提交</el-button>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'repoPurchase',
  data () {
    const purchaseItem = {
      index: '1',
      productId: '12345678',
      productName: '香蕉',
      quantity: '20',
      price: '30.12',
      operator: 'Joe'
    }
    return {
      pageName: '进货',
      adminName: 'Joe',
      productId: '',
      productQuantity: '',
      productPrice: '',
      options: [{
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
      purchaseData: Array(20).fill(purchaseItem),
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() > Date.now()
        }
      },
      dateValue: '',
      purchaseId: '12345566'
    }
  },
  methods: {
    handleChange (quantity) {
      console.log(quantity)
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
