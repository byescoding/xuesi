<template>
  <div class="app-container">
    <el-steps :active="active" align-center style="margin: 30px auto">
      <el-step title="基本信息" icon="el-icon-edit" description="编辑课程基本信息如课程名价格等"/>
      <el-step title="视频章节" icon="el-icon-upload" description="编辑课程章节及上传相应教学视频"/>
      <el-step title="课程大纲" icon="el-icon-picture" description="一个关于该课程的最终汇总"/>
      <el-step title="发布课程" icon="el-icon-check" description="最终确定并正式发布课程"/>
    </el-steps>
    <!-- 填写课程基本信息 -->
    <info v-if="active === 0" />

    <!-- 创建课程大纲 -->
    <chapter v-if="active === 1" />

    <!-- 发布课程 -->
    <Publish v-if="active === 2 || active === 3" />

  </div>
</template>

<script>
import Info from '@/views/course/components/Info'
import Chapter from '@/views/course/components/Chapter' // 此时会去找 chapter文件夹下的  index.vue  文件或者  form.vue 文件
import Publish from '@/views/course/components/Publish'

export default {
  components: { Info, Chapter, Publish },
  data() {
    return {
      active: 0,
      courseId: null
    }
  }, created() {
    this.isInfoEditFormList()
  }, methods: {
    isInfoEditFormList() {
      // 如何判断当前路由id是由list界面的修改按钮点击来的  此时可以用当前跳转过来的组件名去判断到底是回显还是list中的修改
      if (this.$route.name === 'CourseInfoEdit') {
        this.courseId = this.$route.params.id
        this.active = 0
      }

      if (this.$route.name === 'CourseChapterEdit') {
        this.courseId = this.$route.params.id
        this.active = 1
      }
    }
  }
}
</script>
