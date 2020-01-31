<template>
    <div>欢迎登陆</div>
</template>

<script>
    export default {
        name: "home",
        created() {
            this.$emit('handleActiveName', 's-1');
            this.$emit('handleBreadCrumb', ['后台管理', '首页']);
        }
    }
</script>

<style scoped>

</style>