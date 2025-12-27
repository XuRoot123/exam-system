<template>
  <el-menu
    class="el-menu-vertical-demo"
    default-active="/"
    router
    :unique-opened="false"
  >
    <el-menu-item index="/">
      <i class="el-icon-setting"></i>
      <span slot="title">首页</span>
    </el-menu-item>
    <el-submenu index="1">
      <template slot="title">
        <i class="el-icon-location"></i>
        <span>班级管理</span>
      </template>
      <el-menu-item-group>
        <template slot="title">学生管理</template>
        <el-menu-item index="/home/teacher/showThisClassStudent">
          <i class="el-icon-setting"></i>
          <span slot="title">查看班级学生</span>
        </el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group>
        <template slot="title">考试</template>
        <el-menu-item index="/home/teacher/ShowHistoryThisClassScoresView">
          <i class="el-icon-setting"></i>
          <span slot="title">查看历史考试记录</span>
        </el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group>
        <template slot="title">代办信息</template>
        <el-menu-item index="/home/teacher/ShowEnterClassApplication">
          <i class="el-icon-setting"></i>
          <span slot="title">查看学生入班申请</span>
        </el-menu-item>
      </el-menu-item-group>
    </el-submenu>

    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-location"></i>
        <span>考试管理</span>
      </template>
      <el-menu-item index="/home/teacher/ReadTitle">
        <i class="el-icon-setting"></i>
        <span slot="title">录入题目</span>
      </el-menu-item>
      <el-menu-item index="/home/teacher/SystemCompilation">
        <i class="el-icon-setting"></i>
        <span slot="title">随机编卷</span>
      </el-menu-item>
      <el-menu-item index="/home/teacher/SelfCompiledVolume">
        <i class="el-icon-setting"></i>
        <span slot="title">自主编卷</span>
      </el-menu-item>
    </el-submenu>
  </el-menu>
</template>
<script setup lang="ts"></script>
