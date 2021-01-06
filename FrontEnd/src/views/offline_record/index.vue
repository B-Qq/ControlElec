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
      <span>桩号:</span>
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
      <span style="padding-left: 10px">告警:</span>
        <el-select v-model="warn" placeholder="请选择">
          <el-option
            v-for="item in warnOptions"
            :key="item.warn"
            :label="item.label"
            :value="item.warn">
          </el-option>
        </el-select>
      <el-button type="success" :loading="SearchBtn" @click="SearchWarn" style="margin-left: 10px;">搜索</el-button>
      <el-button type="info" @click="resetSearch" style="margin-left: 10px;">重置</el-button>
    </div>
    <el-table style="width: 100%;margin-bottom: 15px" :data="WarnList.slice((currentPage-1)*pagesize,currentPage*pagesize)" align="center">
      <el-table-column type="index" width="50" align="center">
      </el-table-column>
      <el-table-column label="设备编号" prop="stakeName" width="180" align="center">
      </el-table-column>
      <el-table-column label="端口号" prop="port" width="180" align="center">
      </el-table-column>
      <el-table-column label="告警状态" prop="status" width="180" :formatter="statusFormat" align="center">
      </el-table-column>
      <el-table-column label="告警开始时间" prop="warnStartTime" width="200" align="center">
      </el-table-column>
      <el-table-column label="告警结束时间" prop="warnEndTime" width="200" :formatter="warnEndTimeFormat" align="center">
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[5, 10, 20, 40]"
      :page-size="pagesize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="WarnList.length" align="center">
    </el-pagination>
  </div>
  </div>
</template>

<script>
import { getOnlineWarn, getWsPath, getDeviceList } from '@/apis/control-elec'
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
      warnOptions: [{
        warn: '',
        label: '全部'
      }, {
        warn: '0',
        label: '告警产生'
      }, {
        warn: '1',
        label: '告警恢复'
      }],
      user: sessionStorage.getItem('user'),
      currentPage: 1, // 初始页
      pagesize: 10, // 每页的数据
      WarnList: [],
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
      getOnlineWarn(uuid, this.selectDate, this.stakeNo, this.warn).then((res) => {
        this.WarnList = res.data
        this.SearchBtn = false
        console.log(this.WarnList)
      })
        .catch(() => {
          this.SearchBtn = false
        })
    },
    statusFormat(row, column) {
      if (row.status === '1') {
        return '告警恢复'
      } else {
        return '告警产生'
      }
    },
    warnEndTimeFormat(row, column) {
      if (row.warnEndTime === null) {
        return '-'
      } else {
        return row.warnEndTime
      }
    },
    getDate() {
      var aData = new Date()
      return aData.getFullYear() + '-' + (aData.getMonth() + 1) + '-' + aData.getDate()
    },
    SearchWarn() {
      console.log('date:', this.selectDate, 'warn:', this.warn, 'stakeNo:', this.stakeNo)
      this.handleUserList()
    },
    resetSearch() {
      this.stakeNo = ''
      this.warn = ''
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
