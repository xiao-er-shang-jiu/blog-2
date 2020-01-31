module.exports = {
    devServer: {
        historyApiFallback: true,
        hot: true,
        inline: true,
        stats: { colors: true },
        proxy: {
            //匹配代理的url
            '/api': {
                // 目标服务器地址
                target: 'http://localhost:8080',
                //路径重写
                pathRewrite: {'^/api' : ''},
                changeOrigin: true,
                secure: false,
            }
        },
        disableHostCheck:true
    }
}