<template>
    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80" class="form">
        <FormItem label="标题" prop="title">
            <i-input v-model="formValidate.title" placeholder="请输入博客标题..."></i-input>
        </FormItem>
        <FormItem prop="content">
            <mavon-editor v-model="formValidate.content"></mavon-editor>
        </FormItem>
        <FormItem label="分类" prop="type">
            <RadioGroup v-model="formValidate.type">
                <Radio label="金斑蝶"></Radio>
                <Radio label="爪哇犀牛"></Radio>
                <Radio label="印度黑羚"></Radio>
                <Radio label="金斑蝶"></Radio>
                <Radio label="爪哇犀牛"></Radio>
                <Radio label="印度黑羚"></Radio>
                <Radio label="金斑蝶"></Radio>
                <Radio label="爪哇犀牛"></Radio>
                <Radio label="金斑蝶"></Radio>
                <Radio label="爪哇犀牛"></Radio>
                <Radio label="印度黑羚"></Radio>
                <Radio label="印度黑羚"></Radio>
            </RadioGroup>
        </FormItem>
        <FormItem label="标签" prop="tag">
            <CheckboxGroup v-model="formValidate.tag">
                <Checkbox label="金斑蝶"></Checkbox>
                <Checkbox label="爪哇犀牛"></Checkbox>
                <Checkbox label="印度黑羚"></Checkbox>
                <Checkbox label="金斑蝶"></Checkbox>
                <Checkbox label="爪哇犀牛"></Checkbox>
                <Checkbox label="印度黑羚"></Checkbox>
                <Checkbox label="金斑蝶"></Checkbox>
                <Checkbox label="爪哇犀牛"></Checkbox>
                <Checkbox label="金斑蝶"></Checkbox>
                <Checkbox label="爪哇犀牛"></Checkbox>
                <Checkbox label="印度黑羚"></Checkbox>
                <Checkbox label="印度黑羚"></Checkbox>
            </CheckboxGroup>
        </FormItem>
        <FormItem label="类型" prop="flag">
            <RadioGroup v-model="formValidate.flag">
                <Radio label="原创"></Radio>
                <Radio label="转载"></Radio>
                <Radio label="翻译"></Radio>
            </RadioGroup>
        </FormItem>
        <FormItem label="首图" prop="firstPicture">
            <i-input v-model="formValidate.firstPicture" placeholder="请输入博客标题..."></i-input>
        </FormItem>
        <FormItem label="推荐" :style="{float: 'left'}">
            <i-switch v-model="formValidate.recommend">
                <span slot="true">On</span>
                <span slot="false">Off</span>
            </i-switch>
        </FormItem>
        <FormItem label="转载声明" :style="{float: 'left'}">
            <i-switch v-model="formValidate.shareStatement">
                <span slot="true">On</span>
                <span slot="false">Off</span>
            </i-switch>
        </FormItem>
        <FormItem label="赞赏" :style="{float: 'left'}">
            <i-switch v-model="formValidate.appreciation">
                <span slot="true">On</span>
                <span slot="false">Off</span>
            </i-switch>
        </FormItem>
        <FormItem label="评论" :style="{float: 'left'}">
            <i-switch v-model="formValidate.commentabled">
                <span slot="true">On</span>
                <span slot="false">Off</span>
            </i-switch>
        </FormItem>
        <FormItem :style="{clear: 'both'}">
            <Button type="primary" @click="handleSubmit('formValidate')">Submit</Button>
            <Button @click="handleReset('formValidate')" style="margin-left: 8px">Reset</Button>
        </FormItem>
    </Form>
</template>
<script>
    import mavonEditor from 'mavon-editor'

    export default {
        data () {
            return {
                formValidate: {
                    title: '',
                    content: '',
                    type: '',
                    tag: [],
                    flag: '',
                    firstPicture: '',
                    recommend: true,
                    shareStatement: true,
                    appreciation: true,
                    commentabled: true
                },
                ruleValidate: {
                    title: [
                        { required: true, message: '标题不能为空', trigger: 'blur' }
                    ],
                    content: [
                        { required: true, message: '内容不能为空' }
                    ],
                    type: [
                        { required: true, message: '分类不能为空', trigger: 'change' }
                    ],
                    tag: [
                        { required: true, type: 'array', min: 1, message: 'Choose at least one hobby', trigger: 'change' }
                    ],
                    flag: [
                        { required: true, message: '类型不能为空', trigger: 'change' }
                    ],
                    firstPicture: [
                        { required: true, message: '首图不能为空', trigger: 'blur' }
                    ],
                }
            }
        },
        methods: {
            handleSubmit (name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        this.$Message.success('Success!');
                    } else {
                        this.$Message.error('Fail!');
                    }
                })
            },
            handleReset (name) {
                this.$refs[name].resetFields();
            }
        },
        components: {
            'mavonEditor': mavonEditor
        }
    }
</script>

<style scoped>
    @import "~mavon-editor/dist/css/index.css";
    .form{
        width: 100%;
    }
</style>
