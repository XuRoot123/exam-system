<script>
import { enroll, enrollSchoolUser } from "@/api/UserApi";

export default {
  data() {
    return {
      showClassSelect: true,
      role: "",
      undetermined_users: {
        userName: "",
        password: "",
        real_name: "",
        email: "",
        phone: "",
        role: "",
        class_id: "",
        sex: "1",
        level: "",
        created_at: "",
      },
      classes: [
        {
          class_id: "",
          class_name: "",
        },
      ],
      nextRole: "",
      roles: [
        { label: "教师", value: "teacher" },
        { label: "学生", value: "student" },
        { label: "管理员", value: "admin" },
      ],
      rules: {
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 8,
            max: 16,
            message: "长度在 8 到 16 个字符",
            trigger: "blur",
          },
        ],
        // role: [{ required: true, message: "请选择职位", trigger: "change" }],
        phone: [
          {
            required: true,
            len: 11,
            message: "长度为11位",
            trigger: "blur",
          },
        ],
        real_name: [
          { required: true, message: "请输入真实姓名", trigger: "blur" },
          { min: 2, max: 5, message: "长度在 2 到 5 个字符", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    async enrollUser() {
      const valid = await this.$refs.form.validate().catch(() => false);
      if (!valid) {
        this.$message.error("请正确填写所有必填信息");
        return;
      }
      await this.initializationUserInfo();
      console.log(this.undetermined_users);
      if (
        this.undetermined_users.role !== "" &&
        this.undetermined_users.role !== "admin"
      ) {
        enrollSchoolUser(this.undetermined_users)
          .then(() => {
            this.$alert(
              `你的级别为${this.undetermined_users.role},请联系${this.undetermined_users.level}为你同意注册！`,
              `提示`
            );
            this.$router.push("/login");
          })
          .catch((err) => {
            this.$message.error(err.message);
          });
      } else {
        this.undetermined_users.created_at = new Date().toLocaleString();
        enroll(this.undetermined_users)
          .then(() => {
            this.$alert(
              "注册成功你的级别是：" + this.undetermined_users.role,
              "提示"
            );
            this.$router.push("/login");
          })
          .catch((err) => {
            this.$message.error(err.message);
          });
      }
    },
    initializationUserInfo() {
      const roleMap = {
        teacher: 1,
        student: 2,
        admin: 3,
      };
      const roleInt = roleMap[this.role] || 0;
      this.undetermined_users.role = this.role;
      var number = (roleInt * 2) / 2;
      this.nextRole = 0;
      this.nextRole = number + 1;
      console.log(this.nextRole);
      if (this.nextRole === 2) {
        this.undetermined_users.level = "admin";
      }
      if (this.nextRole === 3) {
        this.undetermined_users.level = "teacher";
      }
      switch (this.undetermined_users.role) {
        case "1":
          this.undetermined_users.role = "teacher";
          break;
        case "2":
          this.undetermined_users.role = "student";
          break;
        case "3":
          this.undetermined_users.role = "admin";
          break;
      }
      switch (this.undetermined_users.sex) {
        case "1":
          this.undetermined_users.sex = "男";
          break;
        case "2":
          this.undetermined_users.sex = "女";
          break;
      }
      switch (this.undetermined_users.sex) {
        case "男":
          this.undetermined_users.sex = "1";
          break;
        case "女":
          this.undetermined_users.sex = "2";
          break;
      }
    },
    getAllClasses() {
      var item = window.sessionStorage.getItem("classes");
      item = JSON.parse(item);
      let itemTemp = [{}];
      for (let i = 0; i < item.length; i++) {
        itemTemp[i] = {
          class_id: item[i].class_id,
          class_name: item[i].class_name,
        };
      }
      this.classes = itemTemp;
    },
    cancelEnroll() {
      this.$router.push("/login");
    },
  },
  mounted() {
    this.getAllClasses();
  },
  watch: {
    role: {
      handler(newVal) {
        const adminRoles = ["admin", "3", "管理员", "teacher", "教师", "2"];
        if (adminRoles.includes(newVal)) {
          this.showClassSelect = false;
          this.undetermined_users.class_id = "";
        } else {
          this.showClassSelect = true;
          this.undetermined_users.class_id = "";
        }
      },
      immediate: false,
      deep: false,
    },
  },
};
</script>
<template>
  <div class="whole">
    <div class="enroll">
      <el-form
        :rules="rules"
        ref="form"
        :model="undetermined_users"
        label-width="120px"
        class="demo-ruleForm"
      >
        <h2>enroll</h2>
        <el-form-item label="名称" prop="userName">
          <el-input v-model="undetermined_users.userName"></el-input>
        </el-form-item>
        <el-form-item label="真实姓名" prop="realName">
          <el-input v-model="undetermined_users.real_name"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
            v-model="undetermined_users.password"
            type="password"
            show-password
          ></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio v-model="undetermined_users.sex" label="1">男</el-radio>
          <el-radio v-model="undetermined_users.sex" label="2">女</el-radio>
        </el-form-item>
        <el-form-item prop="phone" label="手机号">
          <el-input
            oninput="value=value.replace(/[^0-9]/g,'')"
            v-model="undetermined_users.phone"
          ></el-input>
        </el-form-item>
        <el-form-item prop="email" label="邮箱">
          <el-input v-model="undetermined_users.email"></el-input>
        </el-form-item>
        <el-form-item prop="role" label="职位">
          <el-select v-model="role" filterable placeholder="请选择">
            <el-option
              v-for="item in roles"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          v-if="showClassSelect"
          label="班级"
          style="display: block"
        >
          <el-select
            v-model="undetermined_users.class_id"
            filterable
            placeholder="请选择"
          >
            <el-option
              v-for="item in classes"
              :key="item.class_id"
              :label="item.class_name"
              :value="item.class_id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="enrollUser">立即创建</el-button>
          <el-button @click="cancelEnroll">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<style scoped>
.whole {
  height: 100vh;
  background: url("@/assets/enrollBack.jpg") center;
  background-size: cover;
}

.enroll {
  position: fixed;
  top: 50%;
  width: 40%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #e6f7ff;
  padding: 30px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
.enroll h2 {
  margin: auto auto 20px;
  text-align: center;
}

.enroll form {
  width: 80%;
}
</style>
