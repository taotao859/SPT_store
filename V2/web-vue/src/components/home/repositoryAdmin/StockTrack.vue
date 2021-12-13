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
            <el-select v-model="repository" placeholder="仓库">
              <el-option v-for="item in repo" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-col>
          <el-col :span="4">
            <el-input v-model="productId" placeholder="商品编号" type="text" oninput="this.value = this.value.replace(/[^0-9]/g, '');"></el-input>
          </el-col>
          <el-col :span="4">
            <el-input-number v-model="updateQuantity" @change="handleChange" :min="0" style="width: 100%"></el-input-number>
          </el-col>
          <el-col :span="7" style="text-align: left">
            <el-button @click="add">新增盘点</el-button>
          </el-col>
        </el-row>
        <br><br>
        <el-table :data="repoDataCheck" height="600px" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
          <el-table-column prop="index" label="序" width="46px"></el-table-column>
          <el-table-column prop="productId" label="商品编号" width="350px"></el-table-column>
          <el-table-column prop="productName" label="商品名称" width="350px"></el-table-column>
          <el-table-column prop="repositoryName" label="仓库名" width="300px"></el-table-column>
          <el-table-column prop="updateQuantity" label="变化数量" width="200px"></el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'stockTrack',
  data () {
    return {
      pageName: '库存盘点',
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
      repository: '',
      updateQuantity: '',
      dateValue: '',
      checkNo: '123456789',
      repoDataCheck: [],
      productId: ''
    }
  },
  methods: {
    handleChange (quantity) {
      console.log(quantity)
    },
    add () {
      this.$axios.get('/repositorymanange/update?repositoryName=' + this.repository + '&productId=' + this.productId + '&newQuantity=' + this.updateQuantity)
        .then(Response => {
          this.repoDataCheck = Response.data

          this.repository = ''
          this.productId = ''
          this.updateQuantity = ''
        })
      this.$alert('新增成功', '盘点确认', {
        confirmButtonText: '确认',
        callback: action => {
        }
      })
    }
  },
  mounted: function () {
    this.$axios.get('/repositorymanange/allpandian').then(Response => {
      this.repoDataCheck = Response.data
    })
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
