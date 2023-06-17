<template>
    <div>
        <div id="codeBox">
            <canvas width="112" height="38" id="verifyCanvas"></canvas>
        </div>
        <img id="code_img" @click="refreshCode" />
    </div>
</template>

<script>
export default {
    data() {
        return {
            // 验证码加入的随机总内容，可自行加入英文字母
            nums: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "0"],
            code: "",
        };
    },
    created() { },
    mounted() {
        this.drawCode();
        // 监听父组件触发子组件事件
        this.$on("bridge", (val) => {
            this.refreshCode();
        });
    },
    methods: {
        // 绘制验证码
        drawCode() {
            // 获取HTML端画布
            var canvas = document.getElementById("verifyCanvas");
            // 获取画布2D上下文
            var context = canvas.getContext("2d");
            // 画布填充色
            context.fillStyle = "cornflowerblue";
            context.fillRect(0, 0, canvas.width, canvas.height);
            // 创建渐变
            var gradient = context.createLinearGradient(0, 0, canvas.width, 0);
            gradient.addColorStop("0", "magenta");
            gradient.addColorStop("0.5", "blue");
            gradient.addColorStop("1.0", "red");
            // 清空画布
            // 设置字体颜色
            context.fillStyle = gradient;
            // 设置字体
            context.font = "25px Arial";
            // 生成的验证码数组
            var rand = [];
            // 验证码位置
            var x = [];
            var y = [];
            for (let i = 0; i < 4; i++) {
                rand[i] = this.nums[Math.floor(Math.random() * this.nums.length)];
                x[i] = i * 16 + 20;
                y[i] = Math.random() * 20 + 20;
                context.fillText(rand[i], x[i], y[i]);
            }
            this.code = rand.join("");
            // 触发getCode事件,传值验证码给父组件
            this.$emit("getCode", this.code);
            // 画3条随机线
            for (let i = 0; i < 3; i++) {
                this.drawline(canvas, context);
            }

            // 画30个随机点
            for (let i = 0; i < 30; i++) {
                this.drawDot(canvas, context);
            }
            this.convertCanvasToImage(canvas);
        },
        // 随机线
        drawline(canvas, context) {
            // 随机线的起点x坐标是画布x坐标0位置，y坐标是画布高度的随机数
            context.moveTo(
                Math.floor(Math.random() * canvas.width),
                Math.floor(Math.random() * canvas.height)
            );
            // 随机线的终点x坐标是画布宽度，y坐标是画布高度的随机数
            context.lineTo(
                Math.floor(Math.random() * canvas.width),
                Math.floor(Math.random() * canvas.height)
            );
            // 随机线宽
            context.lineWidth = 0.5;
            // 随机线描边属性
            context.strokeStyle = "rgba(50,50,50,0.3)";
            // 描边，即起点描到终点
            context.stroke();
        },
        // 随机点(所谓画点其实就是画1px像素的线，方法不再赘述)
        drawDot(canvas, context) {
            var px = Math.floor(Math.random() * canvas.width);
            var py = Math.floor(Math.random() * canvas.height);
            context.moveTo(px, py);
            context.lineTo(px + 1, py + 1);
            context.lineWidth = 0.2;
            context.stroke();
        },
        // 绘制图片
        convertCanvasToImage(canvas) {
            document.getElementById("verifyCanvas").style.display = "none";
            var image = document.getElementById("code_img");
            image.src = canvas.toDataURL("image/png");
            return image;
        },
        // 刷新验证码
        refreshCode() {
            var parent = document.getElementById("codeBox");
            var codeObj = document.getElementById("verifyCanvas");
            parent.removeChild(codeObj);
            var canvas = document.createElement("canvas");
            canvas.width = "112";
            canvas.height = "38";
            canvas.id = "verifyCanvas";
            parent.appendChild(canvas);
            this.drawCode();
        },
    },
};
</script>
<style scoped>
#code_img {
    width: 112px;
    height: 38px;
    cursor: pointer;
    vertical-align: top;
}
</style>
  