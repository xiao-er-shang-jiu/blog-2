<template>
    <el-card>
        <div slot="header" class="clearfix">
            <el-row type="flex" align="middle" style="font-size: 14px">
                <el-col :span="3">
                    <el-row type="flex" align="middle">
                        <el-col :span="10" style="line-height: 0">
                            <el-avatar :size="40" :src="blog.user ? blog.user.avatar:''"></el-avatar>
                        </el-col>
                        <el-col :span="14" style="color: #00b5ad">
                            {{blog.user ? blog.user.nickname:''}}
                        </el-col>
                    </el-row>
                </el-col>
                <el-col :span="5" style="color:#909399">
                    <i class="el-icon-time"></i> {{blog.updateTime}}
                </el-col>
                <el-col :span="2" style="color:#909399">
                    <i class="el-icon-view"></i> {{blog.views}}
                </el-col>
                <el-col :span="2" style="color: #909399">
                    <i class="el-icon-thumb"></i> {{blog.admire}}
                </el-col>
            </el-row>
        </div>
        <el-image
                :src="blog.firstPicture ? blog.firstPicture:''"
                :lazy="true">
            <div slot="placeholder">
                加载中...
            </div>
        </el-image>
        <el-divider></el-divider>
        <el-row>
            <el-tag
                    :type="tagColor[parseInt(Math.random()*5,0)]"
                    style="float: right"
                    @click="$router.push({path: `/tags/${tag.id}`})"
            >{{blog.flag}}</el-tag>
        </el-row>
        <el-row style="text-align: center">
            <h2>{{blog.title}}</h2>
        </el-row>
        <mavon-editor v-model="blog.content"
                      codeStyle="darcula"
                      style="z-index: 0;border: none;font-size: 15px;line-height: 24px"
                      :subfield="false"
                      defaultOpen="preview"
                      :toolbarsFlag="false"
                      :boxShadow="false"
                      previewBackground="none"
        ></mavon-editor>
        <el-row>
            <template v-for="item in blog.tags">
                <el-tag
                        :type="tagColor[parseInt(Math.random()*5,0)]"
                        :key="item.id"
                        style="margin-left: 10px">
                    {{item.name}}</el-tag>
            </template>
        </el-row>
        <el-row type="flex" justify="center" align="middle">
            <el-col :span="3">
                <vue-clap-button
                        @clap="handleClap"
                        @cancel="handleClapCancel"></vue-clap-button>
            </el-col>
            <el-col :span="3" v-if="blog.appreciation">
                <el-popover
                        placement="bottom"
                        trigger="click">
                    <div style="padding: 14px 14px 0 14px;border: 1px solid #ffd04b;border-radius: 5px">
                        <el-row type="flex">
                            <el-col style="padding: 5px;border: 1px solid #EBEEF5;border-radius: 5px;line-height: 0;">
                                <img src="../../assets/img/me-qrcode.png" width="120">
                            </el-col>
                            <el-col style="padding: 5px;border: 1px solid #EBEEF5;border-radius: 5px;line-height: 0;text-align: center;margin-left: 5px">
                                <img src="../../assets/img/zhifubao.jpg" width="120">
                            </el-col>
                        </el-row>
                        <el-row style="color: #ffd04b;line-height: 28px">
                            <el-col :span="12" style="text-align: center">
                                微 信
                            </el-col>
                            <el-col :span="12" style="text-align: center;color: #ffd04b">
                                支 付 宝
                            </el-col>
                        </el-row>
                    </div>
                    <el-button
                            slot="reference"
                            type="primary"
                            plain round>赞 赏</el-button>
                </el-popover>
            </el-col>
        </el-row>
        <div class="shareStatement" style="font-size: 14px" v-if="blog.shareStatement">
            <el-row type="flex" align="middle">
                <el-col :span="16">
                    <ul style="margin: 0">
                        <li>作者：{{blog.user ? blog.user.nickname:''}}
                            <el-link
                                    type="success"
                                    @click="$router.push({path: '/about'})"
                            >（联系作者）</el-link>
                        </li>
                        <li>发表时间：{{blog.updateTime}}</li>
                        <li>版权声明：自由转载-非商用-非衍生-保留署名</li>
                        <li>公众号转载：请在文章添加作者公众号二维码</li>
                    </ul>
                </el-col>
                <el-col :span="8" style="line-height: 0;text-align: right">
                    <img src="../../assets/img/me-qrcode.png" width="120">
                </el-col>
            </el-row>
        </div>
        <el-card style="margin-top: 20px" v-if="blog.commentabled">
            <div slot="header">
                <h3 style="color: #ffd04b;margin:0">评论</h3>
            </div>
            <div
                    v-if="this.commentList.length === 0"
                    style="font-size: 14px;color: #909399">
                <i class="el-icon-info"></i>
                暂无评论，快来发表评论吧！
            </div>
            <template v-for="comment in commentList">
                <el-row type="flex" align="middle" :key="comment.id">
                    <el-col :span="2">
                        <el-avatar shape="square" :src="comment.avatar"></el-avatar>
                    </el-col>
                    <el-col :span="22">
                        <el-row>
                            <strong style="font-size: 15px">{{comment.nickname}}</strong>&nbsp;
                            <el-tag v-if="comment.adminComment" size="mini" effect="dark">博主</el-tag>
                            <span style="color: #909399;font-size: 13px">{{comment.createTime}}</span>
                        </el-row>
                        <el-row style="line-height: 24px;font-size: 14px">{{comment.content}}</el-row>
                        <el-row>
                            <el-button
                                    type="text"
                                    size="small"
                                    style="padding: 0;color: #909399"
                                    @click="handleReplay(comment.id, comment.nickname)"
                            >回复</el-button>
                        </el-row>
                    </el-col>
                </el-row>
                <template v-for="item in comment.replayComments">
                    <el-row :key="item.id" type="flex" align="middle" style="position: relative;">
                        <el-col :span="1" style="position: absolute;top: 0;bottom: 0;text-align: center">
                            <el-divider direction="vertical" class="divider-vertical"></el-divider>
                        </el-col>
                        <el-col :span="2"  :offset="2">
                            <el-avatar shape="square" :src="item.avatar"></el-avatar>
                        </el-col>
                        <el-col :span="20">
                            <el-row>
                                <strong style="font-size: 15px">
                                    {{item.nickname.split('@')[0]}}<span style="color: #00b5ad">
                                    <el-tag v-if="item.adminComment" size="mini" effect="dark">博主</el-tag>
                                    @ {{item.nickname.split('@')[1]}}</span>
                                </strong>&nbsp;
                                <span style="color: #909399;font-size: 13px">{{item.createTime}}</span>
                            </el-row>
                            <el-row style="line-height: 24px;font-size: 14px">{{item.content}}</el-row>
                            <el-row>
                                <el-button
                                        type="text"
                                        size="small"
                                        style="padding: 0;color: #909399"
                                        @click="handleReplay(item.id, item.nickname)"
                                >回复</el-button>
                            </el-row>
                        </el-col>
                    </el-row>
                </template>
            </template>
        </el-card>
        <el-card style="margin-top: 10px" id="comment" v-if="blog.commentabled">
            <el-form
                    :model="commentForm"
                    :rules="commentRules"
                    ref="commentForm">
                <el-form-item prop="content">
                    <el-input
                            type="textarea"
                            :placeholder="placeholder"
                            maxlength="80"
                            v-model="commentForm.content"
                            show-word-limit
                            :rows="4"
                    ></el-input>
                </el-form-item>
                <el-form-item style="float: left" prop="nickname">
                    <el-input
                            placeholder="昵称"
                            prefix-icon="el-icon-user"
                            v-model="commentForm.nickname"
                    ></el-input>
                </el-form-item>
                <el-form-item style="float: left;margin-left: 14px" prop="email">
                    <el-input
                            placeholder="邮箱"
                            prefix-icon="el-icon-message"
                            v-model="commentForm.email"
                    ></el-input>
                </el-form-item>
                <el-form-item style="float: left;margin-left: 14px">
                    <el-button
                            type="primary"
                            icon="el-icon-edit"
                            @click="handleSubmit('commentForm')"
                    >发布</el-button>
                </el-form-item>
            </el-form>
        </el-card>
    </el-card>
</template>

<script>
    import DATA from '../data'
    export default {
        name: "blog-detail",
        data () {
            return{
                blog: {},
                tagColor: ['', 'success', 'info', 'warning', "danger"],
                placeholder: '评论内容...',
                commentForm: DATA.commentForm,
                commentRules: DATA.commentRules,
                commentList: []
            }
        },
        methods: {
            handleReplay (id, nickname) {
                this.commentForm["parentComment.id"] = id;
                this.placeholder = '@' + nickname.split('@')[0] + ': ';
                document.querySelector("#comment").scrollIntoView({behavior:"smooth",block: "start", inline: "nearest"});
            },
            handleSubmit (formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if(!this.commentForm.avatar)
                            this.commentForm.avatar = DATA.avatar[parseInt(Math.random()*8,0)];
                        if(!this.commentForm["blog.id"])
                            this.commentForm["blog.id"] = this.blog.id;
                        if(this.placeholder === '评论内容...' && this.commentForm["parentComment.id"] !== -1)
                            this.commentForm["parentComment.id"] = -1;
                        if(this.commentForm.nickname.split('#')[1] === 'admin') {
                            this.commentForm.nickname = this.commentForm.nickname.split('#')[0];
                            this.commentForm.adminComment = true;
                        }
                        this.submitComment();
                    }
                });
            },
            submitComment () {
                this.post('/comments', this.commentForm).then(res => {
                    if (res.type === 1) {
                        this.loadComment();
                        this.commentForm.content = '';
                        this.commentForm["parentComment.id"] = -1;
                    }
                })
            },
            loadComment () {
                this.get(`/comments/${this.blogId}`, {}).then(res => {
                    this.commentList = res;
                })
            },
            handleClap () {
                this.get(`/blog/admire/${this.blog.id}/${1}`, {});
            },
            handleClapCancel () {
                this.get(`/blog/admire/${this.blog.id}/${-1}`, {});
            },
            loadBlog () {
                this.get(`/blog/${this.blogId}`, {}).then(res => {
                    this.blog = res;
                })
            }
        },
        created() {
            this.loadBlog();
            this.loadComment();
        },
        computed: {
            blogId () {
                return this.$route.params.id;
            }
        },
        watch: {
            content (newVal) {
                if (newVal === '')
                    this.placeholder = '评论内容...';
            },
            blogId () {
                this.loadBlog();
                this.loadComment();
            }
        }
    }
</script>

<style scoped>
    .shareStatement{
        margin-top: 20px;
        border-top: 2px solid #909399;
        border-bottom: 2px solid #909399;
        padding: 40px;
        line-height: 30px;
        color: #2C662D;
        background: #fcfff5;
    }
    .divider-vertical{
        height: 100%;
    }
</style>