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
          <el-col :span="5">
            <el-input v-model="productName" placeholder="商品名称"></el-input>
          </el-col>
          <el-col :span="19" style="text-align: left">
            <el-button @click="enquery">查询</el-button>
          </el-col>
        </el-row>
        <br><br>
        <el-table :data="productData" height="600px" :header-cell-style="{background:'#eef1f6',color:'#606266'}" @cell-click="handle">
          <el-table-column prop="index" label="序" width="46px"></el-table-column>
          <el-table-column prop="productId" label="商品编号" width="300px"></el-table-column>
          <el-table-column prop="productName" label="商品名称" width="300px"></el-table-column>
          <el-table-column prop="quantity" label="数量" width="250px"></el-table-column>
          <el-table-column prop="repository" label="仓库" width="200px"></el-table-column>
          <el-table-column prop="operation" label="操作" width="200px">
            <el-link @click="goodsTransferVisible = true">调货</el-link>
          </el-table-column>
        </el-table>
        <el-dialog title="调货" :visible.sync="goodsTransferVisible" width="25%">
          <el-form :model="form">
            <el-form-item label=" 源仓库" :label-width="formLabelWidth" style="text-align: left">
              <el-select v-model="srcRepository" style="width: 200px">
                <el-option v-for="item in repo" :key="item.value" :label="item.label" :value="item.value"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="目的仓库" :label-width="formLabelWidth" style="text-align: left">
              <el-select v-model="destRepository" style="width: 200px">
                <el-option v-for="item in repo" :key="item.value" :label="item.label" :value="item.value"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="商品数量" :label-width="formLabelWidth" style="text-align: left">
              <el-input-number v-model="transferQuantity" @change="handleChange" :min="0" style="width: 200px"></el-input-number>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="goodsTransferVisible = false">取 消</el-button>
            <el-button type="primary" @click="tuning">确 定</el-button>
          </div>
        </el-dialog>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'GoodsTransfer',
  data () {
    return {
      pageName: '商品调货',
      adminName: this.$store.state.staff_name,
      srcRepository: '',
      destRepository: '',
      productName: '',
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
      repoTransfer: '',
      transferQuantity: '',
      goodsTransferVisible: false,
      // productData: Array(20).fill(product),
      productData: []
    }
  },
  methods: {
    handleChange (quantity) {
      console.log(quantity)
    },
    enquery () {
      this.$axios.get('/repositorymanange/searchinventory?productName=' + this.productName).then(Response => {
        this.productData = Response.data

        this.productName = ''
      })
    },
    tuning () {
      this.$axios.get('/repositorymanange/tuning?srcRepositoryName=' + this.srcRepository + '&dstRepositoryName=' + this.destRepository +
      '&productName=' + this.$store.state.product_name + '&productQuantity=' + this.transferQuantity).then(Response => {
        if (Response.data.code === 200) {
          this.$message({
            message: '调货成功',
            type: 'success'
          })
          this.$axios.get('/repositorymanange/searchinventory').then(Response => {
            this.productData = Response.data
          })
        }
      })
      this.goodsTransferVisible = false
    },
    handle (row) {
      this.$store.commit('saveProduct_name', row['productName'])
    }
  },
  mounted: function () {
    this.$axios.get('/repositorymanange/searchinventory').then(Response => {
      this.productData = Response.data
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
