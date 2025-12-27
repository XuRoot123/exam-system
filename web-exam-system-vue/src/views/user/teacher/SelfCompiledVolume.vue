<script>
import { getAllChapterId, getAllTitle } from "@/api/TitleApi";
import { getAllClass } from "@/api/ClassesApi";
import { addTestPaper } from "@/api/ExamApi";

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
      showTitles: false,
      singleChoice: [],
      fillBlank: [],
      multipleChoice: [],
      selectedTitles: [],
      showTitleType: "",
      titles: [],
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
      conditionalRetrieval: {
        examName: "",
        chapterId: "all",
        difficult: "all",
        createId: "",
        createTime: "",
        startTime: "",
        endTime: "",
        testExamTime: "1:30",
        examInstruct: "",
        classId: "all",
        questions: "",
      },
    };
  },
  methods: {
    getAllTitle(type) {
      getAllTitle(type)
        .then((res) => {
          if (type === "single_choice") {
            this.singleChoice = res.data;
          } else if (type === "fill_blank") {
            this.fillBlank = res.data;
          } else if (type === "multiple_choice") {
            this.multipleChoice = res.data;
          }
          console.log(res.data);
          this.showTitleType = type;
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
        addTestPaper(this.conditionalRetrieval)
          .then((res) => {
            if (res.data.code !== -1) {
              this.$message.success("添加成功！");
            } else {
              this.$message.error("添加失败！");
            }
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
      this.conditionalRetrieval.createId = JSON.parse(
        window.sessionStorage.getItem("user")
      ).userId;
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
      this.conditionalRetrieval.questions = this.titles;
    },
    resetData() {
      this.conditionalRetrieval = {
        examName: "",
        chapterId: "all",
        difficult: "all",
        createId: "",
        createTime: "",
        startTime: "",
        endTime: "",
        testExamTime: "1:30",
        examInstruct: "",
        classId: "all",
        questions: "",
      };
    },
    rollUp() {
      this.showTitles = !this.showTitles;
      this.showConditionalRetrieval = !this.showConditionalRetrieval;
    },
    deleteTitle(title) {
      this.titles = this.titles.filter(
        (item) => item.questionId !== title.questionId
      );
    },
    clearTitles() {
      this.titles = [];
      this.showTitles = false;
    },
    addTitles(title) {
      const exists = this.titles.some(
        (item) => item.questionId === title.questionId
      );
      if (!exists) {
        this.titles.push(title);
      }
    },
    showTitlesData() {
      this.showTitles = !this.showTitles;
    },
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
        .catch((error) => {
          console.error("获取章节失败:", error);
          this.$message.error("服务器异常！");
        });

      getAllClass()
        .then((res) => {
          this.classes = res.data.map((item) => {
            const classId = item.classId || item.class_id;
            return {
              classId: classId,
              className: (item.className || classId) + "班",
            };
          });
          this.classes.unshift({
            classId: "all",
            className: "所有班级",
          });
          this.classes = this.classes.filter((item) => item.classId !== 3);
        })
        .catch((error) => {
          console.error("获取班级失败:", error);
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
    <div>
      <el-button type="primary" @click="getAllTitle('single_choice')"
        >单选题题库</el-button
      >
      <el-button type="primary" @click="getAllTitle('fill_blank')"
        >填空题题库</el-button
      >
      <el-button type="primary" @click="getAllTitle('multiple_choice')"
        >多选题题库</el-button
      >
      <a style="float: right" @click="showTitlesData()"
        ><i
          class="el-icon-s-order"
          v-if="titles.length !== 0"
          style="width: 30px; height: 30px; font-size: 30px"
        ></i
      ></a>
    </div>
    <el-table v-if="showTitleType === 'single_choice'" :data="singleChoice">
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
          <el-button @click="addTitles(scope.row)" type="text" size="small"
            >添加</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-table v-if="showTitleType === 'fill_blank'" :data="fillBlank">
      <el-table-column prop="questionId" label="题目编号" width="80">
      </el-table-column>
      <el-table-column prop="chapterId" label="所属章节" width="80">
      </el-table-column>
      <el-table-column prop="content" label="题目内容"> </el-table-column>
      <el-table-column prop="type" label="题目类型" width="150">
      </el-table-column>
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
          <el-button @click="addTitles(scope.row)" type="text" size="small"
            >添加</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-table v-if="showTitleType === 'multiple_choice'" :data="multipleChoice">
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
          <el-button @click="addTitles(scope.row)" type="text" size="small"
            >添加</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-drawer
      title="已选题目(如果有重复添加的题目会只保留一个)"
      :visible.sync="showTitles"
      direction="btt"
      size="60%"
    >
      <el-table :data="titles">
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
      <div style="float: right; margin-top: 20px">
        <el-button @click="rollUp()" type="primary">编卷</el-button>
        <el-button @click="clearTitles()" type="primary">清空</el-button>
        <el-button @click="showTitles = false" type="primary">取消</el-button>
      </div>
    </el-drawer>
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

<style scoped></style>
