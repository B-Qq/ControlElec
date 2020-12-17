import request from '@/utils/request'

// 导出函数
export function loginReq(userName, password) {
  return request({
    url: '/logini',
    method: 'post',
    data: {
      userName,
      password
    }
  })
}
