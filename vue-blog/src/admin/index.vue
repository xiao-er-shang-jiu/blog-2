<template>
    <div class="layout">
        <Sider :style="{position: 'fixed', height: '100vh', left: 0, overflow: 'auto'}">
            <Menu active-name="s-1" theme="dark" width="auto" :open-names="[]">
                <menu-item name="s-1">
                    <icon type="md-home"></icon>
                    首页
                </menu-item>
                <menu-item name="s-2" to="editor">
                    <icon type="md-brush"></icon>
                    写博客
                </menu-item>
                <Submenu name="s-3">
                    <template slot="title">
                        <Icon type="md-book"></Icon>
                        博客管理
                    </template>
                    <MenuItem name="s-3-1">博客列表</MenuItem>
                </Submenu>
                <Submenu name="s-4">
                    <template slot="title">
                        <Icon type="md-bulb"></Icon>
                        分类管理
                    </template>
                    <MenuItem name="s-4-1">分类列表</MenuItem>
                    <MenuItem name="s-4-2">新增分类</MenuItem>
                </Submenu>
                <Submenu name="s-5">
                    <template slot="title">
                        <Icon type="md-pricetag" />
                        标签管理
                    </template>
                    <MenuItem name="s-5-1">标签列表</MenuItem>
                    <MenuItem name="s-5-2">新增标签</MenuItem>
                </Submenu>
            </Menu>
        </Sider>
        <Layout :style="{marginLeft: '200px'}">
            <Header :style="{background: '#fff', boxShadow: '0 2px 3px 2px rgba(0,0,0,.1)'}">
                <Menu theme="light" mode="horizontal" :style="{float: 'right'}">
                    <MenuItem name="h-1">
                        <Icon type="md-brush"></Icon>
                        写博客
                    </MenuItem>
                    <Submenu name="h-2">
                        <template slot="title">
                            <Avatar src="https://i.loli.net/2017/08/21/599a521472424.jpg" />
                            李浩元
                        </template>
                        <MenuItem name="h-2-2">注销</MenuItem>
                    </Submenu>
                </Menu>
            </Header>
            <Content :style="{padding: '0 16px 16px'}">
                <Breadcrumb :style="{margin: '16px 0'}">
                    <BreadcrumbItem>后台管理</BreadcrumbItem>
                    <BreadcrumbItem>首页</BreadcrumbItem>
                </Breadcrumb>
                <Card>
                    <row type="flex" justify="center" align="middle" :style="{minHeight: '500px'}">
                        <router-view></router-view>
                    </row>
                </Card>
            </Content>
        </Layout>
    </div>
</template>

<script>
    export default {
        name: "index"
    }
</script>

<style scoped>

</style>