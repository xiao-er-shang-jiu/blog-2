module.exports = {
    devServer: {
        port: 8082,
        historyApiFallback: true,
        hot: true,
        inline: true,
        stats: { colors: true },
        proxy: {
            //匹配代理的url
            '/api': {
                // 目标服务器地址
                target: 'http://139.224.252.53:8081',
                //路径重写
                pathRewrite: {'^/api' : ''},
                changeOrigin: true,
                secure: false,
            }
        },
        disableHostCheck:true
    },

    publicPath: process.env.NODE_ENV === 'production' ? '/blog/' : '/',
};