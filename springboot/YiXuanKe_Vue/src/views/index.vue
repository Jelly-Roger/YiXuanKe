<template>
    <el-container style=" height: 100vh;margin-left:200px;">
        <!-- <div style="height: 100%; width: 200px; background-color: rgb(48, 65, 86);">
            <Aside :is-collapse="false" :logo-text-show="true" :side-width="200" ></Aside>
        </div> -->
        <div style="height: 100%; width: 100%">
            <el-carousel indicator-position="outside" height="95vh">
                <el-carousel-item v-for="item in imgSrcs" :key="item.name">
                    <img :src="item.src" width="100%" height="100%" alt="">
                </el-carousel-item>
            </el-carousel>
        </div>
    </el-container>
    
</template>

<script>
import Aside from '@/components/Aside.vue'

export default {
    components: {
        Aside,
        //Timetable,
    },
    data() {
        return {
            imgSrcs: [
                {
                    name: 1,
                    src: require("@/assets/backg.jpg")
                },
                {
                    name: 2,
                    src: require("@/assets/backg.jpg")
                },
                {
                    name: 3,
                    src: require("@/assets/backg.jpg")
                },
                {
                    name: 4,
                    src: require("@/assets/backg.jpg")
                }
            ],
        }
    },
}
</script>

<style>
</style>
