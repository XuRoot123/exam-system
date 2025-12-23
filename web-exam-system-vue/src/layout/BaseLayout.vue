<script>
// import AsideMenu from "@/layout/components/user/admin/AsideMenu.vue";

export default {
  data() {
    return {
      userInfo: {},
      showChangePassword: false,
      showChangePasswordMethod: "rtl",
      AsideMenu: "",
    };
  },
  methods: {
    async loadingUserInfo() {
      this.userInfo = JSON.parse(window.sessionStorage.getItem("user"));
      if (this.userInfo && this.userInfo.role) {
        const roleToMenuMap = {
          admin: () => import("@/layout/components/user/admin/AsideMenu.vue"),
          teacher: () =>
            import("@/layout/components/user/teacher/AsideMenu.vue"),
          student: () =>
            import("@/layout/components/user/student/AsideMenu.vue"),
        };

        try {
          const MenuComponent = await roleToMenuMap[this.userInfo.role]();
          this.AsideMenu = MenuComponent.default || MenuComponent;
        } catch (error) {
          console.error("加载菜单组件失败", error);
          this.$message.error("菜单组件加载失败");
        }
      }
    },
    tcxt() {
      window.sessionStorage.removeItem("user");
      window.sessionStorage.removeItem("classes");
      this.$router.replace("/login");
    },
    xgmm() {
      this.$router.replace("/home/system/updatePassword");
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then(() => {
          done();
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消",
          });
        });
    },
    execCommand(command) {
      if (this[command]) {
        this[command]();
      }
    },
  },
  mounted() {
    this.loadingUserInfo();
  },
};
</script>

<template>
  <el-container>
    <el-aside width="200px">
      <component :is="AsideMenu" v-if="AsideMenu" />
    </el-aside>
    <el-container>
      <el-header style="display: flex">
        <el-row
          type="flex"
          justify="end"
          style="border-bottom: 1px solid #ccc; width: 100%; height: 50px"
        >
          <el-col :span="1" class="flex-center">
            <el-avatar> {{ userInfo.realName }}</el-avatar>
          </el-col>
          <el-col :span="2" class="flex-center">
            <el-dropdown @command="execCommand">
              <span class="el-dropdown-link">
                <i
                  class="el-icon-menu"
                  style="font-size: 20px; margin-top: 11px"
                ></i>
                <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="xgmm">修改密码</el-dropdown-item>
                <el-dropdown-item command="tcxt">退出系统</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <el-drawer
              title="修改密码&nbsp;&nbsp;&nbsp;&nbsp;changePassword"
              :visible.sync="showChangePassword"
              :direction="showChangePasswordMethod || 'rtl'"
              :before-close="handleClose"
            >
              <span>我来啦!</span>
            </el-drawer>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>
<style></style>
