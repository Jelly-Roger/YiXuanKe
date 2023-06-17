<template>
    <el-container style=" height: auto;background-color: rgb(240, 240, 240);margin-top:70px;margin-left:200px;">
        <div class="courselist">
            <div class="search">
                <h3 style="margin-left:20px;padding-top:20px">筛选条件</h3>
                <div style="padding-top:10px;padding-left: 40px;">
                    <span style="margin-right:20px;font-size: 1.5ch;">开设学院:</span>
                    <el-select size="medium" v-model="college_option"  placeholder="请选择">
                    <el-option v-for="item in this.college_options" :key="item.index" :label="item.value" :value="item.value"></el-option>
                    </el-select>

                    <span style="margin-left:30px;margin-right:10px ;font-size: 1.5ch;">课程类型:</span>
                    <el-select size="medium" v-model="course_type_option"  collapse-tags style="margin-left: 20px;" placeholder="请选择">
                    <el-option v-for="item in this.course_type_options" :key="item.index" :label="item.value" :value="item.value"></el-option>
                    </el-select>


                    <el-button type="primary" size="medium" style="margin-left:120px;" icon='el-icon-s-promotion' @click="Query">筛选</el-button>
                </div>
            </div>
            <div style="margin-left: 10px;margin-right: 10px;background-color: white;">
                <div style="margin-bottom:20px;padding-top:20px;margin-left:10px;">
                    <el-input placeholder="课程名-关键词搜索" v-model="search_input" clearable  style="width:300px" size="medium"></el-input>
                    <el-button type="primary" size="medium" style="margin-left:10px;" icon="el-icon-search" @click="KeySearch">搜索</el-button>
                </div>
                 <el-divider></el-divider>
                <el-table ref='allcoursetable' style=" font-size: 14px;" :data="tableData" @expand-change="getdetail"  max-height="400" stripe :row-style="{height:'65px',} ">
                <el-table-column type="expand" >
                    <el-form label-position="left" inline class="demo-table-expand" style="width:100%;margin-left:150px;">
                    <el-form-item label="课程号：">
                        <span>{{ this.Cid}}</span>
                    </el-form-item>
                    <el-form-item label="课程名称：">
                        <span>{{ this.CName }}</span>
                    </el-form-item>
                    <el-form-item label="开设学院：">
                        <span>{{ this.college }}</span>
                    </el-form-item>
                      <el-form-item label="课程教室：">
                        <span>{{ this.classroom }}</span>
                      </el-form-item>
                    <el-form-item label="学分：">
                        <span>{{ this.Ccredit }}</span>
                    </el-form-item>
                    <el-form-item label="课程类型：">
                        <span>{{ this.type}}</span>
                    </el-form-item>
                    <el-form-item label="排课安排：">
                        <span>{{ this.schedule }}</span>
                    </el-form-item>
                      <el-form-item label="教师工号：">
                        <span>{{ this.Tno }}</span>
                      </el-form-item>
                    <el-form-item label="任课教师：">
                        <span>{{ this.Tname }}</span>
                    </el-form-item>
                    <el-form-item label="选课情况：">
                        <span>{{ this.chosenCon }}</span>
                    </el-form-item>
                      <el-form-item label="课程简介：">
                        <span>{{ this.CourseDesc }}</span>
                      </el-form-item>
                    </el-form>
                </el-table-column>
                <el-table-column label="课程号" prop="Cid" width="100" align="center"></el-table-column>
                <el-table-column label="课程名称" prop="CName"  align="center"></el-table-column>
                <el-table-column label="开设学院" prop="college"  align="center"></el-table-column>
                  <el-table-column label="课程教室" prop="classroom"  align="center"></el-table-column>
                <el-table-column label="课程类型" prop="type" align="center"></el-table-column>
                <el-table-column label="选课情况" prop="chosenCon" width="200" align="center"></el-table-column>
            </el-table>
            </div>
      </div>
    </el-container>
</template>

<script>
export default {
  data() {
    return {
      tableData: [],
      college_options:[
        { index:'1', value:'电子与信息工程学院'},
        { index: '2',  value: '软件学院'},
        { index: '3',  value: '经济与管理学院'},
        { index: '4',  value: '艺术与传媒学院'},
        { index: '5',  value: '数学科学学院'},
      ],
      course_type_options: [
        { index: '1', value: '专业必修课'},
        { index: '2', value: '专业选修课'},
        { index: '3', value: '通识必修课'},
        { index: '4', value: '通识选修课'},
      ],
      college_option:'',
      course_type_option:'',

            last_row:{},
            Cid: null,
            CName: ' ',
            Ccredit: null,
            schedule: '',
            college: '',
            type: '',
            chosenCon: '',
            Tno:'',
            Tname: '',
            classroom: '',
            CourseDesc: '',
            search_input:"",
        }
    },
    methods:{
        getdetail(row,expented)
        {
            if(this.last_row!==row)
            {
                this.$refs.allcoursetable.toggleRowExpansion(this.last_row, false)
            }
            this.last_row=row
            if(expented){
                this.request.get("/course/find_all_course_by_id", {
                    params: {
                        Cid: row.Cid,
                    }
                }).then(res => {
                    this.Cid=res.Cid
                    this.CName=res.CName
                    this.college=res.college,
                    this.type= res.type
                    this.schedule=res.schedule
                    this.chosenCon=res.chosenCon
                    this.Tno=res.Tno
                    this.Tname = res.Tname
                    this.Ccredit=res.Ccredit
                    this.classroom = res.classroom
                    this.CourseDesc = res.CourseDesc
                })
            }
        },
        KeySearch()
        {
            this.request.get("/course/find_course_by_cname", {
                params: {
                    CName:this.search_input,
                }
            }).then(res => {
                this.tableData = res
            })
          this.search_input = ""
        },
        Query()
        {
            this.request.get("/course/find_course_by_college_type", {
                params: {
                    college:this.college_option,
                    type:this.course_type_option,
                }
            }).then(res => {
                this.tableData = res
            })
          this.college_option = ''
          this.course_type_option = ''

        },
    },
    mounted()
    {
        this.request.get("/course/find_all_course", {
            params: {
                
            }
        }).then(res => {
            this.tableData= res
        })
    }
}

</script>

<style>
.search
{
    height:120px;
    margin-top:20px;
    margin-left:10px;
    margin-right: 10px;
    margin-bottom:20px;
    background-color: white;
}
.courselist{
    width:100%;
}
      .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
</style>