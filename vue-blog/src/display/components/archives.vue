<template>
    <div>
        <el-card>
            <h2 style="color: #ffd04b;display: inline-block;margin: 0">归档</h2>
            <p style="float: right;margin: 0">
                共 <span style="color: #ffd04b;font-size: 22px">{{computeCount()}}</span> 篇
            </p>
        </el-card>
        <template v-for="(year, index) in years">
            <h2 :key="year" style="color: white;text-align: center">{{year}}</h2>
            <el-card :key="index">
                <template v-for="(blog,index1) in archives[year]">
                    <el-row
                            :key="index1"
                            style="line-height: 32px;"
                    ><div style="display: inline-block;width: 10px;height: 10px;border-radius:5px;background:#67c23a"></div>
                        <el-link
                                style="font-size: 15px;margin-left: 5px"
                                @click="$router.push({path: `/blog-detail/${blog.id}`})"
                        >{{blog.title}}</el-link>
                        <el-tag
                                style="margin-left: 5px"
                                type="success"
                                size="mini">
                            {{transformTime(blog.updateTime)}}
                        </el-tag>
                        <el-tag
                                style="float: right;"
                                :type="tagColor[parseInt(Math.random()*5,0)]"
                        >{{blog.flag}}</el-tag>
                    </el-row>
                    <el-divider
                            v-if="index1 < archives[year].length-1"
                            :key="blog.id"
                            class="divider-horizontal"
                    ></el-divider>
                </template>
            </el-card>
        </template>
    </div>
</template>

<script>
    export default {
        name: "archives",
        data () {
            return{
                archives: {},
                years: [],
                tagColor: ['', 'success', 'info', 'warning', "danger"]
            }
        },
        methods: {
            transformTime (time) {
                let month = parseInt(time.substring(5, 7))
                let day = parseInt(time.substring(8, 10))
                return `${month}月${day}日`;
            },
            computeCount () {
                let count = 0;
                for (let key in this.archives) {
                    count += this.archives[key].length;
                }
                return count;
            },
            reverseYears () {
                let temp, len = this.years.length;
                for (let i=0; i<len/2; i++) {
                    temp = this.years[i];
                    this.years[i] = this.years[len - i - 1];
                    this.years[len - i - 1] = temp;
                }
            },
            loadArchives () {
                this.get('/archives', {}).then(res => {
                    this.archives = res;
                    for(let key in res) {
                        this.years.push(key);
                    }
                    this.reverseYears();
                })
            }
        },
        created() {
            this.loadArchives();
        }
    }
</script>

<style scoped>
    .divider-horizontal{
        margin: 10px 0;
    }
</style>