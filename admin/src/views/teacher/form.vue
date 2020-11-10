<template>
  <div class="app-container">
    <!-- 输入表单 -->
    <el-form label-width="80px" >
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name" />
      </el-form-item>
      <el-form-item label="入驻时间">
        <el-date-picker v-model="teacher.joinDate" value-format="yyyy-MM-dd" />
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" :min="0"/>
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
            -->
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro"/>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career" :rows="10" type="textarea"/>
      </el-form-item>
      <!-- 讲师头像：TODO -->
      <el-form-item label="讲师头像">
        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :on-error="handleAvatarError"
          :action="BASE_API+'/admin/oss/file/upload?module=avatar'"
          class="avatar-uploader">
          <img v-if="teacher.avatar" :src="teacher.avatar" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"/>
        </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate()">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

import teacherApi from '@/api/teacher'

export default {
  data() {
    return {
      BASE_API: process.env.BASE_API, // 获取api
      // 初始化讲师默认数据
      teacher: {
        sort: 0,
        level: 1
      },
      saveBtnDisabled: false // 保存按钮是否禁用，防止表单重复提交
    }
  },
  created() {
    // 获取当前路由的对象如果有id值的话就证明不是新增而是修改
    if (this.$route.params.id) { // 数据回显
      this.fetchDataById(this.$route.params.id)
    }
  },
  methods: {

    saveOrUpdate() {
      this.saveBtnDisabled = true
      if (!this.teacher.id) { // 如果  id 为空的话就新增
        this.saveData()
      } else {
        this.updateData()
      }
    },

    // 通过id去获取数据
    fetchDataById(id) {
      teacherApi.getById(id).then(res => {
        this.teacher = res.data.item
        console.log(this.teacher)
      })
    },

    // 新增老师
    saveData() {
      teacherApi.save(this.teacher).then(res => {
        this.$message({ // 返回添加成功信息
          type: 'success',
          message: res.message
        })
        this.$router.push({ path: '/teacher' })
      })
    },

    // 修改教师信息
    updateData() {
      teacherApi.updateById(this.teacher).then(res => {
        this.$message({ // 返回添加成功信息
          type: 'success',
          message: res.message
        })
        this.$router.push({ path: '/teacher' })
      })
    }, // 头像上传成功函数
    handleAvatarSuccess(res) {
      if (res.success) {
        this.teacher.avatar = res.data.url
        // 重新渲染
        this.$forceUpdate()
      } else {
        this.$message.error('上传失败,请联系网站管理员')
      }
    },
    // 头像上传之前执行的函数
    beforeAvatarUpload(file) {
      const isJPGorPNG = (file.type === 'image/jpeg') || (file.type === 'image/png')
      const isLt2M = file.size / 1024 / 1024 < 1

      if (!isJPGorPNG) {
        this.$message.error('上传头像图片只能是 JPG/PNG 格式！')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 1MB!')
      }
      return isJPGorPNG && isLt2M
    },
    handleAvatarError(res) {
      this.$message.error('上传失败（http失败）')
    }
  }
}
</script>
<style  scoped>
 .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
