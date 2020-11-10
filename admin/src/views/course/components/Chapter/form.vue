<template>
  <!-- 添加和修改章节表单 -->
  <el-dialog :visible="dialogVisible" title="添加章节" @close="close()">
    <el-form :model="chapter" label-width="120px">
      <el-form-item label="章节标题">
        <el-input v-model="chapter.title"/>
      </el-form-item>
      <el-form-item label="章节排序">
        <el-input-number v-model="chapter.sort" :min="0"/>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close()">取 消</el-button>
      <el-button type="primary" @click="saveOrUpdate()">确 定</el-button>
    </div>
  </el-dialog>
</template>
<script>
import chapterApi from '@/api/chapter'
export default {
  data() {
    return {
      dialogVisible: false,
      chapter: {
        sort: 0
      }
    }
  }, created() {

  },
  methods: {
    saveOrUpdate() {
      if (!this.chapter.id) {
        this.save()
      } else {
        this.update()
      }
    },
    // 添加章节
    save() {
      this.chapter.courseId = this.$parent.$parent.courseId
      chapterApi.save(this.chapter).then(res => {
        this.$message.success(res.message)
        // 关闭窗口
        this.close()
        // 刷新列表
        this.$parent.getNestedChapterList()
      })
    },
    close() {
      this.dialogVisible = false
      // 重置表单
      this.resetForm()
    },
    // 添加章节打开窗口
    open(chapterId) {
      this.dialogVisible = true
      if (chapterId) {
        chapterApi.getById(chapterId).then(response => {
          this.chapter = response.data.item
        })
      }
    },
    // 清空表单
    resetForm() {
      this.chapter = { sort: 0 }
    },
    // 更新章节
    update() {
      this.chapter.courseId = this.$parent.$parent.courseId
      chapterApi.updateById(this.chapter).then(res => {
        this.$message.success(res.message)
        // 关闭窗口
        this.close()
        // 刷新列表
        this.$parent.getNestedChapterList()
      })
    },
    // 删除章节
    removeChapterById(chapterId) {
      chapterApi.removeById(chapterId).then(res => {
        this.$message.success(res.message)
        // 刷新章节列表
        this.$parent.getNestedChapterList()
      })
    }

  }
}
</script>
