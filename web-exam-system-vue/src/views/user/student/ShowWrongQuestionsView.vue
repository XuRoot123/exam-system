<script>
import { getScoresByUserId } from "@/api/Scores";
import {
  getExamByExamId,
  getExamHistoryByChapterAndUserId,
  getExamHistoryByUserId,
} from "@/api/ExamApi";
import { getAllChapter } from "@/api/ChapterApi";

export default {
  data() {
    return {
      showSearchByChapterList: false,
      showFindAllExam: false,
      examId: [],
      userId: "",
      examPassed: [],
      chapters: [],
      choiceChapter: 0,
      exams: [],
    };
  },
  methods: {
    initializationData() {
      this.userId = JSON.parse(window.sessionStorage.getItem("user")).userId;
      getScoresByUserId(this.userId)
        .then((res) => {
          for (let i = 0; i < res.data.length; i++) {
            this.examId[i] = res.data[i].examId;
          }
        })
        .catch(() => {
          this.$message.error("服务器出错！");
        });
      for (let i = 0; i < this.examId.length; i++) {
        getExamByExamId(this.examId[i])
          .then((res) => {
            this.examPassed[i] = res.data;
          })
          .catch(() => {
            this.$message.error("服务器出错！");
          });
      }
      getAllChapter()
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
    completeChoiceChapter() {
      this.showFindAllExam = false;
      getExamHistoryByChapterAndUserId(this.userId, this.choiceChapter)
        .then((res) => {
          this.exams = res.data;
        })
        .catch((err) => {
          this.$message.error(err.message + "服务器出错！");
        });
    },
    showFindAllExams() {
      this.showSearchByChapterList = false;
      getExamHistoryByUserId(this.userId)
        .then((res) => {
          this.exams = res.data;
        })
        .catch((err) => {
          this.$message.error(err.message + "服务器出错！");
        });
    },
    showWrongQuestionsResult(examHistory) {
      console.log(examHistory);
    },
  },
  created() {
    this.initializationData();
  },
};
</script>

<template>
  <div>
    <div style="font-size: 24px">查找考过的考试</div>
    <div v-if="showFindAllExam === false && showSearchByChapterList === false">
      <el-button type="primary" @click="showSearchByChapterList = true">
        按照章节查找
      </el-button>
      <el-button
        type="primary"
        @click="
          showFindAllExam = true;
          showFindAllExams();
        "
        >查找全部</el-button
      >
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
        <el-table-column label="考试名称" prop="paperName"></el-table-column>
        <el-table-column label="考试总分" prop="totalScore"></el-table-column>
        <el-table-column label="实际得分" prop="actualScore"></el-table-column>
        <el-table-column label="作答时间" prop="examAt"></el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <el-button
              type="primary"
              @click="showWrongQuestionsResult(scope.row)"
            >
              查看考试错题
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-table
        :data="exams"
        border
        stripe
        v-if="showSearchByChapterList && choiceChapter !== 0"
      >
        <el-table-column label="考试名称" prop="paperName"></el-table-column>
        <el-table-column label="考试总分" prop="totalScore"></el-table-column>
        <el-table-column label="实际得分" prop="actualScore"></el-table-column>
        <el-table-column label="作答时间" prop="examAt"></el-table-column>
      </el-table>
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
  </div>
</template>

<style></style>
