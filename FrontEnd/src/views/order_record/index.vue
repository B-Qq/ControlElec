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
  <div class="cantainer">
    <div class="block">
      <span style="padding-left: 10px">订单编号:</span>
      <el-input style="width: 300px" v-model="orderId" placeholder="请输入订单号"></el-input>
      <span style="padding-left: 10px">桩号:</span>
      <el-select v-model="stakeNo" placeholder="请选择">
        <el-option
          v-for="item in stakeNoOptions"
          :key="item.stakeNo"
          :label="item.label"
          :value="item.stakeNo">
        </el-option>
      </el-select>
      <span style="padding-left: 10px">日期:</span>
      <el-date-picker
        v-model="selectDate"
        type="date"
        placeholder="选择日期"
        value-format="yyyy-MM-dd"
      >
      </el-date-picker>
      <el-button type="success" :loading="SearchBtn" @click="SearchOrders" style="margin-left: 10px;">搜索</el-button>
      <el-button type="info" @click="resetSearch" style="margin-left: 10px;">重置</el-button>
    </div>
    <el-table style="width: 100%;margin-bottom: 15px" :data="OrderList.slice((currentPage-1)*pagesize,currentPage*pagesize)" align="center">
      <el-table-column type="index" width="50" align="center"></el-table-column>
      <el-table-column label="订单编号" prop="orderId" width="280" align="center"></el-table-column>
      <el-table-column label="设备编号" prop="stakeName" width="150" align="center"></el-table-column>
      <el-table-column label="端口号" prop="chargePort" width="130" align="center"></el-table-column>
      <el-table-column label="下单时间" prop="createTime" width="180" align="center"></el-table-column>
      <el-table-column label="开始时间" prop="startTime" width="180" :formatter="startTimeformat" align="center"></el-table-column>
      <el-table-column label="结束时间" prop="endTime" width="180" :formatter="endTimeformat" align="center"></el-table-column>
      <el-table-column label="订单状态" prop="orderStatus" width="130" :formatter="orderStatusformat" align="center"></el-table-column>
      <el-table-column label="停电原因" prop="stopElecReason" width="150" :formatter="stopElecReasonformat" align="center"></el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[5, 10, 20, 40]"
      :page-size="pagesize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="OrderList.length" align="center">
    </el-pagination>
  </div>
  </div>
</template>

<script>
import { getOrders, getWsPath, getDeviceList } from '@/apis/control-elec'
export default {
  created() {
    this.selectDate = this.getDate()
    this.handleUserList()
    this.initWebSocket()
    getDeviceList(sessionStorage.getItem('uuid')).then((res) => {
      // 遍历设备信息数组
      res.data['stakes'].filter((item, i) => {
        this.stakeNoOptions.push({
          stakeNo: res.data['stakes'][i]['stakeNo'],
          label: res.data['stakes'][i]['stakeName']
        })
        return item > res.data['stakes'].length
      })
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
      SearchBtn: false,
      stakeNoOptions: [{stakeNo: '', label: '全部'}],
      orderId: '',
      user: sessionStorage.getItem('user'),
      currentPage: 1, // 初始页
      pagesize: 10, // 每页的数据
      OrderList: [],
      selectDate: '',
      ws: {},
      warn: '',
      stakeNo: ''
    }
  },
  methods: {
    backMain() {
      this.$router.push('/main')
      this.ws.close()
    },
    handleSizeChange(size) {
      this.pagesize = size
      console.log(this.pagesize)
    },
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage
      console.log(this.currentPage)
    },
    handleUserList() {
      var uuid = sessionStorage.getItem('uuid')
      if (uuid === 'bcb73132-3b71-11eb-ab4e-000c29a9186e') {
        uuid = null
      }
      this.SearchBtn = true
      getOrders(uuid, this.selectDate, this.stakeNo, this.orderId).then((res) => {
        this.OrderList = res.data
        this.SearchBtn = false
        console.log(this.OrderList)
      })
        .catch(() => {
          this.SearchBtn = false
        })
    },
    startTimeformat(row, column) {
      if (row.startTime === null) {
        return '-'
      } else {
        return row.startTime
      }
    },
    endTimeformat(row, column) {
      if (row.endTime === null) {
        return '-'
      } else {
        return row.endTime
      }
    },
    orderStatusformat(row, column) {
      if (row.orderStatus === '1') {
        return '开始'
      } else if (row.orderStatus === '2') {
        return '供电中'
      } else if (row.orderStatus === '3') {
        return '结束'
      } else {
        return '-'
      }
    },
    stopElecReasonformat(row, column) {
      if (row.stopElecReason === null) {
        return '-'
      } else {
        return row.stopElecReason
      }
    },
    getDate() {
      var aData = new Date()
      return aData.getFullYear() + '-' + (aData.getMonth() + 1) + '-' + aData.getDate()
    },
    SearchOrders() {
      console.log('date:', this.selectDate, 'orderId:', this.orderId, 'stakeNo:', this.stakeNo)
      this.handleUserList()
    },
    resetSearch() {
      this.stakeNo = ''
      this.orderId = ''
      this.selectDate = this.getDate()
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
        // 连接成功则发送一个数据
        // this.ws.send(sessionStorage.getItem('user'))
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
    }
  }
}
</script>

<style>

.main {
  width: auto;
  margin: auto, auto;
  padding: 20px;
  background-color: rgb(255, 255, 255, 0.8); /* 透明背景色 */
  border-radius: 0px; /* 圆角 */
  margin-top: 5px;
}

.cantainer {
  width: auto;
  margin: auto, auto;
  padding-bottom: 50px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
}

.block {
  text-align: center;
  margin-bottom: 30px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  padding: 20px;
}

</style>
