<template>
    <div>
        <div id="coursesTable" style="width:100%;z-index: 2;"></div>
    </div>
</template>

<script>
import Timetables from '../utils/timetables.min.js'

export default {
    components: {
        // Aside,
        // NavHead,
        // //Timetable,
    },
    props:{
        courseList:Array,
    },
    data() {
        return {
            timetableType: [
                [{ index: '1', name: '第一节课' }, 1],
                [{ index: '2', name: '第二节课' }, 1],
                [{ index: '3', name: '第三节课' }, 1],
                [{ index: '4', name: '第四节课' }, 1],
                [{ index: '5', name: '第五节课' }, 1],
                [{ index: '6', name: '第六节课' }, 1],
                [{ index: '7', name: '第七节课' }, 1],
                [{ index: '8', name: '第八节课' }, 1],
                [{ index: '9', name: '第九节课' }, 1],
                [{ index: '10', name: '第十节课' }, 1],
                [{ index: '11', name: '第十节课' }, 1],
                [{ index: '12', name: '第十二节课' }, 1]
            ],
            week: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
            timetable: null,
            styles: {
                leftHandWidth: 100,
                Gheight: 50,
            },
        }
    },
    mounted() {
        

    },

    methods: {
        change()
        {
            this.timetable = new Timetables({
            el: '#coursesTable',
            timetables: this.courseList,
            week: this.week,
            timetableType: this.timetableType,
            styles: this.styles,
        });
        console.log(this.timetable)
        }
   
    },


}
</script>

<style>
.course {
    width: 100%;
    margin-left: 10%
}

.el-carousel__item h3 {
    color: #475669;
    font-size: 18px;
    opacity: 0.75;
    line-height: 600px;
    margin: 0;
}

.el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
}

.tip {
    margin-top: 10%;
    margin-left: 45%;
}

.tip>h1 {
    color: #666666;
    width: 300px;
}

* {
    margin: 0px;
    padding: 0px;
}


#coursesTable {
    padding: 15px 10px;
}

.Courses-head {
    background-color: #edffff;
}

.Courses-head>div {
    text-align: center;
    font-size: 14px;
    line-height: 28px;
}

.left-hand-TextDom,
.Courses-head {
    background-color: #f2f6f7;
}

.Courses-leftHand {
    background-color: #f2f6f7;
    font-size: 15px;
    width: 200px;
}

.Courses-leftHand .left-hand-index {
    color: #9c9c9c;
    margin-bottom: 4px !important;
}

.Courses-leftHand .left-hand-name {
    color: #666;
}

.Courses-leftHand p {
    text-align: center;
    font-weight: 900;
}

.Courses-head>div {
    border-left: none !important;
}

.Courses-leftHand>div {
    padding-top: 3px;
    border-bottom: 1px dashed rgb(219, 219, 219);
}

.Courses-leftHand>div:last-child {
    border-bottom: none !important;
}

.left-hand-TextDom,
.Courses-head {
    border-bottom: 1px solid rgba(0, 0, 0, 0.1) !important;
}

.Courses-content>ul {
    border: 1.5px dashed rgb(240, 240, 240);
    box-sizing: border-box;
    background-color: white;
}

.Courses-content>ul:nth-child(3) {
    background-color: rgba(210, 210, 210, 0.5);
}

.Courses-content>ul:nth-child(4) {
    background-color: rgba(210, 210, 210, 0.5);
}

.Courses-content>ul:nth-child(7) {
    background-color: rgba(210, 210, 210, 0.5);
}

.Courses-content>ul:nth-child(8) {
    background-color: rgba(210, 210, 210, 0.5);
}

.Courses-content>ul:nth-child(10) {
    background-color: rgba(210, 210, 210, 0.5);
}

.Courses-content>ul:nth-child(11) {
    background-color: rgba(210, 210, 210, 0.5);
}

.Courses-content>ul:last-child {
    border-bottom: none !important;
}

.highlight-week {
    color: #02a9f5 !important;
}



.Courses-content li {
    text-align: center;
    color: #666666;
    font-size: 14px;
    line-height: 50px;
}

.Courses-content li span {
    padding: 6px 2px;
    box-sizing: border-box;
    line-height: 18px;
    border-radius: 4px;
    white-space: normal;
    word-break: break-all;
    cursor: pointer;
}

.grid-active {
    z-index: 9999;
}

.grid-active span {
    box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.2);
}</style>