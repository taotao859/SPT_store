import request from 'request'

export function Init (staffId, repositoryId, customerId) {
  return request({
    url: '/api/retail/init',
    method: 'this.$axios.post',
    params: {staffId, repositoryId, customerId}
  }
  )
}

export function addOrderitem (productId, productQuantity, orders) {
  return request({
    url: '/api/retail/add',
    method: 'this.$axios.post',
    params: {productId, productQuantity, orders}
  }
  )
}

export function payOrder (orders) {
  return request({
    url: '/api/retail/pay',
    method: 'this.$axios.post',
    params: {orders}
  })
}

export function deleteOrderItem (productId, productQuantity, orders) {
  return request({
    url: '/api/retail/delete',
    method: 'this.$axios.post',
    params: {orders}
  })
}

export function cancelOrder (orders) {
  return request({
    url: 'this.$axios./api/retail/cancel',
    method: 'this.$axios.post',
    params: {orders}
  })
}

export function findAllRetail () {
  return request({
    url: '/api/retail/all',
    method: 'this.$axios.put',
    params: null
  })
}
