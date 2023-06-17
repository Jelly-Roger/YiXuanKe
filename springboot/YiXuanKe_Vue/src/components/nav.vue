<template>
        <div class="header">
            <div class="box">
                <h1>
                    <img src="../assets/yixuanke.png" style="width:100px;height:70px;margin-right:50px;">
                    <!-- <span>易选课</span> -->
                </h1>
                <nav>
                    <el-breadcrumb separator-class="el-icon-arrow-right">
                    <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                    <el-breadcrumb-item v-for="(item,index) in route" :key=index>{{item}}</el-breadcrumb-item>
                    </el-breadcrumb>
                </nav>
            </div>
            <div class="box">
                <img src="../assets/tongji.png" style="margin-right:20px;">
                <el-dropdown style="width: 200px; cursor: pointer">
                  <span>你好，{{ username }}</span><i class="el-icon-arrow-down"></i>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item style="font-size: 14px; width:100px;text-align: center;padding-top: 10px;">个人信息</el-dropdown-item>
                    <el-dropdown-item style="font-size: 14px; width:100px;text-align: center;padding: 10px;" @click.native="logout">登出</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
            </div>
        </div>
</template>

<script>
export default {
    name: "BBSHeader",
    data(){
        return{
            username:'init',
            Route:this.route,
        }
    },
    props: {
        route:Array,
    },
    created() {
        if (this.$route.path == '/CourseTable') {
            this.route = ['课程管理', '我的课表']
        }
        else if (this.$route.path == '/manage_student') {
            this.route = ['管理员管理', '学生管理']
        } 
        else if (this.$route.path == '/manage_teacher') {
            this.route = ['管理员管理', '教师管理']
        }
        else if (this.$route.path == '/AllCourse') {
            this.route = ['课程管理', '全校课表']
        }
        else if (this.$route.path == '/SelectCourse') {
            this.route = ['课程管理', '我的选课']
        }
        let token = JSON.parse(localStorage.getItem('token'))
        if(token)
            this.username = token.userName
    },
  methods:{
    logout(){
      localStorage.removeItem('token')
      this.$router.push('/login')
      console.log("1111")
    }
  },
}
</script>

<style scoped>
.header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    height: 80px;
    background-color: #ffffff;
    color: #111;

}

.box {
    display: flex;
    align-items: center;
}

h1 {
    display: flex;
    align-items: center;
    font: normal 28px Cookie, Arial, Helvetica, sans-serif;
    padding: 0px 20px;
}

img {
    width: 40px;
    height: 40px;
}

nav {
    display: flex;
    align-items: center;
    margin: 0px 40px;
    font: 16px Arial, Helvetica, sans-serif;
}

nav a {
    padding: 0 15px;
    width: 32px;
    text-decoration: none;
    color: #111;
    font-size: 16px;
    font-weight: normal;
    opacity: 0.9;
}

nav a:hover {
    opacity: 1;
}

.active {
    color: #608bd2;
    pointer-events: none;
    opacity: 1;
}

/*搜索框*/

.text {
    height: 22px;
    font-size: 14px;
    border: 1px solid #ccc;
    padding: 3px 16px;
    border-bottom-left-radius: 20px;
    border-top-left-radius: 20px;
}

.text:focus {
    outline: none;
    border-color: rgba(82, 168, 236, 0.8);
    box-shadow: inset 0 2px 2px rgba(0, 0, 0, 0.075), 0 0 8px rgba(82, 168, 236, 0.6);
}

.button {
    width: 60px;
    height: 30px;
    font-size: 14px;
    margin-right: 35px;
    border: 1px solid #608bd2;
    background-color: #608bd2;
    border-top-right-radius: 20px;
    border-bottom-right-radius: 20px;
}

.contents {
    display: flex;
    justify-content: center;
}

.content {
    display: flex;
    width: 1400px;
    height: 1400px;
    /*background-color: #f0f2f3;*/
}</style>
