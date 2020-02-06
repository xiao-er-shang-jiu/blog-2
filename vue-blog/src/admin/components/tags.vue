<template>
    <div>
        <el-table :data="tableData" stripe style="width: 100%">
            <el-table-column
                    prop="id"
                    label="序号"
                    width="240">
                <template slot-scope="scope">
                    {{scope.$index}}
                </template>
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="名称">
                <template slot-scope="scope">
                    {{scope.row.name}}
                </template>
            </el-table-column>
            <el-table-column
                    prop="count"
                    label="文章数"
                    width="180">
                <template slot-scope="scope">
                    {{scope.row.count}}
                </template>
            </el-table-column>
            <el-table-column
                    label="操作"
                    width="240">
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            @click="handleEdit(scope.$index)">
                        编辑</el-button>
                    <el-popconfirm
                            confirmButtonText='好的'
                            cancelButtonText='不用了'
                            icon="el-icon-info"
                            iconColor="red"
                            title="您确定要删除吗？"
                            style="margin-left: 10px;"
                            @onConfirm="handleDelete(scope.row.id, scope.row.count)">
                        <el-button size="mini" type="danger" slot="reference">删除</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <el-row style="margin-top: 20px">
            <el-col :span="12">
                <el-pagination
                        background
                        :page-size="8"
                        :current-page.sync="page.current"
                        layout="prev, pager, next"
                        :total="page.total"
                        @current-change="currentChange">
                </el-pagination>
            </el-col>
            <el-col :span="12">
                <el-button
                        type="primary"
                        style="float: right"
                        @click="handleEdit(-1)">
                    创建分类</el-button>
            </el-col>
        </el-row>
        <el-dialog
                title="创建分类"
                :visible.sync="dialogVisible"
                width="30%"
                center
                @close="clearTypeForm">
            <el-form :model="tagForm" :rules="tagRules" ref="tagForm" @submit.native.prevent>
                <el-form-item prop="name">
                    <el-input v-model="tagForm.name" placeholder="分类名"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button
                        type="primary"
                        :loading="btnLoading"
                        @click="handleSubmit('tagForm')"
                        @keyup.enter="handleSubmit('tagForm')">提 交</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import DATA from "../data";

    export default {
        name: "tags",
        data () {
            return{
                dialogVisible: false,
                page: {
                    current: 1,
                    total: 0
                },
                tableData: [],
                tagForm: DATA.tagForm,
                tagRules: DATA.tagRules,
                btnLoading: false
            }
        },
        methods: {
            handleEdit (index){
                if(index >= 0){
                    this.tagForm.id = this.tableData[index].id;
                    this.tagForm.name = this.tableData[index].name;
                }
                this.dialogVisible = true;
            },
            uploadLoading () {
                this.btnLoading = true;
                this.timer = setTimeout(() => {
                    this.btnLoading = false;
                    this.dialogVisible = false;
                }, 1000)
            },
            handleSubmit (formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.uploadLoading();
                        this.uploadData();
                    }
                });
            },
            keyEnter () {
                document.onkeydown = () => {
                    if (window.event.keyCode === 13 && this.dialogVisible){
                        this.handleSubmit('tagForm')
                    }
                };
            },
            clearTypeForm () {
                this.tagForm.id = '';
                this.tagForm.name = '';
            },
            currentChange (index) {
                this.loadData(index);
            },
            handleDelete (index, count){
                if (count > 0) {
                    this.$message('当前标签不允许删除!')
                    return;
                }
                this.get(`/admin/tags/${index}/delete`, {}).then(res => {
                    this.$message({type: 'success', message: res.message});
                    this.loadData(this.page.current);
                })
            },
            uploadData () {
                this.post('/admin/tags/addOrUpdate', {
                    id: this.tagForm.id,
                    name: this.tagForm.name
                }).then(res => {
                    if(res.type === 1){
                        this.$message({type: 'success', message: res.message});
                        this.loadData(this.page.current);
                    }
                    else{
                        this.$message.error(res.message);
                    }
                })
            },
            loadData (index) {
                this.get('/admin/tags', {page: index-1}).then(res => {
                    this.tableData = res.content;
                    this.page.total = res.totalElements;
                });
            }
        },
        created() {
            this.$emit('startLoading');
            this.loadData(this.page.current);
            this.keyEnter();
        },
        destroyed() {
            clearTimeout(this.timer);
        }
    }
</script>

<style scoped>

</style>