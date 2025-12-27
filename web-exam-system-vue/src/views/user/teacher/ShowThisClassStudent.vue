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
    <el-table :data="userList" border style="width: 100%">
      <el-table-column fixed prop="userId" label="编号" width="150">
      </el-table-column>
      <el-table-column
        prop="userName"
        label="账号名"
        width="120"
      ></el-table-column>
      <el-table-column prop="realName" label="用户名" width="120">
      </el-table-column>
      <el-table-column prop="sex" label="性别" width="120"></el-table-column>
      <el-table-column
        prop="classId"
        label="班级id"
        width="120"
      ></el-table-column>
      <el-table-column prop="email" label="邮箱" width="120"></el-table-column>
      <el-table-column prop="role" label="职位" width="120"> </el-table-column>
      <el-table-column prop="phone" label="电话" width="120"></el-table-column>
      <el-table-column
        prop="createdAt"
        label="创建时间"
        width="200"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column label="操作" width="100">
        <template slot-scope="scope">
          <el-button @click="deleteUser(scope.row)" type="text" size="small"
            >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="block">
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
  </div>
</template>
<script>
import { deleteUser, getUserByClassId } from "@/api/UserApi";

export default {
  data() {
    return {
      userList: [],
      user: {
        userId: "",
        userName: "",
        password: "",
        realName: "",
        email: "",
        phone: "",
        role: "",
        classId: "",
        createdAt: "",
      },
      blackListUserId: [17],
      userName: "",
      realName: "",
      pageSizes: [5, 10, 20],
      total: 0,
      choosePageSize: 5,
      currentPage: 1,
      pageIndex: 1,
      params: {
        teacherId: JSON.parse(window.sessionStorage.getItem("user")).userId,
        pageIndex: this.pageIndex,
        pageSize: this.choosePageSize,
        realName: this.realName,
        role: this.role,
      },
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
          teacherId: JSON.parse(window.sessionStorage.getItem("user")).userId,
          pageIndex: 1,
          pageSize: 5,
          realName: "",
          role: "",
        };
        await getUserByClassId(params)
          .then((res) => {
            data = res;
          })
          .catch(() => {
            this.$message.error("服务器异常！");
          });
      } else {
        let params = this.params;
        await getUserByClassId(params)
          .then((res) => {
            data = res;
          })
          .catch(() => {
            this.$message.error("服务器异常！");
          });
      }
      this.userList = data.data.list;
      this.total = data.data.total - this.blackListUserId.length;
      this.userList.forEach(this.setSex);
      for (let i = 0; i < this.userList.length; i++) {
        if (this.blackListUserId.includes(this.userList[i].userId)) {
          this.ShowBlackList(this.userList[i]);
        }
      }
    },
    deleteUser(row) {
      console.log(row);
      this.$confirm("此操作将永久删除该用户, 是否继续?", "提示", {
        cancelButtonText: "取消",
        confirmButtonText: "我已知晓后果，确定删除该用户！",
      })
        .then(() => {
          return deleteUser(row.userId);
        })
        .then(() => {
          this.loadUserList();
        })
        .catch(() => {
          this.$message.info("已取消删除！");
        });
    },
    ShowBlackList(user) {
      if (this.blackListUserId.includes(user.userId)) {
        Object.keys(user).forEach((key) => {
          user[key] = "你无权查看此用户！";
        });
      }
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
      if (this.userList.classId === "" || this.userList.classId === null) {
        this.userList.classId = "无";
      }
      this.choosePageSize = this.pageSizes[0];
      this.params.pageIndex = 1;
      this.params.pageSize = 5;
      this.params.realName = this.realName;
      this.loadUserList();
    },
    setSex(user) {
      if (user.sex === "1") {
        user.sex = "男";
      } else {
        user.sex = "女";
      }
    },
  },
  async created() {
    await this.loadUserList();
    this.initializationData();
  },
};
</script>
