import request from '@/utils/request'

export default{
  saveCourseInfo(courseInfo) {
    return request({
      url: '/admin/college/course/save-course-info',
      method: 'post',
      data: courseInfo
    })
  },
  getCourseInfoById(id) {
    return request({
      url: `/admin/college/course/course-info/${id}`,
      method: 'get'
    })
  },
  updateCourseInfoById(courseInfo) {
    return request({
      url: '/admin/college/course/update-course-info',
      method: 'put',
      data: courseInfo
    })
  },
  // 获取分页数据
  getPageList(page, limit, searchObj) {
    return request({
      url: `/admin/college/course/list/${page}/${limit}`,
      method: 'get',
      params: searchObj
    })
  },
  // 删除课程
  removeCourseById(id) {
    return request({
      url: `/admin/college/course/delete/${id}`,
      method: 'delete'
    })
  },

  // 根据id进行获取发布课堂信息
  getCoursePublishVoById(id) {
    return request({
      url: `/admin/college/course/course-publish/${id}`,
      method: 'get'
    })
  },

  // 根据课堂Id修改发布状态
  publishCourseById(id) {
    return request({
      url: `/admin/college/course/publish/${id}`,
      method: 'put'
    })
  }

}
