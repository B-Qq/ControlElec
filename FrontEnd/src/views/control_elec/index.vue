<template>
  <div class="main">
    <el-row>
    <el-col :span="20">
    <p>用户: {{user}}</p>
    </el-col>
    <el-col :span="2">
       <el-button @click="offlineRecord()" type="success" round>设备离线记录</el-button>
    </el-col>
    <el-col :span="2">
      <el-button class="logout-button" @click="logout()" type="primary">退出登录</el-button>
    </el-col>
    </el-row>
    <hr/>
    <div class="elec-form">
    <el-row class="order">
    <el-col :span="24">
    <p :style="orderColor">订单号: {{orderIdp}}</p>
    </el-col>
    </el-row>
    <el-form ref="start-form" :rules="rules" :model="form" label-width="80px" :span="15">
      <!-- 桩号 -->
      <el-form-item label="桩号:" prop="stakeNo">
        <el-select v-model="form.stakeNo" placeholder="请选择" @change="getDeviceMsg">
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
        <el-select v-model="form.port" placeholder="请选择" @change="getDeviceMsg">
            <el-option
            v-for="item in portOptions"
            :key="item.port"
            :label="item.label"
            :value="item.port">
            </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="success" :loading="loadingStartBtn" @click="startElecForm('start-form')">申请用电</el-button>
        <el-button type="danger" :loading="loadingStopBtn" @click="stopElecForm()">停止用电</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="info" :loading="getSignalBtn" @click="getSignal" style="text-align: center;" plain>获取设备遥信</el-button>
        <el-button type="info" :loading="getMeterBtn" @click="getMeter" style="text-align: center;" plain>获取设备遥测</el-button>
        <el-button type="info" :loading="getElecBtn" @click="getElec" style="text-align: center;" plain>获取设备计量</el-button>
        <el-button type="info" :loading="getRenBtn" @click="getRen" style="text-align: center;" plain>获取认证结果</el-button>
      </el-form-item>
    </el-form>
    </div>
    <hr/>
    <div clas="ren-div">
      <em style="color: #31a188;font-size: 20px;">安全认证结果: </em>
      <label style="color:red;font-size: 25px;">{{renStatus}}</label>
    </div>
    <hr/>
    <div class="singal-div">
      <label style="color:red;">
        "解析数据说明 - "
      </label>
      <em style="color: #2db0c5;font-size: 20px;">遥信</em>
      <ul>
        <el-col :span="12">
        <li>
          <label>采集时间 : </label>
          <label :style="signalstyle">{{time}}</label>
        </li>
        <li>
          <label>工作状态 : </label>
          <label :style="signalstyle">{{work_state}}</label>
        </li>
        <li>
          <label>连接确认开关状态 : </label>
          <label :style="signalstyle">{{switch_state}}</label>
        </li>
        <li>
          <label>输出继电器状态 : </label>
          <label :style="signalstyle">{{relay_state}}</label>
        </li>
        <li>
          <label>交流输出过压告警 : </label>
          <label :style="signalstyle">{{ov_warn}}</label>
        </li>
        <li>
          <label>交流输出欠压告警 : </label>
          <label :style="signalstyle">{{uv_warn}}</label>
        </li>
        <li>
          <label>交流输出缺相告警 : </label>
          <label :style="signalstyle">{{lp_warn}}</label>
        </li>
        <li>
          <label>防雷器故障 : </label>
          <label :style="signalstyle">{{spd_error}}</label>
        </li>
        </el-col>
        <li>
          <label>急停故障 : </label>
          <label :style="signalstyle">{{scram_error}}</label>
        </li>
        <li>
          <label>漏电保护 : </label>
          <label :style="signalstyle">{{leakage_elec}}</label>
        </li>
        <li>
          <label>电表通讯故障 : </label>
          <label :style="signalstyle">{{meter_error}}</label>
        </li>
        <li>
          <label>门禁故障 : </label>
          <label :style="signalstyle">{{access_error}}</label>
        </li>
        <li>
          <label>过流故障 : </label>
          <label :style="signalstyle">{{oc_error}}</label>
        </li>
        <li>
          <label>水浸故障 : </label>
          <label :style="signalstyle">{{water_error}}</label>
        </li>
        <li>
          <label>倾倒故障 : </label>
          <label :style="signalstyle">{{dump_error}}</label>
        </li>
      </ul>
    </div>
    <br/>
    <hr/>
    <el-col :span="12">
      <div class="meter-div">
      <label style="color:red;">
        "解析数据说明 - "
        <em style="color: #2db0c5;font-size: 20px;">遥测</em>
      </label>
      <ul>
        <li>
          <label>采集时间 : </label>
          <label :style="signalstyle">{{meter_time}}</label>
        </li>
       <li>
         <label>A相电压(V) : </label>
         <label :style="signalstyle">{{ua}}</label>
       </li>
        <li>
          <label>B相电压(V) : </label>
          <label :style="signalstyle">{{ub}}</label>
        </li>
        <li>
          <label>C相电压(V) : </label>
          <label :style="signalstyle">{{uc}}</label>
        </li>
        <li>
          <label>A相电流(A) : </label>
          <label :style="signalstyle">{{ia}}</label>
        </li>
        <li>
          <label>B相电流(A) : </label>
          <label :style="signalstyle">{{ib}}</label>
        </li>
        <li>
          <label>C相电流(A) : </label>
          <label :style="signalstyle">{{ic}}</label>
        </li>
        <li>
          <label>功率(kW) : </label>
          <label :style="signalstyle">{{pp}}</label>
        </li>
      </ul>
    </div>
   </el-col>
    <el-col :span="12">
      <div class="meter-div">
        <label style="color:red;">
          "解析数据说明 - "
          <em style="color: #2db0c5;font-size: 20px;">计量</em>
        </label>
        <ul>
          <li>
            <label>采集时间 : </label>
            <label :style="signalstyle">{{elec_time}}</label>
          </li>
          <li>
            <label>总电量(kWh) : </label>
            <label :style="signalstyle">{{total_elec}}</label>
          </li>
          <li>
            <label>尖电量(kWh) : </label>
            <label :style="signalstyle">{{sport_elec}}</label>
          </li>
          <li>
            <label>峰电量(kWh) : </label>
            <label :style="signalstyle">{{peak_elec}}</label>
          </li>
          <li>
            <label>平电量(kWh) : </label>
            <label :style="signalstyle">{{flat_elec}}</label>
          </li>
          <li>
            <label>谷电量(kWh) : </label>
            <label :style="signalstyle">{{valley_elec}}</label>
          </li>
        </ul>
        </div>
    </el-col>
  </div>
</template>

<script>
import { startElec, stopElec, getDeviceList, getOrderId, getWorkState, getDeviceSignal, getDeviceMeter, getDeviceElec, queryCertifyStatus, getWsPath } from '@/apis/control-elec'

export default {
  created: function() {
    getDeviceList(sessionStorage.getItem('uuid')).then((res) => {
      // 遍历设备信息数组
      res.data['stakes'].filter((item, i) => {
        this.stakeNoOptions.push({
          stakeNo: res.data['stakes'][i]['stakeNo'],
          label: res.data['stakes'][i]['stakeName']
        })
        return item > res.data['stakes'].length
      })
      // 选择默认桩号
      this.form.stakeNo = res.data['stakes'][0]['stakeNo']
      // 获取订单号
      getOrderId(this.form.stakeNo, this.form.port).then((res) => {
        this.orderIdp = res.data['orderId']
      })
      // 获取对应设备状态
      getWorkState(this.form.stakeNo, this.form.port).then((res) => {
        if (res.data['workState'] === '3') {
          this.orderColor = 'color:red;'
        } else if (res.data['workState'] === '1') {
          this.orderColor = 'color:orange;'
        } else {
          this.orderColor = 'color:green;'
        }
        console.log('workState:', res.data['workState'])
      })
      this.getSignal()
      this.getElec()
      this.getMeter()
      this.getRen()
      this.initWebSocket()
    })
  },
  mounted() {
    const timer = setInterval(() => {
      this.ws.send('heart')
    }, 5000)
    this.$once('hook:beforeDestroy', () => {
      clearInterval(timer)
    })
  },
  data() {
    return {
      ws: {},
      user: sessionStorage.getItem('user'),
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
      getRenBtn: false,
      renStatus: '',
      elec_time: '',
      total_elec: '',
      sport_elec: '',
      peak_elec: '',
      flat_elec: '',
      valley_elec: '',
      meter_time: '',
      ua: '',
      ub: '',
      uc: '',
      ia: '',
      ib: '',
      ic: '',
      pp: '',
      signalstyle: 'color:#2db0c5;font-size: 20px;',
      time: '',
      work_state: '',
      switch_state: '',
      relay_state: '',
      ov_warn: '',
      uv_warn: '',
      lp_warn: '',
      spd_error: '',
      scram_error: '',
      leakage_elec: '',
      meter_error: '',
      access_error: '',
      oc_error: '',
      water_error: '',
      dump_error: '',
      loadingStartBtn: false,
      loadingStopBtn: false,
      getSignalBtn: false,
      getMeterBtn: false,
      getElecBtn: false,
      orderColor: 'color:black;',
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
              this.orderColor = 'color:red;'
              this.orderIdp = res.data['orderId']
              this.$alert(res.data['msg'], '用电结果', {
                confirmButtonText: '确定',
                type: 'success'
              })
            } else {
              this.$alert(res.data['msg'], '用电结果', {
                confirmButtonText: '确定',
                type: 'error'
              })
            }
            this.loadingStartBtn = false
            // console.log(res.data['msg'])
          })
            .catch((err) => {
              this.loadingStartBtn = false
              console.error('faild', err)
              this.$alert(err, '用电结果', {
                confirmButtonText: '确定',
                type: 'error'
              })
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
            confirmButtonText: '确定',
            type: 'error'
          })
          return
        }
        stopElec(res.data['orderId']).then((res) => {
          this.loadingStopBtn = false
          console.log('1111', res.data)
          if (res.data['status'] === '1') {
            this.orderColor = 'color:green;'
            this.$alert('停止用电成功', '停止用电结果', {
              confirmButtonText: '确定',
              type: 'success'
            })
          } else {
            this.$alert(res.data['msg'], '停止用电结果', {
              confirmButtonText: '确定',
              type: 'error'
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
            confirmButtonText: '确定',
            type: 'error'
          })
        })
    },
    logout() {
      this.$router.push('/login')
      sessionStorage.clear()
      this.ws.close()
    },
    offlineRecord() {
      this.$router.push('/offline')
      this.ws.close()
    },
    getDeviceMsg() {
      // 获取订单号
      getOrderId(this.form.stakeNo, this.form.port).then((res) => {
        this.orderIdp = res.data['orderId']
      })
      // 获取对应设备状态
      getWorkState(this.form.stakeNo, this.form.port).then((res) => {
        if (res.data['workState'] === '3') {
          this.orderColor = 'color:red;'
        } else if (res.data['workState'] === '1') {
          this.orderColor = 'color:orange;'
        } else {
          this.orderColor = 'color:green;'
        }
        console.log(res.data['workState'])
      })
      console.log(this.form.stakeNo, this.form.port)
      this.getSignal()
      this.getMeter()
      this.getElec()
      this.getRen()
    },
    getSignal() {
      this.getSignalBtn = true
      getDeviceSignal(this.form.stakeNo, this.form.port).then((res) => {
        this.getSignalBtn = false
        if (res.data['status'] === '0') {
          this.clearSignal()
          this.$message.error('未获取到遥信数据')
        } else {
          let date = res.data['time'].substring(0, 4) + '-' + res.data['time'].substring(4, 6) + '-' + res.data['time'].substring(6, 8)
          let time = res.data['time'].substring(8, 10) + ':' + res.data['time'].substring(10, 12) + ':' + res.data['time'].substring(12, 14)
          this.time = date + ' ' + time
          if (res.data['workstate'] === '2') {
            this.work_state = res.data['workstate'] + ' (空闲)'
          } else if (res.data['workstate'] === '3') {
            this.work_state = res.data['workstate'] + ' (工作中)'
          } else if (res.data['workstate'] === '5') {
            this.work_state = res.data['workstate'] + ' (完成)'
          } else if (res.data['workstate'] === '1') {
            this.work_state = res.data['workstate'] + ' (故障)'
          }

          this.switch_state = res.data['switchstate']
          this.relay_state = res.data['relaystate']
          this.ov_warn = res.data['ovwarn']
          this.uv_warn = res.data['uvwarn']
          this.lp_warn = res.data['lpwarn']
          this.spd_error = res.data['spderror']
          this.scram_error = res.data['scramerror']
          this.leakage_elec = res.data['leakageelec']
          this.meter_error = res.data['metererror']
          this.access_error = res.data['accesserror']
          this.oc_error = res.data['ocerror']
          this.water_error = res.data['watererror']
          this.dump_error = res.data['dumperror']
          console.log(res.data)
        }
      })
        .catch(() => {
          this.clearSignal()
          this.getSignalBtn = false
        })
    },
    getMeter() {
      this.getMeterBtn = true
      getDeviceMeter(this.form.stakeNo, this.form.port).then((res) => {
        this.getMeterBtn = false
        if (res.data['status'] === '0') {
          this.clearMeter()
          this.$message.error('未获取到遥测数据')
        } else {
          let date = res.data['time'].substring(0, 4) + '-' + res.data['time'].substring(4, 6) + '-' + res.data['time'].substring(6, 8)
          let time = res.data['time'].substring(8, 10) + ':' + res.data['time'].substring(10, 12) + ':' + res.data['time'].substring(12, 14)
          this.meter_time = date + ' ' + time
          this.ua = res.data['ua']
          this.ub = res.data['ub']
          this.uc = res.data['uc']
          this.ia = res.data['ia']
          this.ib = res.data['ib']
          this.ic = res.data['ic']
          this.pp = res.data['pp']
          console.log(res.data)
        }
      })
        .catch(() => {
          this.clearMeter()
          this.getMeterBtn = false
        })
    },
    getElec() {
      this.getElecBtn = true
      getDeviceElec(this.form.stakeNo, this.form.port).then((res) => {
        this.getElecBtn = false
        if (res.data['status'] === '0') {
          this.clearELec()
          this.$message.error('未获取到计量数据')
        } else {
          let date = res.data['time'].substring(0, 4) + '-' + res.data['time'].substring(4, 6) + '-' + res.data['time'].substring(6, 8)
          let time = res.data['time'].substring(8, 10) + ':' + res.data['time'].substring(10, 12) + ':' + res.data['time'].substring(12, 14)
          this.elec_time = date + ' ' + time
          this.total_elec = res.data['totalelec']
          this.sport_elec = res.data['sportelec']
          this.peak_elec = res.data['peakelec']
          this.flat_elec = res.data['flatelec']
          this.valley_elec = res.data['valleyelec']
          console.log(res.data)
        }
      })
        .catch(() => {
          this.clearELec()
          this.getElecBtn = false
        })
    },
    initWebSocket() {
      if (typeof (WebSocket) === 'undefined') {
        this.$alert('不支持websocket', '不支持websocket', {
          confirmButtonText: '确定',
          type: 'error'
        })
      }
      // 实例化socket，这里的实例化直接赋值给this.ws是为了后面可以在其它的函数中也能调用websocket方法，例如：this.ws.close(); 完成通信后关闭WebSocket连接
      this.ws = new WebSocket(getWsPath())

      // 监听是否连接成功
      this.ws.onopen = () => {
        console.log('ws连接状态：' + this.ws.readyState)
      }

      // 接听服务器发回的信息并处理展示
      this.ws.onmessage = (data) => {
        console.log('socket recvie:', data['data'])
        this.$alert(data['data'], '异常原因停电', {
          confirmButtonText: '确定',
          type: 'warning'
        })
      }

      // 监听连接关闭事件
      this.ws.onclose = () => {
        // 监听整个过程中websocket的状态
        console.log('ws连接状态：' + this.ws.readyState)
      }

      // 监听并处理error事件
      this.ws.onerror = function(error) {
        console.log('websocket 连接错误:', error)
      }
    },
    getRen() {
      this.getRenBtn = true
      this.renStatus = ''
      queryCertifyStatus(this.form.stakeNo, this.form.port).then((res) => {
        this.getRenBtn = false
        this.renStatus = res.data['name']
        console.log('认证结果:', res)
      })
        .catch(() => {
          this.renStatus = ''
          this.getRenBtn = false
        })
    },
    clearMeter() {
      this.ua = ''
      this.ub = ''
      this.uc = ''
      this.ia = ''
      this.ib = ''
      this.ic = ''
      this.pp = ''
      this.meter_time = ''
    },
    clearSignal() {
      this.time = ''
      this.work_state = ''
      this.switch_state = ''
      this.relay_state = ''
      this.ov_warn = ''
      this.uv_warn = ''
      this.lp_warn = ''
      this.spd_error = ''
      this.scram_error = ''
      this.leakage_elec = ''
      this.meter_error = ''
      this.access_error = ''
      this.oc_error = ''
      this.water_error = ''
      this.dump_error = ''
    },
    clearELec() {
      this.elec_time = ''
      this.total_elec = ''
      this.sport_elec = ''
      this.peak_elec = ''
      this.flat_elec = ''
      this.valley_elec = ''
    }
  }
}
</script>

<style acoped>

.elec-form {
  width: auto;
  margin: 0px 30px;
  padding: 0px;
  background-color: rgb(255, 255, 255, 0.8); /* 透明背景色 */
  border-radius: 0px; /* 圆角 */
  text-align: center;
  /* box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1) */
}

.main {
  width: auto;
  margin: auto, auto;
  padding: 20px;
  background-color: rgb(255, 255, 255, 0.8); /* 透明背景色 */
  border-radius: 0px; /* 圆角 */
  /* box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04); */
  margin-top: 5px;
}

.order {
  margin: 20px 30px;
  padding: 5px 5px;
  font: 13px Small;
}
</style>
