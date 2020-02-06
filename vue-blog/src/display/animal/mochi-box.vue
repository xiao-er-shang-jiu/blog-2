<template>
    <div class="MochiBox /" :class="[shibaTmp, { pop: pop }]" @mouseenter="randomize" tabindex="0">
        <MochiShiba
                :size="sizeTmp"
                :mood="moodTmp"
                :leftEye="leftEyeTmp"
                :rightEye="rightEyeTmp"
                :leftEar="leftEarTmp"
                :rightEar="rightEarTmp"
                :blush="blushTmp"/>
        <div class="MochiContent">
            <slot></slot>
        </div>
        <MochiPaws :size="sizeTmp" />
    </div>
</template>

<script>
    import MochiShiba from './mochi-shiba'
    import MochiPaws from './mochi-paws'
    export default {
        name: "mochi-box",
        props: {
            shiba: { type: String, default: "okaka" },
            size: { type: String, default: "medium" },
            mood: { type: String, default: "" },
            leftEye: { type: String, default: "open" },
            rightEye: { type: String, default: "open" },
            leftEar: { type: String, default: "up" },
            rightEar: { type: String, default: "flat" },
            blush: { type: Boolean, default: false }
        },
        data () {
            return {
                pop: true,
                shibaList: ["ume", "sesame", "tuna", "okaka", "anko", "kinako", "sakura", "monaka"],
                moodList: ["", "happy", "content", "excited", "cheeky", "drool", "cute", "gleam"],
                eyeList: ["open", "wink", "shy", "laugh"],
                earList: ["up", "down", "flat", "middle"],
                blushed: [true, false],
                shibaTmp: this.shiba,
                moodTmp: this.mood,
                leftEyeTmp: this.leftEye,
                rightEyeTmp: this.rightEye,
                leftEarTmp: this.leftEar,
                rightEarTmp: this.rightEar,
                blushTmp: this.blush,
                sizeTmp: this.size
            }
        },
        created() {
            if ( this.shiba === "random" ) {
                this.canRandom = true;
                this.randomize();
            }
        },
        methods: {
            randomize() {
                if ( this.canRandom ) {
                    this.shibaTmp = this.shibaList[Math.floor((Math.random()*8))];
                    this.moodTmp = this.moodList[Math.floor((Math.random()*8))];
                    this.leftEyeTmp = this.eyeList[Math.floor((Math.random()*4))];
                    this.rightEyeTmp = this.eyeList[Math.floor((Math.random()*4))]
                    this.leftEarTmp = this.earList[Math.floor((Math.random()*4))]
                    this.rightEarTmp = this.earList[Math.floor((Math.random()*4))]
                    this.blushTmp = this.blushed[Math.floor((Math.random()*2))]
                }
            }
        },
        mounted() {
            let time = 3000 + Math.random() * 2000;
            setTimeout(()=>{
                this.pop = false;
            },time);
        },
        components: {
            MochiShiba,
            MochiPaws
        }
    }
</script>

<style scoped>
    @import "css/style.css";
</style>