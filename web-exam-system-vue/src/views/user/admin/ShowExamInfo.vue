<script>
import { deleteExam, getAllTestPaper } from "@/api/ExamApi";

export default {
  data() {
    return {
      exams: [],
    };
  },
  methods: {
    initializationData() {
      getAllTestPaper("")
        .then((res) => {
          this.exams = res.data;
          for (let i = 0; i < this.exams.length; i++) {
            if (this.exams[i].chapterId === 5) {
              this.exams[i].chapterId = "all";
            }
            if (this.exams[i].classId === 3) {
              this.exams[i].classId = "all";
            }
          }
        })
        .catch((err) => {
          this.$message.error(err.message);
        });
    },
    deleteExam(exam) {
      deleteExam(exam.paperId)
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
    <el-table :data="exams">
      <el-table-column label="试卷ID" prop="paperId"></el-table-column>
      <el-table-column label="名称" prop="paperName"></el-table-column>
      <el-table-column label="编卷人" prop="teacherName"></el-table-column>
      <el-table-column label="状态" prop="status"></el-table-column>
      <el-table-column
        label="班级"
        prop="classId"
        width="80px"
      ></el-table-column>
      <el-table-column
        label="章节"
        prop="chapterId"
        width="80px"
      ></el-table-column>
      <el-table-column
        label="总分"
        prop="totalScore"
        width="80px"
      ></el-table-column>
      <el-table-column label="告知" prop="instructions"></el-table-column>
      <el-table-column label="创建时间" prop="createdAt"></el-table-column>
      <el-table-column
        label="作答时间(分钟)"
        prop="testExamTime"
        width="120px"
      ></el-table-column>
      <el-table-column label="开考时间" prop="startTime"></el-table-column>
      <el-table-column label="结束时间" prop="endTime"></el-table-column>
      <el-table-column fixed="right" label="操作" width="100">
        <template slot-scope="scope">
          <el-button @click="deleteExam(scope.row)" type="text" size="small"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<style></style>
