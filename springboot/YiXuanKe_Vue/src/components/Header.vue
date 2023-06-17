<template>
      <div style="line-height: 60px; display: flex">
        <div style="flex: 1; font-size: 25px;">
              <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
            </div>
        </div>

</template>

<script>
export default {
    name: "Header",
    data()
    {
        return {
            username:'init'
        }
    },
    props: {
        collapseBtnClass: String,
        collapse: Function,
    },
    created(){
        let token=JSON.parse(localStorage.getItem('token'))
        this.username=token.userName
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

<style>

</style>