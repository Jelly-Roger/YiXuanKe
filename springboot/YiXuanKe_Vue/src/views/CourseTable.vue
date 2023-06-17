<template>
    <el-container style=" height: auto;background-color: rgb(230, 230, 230);margin-top:70px;margin-left:200px;">
        <div class="course" >
            <StuCourseTable style="width:90%;z-index: 2;" :courseList="courseList" ref="course"></StuCourseTable>
        </div>
    </el-container>
</template>

<script>
import StuCourseTable from '@/components/StuCourseTable.vue';

export default {
    components: {
        StuCourseTable,
    },
    data() {
        return {
            courseList: [
                ['方钰(06097) 操作系统课程设计(10043601) [2-16双] 学院教室', '方钰(06097) 操作系统课程设计(10043601) [2-16双] 学院教室', '赵君峤(13182),叶晨(03180),张光长(91933) 软件工程(10102302) [1-17单] 博楼B314', '赵君峤(13182),叶晨(03180),张光长(91933) 软件工程(10102302) [1-17单] 博楼B314', '李洁(10001) 机器学习(10023701) [1-5, 11, 13-17] 广楼G305', '李洁(10001) 机器学习(10023701) [1-5, 11, 13-17] 广楼G305', '', '', '', '陆有军(93419) 计算机网络(10106202) [1-17] 复楼F211', '陆有军(93419) 计算机网络(10106202) [1-17] 复楼F211', '陆有军(93419) 计算机网络(10106202) [1-17] 复楼F211'],
                ['', '', '程大伟(21008) 人机交互导论(10023401) [1-17] 复楼F211', '程大伟(21008) 人机交互导论(10023401) [1-17] 复楼F211', '', '', '', '', '', '', '', ''],
                ['', '', '', '', '赵君峤(13182),叶晨(03180),张光长(91933) 软件工程(10102302) [1-17] 博楼B314', '赵君峤(13182),叶晨(03180),张光长(91933) 软件工程(10102302) [1-17] 博楼B314', '', '', '', '', '', ''],
                ['张文佳(19098) 体育(4)(32000480)[1 - 17] 体育中心游泳馆', '张文佳(19098) 体育(4)(32000480)[1 - 17] 体育中心游泳馆', '', '', '', '', '', '', '', '', '', ''],
                ['', '', '郭玉臣(99783) 计算机系统实验(10022502) [1-17] 安楼A314', '郭玉臣(99783) 计算机系统实验(10022502) [1-17] 安楼A314', '', '', '', '', '', '丁志军(08154) 编译原理课程设计(10031202) [2-16双] 学院教室', '丁志军(08154) 编译原理课程设计(10031202) [2-16双] 学院教室', ''],
                ['', '', '', '', '', '', '', '', '', '李文根(19034) 数据库系统原理课程设计(10043702) [1-17单] 学院教室', '李文根(19034) 数据库系统原理课程设计(10043702) [1-17单] 学院教室', ''],
                ['', '', '', '', '', '', '', '', '', '', '', ''],
            ],
            userid:"",
        }
    },

    methods: {
        
    },

    mounted() {
        const token = JSON.parse(localStorage.getItem('token'))
        this.userid=token.userName
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
                    if(courseScheMat[i][j]==-1)
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
            this.$refs.course.change();
        })
    },

}
</script>

<style>
.course{
    width:100%;
    margin-left:10%

}
</style>
