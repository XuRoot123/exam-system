<script>
import { addTitle, getAllChapterId } from "@/api/TitleApi";

export default {
  data() {
    return {
      chooserDawer: false,
      judgeDrawer: false,
      blankDrawer: false,
      ifChoose: false,
      ifBlank: false,
      ifJudge: false,
      Choose: {
        ChooseAbbreviation: "",
        Content: "",
      },
      Chooses: [
        { ChooseAbbreviation: "A", Content: "" },
        { ChooseAbbreviation: "B", Content: "" },
        { ChooseAbbreviation: "C", Content: "" },
        { ChooseAbbreviation: "D", Content: "" },
      ],
      choiceChooses: [
        { ChooseAbbreviation: "A", Content: "" },
        { ChooseAbbreviation: "B", Content: "" },
      ],
      judgeChoiceChoose: [
        { ChooseAbbreviation: "A", Content: "" },
        { ChooseAbbreviation: "B", Content: "" },
        { ChooseAbbreviation: "C", Content: "" },
        { ChooseAbbreviation: "D", Content: "" },
      ],
      chapters: [
        {
          chapterId: "",
          chapterName: "",
        },
      ],
      options: [],
      difficulty: ["easy", "medium", "hard"],
      title: {
        options: [],
        chapterId: 1,
        content: "",
        type: "",
        answer: "",
        baseScore: 5,
        explanation: "",
        difficulty: "",
        createdBy: "",
        createdAt: "",
      },
      judgeTitleAnswer: [],
      rules: {
        content: [
          { required: true, message: "请输入题目名称", trigger: "blur" },
        ],
        chapterId: [
          { required: true, message: "请选择题目章节", trigger: "change" },
        ],
        difficulty: [
          { required: true, message: "请选择题目难度", trigger: "change" },
        ],
        answer: [
          { required: true, message: "请选择正确答案", trigger: "change" },
        ],
        aOption: [
          {
            validator: (rule, value, callback) => {
              if (!this.Chooses[0].Content) {
                callback(new Error("A选项不能为空"));
              } else {
                callback();
              }
            },
            required: true,
            message: "选项A为必填选项",
            trigger: "blur",
          },
        ],
        bOption: [
          {
            validator: (rule, value, callback) => {
              if (!this.Chooses[1].Content) {
                callback(new Error("B选项不能为空"));
              } else {
                callback();
              }
            },
            required: true,
            message: "选项B为必填选项",
            trigger: "blur",
          },
        ],
        cOption: [
          {
            validator: (rule, value, callback) => {
              if (!this.Chooses[2].Content) {
                callback(new Error("C选项不能为空"));
              } else {
                callback();
              }
            },
            required: true,
            message: "选项C为必填选项",
            trigger: "blur",
          },
        ],
        dOption: [
          {
            validator: (rule, value, callback) => {
              if (!this.Chooses[3].Content) {
                callback(new Error("D选项不能为空"));
              } else {
                callback();
              }
            },
            required: true,
            message: "选项D为必填选项",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    initializationData() {
      getAllChapterId()
        .then((res) => {
          this.chapters = res.data
            .filter((id) => id !== 5)
            .map((id) => ({
              chapterId: id,
              chapterName: "第" + id + "章",
            }));
          var tempChapter = JSON.parse(JSON.stringify(this.chapters));
          this.chapters = tempChapter;
        })
        .catch(() => {
          this.$message.error("服务器异常！");
        });
    },
    choiceChoose() {
      this.choiceChooses = this.Chooses.filter(
        (choose) =>
          choose.Content !== "" ||
          choose.ChooseAbbreviation === "A" ||
          choose.ChooseAbbreviation === "B"
      );
    },
    updateChooses(item) {
      this.OptionalInput(item);
      var chooses = this.Chooses;
      for (let i = 0; i < chooses.length; i++) {
        var items = ["A", "B", "C", "D"];
        if (
          chooses[i].ChooseAbbreviation !== "" &&
          -1 === items.indexOf(chooses[i].ChooseAbbreviation)
        ) {
          chooses[i].ChooseAbbreviation = item.ChooseAbbreviation;
        }
      }
    },
    OptionalInput(Choose) {
      var Chooses = this.Chooses;
      for (let i = 0; i < Chooses.length; i++) {
        if (Chooses[i].ChooseAbbreviation === Choose.ChooseAbbreviation) {
          Chooses[i].Content = Choose.Content;
        }
      }
    },
    resetForm() {
      this.$confirm("此操作将清空所有输入，是否继续?", "提示", {
        cancelButtonText: "取消",
        confirmButtonText: "确定",
      })
        .then(() => {
          this.title = {
            chapterId: 1,
            options: [],
            content: "",
            type: "",
            answer: "",
            baseScore: 5,
            explanation: "",
            difficulty: "",
          };
        })
        .catch(() => {
          this.$message.info("取消清空");
        });
    },
    submitForm() {
      this.SubmitDataInitialization();
      console.log(this.title);
      if (this.checkSubmit()) {
        addTitle(this.title)
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
    },
    checkSubmit() {
      // return false;
      return true;
    },
    SubmitDataInitialization() {
      this.options = [];
      var title = this.title;
      if (this.ifBlank) {
        title.type = "fill_blank";
      } else if (this.ifChoose) {
        title.type = "single_choice";
      } else if (this.ifJudge) {
        title.type = "multiple_choice";
      }
      var tempUser = JSON.parse(sessionStorage.getItem("user"));
      title.createdBy = tempUser.userId;
      title.createdAt = new Date().toLocaleString();
      var tempOptions = this.choiceChooses;
      this.options = [];
      if (this.ifChoose) {
        for (let i = 0; i < tempOptions.length; i++) {
          var str1 =
            tempOptions[i].ChooseAbbreviation + "." + tempOptions[i].Content;
          this.options.push(str1);
          this.title.options = this.options;
        }
      }
      if (this.ifJudge) {
        tempOptions = this.Chooses;
        this.title.answer = this.judgeTitleAnswer.toString();
        for (let i = 0; i < tempOptions.length; i++) {
          var str2 =
            tempOptions[i].ChooseAbbreviation + "." + tempOptions[i].Content;
          this.options.push(str2);
          this.title.options = this.options;
        }
      }
      if (this.ifBlank) {
        this.title.options = null;
      }
    },
  },
  created() {
    this.initializationData();
  },
};
</script>

<template>
  <div
    :style="{
      width: '85vw',
      height: '85vh',
      backgroundImage: `url(${require('@/assets/readTitleBackground.jpg')})`,
      backgroundRepeat: 'no-repeat',
      backgroundPosition: 'center center',
      backgroundSize: '100% 100%',
    }"
  >
    <el-button
      @click="
        judgeDrawer = false;
        ifJudge = false;
        ifBlank = false;
        blankDrawer = false;
        chooserDawer = true;
        ifChoose = true;
      "
      type="primary"
      style="margin-left: 16px"
    >
      录入选择题
    </el-button>
    <el-button
      @click="
        chooserDawer = false;
        ifChoose = false;
        ifBlank = false;
        blankDrawer = false;
        judgeDrawer = true;
        ifJudge = true;
      "
      type="primary"
      style="margin-left: 16px"
    >
      录入多选题
    </el-button>
    <el-button
      @click="
        chooserDawer = false;
        ifChoose = false;
        judgeDrawer = false;
        ifJudge = false;
        ifBlank = true;
        blankDrawer = true;
      "
      type="primary"
      style="margin-left: 16px"
    >
      录入填空题
    </el-button>
    <el-drawer
      title="选择题录入"
      :visible.sync="chooserDawer"
      :with-header="false"
    >
      <h1>选择题录入</h1>
      <el-form ref="form" :model="title" label-width="auto" :rules="rules">
        <el-form-item label="题目名称">
          <el-input v-model="title.content"></el-input>
        </el-form-item>
        <el-form-item label="题目章节">
          <el-select v-model="title.chapterId" filterable placeholder="请选择">
            <el-option
              v-for="item in chapters"
              :key="item.chapterId"
              :label="item.chapterName"
              :value="item.chapterId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="题目难度">
          <el-select v-model="title.difficulty" placeholder="请选择">
            <el-option label="简单" value="easy"></el-option>
            <el-option label="普通" value="medium"></el-option>
            <el-option label="困难" value="hard"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="titleChoose" label-width="0">
          <el-form-item label="A" prop="aOption">
            <el-input
              class="requiredOptions"
              v-model="Chooses[0].Content"
              placeholder="A选项"
            ></el-input>
          </el-form-item>
          <el-form-item prop="bOption" label="B">
            <el-input
              v-model="Chooses[1].Content"
              placeholder="B选项"
              class="requiredOptions"
            ></el-input>
          </el-form-item>
          <el-form-item label="C">
            <el-input
              v-on:blur="updateChooses(Chooses[2])"
              v-model="Chooses[2].Content"
              placeholder="C选项，输入无代表没有该选项"
              class="requiredOptions"
            ></el-input>
          </el-form-item>
          <el-form-item label="D">
            <el-input
              v-on:blur="updateChooses(Chooses[3])"
              v-model="Chooses[3].Content"
              placeholder="D选项，输入无代表没有该选项"
            ></el-input>
          </el-form-item>
          <el-form-item prop="answer" label-width="70px">
            <el-select
              v-model="title.answer"
              placeholder="请选择正确答案"
              @focus="choiceChoose()"
            >
              <el-option
                v-for="(item, index) in choiceChooses"
                :key="index"
                :label="item.ChooseAbbreviation"
                :value="item.ChooseAbbreviation"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label-width="70px">
            <el-input
              v-model="title.explanation"
              placeholder="请输入题目解析"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="分值"
            v-model="title.baseScore"
            label-width="70px"
          >
            <el-input-number
              v-model="title.baseScore"
              :min="1"
              :max="10"
              label="题目分值"
            ></el-input-number>
          </el-form-item>
          <el-form-item style="margin: auto; text-align: center">
            <el-button
              @click="submitForm()"
              type="primary"
              style="margin-right: 20px"
              >确定</el-button
            >
            <el-button @click="resetForm()">重置</el-button>
          </el-form-item>
        </el-form-item>
      </el-form>
    </el-drawer>
    <el-drawer
      title="多选题录入"
      :visible.sync="judgeDrawer"
      :with-header="false"
    >
      <h1>多选题录入</h1>
      <el-form ref="form" :model="title" label-width="auto" :rules="rules">
        <el-form-item label="题目名称">
          <el-input v-model="title.content"></el-input>
        </el-form-item>
        <el-form-item label="题目章节">
          <el-select v-model="title.chapterId" filterable placeholder="请选择">
            <el-option
              v-for="item in chapters"
              :key="item.chapterId"
              :label="item.chapterName"
              :value="item.chapterId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="题目难度">
          <el-select v-model="title.difficulty" placeholder="请选择">
            <el-option label="简单" value="easy"></el-option>
            <el-option label="普通" value="medium"></el-option>
            <el-option label="困难" value="hard"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="titleChoose" label-width="0">
          <el-form-item label="A" prop="aOption">
            <el-input
              class="requiredOptions"
              v-model="Chooses[0].Content"
              placeholder="A选项"
            ></el-input>
          </el-form-item>
          <el-form-item prop="bOption" label="B">
            <el-input
              v-model="Chooses[1].Content"
              placeholder="B选项"
              class="requiredOptions"
            ></el-input>
          </el-form-item>
          <el-form-item label="C" prop="cOption">
            <el-input
              v-on:blur="updateChooses(Chooses[2])"
              v-model="Chooses[2].Content"
              placeholder="C选项"
              class="requiredOptions"
            ></el-input>
          </el-form-item>
          <el-form-item label="D" prop="dOption">
            <el-input
              v-on:blur="updateChooses(Chooses[3])"
              v-model="Chooses[3].Content"
              placeholder="D选项"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="答案选择"
            prop="answer"
            label-width="80px"
            @click="choiceChoose()"
          >
            <el-checkbox-group
              v-model="judgeTitleAnswer"
              style="margin-bottom: 20px"
            >
              <el-checkbox-button
                v-for="item in judgeChoiceChoose"
                :label="item.ChooseAbbreviation"
                :key="item.ChooseAbbreviation"
                >{{ item.ChooseAbbreviation }}</el-checkbox-button
              >
            </el-checkbox-group>
          </el-form-item>
          <el-form-item label-width="70px">
            <el-input
              v-model="title.explanation"
              placeholder="请输入题目解析"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="分值"
            v-model="title.baseScore"
            label-width="70px"
          >
            <el-input-number
              v-model="title.baseScore"
              :min="1"
              :max="10"
              label="题目分值"
            ></el-input-number>
          </el-form-item>
          <el-form-item style="margin: auto; text-align: center">
            <el-button
              @click="submitForm()"
              type="primary"
              style="margin-right: 20px"
              >确定</el-button
            >
            <el-button @click="resetForm()">重置</el-button>
          </el-form-item>
        </el-form-item>
      </el-form>
    </el-drawer>
    <el-drawer
      title="填空录入"
      :visible.sync="blankDrawer"
      :with-header="false"
    >
      <h1>填空题录入</h1>
      <el-form ref="form" :model="title" label-width="auto" :rules="rules">
        <el-form-item label="题目名称">
          <el-input type="textarea" autosize v-model="title.content"></el-input>
        </el-form-item>
        <el-form-item label="题目章节">
          <el-select v-model="title.chapterId" filterable placeholder="请选择">
            <el-option
              v-for="item in chapters"
              :key="item.chapterId"
              :label="item.chapterName"
              :value="item.chapterId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label-width="0">
          <el-form-item label="填空题答案">
            <el-input
              type="textarea"
              autosize
              v-model="title.answer"
              placeholder="请输入题目答案"
            ></el-input>
          </el-form-item>
        </el-form-item>
        <el-form-item label="题目难度">
          <el-select v-model="title.difficulty" placeholder="请选择">
            <el-option label="简单" value="easy"></el-option>
            <el-option label="普通" value="medium"></el-option>
            <el-option label="困难" value="hard"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label-width="70px">
          <el-input
            v-model="title.explanation"
            placeholder="请输入题目解析"
          ></el-input>
        </el-form-item>
        <el-form-item label="分值" v-model="title.baseScore" label-width="70px">
          <el-input-number
            v-model="title.baseScore"
            :min="1"
            :max="10"
            label="题目分值"
          ></el-input-number>
        </el-form-item>
        <el-form-item style="margin: auto; text-align: center">
          <el-button
            @click="submitForm()"
            type="primary"
            style="margin-right: 20px"
            >确定</el-button
          >
          <el-button @click="resetForm()">重置</el-button>
        </el-form-item>
      </el-form>
    </el-drawer>
  </div>
</template>

<style>
.titleChoose input {
  margin-bottom: 20px;
}
.titleChoose > div:nth-child(1) {
  margin-left: 0;
}
.titleChoose {
  margin-left: 0;
}
.requiredOptions + div {
  top: 60%;
}
</style>
