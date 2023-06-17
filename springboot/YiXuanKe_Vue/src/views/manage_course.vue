<template>
  <el-container style="margin-left:200px;margin-top:80px;">

    <!-- <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow" :sideWidth="sideWidth"></Aside> -->

    <el-container>
      <!-- <el-header style="font-size: 12px; border-bottom: 1px solid #ccc;">
        <Header :collapse="collapse" :collapseBtnClass="collapseBtnClass"></Header>
      </el-header> -->

      <el-main>
        <!-- <div style="margin-bottom: 30px">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>用户管理</el-breadcrumb-item>
          </el-breadcrumb>
        </div> -->

        <div style="margin: 10px 0">
          <el-input style="width: 200px" placeholder="请输入课程名称" suffix-icon="el-icon-search" v-model="CName"></el-input>
          <el-input style="width: 200px" placeholder="请输入上课时间" suffix-icon="el-icon-message" class="ml-5"
                    v-model="Cday"></el-input>
          <el-input style="width: 200px" placeholder="请输入授课教师工号" suffix-icon="el-icon-position" class="ml-5"
                    v-model="Tno"></el-input>
          <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
          <el-button type="warning" @click="reset">重置</el-button>
        </div>

        <div style="margin: 10px 0">
          <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
          <el-upload action="http://121.36.74.25:9090/course/import" :show-file-list="false" accept="xlsx"
                     :on-success="handleImportSuccess" style="display: inline-flex; margin-right: 3px">
            <el-button type="danger" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
          </el-upload>

          <el-button type="primary" @click="handleExport">导出 <i class="el-icon-top"></i></el-button>
        </div>

        <el-table :data="tableData" stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
          <!-- <el-table-column type="selection" width="55"></el-table-column> -->
          <!--          <el-table-column prop="id" label="ID" width="80"></el-table-column>-->
          <el-table-column prop="Cid" label="课程号"></el-table-column>
          <el-table-column prop="CName" label="课程名称" width="120"></el-table-column>
          <el-table-column prop="Ccredit" label="学分"></el-table-column>
          <el-table-column prop="schedule" label="日期" width="120"></el-table-column>
          <el-table-column prop="college" label="开设学院" width="140"></el-table-column>
          <el-table-column prop="classroom" label="课程教室" width="140"></el-table-column>
          <el-table-column prop="type" label="课程类型"></el-table-column>
          <el-table-column prop="chosenCon" label="选课情况"></el-table-column>
          <el-table-column prop="CourseDesc" label="课程详情" width="140"></el-table-column>
          <el-table-column prop="Tno" label="授课教师工号"></el-table-column>
          <el-table-column prop="Tname" label="授课教师姓名"></el-table-column>
          <el-table-column label="操作" width="200" text-align="center">
            <template slot-scope="scope">
              <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
              <el-popconfirm class="ml-5" confirm-button-text='确定' cancel-button-text='我再想想' icon="el-icon-info"
                             icon-color="red" title="您确定删除吗？" @confirm="del(scope.row.Cid)">
                <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
        <div style="padding: 10px 0">
          <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
                         :page-sizes="[2, 5, 10, 20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
                         :total="total">
          </el-pagination>
        </div>

        <el-dialog title="课程信息" :visible.sync="dialogFormVisible" width="30%">
          <el-form label-width="110px" size="small">
            <el-form-item label="课程名称">
              <el-input v-model="form.CName" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="学分">
              <el-input v-model="form.Ccredit" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="日期">
              <el-select v-model="form.Cday" placeholder="周一">
                <el-option
                    v-for="item in dayOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="开始节数">
              <el-input v-model="form.timeStart" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="结束节数">
              <el-input v-model="form.timeEnd" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="开设学院">
              <el-select v-model="form.college" placeholder="请选择">
                <el-option
                    v-for="item in collegeOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="课程教室">
              <el-input v-model="form.classroom" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="课程类型">
              <el-select v-model="form.type" placeholder="请选择">
                <el-option
                    v-for="item in typeOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="已选人数">
              <el-input v-model="form.chosenNum" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="上限人数">
              <el-input v-model="form.totalNum" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="课程简介">
              <el-input v-model="form.CourseDesc" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="授课老师工号">
              <el-input v-model="form.Tno" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
          </div>
        </el-dialog>


      </el-main>

    </el-container>
  </el-container>
</template>

<script>
import Aside from "@/components/Aside.vue"
import Header from "@/components/Header.vue"

export default {
  name: 'Home',
  data() {

    return {
      tableData:[],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      username: "",
      CName: "",
      Cday: "",
      Tno: "",
      dialogFormVisible: false,
      multipleSelection: [],
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      headerBg: 'headerBg',
      form:{
        Cid: -1,
        CName: "",
        Ccredit: null,
        Cday:"周一",
        timeStart: null,
        timeEnd: null,
        college:"电子与信息工程学院",
        classroom: "",
        type:"专业必修课",
        chosenNum:null,
        totalNum:null,
        CourseDesc:"",
        Tno:"",
      },
      dayOptions:[
        { label: '周一', value: '周一' },
        { label: '周二', value: '周二' },
        { label: '周三', value: '周三' },
        { label: '周四', value: '周四' },
        { label: '周五', value: '周五' },
        { label: '周六', value: '周六' },
        { label: '周日', value: '周日' },
      ],
      collegeOptions:[
        { label: '电子与信息工程学院', value: '电子与信息工程学院' },
        { label: '软件学院', value: '软件学院' },
        { label: '经济与管理学院', value: '经济与管理学院' },
        { label: '艺术与传媒学院', value: '艺术与传媒学院' },
        { label: '数学科学学院', value: '数学科学学院' },
      ],
      typeOptions:[
        { label: '专业必修课', value: '专业必修课' },
        { label: '专业选修课', value: '专业选修课' },
        { label: '通识必修课', value: '通识必修课' },
        { label: '通识选修课', value: '通识选修课' },
      ],
      form_type: "insert",
    }
  },
  created() {
    // 请求分页查询数据
    this.load()
    this.initial_form()
  },
  components: {
    Aside,
    Header,
  },
  methods: {
    collapse() {  // 点击收缩按钮触发
      this.isCollapse = !this.isCollapse
      if (this.isCollapse) {  // 收缩
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      } else {   // 展开
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    },
    initial_form() {
      this.form = {
        Cid: -1,
        CName: "",
        Ccredit: null,
        Cday:"周一",
        timeStart: null,
        timeEnd: null,
        college:"电子与信息工程学院",
        classroom: "",
        type:"专业必修课",
        chosenNum:null,
        totalNum:null,
        CourseDesc:"",
        Tno:"",
      }
    },
    load() {
      this.request.get("/course/find_by_page_condition", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          CName: this.CName,
          Cday: this.Cday,
          Tno: this.Tno
        }
      }).then(res => {
        // console.log(res)
        // console.log(res.data)
        console.log(res.data)
        this.tableData = res.data
        // console.log(this.tableData)
        this.total = res.total

      })
    },
    save() {
      console.log('Ccredit')
      console.log(this.form.Ccredit)
      console.log(typeof this.form.Ccredit)
      if(this.form.CName === undefined || this.form.CName.length === 0)
      {
        this.$notify.error({
          title: '错误',
          message: '课程名称不可为空',
          position: 'top-center'
        });
      }
      else if(this.form.Ccredit === null || this.form.Ccredit < 0)
      {
        this.$notify.error({
          title: '错误',
          message: '学分不可为空且为正数',
          position: 'top-center'
        });
      }
      else if(this.form.Cday === undefined ||this.form.Cday.length === 0)
      {
        this.$notify.error({
          title: '错误',
          message: '日期不可为空',
          position: 'top-center'
        });
      }
      else if(this.form.classroom === undefined ||this.form.classroom.length === 0)
      {
        this.$notify.error({
          title: '错误',
          message: '上课教室不可为空',
          position: 'top-center'
        });
      }
      else if(this.form.timeStart === null || this.form.timeStart < 0 || this.form.timeStart > 12)
      {
        this.$notify.error({
          title: '错误',
          message: '开始节数不可为空且须在1-12之间',
          position: 'top-center'
        });
      }
      else if(this.form.timeEnd === null || this.form.timeEnd < 0 || this.form.timeEnd > 12)
      {
        this.$notify.error({
          title: '错误',
          message: '结束节数不可为空且须在1-12之间',
          position: 'top-center'
        });
      }
      else if(this.form.timeEnd < this.form.timeStart)
      {
        this.$notify.error({
          title: '错误',
          message: '开始结束需不大于结束节数',
          position: 'top-center'
        });
      }
      else if(this.form.chosenNum === null || this.form.chosenNum < 0)
      {
        this.$notify.error({
          title: '错误',
          message: '已选人数需要非空且非负',
          position: 'top-center'
        });
      }
      else if(this.form.totalNum === null || this.form.totalNum < 1)
      {
        this.$notify.error({
          title: '错误',
          message: '上限人数需要非空且至少为1',
          position: 'top-center'
        });
      }
      else if(this.form.totalNum < this.form.chosenNum)
      {
        this.$notify.error({
          title: '错误',
          message: '已选人数不得大于上限人数',
          position: 'top-center'
        });
      }
      else if(this.form.Tno === undefined || this.form.Tno.length !== 5)
      {
        this.$notify.error({
          title: '错误',
          message: '授课老师工号必须非空且必须为5位',
          position: 'top-center'
        });
      }
      else if(this.form_type === "insert")
      {
        this.request.post("/course/insert_one_course", this.form).then(res => {
          if (res) {
            this.$message.success("插入成功")
            this.dialogFormVisible = false
            this.load()
          } else {
            this.$message.error("插入失败")
          }
        })
        this.initial_form()
      }
      else
      {
        this.request.post("/course/update_one_course", this.form).then(res => {
          if (res) {
            this.$message.success("更新成功")
            this.dialogFormVisible = false
            this.load()
          } else {
            this.$message.error("更新失败")
          }
        })
        this.initial_form()
      }

    },
    handleAdd() {
      this.dialogFormVisible = true
      this.initial_form()
      this.form_type = "insert"
    },
    handleEdit(row) {
      console.log(row)
      this.form.Cid = row.Cid
      this.form.CName = row.CName
      this.form.Ccredit = row.Ccredit
      this.form.Cday = row.schedule.split(" ")[0]
      this.form.timeStart = row.schedule.split(" ")[1].split("到")[0]
      this.form.timeEnd = row.schedule.split(" ")[1].split("到")[1].split("节")[0]
      this.form.chosenNum = row.chosenCon.split("/")[0]
      this.form.totalNum = row.chosenCon.split("/")[1]
      this.form.CourseDesc = row.CourseDesc
      this.form.Tno = row.Tno
      this.form.classroom = row.classroom
      this.form_type = "update"

      this.dialogFormVisible = true

    },
    del(id) {
      this.request.delete("/course/" + id).then(res => {
        if (res) {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    handleExport() {
      window.open("http://121.36.74.25:9090/course/export")
    },
    reset() {
      this.CName = ""
      this.Cday = ""
      this.Tno = ""

      this.load()
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
    handleImportSuccess(response){
      this.$message.success(response)
      this.load()
    }
  }
}
</script>

<style>
.headerBg {
  background: #eee !important;
}

.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}
</style>

