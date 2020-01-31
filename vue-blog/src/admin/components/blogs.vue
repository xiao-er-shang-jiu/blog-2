<template>
    <Row :style="{width: '100%'}">
        <Form inline>
            <form-item>
                <i-input v-model="query.title" placeholder="标题..."></i-input>
            </form-item>
            <form-item>
                <Select v-model="query.typeId">
                    <Option value="">不选</Option>
                    <Option v-for="item in type" :value="item.id" :key="item.id">{{item.name}}</Option>
                </Select>
            </form-item>
            <form-item>
                <Checkbox v-model="query.recommend">推荐</Checkbox>
            </form-item>
            <form-item>
                <Button @click="search" icon="md-search" type="primary" :loading="query.loading">搜索</Button>
            </form-item>
        </Form>
        <Table :loading="loading" border :columns="tableHeader" :data="tableData" :style="{width: '100%'}">
            <template slot-scope="{ row }" slot="title">
                <strong>{{ row.title }}</strong>
            </template>
            <template slot-scope="{ row }" slot="action">
                <Button type="primary" size="small" style="margin-right: 5px" @click="update1(row.id)">修改</Button>
                <Button type="error" size="small" @click="handleDelete(row.id)">删除</Button>
            </template>
        </Table>
        <br>
        <Row>
            <Page
                    :current="page.current"
                    :total="page.total"
                    :page-size="page.pageSize"
                    @on-change="handlePageChange"
            ></Page>
        </Row>
        <Modal v-model="modal_show" width="360">
            <p slot="header" style="color:#f60;text-align:center">
                <Icon type="ios-information-circle"></Icon>
                <span>删除确认</span>
            </p>
            <div style="text-align:center">
                <p>删除后将不可恢复</p>
                <p>是否继续删除?</p>
            </div>
            <div slot="footer">
                <Button type="error" size="large" long :loading="modal_loading" @click="delete1">删除</Button>
            </div>
        </Modal>
    </Row>
</template>

<script>
    import {timeFormat} from "../../util/util";

    export default {
        name: "blogs",
        data () {
            return{
                tableHeader: [
                    {
                        title: '编号',
                        key: 'id',
                        align: 'center'
                    },
                    {
                        title: '标题',
                        slot: 'title',
                        width: 300
                    },
                    {
                        title: '类型',
                        key: 'flag',
                        align: 'center'
                    },
                    {
                        title: '推荐',
                        key: 'recommend',
                        align: 'center'
                    },
                    {
                        title: '状态',
                        key: 'published',
                        align: 'center'
                    },
                    {
                        title: '浏览量',
                        key: 'views',
                        align: 'center'
                    },
                    {
                        title: '更新时间',
                        key: 'updateTime',
                        align: 'center',
                        width: 200
                    },
                    {
                        title: '操作',
                        slot: 'action',
                        width: 150,
                        align: 'center'
                    }
                ],
                tableData: [],
                loading: true,
                modal_show: false,
                modal_loading: false,
                deleteId: '',
                page: {
                    current: 1,
                    total: 0,
                    pageSize: 10
                },
                query: {
                    title: null,
                    typeId: null,
                    recommend: false,
                    loading: false
                },
                type: []
            }
        },
        methods: {
            handleDelete (id){
                this.deleteId = id;
                this.modal_show = true;
            },
            update1 (id) {
                this.$router.push({path: `/admin/blog-editor/${id}`})
            },
            delete1 () {
                this.modal_loading = true;
                this.loading = true;
                let that = this;
                this.timer = setTimeout(function () {
                    that.modal_loading = false;
                    that.modal_show = false;
                    that.loading = false;
                }, 1000);
                this.get(`/admin/blogs/${this.deleteId}/delete`, {}).then(res => {
                    if (res.type === 1){
                        this.$Message.success(res.message);
                        this.loadData(1);
                    }
                }).catch(err => {
                    // eslint-disable-next-line no-console
                    console.log(err);
                })
            },
            search () {
                this.query.loading = true;
                this.loadData(1);
            },
            handlePageChange (index) {
                this.loadData(index);
            },
            loadData (index) {
                this.loading = true;
                let that = this;
                this.timer = setTimeout(function () {
                    that.loading = false;
                    that.query.loading = false;
                }, 1000)
                this.post('admin/blogs/search', {
                    title: this.query.title,
                    typeId: this.query.typeId,
                    recommend: this.query.recommend,
                    page: index - 1
                }).then(res => {
                    let data = res.content;
                    for(let j = 0, len = data.length; j < len; j++) {
                        data[j].recommend = data[j].recommend === true ? '是':'否';
                        data[j].published = data[j].published === true ? '已发布':'草稿';
                        data[j].updateTime = timeFormat(data[j].updateTime);
                    }
                    this.tableData = data;
                    this.page.current = res.number + 1;
                    this.page.total = res.totalElements;
                }).catch(err => {
                    // eslint-disable-next-line no-console
                    console.log(err);
                })
            }
        },
        created() {
            this.loadData(1);
            this.get('/admin/types/getAll', {})
                .then(res => {
                    this.type = res;
                })
                .catch(err => {
                    // eslint-disable-next-line no-console
                    console.log(err);
                });
            this.$emit('handleActiveName', 's-3-1');
            this.$emit('handleOpenNames', 's-3');
            this.$emit('handleBreadCrumb', ['后台管理', '博客管理', '博客列表']);
        },
        destroyed() {
            clearTimeout(this.timer);
        }
    }
</script>

<style scoped>

</style>