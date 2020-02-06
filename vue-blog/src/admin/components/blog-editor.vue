<template>
    <el-form
            :model="editorForm"
            :rules="editorRules"
            ref="editorForm"
            label-width="60px"
            label-position="left">
        <el-form-item label="标题" prop="title">
            <el-input v-model="editorForm.title" placeholder="标题"></el-input>
        </el-form-item>
        <el-form-item label="编辑" prop="content">
            <mavon-editor
                    v-model="editorForm.content"
                    style="max-height: 100vh"
                    codeStyle="darcula"
                    :tabSize="4"
                    fontSize="15px"
                    @save="save">
            </mavon-editor>
        </el-form-item>
        <el-row>
            <el-col :span="12">
                <el-form-item label="分类" prop="type.id">
                    <el-select
                            v-model="editorForm.type.id"
                            placeholder="请选择分类"
                            style="width: 80%">
                        <template v-for="type in types">
                            <el-option :key="type.id" :label="type.name" :value="type.id"></el-option>
                        </template>
                    </el-select>
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="标签" prop="tagIds">
                    <el-select
                            v-model="editorForm.tagIds"
                            multiple
                            placeholder="请选择标签"
                            style="width: 80%">
                        <template v-for="tag in tags">
                            <el-option :key="tag.id" :label="tag.name" :value="tag.id"></el-option>
                        </template>
                    </el-select>
                </el-form-item>
            </el-col>
        </el-row>
        <el-form-item label="类型" prop="flag">
            <el-radio-group v-model="editorForm.flag">
                <el-radio label="原创"></el-radio>
                <el-radio label="转载"></el-radio>
                <el-radio label="翻译"></el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="描述" prop="description">
            <el-input
                    type="textarea"
                    :rows="4"
                    placeholder="请输入内容描述"
                    :max="200"
                    v-model="editorForm.description">
            </el-input>
        </el-form-item>
        <el-form-item label="首图" prop="firstPicture">
            <el-input v-model="editorForm.firstPicture" placeholder="首图链接"></el-input>
        </el-form-item>
        <el-row type="flex" justify="start">
            <el-form-item label="评论" label-width="40px" style="margin-left: 10px">
                <el-switch
                        v-model="editorForm.commentabled"
                        active-color="#13ce66"
                        inactive-color="#ff4949">
                </el-switch>
            </el-form-item>
            <el-form-item label="转载声明" label-width="70px" style="margin-left: 10px">
                <el-switch
                        v-model="editorForm.shareStatement"
                        active-color="#13ce66"
                        inactive-color="#ff4949">
                </el-switch>
            </el-form-item>
            <el-form-item label="推荐" label-width="40px" style="margin-left: 10px">
                <el-switch
                        v-model="editorForm.recommend"
                        active-color="#13ce66"
                        inactive-color="#ff4949">
                </el-switch>
            </el-form-item>
            <el-form-item label="赞赏" label-width="40px" style="margin-left: 10px">
                <el-switch
                        v-model="editorForm.appreciation"
                        active-color="#13ce66"
                        inactive-color="#ff4949">
                </el-switch>
            </el-form-item>
        </el-row>
        <el-form-item style="text-align: right">
            <el-button @click="handleSubmit('editorForm', false)">保 存</el-button>
            <el-button type="primary" @click="handleSubmit('editorForm', true)">发 布</el-button>
        </el-form-item>
    </el-form>
</template>
<script>
    import DATA from '../data'
    import {transformEditorPost} from "../../util/util";
    export default {
        data () {
            return {
                types: [],
                tags: [],
                editorForm: DATA.editorForm,
                editorRules: DATA.editorRules,
            }
        },
        methods: {
            save () {
                this.saveStorage(this.editorForm);
                this.$message({type: 'success', message: '保存成功!'});
            },
            handleSubmit (formName, published) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.editorForm.published = published;
                        this.publishBlog();
                    }
                });
            },
            publishBlog () {
                this.post('/admin/blogs', transformEditorPost(this.editorForm)).then(res => {
                    if(res.type === 1){
                        this.$message({type: 'success', message: res.message});
                        this.removeStorage();
                        this.$router.push({name: 'blogs'});
                    }
                });
            },
            loadTypes () {
                this.get('/admin/types/getAll', {})
                    .then(res => {
                        this.types = res;
                    });
            },
            loadTags () {
                this.get('/admin/tags/getAll', {})
                    .then(res => {
                        this.tags = res;
                    });
            },
            loadLocalStorage () {
                if (this.loadStorage() !== null) {
                    this.editorForm = this.loadStorage();
                }
            },
            loadBlog (id) {
                this.get(`/admin/blogs/${id}/input`, {})
                    .then(res => {
                        this.editorForm = res;
                    });
            }
        },
        created() {
            this.$emit('startLoading');

            this.loadTypes();
            this.loadTags();

            if (this.$route.params.id !== '0') {
                this.loadBlog(this.$route.params.id)
            } else {
                this.loadLocalStorage();
            }
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
