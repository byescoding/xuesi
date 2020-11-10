<template>
  <div class="app-container">
    <!-- 导航顶部条查询 -->
    <!--查询表单-->
    <el-form :inline="true">
      <el-form-item>
        <el-autocomplete
          v-model="searchObj.name"
          :fetch-suggestions="querySearch"
          :trigger-on-focus="false"
          class="inline-input"
          placeholder="讲师姓名"
          value-key="name" />
      </el-form-item>

      <el-form-item>
        <el-select v-model="searchObj.level" clearable placeholder="头衔">
          <el-option value=" " label=" "/>
          <el-option value="1" label="高级讲师"/>
          <el-option value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>

      <el-form-item label="入驻时间">
        <el-date-picker
          v-model="searchObj.joinDateBegin"
          placeholder="开始时间"
          value-format="yyyy-MM-dd" />
      </el-form-item>
      <el-form-item label="-">
        <el-date-picker
          v-model="searchObj.joinDateEnd"
          placeholder="结束时间"
          value-format="yyyy-MM-dd" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="fetchData()">查询</el-button>
        <el-button type="default" @click="resetData()">清空</el-button>
      </el-form-item>

    </el-form>

    <!-- 工具按钮  批量删除 -->
    <!-- 工具按钮 -->
    <div style="margin-bottom: 10px">
      <el-button type="danger" size="mini" @click="batchRemove()">批量删除</el-button>
    </div>

    <!-- 教师信息列表展示 -->
    <el-table :data="list" border stripe @selection-change="handleSelectionChange" >
      <el-table-column type="selection" />
      <el-table-column width="70" align="center" label="#" >
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="名称" width="90" align="center"/>
      <el-table-column label="头衔" width="100" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.level === 1" type="success" size="mini">高级讲师</el-tag>
          <el-tag v-if="scope.row.level === 2" size="mini">首席讲师</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="intro" label="简介" align="center"/>
      <el-table-column prop="sort" label="排序" width="60" align="center"/>
      <el-table-column prop="joinDate" label="入驻时间" width="160" align="center"/>
      <el-table-column label="操作" width="220" align="center">
        <template slot-scope="scope">
          <router-link :to="'/teacher/edit/'+scope.row.id">
            <el-button type="primary" size="small" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination
      :current-page="page"
      :total="total"
      :page-size="limit"
      :page-sizes="[5, 10, 20, 30, 40, 50, 100]"
      style="padding: 30px 0; text-align: center;"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="changePageSize"
      @current-change="changeCurrentPage"
    />
  </div>
</template>

<script>
import teacherApi from '@/api/teacher'
export default {
  data() {
    return {
      list: null, // 返回的数据列表
      total: 0, // 总记录数
      page: 1, // 当前页码
      limit: 5, // 默认数据的页面大小为5
      searchObj: {}, // 查询条件对象
      muiltpleSelection: []// 批量删除记录
    }
  }, created() {
    // 在页面渲染之前加载数据
    this.fetchData()
  },
  methods: {
    // 获取列表数据
    fetchData() {
      teacherApi.pageList(this.page, this.limit, this.searchObj)
        .then(res => {
          // 在这里的话要注意一下  此时是有过滤的链接的此时返回的数据是经过过滤的返回的数据是  response.data
          this.list = res.data.rows
          this.total = res.data.total
        })
      console.log(this.page, this.limit, this.searchObj)
    },

    // 每页记录数改变，size：回调参数，表示当前选中的“每页条数”
    changePageSize(size) {
      this.limit = size
      this.fetchData()
    },
    // 改变页码，page：回调参数，表示当前选中的“页码”
    changeCurrentPage(page) {
      this.page = page
      this.fetchData()
    },
    // 重置表单
    resetData() {
      this.searchObj = {}
      this.fetchData()
    },
    // 通过id删除  教师
    removeById(id) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        teacherApi.removeById(id).then(res => {
          this.fetchData()
          this.$message.success(res.message)
        })
      }).catch((error) => {
        // 当取消时会进入catch语句:error = 'cancel'
        // 当后端服务抛出异常时：error = 'error'
        if (error === 'cancel') {
          this.message.info('删除失败')
        }
      })
    },
    // 当多选选项发生变化的时候调用  将id  传进数组
    handleSelectionChange(selection) {
      console.log(selection)
      selection.forEach(item => {
        this.muiltpleSelection.push(item.id)
      })
      console.log(this.muiltpleSelection)
    },
    // 批量删除教师信息
    batchRemove() {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        teacherApi.batchRemove(this.muiltpleSelection).then(res => {
          this.fetchData()
          this.$message.success(res.message)
        })
      }).catch((error) => {
        // 当取消时会进入catch语句:error = 'cancel'
        // 当后端服务抛出异常时：error = 'error'
        if (error === 'cancel') {
          this.message.info('删除失败')
        }
      })
    },
    querySearch(queryString, callback) {
      teacherApi.selectNameListByKey(queryString).then(res => {
        callback(res.data.nameList)
      })
    }
  }

}
</script>
