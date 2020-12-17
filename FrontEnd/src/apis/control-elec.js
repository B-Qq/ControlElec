import request from '@/utils/request'

// 导出函数
export function startElec(stakeNo, port) {
  return request({
    url: '/startElec',
    method: 'post',
    data: {
      stakeNo,
      port
    }
  })
}

// 导出函数
export function stopElec(orderId) {
  return request({
    url: '/stopElec',
    method: 'post',
    data: {
      orderId
    }
  })
}

// 导出函数
export function getDeviceList(uuid) {
  return request({
    url: '/getDeviceList',
    method: 'post',
    data: {
      uuid
    }
  })
}

// 导出函数
export function getOrderId(stakeNo, port) {
  return request({
    url: '/getOrderId',
    method: 'post',
    data: {
      stakeNo,
      port
    }
  })
}
