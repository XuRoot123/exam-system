<script>
import { getScoresByUserId } from "@/api/Scores";
import { getAllChapter } from "@/api/ChapterApi";
import { getAllClassIdByTeacherId } from "@/api/ClassesApi";
import {
  getExamHistoryByChapterAndUserId,
  getExamHistoryByUserId,
} from "@/api/ExamApi";
import { getRealNameByUserId, getUserIdByClassId } from "@/api/UserApi";

export default {
  data() {
    return {
      teacherId: 0,
      showSearchByChapterList: false,
      showFindAllExam: false,
      examId: [],
      exams: [],
      studentId: [],
      chapters: [],
      scores: [],
      choiceChapter: 1,
      classId: 0,
    };
  },
  methods: {
    async initializationData() {
      this.teacherId = JSON.parse(window.sessionStorage.getItem("user")).userId;
      await getAllClassIdByTeacherId(this.teacherId)
        .then((res) => {
          this.classId = res.data;
        })
        .catch((err) => {
          this.$message.error(
            "在获取班级id时出错！getAllClassIdByTeacherId",
            err.message
          );
        });
      await getUserIdByClassId(this.classId)
        .then((res) => {
          this.studentId = res.data;
        })
        .catch((err) => {
          this.$message.error(
            "在获取用户id时出错！getUserIdByClassId",
            err.message
          );
        });
      const scorePromises = this.studentId.map((studentId) =>
        getScoresByUserId(studentId)
      );
      const scoreResults = await Promise.all(scorePromises);
      scoreResults.forEach((res, index) => {
        this.examId[index] = res.data.map((item) => item.examId);
        this.scores = this.scores.concat(res.data);
      });
      await getAllChapter()
        .then((res) => {
          var tempChapters = [];
          for (let i = 0; i < res.data.length; i++) {
            if (res.data[i].chapterId !== 5) {
              tempChapters.push({
                chapterId: res.data[i].chapterId,
                chapterName: res.data[i].chapterName,
              });
            }
          }
          this.chapters = tempChapters;
        })
        .catch((err) => {
          this.$message.error(err.message);
        });
    },
    async mergeDataSources() {
      const userPromises = this.studentId.map((studentId) =>
        getRealNameByUserId(studentId)
      );
      let realNameResults = {};
      try {
        const nameResults = await Promise.all(userPromises);
        nameResults.forEach((result, index) => {
          realNameResults[this.studentId[index]] = result.data;
        });
      } catch (error) {
        this.$message.error(error.message, "在getRealNameByUserId出错");
      }
      for (let i = 0; i < this.exams.length; i++) {
        const exam = this.exams[i];
        const scoreInfo = this.scores.find(
          (score) => score.examId === exam.examId
        );
        if (scoreInfo) {
          exam.subjectiveScore = scoreInfo.subjectiveScore;
          exam.objectiveScore = scoreInfo.objectiveScore;
          const studentName = realNameResults[scoreInfo.studentId];
          exam.userName = studentName || "未知学生";
        } else {
          exam.subjectiveScore = "-";
          exam.objectiveScore = "-";
          exam.userName = "未知学生";
        }
      }
    },
    async showFindAllExams() {
      this.showSearchByChapterList = false;
      const examPromises = this.studentId.map((studentId) =>
        getExamHistoryByUserId(studentId)
      );
      try {
        const examResults = await Promise.all(examPromises);
        this.exams = [];
        examResults.forEach((result) => {
          this.exams = this.exams.concat(result.data);
        });
        await this.mergeDataSources();
        this.showFindAllExam = true;
      } catch (err) {
        this.$message.error(
          "获取考试历史失败: " + err.message + "位置在getExamHistoryByUserId"
        );
      }
    },
    completeChoiceChapter() {
      this.showFindAllExam = false;
      getExamHistoryByChapterAndUserId(this.userId, this.choiceChapter)
        .then((res) => {
          this.exams = res.data;
          this.mergeDataSources();
        })
        .catch((err) => {
          this.$message.error(err.message + "服务器出错！");
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
    <div style="font-size: 24px">分数查询</div>
    <div v-if="showFindAllExam === false && showSearchByChapterList === false">
      <el-button type="primary" @click="showSearchByChapterList = true">
        按照章节查找
      </el-button>
      <el-button type="primary" @click="showFindAllExams()">查找全部</el-button>
    </div>
    <div>
      <el-select
        v-if="showSearchByChapterList"
        placeholder="请选择章节"
        v-model="choiceChapter"
        @change="completeChoiceChapter"
      >
        <el-option
          v-for="item in chapters"
          :key="item.chapterId"
          :label="item.chapterName"
          :value="item.chapterId"
        >
        </el-option>
      </el-select>
      <el-table :data="exams" border stripe v-if="showFindAllExam">
        <el-table-column label="学生名称" prop="userName"></el-table-column>
        <el-table-column label="考试名称" prop="paperName"></el-table-column>
        <el-table-column label="考试总分" prop="totalScore"></el-table-column>
        <el-table-column label="实际得分" prop="actualScore"></el-table-column>
        <el-table-column label="作答时间" prop="examAt"></el-table-column>
        <el-table-column
          label="主观题得分"
          prop="subjectiveScore"
        ></el-table-column>
        <el-table-column
          label="客观题得分"
          prop="objectiveScore"
        ></el-table-column>
      </el-table>
      <el-table
        :data="exams"
        border
        stripe
        v-if="showSearchByChapterList && choiceChapter !== 0"
      >
        <el-table-column label="学生名称" prop="userName"></el-table-column>
        <el-table-column label="考试名称" prop="paperName"></el-table-column>
        <el-table-column label="考试总分" prop="totalScore"></el-table-column>
        <el-table-column label="实际得分" prop="actualScore"></el-table-column>
        <el-table-column label="作答时间" prop="examAt"></el-table-column>
        <el-table-column
          label="主观题得分"
          prop="subjectiveScore"
        ></el-table-column>
        <el-table-column
          label="客观题得分"
          prop="objectiveScore"
        ></el-table-column>
      </el-table>
    </div>
    <div v-if="showSearchByChapterList || showFindAllExam">
      <el-button
        type="primary"
        @click="
          showSearchByChapterList = false;
          showFindAllExam = false;
          choiceChapter = 0;
        "
      >
        返回
      </el-button>
    </div>
  </div>
</template>

<style></style>
