const path = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin')

module.exports = {
    entry: './src/main.js',
    output: {
        path: path.resolve(__dirname, "dist"),
        filename: 'bundle.js'
    },
    resolve: {
        alias: {
            'vue$': 'vue/dist/vue.esm.js'
        }
    },
    module: {
        rules: [
            {
                test: /\.png$/,
                use: ['file-loader']
            },
            {
                test: /\.vue$/,
                use: ['vue-loader']
            },
            {
                test: /\.css$/,
                use: ['css-loader', 'style-loader'],
                exclude: ['./node_modules']
            },
            {
                test: /\.m?js$/,
                exclude: /(node_modules|bower_components)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['es2015'],
                        plugins: ['syntax-dynamic-import']
                    }
                },
            }

        ]
    },
    devServer: {
        "contentBase": './dist',
        inline: true,
        historyApiFallback: true,
        port: 8082,
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: 'index.html'
        }),
    ]
}
