<template>
    <div style="width: 100%; height: 100%;">
        <el-container>
            <el-main>
                <el-card class="box-card">
                    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
                        <el-tab-pane  name="first">
                            <el-badge :value="do_not_read" class="item_title" slot=label>
                                通知公告
                            </el-badge>
                        </el-tab-pane> 
                        <!-- <el-tab-pane label="配置管理" name="second">配置管理</el-tab-pane>
                        <el-tab-pane label="角色管理" name="third">角色管理</el-tab-pane>
                        <el-tab-pane label="定时任务补偿" name="fourth">定时任务补偿</el-tab-pane> -->
                    </el-tabs>
                    <el-card style="background-color: #FFFFFF; height: 70px;">
                        <div style="display: flex; justify-content: space-between;">
                            <div>
                                <el-button size="large" type="primary" plain 
                                    @click="title_sort">标题</el-button>
                            </div>
                            <div>
                                <el-button size="large" type="primary" plain
                                    @click="time_sort">时间</el-button>
                            </div>
                            <div>
                                <el-button size="large" type="primary" plain
                                    @click="state_sort">状态</el-button>
                            </div>
                        </div>
                    </el-card>
                    <div v-for="o in tableData">
                        <el-card shadow="hover" style="height: 60px;" >
                            <div style="display: flex; justify-content: space-between">
                                <div style="width: 33%">
                                    <i class="el-icon-edit"></i> 
                                    <el-link type="primary" @click="link_information_click(o)"> {{ o['infoTitle'] }}</el-link>
                                    <el-dialog
                                      :visible.sync="dialogVisible"
                                      width="30%"
                                      :before-close="handleClose">
                                      <span slot="title">
                                        {{ dialogTitle }}
                                      </span>
                                      <span>{{ dialogInformation }}</span>
                                      <span slot="footer" class="dialog-footer">
                                        <el-button type="primary" @click="dialogConfirm(o)">确 定</el-button>
                                      </span>

                                    </el-dialog>
                                </div>
                                <div style="width: 33%;text-align: center">
                                  {{ o['infoDate'] }}
                                </div>
                                <div style="width: 33%;text-align: right">
                                    <el-tag
                                    size="large"
                                    :type="tags[o['readStatus']]"
                                    :key="o['readStatus']"
                                    >{{ o['readStatus'] }}</el-tag>
                                </div>
                            </div>
                        </el-card>
                    </div>

                </el-card>
            </el-main>
            <el-footer>
                <el-pagination 
                        @size-change="handleSizeChange" 
                        @current-change="handleCurrentChange"
                        background layout="total,sizes,prev,pager,next,jumper" 
                        :total="total"
                        :page-size="10"
                        :page-sizes="[10,20,30,40,50,100]"
                        style="text-align: center; margin-top: 15px;">
                </el-pagination>
            </el-footer>
        </el-container>
    </div>
</template>

<script>
export default {
    data() {
        return {
            activeName: 'first',
            total: 0,  //总条数
            pSize: 10,  //默认10条
            page:1,
            do_not_read:0,
            dialogVisible: false,
            dialogInformation:"",
            dialogTitle:"",
            updateStatus: {},

            click_title :0,
            click_time :0,
            click_state :0,
            tableData:[],
            tags: {
                 '未查阅': '' ,
                 '已查阅': 'success' ,
            },
        };
    },
    methods: {
        handleClick(tab, event) {
            console.log(tab, event);
        },
        getDataList(index, pageSize) {  //获取数据列表

          const token = JSON.parse(localStorage.getItem('token'))
          if(token)
          {
            console.log(token)
            console.log(token.userName.length)
          }
            this.tableData = [];
          this.request.get("/InfoStructure/find_info_by_page_id", {
            params: {
              pageStart: index - 1,
              pageSize: pageSize,
              infoTarId: token.userName
            }
          }).then(res => {
            // console.log(res)


            this.tableData = res
          })
          this.request.get("/InfoStructure/find_unread_info", {
            params: {
              infoTarId: token.userName
            }
          }).then(res => {
            console.log(res)
            this.do_not_read =  res;
          })
          console.log("do_not_read");
          console.log(this.do_not_read);
            //alert(this.tableData);
        },
        handleSizeChange(val) {
            console.log('每页' + val + ' 条');
            this.pSize = val;
            this.page = 1;
            this.click_title = this.click_time = this.click_state = 0;
            this.getDataList(1, val);
        },
        handleCurrentChange(val) {
            this.page = val;
            this.click_title = this.click_time = this.click_state = 0;
            console.log('当前页: ' + val);
            this.getDataList((val-1)*this.pSize+1, this.pSize);
        },
        title_sort(){
          if(this.click_title % 2 === 0){
            this.tableData.sort(function(a,b){
                if(a.infoTitle < b.infoTitle)
                    return 1;
                else if(a.infoTitle > b.infoTitle)
                    return -1;
                else
                    return 0;
            })
          }
          else{
            console.log(this.page,this.pSize);
            this.getDataList((this.page-1)*this.pSize+1,this.pSize);
          }
          this.click_title++;
        },
        time_sort(){
          if(this.click_time % 2 === 0){
            this.tableData.sort(function(a,b){
                if(a.infoDate < b.infoDate)
                    return 1;
                else if(a.infoDate > b.infoDate)
                    return -1;
                else
                    return 0;
            })
          }
          else{
            console.log(this.page,this.pSize);
            this.getDataList((this.page-1)*this.pSize+1,this.pSize);
          }
          this.click_time ++;
        },
        state_sort(){
          if(this.click_state % 2 === 0) {
            this.tableData.sort(function (a, b) {
              if (a.readStatus < b.readStatus)
                return 1;
              else if (a.readStatus > b.readStatus)
                return -1;
              else
                return 0;
            })
            this.tableData.forEach(element => {
              console.log(element.readStatus);
            });
          }
          else{
            console.log(this.page,this.pSize);
            this.getDataList((this.page-1)*this.pSize+1,this.pSize);
          }
          this.click_state++;
        },
        link_information_click(o){
          console.log("o.infoId")
          console.log(o.infoId)
          // console.log(o.infoTitle)
          this.dialogTitle = o.infoTitle
          this.request.get("/InfoStructure/find_info_by_info_id",  {
            params: {
              infoId: o.infoId
            }
          }).then(res => {
            // console.log(res)
            this.dialogInformation = res.infoContent;
            console.log("res.infoId")
            console.log(res.infoId)
            if(res.readStatus === "未查阅")
            {
              this.request.get("/InfoStructure/update_read_status", {
                params: {
                  readStatus: "已查阅",
                  upInfoId: res.infoId
                }
              }).then(res2 => {
                // console.log(res)
                console.log(res2)
              })
            }

          })

          this.dialogVisible = true;

        },
        dialogConfirm(o){
          this.updateStatus = {}
          this.dialogVisible = false
          location.reload()
        },
        handleClose(done) {
          this.$confirm('确认关闭？')
            .then(_ => {
              done();
            })
            .catch(_ => {});
        },
    },
    mounted(){
      const token = JSON.parse(localStorage.getItem('token'))
      if(token)
      {
        console.log(token)
        console.log(token.userName.length)
      }
      this.request.get("/InfoStructure/find_info_count", {
        params: {
          infoTarId: token.userName
        }
      }).then(res => {
        console.log(res)
        this.total =  res;
      })
      this.getDataList(1,this.pSize);
    },
}
</script>

<style>
.text {
    font-size: 14px;
}

.item {
    padding: 18px 0;
}

.box-card {
    width: 85%;
    height: 100%;
    margin-left: 200px;
    margin-top: 75px;
}

.el-tabs__item {
    font-size: 18px !important;
}

.el-main{
    height:100%;
}

.item_title {
  margin-right: 20px;
}

.el-badge__content {
    margin-top:10px
}
</style>