<template>
    <div style="width: 100%; height: 100%;">

        <el-container style="width: 85%; height: 100%;margin-left:215px;padding-top:75px;">

            <el-main>
                <el-card class="box-card">
                    <el-tabs  v-model="activeName" type="card" @tab-click="dialog_show = true">
                        <el-tab-pane label="问题中心" name="first"></el-tab-pane>
                        <el-tab-pane label="提问" name="second" ></el-tab-pane>
                    </el-tabs>
                    <el-container style="max-height:540px; overflow-y: auto;" direction="vertical">
                        <div :span="8" v-for="(item, index) in datalist" :key="item.index" :offset="index > 0 ? 2 : 0" style="padding-bottom:10px;" >
                            <el-card :body-style="{ padding: '5px' }" >
                                <div style="padding: 14px;cursor: pointer;"  @click="enter(index)">
                                    <span style="font-weight:800;">{{ item["title"]}}</span><br/>
                                    <span style="font-size: 10px;color:rgb(120,120,120);font-weight:600;">{{ item["name"] }}</span><br/>
                                    <span style="font-size: 10px;font-weight:500;">{{ item["content"] }}</span>
                                    <div class="bottom clearfix">
                                        <span style="font-size: 10px;color:darkgrey;">{{ item["thumbNum"] }} 赞同</span>
                                        <span style="font-size: 10px;color:darkgrey;"> · {{ item["commentNum"] }} 评论</span>
                                        <span style="font-size: 10px;color:darkgrey;">  ·  {{ item["time"] }}</span>
                                    </div>
                                </div>
                            </el-card>
                        </div>
                        <el-pagination backgroundnlayout="prev, pager, next" :total="total_page" @current-change="change"></el-pagination>
                    </el-container>
                </el-card>
            </el-main>
        </el-container>
        <el-dialog title="发布提问" :visible.sync="dialog_show" width="40%" @close="close">

            <el-form ref="form" :model="form" label-width="80px">
                <el-form-item label="你的问题">
                    <el-input v-model="form.title" size="medium"></el-input>
                </el-form-item>

                <el-form-item label="问题描述">
                    <el-input type="textarea" v-model="form.detail" :autosize="{ minRows: 10, maxRows: 30 }" style="font-size:18px;"></el-input>
                </el-form-item>
                <el-form-item label="匿名提问">
                    <el-switch v-model="form.anonymous"></el-switch>
                </el-form-item>
            </el-form>

            <span slot="footer" class="dialog-footer">
                <el-button @click="close" size="medium" >取 消</el-button>
                <el-button type="primary" @click="publish_question" size="medium">确定提问</el-button>
            </span>
        </el-dialog>

        <el-dialog title="" :visible.sync="show_question" width="40%" style="top:-10%">
            <el-main style="background-color:azure;border-radius:20px;">
                <span style="font-size:17px ;color:black">{{ datalist[index].name}}</span><br/>
                <span style="font-size:12px;">{{ datalist[index].time }}</span><br/>
                <div style="padding-top: 20px;">
                    <span style="font-size:15px;color: rgb(100,100,100);">{{ datalist[index].content }}</span>
                </div>
                <div style="margin-top:20px">
                    <el-button  v-bind:style="{ color: color }" style="margin-left:90%;" icon="el-icon-thumb" circle @click="thumb"></el-button>
                    <span style="font-size:12px;color:darkgrey;;">{{ datalist[index].thumbNum }}</span>
                </div>
                <span  v-bind:style="{ color:report_span.color,fontSize:report_span.size}" style="cursor: pointer;" @click="report" @mouseover="over" @mouseleave="leave">举报？点击这里</span>
            </el-main>

            <el-main style="background-color:aliceblue;border-radius:20px;margin-top:20px;max-height:300px; overflow-y: auto;" >
                <span style="font-size:14px;color:black;margin-bottom: 5px;">全部评论 · {{ datalist[index].commentNum }}</span><br/>
                <el-divider></el-divider>
                <div style="margin-bottom:10px;border-radius: 10px;border-color: black;background-color: white;" v-for="(item, index) in comment" :key="index">
                    <span style="font-size:14px;color:black;padding-top: 10px;margin-left: 10px;">{{ item.name }}</span><br/>
                    <div style="padding-top: 10px;margin-left: 10px;">
                        <span style="font-size:13px;color: rgb(100,100,100);">{{ item.content }}</span>
                    </div>
                    <div style="padding-top: 9px;margin-left: 10px;">
                        <span style="font-size:11px;">{{ item.time }}</span>
                    </div>
                </div>
            </el-main>
            <div style="margin-top: 15px;">
                <el-input type="textarea" placeholder="请输入内容" v-model="input" class="input-with-select" maxlength="50">
                </el-input>
                <el-button style="margin-top: 10px;" type="primary" round @click="publish_comment">发表评论</el-button>
            </div>
        </el-dialog>

    </div>

</template>

<style>
.time {
    font-size: 13px;
    color: #999;
}

.bottom {
    margin-top: 13px;
    line-height: 12px;
}

.button {
    padding: 0;
    float: right;
}

.image {
    width: 100%;
    display: block;
}

.clearfix:before,
.clearfix:after {
    display: table;
    content: "";
}

.clearfix:after {
    clear: both
}
</style>

<script>


export default {
    components: {

    },
    data() {
        return {
            activeName:"first",
            dialog_show:false,
            current_page:1,
            total_page:1,
            index:0,
            color:'darkgrey',
            report_span:{
                color:'darkgrey',
                size:'12px',
            },
            input:'',
            comment:[
                // {
                //     content: "content1",
                //     name: "name1",
                //     time: "2023/5/29 13:00:14"
                // },
                // {
                //     content: "content2",
                //     name: "name2",
                //     time: "2023/5/29 13:00:14"
                // },
                // {
                //     content: "content2",
                //     name: "name2",
                //     time: "2023/5/29 13:00:14"
                // },
                // {
                //     content: "content2",
                //     name: "name2",
                //     time: "2023/5/29 13:00:14"
                // },
            ],
            form: {
                title:"",
                detail:"",
                anonymous:false,
            },
            show_question:false,
            datalist:[
                // {
                //     title:"title1",
                //     content: "content1",
                //     name:"name1",
                //     commentNum:10,
                //     thumbNum:10,
                //     time:"2023/5/29 13:00:14"
                // },
                // {
                //     title: "title2",
                //     content: "content2",
                //     name: "name2",
                //     commentNum: 10,
                //     thumbNum: 10,
                //     time: "2023/5/29 13:00:14"
                // },
                // {
                //     title: "title3",
                //     content: "content3",
                //     name: "name3",
                //     commentNum: 10,
                //     thumbNum: 10,
                //     time: "2023/5/29 13:00:14"
                // },
                // {
                //     title: "title3",
                //     content: "content3",
                //     name: "name3",
                //     commentNum: 10,
                //     thumbNum: 10,
                //     time: "2023/5/29 13:00:14"
                // },
                // {
                //     title: "title4",
                //     content: "content4",
                //     name: "name4",
                //     commentNum: 10,
                //     thumbNum: 10,
                //     time: "2023/5/29 13:00:14"
                // },
            ],
        };x
    },
    methods:
        {
            getComment(id){
                // console.log(this.datalist.id)
                this.request.get("/question-square/getCommentByQid", {
                    params: {
                        id:this.datalist.id
                    }
                }).then(res => {
                    this.comment = res.comment
                    // this.total_page = res.Total
                })
            },
            publish_comment()
            {
                this.getComment(this.datalist[this.index].id)

                const currentDate = new Date()
                const time=currentDate.getFullYear() + "-" + currentDate.getMonth() + 1 + "-" + currentDate.getDate() + " " + currentDate.getHours() + ":" + currentDate.getMinutes() + ":" + currentDate.getSeconds()
                const content= this.input
                const token = JSON.parse(localStorage.getItem('token'))
                const username= token.userName
                // this.comment.push({name:username,content:content,time:time})

                const formData = new FormData();
                formData.append('qid', this.datalist[this.index].id);
                formData.append('content',content);
                formData.append('name', username);
                // formData.append('thumbNum', datatoken.thumbNum);
                // formData.append('commentId', datatoken.commentId);
                // formData.append('time', datatoken.time);

                this.request.post("/question-square/insertComment", formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }).then(res => {
                    if (res) {
                        this.$message.success("发布成功")

                    } else {
                        this.$message.error("发布失败")
                    }
                }).catch(error => {
                    console.error(error);
                });
                this.getComment(this.datalist[this.index].id)
            },
            over()
            {
                this.report_span.size='13px'
                this.report_span.color='lightblue'
            },
            leave(){
                this.report_span.size = '12px'
                this.report_span.color = 'darkgrey'
            },
            thumb()
            {
                if(this.color=="darkgrey")
                {
                    this.datalist[this.index].thumbNum+=1
                    this.color='red'
                }
                else
                {
                    this.datalist[this.index].thumbNum -= 1
                    this.color = 'darkgrey'
                }
            },
            report(){
                const h = this.$createElement;
                this.$notify({
                    title: '举报成功',
                    message: h('i', { style: 'color: teal' }, '我们将审核该评论')
                });
            },
            change(e)
            {
                this.current_page=e
                this.request.get("/", {
                    params: {
                        CurrentPage: this.current_page,
                        ItemNum:20,
                    }
                }).then(res => {
                    this.datalist = res.datalist
                    this.total_page=res.Total
                })
            },
            enter(index){
                this.show_question=true

                this.index=index
                this.getComment(this.datalist[this.index].id)
                console.log(this.datalist)
                console.log(this.index)

                // console.log(index)
            },
            close()
            {
                this.activeName= "first"
                this.dialog_show=false
            },
            publish_question()
            {
                if(this.form.title==="")
                {
                    const h = this.$createElement;
                    this.$notify({
                        title: '请重试',
                        message: h('i', { style: 'color: teal' }, '问题不能为空')
                    });
                    return
                }
                if(this.form.detail==="")
                {
                    const h = this.$createElement;
                    this.$notify({
                        title: '请重试',
                        message: h('i', { style: 'color: teal' }, '问题详情不能为空')
                    });
                    return
                }
                this.activeName= "first",
                    this.dialog_show=false
                let publisher=""
                if(this.form.anonymous==true)
                    publisher ="匿名用户"
                else
                {
                    const token = JSON.parse(localStorage.getItem('token'))
                    publisher = token.userName
                }
                const currentDate=new Date()
                currentDate.getTime()
                const month= currentDate.getMonth()+1
                let datatoken={
                    title: this.form.title, //问题标题
                    content: this.form.detail,  //问题详情
                    name: publisher,  //问题发布者ID
                    anonymous:this.form.anonymous, //是否匿名
                    // commentNum: 0, //评论数
                    thumbNum: 0,   //点赞数
                    commentId: 0,
                    //时间YYYY-MM-DD HH:MM:SS
                }
                const token = JSON.parse(localStorage.getItem('token'))
                console.log(datatoken.time)
                console.log(datatoken.name)
                const formData = new FormData();
                formData.append('title', datatoken.title);
                formData.append('content', datatoken.content);
                formData.append('name', datatoken.name);
                formData.append('thumbNum', datatoken.thumbNum);
                formData.append('commentId', datatoken.commentId);
                // formData.append('time', datatoken.time);

                this.request.post("/question-square/insert", formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }).then(res => {
                    if (res) {
                        this.$message.success("发布成功")
                        // this.$forceUpdate();
                        // this.mounted()
                    } else {
                        this.$message.error("发布失败")
                    }
                }).catch(error => {
                    console.error(error);
                });
                this.getPage()
                location.reload()

            },
            getPage(){
                this.request.get("/question-square/getByPage", {
                    params: {
                        pageNum: this.current_page,
                        pageSize: 20,
                    }
                }).then(res => {
                    this.datalist = res.datalist
                    this.total_page = res.Total
                    console.log(this.datalist)
                    console.log(this.index)

                })
            }

        },
    mounted(){
        this.getPage()

    },
}
</script>