<template>
  <div class="main">
    <el-row>
    <el-col :span="22">
    <i class="el-icon-s-opportunity" :style="stakeStatus"></i>
    </el-col>
    <el-col :span="2">
       <el-button class="logout-button" @click="logout()" type="primary" round>退出登录</el-button>
    </el-col>
    </el-row>
    <el-row class="header">
    <el-col :span="24">
    <p>订单号: {{orderIdp}}</p>
    </el-col>
    </el-row>
    <el-form ref="start-form" :rules="rules" :model="form" label-width="80px" class="start-elec-form" :span="15">
      <!-- 桩号 -->
      <el-form-item label="桩号:" prop="stakeNo">
        <el-select v-model="form.stakeNo" placeholder="请选择">
            <el-option
            v-for="item in stakeNoOptions"
            :key="item.stakeNo"
            :label="item.label"
            :value="item.stakeNo">
            </el-option>
        </el-select>
      </el-form-item>
      <!-- 端口号 -->
      <el-form-item label="端口号:" prop="port">
        <el-select v-model="form.port" placeholder="请选择">
            <el-option
            v-for="item in portOptions"
            :key="item.port"
            :label="item.label"
            :value="item.port">
            </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button class="start-button" type="success" :loading="loadingStartBtn" @click="startElecForm('start-form')">申请用电</el-button>
        <el-button class="stop-button" type="danger" :loading="loadingStopBtn" @click="stopElecForm()">停止用电</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { startElec, stopElec, getDeviceList, getOrderId } from '@/apis/control-elec'

export default {
  created: function() {
    getDeviceList(sessionStorage.getItem('uuid')).then((res) => {
    //   console.log(res.data)
      // 遍历设备信息数组
      res.data['stakes'].filter((item, i) => {
        this.stakeNoOptions.push({
          stakeNo: res.data['stakes'][i]['stakeNo'],
          label: res.data['stakes'][i]['stakeName']
        })
        return item > res.data['stakes'].length
      })
      this.form.stakeNo = res.data['stakes'][0]['stakeNo']
    })
  },
  data() {
    return {
      //   桩号选择
      stakeNoOptions: [],
      //   端口号选择
      portOptions: [{
        port: '1',
        label: '端口1'
      }, {
        port: '2',
        label: '端口2'
      }],
      loadingStartBtn: false,
      loadingStopBtn: false,
      stakeStatus: 'color:lightgreen;',
      orderIdp: '',
      form: {
        stakeNo: '',
        port: '1'
      },
      rules: {
        stakeNo: [
          { required: true, message: '桩号不能为空', trigger: 'blur' },
          { min: 16, max: 16, message: '桩号12位', trigger: 'blur' }
        ],
        port: [
          { required: true, message: '端口号不能为空', trigger: 'blur' },
          { min: 1, max: 1, message: '端口1位', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    startElecForm(formName) {
      this.loadingStartBtn = true
      this.$refs[formName].validate(valid => {
        if (valid) {
          startElec(this.form.stakeNo, this.form.port).then((res) => {
            console.log('下供电:', this.form.stakeNo, ', ', this.form.port, ' ', res.data)
            if (res.data['status'] === '1') {
              this.stakeStatus = 'color:red;'
              this.orderIdp = res.data['orderId']
            }
            this.loadingStartBtn = false
            this.$alert(res.data['msg'], '用电结果', {
              confirmButtonText: '确定'
            })
            // console.log(res.data['msg'])
          })
            .catch((err) => {
              this.loadingStartBtn = false
              console.error('faild', err)
            })
        } else {
          this.loadingStartBtn = false
          this.$message.error('桩号不符合规则请重新输入')
          return false
        }
      })
    },
    stopElecForm() {
      this.loadingStopBtn = true
      getOrderId(this.form.stakeNo, this.form.port).then((res) => {
        console.log('下断电:', res.data)
        if (res.data['orderId'] === '') {
          this.loadingStopBtn = false
          this.$alert('未找到供电中的订单', '停止用电结果', {
            confirmButtonText: '确定'
          })
          return
        }
        stopElec(res.data['orderId']).then((res) => {
          this.loadingStopBtn = false
          console.log('1111', res.data)
          if (res.data['status'] === '1') {
            this.stakeStatus = 'color:lightgreen'
            this.$alert('停止用电成功', '停止用电结果', {
              confirmButtonText: '确定'
            })
          } else {
            this.$alert(res.data['msg'], '停止用电结果', {
              confirmButtonText: '确定'
            })
          }
        })
          .catch((err) => {
            this.loadingStopBtn = false
            console.error('faild', err)
          })
      })
        .catch((eerr) => {
          this.loadingStopBtn = false
          this.$alert('超时', '停止用电结果', {
            confirmButtonText: '确定'
          })
        })
    },
    logout() {
      this.$router.push('/login')
      sessionStorage.clear()
    }
  }
}
</script>

<style acoped>

.header {
  width: auto;
  margin: 20px 30px;
  padding: 0px 20px;
  font: 13px Small;
  background-color: rgb(255, 255, 255, 0.8); /* 透明背景色 */
  border-radius: 0px; /* 圆角 */
}

.start-elec-form {
  width: auto;
  margin: 20px 30px;
  padding: 20px;
  background-color: rgb(255, 255, 255, 0.8); /* 透明背景色 */
  border-radius: 0px; /* 圆角 */
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
}

.main {
  width: auto;
  margin: auto, auto;
  padding: 20px;
  background-color: rgb(255, 255, 255, 0.8); /* 透明背景色 */
  border-radius: 0px; /* 圆角 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)
}

/* 背景 */
.main-container {
  position: absolute;
  width: 100%;
  height: 100%;
  background: url("../../assets/login.jpg");
}

/* 标题 */
.main-title {
  color: #1f3f7e;
  text-align: center;

}

.start-button {
  color:aqua,
}
</style>-->
