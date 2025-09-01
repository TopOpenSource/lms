<template>
  <div class="app-container" v-loading="loading">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="学期" prop="term">
        <el-select v-model="queryParams.term" placeholder="学期" clearable>
          <el-option
            v-for="dict in dict.type.term"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="班级" prop="clazz">
        <el-select v-model="queryParams.clazz" placeholder="班级" clearable>
          <el-option
            v-for="dict in dict.type.clazz"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
      </el-form-item>
    </el-form>


    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="submitForm">保存</el-button>
      </el-col>
    </el-row>

    <!-- 课程表 -->
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="period" label="节次" width="80"></el-table-column>
      <el-table-column
        v-for="day in week_days"
        :key="day.key"
        :label="day.label"
      >
        <template slot-scope="scope">
          <el-select
            v-model="scope.row[day.key]"
            placeholder="请选择课程"
          >
            <el-option
              v-for="dict in dict.type.course"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </template>
      </el-table-column>
    </el-table>

  </div>
</template>
<script>

import {list,saveBatch } from "@/api/lms/course";
export default {
  name: "Course",
  dicts: ['clazz', 'term','course'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 查询参数
      queryParams: {
        term: '20251',
        clazz: '1'
      },
      week_days: [
        { key: 1, label: '星期一' },
        { key: 2, label: '星期二' },
        { key: 3, label: '星期三' },
        { key: 4, label: '星期四' },
        { key: 5, label: '星期五' }
      ],
      class_period:[1,2,3,4,5,6],
      tableData:[]
    };
  },
  created() {
    this.getList();
  },
  methods: {
    initEmptyTable() {
      this.tableData = this.class_period.map(period => {
        const row = { period }
        this.week_days.forEach(day => {
          row[day.key] = ''
        })
        return row
      })
    },
    /**列表 */
    getList() {
      this.loading = true;
      list(this.queryParams).then(response => {
        this.loading = false;
      });
    },

    reset() {
      this.tableData = [];
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.reset()
      this.initEmptyTable()
      this.getList();
    },

    /** 提交按钮 */
    submitForm: function() {
      this.loading = true;
      console.log(this.tableData)
      const records = []
      this.tableData.forEach(row => {
        this.week_days.forEach(day => {
          const course = row[day.key]
          if (course) {
            records.push({
              clazz: this.queryParams.clazz,
              term: this.queryParams.term,
              weekDay: day.key,
              classPeriod: row.period,
              course: course
            })
          }
        })
      })
      saveBatch({"courseDtoList":records}).then(response => {
        this.loading = false;
      })
    },
    mounted() {

    }

  }
};

</script>
