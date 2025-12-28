const { defineConfig } = require("@vue/cli-service");
const path = require("path");
module.exports = defineConfig({
  transpileDependencies: true,
  configureWebpack: {
    resolve: {
      alias: {
        "@": path.resolve("src"),
      },
    },
  },
  devServer: {
    host: "0.0.0.0",
    port: 8000,
    open: true,
    proxy: {
      "/api": {
        target: process.env.VUE_APP_API_URL || "http://localhost:7000",
        changeOrigin: true,
        pathRewrite: {
          "^/api": "",
        },
      },
    },
  },
});
