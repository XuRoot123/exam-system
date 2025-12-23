<script>
export default {
  data() {
    return {
      user: {},
    };
  },
  methods: {
    initializationData() {
      this.user = JSON.parse(window.sessionStorage.getItem("user"));
    },
  },
  created() {
    this.initializationData();
  },
};
</script>

<template>
  <div>
    <el-descriptions title="个人信息" style="font-size: 16px">
      <el-descriptions-item label="学号">{{
        user.userId
      }}</el-descriptions-item>
      <el-descriptions-item label="姓名">{{
        user.userName
      }}</el-descriptions-item>
      <el-descriptions-item label="性别">{{
        user.sex !== 1 ? "女" : "男"
      }}</el-descriptions-item>
      <el-descriptions-item label="手机号">{{
        user.phone
      }}</el-descriptions-item>
      <el-descriptions-item label="邮箱">{{ user.email }}</el-descriptions-item>
      <el-descriptions-item label="班级">{{
        user.classId
      }}</el-descriptions-item>
      <el-descriptions-item label="入学时间">{{
        user.createdAt
      }}</el-descriptions-item>
      <el-descriptions-item label="真实姓名">{{
        user.realName
      }}</el-descriptions-item>
    </el-descriptions>
  </div>
</template>

<style></style>
