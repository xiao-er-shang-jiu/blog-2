<template>
    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80" class="form">
        <FormItem label="标签名称" prop="name">
            <i-input v-model="formValidate.name" placeholder="请输入标签名称..."></i-input>
        </FormItem>
        <form-item>
            <Button type="primary" @click="handleSubmit('formValidate')">提交</Button>
            <Button type="dashed" @click="$router.go(-1)" :style="{marginLeft: '20px'}">返回</Button>
        </form-item>
    </Form>
</template>

<script>
    export default {
        name: "tags-add",
        data () {
            return{
                id: '',
                formValidate: {
                    name: ''
                },
                ruleValidate: {
                    name: [
                        { required: true, message: '名称不能为空', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            handleSubmit (name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        this.post("/admin/tags/addOrUpdate", {
                            id: this.id,
                            name: this.formValidate.name
                        }).then(res => {
                            if(res.type === 1){
                                this.$Message.success(res.message);
                                this.$router.push({name: 'tags'});
                            }
                            else{
                                this.$Message.error(res.message);
                            }
                        }).catch(err => {
                            // eslint-disable-next-line no-console
                            console.log(err);
                        })
                    } else {
                        this.$Message.error('请填写完整!');
                    }
                })
            },
        },
        created() {
            this.$emit('handleActiveName', 's-5-2');
            this.$emit('handleOpenNames', 's-5');
            this.$emit('handleBreadCrumb', ['后台管理', '标签管理', '新增标签']);
        },
        mounted() {
            if(this.$route.params.id !== '-1'){
                this.id = this.$route.params.id;
                this.formValidate.name = this.$route.params.name;
            }
        }
    }
</script>

<style scoped>
    .form{
        width: 50%;
        text-align: center;
    }
</style>