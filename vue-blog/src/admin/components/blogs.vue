<template>
    <div>
        <el-form :inline="true" :model="query" @submit.native.prevent>
            <el-form-item>
                <el-input v-model="query.title" placeholder="标题"></el-input>
            </el-form-item>
            <el-form-item>
                <el-select v-model="query.typeId" placeholder="分类">
                    <template v-for="type in types">
                        <el-option
                                :label="type.name"
                                :value="type.id"
                                :key="type.id">
                        </el-option>
                    </template>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-checkbox v-model="query.recommend">推荐</el-checkbox>
            </el-form-item>
            <el-form-item>
                <el-button
                        type="primary"
                        icon="el-icon-search"
                        @click="search"
                        @keyup.enter="search">
                    搜 索</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="tableData" stripe style="width: 100%">
            <el-table-column
                    prop="id"
                    label="序号"
                    width="60">
                <template slot-scope="scope">
                    {{scope.$index}}
                </template>
            </el-table-column>
            <el-table-column
                    prop="title"
                    label="标题"
                    width="320">
                <template slot-scope="scope">
                    {{scope.row.title}}
                </template>
            </el-table-column>
            <el-table-column
                    prop="flag"
                    label="类型"
                    width="60">
                <template slot-scope="scope">
                    {{scope.row.flag}}
                </template>
            </el-table-column>
            <el-table-column
                    prop="published"
                    label="状态"
                    width="80">
                <template slot-scope="scope">
                    {{scope.row.published === true ? '已发布':'草稿'}}
                </template>
            </el-table-column>
            <el-table-column
                    prop="updateTime"
                    label="更新时间"
                    width="160">
                <template slot-scope="scope">
                    {{timeTransform(scope.row.updateTime)}}
                </template>
            </el-table-column>
            <el-table-column
                    prop="views"
                    label="浏览"
                    width="60">
                <template slot-scope="scope">
                    {{scope.row.views}}
                </template>
            </el-table-column>
            <el-table-column
                    prop="admire"
                    label="点赞"
                    width="60">
                <template slot-scope="scope">
                    {{scope.row.admire}}
                </template>
            </el-table-column>
            <el-table-column
                    prop="appreciation"
                    label="赞赏"
                    width="60">
                <template slot-scope="scope">
                    {{scope.row.appreciation === true ? '开启':'关闭'}}
                </template>
            </el-table-column>
            <el-table-column
                    prop="shareStatement"
                    label="转载"
                    width="60">
                <template slot-scope="scope">
                    {{scope.row.shareStatement === true ? '开启':'关闭'}}
                </template>
            </el-table-column>
            <el-table-column
                    prop="commentabled"
                    label="评论"
                    width="60">
                <template slot-scope="scope">
                    {{scope.row.commentabled === true ? '开启':'关闭'}}
                </template>
            </el-table-column>
            <el-table-column
                    prop="recommend"
                    label="推荐"
                    width="60">
                <template slot-scope="scope">
                    {{scope.row.recommend === true ? '是':'否'}}
                </template>
            </el-table-column>
            <el-table-column
                    label="操作"
                    fixed="right"
                    width="180">
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            @click="$router.push({path: `/admin/blog-editor/${scope.row.id}`})">
                        编辑</el-button>
                    <el-popconfirm
                            confirmButtonText='好的'
                            cancelButtonText='不用了'
                            icon="el-icon-info"
                            iconColor="red"
                            title="您确定要删除吗？"
                            style="margin-left: 10px;"
                            @onConfirm="handleDelete(scope.row.id)">
                        <el-button size="mini" type="danger" slot="reference">删除</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
                background
                style="margin-top: 20px"
                :page-size="8"
                :current-page.sync="page.current"
                layout="prev, pager, next"
                :total="page.total"
                @current-change="currentChange">
        </el-pagination>
    </div>
</template>

<script>
    import {timeFormatYMDHMS} from "../../util/util";

    export default {
        name: "blogs",
        data () {
            return{
                tableData: [],
                page: {
                    current: 1,
                    total: 0
                },
                query: {
                    title: null,
                    typeId: null,
                    recommend: false
                },
                types: []
            }
        },
        methods: {
            timeTransform (time) {
                return timeFormatYMDHMS(time)
            },
            search () {
                this.page.current = 1;
                this.loadBlogs(this.page.current);
            },
            keyEnter () {
                document.onkeydown = () => {
                    if (window.event.keyCode === 13 && this.dialogVisible){
                        this.handleSubmit('typeForm')
                    }
                };
            },
            currentChange (index) {
                this.loadBlogs(index);
            },
            handleDelete (index) {
                this.get(`/admin/blogs/${index}/delete`, {}).then(res => {
                    if (res.type === 1){
                        this.$message({type: 'success', message: res.message});
                        this.loadBlogs(this.page.current);
                    }
                })
            },
            loadBlogs (index) {
                this.post('admin/blogs/search', {
                    title: this.query.title,
                    typeId: this.query.typeId,
                    recommend: this.query.recommend,
                    page: index - 1
                }).then(res => {
                    this.tableData = res.content;
                    this.page.total = res.totalElements;
                })
            },
            loadTypes () {
                this.get('/admin/types/getAll', {}).then(res => {
                    this.types = res;
                })
            }
        },
        created() {
            this.$emit('startLoading');
            this.loadTypes();
            this.loadBlogs(this.page.current);
            this.keyEnter();
        },
        destroyed() {
            clearTimeout(this.timer);
        }
    }
</script>

<style scoped>

</style>