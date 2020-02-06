<template>
    <el-container>
        <el-header class="header">
            <el-row style="height: 100%;margin: 0 auto;width: 1280px;">
                <el-col :span="2.5" style="padding: 9px 0">
                    <img src="../assets/img/logo.png">
                </el-col>
                <el-col :span="12">
                    <el-menu
                            mode="horizontal"
                            default-active="2"
                            background-color="#545c64"
                            text-color="#fff"
                            active-text-color="#ffd04b"
                            style="border: none">
                        <el-menu-item index="2">
                            <i class="el-icon-s-home"></i>
                            <span slot="title">首页</span>
                        </el-menu-item>
                        <el-menu-item index="3">
                            <i class="el-icon-notebook-1"></i>
                            <span slot="title">分类</span>
                        </el-menu-item>
                        <el-menu-item index="4">
                            <i class="el-icon-collection-tag"></i>
                            <span slot="title">标签</span>
                        </el-menu-item>
                        <el-menu-item index="5">
                            <i class="el-icon-collection"></i>
                            <span slot="title">归档</span>
                        </el-menu-item>
                        <el-menu-item index="6">
                            <i class="el-icon-position"></i>
                            <span slot="title">关于我</span>
                        </el-menu-item>
                    </el-menu>
                </el-col>
                <el-col :span="9" style="position: relative">
                    <div class="s-wrapper">
                        <span>
                            <input
                                    v-model="searchText"
                                    class="s-input"
                                    placeholder="搜索..."
                                    @keyup.enter="search"/>
                        </span>
                        <span>
                            <button class="s-btn" @click="search">
                                <i class="el-icon-search"></i>
                            </button>
                        </span>
                    </div>
                </el-col>
            </el-row>
        </el-header>
        <el-main style="margin: 70px auto 0 auto;width: 1200px;overflow: visible;padding:0;">
            <el-row id="fir" style="position: relative;height: 90px">
                <div class="playing-wrapper">
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                </div>
                <div class="music-wrapper">
                    <a-player
                            :music="musicList[0]"
                            :list="musicList"
                            :showLrc="true"
                            :mutex="false"
                            repeat="repeat-all"
                            :listFolded="true"
                            style="margin: 0;width: 380px;">
                    </a-player>
                </div>
            </el-row>
            <div style="float: left;width: 900px;margin-top: 10px">
                <router-view></router-view>
            </div>
            <div style="float: right;width: 290px;margin-top:10px">
                <mochi-box shiba="random">
                    <el-card>
                        <div slot="header">
                            <el-row style="color: #ffd04b;font-size: 15px">
                                <el-col :span="12">
                                    <i class="el-icon-notebook-1"></i><span> 分类</span>
                                </el-col>
                                <el-col :span="12" style="text-align: right">
                                    <span style="cursor: pointer">
                                        more <i class="el-icon-d-arrow-right"></i>
                                    </span>
                                </el-col>
                            </el-row>
                        </div>
                        <div style="border: 1px solid #EBEEF5;border-radius: 5px;font-size: 14px">
                            <template v-for="(type, index) in typeList">
                                <div class="typeItem" :key="type.id">
                                    {{type.name}}
                                    <el-tag size="mini" style="float: right">{{type.count}}</el-tag>
                                </div>
                                <el-divider v-if="index < 5" :key="type.id" class="divider-horizontal"></el-divider>
                            </template>
                        </div>
                    </el-card>
                </mochi-box>
                <mochi-box shiba="random"
                           style="margin-top: 10px">
                    <el-card>
                        <div slot="header">
                            <el-row style="color: #ffd04b;font-size: 15px">
                                <el-col :span="12">
                                    <i class="el-icon-collection-tag"></i><span> 标签</span>
                                </el-col>
                                <el-col :span="12" style="text-align: right">
                                    <span style="cursor: pointer">
                                        more <i class="el-icon-d-arrow-right"></i>
                                    </span>
                                </el-col>
                            </el-row>
                        </div>
                        <template v-for="tag in tagList">
                            <el-tag
                                    :type="tagColor[parseInt(Math.random()*5,0)]"
                                    :key="tag.id"
                                    style="margin: 5px;cursor: pointer">
                                {{tag.name}}</el-tag>
                        </template>
                    </el-card>
                </mochi-box>
                <mochi-box shiba="random"
                           style="margin-top: 10px">
                    <el-card>
                        <div slot="header" style="color: #ffd04b;font-size: 15px">
                            <i class="el-icon-s-help"></i> 最新推荐
                        </div>
                        <template v-for="(item, index) in newRecommendList">
                            <div
                                    class="typeItem"
                                    :key="item.id"
                                    @click="$router.push({path: `/blog-detail/${item.id}`})"
                            >{{item.title}}</div>
                            <el-divider
                                    v-if="index < 7"
                                    :key="item.id"
                                    class="divider-horizontal"
                            ></el-divider>
                        </template>
                    </el-card>
                </mochi-box>
                <mochi-box shiba="random"
                           style="margin-top: 10px">
                    <el-card style="text-align: center">
                        <el-divider class="divider-horizontal">
                            <span style="color: #ffd04b;font-size: 15px">扫码关注我</span>
                        </el-divider>
                        <el-image
                                :src="require('../assets/img/me-qrcode.png')"
                                style="width: 120px;border-radius: 5px;border: 1px solid #DCDFE6;margin-top: 20px"
                                lazy>
                        </el-image>
                        <p class="new-blog">Email: 1617721824@qq.com</p>
                        <p class="new-blog">QQ: 1617721824</p>
                    </el-card>
                </mochi-box>
            </div>
        </el-main>
        <el-footer style="background: #545c64; height: 240px;margin-top:10px;">
            <el-row
                    style="height: 179px;margin: 0 auto;width: 1280px;"
                    type="flex"
                    justify="center"
                    align="middle">
                <el-col :span="4" style="line-height: 0;text-align: center">
                    <el-image
                            :src="require('../assets/img/me-qrcode.png')"
                            style="width: 120px;border-radius: 5px"
                            lazy>
                    </el-image>
                </el-col>
                <el-col :span="1" style="height: 120px;text-align: center">
                    <el-divider direction="vertical" class="divider-vertical"></el-divider>
                </el-col>
                <el-col :span="4" style="text-align: center">
                    <p style="color: white;font-size: 15px;margin: 0;line-height: 28px">最新博客</p>
                    <p class="new-blog new-blog-hover">最新博客</p>
                    <p class="new-blog">最新博客</p>
                    <p class="new-blog">最新博客</p>
                </el-col>
                <el-col :span="1" style="height: 120px;text-align: center">
                    <el-divider direction="vertical" class="divider-vertical"></el-divider>
                </el-col>
                <el-col :span="4" style="text-align: center">
                    <p style="color: white;font-size:15px;margin: 0;line-height: 28px">联系我</p>
                    <p class="new-blog">Email: 1617721824@qq.com</p>
                    <p class="new-blog">QQ: 1617721824</p>
                </el-col>
                <el-col :span="1" style="height: 120px;text-align: center">
                    <el-divider direction="vertical" class="divider-vertical"></el-divider>
                </el-col>
                <el-col :span="9" style="text-align: center">
                    <p style="color: white;font-size: 15px;margin: 0;line-height: 28px">Lihaoyan</p>
                    <p class="new-blog">这是我的个人博客，会分享关于编程的相关内容，希望可以给来到这儿的人有所帮助...</p>
                </el-col>
            </el-row>
            <el-divider class="divider-horizontal"></el-divider>
            <el-row
                    style="height: 60px;margin: 0 auto;width: 1280px;color: #909399;font-size: 14px"
                    type="flex"
                    justify="center"
                    align="middle">
                <span>Copyright @www.lihaoyuan.xyz Designed By Lihaoyuan</span>
            </el-row>
        </el-footer>

        <el-backtop target="#app" :bottom="100">
            <div style="{
                        height: 100%;
                        width: 100%;
                        background-color: #f2f5f6;
                        box-shadow: 0 0 6px rgba(0,0,0, .12);
                        text-align: center;
                        line-height: 40px;
                        color: #ffd04b;font-size: 15px}">
                UP
            </div>
        </el-backtop>

        <canvas id="space"></canvas>
    </el-container>
</template>

<script>
    import DATA from './data'
    import APlayer from 'vue-aplayer'
    import MochiBox from './animal/mochi-box'
    import {star} from '../plugins/star'
    export default {
        name: "index",
        data () {
            return{
                showMini: true,
                searchText: '',
                musicList: DATA.musicList,
                typeList: [],
                tagList: [],
                newRecommendList: [],
                tagColor: ['', 'success', 'info', 'warning', "danger"]
            }
        },
        methods: {
            search () {
                if(this.searchText !== ''){
                    this.$router.push({path: `/search/${this.searchText}`})
                }
            },
            loadTypeList () {
                this.get('/typesIndex', {}).then(res => {
                    this.typeList = res;
                })
            },
            loadTagList () {
                this.get('/tagsIndex', {}).then(res => {
                    this.tagList = res;
                });
            },
            loadRecommendList () {
                this.get('/newBlogsIndex', {}).then(res => {
                    this.newRecommendList = res;
                });
            }
        },
        created() {
            if(this.$route.params.text){
                this.searchText = this.$route.params.text;
            }
            this.loadTypeList();
            this.loadTagList();
            this.loadRecommendList();
        },
        mounted() {
            star();
        },
        components: {
            APlayer,
            MochiBox,
        },
    }
</script>

<style scoped>
    .header{
        background: #545c64;
        position: fixed;
        top: 0;
        left: 0;
        width: calc(100% - 17px);
        box-shadow: 0 5px 5px rgba(0,0,0,0.1);
        z-index: 10;
    }
    .header:hover{
        transition: .5s;
        box-shadow: 0 5px 5px rgba(0,0,0,0.2);
    }
    .s-wrapper{
        position: absolute;
        top: 12px;
        right: 0;
        height: 36px;
        border: 1px solid #909399;
        padding: 4px 10px 4px 20px;
        border-radius: 20px;
        box-sizing: border-box;
    }
    .s-wrapper:hover{
        border: 1px solid #fff;
        transition: .5s;
    }
    .s-wrapper:hover .s-btn{
        color: #fff;
        transition: .5s;
    }
    .s-input{
        display: inline-block;
        padding: 0;
        height: 28px;
        border: none;
        background: none;
        outline: none;
        color: white;
    }
    .s-wrapper:focus-within {
        border: 1px solid #fff;
    }
    .s-wrapper:focus-within .s-btn{
        color: #fff;
    }
    .s-btn{
        padding: 0;
        height: 28px;
        width: 28px;
        border-radius: 14px;
        background: none;
        border: none;
        color: #909399;
        outline: none;
        cursor: pointer;
    }
    .new-blog{
        font-size: 14px;
        color: #909399;
        cursor: default;
        line-height: 28px;
        margin: 0;
    }
    .new-blog-hover:hover{
        transition: .3s;
        color: #ffd04b;
    }
    .divider-vertical{
        height: 100%;
    }
    .divider-horizontal{
        margin: 0;
    }
    .typeItem{
        padding: 8px 10px;
        cursor: pointer;
        font-size: 14px;
    }
    .typeItem:hover{
        transition: .5s;
        background: #EBEEF5;
    }
    #space{
        position: fixed;
        top: 0;
        left: 0;
        z-index: -1;
    }
    #fir div:first-child:hover + .music-wrapper{
        width: 380px;
        opacity: 1;
    }
    .music-wrapper:hover{
        width: 380px;
        opacity: 1;
    }
    .music-wrapper{
        position: absolute;
        top: 0;
        right: 0;
        width: 0;
        overflow: hidden;
        z-index: 3;
        transition: .8s;
        opacity: 0;
    }
    .playing-wrapper{
        position: absolute;
        right: 0;
        top: 0;
        height: 90px;
        width: 80px;
    }
    .playing-wrapper span{
        position: absolute;
        bottom: 20px;
        width: 4px;
        background: #ffd04b;
        border-radius: 5px;
        animation: playing alternate linear infinite;
    }
    .playing-wrapper span:first-child{
        left: 20px;
        animation-duration: .4s;
    }
    .playing-wrapper span:nth-child(2){
        left: 26px;
        animation-duration: .6s;
    }
    .playing-wrapper span:nth-child(3){
        left: 32px;
        animation-duration: .4s;
        animation-delay: .6s;
    }
    .playing-wrapper span:nth-child(4){
        left: 38px;
        animation-duration: .3s;
    }
    .playing-wrapper span:nth-child(5){
        left: 44px;
        animation-duration: .5s;
    }
    .playing-wrapper span:nth-child(6){
        left: 50px;
        animation-duration: .7s;
    }
    .playing-wrapper span:nth-child(7){
        left: 56px;
        animation-duration: .6s;
        animation-delay: .6s;
    }
    @keyframes playing {
        0%{
            height: 1px;
        }
        100%{
            height: 50px;
        }
    }
</style>