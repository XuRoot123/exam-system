<script>
import { getAllClass, login } from "@/api/UserApi";

export default {
  data() {
    return {
      type: "",
      types: ["student", "teacher", "admin"],
      user: {
        userName: "teacher_zhang",
        password: "zhang123",
      },
    };
  },
  methods: {
    checkType() {
      if (this.type === "") {
        this.$message.error("用户类型有误");
      }
      if (this.type === "student") {
        this.$router.push("/home/student");
      }
      if (this.type === "teacher") {
        this.$router.push("/home/teacher");
      }
      if (this.type === "admin") {
        this.$router.push("/home/admin");
      }
    },
    login() {
      if (this.user.userName !== null && this.user.password !== null) {
        login(this.user)
          .then((res) => {
            this.type = res.data.role;
            window.sessionStorage.setItem("user", JSON.stringify(res.data));
            this.$message.success("登录成功");
            this.checkType();
          })
          .catch((err) => {
            this.$message.error(err.message);
          });
      } else {
        this.$message.error("请填写用户名或密码");
      }
    },
    enroll() {
      this.$router.push("/enroll");
    },
    checkUserLogin() {
      var item = window.sessionStorage.getItem("user");
      if (item) {
        this.checkType();
      }
    },
    getAllClass() {
      getAllClass().then((res) => {
        window.sessionStorage.setItem("classes", JSON.stringify(res.data));
      });
    },
  },
  mounted() {
    this.checkUserLogin();
    this.getAllClass();
  },
};
</script>
<template>
  <div class="whole">
    <div class="helpText">
      <div>
        <a href="">需要帮助...</a>
      </div>
      <p>目前没有账号却想使用此系统应该怎么做...</p>
      <p>学生注册需要联系班级班主任同意入班~</p>
      <p>教师注册需要联系管理人员出示证明入职~</p>
      <p>应该如何注册属于自己的账号...</p>
      <p>快点击下方按钮注册一个属于自己的账号吧~</p>
      <div class="input-wrapper">
        <el-button type="primary" @click="enroll">注册</el-button>
      </div>
    </div>
    <div
      style="
        position: fixed;
        right: 20px;
        top: 50%;
        transform: translateY(-50%);
        width: 30%;
        min-width: 300px;
        padding: 20px;
        background-color: #f8f9ff;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        border-radius: 8px;
      "
    >
      <h2 style="text-align: center; margin-bottom: 20px">
        Login &nbsp;&nbsp;Password
      </h2>
      <div style="margin: auto auto auto 60px">
        账号：
        <el-input
          placeholder="请输入账号名"
          v-model="user.userName"
          style="margin-bottom: 20px; width: 60%"
        ></el-input>
        <br />
        密码：
        <el-input
          show-password
          placeholder="请输入密码"
          v-model="user.password"
          style="width: 60%"
        ></el-input>
        <br />
        <div style="width: 50%; margin: 20px auto auto">
          <el-button type="primary" style="margin-right: 10%" @click="login"
            >登录</el-button
          >
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
* {
  margin: 0;
  padding: 0;
}

.whole {
  height: 100vh;
  background: url("@/assets/loginBack.png") center;
  background-size: cover;
}

.helpText {
  position: fixed;
  border: 1px solid #000;
  left: 20px;
  top: 50%;
  transform: translateY(-50%);
  background-color: #e6f7ff;
  padding: 20px;
  border-radius: 8px;
}

.helpText p:hover {
  color: #d9b3ff;
  text-decoration: underline;
}

.helpText a {
  display: block;
  text-align: center;
  font-size: 25px;
  text-decoration: none;
  color: inherit;
  margin-bottom: 20px;
}

.helpText a:hover {
  color: #d9b3ff;
  text-decoration: underline;
}

.helpText p {
  margin-bottom: 15px;
}

.helpText i {
  display: block;
  text-align: center;
  font-size: 30px;
  margin-top: 10px;
  color: #333;
}

.input-wrapper {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  margin-top: 15px;
}
</style>
