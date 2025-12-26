<script>
import { getWrongAnswersByUserIdAndExamId } from "@/api/WrongAnswers";
import { getQuestionsByQuestionId } from "@/api/TitleApi";

export default {
  data() {
    return {
      userId: 0,
      examHistory: {},
      wrongAnswers: [],
      questions: [],
      questionIndex: 0,
    };
  },
  methods: {
    answerDecrypt(encryptedAnswer) {
      const CryptoJS = require("crypto-js");
      const key = CryptoJS.enc.Utf8.parse("MySecretKey12345");
      const decrypted = CryptoJS.AES.decrypt(encryptedAnswer, key, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7,
      });
      return decrypted.toString(CryptoJS.enc.Utf8);
    },
    async initializationData() {
      this.userId = JSON.parse(window.sessionStorage.getItem("user")).userId;
      this.examHistory = JSON.parse(this.$route.query.examHistory);
      await getWrongAnswersByUserIdAndExamId(
        this.userId,
        this.examHistory.examId
      )
        .then((res) => {
          this.wrongAnswers = res.data;
        })
        .catch((err) => {
          console.error("服务器出错在获取wrongAnswers时发生错误", err);
        });
      for (let i = 0; i < this.wrongAnswers.length; i++) {
        await getQuestionsByQuestionId(this.wrongAnswers[i].questionId)
          .then((res) => {
            res.data.answer = this.answerDecrypt(res.data.answer);
            if (typeof res.data.options === "string") {
              try {
                res.data.options = JSON.parse(res.data.options);
              } catch (e) {
                console.warn("选项解析失败，使用原始值:", res.data.options);
                res.data.options = [res.data.options];
              }
            }
            this.questions.push(res.data);
          })
          .catch((err) => {
            console.error("服务器出错在获取questions时发生错误", err);
          });
      }
    },
    getWrongAnswerClass(questionId, optionIndex) {
      const wrongRecord = this.wrongAnswers.find(
        (wa) => wa.questionId === questionId
      );
      if (wrongRecord) {
        const optionLetter = String.fromCharCode(65 + optionIndex);
        if (optionLetter === wrongRecord.wrongAnswer) {
          return "wrongAnswer";
        }
        if (optionLetter === wrongRecord.correctAnswer) {
          return "correctAnswer";
        }
      }
      return "";
    },
    goToNextQuestion() {
      if (this.questionIndex < this.questions.length - 1) {
        this.questionIndex++;
      }
      console.log(this.questions[this.questionIndex].answer);
    },
    goToPrevQuestion() {
      if (this.questionIndex > 0) {
        this.questionIndex--;
      }
    },
    goShowWrongQuestions() {
      this.$router.push({
        path: "/home/student/ShowWrongQuestionsView",
      });
    },
  },
  async created() {
    await this.initializationData();
  },
};
</script>

<template>
  <div class="view">
    <div class="head">
      <div>在线考试系统</div>
      <div>错题集查看</div>
    </div>
    <div class="main" v-if="questions.length > 0 && questions[questionIndex]">
      <div class="questions">
        <div class="questionsMain">
          <div class="questionsTitle">
            {{ questionIndex + 1 }}.{{ questions[questionIndex].content }}({{
              questions[questionIndex].baseScore
            }}分)
          </div>
          <div class="questionsAnswer">
            <div
              class="choiceQuestion"
              v-if="questions[questionIndex].type === 'single_choice'"
            >
              <el-input
                :class="{
                  questionsOptions: true,
                  wrongAnswer:
                    getWrongAnswerClass(
                      questions[questionIndex].questionId,
                      index
                    ) === 'wrongAnswer',
                  correctAnswer:
                    getWrongAnswerClass(
                      questions[questionIndex].questionId,
                      index
                    ) === 'correctAnswer',
                }"
                v-for="(item, index) in questions[questionIndex].options"
                :value="item"
                :key="index"
              >
              </el-input>
            </div>
            <div
              class="blankQuestion"
              v-if="questions[questionIndex].type === 'fill_blank'"
            >
              <el-input
                class="questionsBlank"
                :disabled="true"
                :value="questions[questionIndex].answer"
              ></el-input>
            </div>
            <div
              class="multipleQuestion"
              v-if="questions[questionIndex].type === 'multiple_choice'"
            >
              <el-input
                v-for="(item, index) in questions[questionIndex].options"
                :key="index"
                >{{ item }}</el-input
              >
            </div>
          </div>
        </div>
        <div class="questionsBottom">
          <el-button @click="goToPrevQuestion" :disabled="questionIndex <= 0"
            >上一题</el-button
          >
          <el-button
            @click="goToNextQuestion"
            :disabled="questionIndex >= questions.length - 1"
            >下一题</el-button
          >
        </div>
      </div>
      <div class="analysis">
        <div class="analysisTitle">讲师分析</div>
        <div class="analysisMain">
          {{ questions[questionIndex].explanation }}
        </div>
        <div class="analysisBottom">
          <el-button
            type="primary"
            style="float: right"
            @click="goShowWrongQuestions"
          >
            返回
          </el-button>
        </div>
      </div>
    </div>
    <div v-else class="loading">加载中...</div>
  </div>
</template>

<style>
* {
  margin: 0;
  padding: 0;
}
.view {
  height: 100vh;
  background-color: #f5f5f5;
}
.head {
  background-color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  text-align: center;
  border-bottom: 1px solid grey;
  padding: 20px 0;
}
.head div {
  font-size: 26px;
  margin: auto;
  width: 55vw;
  font-weight: bold;
}
.main {
  height: 60vh;
  width: 70vw;
  background-color: white;
  margin: 3vh auto auto;
  border-radius: 20px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  display: flex;
}
.questions {
  width: 70%;
  height: 80%;
}
.analysis {
  width: 30%;
  height: 100%;
  border-left: 1px dashed grey;
}
.questionsMain {
  margin-left: 40px;
}
.questionsTitle {
  font-size: 24px;
  margin-top: 30px;
  margin-bottom: 40px;
}
.questionsOptions {
  font-size: 24px;
  width: 85%;
  margin-bottom: 30px;
  border-radius: 10px;
  overflow: hidden;
}
.questionsBlank {
  font-size: 24px;
  width: 85%;
  margin-bottom: 30px;
  border-radius: 10px;
  overflow: hidden;
}
.questionsBlank input {
  color: rgba(255, 45, 0, 0.69) !important;
}

.questionsOptions input {
  height: 50px;
  color: #000000;
}
.wrongAnswer input {
  background-color: rgba(255, 74, 74, 0.69) !important;
}
.correctAnswer input {
  background-color: #00f400 !important;
}
.analysisTitle {
  font-size: 24px;
  text-align: center;
  margin-top: 20px;
  padding-bottom: 20px;
  border-bottom: 1px dashed grey;
}
.analysisMain {
  font-size: 20px;
  height: 65%;
  width: 90%;
  border-radius: 10px;
  margin: 10px auto;
}
.questionsBottom {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
}
</style>
