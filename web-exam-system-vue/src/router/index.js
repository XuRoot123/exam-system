import Vue from "vue";
import VueRouter from "vue-router";
import LoginView from "@/views/LoginView.vue";
Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "",
    component: LoginView,
  },
  {
    path: "/login",
    name: "Login",
    component: LoginView,
  },
  {
    path: "/exam/ExamView",
    name: "Exam",
    component: () => import("@/views/exam/ExamView.vue"),
  },
  {
    path: "/exam/WrongQuestionsListView",
    name: "WrongQuestionsList",
    component: () => import("@/views/exam/WrongQuestionsListView.vue"),
  },
  {
    path: "/home",
    name: "Home",
    component: () => import("@/layout/BaseLayout.vue"),
    children: [
      {
        path: "student",
        name: "Student",
        component: () => import("@/views/user/StudentView.vue"),
      },
      {
        path: "teacher",
        name: "Teacher",
        component: () => import("@/views/user/TeacherView.vue"),
      },
      {
        path: "admin",
        name: "Admin",
        component: () => import("@/views/user/AdminView.vue"),
      },
      {
        path: "system/UpdatePassword",
        name: "UpdatePassword",
        component: () => import("@/views/user/system/UpdatePassword.vue"),
      },
      {
        path: "student/ShowWrongQuestionsView",
        name: "ShowWrongQuestionsView",
        component: () =>
          import("@/views/user/student/ShowWrongQuestionsView.vue"),
      },
      {
        path: "student/ShowHistoryScoresView",
        name: "ShowHistoryScoresView",
        component: () =>
          import("@/views/user/student/ShowHistoryScoresView.vue"),
      },
      {
        path: "student/ChoiceExam",
        name: "ChoiceExam",
        component: () => import("@/views/user/student/ChoiceExam.vue"),
      },
      {
        path: "student/ShowThisInfo",
        name: "ShowThisInfo",
        component: () => import("@/views/user/student/ShowThisInfo.vue"),
      },
      {
        path: "admin/ShowUserInfo",
        name: "ShowUserInfo",
        component: () => import("@/views/user/admin/ShowUserInfo.vue"),
      },
      {
        path: "admin/ShowExamInfo",
        name: "ShowExamInfo",
        component: () => import("@/views/user/admin/ShowExamInfo.vue"),
      },
      {
        path: "admin/ShowQuestionsInfo",
        name: "ShowQuestionsInfo",
        component: () => import("@/views/user/admin/ShowQuestionsInfo.vue"),
      },
      {
        path: "teacher/ShowThisClassStudent",
        name: "ShowThisClassStudent",
        component: () =>
          import("@/views/user/teacher/ShowThisClassStudent.vue"),
      },
      {
        path: "teacher/ShowEnterClassApplication",
        name: "ShowEnterClassApplication",
        component: () =>
          import("@/views/user/teacher/ShowEnterClassApplication.vue"),
      },
      {
        path: "teacher/ReadTitle",
        name: "ReadTitle",
        component: () => import("@/views/user/teacher/ReadTitle.vue"),
      },
      {
        path: "teacher/SystemCompilation",
        name: "SystemCompilation",
        component: () => import("@/views/user/teacher/SystemCompilation.vue"),
      },
      {
        path: "teacher/SelfCompiledVolume",
        name: "SelfCompiledVolume",
        component: () => import("@/views/user/teacher/SelfCompiledVolume.vue"),
      },
    ],
  },
  {
    path: "/enroll",
    name: "Enroll",
    component: () => import("@/views/EnrollView.vue"),
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
