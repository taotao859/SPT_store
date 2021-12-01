<template>
  <div id="background">
    <div id="loginContainer">
      <h2>登录系统</h2>
      <br><br>
      <input class="input" type="text" v-model="loginForm.username" placeholder="Username" />
      <br><br>
      <input class="input" type="password" v-model="loginForm.password" placeholder="Password" />
      <br><br>
      <button class="button" v-on:click="login">登录</button>
    </div>
  </div>
</template>

<script>

export default {
  name: 'Login',
  data () {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      responseResult: []
    }
  },
  methods: {
    login () {
      this.$axios
        .post('/login', {
          username: this.loginForm.username,
          password: this.loginForm.password
        })
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            this.$router.replace({path: '/saleHomePage'})
          }
        })
        .catch(failResponse => {
        })
    }
  }
}
</script>

<style>
  #background {
    background: url("../assets/login.jpg") no-repeat;
    height: 100%;
    width: 100%;
    background-size: cover;
    position: fixed;
  }
  #loginContainer {
    background-color: rgba(255, 255, 255, 0.7);
    border-radius: 8px;
    width: 400px;
    height: 305px;
    display: table-cell;
    vertical-align: middle;
    text-align: center;
    position: absolute;
    left: 50%;
    top: 40%;
    transform: translate(-50%, -50%);
  }
  .input {
    width: 350px;
    height: 40px;
    font-size: medium;
    border-radius: 5px;
    border: none;
  }
  .button {
    width: 350px;
    height: 40px;
    font-size: medium;
    border-radius: 5px;
    border: none;
    color: white;
    background-color: rgb(107, 164, 163);
  }
</style>
