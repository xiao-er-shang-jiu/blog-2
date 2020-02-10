<template>
    <el-card>
        <div slot="header" class="clearfix">
            <h2 style="color: #ffd04b;display: inline-block;margin:0">搜索结果</h2>
            <p style="float: right;margin: 0">
                共 <span style="color: #ffd04b;font-size: 22px">{{total}}</span> 篇
            </p>
        </div>
        <div v-if="!blogList.length" style="text-align: center;line-height: 0;">
            <img src="../../assets/img/empty.jpg" style="width: 350px" />
            <p style="line-height: 32px;color: #909399;font-size: 16px">暂无搜索结果...</p>
        </div>
        <template v-for="(blog, index) in blogList">
            <el-row :key="index" type="flex" align="middle">
                <el-col :span="15">
                    <el-row type="flex" align="middle">
                        <el-col :span="3" style="line-height: 0">
                            <el-avatar shape="square" :size="40" :src="blog.user.avatar"></el-avatar>
                        </el-col>
                        <el-col :span="21">
                            <el-link
                                    @click="$router.push({path: `/blog-detail/${blog.id}`})"
                                    style="font-size: 15px"
                            ><strong>{{blog.title}}</strong></el-link>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :offset="2" :span="22" class="des-content">
                            {{transformDes(blog.description)}}
                        </el-col>
                    </el-row>
                    <el-row style="line-height: 42px;text-align: center;font-size: 13px">
                        <el-col :span="6" style="color: #00b5ad">
                            <i class="el-icon-user"></i> {{blog.user.nickname}}
                        </el-col>
                        <el-col :span="6" style="color:#909399">
                            <i class="el-icon-time"></i> {{blog.createTime.substring(0, 10)}}
                        </el-col>
                        <el-col :span="3" style="color:#909399">
                            <i class="el-icon-view"></i> {{blog.views}}
                        </el-col>
                        <el-col :span="3" style="color: #909399">
                            <i class="el-icon-thumb"></i> {{blog.admire}}
                        </el-col>
                        <el-col :span="6">
                            <el-tag
                                    style="cursor: pointer"
                                    :type="tagColor[parseInt(Math.random()*5,0)]"
                                    @click="$router.push({path: `/types/${blog.type.id}`})"
                            >{{blog.type.name}}</el-tag>
                        </el-col>
                    </el-row>
                </el-col>
                <el-col :span="9" style="line-height: 0;text-align: right">
                    <el-image
                            :src="blog.firstPicture"
                            style="width: 300px"
                            lazy>
                    </el-image>
                </el-col>
            </el-row>
            <el-divider :key="index"></el-divider>
        </template>
        <el-pagination
                v-if="blogList.length"
                background
                :page-size="6"
                layout="prev, pager, next"
                :total="total"
                @current-change="handlePageChange">
        </el-pagination>
    </el-card>
</template>

<script>
    export default {
        name: "search-result",
        data () {
            return{
                total: 0,
                blogList: [],
                tagColor: ['', 'success', 'info', 'warning', "danger"]
            }
        },
        methods: {
            transformDes (des) {
                if (des.length >= 150) {
                    des = des.substring(1, 150) + "...";
                }
                return des;
            },
            handlePageChange (index) {
                this.loadBlogList(index);
            },
            loadBlogList (index) {
                this.post('/search', {
                    query: this.pathText,
                    page: index - 1
                }).then(res => {
                    this.total = res.totalElements;
                    this.blogList = res.content;
                })
            }
        },
        created() {
            this.loadBlogList(1);
        },
        computed: {
            pathText() {
                return this.$route.params.text;
            }
        },
        watch: {
            pathText() {
                this.loadBlogList(1);
            }
        }
    }
</script>

<style scoped>
    .des-content{
        line-height: 24px;
        color: #909399;
        font-size: 14px;
    }
</style>