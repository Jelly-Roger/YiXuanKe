<template>
    <div>
        <div class="bg-cover" v-if="showCover"></div>
        <el-container style="width: 90%;background-color: rgb(230, 230, 230);margin-left:100px;" v-if="!showCover">
            <el-main>
            <div class="tip" v-show="!showselect">
                <h1>还没有对你开放的选课</h1>
            </div>
            <div class="course" v-show="showselect">
                <div style="display: flex; justify-content:space-between; margin-right: 200px;">
                    <div class="select">

                        <div class="buttom1">
                          <div style=" width:40%"><span style="margin-left:30px;"> 备选课程</span></div>
                          <div><el-button style="font-size:80%;margin-top: 5px;" @click="select">选择课程</el-button></div>
                          <div><el-button style="font-size:80%;margin-top: 5px;" @click="deleteCurriculum">删除已选课程</el-button></div>
                          <div><el-button style="margin-top: 5px;" @click="saveCurriculumTable">保存课表</el-button></div>
                        </div>
                        <el-divider></el-divider>
                        <div style="margin-top: -23px;">
                            <el-table
                                ref="table1"
                                :show-header=false
                                :data="results"

                                style="width: 100%"
                                max-height="300" @row-click="handleSelect" highlight-current-row stripe>
                                <el-table-column label="课号" prop="id">
                                </el-table-column>
                                <el-table-column label="课程名称" prop="name">
                                </el-table-column>
                                <el-table-column label="是否保存" prop="save">
                                </el-table-column>
                                <el-table-column align="right">
                                    <template slot-scope="scope">
                                        <el-button size="mini" type="danger"
                                            @click="handleDelete(scope.$index, scope.row)">删除
                                        </el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </div>
                    </div>
                    <div class="select">
                        <div style="margin-top: 27px; margin-left: 20px;"><span>详细信息</span></div>
                        <div><el-divider></el-divider></div>
                        <div class="information_box" style="margin-top: -20px;">
                            <el-table
                                :show-header=true
                                :data="details"
                                style="width: 100%"
                                max-height="300">
                                <el-table-column label="课号" prop="id">
                                </el-table-column>
                                <el-table-column label="课程名称" prop="name">
                                </el-table-column>
                                <el-table-column label="上课时间" prop="time">
                                </el-table-column>
                                <el-table-column label="上课老师" prop="tname">
                                </el-table-column>
                                <el-table-column label="已选人数" prop="chosenCon">
                                </el-table-column>
                                <el-table-column label="详细信息" prop="CourseDesc">
                                </el-table-column>

                            </el-table>
                        </div>
                    </div>
                </div>
                <StuCourseTable style="width:90%;z-index: 2;" :courseList="courseList" ref="course2"></StuCourseTable>
            </div>

            </el-main>
        </el-container>
        <courseList ref="child" :fiter="fiter" :tableData="tableData"
                    :already_select_list_CID="already_select_list_CID"
                    :has_choosen="has_choosen"
                    class="box" v-if="showCover"
            @selectFinish="selectFinish" @finish="finish"></courseList>
        <el-dialog :visible.sync="outerVisible" title="退课列表">
          <el-table ref="multipleTable"  :data="show_has_chosen"  tooltip-effect="dark"  style="width: 100%"
              @selection-change="handleSelectionChange">
            <el-table-column
                type="selection"
                width="55">
            </el-table-column>
            <el-table-column
                label="课号"
                width="120">
              <template slot-scope="scope">{{ scope.row.Cid }}</template>
            </el-table-column>
            <el-table-column
                prop="CName"
                label="课程名称"
                width="120">
            </el-table-column>
            <el-table-column
                prop="type"
                label="课程类型"
                show-overflow-tooltip>
              <template slot-scope="scope">
                <el-tag :type="scope.row.type === fiter[0].text ?
                            (scope.row.type === fiter[1].text ? 'warning' : 'info')
                            :(scope.row.type === fiter[2].text ? 'danger' : 'success')"
                        disable-transitions>{{scope.row.type}}</el-tag>
              </template>
            </el-table-column>
            <el-table-column
                prop="chosenCon"
                label="选课人数"
                width="120">
            </el-table-column>

            <el-table-column
                prop="classroom"
                label="教室"
                width="120">
            </el-table-column>

          </el-table>
          <div slot="footer" class="dialog-footer">
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                background layout="total,sizes,prev,pager,next,jumper"
                :total="total"
                :page-size="10"
                :page-sizes="[10,20,30,40,50,100]"
                style="text-align: center; margin-top: 15px;">
            </el-pagination>
            <el-button @click="deleteCourseList">确 定</el-button>
          </div>
        </el-dialog>
    </div>
</template>

<script>
import StuCourseTable from '@/components/StuCourseTable.vue';
import courseList from '@/components/courseList.vue'

export default {
    components: {
        StuCourseTable,
        courseList,
    },
    data() {
        return {
            pSize:10,
            page:1,
            total:0,
            outerVisible:false,
            already_select_list_CID:[],
            courseList: [
                ['方钰(06097) 操作系统课程设计(10043601) [2-16双] 学院教室', '方钰(06097) 操作系统课程设计(10043601) [2-16双] 学院教室', '赵君峤(13182),叶晨(03180),张光长(91933) 软件工程(10102302) [1-17单] 博楼B314', '赵君峤(13182),叶晨(03180),张光长(91933) 软件工程(10102302) [1-17单] 博楼B314', '李洁(10001) 机器学习(10023701) [1-5, 11, 13-17] 广楼G305', '李洁(10001) 机器学习(10023701) [1-5, 11, 13-17] 广楼G305', '', '', '', '陆有军(93419) 计算机网络(10106202) [1-17] 复楼F211', '陆有军(93419) 计算机网络(10106202) [1-17] 复楼F211', '陆有军(93419) 计算机网络(10106202) [1-17] 复楼F211'],
                ['', '', '程大伟(21008) 人机交互导论(10023401) [1-17] 复楼F211', '程大伟(21008) 人机交互导论(10023401) [1-17] 复楼F211', '', '', '', '', '', '', '', ''],
                ['', '', '', '', '赵君峤(13182),叶晨(03180),张光长(91933) 软件工程(10102302) [1-17] 博楼B314', '赵君峤(13182),叶晨(03180),张光长(91933) 软件工程(10102302) [1-17] 博楼B314', '', '', '', '', '', ''],
                ['张文佳(19098) 体育(4)(32000480)[1 - 17] 体育中心游泳馆', '张文佳(19098) 体育(4)(32000480)[1 - 17] 体育中心游泳馆', '', '', '', '', '', '', '', '', '', ''],
                ['', '', '郭玉臣(99783) 计算机系统实验(10022502) [1-17] 安楼A314', '郭玉臣(99783) 计算机系统实验(10022502) [1-17] 安楼A314', '', '', '', '', '', '丁志军(08154) 编译原理课程设计(10031202) [2-16双] 学院教室', '丁志军(08154) 编译原理课程设计(10031202) [2-16双] 学院教室', ''],
                ['', '', '', '', '', '', '', '', '', '李文根(19034) 数据库系统原理课程设计(10043702) [1-17单] 学院教室', '李文根(19034) 数据库系统原理课程设计(10043702) [1-17单] 学院教室', ''],
                ['', '', '', '', '', '', '', '', '', '', '', ''],
            ],
            showselect: true,
            showCover: false,
            tableData: [],
            fiter: [{ text: '专业选修课', value: '专业选修课' }, { text: '专业必修课', value: '专业必修课' },
              { text: '通识必修课', value: '通识必修课' },{ text: '通识选修课', value: '通识选修课' }], // 目前只支持到双选
            results: [],
            details:[],
            has_choosen:[],
            show_has_chosen:[],
            multipleSelection: [],
        }
    },

    methods: {
        selectRow(row) {
            if (row) {
                this.$refs.table1.setCurrentRow(row, true);
            } else {
                this.$refs.table1.setCurrentRow();  // 取消高亮
            }
        },
        finish(){
            this.showCover = false;
            document.body.style.overflow = "visible";
            this.$router.go(0);
        },
        selectFinish(val,already_select_list_CID) {
            val.forEach(row => {
                if(!row.include){
                    this.results.push({
                        id : row.Cid,
                        name : row.CName,
                        time:row.schedule,
                        location:row.classroom,
                        tname:row.Tname,
                        CourseDesc: row.CourseDesc,
                        chosenCon:row.chosenCon,
                        save : "未保存"
                    });
                    row.include = true;
                    already_select_list_CID.push(row.Cid);
                }
            });
            console.log(already_select_list_CID);

            //alert(this.results.length);
            this.showCover = false;
            document.body.style.overflow = "visible";
            // this.$router.go(0);
        },
        select() {
            this.showCover = true;
            document.body.style.overflow = "hidden";
        },

        getDataList(index,pageSize) {
          var temp_show_has_chosen_cid = []
          var start = 0;
          //console.log("当前页,页大小：",index,pageSize);
          //console.log("当前total：",this.total);
          for(start = index * pageSize - pageSize;start < index * pageSize && start < this.total;start++) {
            //console.log("当前 start",start);
            temp_show_has_chosen_cid.push(this.has_choosen[start]);
          }
          console.log(temp_show_has_chosen_cid);
          var requestStr = "Cid="
          for(var i = 0; i < temp_show_has_chosen_cid.length; i++)
          {
            if(i == 0)
              requestStr += temp_show_has_chosen_cid[i]
            else
              requestStr += ","+temp_show_has_chosen_cid[i]
          }
          this.request.get("/course/find_course_by_cid?" + requestStr).then(res=>{
            this.show_has_chosen = res;
          });
          //console.log(this.show_has_chosen);
        },
        handleSizeChange(val) {
          console.log('每页' + val + ' 条');
          this.pSize = val;
          this.page = 1;
          this.getDataList(1, val);
        },
        handleCurrentChange(val) {
          this.page = val;
          console.log('当前页: ' + val);
          this.getDataList(val, this.pSize);
        },
        handleDelete(index, row) {
            //console.log(index, row);
/*            this.tableData.forEach(
                e =>{
                    if(e.Cid == row.id)
                        e.include = false;
                }
            );*/
          for(var i=0;i<this.already_select_list_CID.length;i++){
            if(this.already_select_list_CID[i] === row.id){
              this.already_select_list_CID.splice(i,1);
              break;
            }
          }
          this.results.splice(index,1);
        },
        handleSelect(row, event, column){
            this.selectRow(row);
            this.details = [];
            this.details.push(row);
        },
      // {cid: , list；}
        saveCurriculumTable(){
          const token = JSON.parse(localStorage.getItem('token'))
          if(token)
          {
            console.log(token)
            console.log(token.userName.length)
          }

          var post_list = [];
          console.log(this.results);
          for(var i = 0;i < this.results.length;i++){
            if(this.results[i].save === "未保存"){
              this.results[i].save = "已保存";
              post_list.push(this.results[i].id);
            }
          }
/*          for(var i = 0;i<this.already_select_list_CID.length;i++){
            if(this.results[this.already_select_list_CID[i]].save === "未保存"){
              post_list.push(this.already_select_list_CID[i]);
              this.results[this.already_select_list_CID[i]].save = "已保存";
            }
          }*/



          this.request.post("/course/submit_course_select",{
            sno:token.userName,
            cidList: post_list,
          }).then(res =>{
            console.log(res);
            var msg = "";
            for(var item in res){
              msg += "课号："+ item + "，结果：" + res[item] + "\n";
            }

            if(msg === ""){
              msg = "没有课程可以保存\n";
            }
            this.$alert(msg, '选课结果', {
              confirmButtonText: '确定',
              callback: action => {
                this.$message({
                  type: 'info',
                  message: `信息: ${ action }`
                });
                this.$router.go(0);
              }
            });

          });
        },
        deleteCurriculum(){
          this.getDataList(1,this.pSize);
          this.outerVisible = true;
        },
        deleteCourseList(){
          this.outerVisible = false;
          const token = JSON.parse(localStorage.getItem('token'))
          if(token)
          {
            console.log(token)
            console.log(token.userName.length)
          }

          console.log(this.multipleSelection);
          var post_list = [];
          for(var item in this.multipleSelection){
            post_list.push(this.multipleSelection[item].Cid);
          }
          console.log(post_list);
          this.request.post("/course/delete_course_select",{
              sno:token.userName,
              cidList:post_list,
          }).then(res =>{
            console.log(res);
            var msg = "";
            for(var item in res){
              msg += "课号："+ item + "，结果：" + res[item] + "\n";
            }

            if(msg === ""){
              msg = "没有课程可以保存\n";
            }
            this.$alert(msg, '选课结果', {
              confirmButtonText: '确定',
              callback: action => {
                this.$message({
                  type: 'info',
                  message: `信息: ${ action }`
                });
                this.$router.go(0);
              }
            });

          })
        },
        handleSelectionChange(val) {
          this.multipleSelection = val;

        },
    },
  mounted() {
    const token = JSON.parse(localStorage.getItem('token'))
    this.userid=token.userName;
    this.request.get("/course/course_schedule", {
      params: {
        Sno: this.userid
      }
    }).then(res => {
      console.log(res.courseInfoList)
      var courseInfoList=res.courseInfoList
      var courseScheMat=res.courseScheMat
      console.log(courseInfoList)
      for(var i=0;i< res.courseScheMat.length;i++)
      {
        for (var j = 0; j < res.courseScheMat[0].length; j++)
        {
          if(courseScheMat[i][j] == -1)
            this.courseList[i][j] =""
          else{
            var idx= courseScheMat[i][j]
            console.log(idx)
            var str= courseInfoList[idx]["Tname"]+" "+ courseInfoList[idx]["CName"]+"("+ courseInfoList[idx]["Cid"]+") " +courseInfoList[idx]["classroom"]
            console.log(str)
            this.courseList[i][j] =str
            console.log(this.courseList)
          }
        }
      }
      this.$refs.course2.change();
    });


    this.request.get("/course/find_cid_by_sno",{
      params:{
        Sno: token.userName,
      }
    }).then(res =>{
      console.log(res)
      this.has_choosen = res;
      console.log(this.has_choosen);
      this.total = this.has_choosen.length;

    });

  },
}
</script>

<style>
.select {
    margin-top: 100px;
    width: 500px;
    height: 400px;
    border-color: aqua;
    background-color: white;
    border-width: 1px;
    border-style: solid;
}

.course {
    width: 100%;
    margin-left: 10%;

}

.buttom1 {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
    margin-right: 20px;

}

.tip {
    margin-top: 10%;
}

.tip>h1 {
    color: darkgray;
    width: 300px;
    font-size: 20px;
}

.bg-cover {
    width: 100%;
    height: 100%;
    position: fixed;
    top: 0;
    left: 0;
    background-color: rgba(0, 0, 0, 0.8);
    z-index: 98;
}

.box {
    z-index: 99;
}
.el-table__body tr.current-row>td {
    background: #FFFACD !important;
}

</style>
