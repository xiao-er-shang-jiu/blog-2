<template>
    <Row :style="{width: '100%'}">
        <Table :loading="loading" border :columns="tableHeader" :data="tableData" :style="{width: '100%'}">
            <template slot-scope="{ row }" slot="name">
                <strong>{{ row.name }}</strong>
            </template>
            <template slot-scope="{ row }" slot="action">
                <Button type="primary" size="small" style="margin-right: 5px" @click="update1(row.id, row.name)">修改</Button>
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
        <Row type="flex" justify="end">
            <Button type="primary" @click="toTypesAdd">新增</Button>
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
    export default {
        name: "types",
        data () {
            return{
                tableHeader: [
                    {
                        title: '编号',
                        key: 'id',
                        align: 'center'
                    },
                    {
                        title: '分类名称',
                        slot: 'name',
                        align: 'center'
                    },
                    {
                        title: '操作',
                        slot: 'action',
                        width: 300,
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
                    pageSize: 6
                }
            }
        },
        methods: {
            update1 (id, name){
                this.$router.push({path: `/admin/types-add/${id}/${name}`})
            },
            handleDelete (id){
                this.deleteId = id;
                this.modal_show = true;
            },
            handlePageChange (index) {
                this.loadData(index);
            },
            delete1 (){
                this.modal_loading = true;
                this.get(`/admin/types/${this.deleteId}/delete`, {})
                    .then(res => {
                        this.$Message.success(res.message);
                        this.loadData(1);
                    })
                    .catch(err => {
                        // eslint-disable-next-line no-console
                        console.log(err)
                    })
            },
            toTypesAdd () {
                this.$router.push({path: '/admin/types-add/-1/0'});
            },
            loadData (index) {
                this.loading = true;
                let that = this;
                this.timer = setTimeout(function () {
                    that.loading = false;
                    that.modal_loading = false;
                    that.modal_show = false;
                }, 1000);
                this.get('/admin/types', {page: index-1}).then(res => {
                    this.tableData = res.content;
                    this.page.current = res.number + 1;
                    this.page.total = res.totalElements;
                }).catch(err => {
                    // eslint-disable-next-line no-console
                    console.log(err);
                });
            }
        },
        created() {
            this.loadData(1);
            this.$emit('handleActiveName', 's-4-1');
            this.$emit('handleOpenNames', 's-4');
            this.$emit('handleBreadCrumb', ['后台管理', '分类管理', '分类列表']);
        },
        destroyed() {
            clearTimeout(this.timer);
        }
    }
</script>

<style scoped>

</style>