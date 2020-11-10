import request from '@/utils/request'

export default {
  removeById(id) {
    return request({
      url: `/admin/college/video/remove/${id}`,
      method: 'delete'
    })
  },

  save(chapter) {
    return request({
      url: '/admin/college/video/save',
      method: 'post',
      data: chapter
    })
  },

  getById(id) {
    return request({
      url: `/admin/college/video/get/${id}`,
      method: 'get'
    })
  },

  updateById(chapter) {
    return request({
      url: '/admin/college/video/update',
      method: 'put',
      data: chapter
    })
  }

}
