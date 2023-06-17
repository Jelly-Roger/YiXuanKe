<template>
  <el-container style="margin-left:200px;margin-top:80px;">

    <el-container>

      <el-main>



        <el-table :data="tableData" stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">

          <el-table-column prop="id" label="申请ID"></el-table-column>

          <el-table-column prop="type" label="申请类型"></el-table-column>
          <el-table-column prop="status" label="申请状态" ></el-table-column>
          <el-table-column prop="submitTime" label="提交时间" >
            <template slot-scope="scope">
              {{ new Date(scope.row.submitTime).toLocaleDateString() }}
            </template>
          </el-table-column>

          <el-table-column label="查看详情" width="200" text-align="center">
            <template slot-scope="scope">
              <el-button type="success" @click="open_info(scope.row)" style="background-color:rgb(111, 209, 255)">查看 <i class="el-icon-edit"></i></el-button>
            </template>
          </el-table-column>


          <el-table-column label="操作" width="200" text-align="center">
            <template slot-scope="scope">
              <el-button type="success" @click="agree1(scope.row)" v-show="scope.row.status === '审核中'">同意 <i class="el-icon-edit"></i></el-button>
              <el-popconfirm class="ml-5" confirm-button-text='确定' cancel-button-text='我再想想' icon="el-icon-info"
                             icon-color="red" title="您确定拒绝吗？" @confirm="refuse1(scope.row)">
                <el-button type="danger" slot="reference" v-show="scope.row.status === '审核中'">拒绝 <i class="el-icon-remove-outline"></i></el-button>
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
      sid:undefined,
      mid:undefined,
      type:undefined,
      aid:undefined,
      total: 0,
      pageNum: 1,
      pageSize: 10,
      username: "",
      sno: "",
      cname:"",
      sname: "",
      grade: "",
      email: "",
      agree:0,
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




    }


  },
  created() {
    // 请求分页查询数据
    const token = JSON.parse(localStorage.getItem('token'))
    let mno = undefined;
    if(token)
    {
      //console.log(token)
      //console.log(token.userName.length)
      mno = token.userName;
    }
    this.mid = mno

    this.load()
    this.initial_form()
  },
  components: {
    Aside,
    Header,
  },

  methods: {
    open_info(row) {
      this.request.get("/appcheck/getDetailAppCheck", {
        params: {
          appID: row.id
        }
      }).then(res => {

        this.$alert(`<div style="text-align: center;">老师工号：${res.tno}<br>学生学号：${res.sno}<br>课程ID：${res.cid}<br>平时成绩：${res.procScore}<br>
        考试成绩：${res.examScore}<br>最终成绩：${res.finalScore}<br>等第：${res.scoreRank}</div>`, '申请信息', {
          confirmButtonText: '确定',
          dangerouslyUseHTMLString: true,
          callback: action => {

          }
        });

      })

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

      this.form.performance = undefined
      this.form.exam = undefined
      this.form.total_score = undefined


      this.form.grade = "优"
    },
    load() {

      this.request.get("/appcheck/getByPage", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          mid: this.mid,

          //sname: this.sname,
          //grade: this.grade
        }
      }).then(res => {
        console.log(res)

        this.tableData = res.data
        this.total = res.total
        console.log(this.tableData)


      })

      //this.tableData = [{type:"aksd",id:452, aid:"2052643",mid:"kk",status:"hbx",audit_time:"dasan",submit_time:"U",is_approved:0},{type:"aksj",id:515, aid:"2052364",mid:"ll",status:"lw",audit_time:"dasi",submit_time:"良",is_approved:1}]


    },
    save() {

      {

        let formpost = {mid:this.mid, id:this.sid, isApproved:this.agree, type:'成绩修改', aid:this.aid}
        console.log(formpost)
        this.request.get("/appcheck/updateStatus", {
          params:{
            mid:this.mid, id:this.sid, isApproved:this.agree, type:'成绩修改', aid:this.aid
          }}).then(res => {
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
    },
    agree1(row) {
      this.sid = row.id
      this.agree = 1
      this.type = row.type
      this.aid = row.aid

      this.save()
    },
    refuse1(row) {
      this.sid = row.id
      this.agree = 0
      this.type = row.type
      this.aid = row.aid
      this.save()

    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
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

