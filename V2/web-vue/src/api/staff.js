import request from 'request'

export function staffGet (staffId) {
  return request({
    url: '/staff/get',
    method: 'this.$axios.post',
    params: {staffId}
  }
  )
}

export function staffDelete (staffId) {
  return request({
    url: '/staff/delete',
    method: 'this.$axios.delete',
    params: {staffId}
  }
  )
}

export function staffSearch (staffId) {
  return request({
    url: '/staff/search',
    method: 'this.$axios.post',
    params: {staffId}
  }
  )
}
