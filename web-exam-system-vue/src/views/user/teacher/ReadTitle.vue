<script>
import { getAllChapterId } from "@/api/TitleApi";

export default {
  data() {
    return {
      drawer: false,
      ifChoose: "false",
      ifBlank: "false",
      ifJudge: "false",
      chapter_id: [],
      difficulty: ["easy", "medium", "hard"],
      title: {
        chapter_id: "",
        content: "",
        type: "",
        options: [],
        answer: "",
        base_score: 0,
        explanation: "",
        difficulty: "",
      },
    };
  },
  methods: {
    initializationData() {
      getAllChapterId()
        .then((res) => {
          this.chapter_id = res.data.list;
        })
        .catch(() => {
          this.$message.error("服务器异常！");
        });
    },
  },
  created() {
    this.initializationData();
  },
};
</script>

<template>
  <div>
    <el-button @click="drawer = true" type="primary" style="margin-left: 16px">
      录入选择题
    </el-button>
    <el-drawer title="选择题录入" :visible.sync="drawer" :with-header="false">
      <h1>选择题录入</h1>
      <el-form ref="form" :model="title" label-width="80px">
        <el-form-item label="题目名称">
          <el-input v-model="title.content"></el-input>
        </el-form-item>
        <el-form-item label="题目章节">
          <el-input v-model="title.chapter_id"></el-input>
        </el-form-item>
        <el-form-item label="题目难度">
          <el-select v-model="title.difficulty" placeholder="请选择">
            <el-option label="简单" value="easy"></el-option>
            <el-option label="普通" value="medium"></el-option>
            <el-option label="困难" value="hard"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
    </el-drawer>
  </div>
</template>

<style></style>
