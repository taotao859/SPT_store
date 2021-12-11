import request from 'request'
import axios from 'axios'

export function Login (staff) {
  return request({
    url: ('/login'),
    method: 'this.$axios.post',
    params: staff
  }
  )
}
