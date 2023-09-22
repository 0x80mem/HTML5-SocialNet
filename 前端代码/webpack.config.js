// npm原有的模块
const path = require('path');
const { VueLoaderPlugin } = require('vue-loader');
 
module.exports = {
  // 入口文件
  entry: './src/main.js',
  // 导出的部分
  output: {
    // 导出的文件名
    filename: 'bundle.js',
    // 导出的文件名会放置的路径
    path: path.resolve(__dirname, 'dist/'),
    publicPath: './',
  },
  module: {
    rules: [
    // 解析Vue文件
    { test: /\.vue$/, loader: 'vue-loader' },
    // 它会应用到普通的 `.js` 文件
    // 以及 `.vue` 文件中的 `<script>` 块
    {
      test: /\.js$/,
      loader: 'babel-loader',
    },
    // 它会应用到普通的 `.css` 文件
    // 以及 `.vue` 文件中的 `<style>` 块
    {
      test: /\.css$/,
      use: ['vue-style-loader', 'css-loader'],
    },
    {
        // 对 less 文件进行处理
        test: /\.less$/i,
        use: [
          // compiles Less to CSS
          'style-loader',
          'css-loader',
          'less-loader',
        ],
    },
    {
        test: /\.png$/i,
        // 如果像上面一样直接使用 use: "file-loader"
        // 会出现图片资源为 "[object Module]" 的问题
        // 这是因为新版的 file-loader 自动开启了 ES模块
        // 手动关闭即可正确显示
        use: {
          loader: 'file-loader',
          options: {
            esModule: false,
          },
        },
    },


  ],

  },
  plugins: [new VueLoaderPlugin()],
};