<template>
  <div id="app">
    <el-container style="height: 100vh;background-color: rgb(230, 230, 230);width:100%" v-if="$route.meta.keepAlive">
          <div style="height: 100%; width: 200px; background-color: rgb(200 200,200);top:10px;left:0;position: fixed;">
              <Aside :is-collapse="false" :logo-text-show="true" :side-width="200" @GetRoute="GetRoute" :UserType="UserType"></Aside>
          </div>
          <div style="right:0;position:fixed;z-index: 999;width:100%;">
             <NavHead :route="route"></NavHead>
          </div>
        <router-view />
      </el-container>
    <router-view v-if="!$route.meta.keepAlive" ></router-view>
  </div>
</template>


<script>
import Aside from '@/components/Aside.vue'
import NavHead from '@/components/nav.vue'
export default {
  name: "APP",
  components: {
    Aside,
    NavHead,
    //Timetable,
  },
  data()
  {
    return{
      route:[],
      UserType:'学生',
    }
  },
    methods: {
    GetRoute(route) {
      this.route = route
      console.log(this.route)
    },
  },
  mounted()
  {
     this.$bus.on("GetValue", value => {
      this.UserType=value
       console.log('UserType')
       console.log(this.UserType)
       const token = localStorage.getItem('token')
       console.log('token')
       console.log(token)
       if(!token)
       {
         if(token.InputUsename.length === 7)
           this.UserType = '学生'
         else if(token.InputUsename.length === 5)
           this.UserType = '教师'
         else
           this.UserType = '管理员'
       }
    });
  }
}
</script>

<style>
#app{
  min-height: 100vh;
}
</style>
