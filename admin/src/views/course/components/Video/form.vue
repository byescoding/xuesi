<template>
  <!-- 添加和修改课时表单 -->
  <el-dialog :visible="dialogVisible" title="添加课时" @close="close()">
    <el-form :model="video" label-width="120px">
      <el-form-item label="课时标题">
        <el-input v-model="video.title"/>
      </el-form-item>
      <el-form-item label="课时排序">
        <el-input-number v-model="video.sort" :min="0" />
      </el-form-item>
      <el-form-item label="是否免费">
        <el-radio-group v-model="video.free">
          <el-radio :label="true">免费</el-radio>
          <el-radio :label="false">默认</el-radio>
        </el-radio-group>
      </el-form-item>
      <!-- 上传视频 -->
      <el-form-item label="上传视频">
        <el-upload
          ref="upload"
          :auto-upload="false"
          :on-success="handleUploadSuccess"
          :on-error="handleUploadError"
          :on-exceed="handleUploadExceed"
          :file-list="fileList"
          :before-remove="handleBeforeRemove"
          :on-remove="handleOnRemove"
          :limit="1"
          :action="BASE_API+'/admin/vod/media/upload'">
          <el-button slot="trigger" size="small" type="primary">选择视频</el-button>
          <el-button
            :disabled="uploadBtnDisabled"
            style="margin-left: 10px;"
            size="small"
            type="success"
            @click="submitUpload()">上传</el-button>
        </el-upload>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close()">取 消</el-button>
      <el-button type="primary" @click="saveOrUpdate()">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import videoApi from '@/api/video'
import vodApi from '@/api/vod'
export default {
  data() {
    return {
      dialogVisible: false, // 是否显示对话框
      video: {
        sort: 0,
        free: false
      },
      fileList: [], // 上传文件列表
      uploadBtnDisabled: false,
      BASE_API: process.env.BASE_API
    }
  },
  created() {

  },
  methods: {
    // 上传视频
    submitUpload() {
      this.uploadBtnDisabled = true
      this.$refs.upload.submit() // 提交上传请求
    },
    // 关闭
    close() {
      this.dialogVisible = false
      // 重置表单
      this.resetForm()
    },
    resetForm() {
      this.video = {
        sort: 0,
        free: false
      }
      this.fileList = [] // 重置视频上传列表
    },
    saveOrUpdate() {
      if (!this.video.id) {
        this.save()
      } else {
        this.update()
      }
    },
    save() {
      this.video.courseId = this.$parent.$parent.courseId
      videoApi.save(this.video).then(res => {
        this.$message.success(res.message)
        // 关闭组件
        this.close()
        // 刷新列表
        this.$parent.getNestedChapterList()
      })
    },
    update() {
      videoApi.updateById(this.video).then(res => {
        this.$message.success(res.message)
        // 关闭组件
        this.close()
        // 刷新列表
        this.$parent.getNestedChapterList()
      })
    },
    // 显示对话框
    open(chapterId, videoId) {
      this.dialogVisible = true
      this.video.chapterId = chapterId
      if (videoId) {
        videoApi.getById(videoId).then(response => {
          this.video = response.data.item
          // 回显
          if (this.video.videoOriginalName) {
            this.fileList = [{ 'name': this.video.videoOriginalName }]
          }
        })
      }
    },

    removeVideoById(videoId) {
      videoApi.removeById(videoId).then(res => {
        this.$message.success(res.message)
        // 刷新列表
        this.$parent.getNestedChapterList()
      })
      console.log(videoId)
    },
    // 删除视频文件确认
    handleBeforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    // 删除视频文件
    handleOnRemove(file, fileList) {
      console.log(this.video.videoSourceId)
      if (!this.video.videoSourceId) {
        return
      }
      vodApi.removeByVodId(this.video.videoSourceId).then(res => {
        this.video.videoSourceId = ''
        this.video.videoOriginalName = ''
        videoApi.updateById(this.video)
        this.$message.success(res.message)
      })
    },

    handleUploadSuccess(response, file, fileList) {
      this.uploadBtnDisabled = false
      if (response.success) {
        this.video.videoSourceId = response.data.videoId
        this.video.videoOriginalName = file.name
      } else {
        this.$message.error('上传文件失败')
      }
      console.log(this.video.videoSourceId)
    },
    handleUploadError() {
      this.dialogVisible = false// 关闭提交按钮
      this.$message.error('上传文件失败')
    },
    handleUploadExceed() {
      this.$message.success('只能传输一个文件')
    }

  }
}
</script>

<style  scoped>

</style>
