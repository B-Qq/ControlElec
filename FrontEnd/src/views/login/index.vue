<template>
  <div class="login-container">
    <el-form ref="form" :rules="rules" :model="form" label-width="80px" class="login-form">
      <h2 class="login-title">散桩产家联调测试</h2>
      <el-form-item label="用户名" prop="userName">
        <el-input v-model="form.userName" type='text' placeholder='请输入账号'></el-input>
      </el-form-item>
      <el-form-item label="密码" prop='password'>
        <el-input v-model="form.password" type='password' placeholder='请输入密码'></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('form')">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { loginReq } from '@/apis/login'

export default {
  data() {
    return {
      form: {
        userName: '',
        password: ''
      },
      rules: {
        userName: [
          { required: true, message: '用户名不能为空', trigger: 'blur' },
          { min: 3, max: 10, message: '用户名3-10位', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '密码不能为空', trigger: 'blur' },
          { min: 3, max: 10, message: '密码3-10位', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          loginReq(this.form.userName, this.form.password).then((res) => {
            console.log(res.data)
            if (res.data['status'] === '0') {
              this.$message.error('用户名或密码错误请重新输入')
            } else {
              // 判断是否登录
              sessionStorage.username = this.userName
              sessionStorage.setItem('uuid', res.data['uuid'])
              this.$router.push('/main')
            }
          })
            .catch((err) => {
              this.$message.error('登录超时')
              console.error('faild', err)
            })
        } else {
          this.$message.error('用户名或密码不符合规则请重新输入')
          return false
        }
      })
    }
  }
}
</script>

<style acoped>
.login-form {
  width: 350px;
  margin: 160px auto; /* 上下间距160px，左右自动居中*/
  background-color: rgb(255, 255, 255, 0.8); /* 透明背景色 */
  padding: 30px;
  border-radius: 20px; /* 圆角 */
}

/* 背景 */
.login-container {
  /*position: absolute;*/
  width: 100%;
  height: 100%;
  background-color: rgb(29, 105, 105);
  /* background: url("../../assets/login.jpg"); */
}

/* 标题 */
.login-title {
  color: #303133;
  text-align: center;
}
</style>-->
