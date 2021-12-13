<template>
  <body id = "poster">
  <el-form class="login-container" label-position="left"
           label-width="0px">
    <h3 class="login_title">用户登录</h3>
    <el-form-item>
      <el-input type="text" v-model="loginForm.staffId"
                auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item>
      <el-input type="password" v-model="loginForm.staffPassword"
                auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <!-- 验证码 -->
    <el-form-item prop="vertify_code">
      <el-input type="text" v-model="loginForm.verifyCode" placeholder="验证码" prefix-icon="el-icon-key">
        <template slot="append">
          <img class="login-code" :src=code_url @click="getVertifyCode" title="看不清？点击切换"/>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item style="width: 100%">
      <el-button type="primary" style="width: 100%;background: #505458;border: none" v-on:click="login">登录</el-button>
    </el-form-item>
  </el-form>
  </body>
</template>

<script>


export default {
  name: 'Login',
  data () {
    return {
      loginForm: {
        staffId: '',
        staffPassword: '',
        verifyCode:''
      },
      staff: [],
      code_url:''
    }
  },
  created(){
    this.getVertifyCode ()
  },
  methods: {
    login () {
      console.log(this.$store.state)
      this.$axios.post('/login', {staffId: this.loginForm.staffId, staffPassword: this.loginForm.staffPassword, verifyCode:this.loginForm.verifyCode}).then(response => {
      //Login(this.loginForm).then(response => {
        if (response.data.code === 200) {
          this.$router.replace({path: '/Boss'})
          this.$axios.get('/staff/get?staffId='+this.loginForm.staffId).then(response => {
            this.staff = response.data
            this.$store.commit('saveStaff_id',response.data.staffId)
            this.$store.commit('saveStaff_name',response.data.staffName)
            var path = this.$route.query.redirect
            this.$router.replace({path: path === '/' || path === undefined ? '/login' : path})
          })
        }
        if (response.data.code === 201) {
          this.$router.replace({path: '/RepoHomePage'})
          this.$axios.get('/staff/get?staffId='+this.loginForm.staffId).then(response => {
            this.staff = response
            this.$store.commit('saveStaff_id',response.data.staffId)
            this.$store.commit('saveStaff_name',response.data.staffName)
            var path = this.$route.query.redirect
            this.$router.replace({path: path === '/' || path === undefined ? '/login' : path})
          })
        }
        if (response.data.code === 202) {
          this.$router.replace({path: '/salesperson'})
          this.$axios.get('/staff/get?staffId='+this.loginForm.staffId).then(response => {
            this.staff = response
            this.$store.commit('saveStaff_id',response.data.staffId)
            this.$store.commit('saveStaff_name',response.data.staffName)
            var path = this.$route.query.redirect
            this.$router.replace({path: path === '/' || path === undefined ? '/login' : path})
          })
        }
        if(response.data.code === 400 || response.data.code === 405)
        {
          this.$message({
            message: '登录失败：用户名或密码错误',
            type: 'error'
          })
        }
        if(response.data.code === 500)
        {
          this.$message({
            message: '登录失败，验证码错误',
            type: 'error'
          })
        }
      }).catch(failResponse => {
      })
    },
    // 获取验证码
    async getVertifyCode () {
      const { data: res } = await this.$http.get('/getVerifyCodeImage/?time=' + Math.random())
      // console.log(res)
      this.code_url = res // 验证码图片渲染
    },
  },
  mounted: function() {
    this.$store.commit('saveStaff_id',null)
    this.$store.commit('saveStaff_name',null)
  }
}

</script>

<style>
#poster {
  background: url("../assets/Login.jpg") no-repeat center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
}
body{
  margin: 0px;
}

.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 90px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.login_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}

</style>
