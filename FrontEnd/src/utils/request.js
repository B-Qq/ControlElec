import axios from 'axios'
import qs from 'qs'

// axios.defaults.baseURL = 'http://127.0.0.1:5000'

const service = axios.create({
  // baseURL: 'http://127.0.0.1:9002',
  baseURL: 'http://49.7.60.22:18882',
  timeout: 65000,
  headers: {'Content-Type': 'application/x-www-form-urlencoded'},
  transformRequest: [function (data) {
    // console.log(data)
    return qs.stringify(data)
  }]
})

export default service
