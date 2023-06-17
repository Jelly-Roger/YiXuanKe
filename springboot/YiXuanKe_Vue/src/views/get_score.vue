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
          <el-input style="width: 200px" placeholder="请输入课程名称" suffix-icon="el-icon-search" v-model.number="cname"></el-input>
          <!-- <el-input style="width: 200px" placeholder="请输入姓名" suffix-icon="el-icon-message" class="ml-5"
            v-model="sname"></el-input> -->
          <!-- <el-input style="width: 200px" placeholder="请输入年级" suffix-icon="el-icon-position" class="ml-5"
            v-model="grade"></el-input> -->
          <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
          <el-button type="warning" @click="reset">重置</el-button>
        </div>

<!--        <div style="margin: 10px 0">-->
<!--          &lt;!&ndash; <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button> &ndash;&gt;-->
<!--          <el-button type="primary" class="ml-5">保存 <i class="el-icon-bottom"></i></el-button>-->
<!--          <el-button type="danger" @click="handleExport">提交 <i class="el-icon-top"></i></el-button>-->
<!--        </div>-->

        <el-table :data="tableData" stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
          <!-- <el-table-column type="selection" width="55"></el-table-column> -->
          <!--          <el-table-column prop="id" label="ID" width="80"></el-table-column>-->
          <el-table-column prop="cname" label="课程"></el-table-column>
          <!-- <el-table-column prop="cid" label="课程"></el-table-column> -->
          <el-table-column prop="sno" label="学号"></el-table-column>
          <el-table-column prop="majorName" label="专业" width="120"></el-table-column>
          <el-table-column prop="sname" label="姓名"></el-table-column>
          <!-- <el-table-column prop="password" label="密码"></el-table-column>
          <el-table-column prop="sex" label="性别"></el-table-column> -->
          <el-table-column prop="grade" label="年级"></el-table-column>
          <!-- <el-table-column prop="email" label="邮箱" width="200"></el-table-column> -->
          <el-table-column prop="procScore" label="平时成绩" ></el-table-column>
          <el-table-column prop="examScore" label="考试成绩" ></el-table-column>
          <el-table-column prop="finalScore" label="总成绩" ></el-table-column>
          <el-table-column prop="scoreRank" label="总评" ></el-table-column>
          <el-table-column label="操作" width="200" text-align="center">
            <template slot-scope="scope">
              <el-button type="success" @click="handleEdit(scope.row)">录入 <i class="el-icon-edit"></i></el-button>
              <el-popconfirm class="ml-5" confirm-button-text='确定' cancel-button-text='我再想想' icon="el-icon-info"
                icon-color="red" title="您确定删除吗？" @confirm="del(scope.row)">
                <el-button type="danger" slot="reference">清除 <i class="el-icon-remove-outline"></i></el-button>
              </el-popconfirm>
              <!-- <el-button type="danger" slot="reference">清除 <i class="el-icon-remove-outline"></i></el-button> -->
            </template>
          </el-table-column>
        </el-table>
        <div style="padding: 10px 0">
          <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
            :page-sizes="[2, 5, 10, 20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
            :total="total">
          </el-pagination>
        </div>

        <el-dialog title="学生信息" :visible.sync="dialogFormVisible" width="30%">
          <el-form label-width="80px" size="small">
            
            <!-- <el-form-item label="专业">
              <el-select v-model="mName" placeholder="计算机科学与技术">
                <el-option
                    v-for="item in majorOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
            </el-form-item> -->
            <el-form-item label="平时成绩" >
              <el-input v-model.number="form.performance" @input="get_total" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="考试成绩">
              <el-input v-model.number="form.exam" @input="get_total" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="总成绩">
              <el-input v-model.number="form.total_score" @focus="get_total" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="等级">
              <el-select v-model="form.grade" placeholder="优">
                <el-option
                    v-for="item in scoreOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <!-- <el-form-item label="年级">
              <el-select v-model="form.grade" placeholder="大一">
                <el-option
                    v-for="item in gradeOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
            </el-form-item> -->
            <!-- <el-form-item label="邮箱">
              <el-input v-model="form.email" autocomplete="off"></el-input>
            </el-form-item> -->
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
      formpost:{
          
          cname:"",
          sno:"",
          procScore:"",
          examScore:"",
          finalScore:"",
          scoreRank:""
      },
      total: 0,
      pageNum: 1,
      pageSize: 10,
      username: "",
      sno: "",
      cname:"",
      sname: "",
      grade: "",
      email: "",
      dialogFormVisible: false,
      multipleSelection: [],
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      headerBg: 'headerBg',
      form:{
        cname:0,
        sno:"",
        performance:100,
        exam:100,
        total_score:100,
        
        //MajorId: 1,
        
        grade:"优",
        
      },
      // majorOptions:[
      //   { label: '计算机科学与技术', value: '计算机科学与技术' },
      //   { label: '信息安全', value: '信息安全' },
      //   { label: '大数据科学', value: '大数据科学' }
      // ],
      scoreOptions:[
        { label: '优', value: '优' },
        { label: '良', value: '良' },
        { label: '中', value: '中' },
        { label: '及格', value: '及格' },
        { label: '不及格', value: '不及格' }
      ],
      // sexOptions:[
      //   { label: '男', value: '男' },
      //   { label: '女', value: '女' },
      // ],
      // gradeOptions:[
      //   { label: '大一', value: '大一' },
      //   { label: '大二', value: '大二' },
      //   { label: '大三', value: '大三' },
      //   { label: '大四', value: '大四' }
      // ],
      gradeConvert:
          {"优": 1,
            "良": 2,
            '中':3,
            "及格": 4,
            "不及格": 5},
      //mName: "计算机科学与技术",
      //form_type: "insert",
      //showNo : true





      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },
      value1: '',
      value2: '',
    



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
//     watch: {
//      form(newVal) {
//        this.form.total_score = Math.round((this.form.exam + this.form.performance)/2);
//        console.log('****')
//      }
  
//   },
  methods: {
    get_total() {
        this.form.total_score =  Math.round((this.form.exam + this.form.performance)/2) ;

        if(this.form.total_score>=0&&this.form.total_score<60){
          this.form.grade = "不及格"
        }
        else if (this.form.total_score>=60&&this.form.total_score<70){
          this.form.grade = "及格"
        }
        else if (this.form.total_score>=70&&this.form.total_score<80){
          this.form.grade = "中"
        }
        else if (this.form.total_score>=80&&this.form.total_score<90){
          this.form.grade = "良"
        }
        else if (this.form.total_score>=90&&this.form.total_score<=100){
          this.form.grade = "优"
        }
        else{
          this.form.grade = "错误"
        }

       // console.log(this.form)
      //   console.log(typeof this.form.exam)
      //   console.log(this.form.performance)
      //   console.log(this.form.total_score)
    },
    average() {
        return Math.round((this.form.exam + this.form.performance)/2) ;
        
    },

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
      this.form.sno = undefined
      this.form.cname = undefined
      // this.form.MajorId = 1
      // this.form.sname = ""
      // this.form.password = ""
      // this.form.sex = "男"
      // this.form.grade = "大一"
      // this.form.email = ""
      // this.form_type = "insert"
      this.form.performance = undefined
      this.form.exam = undefined
      this.form.total_score = undefined
        
      //this.form.MajorId = 1
        
      this.form.grade = "优"
    },
    load() {
      const token = JSON.parse(localStorage.getItem('token'))
      let tno = undefined;
       if(token)
       {
          //console.log(token)
          //console.log(token.userName.length)
          tno = token.userName;
        }
      this.request.get("/course/select_info_by_page_tno", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          CName: this.cname,
          Tno: tno
          //sname: this.sname,
          //grade: this.grade
        }
      }).then(res => {
         console.log(res)

        this.tableData = res
        console.log(this.tableData)
       

      })

    },
    save() {
      //this.form.MajorId = this.gradeConvert[this.mName]
      if(this.form.performance === undefined || this.form.performance.length === 0)
      {
        this.$notify.error({
          title: '错误',
          message: '平时成绩不可为空',
          position: 'top-center'
        });
      }
      else if(this.form.exam === undefined || this.form.exam.length === 0)
      {
        this.$notify.error({
          title: '错误',
          message: '考试成绩不可为空',
          position: 'top-center'
        });
      }
      else if(this.form.total_score === undefined ||this.form.total_score.length === 0)
      {
        this.$notify.error({
          title: '错误',
          message: '总成绩不可为空',
          position: 'top-center'
        });
      }
      else if(this.form.total_score <0 ||this.form.total_score >100 ||this.form.performance <0 ||this.form.performance >100||this.form.exam <0 ||this.form.exam >100)
      {
        this.$notify.error({
          title: '错误',
          message: '成绩在0-100之间',
          position: 'top-center'
        });
      }
      else if (typeof this.form.total_score !== 'number'||typeof this.form.performance !== 'number'||typeof this.form.exam !== 'number') {
          // 变量 pageSize 是一个数字
          // 在这里添加逻辑以处理数字类型的变量
          this.$notify.error({
          title: '错误',
          message: '成绩应该是一个数字',
          position: 'top-center'
        });
      }
      // else if(this.form.password === undefined || this.form.password.length === 0)
      // {
      //   this.$notify.error({
      //     title: '错误',
      //     message: '密码不可为空',
      //     position: 'top-center'
      //   });
      // }
      // else if(this.form.sno.length !== 7)
      // {
      //   this.$notify.error({
      //     title: '错误',
      //     message: '学号必须为7位',
      //     position: 'top-center'
      //   });
      // }
      // else if(!this.form.email.includes("@") || !this.form.email.includes("com"))
      // {
      //   this.$notify.error({
      //     title: '错误',
      //     message: '邮箱格式不符合规范',
      //     position: 'top-center'
      //   });
      // }
      // else if(this.form_type === "insert")
      // {
      //   this.request.post("/student/insert_one_student", this.form).then(res => {
      //     if (res) {
      //       this.$message.success("插入成功")
      //       this.dialogFormVisible = false
      //       this.load()
      //     } else {
      //       this.$message.error("插入失败")
      //     }
      //   })
      //   this.initial_form()
      // }
      else
      {
      //   console.log("formpost")
        
      const token = JSON.parse(localStorage.getItem('token'))
      let tno = undefined;
       if(token)
       {
          //console.log(token)
          //console.log(token.userName.length)
          tno = token.userName;
        }
        let formpost = {tno:tno, sno:this.form.sno, cid:this.form.cid, examScore:this.form.exam ,procScore:this.form.performance, scoreRank:this.form.grade, finalScore:this.form.total_score}
      console.log(formpost)
        this.request.post("/course/app_update_scores", [formpost]).then(res => {
          if (res) {
            this.$message.success("已提交管理员审核")
            
            this.load()
          } else {
            this.$message.error("提交审核失败")
          }
        })
        this.initial_form()
        this.dialogFormVisible = false
      }

    },
    handleAdd() {
      this.dialogFormVisible = true
      this.initial_form()
      // this.mName = "计算机科学与技术"
      // this.form_type = "insert"
      //this.showNo = true
    },
    handleEdit(row) {
      this.form.cname = row.cname
      this.form.sno = row.sno
      this.form.performance = row.procScore
      // this.form.MajorId = 1
      this.form.exam = row.examScore
      this.form.total_score = row.finalScore
      this.form.grade = row.scoreRank
      this.form.cid = row.cid
      //this.form_type = "update"
      //this.mName = row.majorName
      this.dialogFormVisible = true
      //this.showNo = false
    },
    del(row) {
      
      this.form.cname = row.cname
      this.form.sno = row.sno
      this.form.performance = ""
      this.form.exam = ""
      this.form.total_score = ""
      this.form.cid = row.cid
      //this.form.MajorId = 1
        
      this.form.grade = ""
      //console.log(this.form)
      this.save()
      //console.log("5555556666666")
      // this.request.delete("/student/" + id).then(res => {
      //   if (res) {
      //     this.$message.success("删除成功")
      //     this.load()
      //   } else {
      //     this.$message.error("删除失败")
      //   }
      // })
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    handleExport() {
      window.open("http://121.36.74.25:9090/student/export")
    },
    reset() {
      // this.sno = ""
      // this.sname = ""
      // this.grade = ""
      this.cname = ""

      this.load()
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
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

body {
  margin: 0;
}
.example-showcase .el-loading-mask {
  z-index: 9;
}


</style>

