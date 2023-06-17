<<template>
  <el-aside :width="sideWidth + 'px'" style="box-shadow: 2px 0 6px rgb(0 21 41 / 35%); height:100%">
    <el-menu :default-openeds="['1', '2',]" style="min-height: 100%; overflow-x: hidden"
             background-color='rgb(255,255,255)' text-color="rgb(136,163,201)" active-text-color="#ffd04b" :collapse-transition="false"
             :collapse="isCollapse">
      <div style="height: 60px; line-height: 60px; text-align: center">
        <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; right: 5px">
        <b style="color: white">导航</b>
      </div>
      <el-submenu index="1" v-show="isAdmin">
        <template slot="title">
          <i class="el-icon-message"></i>
          <span slot="title">管理员管理</span>
        </template>
        <el-menu-item index="1-1" @click="ToManagerStudent">学生管理</el-menu-item>
        <el-menu-item index="1-2" @click="ToManagerTeacher">教师管理</el-menu-item>
        <el-menu-item index="1-3" @click="ToManagerCourse">课程管理</el-menu-item>
        <el-menu-item index="1-3" @click="ToManagerCheck">审核管理</el-menu-item>
      </el-submenu>
      <el-submenu index="2" v-show="isStudent">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span slot="title">课程管理</span>
        </template>
        <el-menu-item index="2-1" @click="MyTable">我的课表</el-menu-item>
        <el-menu-item index="2-2" @click="ALLTable">全校课表</el-menu-item>
        <el-menu-item index="2-3" @click="SelectCourse">我的选课</el-menu-item>
        <el-menu-item index="2-4" @click="viewScore">查看成绩</el-menu-item>
      </el-submenu>
      <el-submenu index="3" v-show="isTeacher">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span slot="title">课程管理</span>
        </template>
        <el-menu-item index="3-1" @click="ScoreRecord">成绩登记</el-menu-item>
        <!-- <el-menu-item index="3-2" @click="ALLTable">全校课表</el-menu-item>
        <el-menu-item index="3-3" @click="SelectCourse">我的选课</el-menu-item> -->
      </el-submenu>
      <el-submenu index="4">
        <template slot="title">
          <i class="el-icon-setting"></i>
          <span slot="title">我的信息</span>
        </template>
        <el-submenu index="4-4">
          <template slot="title">消息通知</template>
          <el-menu-item index="4-4-1" @click="messageCenter">通知中心</el-menu-item>
          <el-menu-item index="4-4-2" @click="questionSquare">问题广场</el-menu-item>
        </el-submenu>
      </el-submenu>
    </el-menu>
  </el-aside>
</template>

<script>
export default {
  name: "Aside",
  props:{
    isCollapse: Boolean,
    logoTextShow:Boolean,
    sideWidth:Number,
  },
  data(){
    return{
      route:"",
      isAdmin:false,
      isCollapse:false,
      isStudent:false,
      isTeacher:false
    }
  },
  methods:{
    ToManagerStudent() {
      this.route = ['管理员管理', '学生管理']
      this.$emit('GetRoute', this.route)
      this.$router.push("/manage_student");
    },
    ToManagerTeacher() {
      this.route = ['管理员管理', '教师管理']
      this.$emit('GetRoute', this.route)
      this.$router.push("/manage_teacher");
    },
    ToManagerCourse() {
      this.route = ['管理员管理', '课程管理']
      this.$emit('GetRoute', this.route)
      this.$router.push("/manage_course");
    },
    ToManagerCheck() {
      this.route = ['管理员管理', '审核管理']
      this.$emit('GetRoute', this.route)
      this.$router.push("/check");
    },
    MyTable() {
      this.route = ['课程管理', '我的课表']
      this.$emit('GetRoute', this.route)
      this.$router.push("/CourseTable");
    },
    ALLTable()
    {
      this.route = ['课程管理', '全校课表']
      this.$emit('GetRoute', this.route)
      this.$router.push("/AllCourse");
    },
    SelectCourse() {
      this.route = ['课程管理', '我的选课']
      this.$emit('GetRoute', this.route)
      this.$router.push("/SelectCourse");
    },
    viewScore() {
      this.route = ['课程管理', '查看成绩']
      this.$emit('GetRoute', this.route)
      this.$router.push("/view_score");
    },
    messageCenter(){
      this.route = ['消息通知', '通知中心']
      this.$emit("GetRoute",this.route)
      this.$router.push("/messageCenter")
    },
    ScoreRecord() {
      this.route = ['课程管理', '成绩登记']
      this.$emit('GetRoute', this.route)
      this.$router.push("/get_score");
    },
    questionSquare(){
      this.route = ['消息通知', '问题广场']
      this.$emit("GetRoute",this.route)
      this.$router.push("/forum")
    },
  },

  created()
  {
    const token = JSON.parse(localStorage.getItem('token'))
    if(token)
    {
      console.log(token)
      console.log(token.userName.length)
      if(token.userName.length===1)
      {
        this.isAdmin=true;
        this.isStudent=false;
        this.isTeacher=false;
      }
      else if (token.userName.length === 5) {
        this.isAdmin = false;
        this.isStudent = false;
        this.isTeacher = true;
      }
      else{
        this.isAdmin = false;
        this.isStudent = true;
        this.isTeacher = false;
      }

    }
  },
}
</script>

<style scoped>
.el-aside {
  color: #333;
}
</style>>