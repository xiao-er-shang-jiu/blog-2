<template>
    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80" class="form">
        <FormItem label="分类名称" prop="name">
            <i-input v-model="formValidate.name" placeholder="请输入分类名称..."></i-input>
        </FormItem>
        <form-item>
            <Button type="primary" @click="handleSubmit('formValidate')">提交</Button>
            <Button type="dashed" @click="$router.go(-1)" :style="{marginLeft: '20px'}">返回</Button>
        </form-item>
    </Form>
</template>

<script>
    export default {
        name: "type-add",
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
                        this.post("/admin/types/addOrUpdate", {
                            id: this.id,
                            name: this.formValidate.name
                        }).then(res => {
                            if(res.type === 1){
                                this.$Message.success(res.message);
                                this.$router.push({name: 'types'});
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
            this.$emit('handleActiveName', 's-4-2');
            this.$emit('handleOpenNames', 's-4');
            this.$emit('handleBreadCrumb', ['后台管理', '分类管理', '新增分类']);
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