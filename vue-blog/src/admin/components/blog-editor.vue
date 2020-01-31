<template>
    <Row :style="{width: '100%'}">
        <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80" class="form">
            <FormItem label="标题" prop="title">
                <i-input v-model="formValidate.title" placeholder="请输入博客标题..."></i-input>
            </FormItem>
            <FormItem label="内容">
                <mavon-editor
                        :tabSize="4"
                        codeStyle="darcula"
                        v-model="formValidate.content"
                        :style="{zIndex: 100}"
                        @save="save"
                ></mavon-editor>
            </FormItem>
            <FormItem label="分类" prop="typeId">
                <RadioGroup v-model="formValidate.typeId">
                    <Radio v-for="type in types" :label="type.id" :key="type.name">{{type.name}}</Radio>
                </RadioGroup>
            </FormItem>
            <FormItem label="标签" prop="tagIds">
                <CheckboxGroup v-model="formValidate.tagIds">
                    <Checkbox v-for="tag in tags" :label="tag.id" :key="tag.id">{{tag.name}}</Checkbox>
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
                <Row type="flex" justify="end">
                    <Button type="dashed" @click="handleSave()">保存</Button>
                    <Button type="primary" @click="handleSubmit('formValidate')" :style="{marginLeft: '20px'}">发布</Button>
                </Row>
            </FormItem>
        </Form>
        <Spin size="large" fix v-if="loading" :style="{zIndex: 9999}"></Spin>
    </Row>
</template>
<script>
    export default {
        data () {
            return {
                types: [],
                tags: [],
                formValidate: {
                    id: '',
                    title: '',
                    content: '',
                    typeId: '',
                    tagIds: [],
                    flag: '',
                    firstPicture: '',
                    recommend: true,
                    shareStatement: true,
                    appreciation: true,
                    commentabled: true,
                    published: ''
                },
                ruleValidate: {
                    title: [
                        { required: true, message: '标题不能为空', trigger: 'blur' }
                    ],
                    typeId: [
                        { required: true, type: 'integer', message: '分类不能为空', trigger: 'change' }
                    ],
                    tagIds: [
                        { required: true, type: 'array', min: 1, message: '至少选择一个标签', trigger: 'change' }
                    ],
                    flag: [
                        { required: true, message: '类型不能为空', trigger: 'change' }
                    ],
                    firstPicture: [
                        { required: true, message: '首图不能为空', trigger: 'blur' }
                    ],
                },
                loading: true
            }
        },
        methods: {
            save () {
                this.saveBlog(this.formValidate);
                this.$Message.success('保存成功!');
            },
            handleSubmit (name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        this.formValidate.published = true;
                        this.publishBlog();
                    } else {
                        this.$Message.error('请填写完整!');
                    }
                })
            },
            handleSave () {
                this.formValidate.published = false;
                this.publishBlog();
            },
            publishBlog () {
                this.post('/admin/blogs', this.formValidate).then(res => {
                    if(res.type === 1){
                        this.$Message.success(res.message);
                        this.removeBlog();
                        this.$router.push({name: 'blogs'});
                    }
                }).catch(err => {
                    // eslint-disable-next-line no-console
                    console.log(err);
                })
            }
        },
        created() {
            this.get('/admin/types/getAll', {})
                .then(res => {
                    this.types = res;
                })
                .catch(err => {
                    // eslint-disable-next-line no-console
                    console.log(err);
                });
            this.get('/admin/tags/getAll', {})
                .then(res => {
                    this.tags = res;
                })
                .catch(err => {
                    // eslint-disable-next-line no-console
                    console.log(err);
                });

            if(this.$route.params.id === '0' && this.loadBlog() !== null){
                this.formValidate = this.loadBlog();
            }
            if(this.$route.params.id !== '0'){
                this.get(`/admin/blogs/${this.$route.params.id}/input`, {})
                    .then(res => {
                        this.formValidate.id = res.id;
                        this.formValidate.title = res.title;
                        this.formValidate.content = res.content;
                        this.formValidate.typeId = res.type.id;
                        this.formValidate.tagIds = res.tagIds;
                        this.formValidate.flag = res.flag;
                        this.formValidate.firstPicture = res.firstPicture;
                        this.formValidate.recommend = res.recommend;
                        this.formValidate.shareStatement = res.shareStatement;
                        this.formValidate.appreciation = res.appreciation;
                        this.formValidate.commentabled = res.commentabled;
                    })
                    .catch(err => {
                        // eslint-disable-next-line no-console
                        console.log(err);
                    })
            }
            this.$emit('handleActiveName', 's-2');
            this.$emit('handleBreadCrumb', ['后台管理', '博客编辑']);
        },
        mounted() {
            let that = this;
            this.timer = setTimeout(function () {
                that.loading = false;
            },1000)
        },
        destroyed() {
            clearTimeout(this.timer)
        }
    }
</script>

<style scoped>
    .form{
        width: 100%;
    }
</style>
