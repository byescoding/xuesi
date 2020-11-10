<template>
  <div class="app-container">
    <!-- 输入表单 -->
    <el-form label-width="120px">
      <el-form-item label="广告推荐名称">
        <el-input v-model="ad.title" />
      </el-form-item>
      <!-- 推荐位 -->
      <el-form-item label="推荐位">
        <el-select
          v-model="ad.typeId"
          placeholder="请选择">
          <el-option
            v-for="adType in adTypeList"
            :key="adType.id"
            :label="adType.title"
            :value="adType.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="排序">
        <el-input-number v-model="ad.sort" :min="0"/>
      </el-form-item>
      <el-form-item label="广告图片">
        <el-upload
          :on-success="handleAvatarSuccess"
          :on-error="handleAvatarError"
          :on-exceed="handleUploadExceed"
          :before-upload="beforeAvatarUpload"
          :limit="1"
          :file-list="fileList"
          :action="BASE_API+'/admin/oss/file/upload?module=ad'"
          list-type="picture">
          <el-button size="small" type="primary">点击上传</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item label="背景颜色">
        <el-color-picker v-model="ad.color"/>
      </el-form-item>
      <el-form-item label="链接地址">
        <el-input v-model="ad.linkUrl" />
      </el-form-item>
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate()">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import adApi from '@/api/ad'
import adTypeApi from '@/api/adType'
export default {
  data() {
    return {
      ad: {
        sort: 0
      },
      fileList: [], // 上传文件列表
      adTypeList: [], // 广告位置列表
      saveBtnDisabled: false, // 保存按钮是否禁用，防止表单重复提交
      BASE_API: process.env.BASE_API
    }
  },
  created() {
    if (this.$route.params.id) {
      // 获取广告数据
      this.fetchData(this.$route.params.id)
    }
    // 初始化列表
    this.fetchAdTypeList()
  },
  methods: {
    fetchData() {
      adApi.getById(this.$route.params.id).then(res => {
        this.ad = res.data.item
      })
    },
    // 获取位置信息  列表
    fetchAdTypeList() {
      adTypeApi.list().then(res => {
        this.adTypeList = res.data.items
      })
    },
    // 跟新或者删除
    saveOrUpdate() {
      if (!this.ad.id) {
        this.save()
      } else {
        this.updataById()
      }
    },
    // 保存广告信息
    save() {
      adApi.save(this.ad).then(res => {
        this.$message.success(res.message)
        this.$router.push({ path: '/ad/list' }) // 跳转至广告列表
      })
    },

    // 跟新广告消息
    updataById() {
      // teacher数据的获取
      adApi.updateById(this.ad).then(response => {
        this.$message.success(response.message)
        this.$router.push({ path: '/ad/list' })
      })
    },

    // 上传多于一个文件
    handleUploadExceed(files, fileList) {
      this.$message.warning('想要重新上传图片，请先删除已上传的视频')
    },

    // 上传校验
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },

    // 上传成功回调
    handleAvatarSuccess(res, file) {
      console.log(res)
      if (res.success) {
        // console.log(res)
        this.ad.imageUrl = res.data.url
        // 强制重新渲染
        // this.$forceUpdate()
      } else {
        this.$message.error('上传失败1')
      }
    },

    // 错误处理
    handleAvatarError() {
      console.log('error')
      this.$message.error('上传失败2')
    }
  }
}
</script>
