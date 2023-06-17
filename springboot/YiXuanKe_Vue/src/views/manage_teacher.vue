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
          <el-input style="width: 200px" placeholder="请输入工号" suffix-icon="el-icon-search" v-model="tno"></el-input>
          <el-input style="width: 200px" placeholder="请输入姓名" suffix-icon="el-icon-message" class="ml-5"
                    v-model="tname"></el-input>
          <el-input style="width: 200px" placeholder="请输入职称" suffix-icon="el-icon-position" class="ml-5"
                    v-model="title"></el-input>
          <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
          <el-button type="warning" @click="reset">重置</el-button>
        </div>

        <div style="margin: 10px 0">
          <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
          <el-upload action="http://121.36.74.25:9090/teacher/import" :show-file-list="false" accept="xlsx"
                     :on-success="handleImportSuccess" style="display: inline-flex; margin-right: 3px">
            <el-button type="danger" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
          </el-upload>
          <el-button type="primary" @click="handleExport">导出 <i class="el-icon-top"></i></el-button>
        </div>

        <el-table :data="tableData" stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
          <!-- <el-table-column type="selection" width="55"></el-table-column> -->
          <!--          <el-table-column prop="id" label="ID" width="80"></el-table-column>-->
          <el-table-column prop="Tno" label="工号" width="140"></el-table-column>
          <el-table-column prop="MajorName" label="专业" width="120"></el-table-column>
          <el-table-column prop="Tname" label="姓名"></el-table-column>
          <el-table-column prop="password" label="密码"></el-table-column>
          <el-table-column prop="sex" label="性别"></el-table-column>
          <el-table-column prop="Title" label="职称"></el-table-column>
          <el-table-column prop="phone" label="联系电话"></el-table-column>
          <el-table-column label="操作" width="200" text-align="center">
            <template slot-scope="scope">
              <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
              <el-popconfirm class="ml-5" confirm-button-text='确定' cancel-button-text='我再想想' icon="el-icon-info"
                             icon-color="red" title="您确定删除吗？" @confirm="del(scope.row.Tno)">
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

        <el-dialog title="教师信息" :visible.sync="dialogFormVisible" width="30%">
          <el-form label-width="80px" size="small">
            <el-form-item label="工号" v-show="showNo">
              <el-input v-model="form.Tno" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="专业">
              <el-select v-model="mName" placeholder="计算机科学与技术">
                <el-option
                    v-for="item in majorOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="姓名">
              <el-input v-model="form.Tname" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="form.password" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="性别">
              <el-select v-model="form.sex" placeholder="男">
                <el-option
                    v-for="item in sexOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="职称">
              <el-select v-model="form.title" placeholder="教授">
                <el-option
                    v-for="item in titleOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="手机">
              <el-input v-model="form.phone" autocomplete="off"></el-input>
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
      tno: "",
      tname: "",
      title: "",
      email: "",
      address: "",
      dialogFormVisible: false,
      multipleSelection: [],
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      headerBg: 'headerBg',
      form:{
        Tno: "",
        MajorId: 1,
        Tname: "",
        password:"",
        sex:"",
        title:"",
        phone:"",
      },
      majorOptions:[
        { label: '计算机科学与技术', value: '计算机科学与技术' },
        { label: '信息安全', value: '信息安全' },
        { label: '大数据科学', value: '大数据科学' }
      ],
      sexOptions:[
        { label: '男', value: '男' },
        { label: '女', value: '女' },
      ],
      titleOptions:[
        { label: '教授', value: '教授' },
        { label: '副教授', value: '副教授' },
        { label: '助理教授', value: '助理教授' },
        { label: '讲师', value: '讲师' }
      ],
      majorConvert:
          {"计算机科学与技术": 1,
            "信息安全": 2,
            '大数据科学':3},
      mName: "计算机科学与技术",
      form_type: "insert",
      showNo : true
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
      this.form= {
        Tno: "",
        MajorId: 1,
        Tname: "",
        password:"",
        sex:"",
        title:"",
        phone:"",
      }
    },
    load() {
      this.request.get("/teacher/find_by_page_condition", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          tno: this.tno,
          tname: this.tname,
          title: this.title

        }
      }).then(res => {
        this.tableData = res.data
        console.log(this.tableData)
        this.total = res.total

      })
    },
    save() {
      this.form.MajorId = this.majorConvert[this.mName]
      if(this.form.Tno === undefined || this.form.Tno.length === 0)
      {
        this.$notify.error({
          title: '错误',
          message: '工号不可为空',
          position: 'top-center'
        });
      }
      else if(this.form.Tname === undefined || this.form.Tname.length === 0)
      {
        this.$notify.error({
          title: '错误',
          message: '姓名不可为空',
          position: 'top-center'
        });
      }
      else if(this.form.phone === undefined ||this.form.phone.length === 0)
      {
        this.$notify.error({
          title: '错误',
          message: '手机不可为空',
          position: 'top-center'
        });
      }
      else if(this.form.password === undefined || this.form.password.length === 0)
      {
        this.$notify.error({
          title: '错误',
          message: '密码不可为空',
          position: 'top-center'
        });
      }
      else if(this.form.Tno.length !== 5)
      {
        this.$notify.error({
          title: '错误',
          message: '工号必须为5位',
          position: 'top-center'
        });
      }
      else if(this.form.phone.length !== 11)
      {
        this.$notify.error({
          title: '错误',
          message: '手机号必须为11位',
          position: 'top-center'
        });
      }
      else if(this.form_type === "insert")
      {
        this.request.post("/teacher/insert_one_teacher", this.form).then(res => {
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
        this.request.post("/teacher/update_one_teacher", this.form).then(res => {
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
      this.mName = "计算机科学与技术"
      this.form_type = "insert"
      this.showNo = true
    },
    handleEdit(row) {
      console.log(row)
      this.form.Tno = row.Tno
      // this.form.MajorId = 1
      this.form.Tname = row.Tname
      this.form.password = row.password
      this.form.sex = row.sex
      this.form.title = row.Title
      this.form.phone = row.phone
      this.form_type = "update"
      this.mName = row.majorName
      this.dialogFormVisible = true
      this.showNo = false
    },
    del(id) {
      this.request.delete("/teacher/" + id).then(res => {
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
      window.open("http://121.36.74.25:9090/teacher/export")
    },
    reset() {
      this.tno = ""
      this.tname = ""
      this.title = ""

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

