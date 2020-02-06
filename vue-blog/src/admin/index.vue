<template>
    <el-container>
        <el-header class="admin-header">
            <el-row type="flex" align="middle" style="height: 100%">
                <el-col :span="12">
                    <el-row type="flex" align="middle">
                        <el-col :span="2">
                            <img src="../assets/img/logo-icon.png" style="float: left"/>
                        </el-col>
                        <el-col :span="22">
                            <h2 style="margin: 0">后台管理系统</h2>
                        </el-col>
                    </el-row>
                </el-col>
                <el-col :span="12">
                    <el-menu
                            mode="horizontal"
                            :default-active="$route.path"
                            active-text-color="#FFC940"
                            style="float: right">
                        <el-menu-item
                                index="/admin/blog-editor/0"
                                @click="$router.push({path: '/admin/blog-editor/0'})">
                            <i class="el-icon-edit"></i>
                            <span slot="title">写博客</span>
                        </el-menu-item>
                        <el-submenu index="me">
                            <template slot="title">
                                <el-avatar :size="40" :src="adminInfo.avatar"></el-avatar>
                                {{adminInfo.nickname}}
                            </template>
                            <el-menu-item index="logout" @click="logout">退出登录</el-menu-item>
                        </el-submenu>
                    </el-menu>
                </el-col>
            </el-row>
        </el-header>
        <el-container>
            <el-aside class="admin-aside">
                <el-menu
                        :default-active="$route.path"
                        background-color="#545c64"
                        text-color="#fff"
                        active-text-color="#ffd04b"
                        :router="true"
                        style="height: 100%">
                    <el-menu-item index="/admin/home">
                        <i class="el-icon-s-home"></i>
                        <span slot="title">首页</span>
                    </el-menu-item>
                    <el-menu-item index="/admin/blog-editor/0">
                        <i class="el-icon-edit"></i>
                        <span slot="title">写博客</span>
                    </el-menu-item>
                    <el-menu-item index="/admin/blogs">
                        <i class="el-icon-reading"></i>
                        <span slot="title">博客管理</span>
                    </el-menu-item>
                    <el-menu-item index="/admin/types">
                        <i class="el-icon-notebook-1"></i>
                        <span slot="title">分类管理</span>
                    </el-menu-item>
                    <el-menu-item index="/admin/tags">
                        <i class="el-icon-collection-tag"></i>
                        <span slot="title">标签管理</span>
                    </el-menu-item>
                </el-menu>
            </el-aside>
            <el-container>
                <el-main class="wrapper" style="padding: 30px">
                    <router-view
                            @startLoading="startLoading"
                            @endLoading="endLoading"></router-view>
                </el-main>
            </el-container>
        </el-container>
    </el-container>
</template>

<script>
    import DATA from './data'
    export default {
        name: "index",
        data () {
            return{
                adminInfo: DATA.adminInfo
            }
        },
        methods: {
            logout () {
                this.get('/admin/logout', {}).then(res => {
                    if(res.type === 1){
                        this.$message({type: 'success', message: res.message});
                        this.$router.push({name: 'login'})
                    }
                });
            },
            loadAdminInfo () {
                this.get('/admin/login/info', {}).then(res => {
                    this.adminInfo = res;
                })
            },
            startLoading () {
                this.loading = this.$loading({
                    lock: true,
                    text: '加载中...',
                    target: document.querySelector('.wrapper')
                });
                this.timer = setTimeout(() => {
                    this.loading.close();
                }, 1000)
            },
            endLoading () {
                this.loading.close();
            }
        },
        created() {
            this.loadAdminInfo();
        }
    }
</script>

<style scoped>
    .admin-header{
        box-shadow: 0 0 5px rgba(0,0,0,0.2);
    }
    .admin-aside{
        width: 240px !important;
        height: calc(100vh - 60px);
    }
    .wrapper{
        height: calc(100vh - 60px);
        overflow: auto;
    }
</style>