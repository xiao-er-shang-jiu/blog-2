<template>
    <el-row class="page-container" type="flex" justify="center" align="middle">
        <transition-group name="bg">
            <div :key="1" v-show="bgImgIndex === 1" class="bg-container">
                <img src="https://lhy-blog.oss-cn-hangzhou.aliyuncs.com/background/1.jpg">
            </div>
            <div :key="2" v-show="bgImgIndex === 2" class="bg-container">
                <img src="https://lhy-blog.oss-cn-hangzhou.aliyuncs.com/background/2.jpg">
            </div>
            <div :key="3" v-show="bgImgIndex === 3" class="bg-container">
                <img src="https://lhy-blog.oss-cn-hangzhou.aliyuncs.com/background/3.jpg">
            </div>
        </transition-group>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm">
            <el-form-item>
                <h1>后台登陆</h1>
            </el-form-item>
            <el-form-item prop="username">
                <el-input v-model="ruleForm.username" placeholder="Username"></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input v-model="ruleForm.password" placeholder="Password" type="password"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button
                        @keyup.enter="handleSubmit('ruleForm')"
                        @click="handleSubmit('ruleForm')"
                        type="primary"
                        style="width: 100%"
                        :loading="loading">
                    登 录</el-button>
            </el-form-item>
        </el-form>
    </el-row>
</template>

<script>
    import md5 from 'js-md5'
    import DATA from './data'
    export default {
        name: "login",
        data () {
            return{
                bgImgIndex: 1,
                loading: false,
                ruleForm: DATA.loginForm,
                rules: DATA.loginRules
            }
        },
        created() {
           this.keyEnter();
        },
        methods: {
            login () {
                this.post('/admin/login', {
                    username: this.ruleForm.username,
                    password: md5(this.ruleForm.password)
                }).then(res => {
                    if (res.type === 1){
                        this.$message({message: res.message, type: 'success'});
                        this.$router.push({name: 'admin'});
                    } else {
                        this.$message.error(res.message);
                    }
                })
            },
            handleLoading () {
                this.loading = true;
                this.timeout = setTimeout(() => {
                    this.login();
                }, 1000)
            },
            keyEnter () {
                document.onkeydown = () => {
                    if (window.event.keyCode === 13){
                        this.handleSubmit('ruleForm')
                    }
                };
            },
            handleSubmit (formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.handleLoading();
                    }
                });
            }
        },
        mounted() {
            this.interval = setInterval( () => {
                if(this.bgImgIndex === 3)
                    this.bgImgIndex = 1;
                else
                    this.bgImgIndex++;
            }, 5000)
        },
        destroyed() {
            clearInterval(this.interval);
            clearTimeout(this.timeout);
        }
    }
</script>

<style scoped>
    .page-container {
        width: 100%;
        height: 100vh;
        position: relative;
        color: white;
    }
    .bg-container{
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        z-index: -1;
        overflow: hidden;
    }
    .bg-container img{
        width: 100%;
        height: 100%;
    }
    .bg-enter-active, .bg-leave-active {
        transition: all 1s linear;
    }
    .bg-enter-to, .bg-leave{
        opacity: 1;
    }
    .bg-enter, .bg-leave-to {
        opacity: 0;
    }
    h1 {
        font-size: 30px;
        font-weight: 700;
        text-shadow: 0 1px 4px rgba(0,0,0,.2);
    }
    form {
        width: 300px;
        text-align: center;
    }
</style>