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
            Hello, <span id="salesman" v-text="bossName"></span>
            <el-button icon="el-icon-s-home" circle style="border: none;background: none" @click="$router.push({path: '/bossHomePage'})"></el-button>
            <el-button id="log-out-button" icon="el-icon-switch-button" @click="$router.push({path: '/login'})"></el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-row :gutter="20">
          <el-col :span="4">
            <el-input v-model="staffId" placeholder="员工编号"></el-input>
          </el-col>
          <el-col :span="4">
            <el-input v-model="staffName" placeholder="员工姓名"></el-input>
          </el-col>
          <el-col :span="4">
            <el-select v-model="staffTitle" placeholder="员工职位">
              <el-option value="销售员" ></el-option>
              <el-option value="仓库管理员"></el-option>
            </el-select>
          </el-col>
          <el-col :span="2">
            <el-button @click="addItem">添加</el-button>
          </el-col>
        </el-row>
        <br><br>
        <el-table :data="staffList" style="width: 100%" height="600px" :header-cell-style="{background:'#eef1f6',color:'#606266'}" @cell-click="handle">
          <el-table-column prop="index" label="序" width="76px"></el-table-column>
          <el-table-column prop="id" label="员工编号" width="300px"></el-table-column>
          <el-table-column prop="name" label="员工姓名" width="300px"></el-table-column>
          <el-table-column prop="title" label="职位" width="220px"></el-table-column>
          <el-table-column prop="staffInfo" label="员工信息" width="200px">
            <el-link @click="check">查看</el-link>
          </el-table-column>
          <el-table-column prop="operation" label="操作" width="200px">
            <el-link @click="fireStaff">解雇</el-link>
          </el-table-column>
        </el-table>
        <el-dialog title="员工信息" :visible.sync="infoVisible" width="40%">
          <div v-text="staffInfo.staffId" style="line-height: 40px; font-size: 20px"></div>
          <div v-text="staffInfo.staffName" style="line-height: 40px; font-size: 20px"></div>
          <div v-text="staffInfo.staffTitle" style="line-height: 40px; font-size: 20px"></div>
          <div v-text="staffInfo.staffContact" style="line-height: 40px; font-size: 20px"></div>
        </el-dialog>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'StaffList',
  data () {
    const staff = {
      index: '1',
      id: '1234',
      name: 'Sandy',
      title: '售货员'
    }
    return {
      staffInfo: {
        staffId: '',
        staffName: '',
        staffTitle: '',
        staffContact: ''
      },
      staffId:'',
      staffName: '',
      staffTitle: '',
      pageName: '员工列表',
      bossName: this.$store.state.staff_name,
      infoVisible: false,
      //staffList: Array(15).fill(staff),
      staffList:[]
    }
  },
  mounted: function() {
    this.$axios.get("/staff/all").then(Response=> {
      this.staffList = Response.data
    })
  },
  methods: {
    addItem(){
      this.$axios.get('/staff/add?staffId='+this.staffId+'&staffName='+this.staffName+'&staffTitle='+this.staffTitle).then(Response=>{
        if(Response.data.code === 200)
        {
          this.$message({
            message: '添加成功',
            type: 'success'
          }),
            this.$axios.get("/staff/all").then(Response=> {
              this.staffList = Response.data
            })

          this.staffId=''
          this.staffName=''
          this.staffTitle=''
        }
      })
    },
    fireStaff () {
      this.$axios.post('/staff/delete',{staffId:this.$store.state.opStaff_id}).then(Response=>{
        if(Response.data.code === 200)
        {
          this.$message({
            message: '员工已被解雇',
            type: 'success'
          }),
            this.$axios.get("/staff/all").then(Response=> {
              this.staffList = Response.data
            })
        }
      })
    },
    check(){
      this.$axios.get('/staff/get?staffId=' + this.$store.state.opStaff_id).then(Response=>{
        this.staffInfo = Response.data
      })
      this.infoVisible = true
    },
    handle (row, column, cell, event) {
      this.$store.commit('saveOpStaff_id',row['id'])
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
