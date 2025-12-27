<script>
import { getAllChapterId } from "@/api/TitleApi";
import { addRandomTestPaper } from "@/api/ExamApi";
import { getAllClass } from "@/api/ClassesApi";

export default {
  data() {
    return {
      pickerOptions: {
        shortcuts: [
          {
            text: "最近一周",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近一个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近三个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit("pick", [start, end]);
            },
          },
        ],
      },
      showConditionalRetrieval: false,
      conditionalRetrieval: {
        examName: "",
        chapterId: "all",
        difficult: "all",
        singleChoiceCount: 5,
        judgeCount: 5,
        multipleChoiceCount: 5,
        createId: "",
        createTime: "",
        startTime: "",
        endTime: "",
        testExamTime: "1:30",
        examInstruct: "",
        classId: "all",
      },
      chapters: [],
      classes: [],
      difficult: [
        {
          difficult: "all",
          difficultName: "所有难度",
        },
        {
          difficult: "easy",
          difficultName: "简单",
        },
        {
          difficult: "middle",
          difficultName: "中等",
        },
        {
          difficult: "hard",
          difficultName: "困难",
        },
      ],
    };
  },
  methods: {
    initializationData() {
      getAllChapterId()
        .then((res) => {
          this.chapters = res.data.map((id) => ({
            chapterId: id,
            chapterName: "第" + id + "章",
          }));
          this.chapters.unshift({
            chapterId: "all",
            chapterName: "所有章节",
          });
          this.chapters = this.chapters.filter((item) => item.chapterId !== 5);
        })
        .catch(() => {
          this.$message.error("服务器异常！");
        });
      getAllClass()
        .then((res) => {
          this.classes = res.data.map((item) => ({
            classId: item.classId,
            className: item.classId + "班",
          }));
          this.classes.unshift({
            classId: "all",
            className: "所有班级",
          });
          this.classes = this.classes.filter((item) => item.classId !== 3);
        })
        .catch(() => {
          this.$message.error("服务器异常！");
        });
    },
    checkSubmit() {
      // return false;
      return true;
    },
    systemCompilation() {
      this.submitDataInitialization();
      if (this.checkSubmit()) {
        console.log(this.conditionalRetrieval);
        addRandomTestPaper(this.conditionalRetrieval)
          .then(() => {
            this.$message.success("添加成功！");
          })
          .catch(() => {
            this.$message.error("服务器异常！");
          });
      }
      this.resetData();
      this.showConditionalRetrieval = false;
    },
    submitDataInitialization() {
      var tempTime = this.conditionalRetrieval.testExamTime;
      var tempMin = 0;
      for (let i = 0; i < parseInt(tempTime.split(":")[0]); i++) {
        tempMin += 60;
      }
      tempMin += parseInt(tempTime.split(":")[1]);
      this.conditionalRetrieval.testExamTime = tempMin;
      this.conditionalRetrieval.createId = 25;
      this.conditionalRetrieval.createTime = new Date();
      this.conditionalRetrieval.endTime =
        this.conditionalRetrieval.startTime[1];
      this.conditionalRetrieval.startTime =
        this.conditionalRetrieval.startTime[0];
      if (this.conditionalRetrieval.chapterId === "all") {
        this.conditionalRetrieval.chapterId = "5";
      }
      if (this.conditionalRetrieval.difficult === "all") {
        this.conditionalRetrieval.difficult = "0";
      }
      if (this.conditionalRetrieval.classId === "all") {
        this.conditionalRetrieval.classId = "3";
      }
    },
    resetData() {
      this.conditionalRetrieval = {
        examName: "",
        chapterId: "all",
        difficult: "all",
        singleChoiceCount: 5,
        judgeCount: 5,
        multipleChoiceCount: 5,
        createId: "",
        createTime: "",
        startTime: "",
        endTime: "",
        testExamTime: "1:30",
        examInstruct: "",
        classId: "all",
      };
    },
  },
  created() {
    this.initializationData();
  },
};
</script>

<template>
  <div>
    <el-button type="primary" @click="showConditionalRetrieval = true"
      >随机编卷</el-button
    >
    <el-drawer
      :visible.sync="showConditionalRetrieval"
      :with-header="false"
      title="条件检索"
    >
      <h1>条件检索</h1>
      <el-form label-width="auto" ref="form" :model="conditionalRetrieval">
        <el-form-item label="试卷名称">
          <el-input
            v-model="conditionalRetrieval.examName"
            placeholder="请输入试卷名称"
          ></el-input>
        </el-form-item>
        <el-form-item label="题目章节">
          <el-select
            v-model="conditionalRetrieval.chapterId"
            filterable
            placeholder="请选择"
          >
            <el-option
              v-for="item in chapters"
              :key="item.chapterId"
              :label="item.chapterName"
              :value="item.chapterId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="考试班级">
          <el-select
            v-model="conditionalRetrieval.classId"
            filterable
            placeholder="请选择"
          >
            <el-option
              v-for="item in classes"
              :key="item.classId"
              :label="item.className"
              :value="item.classId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="题目难度">
          <el-select
            v-model="conditionalRetrieval.difficult"
            filterable
            placeholder="请选择"
          >
            <el-option
              v-for="item in difficult"
              :key="item.difficult"
              :label="item.difficultName"
              :value="item.difficult"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="单选题数量">
          <el-input-number
            v-model="conditionalRetrieval.singleChoiceCount"
            label="单选题数量"
            :min="0"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="填空题数量">
          <el-input-number
            v-model="conditionalRetrieval.judgeCount"
            label="填空题数量"
            :min="0"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="多选题数量">
          <el-input-number
            v-model="conditionalRetrieval.multipleChoiceCount"
            label="多选题数量"
            :min="0"
          ></el-input-number>
        </el-form-item>
        <el-form-item>
          <span class="demonstration">开始考试到结束考试时间</span>
          <el-date-picker
            v-model="conditionalRetrieval.startTime"
            type="datetimerange"
            :picker-options="pickerOptions"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="考试时限">
          <el-time-picker
            v-model="conditionalRetrieval.testExamTime"
            format="HH:mm"
            value-format="HH:mm"
            placeholder="请选择时间"
          >
          </el-time-picker>
        </el-form-item>
        <el-form-item label="考试说明">
          <el-input
            v-model="conditionalRetrieval.examInstruct"
            type="textarea"
            placeholder="请输入内容"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="systemCompilation()">
            确定</el-button
          >
          <el-button type="primary" @click="showConditionalRetrieval = false">
            取消</el-button
          >
          <el-button type="primary" @click="resetData()"> 重置</el-button>
        </el-form-item>
      </el-form>
    </el-drawer>
  </div>
</template>

<style></style>
