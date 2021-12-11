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
        <el-menu-item index="/afterSale">
          <template slot="title">售后服务</template>
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
            <el-button @click="addItem">添加</el-button>
          </el-col>
        </el-row>
        <br><br>
        <el-table :data="customerList" style="width: 100%" height="600px" :header-cell-style="{background:'#eef1f6',color:'#606266'}"@cell-click="handle">
          <el-table-column prop="index" label="序" width="76px"></el-table-column>
          <el-table-column prop="id" label="客户编号" width="300px"></el-table-column>
          <el-table-column prop="name" label="客户姓名" width="300px"></el-table-column>
          <el-table-column prop="tag" label="客户类型" width="320px"></el-table-column>
          <el-table-column prop="customerInfo" label="客户信息" width="300px">
            <el-link @click="check">查看</el-link>
          </el-table-column>
        </el-table>
        <el-dialog title="客户信息" :visible.sync="customerinfo" width="40%">
          <div v-text="staffInfo.name" style="line-height: 40px; font-size: 20px"></div>
          <div v-text="staffInfo.tag" style="line-height: 40px; font-size: 20px"></div>
          <div style="line-height: 40px; font-size: 20px">
            <el-row :gutter="20">
              <el-col :span="12" style="text-align: right">
                <span>联系方式:</span>
              </el-col>
              <el-col :span="12" style="text-align: left">
                <span v-text="staffInfo.contact"></span>
              </el-col>
            </el-row>
          </div>
          <div style="line-height: 40px; font-size: 20px">
            <el-row :gutter="20">
              <el-col :span="12" style="text-align: right">
                <span>积分:</span>
              </el-col>
              <el-col :span="12" style="text-align: left">
                <span v-text="staffInfo.score"></span>
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
      salesName: this.$store.state.staff_name,
      //下面三个是输入框用来绑定数据的
      customerName: '',
      customerTag: '',
      customerContact: '',
      //弹窗显示与否的判定
      customerinfo: false,
      //列表展示
      //customerList: Array(15).fill(customer),
      customerList:[],
      editInfo: false,
      //下面是进一步的员工信息展示
      staffInfo: {
        name:'',
        tag:'',
        contact:'',
        score:''
      }

    }
  },
  mounted: function() {
    this.$axios.get("/customer/all").then(Response=> {
      this.customerList = Response.data
    })
  },
  methods:{
    //添加客户信息
    //客户种类到01的对应还没确定好
    addItem(){
      if(this.customerTag == "零售客户"){
        this.customerTag = false
      }else {
        this.customerTag = true
      }
      this.$axios.post('/customer/add',{customerName:this.customerName,customerTag: this.customerTag,customerContact:this.customerContact})
      .then(Response=>{
        if(Response.data.code === 200)
        {
          this.$message({
            message: '添加成功',
            type: 'success'
          }),
            this.$axios.get("/customer/all").then(Response=> {
              this.customerList = Response.data
            })
        }
        this.customerName=''
        this.customerTag=''
        this.customerContact=''
      })
    },
    //查看客户信息
    check(){
      this.$axios.get('/customer/get?customerId=' + this.$store.state.customer_id).then(Response=>{
          this.staffInfo.name = Response.data.name
        this.staffInfo.tag = Response.data.tag
        this.staffInfo.contact = Response.data.contact
        this.staffInfo.score = Response.data.score
      })
        this.customerinfo = true
    },
    handle (row, column, cell, event) {
      this.$store.commit('saveCustomer_id',row['id'])
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
