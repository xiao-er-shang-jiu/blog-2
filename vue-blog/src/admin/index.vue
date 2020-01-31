<template>
    <div class="layout">
        <Sider :style="{position: 'fixed', height: '100vh', left: 0, overflow: 'auto'}">
            <Menu :active-name="activeName" theme="dark" width="auto" :open-names="openNames">
                <menu-item name="s-1" :to="{name: 'home'}">
                    <icon type="md-home"></icon>
                    首页
                </menu-item>
                <menu-item name="s-2" to='/admin/blog-editor/0'>
                    <icon type="md-brush"></icon>
                    写博客
                </menu-item>
                <Submenu name="s-3">
                    <template slot="title">
                        <Icon type="md-book"></Icon>
                        博客管理
                    </template>
                    <MenuItem name="s-3-1" :to="{name: 'blogs'}">博客列表</MenuItem>
                </Submenu>
                <Submenu name="s-4">
                    <template slot="title">
                        <Icon type="md-bulb"></Icon>
                        分类管理
                    </template>
                    <MenuItem name="s-4-1" :to="{name: 'types'}">分类列表</MenuItem>
                    <MenuItem name="s-4-2" to="/admin/types-add/-1/0">新增分类</MenuItem>
                </Submenu>
                <Submenu name="s-5">
                    <template slot="title">
                        <Icon type="md-pricetag" />
                        标签管理
                    </template>
                    <MenuItem name="s-5-1" :to="{name: 'tags'}">标签列表</MenuItem>
                    <MenuItem name="s-5-2" to="/admin/tags-add/-1/0">新增标签</MenuItem>
                </Submenu>
            </Menu>
        </Sider>
        <Layout :style="{marginLeft: '200px'}">
            <Header :style="{background: '#fff', boxShadow: '0 2px 3px 2px rgba(0,0,0,.1)'}">
                <Menu :active-name="activeName" theme="light" mode="horizontal" :style="{float: 'right', zIndex: 99}">
                    <MenuItem name="s-2" to='/admin/blog-editor/0'>
                        <Icon type="md-brush"></Icon>
                        写博客
                    </MenuItem>
                    <Submenu name="avatar">
                        <template slot="title">
                            <Avatar :src="adminInfo.avatar" />
                            {{adminInfo.nickname}}
                        </template>
                        <MenuItem name="avatar-1">
                            <Button :style="{width: '100%'}" type="text" @click="logout">注销</Button>
                        </MenuItem>
                    </Submenu>
                </Menu>
            </Header>
            <Content :style="{padding: '0 16px 16px'}">
                <Breadcrumb :style="{margin: '16px 0'}">
                    <BreadcrumbItem v-for="(item, index) in breadcrumb" :key="index">{{item}}</BreadcrumbItem>
                </Breadcrumb>
                <Card>
                    <row type="flex" justify="center" :style="{minHeight: '500px'}">
                        <router-view
                                @handleActiveName="handleActiveName"
                                @handleOpenNames="handleOpenNames"
                                @handleBreadCrumb="handleBreadCrumb"
                        ></router-view>
                    </row>
                </Card>
            </Content>
        </Layout>
    </div>
</template>

<script>
    export default {
        name: "index",
        data () {
            return{
                activeName: '',
                openNames: [],
                breadcrumb: [],
                adminInfo: {
                    nickname: '',
                    avatar: ''
                }
            }
        },
        methods: {
            handleActiveName (name) {
                this.activeName = name;
            },
            handleOpenNames (name) {
                this.openNames.push(name);
            },
            handleBreadCrumb (breadcrumb){
                this.breadcrumb = breadcrumb;
            },
            logout () {
                this.get('/admin/logout', {}).then(res => {
                    if(res.type === 1){
                        this.$Message.success(res.message);
                        this.$router.push({name: 'login'})
                    }
                }).catch(err => {
                    // eslint-disable-next-line no-console
                    console.log(err);
                })
            }
        },
        created() {
            this.get('/admin/login/info', {}).then(res => {
                this.adminInfo = res;
            }).catch(err => {
                // eslint-disable-next-line no-console
                console.log(err)
            })
        }
    }
</script>

<style scoped>
    .layout{
        opacity: 0.9;
    }
</style>