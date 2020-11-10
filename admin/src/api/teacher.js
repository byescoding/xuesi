import request from '@/utils/request'

export default {
  // 教师列表信息获取
  list() {
    return request({
      url: '/admin/college/teacher/list',
      method: 'get'
    })
  },

  // 分页查询  和  根据条件的分页查询
  pageList(page, limit, searchObj) {
    return request({
      url: `/admin/college/teacher/list/${page}/${limit}`,
      method: 'get',
      params: searchObj
    })
  },

  // 根据id进行删除
  removeById(id) {
    return request({
      url: `/admin/college/teacher/delete/${id}`,
      method: 'delete'
    })
  },

  // 根据id进行批量删除
  batchRemove(idList) {
    return request({
      url: '/admin/college/teacher/batch-remove',
      method: 'post',
      data: idList
    })
  },

  // 添加教师
  save(teacher) {
    return request({
      url: '/admin/college/teacher/save',
      method: 'post',
      data: teacher
    })
  },

  // 通过id获取教师
  getById(id) {
    return request({
      url: `/admin/college/teacher/get/${id}`,
      method: 'get'
    })
  },

  // 通过id 更新
  updateById(teacher) {
    return request({
      url: '/admin/college/teacher/update',
      method: 'post',
      data: teacher
    })
  },
  // 最左侧匹配原则
  selectNameListByKey(key) {
    return request({
      url: `/admin/college/teacher/list/name/${key}`,
      method: 'get'
    })
  }

}
