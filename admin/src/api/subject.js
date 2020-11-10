import request from '@/utils/request'

export default {
  getNestedList() {
    return request({
      url: '/admin/college/subject/nested-list',
      method: 'get'
    })
  }
}
