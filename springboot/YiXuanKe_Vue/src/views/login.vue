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
                    <div id="form-info">
                        <div class="logo">
                            <div>
                                <img src="@/assets/tongji.png" alt=""
                                    style="height: 100px; width: 100px;padding-right: 0px;">
                            </div>
                            <div id="register" style="padding-left: 50px;"> 欢迎登录</div>
                        </div>
                        <div style="position: absolute;">
                            <form action="#" method="POST" @focus="doAllFocus" @blur="doAllBlur">
                                <!-- 用户名开始 -->
                                <div style="margin-left:40px;width:500px;height: 40px;">
                                    <label for="identity" class="label-psw" style="padding-top:70px">身份</label>
                                    <el-select v-model="value" placeholder="请选择" size="medium" style="width:200px">
                                        <el-option v-for="item in options" :key="item.value" :label="item.label"
                                            :value="item.value" :disabled="item.disabled">
                                        </el-option>
                                    </el-select>
                                </div>
                                <div class=" form form1">
                                    <label for="userName" class="label-psw">ID</label>
                                    <input v-model="InputUsername" v-on:focus="doFocusForm1" v-on:blur="doBlurForm1"
                                        type="text" id="userName" placeholder="  请输入ID" class="input input1">
                                </div>
                                <div style="margin-left: 100px;" v-if="pwdLen">
                                    <br>
                                    <span>
                                        <p style="font-size: 14px; color: red; position: absolute; margin-top: 5px;">
                                            请选择身份/按照身份正确填写id
                                        </p>
                                    </span>
                                </div>
                                <!-- 用户名结束 -->

                                <!-- 手机号开始 -->
                                <!-- <div class=" form form2">
                                    <label for="phone" class="">邮箱</label>
                                    <input type="text" id="phone" placeholder="可用于找回密码" class="input input1">
                                </div> -->
                                <!-- 手机号结束 -->

                                <!-- 密码开始 -->
                                <div class="form form2">
                                    <label for="psw" class="label-psw">密码</label>
                                    <input v-model="InputPassword" type="password" id="psw" placeholder="  请输入密码"
                                        class=" input input1" show-password @focus="doAllFocus" @blur="doAllBlur">
                                    <!-- <div class="gth">
                                        <img src="@/assets/download (1).png" alt="">
                                    </div> -->
                                </div>
                                <!-- 密码结束 -->

                                <!-- 验证码开始 -->
                                <div class="form2 form">
                                    <div>
                                        <label for="captcha" class="label-psw1">验证码</label>
                                        <input type="text" id="captcha" placeholder="  请输入验证码" class=" input input2"
                                            v-model="inputIdentifyCode" @focus="doAllFocus" @blur="doAllBlur">

                                        <input type="button" id="getcode" name="getcode" value="获取验证码" class="input input3"
                                            @click="showcode" style="margin-left:5px;">
                                    </div>
                                    <div v-if="isShow">
                                        <!-- <Verify @success="success" @error="alert('error')" :type="5" showButton='false'></Verify> -->
                                        <SIdentify @getCode="getIdentifyCode" ref="code"
                                            style="margin-top:30px;margin-left: 270px;"></SIdentify>
                                    </div>

                                </div>
                                <!-- 验证码结束 -->
                                <br>
                                <div id="reset_ps" style="margin-left: 100px;margin-top: 30px;">
<!--                                    <el-badge class="item">-->
<!--                                        <el-button size="small" @click="modifyPWD">修改密码</el-button>-->
<!--                                    </el-badge>-->
                                    <el-badge class="item" value="忘记密码" style="margin-left:20px">
                                        <el-button size="small" @click="forgetPWD">找回密码</el-button>
                                    </el-badge>
                                    <!-- <router-link  to="/" style="color:black" @hover="function(){}">修改密码    </router-link>    
                                    <router-link  to="/" style="color:black">找回密码</router-link> -->
                                </div>
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
                                    <input class="register" type="button" value="登录" @click="login" />
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
            userName: "",
            passWord: "",
            InputUsername: "",
            InputPassword: "",
            isShow: false,
            userDto: {},
            identifyCode: '2', // 生成的验证码
            inputIdentifyCode: '',
            isFalseCode: false,
            isFocus: 1.0,
            options: [{
                value: '学生',
                label: '学生'
            }, {
                value: '老师',
                label: '老师',
                // disabled: true
            }, {
                value: '管理员',
                label: '管理员',
            }],
            value: '',
            pwdlength: 0
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
            if (this.getLength(this.InputUsername) == this.pwdlength)
                this.isOnForm1 = false;
        },
        submit() {
            this.isShow = true;
        },
        showcode() {
            this.isShow = true;
            this.isFalseCode = false;
        },
        getIdentifyCode(val) {
            this.identifyCode = val;
        },
        //切换验证码(点击文字切换)
        refreshCode() {
            this.identifyCode = '';
            // 触发子组件刷新验证码事件
            this.$refs.code.$emit('bridge');
        },
        login() {
            //alert(this.identifyCode)
            let login_url = "";
            if (this.value == "学生")
                login_url = "/student/login";
            else if (this.value == "老师")
                login_url = "/teacher/login";
            else if (this.value == "管理员")
                login_url = "/manager/login";
            this.$bus.emit('GetValue', this.value)
            if (!this.isShow) {
                this.isFalseCode = true;//验证码空白
                this.isShow = false;
                this.open2();
                this.refreshCode()
            }
            else if (this.identifyCode != this.inputIdentifyCode) {
                this.isFalseCode = true;//验证码检验错误
                this.isShow = false;
                this.open();
                this.refreshCode()
            }
            else {
                this.userDto.no = this.InputUsername
                this.userDto.id = this.InputUsername
                this.userDto.password = this.InputPassword
                console.log(this.userDto)
                this.request.post(login_url, this.userDto).then(res => {
                    console.log(res)
                    // this.userName=res.username
                    // this.passWord=res.password
                    if (res) {
                        const token = {
                            userName: this.InputUsername,
                            passWord: this.InputPassword
                        }
                        localStorage.setItem('token', JSON.stringify(token))
                        this.$router.push('/');
                    }
                    else {
                      this.$message.error("并未找到适配的用户信息")
                      this.refreshCode()
                    }
                })
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
        open2() {
            this.$notify.error({
                title: '错误',
                message: '请输入验证码',
                position: 'bottom-right'
            });
        }
    },

    computed: {
        pwdLen: function () {
            //alert(this.userName);
            if (this.value === "老师")
                this.pwdlength = 5
            else if (this.value === "管理员")
                this.pwdlength = 1
            else if (this.value === "学生")
                this.pwdlength = 7
            else
                return true
            return this.getLength(this.InputUsername) != this.pwdlength && this.isOnForm1;
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
    margin-top: 40px;
    margin-left: 70px;
    margin-bottom: 20px;
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
}
</style>