<template>
  <el-menu default-active="/" router :unique-opened="false">
    <el-menu-item index="/">
      <i class="el-icon-setting"></i>
      <span slot="title">首页</span>
    </el-menu-item>
    <el-submenu index="1">
      <template slot="title">
        <i class="el-icon-location"></i>
        <span>系统管理</span>
      </template>
      <el-menu-item index="/home/admin/ShowUserInfo">
        <i class="el-icon-setting"></i>
        <span slot="title">用户管理</span>
      </el-menu-item>
      <el-menu-item index="/home/admin/ShowExamInfo">
        <i class="el-icon-setting"></i>
        <span slot="title">试卷管理</span>
      </el-menu-item>
      <el-menu-item index="/home/admin/ShowQuestionsInfo">
        <i class="el-icon-setting"></i>
        <span slot="title">题库管理</span>
      </el-menu-item>
    </el-submenu>
  </el-menu>
</template>
