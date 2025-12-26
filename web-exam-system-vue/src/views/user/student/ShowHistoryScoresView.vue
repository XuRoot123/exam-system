<script>
import { getAllChapter } from "@/api/ChapterApi";
import {
  getExamHistoryByChapterAndUserId,
  getExamHistoryByUserId,
} from "@/api/ExamApi";
import { getScoresByUserId } from "@/api/Scores";

export default {
  data() {
    return {
      showSearchByChapterList: false,
      showFindAllExam: false,
      examId: [],
      exams: [],
      userId: 0,
      chapters: [],
      scores: [],
      choiceChapter: 1,
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
          this.scores = res.data;
          console.log("scores", this.scores);
        })
        .catch(() => {
          this.$message.error("服务器出错！");
        });
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
