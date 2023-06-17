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
          <el-button class="ml-5" type="primary" @click="select">搜索</el-button>
          <el-button type="warning" @click="reset">重置</el-button>
          <el-button type="success" @click="handleExport">导出</el-button>
        </div>


        <!-- <el-collapse accordion>
<el-collapse-item>
  <template slot="title">          一致性 Consistency<i class="header-icon el-icon-info"></i>
  </template>
  <div>与现实生活一致：与现实生活的流程、逻辑保持一致，遵循用户习惯的语言和概念；</div>
  <div>在界面中一致：所有的元素和结构需保持一致，比如：设计样式、图标和文本、元素的位置等。</div>
</el-collapse-item>

</el-collapse> -->


        <div style="margin-top: 20px; margin-bottom: 20px;width: 600px;margin-left: 30px;">
  <el-row :gutter="20">
    <el-col :span="6">
      <div>
        <el-statistic title="平均成绩" :value="value1" :precision="2">
          <template slot="formatter">  </template>
        </el-statistic>
      </div>
    </el-col>
    <el-col :span="6">
      <div>
        <el-statistic title="总学分" :value="value2" :precision="1">
          <template slot="formatter" >  </template>
        </el-statistic>
      </div>
    </el-col>
    <el-col :span="6">
      <div>
        <el-statistic title="不及格门数" :value="value3">
          <template slot="formatter" >  </template>
        </el-statistic>
      </div>
    </el-col>
    <el-col :span="6">
      <div>
        <el-statistic title="不及格学分" :value="value4" :precision="1">
          <template slot="formatter">  </template>
        </el-statistic>
      </div>
    </el-col>
  </el-row>

  


</div>


<!-- <div style="margin-left: 20px;margin-bottom: 20px;" >
          
          
          <el-rate
            v-model="value1"
            disabled
            show-score
            text-color="#ff9900"
            score-template="{value}">
            </el-rate>

        </div> -->


        <el-card class="box-card" shadow="hover">
<div slot="header" class="clearfix">
  <el-rate
            v-model="value1"
            disabled
            show-score
            text-color="#ff9900"
            score-template="{value}">
            </el-rate>
  
</div>
<div><el-table :data="tableData" stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
         
         <el-table-column prop="CName" label="课程"></el-table-column>
        
         <el-table-column prop="procScore" label="平时成绩" ></el-table-column>
         <el-table-column prop="examScore" label="考试成绩" ></el-table-column>
         <el-table-column prop="finalScore" label="总成绩" ></el-table-column>
         <el-table-column prop="scoreRank" label="总评" ></el-table-column>
<!--         <el-table-column label="操作" width="200" text-align="center">-->
<!--           <template slot-scope="scope">-->
<!--             <el-button type="success" @click="handleEdit(scope.row)">成绩复核 <i class="el-icon-edit"></i></el-button>-->
<!--            -->
<!--           </template>-->
<!--         </el-table-column>-->
       </el-table>
       <div style="padding: 10px 0">
         <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
           :page-sizes="[2, 5, 10, 20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
           :total="total">
         </el-pagination>
       </div>
</div>
</el-card>


        <!-- <el-table :data="tableData" stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
         
          <el-table-column prop="CName" label="课程"></el-table-column>
         
          <el-table-column prop="procScore" label="平时成绩" ></el-table-column>
          <el-table-column prop="examScore" label="考试成绩" ></el-table-column>
          <el-table-column prop="finalScore" label="总成绩" ></el-table-column>
          <el-table-column prop="scoreRank" label="总评" ></el-table-column>
          <el-table-column label="操作" width="200" text-align="center">
            <template slot-scope="scope">
              <el-button type="success" @click="handleEdit(scope.row)">成绩复核 <i class="el-icon-edit"></i></el-button>
             
            </template>
          </el-table-column>
        </el-table>
        <div style="padding: 10px 0">
          <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
            :page-sizes="[2, 5, 10, 20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
            :total="total">
          </el-pagination>
        </div> -->


        


        <el-dialog title="成绩复核" :visible.sync="dialogFormVisible" width="30%">
          <el-form label-width="80px" size="small">
            
              <el-form ref="form" :model="form" label-width="80px">
<!-- <el-form-item label="申请">
  <el-input v-model="form.name"></el-input>
</el-form-item> -->
<el-form-item label="申请部分">
  <el-select v-model="form.region" placeholder="请选择申请的部分">
    <el-option label="平时成绩" value="procScore"></el-option>
    <el-option label="考试成绩" value="examScore"></el-option>
    <el-option label="总成绩" value="finalScore"></el-option>
    <el-option label="总评" value="scoreRank"></el-option>
  </el-select>
</el-form-item>
<el-form-item label="考试时间">
  <el-col :span="11">
    <el-date-picker type="date" placeholder="选择日期" v-model="form.date1" style="width: 100%;"></el-date-picker>
  </el-col>
  <!-- <el-col class="line" :span="2">-</el-col>
  <el-col :span="11">
    <el-time-picker placeholder="选择时间" v-model="form.date2" style="width: 100%;"></el-time-picker>
  </el-col> -->
</el-form-item>
<el-form-item label="查看原卷">
  <el-switch v-model="form.watchpaper"></el-switch>
</el-form-item>
<!-- <el-form-item label="活动性质">
  <el-checkbox-group v-model="form.type">
    <el-checkbox label="美食/餐厅线上活动" name="type"></el-checkbox>
    <el-checkbox label="地推活动" name="type"></el-checkbox>
    <el-checkbox label="线下主题活动" name="type"></el-checkbox>
    <el-checkbox label="单纯品牌曝光" name="type"></el-checkbox>
  </el-checkbox-group>
</el-form-item> -->
<el-form-item label="重新阅卷">
  <el-radio-group v-model="form.remark">
    <el-radio label="是"></el-radio>
    <el-radio label="否"></el-radio>
  </el-radio-group>
</el-form-item>
<el-form-item label="理由">
  <el-input type="textarea" v-model="form.reason"></el-input>
</el-form-item>
<el-form-item>
  <el-button type="primary" @click="onSubmit">申请</el-button>
  <el-button>取消</el-button>
</el-form-item>
</el-form>
          </el-form>
          
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
      tableData_pre:[],
      formpost:{
          
          cname:"",
          sno:"",
          procScore:"",
          examScore:"",
          finalScore:"",
          scoreRank:""
      },
      like: true,
      value1: 0,
      value2: 0,
      
      value3: 0,
      value4: 0,
      total: 0,
      pageNum: 1,
      pageSize: 10,
      username: "",
      sno: "",
      cname:"",
      sname: "",
      value:"",
      grade: "",
      email: "",
      dialogFormVisible: false,
      multipleSelection: [],
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      headerBg: 'headerBg',
      form: {
        sno:"",
        performance:"",
        exam:"",
        total_score:"",
        grade:"",
        cid:0,
        cname:"",
        region: "",
        date1: "",
        watchpaper: false,
        remark: "",
        reason: ""
      }
      // majorOptions:[
      //   { label: '计算机科学与技术', value: '计算机科学与技术' },
      //   { label: '信息安全', value: '信息安全' },
      //   { label: '大数据科学', value: '大数据科学' }
      // ],
      
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
      
      //mName: "计算机科学与技术",
      //form_type: "insert",
      //showNo : true





    



    }
    

  },
  created() {
    // 请求分页查询数据
    this.load()
    this.initial_form()
    this.getscore()
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
      onSubmit() {
      
          
          save()
          this.dialogFormVisible = false
    },
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
      this.form.sno="",
      this.form.performance="",
      this.form.exam="",
      this.form.total_score="",
      this.form.grade="",
      this.form.cid=0,
      
      this.form.cname="",
      this.form.region= "",
      this.form.date1= "",
      this.form.watchpaper= false,
      this.form.remark= "",
      this.form.reason= ""
    },
    load() {
      const token = JSON.parse(localStorage.getItem('token'))
      let sno = undefined;
       if(token)
       {
          //console.log(token)
          //console.log(token.userName.length)
          sno = token.userName;
        }
      this.request.get("/course/score_detail", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          //CName: this.cname,
          Sno: sno
          //sname: this.sname,
          //grade: this.grade
        }
      }).then(res => {
         console.log(res)

        this.tableData_pre = res
        this.tableData = this.tableData_pre
        console.log(this.tableData)
      })

      // console.log(this.tableData)
      // console.log("test")

    },
    getscore() {
      const token = JSON.parse(localStorage.getItem('token'))
      let sno = undefined;
       if(token)
       {
          //console.log(token)
          //console.log(token.userName.length)
          sno = token.userName;
        }
        console.log(sno)
      this.request.get("/course/score_info", {
        params: {
          //pageNum: this.pageNum,
          //pageSize: this.pageSize, 
          //CName: this.cname,
          Sno: sno
          //sname: this.sname,
          //grade: this.grade
        }
      }).then(res => {
         this.value1 = res.gpa
         this.value2 = res.credit
         this.value3 = res.failedNum
         this.value4 = res.failedCredit
      })
    },

    select(){
      if(this.cname===""){
          this.tableData = this.tableData_pre

      }
      else{
          let flag =0
          console.log(this.tableData_pre)
          // console.log(this.tableData_pre[0].cName)
          for(var Data in this.tableData_pre){

              if(this.tableData_pre[Data].CName === this.cname){
                  console.log('success')
                  this.tableData = [this.tableData_pre[Data]]
                  flag = 1
              }

          }
          console.log(flag)
          if(flag===0){
              this.tableData = []
          }

      }
    },
    save() {
      //this.form.MajorId = this.gradeConvert[this.mName]
      // if(this.form.performance === undefined || this.form.performance.length === 0)
      // {
      //   this.$notify.error({
      //     title: '错误',
      //     message: '平时成绩不可为空',
      //     position: 'top-center'
      //   });
      // }
      // else if(this.form.exam === undefined || this.form.exam.length === 0)
      // {
      //   this.$notify.error({
      //     title: '错误',
      //     message: '考试成绩不可为空',
      //     position: 'top-center'
      //   });
      // }
      // else if(this.form.total_score === undefined ||this.form.total_score.length === 0)
      // {
      //   this.$notify.error({
      //     title: '错误',
      //     message: '总成绩不可为空',
      //     position: 'top-center'
      //   });
      // }
      // else if(this.form.total_score <0 ||this.form.total_score >100 ||this.form.performance <0 ||this.form.performance >100||this.form.exam <0 ||this.form.exam >100)
      // {
      //   this.$notify.error({
      //     title: '错误',
      //     message: '成绩在0-100之间',
      //     position: 'top-center'
      //   });
      // }
      // else
      {
      //   console.log("formpost")
        
      const token = JSON.parse(localStorage.getItem('token'))
      let sno = undefined;
       if(token)
       {
          //console.log(token)
          //console.log(token.userName.length)
          sno = token.userName;
          this.form.sno = token.userName;
        }
        
        //let formpost = {sno:sno, cid:this.form.cid, examScore:this.form.exam ,procScore:this.form.performance, scoreRank:this.form.grade, finalScore:this.form.total_score}
        console.log(this.form);
        this.request.post("/course/app_update_scores", [this.form]).then(res => {
          if (res) {
            this.$message.success("更新成功")
            
            this.load()
          } else {
            this.$message.error("更新失败")
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
      this.form.performance = row.procScore
      this.form.exam = row.examScore
      this.form.total_score = row.finalScore
      this.form.grade = row.scoreRank
      this.form.cid = row.cid
      const token = JSON.parse(localStorage.getItem('token'))
      
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

      const token = JSON.parse(localStorage.getItem('token'))
      let sno = undefined;
      if(token)
      {
        //console.log(token)
        //console.log(token.userName.length)
        sno = token.userName;
      }

      console.log(sno)
      let url = "http://121.36.74.25:9090/course/export_personal_grade?Sno=" + sno
      window.open(url)

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


















<style >
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
.like {
  cursor: pointer;
  font-size: 25px;
  display: inline-block;
}
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.box-card {
  width: 1150px;
  margin-left: 30px;
}
</style>




