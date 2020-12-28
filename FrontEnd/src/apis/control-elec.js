import request from '@/utils/request'

// 下供电指令
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

// 下断电指令
export function stopElec(orderId) {
  return request({
    url: '/stopElec',
    method: 'post',
    data: {
      orderId
    }
  })
}

// 获取设备列表
export function getDeviceList(uuid) {
  return request({
    url: '/getDeviceList',
    method: 'post',
    data: {
      uuid
    }
  })
}

// 获取订单号
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

// 获取设备工作状态
export function getWorkState(stakeNo, port) {
  return request({
    url: '/getWorkState',
    method: 'post',
    data: {
      stakeNo,
      port
    }
  })
}

// 获取设备遥信
export function getDeviceSignal(stakeNo, port) {
  return request({
    url: '/getSignal',
    method: 'post',
    data: {
      stakeNo,
      port
    }
  })
}

// 获取设备遥测
export function getDeviceMeter(stakeNo, port) {
  return request({
    url: '/getMeter',
    method: 'post',
    data: {
      stakeNo,
      port
    }
  })
}

// 获取设备电量
export function getDeviceElec(stakeNo, port) {
  return request({
    url: '/getElec',
    method: 'post',
    data: {
      stakeNo,
      port
    }
  })
}

// 获取设备认证状态
export function queryCertifyStatus(stakeNo, port) {
  return request({
    url: '/queryCertifyStatus',
    method: 'post',
    data: {
      stakeNo,
      port
    }
  })
}
