<script>
import { getAllQuestionByPaperId } from "@/api/TitleApi";
import { addStudentAnswer, checkAnswerRecord } from "@/api/StudentAnswers";
import { addWrongAnswers } from "@/api/WrongAnswers";
import { addScores } from "@/api/Scores";

export default {
  data() {
    return {
      showExamNotice: false,
      showQuestions: false,
      showResult: false,
      isAutoSubmit: false,
      checkAnswerRecordResult: false,
      paperId: "",
      inTotalTime: "",
      startTime: Date.now(),
      displayTime: "00:00:00",
      timer: null,
      testPaperDescription: "",
      questions: [{}],
      questionIndex: 0,
      allQuestionsBlankIndex: [],
      allQuestionsSingleIndex: [],
      allQuestionsMultipleIndex: [],
      selectedOption: "",
      selectedOptions: [],
      fraction: 0,
      answers: [],
      questionAnswers: {
        single: {},
        multiple: {},
        blank: {},
      },
      examAnswers: [
        {
          questionIndex: "",
          questionAnswer: "",
        },
      ],
      answeredQuestionsAndAnswers: [
        {
          questionIndex: "",
          questionAnswer: "",
        },
      ],
      answeredQuestions: new Set(),
      currentQuestionIndex: 0,
      studentAnswers: {
        recordId: 0,
        examId: 0,
        studentId: 0,
        questionsId: [],
        answers: [],
        isCorrect: [],
        isReviewed: [],
        score: [],
        reviewedBy: [],
        reviewNotes: [],
        createdAt: new Date(),
      },
      wrongQuestion: {
        wrongId: 0,
        studentId: 0,
        questionsId: [],
        examID: 0,
        wrongAnswers: [],
        correctAnswers: [],
        createdAt: new Date(),
      },
      scores: {
        examId: 0,
        studentId: 0,
        totalScore: 0,
        objectiveScore: 0,
        subjectiveScore: 0,
        ranking: 0,
        evaluatedAt: new Date(),
      },
    };
  },
  methods: {
    initializationData() {
      this.paperId = this.$route.query.testPaperId;
      this.inTotalTime = this.$route.query.durationMin;
      this.testPaperDescription = this.$route.query.testPaperDescription;
      this.displayTime = this.formattedInitialTime || "00:00:00";
      getAllQuestionByPaperId(this.paperId)
        .then((res) => {
          this.questions = res.data;
          for (let i = 0; i < this.questions.length; i++) {
            this.answers.push({
              questionIndex: i,
              questionAnswer: this.answerDecrypt(this.questions[i].answer),
            });
          }
          this.questions.forEach((question) => {
            if (question.options && typeof question.options === "string") {
              try {
                question.options = JSON.parse(
                  question.options.replace(/\\"/g, '"')
                );
              } catch (e) {
                console.error("选项解析失败:", e);
              }
            }
            for (let i = 0; i < this.questions.length; i++) {
              if (this.questions[i].type === "single_choice") {
                this.allQuestionsSingleIndex.push(i);
              } else if (this.questions[i].type === "multiple_choice") {
                this.allQuestionsMultipleIndex.push(i);
              } else if (this.questions[i].type === "fill_blank") {
                this.allQuestionsBlankIndex.push(i);
              }
            }
          });
          this.showExamNotice = true;
        })
        .catch((err) => {
          this.$message.error(err.message);
        });
    },
    checkAnswerRecord() {
      let studentId = JSON.parse(window.sessionStorage.getItem("user")).userId;
      checkAnswerRecord(studentId, this.$route.query.examId)
        .then((res) => {
          this.checkAnswerRecordResult = !res.data;
        })
        .catch((err) => {
          console.error("服务器出错", err);
        });
    },

    answerDecrypt(encryptedAnswer) {
      const CryptoJS = require("crypto-js");
      const key = CryptoJS.enc.Utf8.parse("MySecretKey12345");
      const decrypted = CryptoJS.AES.decrypt(encryptedAnswer, key, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7,
      });
      return decrypted.toString(CryptoJS.enc.Utf8);
    },
    startPrompt() {
      this.$confirm("是否开始", "考试信息", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.startTimer();
        })
        .catch(() => {
          this.$message.info("已取消！");
        });
    },
    startTimer() {
      this.showExamNotice = false;
      this.showQuestions = true;
      this.timer = setInterval(() => {
        const totalSeconds = this.inTotalTime * 60;
        const elapsedSeconds = Math.floor((Date.now() - this.startTime) / 1000);
        const remainingSeconds = totalSeconds - elapsedSeconds;
        if (remainingSeconds <= 0) {
          this.displayTime = "00:00:00";
          this.isAutoSubmit = true;
          this.stopTimer();
        } else {
          const hours = Math.floor(remainingSeconds / 3600);
          const minutes = Math.floor((remainingSeconds % 3600) / 60);
          const seconds = remainingSeconds % 60;
          this.displayTime = `${hours.toString().padStart(2, "0")}:${minutes
            .toString()
            .padStart(2, "0")}:${seconds.toString().padStart(2, "0")}`;
        }
      }, 1000);
    },
    // startTimer() {
    //   this.showExamNotice = false;
    //   this.showQuestions = true;
    //   let fakeElapsedSeconds = 0;
    //   const speedMultiplier = 80;
    //   this.timer = setInterval(() => {
    //     fakeElapsedSeconds += speedMultiplier;
    //     const totalSeconds = this.inTotalTime * 60;
    //     const remainingSeconds = totalSeconds - fakeElapsedSeconds;
    //     if (remainingSeconds <= 0) {
    //       this.displayTime = "00:00:00";
    //       this.stopTimer();
    //     } else {
    //       const hours = Math.floor(remainingSeconds / 3600);
    //       const minutes = Math.floor((remainingSeconds % 3600) / 60);
    //       const seconds = remainingSeconds % 60;
    //       this.displayTime = `${hours.toString().padStart(2, "0")}:${minutes
    //         .toString()
    //         .padStart(2, "0")}:${seconds.toString().padStart(2, "0")}`;
    //     }
    //   }, 100);
    // },
    stopTimer() {
      if (this.timer) {
        clearInterval(this.timer);
        this.timer = null;
        if (this.isAutoSubmit) {
          this.$alert("时间已到，自动提交！！", "提示", {
            confirmButtonText: "确定",
            type: "warning",
          });
        }
        this.submitPaper();
      }
    },
    jumpToQuestion(index) {
      this.saveCurrentAnswer();
      this.questionIndex = index;
      this.selectedOption = "";
      this.selectedOptions = [];
      const currentQuestion = this.questions[this.questionIndex];
      if (currentQuestion) {
        if (currentQuestion.type === "single_choice") {
          this.selectedOption = this.questionAnswers.single[index] || "";
        } else if (currentQuestion.type === "multiple_choice") {
          this.selectedOptions = this.questionAnswers.multiple[index] || [];
        } else if (currentQuestion.type === "fill_blank") {
          this.selectedOption = this.questionAnswers.blank[index] || "";
        }
        if (
          currentQuestion.type === "single_choice" &&
          this.questionAnswers.single[index]
        ) {
          this.answeredQuestions.add(index);
        } else if (
          currentQuestion.type === "multiple_choice" &&
          this.questionAnswers.multiple[index] &&
          this.questionAnswers.multiple[index].length > 0
        ) {
          this.answeredQuestions.add(index);
        } else if (
          currentQuestion.type === "fill_blank" &&
          this.questionAnswers.blank[index]
        ) {
          this.answeredQuestions.add(index);
        }
      }
    },
    saveCurrentAnswer() {
      const currentIndex = this.questionIndex;
      const currentQuestion = this.questions[currentIndex];
      if (currentQuestion) {
        if (currentQuestion.type === "single_choice") {
          if (this.selectedOption) {
            this.questionAnswers.single[currentIndex] = this.selectedOption;
          } else {
            delete this.questionAnswers.single[currentIndex];
          }
        } else if (currentQuestion.type === "multiple_choice") {
          if (this.selectedOptions.length > 0) {
            this.questionAnswers.multiple[currentIndex] = [
              ...this.selectedOptions,
            ];
          } else {
            delete this.questionAnswers.multiple[currentIndex];
          }
        } else if (currentQuestion.type === "blank") {
          if (this.selectedOption) {
            this.questionAnswers.blank[currentIndex] = this.selectedOption;
          } else {
            delete this.questionAnswers.blank[currentIndex];
          }
        }
      }
    },
    loadCurrentAnswer() {
      const currentIndex = this.questionIndex;
      const currentQuestion = this.questions[currentIndex];
      if (currentQuestion) {
        if (currentQuestion.type === "single_choice") {
          this.selectedOption = this.questionAnswers.single[currentIndex] || "";
          this.selectedOptions = [];
        } else if (currentQuestion.type === "multiple_choice") {
          this.selectedOptions =
            this.questionAnswers.multiple[currentIndex] || [];
          this.selectedOption = "";
        } else if (currentQuestion.type === "blank") {
          this.selectedOption = this.questionAnswers.blank[currentIndex] || "";
          this.selectedOptions = [];
        }
      }
    },
    isPartiallyAnswered(index) {
      const question = this.questions[index];
      if (!question) return false;
      if (question.type === "multiple_choice") {
        const savedAnswers = this.questionAnswers.multiple[index];
        if (savedAnswers && savedAnswers.length === 1) {
          return true;
        }
        if (index === this.questionIndex) {
          return this.selectedOptions.length === 1;
        }
      }
      return false;
    },
    onFillBlankBlur() {
      const currentIndex = this.questionIndex;
      const currentQuestion = this.questions[currentIndex];
      if (currentQuestion && currentQuestion.type === "fill_blank") {
        if (this.selectedOption) {
          this.questionAnswers.blank[currentIndex] = this.selectedOption;
          this.answeredQuestions.add(currentIndex);
        } else {
          delete this.questionAnswers.blank[currentIndex];
          this.answeredQuestions.delete(currentIndex);
        }
      }
    },
    escHome() {
      let item = JSON.parse(window.sessionStorage.getItem("user"));
      if (item.role === "student") {
        this.$router.push("/home/student");
      }
      if (item.role === "teacher") {
        this.$router.push("/home/teacher");
      }
      if (item.role === "admin") {
        this.$router.push("/home/admin");
      }
    },
    checking() {
      return true;
      // return false;
    },
    submitPaper() {
      const paperTotalScore = this.questions.reduce(
        (sum, question) => sum + (question.baseScore || 0),
        0
      );
      this.isAutoSubmit = false;
      let blankAnswers = this.questionAnswers.blank;
      let singleAnswers = this.questionAnswers.single;
      let multipleAnswers = this.questionAnswers.multiple;
      let objectiveScore = 0;
      let subjectiveScore = 0;
      this.wrongQuestions = {
        wrongId: 0,
        studentId: JSON.parse(window.sessionStorage.getItem("user")).userId,
        questionsId: [],
        examID: this.$route.query.examId,
        wrongAnswers: [],
        correctAnswers: [],
        createdAt: new Date(),
      };
      for (let blankAnswersKey in blankAnswers) {
        blankAnswers[blankAnswersKey] = blankAnswers[blankAnswersKey].trim();
      }
      for (let index of this.allQuestionsBlankIndex) {
        if (!(index in blankAnswers)) {
          blankAnswers[index] = null;
        }
      }
      for (let singleAnswersKey in singleAnswers) {
        if (singleAnswers[singleAnswersKey]) {
          const match = singleAnswers[singleAnswersKey].match(/^([A-Z])\./);
          if (match) {
            singleAnswers[singleAnswersKey] = match[1];
          } else {
            singleAnswers[singleAnswersKey] =
              singleAnswers[singleAnswersKey].trim();
          }
        }
      }
      for (let index of this.allQuestionsSingleIndex) {
        if (!(index in singleAnswers)) {
          singleAnswers[index] = null;
        }
      }
      for (let index in multipleAnswers) {
        if (multipleAnswers[index]) {
          const processedOptions = multipleAnswers[index].map((option) => {
            const match = option.match(/^([A-Z])\./);
            return match ? match[1] : option.trim();
          });
          multipleAnswers[index] = processedOptions.join(",");
        }
      }
      for (let index of this.allQuestionsMultipleIndex) {
        if (!(index in multipleAnswers)) {
          multipleAnswers[index] = null;
        }
      }
      const tempAnswerQuestions = [];
      for (let i = 0; i < this.questions.length; i++) {
        const question = this.questions[i];
        let answer = null;

        if (question.type === "single_choice") {
          answer = this.questionAnswers.single[i] || null;
        } else if (question.type === "multiple_choice") {
          answer = this.questionAnswers.multiple[i] || null;
        } else if (question.type === "fill_blank") {
          answer = this.questionAnswers.blank[i] || null;
        }
        tempAnswerQuestions.push({
          questionIndex: i,
          questionAnswer: answer,
        });
      }
      let totalScore = 0;
      for (let i = 0; i < tempAnswerQuestions.length; i++) {
        const userAnswer = tempAnswerQuestions[i];
        const correctAnswer = this.answers[i];
        const question = this.questions[i];
        let isCorrect = 0;
        let isPartial = false;
        if (userAnswer && correctAnswer) {
          if (question.type === "multiple_choice") {
            if (
              this.isAnswerCorrect(
                userAnswer.questionAnswer,
                correctAnswer.questionAnswer
              )
            ) {
              totalScore += question.baseScore;
              isCorrect = 1;
            } else if (
              this.isPartialAnswerCorrect(
                userAnswer.questionAnswer,
                correctAnswer.questionAnswer
              )
            ) {
              totalScore += question.baseScore * 0.3;
              isCorrect = 1;
            }
          } else if (
            this.isAnswerCorrect(
              userAnswer.questionAnswer,
              correctAnswer.questionAnswer
            )
          ) {
            totalScore += question.baseScore;
            isCorrect = 1;
          }
          let questionScore = 0;
          if (question.type === "multiple_choice") {
            if (
              this.isAnswerCorrect(
                userAnswer.questionAnswer,
                correctAnswer.questionAnswer
              )
            ) {
              questionScore = question.baseScore;
            } else if (
              this.isPartialAnswerCorrect(
                userAnswer.questionAnswer,
                correctAnswer.questionAnswer
              )
            ) {
              questionScore = question.baseScore * 0.3;
            }
          } else if (
            this.isAnswerCorrect(
              userAnswer.questionAnswer,
              correctAnswer.questionAnswer
            )
          ) {
            questionScore = question.baseScore;
          }
          if (
            question.type === "single_choice" ||
            question.type === "multiple_choice"
          ) {
            objectiveScore += questionScore;
          } else if (question.type === "fill_blank") {
            subjectiveScore += questionScore;
          }

          this.scores.objectiveScore = objectiveScore;
          this.scores.subjectiveScore = subjectiveScore;
        }
        this.studentAnswers.isCorrect.push(isCorrect);
        this.studentAnswers.answers.push(userAnswer.questionAnswer);
        this.studentAnswers.score.push(
          isCorrect === 1
            ? question.type === "multiple_choice" &&
              this.isPartialAnswerCorrect(
                userAnswer.questionAnswer,
                correctAnswer.questionAnswer
              )
              ? question.baseScore * 0.3
              : question.baseScore
            : 0
        );
        if (isCorrect === 0 || isPartial) {
          this.wrongQuestions.questionsId.push(question.questionId);
          this.wrongQuestions.wrongAnswers.push(userAnswer.questionAnswer);
          this.wrongQuestions.correctAnswers.push(correctAnswer.questionAnswer);
        }
      }
      this.fraction = totalScore;
      this.showResult = true;
      this.showQuestions = false;
      this.studentAnswers.examId = this.$route.query.examId;
      this.studentAnswers.studentId = JSON.parse(
        window.sessionStorage.getItem("user")
      ).userId;
      for (let i = 0; i < this.questions.length; i++) {
        this.studentAnswers.questionsId.push(this.questions[i].questionId);
      }
      this.studentAnswers.isReviewed = 1;
      this.studentAnswers.reviewedBy = 25;
      this.studentAnswers.reviewNotes = "";
      if (this.checking()) {
        addStudentAnswer(this.studentAnswers).then((res) => {
          this.$message.success(res.data);
          this.timer = null;
          this.displayTime = "00:00:00";
        });
        this.wrongQuestions.examId = this.$route.query.examId;
        addWrongAnswers(this.wrongQuestions).then((res) => {
          this.$message.success(res.data);
        });
      }
      for (let i = 0; i < tempAnswerQuestions.length; i++) {
        const userAnswer = tempAnswerQuestions[i];
        const correctAnswer = this.answers[i];
        const question = this.questions[i];
        let isCorrect = 0;
        let isPartial = false;
        if (userAnswer && correctAnswer) {
          if (question.type === "multiple_choice") {
            if (
              this.isAnswerCorrect(
                userAnswer.questionAnswer,
                correctAnswer.questionAnswer
              )
            ) {
              totalScore += question.baseScore;
              isCorrect = 1;
            } else if (
              this.isPartialAnswerCorrect(
                userAnswer.questionAnswer,
                correctAnswer.questionAnswer
              )
            ) {
              totalScore += question.baseScore * 0.3;
              isCorrect = 1;
              // eslint-disable-next-line no-unused-vars
              isPartial = true;
            }
          } else if (
            this.isAnswerCorrect(
              userAnswer.questionAnswer,
              correctAnswer.questionAnswer
            )
          ) {
            totalScore += question.baseScore;
            isCorrect = 1;
          }
        }
        if (this.scores.examId !== this.$route.query.examId) {
          this.scores.examId = this.$route.query.examId;
          this.scores.ranking = 0;
          this.scores.totalScore = paperTotalScore;
          this.scores.studentId = JSON.parse(
            window.sessionStorage.getItem("user")
          ).userId;
          addScores(this.scores).catch(() => {
            this.$message.error("服务器出错，请检查！");
          });
        }
        this.studentAnswers.isCorrect.push(isCorrect);
        this.studentAnswers.answers.push(userAnswer.questionAnswer);
        this.studentAnswers.score.push(
          isCorrect === 1
            ? question.type === "multiple_choice" &&
              this.isPartialAnswerCorrect(
                userAnswer.questionAnswer,
                correctAnswer.questionAnswer
              )
              ? question.baseScore * 0.3
              : question.baseScore
            : 0
        );
      }
    },
    isAnswerCorrect(userAnswer, correctAnswer) {
      if (Array.isArray(userAnswer) && Array.isArray(correctAnswer)) {
        if (userAnswer.length !== correctAnswer.length) {
          return false;
        }
        const sortedUserAnswer = [...userAnswer].sort();
        const sortedCorrectAnswer = [...correctAnswer].sort();
        return (
          JSON.stringify(sortedUserAnswer) ===
          JSON.stringify(sortedCorrectAnswer)
        );
      }
      return userAnswer === correctAnswer;
    },
    isPartialAnswerCorrect(userAnswer, correctAnswer) {
      if (typeof userAnswer === "string" && typeof correctAnswer === "string") {
        const userOptions = userAnswer
          .split(",")
          .filter((opt) => opt.trim() !== "");
        const correctOptions = correctAnswer
          .split(",")
          .filter((opt) => opt.trim() !== "");
        if (
          userOptions.length > 0 &&
          userOptions.length < correctOptions.length
        ) {
          return userOptions.every((option) => correctOptions.includes(option));
        }
      }
      return false;
    },
  },
  watch: {
    selectedOption(newVal) {
      const currentIndex = this.questionIndex;
      const currentQuestion = this.questions[currentIndex];
      if (!currentQuestion) return;
      if (currentQuestion.type === "single_choice") {
        if (newVal) {
          this.questionAnswers.single[currentIndex] = newVal;
        } else {
          delete this.questionAnswers.single[currentIndex];
        }
        if (newVal) {
          this.answeredQuestions.add(currentIndex);
        } else {
          this.answeredQuestions.delete(currentIndex);
        }
      } else if (currentQuestion.type === "blank") {
        if (newVal) {
          this.questionAnswers.blank[currentIndex] = newVal;
          this.answeredQuestions.add(currentIndex);
        } else {
          delete this.questionAnswers.blank[currentIndex];
          this.answeredQuestions.delete(currentIndex);
        }
      }
    },
    selectedOptions: {
      handler(newVal) {
        const currentIndex = this.questionIndex;
        if (newVal && newVal.length > 0) {
          this.questionAnswers.multiple[currentIndex] = [...newVal];
        } else {
          delete this.questionAnswers.multiple[currentIndex];
        }
        if (newVal && newVal.length > 0) {
          this.answeredQuestions.add(currentIndex);
        } else if (this.answeredQuestions.has(currentIndex)) {
          this.answeredQuestions.delete(currentIndex);
        }
      },
      deep: true,
    },
  },
  computed: {
    formattedInitialTime() {
      const totalSeconds = this.inTotalTime * 60;
      const hours = Math.floor(totalSeconds / 3600);
      const minutes = Math.floor((totalSeconds % 3600) / 60);
      const seconds = totalSeconds % 60;
      return `${hours.toString().padStart(2, "0")}:${minutes
        .toString()
        .padStart(2, "0")}:${seconds.toString().padStart(2, "0")}`;
    },
    formattedOptions() {
      if (
        !this.questions[this.questionIndex] ||
        !this.questions[this.questionIndex].options
      ) {
        return [];
      }
      return this.questions[this.questionIndex].options.map((option) => {
        if (
          typeof option === "string" &&
          option.startsWith('"') &&
          option.endsWith('"')
        ) {
          option = option.substring(1, option.length - 1);
        }
        return option;
      });
    },
    allQuestionsAnswered() {
      for (let i = 0; i < this.questions.length; i++) {
        const question = this.questions[i];
        if (question.type === "single_choice") {
          if (!this.questionAnswers.single[i]) {
            return false;
          }
        } else if (question.type === "multiple_choice") {
          const answers = this.questionAnswers.multiple[i];
          if (!answers || answers.length === 0) {
            return false;
          }
        } else if (question.type === "blank") {
          if (!this.questionAnswers.blank[i]) {
            return false;
          }
        }
      }
      return true;
    },
    isQuestionAnswered() {
      return (index) => {
        const question = this.questions[index];
        if (!question) return false;

        if (question.type === "single_choice") {
          return !!this.questionAnswers.single[index];
        } else if (question.type === "multiple_choice") {
          const answers = this.questionAnswers.multiple[index];
          return answers && answers.length > 0;
        } else if (question.type === "fill_blank") {
          return !!this.questionAnswers.blank[index];
        }
        return false;
      };
    },
    totalScore() {
      return this.questions.reduce((total, question) => {
        return total + (question.baseScore || 0);
      }, 0);
    },
    isPass() {
      const passingScore = this.totalScore * 0.6;
      return this.fraction >= passingScore;
    },
  },
  created() {
    this.checkAnswerRecord();
    this.initializationData();
    this.questionAnswers = {
      single: {},
      multiple: {},
      blank: {},
    };
  },
  beforeDestroy() {
    this.stopTimer();
  },
};
</script>

<template>
  <div class="view">
    <div class="head">
      <div>在线考试系统</div>
      <div>
        <i class="el-icon-time" style="margin-right: 5px"></i>{{ displayTime }}
      </div>
    </div>
    <div v-if="showExamNotice" class="examNoticeMain" style="margin-top: 4vh">
      <div class="mainHead">考前须知</div>
      <div class="mainBody">
        <div>1. 考试时间：{{ inTotalTime }} 分钟</div>
        <div>2. 考试须知：{{ testPaperDescription }}</div>
      </div>
      <div class="mainButton">
        <div v-if="checkAnswerRecordResult" class="already-answer-tip">
          你已答过此张试卷，不可重复作答！
        </div>
        <el-button v-else @click="startPrompt" type="primary"
          >开始考试</el-button
        >
      </div>
    </div>
    <div v-if="showQuestions" class="main-container">
      <div class="main">
        <div class="mainHead">
          {{ questionIndex + 1 + "." }}{{ questions[questionIndex].content }}({{
            questions[questionIndex].baseScore + "分"
          }})
        </div>
        <div
          class="options-container"
          v-if="
            questions[questionIndex] &&
            questions[questionIndex].type === 'fill_blank'
          "
        >
          <div
            class="option-item"
            :class="{ 'option-selected': selectedOption }"
          >
            <input
              type="text"
              v-model="selectedOption"
              @blur="onFillBlankBlur"
              placeholder="请输入答案"
              class="option-input"
              style="
                width: 100%;
                border: none;
                outline: none;
                font-size: 18px;
                height: 35px;
              "
            />
          </div>
        </div>

        <div
          class="options-container"
          v-if="
            questions[questionIndex] &&
            questions[questionIndex].type === 'single_choice'
          "
        >
          <div
            v-for="(option, index) in formattedOptions"
            :key="index"
            class="option-item"
            :class="{ 'option-selected': selectedOption === option }"
          >
            <input
              type="radio"
              :id="'option-' + index"
              :value="option"
              v-model="selectedOption"
              class="option-radio"
            />
            <label :for="'option-' + index" class="option-label">
              {{ option }}
            </label>
          </div>
        </div>
        <div
          class="options-container"
          v-if="
            questions[questionIndex] &&
            questions[questionIndex].type === 'multiple_choice'
          "
        >
          <div
            v-for="(option, index) in formattedOptions"
            :key="index"
            class="option-item"
            :class="{ 'option-selected': selectedOptions.includes(option) }"
          >
            <input
              type="checkbox"
              :id="'option-' + index"
              :value="option"
              v-model="selectedOptions"
              class="option-radio"
            />
            <label :for="'option-' + index" class="option-label">
              {{ option }}
            </label>
          </div>
        </div>
        <div class="bottomButton">
          <el-button
            @click="jumpToQuestion(questionIndex - 1)"
            type="primary"
            :disabled="this.questionIndex === 0"
            style="margin-top: 10px; margin-left: 20px; float: left"
          >
            上一题
          </el-button>
          <div style="float: right; margin-right: 20px">
            <el-button
              @click="jumpToQuestion(questionIndex + 1)"
              type="primary"
              v-if="questionIndex < questions.length - 1"
              style="margin-top: 10px; margin-left: 10px"
            >
              下一题
            </el-button>
            <el-button
              @click="submitPaper"
              type="success"
              :disabled="!allQuestionsAnswered"
              v-if="questionIndex === questions.length - 1"
              style="margin-top: 10px; margin-left: 10px"
            >
              提交答案
            </el-button>
          </div>
          <div style="clear: both"></div>
        </div>
      </div>
      <div class="mainRight">
        <div class="mainRightHead">题目导航</div>
        <div class="mainRightBody">
          <div
            v-for="(question, index) in questions"
            :key="index"
            class="nav-item"
            :class="{
              'nav-current': index === questionIndex,
              'nav-answered':
                isQuestionAnswered(index) && index !== questionIndex,
              'nav-partial': isPartiallyAnswered(index),
              'nav-unanswered':
                !isQuestionAnswered(index) &&
                !isPartiallyAnswered(index) &&
                index !== questionIndex,
            }"
            @click="jumpToQuestion(index)"
          >
            {{ index + 1 }}
          </div>
        </div>
      </div>
    </div>
    <div class="main" v-if="showResult">
      <div class="examResultContainer">
        <div class="score-circle" :class="{ pass: isPass, fail: !isPass }">
          {{ fraction.toFixed(1) }}
        </div>
        <div class="result-text">
          {{ isPass ? "恭喜您，考试通过！" : "很遗憾，考试未通过" }}
        </div>
        <div class="hint-text">如果要查看错题，可退出至主界面查看</div>
        <div>
          <el-button @click="escHome" style="margin-top: 10px" type="primary">
            返回
          </el-button>
        </div>
      </div>
    </div>
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
.head div {
  font-size: 26px;
  margin: auto;
  width: 55vw;
}
.head div:nth-child(1) {
  font-weight: bold;
}
.head div:nth-child(2) {
  font-size: 20px;
  color: red;
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
.examNoticeMain {
  width: 60vw;
  padding: 20px 0;
  background-color: white;
  margin: auto;
  border-radius: 20px;
}
.main {
  flex: 3.5;
  padding: 20px 0;
  background-color: white;
  margin: auto;
  border-radius: 20px;
}
.main-container {
  display: flex;
  gap: 20px;
  margin-top: 4vh;
  padding: 0 5vh;
  width: 55vw;
  margin: 4vh auto 0;
}
.mainRight {
  flex: 1;
  padding: 20px;
  background-color: white;
  border-radius: 20px;
  align-self: flex-start;
}
.main hr {
  border: none;
  height: 1px;
  background-color: grey;
}
.examNoticeMain .mainHead {
  font-size: 22px;
  margin-left: 5vh;
  height: 8vh;
  display: flex;
  align-items: center;
  border-bottom: 1px solid grey;
}
.main .mainHead {
  font-size: 22px;
  margin-left: 5vh;
  height: 8vh;
  display: flex;
  align-items: center;
}
.mainBody {
  font-size: 22px;
  margin-left: 5vh;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  border-bottom: 1px solid grey;
}
.mainBody div {
  margin: 10px 0;
}
.mainButton {
  display: flex;
  justify-content: flex-end;
  margin-top: 3vh;
  align-items: center;
}
.options-container {
  margin: 20px 5vh;
}

.option-item {
  display: flex;
  align-items: center;
  margin-bottom: 25px;
  padding: 10px 15px;
  border: 1px solid #dcdfe6;
  border-radius: 8px;
  background-color: white;
}

.option-radio {
  margin-right: 10px;
  transform: scale(1.3);
}

.option-label {
  flex-grow: 1;
  display: flex;
  align-items: center;
  min-height: 24px;
  font-size: 18px;
  cursor: pointer;
  margin: 0;
  padding: 0;
}

.option-item:hover {
  border-color: #409eff;
  background-color: #f5f7fa;
}
.option-item:last-child {
  margin-bottom: 20px;
}
.option-item.option-selected:hover {
  background-color: #d1e9ff;
}
.option-item.option-selected {
  background-color: #e6f7ff;
}
.mainRight {
  margin-top: 20px;
  padding: 0 5vh;
}

.mainRightHead {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
}

.mainRightBody {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.nav-item {
  width: 30px;
  height: 30px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  font-size: 14px;
  font-weight: bold;
  transition: all 0.3s ease;
}
.option-input {
  width: 100%;
  border: none;
  outline: none;
  font-size: 18px;
  background: transparent;
}

.option-item {
  display: flex;
  align-items: center;
  margin-bottom: 25px;
  padding: 10px 15px;
  border: 1px solid #dcdfe6;
  border-radius: 8px;
  background-color: white;
}

.option-item:hover {
  border-color: #409eff;
  background-color: #f5f7fa;
}

.nav-current {
  background-color: #1890ff;
  color: white;
}

.nav-answered {
  background-color: #52c41a;
  color: white;
}

.nav-unanswered {
  background-color: #d9d9d9;
  color: #666;
}

.nav-item:hover:not(.nav-current) {
  opacity: 0.8;
  transform: scale(1.05);
}
.main-container {
  display: flex;
  gap: 20px;
  margin-top: 4vh;
  padding: 0 5vh;
}

.main {
  flex: 3;
  padding: 20px 0;
  background-color: white;
  border-radius: 20px;
}

.mainRight {
  flex: 1;
  padding: 20px;
  background-color: white;
  border-radius: 20px;
  align-self: flex-start;
}
.nav-partial {
  background-color: #faad14;
  color: white;
}
.examResultContainer {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 50px 0;
}

.score-circle {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  background-color: #d9d9d9;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 48px;
  font-weight: bold;
  margin-bottom: 20px;
}

.score-circle.pass {
  color: #52c41a;
}

.score-circle.fail {
  color: #ff4d4f;
}

.result-text {
  font-size: 24px;
  margin-bottom: 10px;
}

.result-text.pass {
  color: #52c41a;
}

.result-text.fail {
  color: #ff4d4f;
}

.hint-text {
  font-size: 16px;
  color: #888;
  margin-top: 20px;
}
.already-answer-tip {
  color: #ff4d4f;
  font-size: 18px;
  font-weight: bold;
  padding: 20px;
}
</style>
