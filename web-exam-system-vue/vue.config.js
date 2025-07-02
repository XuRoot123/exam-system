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
    port: 8000,
    open: true,
    proxy: "http://localhost:7000",
  },
});
