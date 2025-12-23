<template>
  <div style="margin: 0 auto">
    <el-input
      style="width: 20%"
      placeholder="请输入你要查找的名字："
      v-model="real_name"
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
      <el-table-column fixed prop="user_id" label="编号" width="150">
      </el-table-column>
      <el-table-column
        prop="userName"
        label="账号名"
        width="120"
      ></el-table-column>
      <el-table-column prop="real_name" label="用户名" width="120">
      </el-table-column>
      <el-table-column prop="sex" label="性别" width="120"></el-table-column>
      <el-table-column
        prop="class_id"
        label="班级id"
        width="120"
      ></el-table-column>
      <el-table-column prop="email" label="邮箱" width="120"></el-table-column>
      <el-table-column prop="role" label="职位" width="120"> </el-table-column>
      <el-table-column prop="phone" label="电话" width="120"></el-table-column>
      <el-table-column
        prop="created_at"
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
import { deleteUser, showUserListPage } from "@/api/UserApi";

export default {
  data() {
    return {
      userList: [],
      user: {
        user_id: "",
        userName: "",
        password: "",
        real_name: "",
        email: "",
        phone: "",
        role: "",
        class_id: "",
        created_at: "",
      },
      blackListUserId: [17],
      userName: "",
      real_name: "",
      pageSizes: [5, 10, 20],
      total: 0,
      choosePageSize: 5,
      currentPage: 1,
      pageIndex: 1,
      params: {
        pageIndex: this.pageIndex,
        pageSize: this.choosePageSize,
        real_name: this.real_name,
        role: this.role,
      },
    };
  },
  methods: {
    async loadUserList() {
      let params = this.params;
      var data = null;
      if (params.pageIndex === undefined || params.pageSize === undefined) {
        params = {
          pageIndex: 1,
          pageSize: 5,
          real_name: "",
          role: "",
        };
        await showUserListPage(params)
          .then((res) => {
            data = res;
          })
          .catch(() => {
            this.$message.error("服务器异常！");
          });
      } else {
        let params = this.params;
        await showUserListPage(params)
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
        if (this.blackListUserId.includes(this.userList[i].user_id)) {
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
          return deleteUser(row.user_id);
        })
        .then(() => {
          this.loadUserList();
        })
        .catch(() => {
          this.$message.info("已取消删除！");
        });
    },
    ShowBlackList(user) {
      if (this.blackListUserId.includes(user.user_id)) {
        Object.keys(user).forEach((key) => {
          user[key] = "你无权查看此用户！";
        });
      }
    },
    loadUserListLike() {
      this.real_name = this.real_name.trim();
      this.params.pageIndex = 1;
      this.params.real_name = this.real_name;
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
      if (this.userList.class_id === "" || this.userList.class_id === null) {
        this.userList.class_id = "无";
      }
      this.choosePageSize = this.pageSizes[0];
      this.params.pageIndex = 1;
      this.params.pageSize = 5;
      this.params.real_name = this.real_name;
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
