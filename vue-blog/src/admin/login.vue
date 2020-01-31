<template>
    <Layout :style="{height: '100vh', background: 'none', opacity: 0.9}">
        <Row :style="{height: '100%'}" type="flex" justify="center" align="middle">
            <Card :style="{width: '35%'}" align="center" :padding="50">
                <h2 slot="title">后台管理登陆</h2>
                <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" align="center" :label-width="80" >
                    <form-item label="用户名" prop="username">
                        <i-input v-model="formValidate.username" placeholder="用户名..."></i-input>
                    </form-item>
                    <form-item label="密码" prop="password">
                        <i-input v-model="formValidate.password" type="password" password placeholder="密码..."></i-input>
                    </form-item>
                </Form>
                <Button
                        type="primary"
                        :loading="loading"
                        @click="handleSubmit('formValidate')"
                >登陆</Button>
            </Card>
        </Row>
    </Layout>
</template>

<script>
    import md5 from 'js-md5'
    export default {
        name: "login",
        data () {
            return{
                formValidate: {
                    username: '',
                    password: ''
                },
                ruleValidate: {
                    username: [
                        { required: true, message: '用户名不能为空', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '密码不能为空', trigger: 'blur' }
                    ]
                },
                loading: false
            }
        },
        created() {
            let that = this;
            document.onkeydown = function () {
                if (window.event.keyCode === 13){
                    that.handleSubmit('formValidate')
                }
            };
        },
        methods: {
            handleSubmit(name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        this.loading = true;
                        this.post('/admin/login', {
                            username: this.formValidate.username,
                            password: md5(this.formValidate.password)
                        }).then(res => {
                            if(res.type === 1){
                                this.$Message.success(res.message);
                                let that = this;
                                this.timer = setTimeout(function () {
                                    that.loading = false;
                                    that.$router.push({name: 'home'})
                                },1000)
                            }
                            else {
                                this.$Message.error(res.message);
                                let that = this;
                                this.timer = setTimeout(function () {
                                    that.loading = false;
                                }, 1000);
                            }
                        }).catch(err => {
                            // eslint-disable-next-line no-console
                            console.log(err);
                        })
                    } else {
                        this.$Message.error('请填写完整!');
                    }
                })
            }
        },
        destroyed() {
            clearTimeout(this.timer);
        }
    }
</script>

<style scoped>

</style>