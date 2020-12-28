<template>
  <div class="main">
    <el-row>
      <el-col :span="22">
        <p>用户: {{user}}</p>
      </el-col>
      <el-col :span="2">
        <el-button @click="backMain()" type="primary">返回首页</el-button>
      </el-col>
    </el-row>
    <hr/>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user: sessionStorage.getItem('user')
    }
  },
  methods: {
    backMain() {
      console.log('uuid', sessionStorage.getItem('uuid'))
      this.$router.push('/main')
    }
  }
}
</script>
