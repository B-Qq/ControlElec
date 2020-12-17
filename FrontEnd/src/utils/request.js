import axios from 'axios'
import qs from 'qs'

// axios.defaults.baseURL = 'http://127.0.0.1:5000'

const service = axios.create({
  baseURL: 'http://127.0.0.1:8084',
  // baseURL: 'http://203.93.121.134:18882',
  timeout: 65000,
  headers: {'Content-Type': 'application/x-www-form-urlencoded'},
  transformRequest: [function (data) {
    // console.log(data)
    return qs.stringify(data)
  }]
})

export default service
