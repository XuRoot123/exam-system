<script>
import {
  deleteUndeterminedUser,
  enroll,
  getAllUndeterminedUserPage,
} from "@/api/UserApi";

export default {
  data() {
    return {
      tempUserList: [],
      user: {
        userId: "",
        userName: "",
        realName: "",
        email: "",
        phone: "",
        role: "",
        classId: "",
        createdAt: "",
      },
      pageSizes: [5, 10, 20],
      total: 0,
      choosePageSize: 5,
      currentPage: 1,
      pageIndex: 1,
      params: {
        userId: JSON.parse(window.sessionStorage.getItem("user")).userId,
        pageIndex: this.pageIndex,
        pageSize: this.choosePageSize,
        realName: this.realName,
        role: this.role,
      },
      realName: "",
    };
  },
  methods: {
    async loadUserList() {
      let params = this.params;
      var data = null;
      if (
        params.pageIndex === undefined ||
        params.pageSize === undefined ||
        params.teacherId === undefined
      ) {
        params = {
          userId: JSON.parse(window.sessionStorage.getItem("user")).userId,
          pageIndex: 1,
          pageSize: 5,
          realName: "",
          role: "",
        };
        await getAllUndeterminedUserPage(params)
          .then((res) => {
            data = res;
          })
          .catch(() => {
            this.$message.error("服务器异常！");
          });
      } else {
        let params = this.params;
        await getAllUndeterminedUserPage(params)
          .then((res) => {
            data = res;
          })
          .catch(() => {
            this.$message.error("服务器异常！");
          });
      }
      if (data.data === null || data.data === undefined) {
        this.$message.info("没有更多数据！");
      } else {
        this.tempUserList = data.data.list;
        this.total = data.data.total;
        this.tempUserList.forEach(this.setSex);
      }
    },
    deleteUser(row) {
      console.log(row);
      this.$confirm("此操作将永久删除该用户, 是否继续?", "提示", {
        cancelButtonText: "取消",
        confirmButtonText: "我已知晓后果，确定删除该用户！",
      })
        .then(() => {
          return deleteUndeterminedUser(row.userId)
            .then((res) => {
              if (res.data === 1) {
                this.$message.success("删除成功！");
              } else {
                this.$message.error("该用户已被删除！");
              }
            })
            .catch(() => {
              this.$message.error("服务器异常！");
            });
        })
        .then(() => {
          this.loadUserList();
        })
        .catch(() => {
          this.$message.info("已取消删除！");
        });
    },
    loadUserListLike() {
      this.realName = this.realName.trim();
      this.params.pageIndex = 1;
      this.params.realName = this.realName;
      this.loadUserList();
    },
    changePageSize(p) {
      this.choosePageSize = p;
      this.params.pageSize = p;
      this.loadUserList();
    },
    jumpPage(p) {
      this.pageIndex = p;
      this.params.pageIndex = p;
      this.loadUserList();
    },
    nextPage(p) {
      this.pageIndex = p;
      this.params.pageIndex = p;
      this.loadUserList();
    },
    lastPage(p) {
      this.pageIndex = p;
      this.params.pageIndex = p;
      this.loadUserList();
    },
    initializationData() {
      if (
        this.tempUserList.classId === "" ||
        this.tempUserList.classId === null
      ) {
        this.tempUserList.classId = "无";
      }
      this.choosePageSize = this.pageSizes[0];
      this.params.pageIndex = 1;
      this.params.pageSize = 5;
      this.params.realName = this.realName;
      this.loadUserList();
    },
    setSex(user) {
      if (user.sex === 1) {
        user.sex = "男";
      } else {
        user.sex = "女";
      }
    },
    reverseSideSetSex(user) {
      if (user.sex === "男") {
        user.sex = 1;
      } else {
        user.sex = 0;
      }
      return user;
    },
    enrollUser(row) {
      var user = this.reverseSideSetSex(row);
      user.createdAt = new Date().toLocaleString();
      enroll(user)
        .then((res) => {
          if (res.data === 1) {
            return deleteUndeterminedUser(row.userId)
              .then(() => {
                this.$message.success("添加成功");
                this.loadUserList();
              })
              .catch(() => {
                this.$message.error("服务器异常！");
              });
          } else {
            this.$message.error("添加失败！");
          }
        })
        .catch(() => {
          this.$message.error("通过失败！");
        });
    },
  },
  async created() {
    await this.loadUserList();
    this.initializationData();
  },
};
</script>

<template>
  <div style="margin: 0 auto">
    <h1 style="text-align: center; width: 100%">用户管理</h1>
    <el-input
      style="width: 20%"
      placeholder="请输入你要查找的名字："
      v-model="realName"
      @blur="loadUserListLike"
      clearable
    ></el-input>
    <el-button
      style="margin-left: 20px"
      @click="loadUserListLike"
      type="primary"
      >查找
    </el-button>
    <el-table :data="tempUserList" stripe style="width: 100%">
      <el-table-column prop="userId" label="id" width="180"></el-table-column>
      <el-table-column
        prop="userName"
        label="用户名"
        width="180"
      ></el-table-column>
      <el-table-column
        prop="realName"
        label="姓名"
        width="180"
      ></el-table-column>
      <el-table-column prop="email" label="邮箱" width="180"></el-table-column>
      <el-table-column
        prop="phone"
        label="电话号码"
        width="180"
      ></el-table-column>
      <el-table-column
        prop="classId"
        label="班级编号"
        width="180"
      ></el-table-column>
      <el-table-column
        prop="address"
        label="地址"
        width="180"
      ></el-table-column>
      <el-table-column prop="sex" label="性别" width="180"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button @click="enrollUser(scope.row)" type="primary" size="small">
            通过 </el-button
          ><el-button @click="deleteUser(scope.row)" type="text" size="small"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page="currentPage === 1 ? 1 : currentPage"
      :page-sizes="pageSizes === null ? [5, 10, 15] : pageSizes"
      :page-size="choosePageSize === 5 ? 5 : choosePageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @next-click="nextPage"
      @prev-click="lastPage"
      @current-change="jumpPage"
      @size-change="changePageSize"
    >
    </el-pagination>
  </div>
</template>
