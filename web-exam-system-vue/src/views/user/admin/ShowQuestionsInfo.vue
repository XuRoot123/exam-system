<script>
import { deleteTitle, getAllTitle } from "@/api/TitleApi";

export default {
  data() {
    return {
      questions: [],
    };
  },
  methods: {
    initializationData() {
      getAllTitle()
        .then((res) => {
          this.questions = res.data;
          for (let i = 0; i < this.questions.length; i++) {
            if (this.questions[i].chapterId === 5) {
              this.questions[i].chapterId = "all";
            }
            if (this.questions[i].classId === 3) {
              this.questions[i].classId = "all";
            }
          }
        })
        .catch((err) => {
          this.$message.error(err.message);
        });
    },
    deleteTitle(question) {
      deleteTitle(question.questionId)
        .then(() => {
          this.$message.success("删除成功");
          this.initializationData();
        })
        .catch(() => {
          this.$message.error("服务器出错！");
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
    <el-table :data="questions">
      <el-table-column prop="questionId" label="题目编号" width="80">
      </el-table-column>
      <el-table-column prop="chapterId" label="所属章节" width="80">
      </el-table-column>
      <el-table-column prop="content" label="题目内容"> </el-table-column>
      <el-table-column prop="type" label="题目类型" width="150">
      </el-table-column>
      <el-table-column prop="options" label="题目选项"> </el-table-column>
      <el-table-column prop="answer" label="题目答案" width="80">
      </el-table-column>
      <el-table-column prop="baseScore" label="题目分值" width="80">
      </el-table-column>
      <el-table-column prop="difficulty" label="题目难度" width="100">
      </el-table-column>
      <el-table-column prop="createdBy" label="创作者id" width="80">
      </el-table-column>
      <el-table-column prop="createdAt" label="创作时间"> </el-table-column>
      <el-table-column fixed="right" label="操作" width="100">
        <template slot-scope="scope">
          <el-button @click="deleteTitle(scope.row)" type="text" size="small"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<style></style>
