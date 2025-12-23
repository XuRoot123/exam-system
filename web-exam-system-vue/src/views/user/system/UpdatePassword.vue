<script>
import { updatePassword } from "@/api/UserApi";

export default {
  data() {
    return {
      updateData: {
        oldPassword: "",
        newPassword: "",
      },
      password: "",
      user_id: "",
      updatePassword: false,
    };
  },
  methods: {
    initializationData() {
      this.password = JSON.parse(
        window.sessionStorage.getItem("user")
      ).password;
      this.user_id = JSON.parse(window.sessionStorage.getItem("user")).user_id;
    },
    checkPassword() {
      if (this.updateData.newPassword !== this.updateData.password) {
        this.$message.error("两次输入的密码不一致");
        return false;
      }
      if (this.updateData.oldPassword !== this.password) {
        this.$message.error("旧密码输入错误");
        return false;
      }
      if (this.updateData.newPassword === "") {
        this.$message.error("新密码不能为空");
        return false;
      }
      if (this.password === this.updateData.newPassword) {
        this.$message.error("新密码不能与旧密码一致");
        return false;
      }
      return true;
    },
    update() {
      if (this.checkPassword()) {
        updatePassword(this.updateData.newPassword, this.user_id)
          .then(() => {
            this.$message.success("修改成功");
            this.updatePassword = false;
            window.sessionStorage.clear();
            this.$router.replace("/login");
          })
          .catch((err) => {
            this.$message.error(err.message);
          });
      }
    },
  },
  created() {
    this.initializationData();
  },
};
</script>

<template>
  <div style="height: 30vh; width: 30vw; margin: 20vh auto auto">
    <el-form
      style="margin: auto"
      :model="updateData"
      label-width="auto"
      ref="form"
    >
      <el-form-item label="旧密码">
        <el-input show-password v-model="updateData.oldPassword"></el-input>
      </el-form-item>
      <el-form-item label="新密码">
        <el-input show-password v-model="updateData.password"></el-input>
      </el-form-item>
      <el-form-item label="确认密码">
        <el-input show-password v-model="updateData.newPassword"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="update">修改</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style></style>
