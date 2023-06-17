<template>
    <div style="position: fixed; background-color: rgb(255,255,255);margin-top: 100px;margin-left: 25%;">
        <div style="display: flex;justify-content: space-between">
          <div style="margin-left: 320px; margin-top: 20px;">
              <span style="font-size: 16px;">备选列表</span>
          </div>

          <div style=" margin-right: 20px; text-align: right;margin-top: 20px;">
  <!--            <el-button @click="professional">专业课</el-button>
              <el-button @click="general">通识课</el-button>-->
              <el-button type="danger" @click="close" style="text-align: right">关闭</el-button>
          </div>
        </div>
        <el-divider></el-divider>
        <div style="position: relative;">
            <el-table ref="multipleTable" :data="tableData.filter(f => f.include == false)"
                      max-height="350px"
                      tooltip-effect="dark" style="width: 100%"
                @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55"
                :selectable="isAbleToSelect">
                </el-table-column>
                <el-table-column label="课号" width="120">
                    <template slot-scope="scope">{{ scope.row.Cid }}</template>
                </el-table-column>
                <el-table-column prop="CName" label="课程名称" width="200">
                </el-table-column>
                <el-table-column prop="type" label="课程类型" width="120" 
                        :filters="fiter"
                        :filter-method="filterTag"
                        filter-placement="bottom-end"
                        >
                        <template slot-scope="scope">
                        <el-tag :type="scope.row.type === fiter[0].text ?
                            (scope.row.type === fiter[1].text ? 'warning' : 'info')
                            :(scope.row.type === fiter[2].text ? 'danger' : 'success')"
                            disable-transitions>{{scope.row.type}}</el-tag>
                        </template>
                </el-table-column>
                <el-table-column prop="chosenCon" label="已选人数" width="120">
                </el-table-column>
                <el-table-column prop="college" label="开课学院" show-overflow-tooltip>
                </el-table-column>
            </el-table>
        </div >
        <div>
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              background layout="total,sizes,prev,pager,next,jumper"
              :total="total"
              :page-size="10"
              :page-sizes="[10,20,30,40,50,100]"
              style="text-align: center; margin-top: 15px;">
          </el-pagination>
        </div>
        <el-divider></el-divider>

        <div style="position: relative; text-align:center; margin-bottom: 5%;">
            <el-button @click="choose">选择</el-button>
            <el-button @click="toggleSelection()">取消选择</el-button>
        </div>
        
    </div>
</template>

<script>
export default {
    props:{
        tableData:Array,
        fiter:Array,
        already_select_list_CID:Array,
        has_choosen :Array,
    },
    data() {
        return {
            pSize:10,
            page:1,
            multipleSelection: [],

            total:0,
        }
    },

    methods: {
        getDataList(index, pageSize) {  //获取数据列表
          this.tableData = [];
          this.request.get("/course/all_course_list_for_select", {
            params: {
              pageNum:index,
              pageSize:pageSize,
            }
          }).then(res => {
            console.log(res['data']);
            this.tableData = res['data'];
            console.log("already_select_list_CID")
            console.log(this.already_select_list_CID)
            for(var i =0;i < this.tableData.length;i++){
              this.tableData[i]['include'] = this.already_select_list_CID.includes(this.tableData[i].Cid);
            }
            this.total = res['total'];
          })
          console.log(this.tableData);
          //alert(this.tableData);
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
        toggleSelection(rows) {
            if (rows) {
                rows.forEach(row => {
                    this.$refs.multipleTable.toggleRowSelection(row);
                });
            } else {
                this.$refs.multipleTable.clearSelection();
            }
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        resetidFilter() {
            this.$refs.filterTable.clearFilter('id');
        },
        clearFilter() {
            this.$refs.filterTable.clearFilter();
        },
        filterTag(value, row) {
            return row.type === value;
        },
        filterHandler(value, row, column) {
            const property = column['property'];
            return row[property] === value;
        },
        professional(){
            // alert("筛选专业课");
        },
        general(){
            // alert("筛选通识课");
        },
        close(){
            //this.$router.go(0);
            this.$emit("finish");
        },
        choose(){
            console.log(this.multipleSelection);
            this.$emit("selectFinish",this.multipleSelection,this.already_select_list_CID);
            // 这里需要留一个送后端的位置
            //this.$router.go(0);
        },
        isAbleToSelect(row){
          return !this.has_choosen.includes(row.Cid);
        },
    },
    mounted() {
      console.log(this.already_select_list_CID);
      this.getDataList(1,this.pSize);

    }
}
</script>
<style>
</style>