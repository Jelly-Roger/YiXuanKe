<template>
  <div style="justify-content: space-between; height:100%;">
    <div style="{
                    width:100%;
                    height:100%;
                    z-index:-1;
                    position: fixed;
                }">
      <img :src="imgSrc" width="100%" height="100%" alt="" />
    </div>

    <div>
      <transition name="el-zoom-in-center">
        <div v-show="show">
          <!-- 文字开始 -->
          <!-- <div class="text1">愿天堂</div>
          <div class="text2">没有程序BUG</div> -->
          <!-- 文字结束 -->

          <!-- 底部文字开始 -->
          <!-- <div class="text3">帮助中心&nbsp;&nbsp;&nbsp;&nbsp;|</div>
          <div class="text4">
              <a href="#"><img src="@/assets/dodswnload.png" alt=""></a>
          </div>
          <div class="text5">2021@Baidu</div> -->
          <!-- 底部文字结束 -->

          <!-- 表单开始 -->
          <div id="form-info">
            <div class="logo">
              <div>
                <img src="@/assets/tongji.png" alt=""
                     style="height: 100px; width: 100px;padding-right: 0px;">
              </div>
              <div id="register" style="padding-left: 50px;">密码更改</div>
            </div>
            <div style="position: absolute;">
              <form action="#" method="POST" @focus="doAllFocus" @blur="doAllBlur">
                <!-- 用户名开始 -->
                <div class=" form form1">
                  <label for="userName" class="label-psw">学号</label>
                  <input v-model="userName" v-on:focus="doFocusForm1" v-on:blur="doBlurForm1" type="text"
                         id="userName" placeholder="  请输入学号" class="input input1">
                </div>
                <div style="margin-left: 100px;" v-if="pwdLen">
                  <br>
                  <span>
                                        <p style="font-size: 14px; color: red; position: absolute; margin-top: 5px;">
                                            学号必须为7位
                                        </p>
                                    </span>
                </div>
                <!-- 用户名结束 -->

                <!-- 邮箱开始 -->
                <div class=" form form2">
                  <label for="email" class="label-psw">邮箱</label>
                  <input v-model="email" type="text" id="email" placeholder="  请输入你与学号相关联的邮箱" class="input input1">
                </div>
                <!-- 邮箱结束 -->

                <!-- 密码开始 -->
                <div class="form form2">
                  <label for="psw" class="label-psw">新密码</label>
                  <input v-model="passWord" type="password" id="psw" placeholder="  请输入新密码"
                         class=" input input1" show-password @focus="doAllFocus" @blur="doAllBlur">
                  <!-- <div class="gth">
                      <img src="@/assets/download (1).png" alt="">
                  </div> -->
                </div>
                <!-- 密码结束 -->

                <!-- 验证码开始 -->
                <div class="form2 form">
                  <div>
                    <label for="captcha" class="label-psw1">邮箱验证码</label>
                    <input type="text" id="captcha" placeholder="  请输入验证码" class=" input input2"
                           v-model="code" @focus="doAllFocus" @blur="doAllBlur">

                    <input type="button" id="getcode" name="getcode" value="获取验证码" class="input input3"
                           @click="sendEmailCode(2)" style="margin-left:5px;">
                  </div>

                </div>
                <!-- 验证码结束 -->
                <br>
                <div v-if="isFalseCode">
                  <!-- <el-alert title="验证码错误" type="error"
                      style="width: auto;">
                  </el-alert> -->
                  <!-- <span>
                      <p style="font-size: 14px; color: red;padding-top:20px;text-align:center;">
                          验证码错误
                      </p>
                  </span> -->
                </div>
                <!-- 注册开始 -->
                <div id="register" style="margin-left: 40px;">
                  <input class="register" type="button" value="修改密码" @click="submit" />
                </div> <br>

                <div><br><br>
                  <span>
                                        <p style="font-family: 'Hanken', Times, serif; font-size: 14px;
                                                position: absolute; margin-left: 22%;">
                                            Copyright by YiXuanKe, Tongji University.
                                        </p>
                                    </span>
                </div>
                <!-- 注册结束 -->
                <!--  -->
                <!-- 阅读并接受开始 -->
                <!-- <div class="read" d>
                    <input type="checkbox" name="read" id="read" class="read1">
                    同意使用易选课app
                </div> -->
                <!-- 阅读并接受结束 -->
              </form>
            </div>
          </div>
          <!-- 表单结束 -->
        </div>
      </transition>
    </div>
  </div>
</template>

<script>
import SIdentify from '@/components/identify.vue'
export default {
  data() {
    return {
      imgSrc: require("@/assets/backg.jpg"),
      show: false,
      isOnForm1: false,
      email:"",
      code:"",
      userName: "",
      passWord: "",
      inputIdentifyCode: '',
      isFalseCode: false,
      isFocus: 1.0,
      StudentPasswordDTO: {}
    }
  },
  components: {
    SIdentify,
  },

  mounted: function () {
    //show:!show

    this.show = true;
  },

  methods: {
    sendEmailCode(type) {
      let email=this.email;
      // if (type === 1) {
      //     email = this.user.email
      // } else if(type === 2) {
      //     email = this.pass.email
      // }
      if(!email) {
        this.$message.warning("请输入邮箱账号")
        return
      }
      if(!/^\w+((.\w+)|(-\w+))@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+).[A-Za-z0-9]+$/.test(email)) {
        this.$message.warning("请输入正确的邮箱账号")
        return
      }
        // const token = JSON.parse(localStorage.getItem('token'))
        // let username=token.userName
        // let username="2050259"
        // console.log(username)
      // 发送邮箱验证码
      this.request.get("/student/modify/" + email + "/" + type +"/"+this.userName).then(res => {
        // this.$message.success("发送成功")
        if (res.code === '200') {
          this.$message.success("发送成功")
        } else {
          this.$message.success("发送失败")
          this.$message.error(res.msg)
        }
      })
    },


    doThis() {
      this.$refs["login_appear"].click()
    },
    doAllFocus() {
      this.isFocus = 0.3;
    },
    doAllBlur() {
      this.isFocus = 1.0;
    },
    getLength(str) {
      ///<summary>获得字符串实际长度，中文2，英文1</summary>
      ///<param name="str">要获得长度的字符串</param>
      var realLength = 0, len = str.length, charCode = -1;
      for (var i = 0; i < len; i++) {
        charCode = str.charCodeAt(i);
        if (charCode >= 0 && charCode <= 128)
          realLength += 1;
        else
          realLength += 2;
      }
      return realLength;
    },
    doFocusForm1() {
      // alert("asdfas");
      this.isFocus = 0.3;
      this.isOnForm1 = true;
    },
    doBlurForm1() {
      this.isFocus = 1.0;
      if (this.getLength(this.userName) == 7)
        this.isOnForm1 = false;
    },
    showcode() {
      // TODO : 要发给后端一个信号

    },
    login() {
      //alert(this.identifyCode)
      if (this.identifyCode != this.inputIdentifyCode) {
        this.isFalseCode = true;//验证码检验错误
        this.open();
      }
    },

    forgetPWD() {
      this.$router.push("/forget");
    },
    modifyPWD() {
      this.$router.push("/modify");
    },
    open() {
      this.$notify.error({
        title: '错误',
        message: '验证码错误',
        position: 'bottom-right'
      });
    },
    getCode() {
      this.request
          .get("url", {
            params: {
              email: this.email
            }
          })
          .then(() => {
            this.$message({
              message: "已发送验证码",
              type: "success"
            });
          })
          .catch(() => {
            this.$message({
              message: "请求超时，请检查网络连接",
              type: "error"
            });
          });
    },

    submit() {
      let code=this.code;
      const data = {
        email: this.email,
        code: this.code
      };
      if(!code) {
        this.$message.warning("请输入验证码")
        return
      }
      this.StudentPasswordDTO.password = this.passWord
      this.StudentPasswordDTO.email = this.email
      this.StudentPasswordDTO.code = this.code
      if(!(/^(?=.*[a-zA-Z])(?=.*\d)(?=.*\W).{8,}$/.test(this.passWord))) 
      {
          this.$message.warning("密码不满足长度不小于 8、至少包含字母、下划线、数字中")
          return
      }
      this.request.put("/student/modify", this.StudentPasswordDTO).then(res => {
        this.$message.warning("校验完成")
        if(res.msg === "")
          this.$message.success("修改密码成功")
        else
          this.$message.error(res.msg)
        this.$router.back()
        // if (res.code === '200') {
        // this.$message.success("发送成功")
        // } else {
        // this.$message.error(res.msg)
        // }
      })},


    // submit() {
    //     const data = {
    //         email: this.email,
    //         code: this.code
    //     };
    //     this.request
    //         .post("url", data)
    //         .then(res => {
    //             if ((res.data.code != this.code)) {
    //                 this.$message({
    //                     message: "验证码错误",
    //                     type: "error"
    //                 });
    //                 return;
    //             } else {
    //                 this.$router.push("/login");
    //                 this.$message({
    //                     message: "验证通过,成功",
    //                     type: "success"
    //                 });
    //             }
    //         })
    //         .catch(() => {
    //             this.$message({
    //                 message: "请求超时，请检查网络连接",
    //                 type: "error"
    //             });
    //         });
    // },

  },

  computed: {
    pwdLen: function () {
      //alert(this.userName);
      return this.getLength(this.userName) != 7 && this.isOnForm1;
    },
    opacity: function () {
      return this.isFocus;
    },
  }
}

</script>

<style>
/* 背景图 */
/* body {
            
            background: url("@/assets/backg.jpg") no-repeat;
            
			background-size: 100% 100%;
			background-attachment: fixed;
            opacity: var(opacitys);
        } */
/* .background {
            width:100%;
            height:98%;
            z-index:-1;
            position: absolute;
        } */

.logo {
  width: auto;
  height: 60px;
  /* width: auto;
          height: 50px; */
  margin-top: 60px;
  margin-left: 70px;
  display: flex;
}

.logo img {
  width: 125px;

}

/* 文字 */
.text1 {
  font-family: 'Poppins', sans-serif;
  width: 170px;
  height: 50px;
  color: #fff;
  margin-top: 60px;
  margin-left: 200px;
  font-size: 50px;
  font-weight: bold;

}

.text2 {
  font-family: 'Poppins', sans-serif;
  width: 300px;
  height: 40px;
  color: #fff;
  margin-top: 10px;
  margin-left: 200px;
  font-size: 30px;
  letter-spacing: 5px;


}

/* 底部文字 */
.text3 {
  font-family: 'Poppins', sans-serif;
  width: 80px;
  height: 15px;
  margin-left: 70px;
  margin-top: 440px;
  color: #BFC2CA;
  font-size: 14px;
  float: left;
}

.text4 {
  width: 80px;
  height: 15px;
  margin-left: 10px;
  margin-top: 440px;

  float: left;
}

.text4 img {
  width: 15px;
}

.text5 {
  width: 80px;
  height: 15px;
  margin-left: -30px;
  margin-top: 445px;
  color: #BFC2CA;
  font-size: 12px;
  float: left;
}

/* 表单 */
#form-info {
  width: 480px;
  height: 680px;
  border-radius: 10px;
  background-color: rgba(255, 255, 255, 0.9);
  position: absolute;
  top: 50px;
  bottom: 0px;
  right: 140px;

}

/* 欢迎注册 */
#register {
  width: 480px;
  height: 50px;
  margin-left: 0;
  margin-top: 35px;
  font-size: 36px;
  color: #000;
  font-weight: bold;
}

/* 已有账号 */
#old-account {
  width: 480px;
  height: 20px;
  font-size: 14px;
  color: #9B9B9B;
  float: left;
  margin-left: 40px;

}

/* 登录 */

#login {
  color: #2e58ff;
  cursor: pointer;
  text-decoration: none;
}

/* 输入框 */
.input {

  height: 40px;
  margin-bottom: 15px;
  border-radius: 4px;
  border: 1px solid #E0E0E0;
  background-color: #fff;
}

.input1 {
  font-style: italic;
  width: 340px;
  margin-left: 0px;
  height: 40px;
}

.input2 {
  font-style: italic;
  width: 205px;
}

.input3 {
  width: 120px;
  cursor: pointer;

}



/* 表单内容样式 */
.form {
  width: 480px;
  height: 40px;
  margin-left: 40px;
}

.form1 {
  margin-top: 90px;
}

.form2 {
  margin-top: 35px;
}



/* 字体 */
label {

  width: 68px;
  /* 转换为块元素 */
  display: inline-block;
  font-size: 14px;
  color: #333;
  font-weight: 200;

}

.label-psw {
  font-weight: 300;
  font-size: 18px;
  font-family: SimHei;
}

.label-psw1 {
  font-weight: 300;
  font-size: 18px;
  font-family: SimHei;

}

.gth {
  width: 15px;
  height: 20px;
  position: absolute;
  margin-left: 370px;
  margin-top: -45px;

}

.gth img {
  width: 100%;
}

/* 注册按钮 */
.register {
  width: 400px;
  height: 50px;
  background: #BDCEFC;
  border-radius: 25px;
  border: rgba(255, 255, 255, 0.5) 1px solid;
  color: white;
  font-size: 16px;
  margin-top: 20px;
  cursor: pointer;

}


.register:hover {
  background-color: #0b19da;
  color: #fff;
}

.read {
  width: 330px;
  height: 10px;
  margin-left: 80px;
  margin-top: 30px;
  font-size: 12px;
  color: #666;
  position: absolute;
}

a {
  text-decoration: none;
  color: blue;

}

/* input内文字颜色 */
input::-webkit-input-placeholder {
  color: #ccc;
}

html,
body,
div,
span,
applet,
object,
iframe,
h1,
h2,
h3,
h4,
h5,
h6,
p,
blockquote,
pre,
a,
abbr,
acronym,
address,
big,
cite,
code,
del,
dfn,
em,
img,
ins,
kbd,
q,
s,
samp,
small,
strike,
strong,
sub,
sup,
tt,
var,
b,
u,
i,
center,
dl,
dt,
dd,
ol,
ul,
li,
fieldset,
form,
label,
legend,
table,
caption,
tbody,
tfoot,
thead,
tr,
th,
td,
article,
aside,
canvas,
details,
embed,
figure,
figcaption,
footer,
header,
hgroup,
menu,
nav,
output,
ruby,
section,
summary,
time,
mark,
audio,
video {
  margin: 0;
  padding: 0;
  border: 0;
  font-size: 100%;
  font: inherit;
  vertical-align: baseline;
}

/* HTML5 display-role reset for older browsers */
article,
aside,
details,
figcaption,
figure,
footer,
header,
hgroup,
menu,
nav,
section {
  display: block;
}

body {
  line-height: 1;
}

ol,
ul {
  list-style: none;
}

blockquote,
q {
  quotes: none;
}

blockquote:before,
blockquote:after,
q:before,
q:after {
  content: '';
  content: none;
}

table {
  border-collapse: collapse;
  border-spacing: 0;
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}

.bg2 {
  width: 700px;
  height: 700px;
  margin-left: 770px;
  margin-right: 0px;
  background-color: rgba(100, 100, 100, 0.5);
}

.el-fade-in-linear-enter-active {
  transition: all .10s ease;
}

.el-fade-in-linear-eZnter

  /* .slide-fade-leave-active for below version 2.1.8 */
{

  opacity: 0;
}</style>