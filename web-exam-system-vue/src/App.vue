<template>
  <div id="app">
    <router-view />
  </div>
</template>

<style>
body {
  margin: 0;
}

.el-container {
  height: 100vh;
}

.el-aside {
  overflow: hidden !important;
}
</style>
<script>
export default {
  name: "app",
};
</script>
