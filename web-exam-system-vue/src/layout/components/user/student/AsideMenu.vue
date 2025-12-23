<template>
  <el-menu
    ref="menu"
    default-active="/"
    router
    :unique-opened="false"
    :collapse-transition="false"
  >
    <el-menu-item index="/">
      <i class="el-icon-setting"></i>
      <span slot="title">首页</span>
    </el-menu-item>
    <el-submenu index="1">
      <template slot="title">
        <i class="el-icon-location"></i>
        <span>考试</span>
      </template>
      <el-menu-item index="/home/student/ChoiceExam">
        <i class="el-icon-setting"></i>
        <span slot="title">选择考试</span>
      </el-menu-item>
      <el-menu-item index="/home/student/ShowWrongQuestionsView">
        <i class="el-icon-setting"></i>
        <span slot="title">查看错题</span>
      </el-menu-item>
      <el-menu-item index="/storage">
        <i class="el-icon-setting"></i>
        <span slot="title">查看分数</span>
      </el-menu-item>
    </el-submenu>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-location"></i>
        <span>个人</span>
      </template>
      <el-menu-item index="/home/student/ShowThisInfo">
        <i class="el-icon-setting"></i>
        <span slot="title">查看个人信息</span>
      </el-menu-item>
      <el-menu-item index="/home/system/updatePassword">
        <i class="el-icon-setting"></i>
        <span slot="title">修改密码</span>
      </el-menu-item>
    </el-submenu>
  </el-menu>
</template>
<script setup lang="ts"></script>
