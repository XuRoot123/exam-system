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
        path: "admin/showUserInfo",
        name: "ShowUserInfo",
        component: () => import("@/views/user/admin/ShowUserInfo.vue"),
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
