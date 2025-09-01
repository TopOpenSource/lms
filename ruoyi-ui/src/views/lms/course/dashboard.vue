<template>
  <div class="app-container" v-loading="loading">

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="学期" prop="term">
        <el-select v-model="queryParams.term" placeholder="学期" @change="handleQuery">
          <el-option
            v-for="dict in dict.type.term"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="年级" prop="grade">
        <el-select v-model="queryParams.grade" placeholder="年级"  @change="handleQuery">
          <el-option
            v-for="dict in dict.type.grade"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>

<!--      <el-form-item>-->
<!--        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>-->
<!--      </el-form-item>-->
    </el-form>

    <el-table :data="tableData" border>
      <!-- 节次列 -->
      <el-table-column
        prop="classPeriod"
        label="节次"
        fixed
        align="center"
      ></el-table-column>

      <!-- 动态星期 -->
      <el-table-column
        v-for="day in week_days"
        :key="day.key"
        :label="day.label"
        align="center"
      >
        <!-- 动态班级 -->
        <el-table-column
          v-for="clazz in dict.type.clazz"
          :key="`${day.value}-${clazz.value}`"
          :label="clazz.label"
          align="center"
        >
          <template slot-scope="scope">
            <div v-if="scope.row[day.key] && scope.row[day.key][clazz.value] && scope.row[day.key][clazz.value].course">
              <dict-tag :options="dict.type.course" :value="scope.row[day.key][clazz.value].course"/>
            </div>
            <div v-else>--</div>
          </template>
        </el-table-column>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import {list} from "@/api/lms/course";

export default {
  dicts: ['term', 'clazz', 'course','grade'],
  data() {
    return {
      loading: false,
      // 显示搜索条件
      showSearch: true,
      // 查询参数
      queryParams: {
        term: '20251',
        grade: '1',
      },
      // 核心数据
      tableData: [],
      week_days: [
        {key: 1, label: '星期一'},
        {key: 2, label: '星期二'},
        {key: 3, label: '星期三'},
        {key: 4, label: '星期四'},
        {key: 5, label: '星期五'}
      ],
      class_period: [1, 2, 3, 4, 5, 6],
    };
  },
  methods: {
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    //根据节次获取行
    getRow(classPeriod) {
      let rows = this.tableData.filter(item => {
        return item.classPeriod == classPeriod;
      })

      if (rows.length == 1) {
        return rows[0]
      }
    },

    getList(callBack) {
      //班级
      this.getDicts("clazz").then(response => {
        let clazzList = response.data
        //节次
        this.tableData = this.class_period.map(num => {
          const row = {
            classPeriod: num
          };
          //星期
          this.week_days.forEach(day => {
            const week = {}
            //班级
            clazzList.forEach(clazz => {
              week[clazz.dictValue] = {course: ''}
            })
            row[day.key] = week
          })
          return row;
        })

        list(this.queryParams).then(response => {
          response.forEach(row => {
            let periodObj=this.getRow(row.classPeriod)
            periodObj[row.weekDay][row.clazz].course=row.course
          })

          console.log(this.tableData)
          this.loading = false;
        });

      })
    },
  },
  mounted() {
    this.getList()
  }
};
</script>
