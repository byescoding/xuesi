import request from '@/utils/request'

export default {
  // 过去嵌套列表
  getNestedTreeList(id) {
    return request({
      url: `/admin/college/chapter/nested-list/${id}`,
      method: 'get'
    })
  },
  removeById(id) {
    return request({
      url: `/admin/college/chapter/delete/${id}`,
      method: 'delete'
    })
  },

  save(chapter) {
    return request({
      url: '/admin/college/chapter/save',
      method: 'post',
      data: chapter
    })
  },

  getById(id) {
    return request({
      url: `/admin/college/chapter/get/${id}`,
      method: 'get'
    })
  },

  updateById(chapter) {
    return request({
      url: '/admin/college/chapter/update',
      method: 'put',
      data: chapter
    })
  }

}
