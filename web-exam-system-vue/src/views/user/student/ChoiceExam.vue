<script>
import { getAllChapter } from "@/api/ChapterApi";
import {
  getAllTestPaperByClassId,
  getTestPaperByChapterId,
} from "@/api/ExamApi";

export default {
  data() {
    return {
      showChapterExamButton: true,
      showAllExamButton: true,
      showChapterExam: false,
      showAllExam: false,
      showAllExamsAvailableForExamination: false,
      showTheExamBeingObserved: false,
      allTestPaper: [],
      theExamBeingObserved: {},
      chapters: [
        {
          chapterId: "",
          chapterName: "",
        },
      ],
      choiceChapter: "",
      step: 1,
    };
  },
  methods: {
    async initializationData() {
      getAllChapter()
        .then((res) => {
          var tempChapters = [];
          for (let i = 0; i < res.data.length; i++) {
            if (res.data[i].chapterId !== 5) {
              tempChapters.push({
                chapterId: "第" + res.data[i].chapterId + "章",
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
      if (this.choiceChapter !== undefined && this.choiceChapter !== "") {
        const chapterId = this.choiceChapter.replace(/[^0-9]/g, "");
        getTestPaperByChapterId(
          chapterId,
          JSON.parse(window.sessionStorage.getItem("user")).classId
        )
          .then((res) => {
            this.step++;
            this.allTestPaper = res.data;
          })
          .catch((err) => {
            this.$message.error(err.message);
          });
      }
    },
    getAllExamsToClass() {
      getAllTestPaperByClassId(
        JSON.parse(window.sessionStorage.getItem("user")).classId
      )
        .then((res) => {
          this.allTestPaper = res.data;
        })
        .catch((err) => {
          this.$message.error(err.message);
        });
    },
    goToExam() {
      this.showTheExamBeingObserved = false;
      this.$router.push({
        path: "/exam/ExamView",
        query: {
          examId: this.theExamBeingObserved.examId,
          testPaperId: this.theExamBeingObserved.paperId,
          durationMin: this.theExamBeingObserved.testExamTime,
          testPaperDescription: this.theExamBeingObserved.instructions,
        },
      });
    },
    showExamInfo(row) {
      this.theExamBeingObserved = row;
      this.showAllExamsAvailableForExamination = false;
      this.showTheExamBeingObserved = true;
    },
    closeShowAllExamsAvailableForExamination() {
      if (this.showAllExam === true && this.step === 1) {
        this.showAllExamsAvailableForExamination = false;
        this.showChapterExam = false;
        this.showAllExam = false;
        this.showChapterExamButton = true;
        this.showAllExamButton = true;
      } else {
        this.choiceChapter = "";
      }
    },
  },
  async created() {
    await this.initializationData();
  },
};
</script>

<template>
  <div>
    <div>
      <div style="display: flex; gap: 20px; margin: 0 20px">
        <div v-if="showAllExamButton">
          <el-button
            @click="
              (showChapterExamButton = false),
                (showAllExamButton = false),
                (showAllExam = true),
                (showChapterExam = false),
                (showAllExamsAvailableForExamination = true),
                getAllExamsToClass()
            "
            type="primary"
          >
            查看所有考试
          </el-button>
        </div>
        <div v-if="showChapterExamButton">
          <el-button
            @click="
              (showChapterExamButton = false),
                (showAllExamButton = false),
                (showAllExam = false),
                (showChapterExam = true)
            "
            type="primary"
          >
            查看章节考试
          </el-button>
        </div>
      </div>
    </div>
    <div v-if="showChapterExam">
      <el-select
        v-if="step === 1 && showChapterExam"
        @change="completeChoiceChapter"
        v-model="choiceChapter"
        placeholder="请选择章节"
      >
        <el-option
          v-for="item in chapters"
          :key="item.chapterId"
          :label="item.chapterName"
          :value="item.chapterId"
        >
        </el-option>
      </el-select>
      <el-button
        type="primary"
        style="margin-left: 20px"
        @click="
          (showChapterExam = false),
            (showAllExam = false),
            (showAllExamButton = true),
            (showChapterExamButton = true),
            (step = 1)
        "
        v-if="step === 1 && showChapterExam"
        >返回</el-button
      >
      <el-button
        @click="showAllExamsAvailableForExamination = true"
        v-if="step === 2 && showChapterExam"
        type="primary"
        >选择该章节的所有可考考试</el-button
      >
      <el-button
        @click="
          (showChapterExam = false),
            (showAllExam = false),
            (showAllExamButton = true),
            (showChapterExamButton = true),
            (step = 1)
        "
        v-if="(step === 2 && showChapterExam) || (step === 1 && showAllExam)"
        >返回</el-button
      >
      <el-button
        @click="showAllExamsAvailableForExamination = true"
        v-if="step === 1 && showAllExam"
        type="primary"
        >查看所有可考考试
      </el-button>
    </div>
    <div
      style="
        position: absolute;
        bottom: 20px;
        left: 0;
        right: 0;
        margin: 0 auto 50px;
        width: 80%;
        max-width: 800px;
      "
      v-if="showChapterExam"
    >
      <el-steps :active="step" v-if="showChapterExam">
        <el-step title="选择章节" description="选择考试的章节"></el-step>
        <el-step title="选择考试" description="选择可以考试的考试"></el-step>
        <el-step
          title="查看考试"
          description="查看考试信息，并准备考试"
        ></el-step>
      </el-steps>
    </div>
    <el-drawer
      @close="closeShowAllExamsAvailableForExamination"
      destroy-on-close
      size="60vh"
      :visible.sync="showAllExamsAvailableForExamination"
      direction="btt"
    >
      <el-table
        :data="allTestPaper"
        stripe
        style="margin: auto; width: 90%; font-size: 16px"
      >
        <el-table-column
          align="center"
          label="考试名称"
          prop="paperName"
        ></el-table-column>
        <el-table-column
          align="center"
          label="考前说明"
          prop="instructions"
        ></el-table-column>
        <el-table-column
          align="center"
          label="考试可用时间（分）"
          prop="testExamTime"
        ></el-table-column>
        <el-table-column
          align="center"
          label="考试总分"
          prop="totalScore"
        ></el-table-column>
        <el-table-column align="center" prop="startTime" label="开始时间">
        </el-table-column>
        <el-table-column align="center" prop="endTime" label="结束时间">
        </el-table-column>
        <el-table-column align="center" label="是否可考">
          <template slot-scope="scope">
            <span v-if="new Date(scope.row.startTime) > new Date()"
              >未开始</span
            >
            <span v-else-if="new Date(scope.row.endTime) < new Date()"
              >已完结</span
            >
            <span v-else>进行中</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button
              type="primary"
              @click="showExamInfo(scope.row), step++"
              :disabled="
                new Date(scope.row.startTime) > new Date() ||
                new Date(scope.row.endTime) < new Date()
              "
            >
              查看，并准备考试
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-drawer>
    <el-descriptions
      style="font-size: 16px"
      title="考试信息"
      v-if="showTheExamBeingObserved"
    >
      <el-descriptions-item label="试卷名称">{{
        theExamBeingObserved.paperName
      }}</el-descriptions-item>
      <el-descriptions-item label="试卷描述">{{
        theExamBeingObserved.instructions
      }}</el-descriptions-item>
      <el-descriptions-item label="开始时间">{{
        theExamBeingObserved.startTime
      }}</el-descriptions-item>
      <el-descriptions-item label="结束时间">
        {{ theExamBeingObserved.endTime }}
      </el-descriptions-item>
      <el-descriptions-item label="考试期间可使用的时间(分)">
        {{ theExamBeingObserved.testExamTime }}min
      </el-descriptions-item>
      <el-descriptions-item label="考试总分">
        {{ theExamBeingObserved.totalScore }}
      </el-descriptions-item>
      <el-descriptions-item label="出题教师">
        {{
          theExamBeingObserved.teacherName !== "root_teacher"
            ? theExamBeingObserved.teacherName
            : "系统"
        }}
      </el-descriptions-item>
      <el-descriptions-item label="创建时间">
        {{ theExamBeingObserved.createdAt }}
      </el-descriptions-item>
      <el-descriptions-item label="及格分数">
        {{ theExamBeingObserved.totalScore * 0.6 }}
      </el-descriptions-item>
      <el-descriptions-item>
        <div style="text-align: center; margin: 40vh auto 20px">
          <el-button
            size="large"
            style="padding: 12px 24px; font-size: 16px; margin-right: 20px"
            @click="goToExam"
            type="primary"
          >
            我准备好了！前往考试
          </el-button>
          <el-button
            @click="
              (showChapterExam = false),
                (showAllExam = false),
                (showAllExamButton = true),
                (showChapterExamButton = true),
                (showTheExamBeingObserved = false),
                (step = 1)
            "
            type="primary"
            size="large"
            style="margin-right: 20px; padding: 12px 24px; font-size: 16px"
            >返回</el-button
          >
        </div>
      </el-descriptions-item>
    </el-descriptions>
  </div>
</template>

<style></style>
