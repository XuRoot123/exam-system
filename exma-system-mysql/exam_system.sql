/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80040
 Source Host           : localhost:3306
 Source Schema         : exam_system

 Target Server Type    : MySQL
 Target Server Version : 80040
 File Encoding         : 65001

 Date: 04/03/2026 17:27:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for chapters
-- ----------------------------
DROP TABLE IF EXISTS `chapters`;
CREATE TABLE `chapters`  (
  `chapter_id` int NOT NULL AUTO_INCREMENT COMMENT '章节ID',
  `course_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '课程名称',
  `chapter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '章节名称',
  `chapter_order` int NOT NULL COMMENT '章节顺序',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '章节描述',
  PRIMARY KEY (`chapter_id`) USING BTREE,
  INDEX `idx_course_name`(`course_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '课程章节表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of chapters
-- ----------------------------
INSERT INTO `chapters` VALUES (1, '高等数学', '微积分基础', 1, '介绍微积分基本概念和计算方法');
INSERT INTO `chapters` VALUES (2, '高等数学', '多元微积分', 2, '多元函数微分学和积分学');
INSERT INTO `chapters` VALUES (3, '线性代数', '矩阵与行列式', 1, '矩阵运算和行列式计算');
INSERT INTO `chapters` VALUES (4, '线性代数', '向量空间', 2, '向量空间与线性变换');
INSERT INTO `chapters` VALUES (5, '无', '无', 25, '无');

-- ----------------------------
-- Table structure for classes
-- ----------------------------
DROP TABLE IF EXISTS `classes`;
CREATE TABLE `classes`  (
  `class_id` int NOT NULL AUTO_INCREMENT COMMENT '班级ID',
  `class_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '班级名称',
  `teacher_id` int NOT NULL COMMENT '负责教师ID',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`class_id`) USING BTREE,
  UNIQUE INDEX `class_name`(`class_name`) USING BTREE,
  INDEX `teacher_id`(`teacher_id`) USING BTREE,
  CONSTRAINT `classes_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '班级表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of classes
-- ----------------------------
INSERT INTO `classes` VALUES (1, '计算机科学1班', 2, '2025-06-17 14:44:56');
INSERT INTO `classes` VALUES (2, '软件工程2班', 17, '2025-06-17 14:44:56');
INSERT INTO `classes` VALUES (3, 'all', 25, '2025-10-30 13:19:40');

-- ----------------------------
-- Table structure for exampapers
-- ----------------------------
DROP TABLE IF EXISTS `exampapers`;
CREATE TABLE `exampapers`  (
  `paper_id` int NOT NULL AUTO_INCREMENT COMMENT '试卷ID',
  `paper_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '试卷名称',
  `teacher_id` int NOT NULL COMMENT '出卷教师',
  `chapter_id` int NULL DEFAULT NULL COMMENT '所属章节',
  `total_score` int NOT NULL COMMENT '试卷总分',
  `duration_min` int NOT NULL COMMENT '考试时长(分钟)',
  `status` enum('draft','published') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'draft' COMMENT '试卷状态',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`paper_id`) USING BTREE,
  INDEX `teacher_id`(`teacher_id`) USING BTREE,
  INDEX `chapter_id`(`chapter_id`) USING BTREE,
  INDEX `idx_paper_status`(`status`) USING BTREE,
  CONSTRAINT `exampapers_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `exampapers_ibfk_2` FOREIGN KEY (`chapter_id`) REFERENCES `chapters` (`chapter_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 59 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '试卷表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exampapers
-- ----------------------------
INSERT INTO `exampapers` VALUES (29, '测试试卷111', 25, 1, 20, 90, 'published', '2025-11-18 04:45:49');
INSERT INTO `exampapers` VALUES (30, '测试试卷222', 25, 1, 20, 90, 'published', '2025-11-18 04:55:01');
INSERT INTO `exampapers` VALUES (31, '测试试卷333', 25, 1, 20, 90, 'published', '2025-11-18 04:55:26');
INSERT INTO `exampapers` VALUES (32, '测试试卷444', 25, 1, 20, 90, 'published', '2025-11-18 05:48:15');
INSERT INTO `exampapers` VALUES (33, '测试试卷555', 25, 5, 29, 90, 'published', '2025-11-18 06:01:50');
INSERT INTO `exampapers` VALUES (34, '测试数据666', 25, 5, 29, 90, 'published', '2025-11-18 06:05:29');
INSERT INTO `exampapers` VALUES (35, '测试数据777', 25, 3, 0, 90, 'published', '2025-11-18 07:35:51');
INSERT INTO `exampapers` VALUES (36, '测试试卷777', 25, 5, 72, 150, 'published', '2025-11-27 17:15:51');
INSERT INTO `exampapers` VALUES (37, '测试试卷888', 25, 5, 129, 330, 'published', '2025-11-27 17:16:22');
INSERT INTO `exampapers` VALUES (38, '测试试卷999', 25, 5, 60, 150, 'published', '2025-11-27 17:16:42');
INSERT INTO `exampapers` VALUES (39, '全填空111', 25, 5, 0, 90, 'published', '2025-11-27 17:49:23');
INSERT INTO `exampapers` VALUES (40, '全填空111', 25, 5, 0, 90, 'published', '2025-11-27 17:54:30');
INSERT INTO `exampapers` VALUES (41, '全填空111', 25, 5, 35, 90, 'published', '2025-11-27 17:57:17');
INSERT INTO `exampapers` VALUES (42, '完整测试111', 25, 5, 195, 210, 'published', '2025-11-29 15:06:48');
INSERT INTO `exampapers` VALUES (43, '完整测试222', 25, 5, 64, 90, 'published', '2025-11-29 15:11:44');
INSERT INTO `exampapers` VALUES (44, '完整测试333', 25, 5, 69, 90, 'published', '2025-11-30 23:31:33');
INSERT INTO `exampapers` VALUES (45, '完整测试444', 25, 5, 67, 90, 'published', '2025-11-30 23:44:12');
INSERT INTO `exampapers` VALUES (46, '', 25, 5, 27, 90, 'published', '2025-11-30 23:56:18');
INSERT INTO `exampapers` VALUES (47, '错题测试111', 25, 5, 46, 90, 'published', '2025-12-18 14:33:20');
INSERT INTO `exampapers` VALUES (48, '错题测试222', 25, 5, 69, 90, 'published', '2025-12-18 14:33:36');
INSERT INTO `exampapers` VALUES (49, '错题测试333', 25, 5, 44, 90, 'published', '2025-12-18 16:28:24');
INSERT INTO `exampapers` VALUES (50, '错题测试444', 25, 5, 40, 90, 'published', '2025-12-18 16:28:43');
INSERT INTO `exampapers` VALUES (51, '错题测试555', 25, 5, 40, 90, 'published', '2025-12-18 16:28:57');
INSERT INTO `exampapers` VALUES (52, '错题测试666', 25, 5, 47, 90, 'published', '2025-12-18 17:01:01');
INSERT INTO `exampapers` VALUES (53, '错题测试777', 25, 5, 48, 90, 'published', '2025-12-18 17:01:22');
INSERT INTO `exampapers` VALUES (54, '错题测试888', 25, 5, 41, 90, 'published', '2025-12-18 17:01:36');
INSERT INTO `exampapers` VALUES (55, '错题测试888', 25, 5, 41, 90, 'published', '2025-12-18 17:20:09');
INSERT INTO `exampapers` VALUES (56, '错题测试999', 25, 5, 42, 90, 'published', '2025-12-18 17:20:24');
INSERT INTO `exampapers` VALUES (57, '全面测试111', 25, 5, 73, 90, 'published', '2025-12-27 11:11:27');
INSERT INTO `exampapers` VALUES (58, '全面测试222', 2, 5, 66, 90, 'published', '2025-12-27 11:17:03');
INSERT INTO `exampapers` VALUES (59, '实操测试111', 2, 5, 66, 210, 'published', '2025-12-27 23:48:33');

-- ----------------------------
-- Table structure for exams
-- ----------------------------
DROP TABLE IF EXISTS `exams`;
CREATE TABLE `exams`  (
  `exam_id` int NOT NULL AUTO_INCREMENT COMMENT '考试ID',
  `paper_id` int NOT NULL COMMENT '试卷ID',
  `class_id` int NOT NULL COMMENT '班级ID',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `status` enum('pending','ongoing','completed') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'pending' COMMENT '考试状态',
  `instructions` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '考试说明',
  PRIMARY KEY (`exam_id`) USING BTREE,
  INDEX `paper_id`(`paper_id`) USING BTREE,
  INDEX `class_id`(`class_id`) USING BTREE,
  INDEX `idx_exam_time`(`start_time`, `end_time`) USING BTREE,
  CONSTRAINT `exams_ibfk_1` FOREIGN KEY (`paper_id`) REFERENCES `exampapers` (`paper_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `exams_ibfk_2` FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '考试安排表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exams
-- ----------------------------
INSERT INTO `exams` VALUES (11, 29, 3, '2025-11-11 04:45:46', '2025-11-18 04:45:46', 'completed', '123');
INSERT INTO `exams` VALUES (12, 30, 3, '2025-11-11 04:54:59', '2025-11-18 04:54:59', 'completed', '123');
INSERT INTO `exams` VALUES (13, 31, 3, '2025-11-21 00:00:00', '2025-11-22 00:00:00', 'pending', '123');
INSERT INTO `exams` VALUES (14, 32, 2, '2025-11-11 05:48:13', '2025-11-18 05:48:13', 'completed', '123');
INSERT INTO `exams` VALUES (15, 33, 3, '2025-11-18 00:00:00', '2025-11-22 00:00:00', 'ongoing', '123');
INSERT INTO `exams` VALUES (16, 34, 3, '2025-11-18 00:00:00', '2025-11-20 00:00:00', 'ongoing', '123');
INSERT INTO `exams` VALUES (17, 35, 3, '2025-11-18 00:00:00', '2025-11-23 00:00:00', 'ongoing', '123123');
INSERT INTO `exams` VALUES (18, 36, 3, '2025-11-26 00:00:00', '2025-11-30 00:00:00', 'ongoing', '111');
INSERT INTO `exams` VALUES (19, 37, 3, '2025-11-26 00:00:00', '2025-11-30 00:00:00', 'ongoing', '111');
INSERT INTO `exams` VALUES (20, 38, 3, '2025-11-19 00:00:00', '2025-11-30 00:00:00', 'ongoing', '123');
INSERT INTO `exams` VALUES (21, 39, 3, '2025-11-20 00:00:00', '2025-11-30 00:00:00', 'ongoing', '111');
INSERT INTO `exams` VALUES (22, 40, 3, '2025-11-25 00:00:00', '2025-11-30 00:00:00', 'ongoing', '111');
INSERT INTO `exams` VALUES (23, 41, 3, '2025-11-18 00:00:00', '2025-11-30 00:00:00', 'ongoing', '111');
INSERT INTO `exams` VALUES (24, 42, 3, '2025-11-27 00:00:00', '2025-12-02 00:00:00', 'ongoing', '111');
INSERT INTO `exams` VALUES (25, 43, 3, '2025-11-28 00:00:00', '2025-12-02 00:00:00', 'ongoing', '111');
INSERT INTO `exams` VALUES (26, 44, 3, '2025-11-26 00:00:00', '2025-12-02 00:00:00', 'ongoing', '111');
INSERT INTO `exams` VALUES (27, 45, 3, '2025-11-25 00:00:00', '2025-12-09 00:00:00', 'ongoing', '111');
INSERT INTO `exams` VALUES (28, 46, 3, '2025-11-27 00:00:00', '2025-12-01 00:00:00', 'ongoing', '111');
INSERT INTO `exams` VALUES (29, 47, 3, '2025-12-11 00:00:00', '2025-12-19 00:00:00', 'ongoing', '111');
INSERT INTO `exams` VALUES (30, 48, 3, '2025-12-16 00:00:00', '2025-12-30 00:00:00', 'ongoing', '111');
INSERT INTO `exams` VALUES (31, 49, 3, '2025-12-17 00:00:00', '2025-12-31 00:00:00', 'ongoing', '111');
INSERT INTO `exams` VALUES (32, 50, 3, '2025-12-17 00:00:00', '2025-12-23 00:00:00', 'ongoing', '111');
INSERT INTO `exams` VALUES (33, 51, 3, '2025-12-17 00:00:00', '2025-12-23 00:00:00', 'ongoing', '111');
INSERT INTO `exams` VALUES (34, 52, 3, '2025-12-15 00:00:00', '2025-12-23 00:00:00', 'ongoing', '111');
INSERT INTO `exams` VALUES (35, 53, 3, '2025-12-17 00:00:00', '2025-12-27 00:00:00', 'ongoing', '111');
INSERT INTO `exams` VALUES (36, 54, 3, '2025-12-17 00:00:00', '2025-12-31 00:00:00', 'ongoing', '111');
INSERT INTO `exams` VALUES (37, 55, 3, '2025-12-17 00:00:00', '2025-12-31 00:00:00', 'ongoing', '钱钱钱111');
INSERT INTO `exams` VALUES (38, 56, 3, '2025-12-18 00:00:00', '2025-12-31 00:00:00', 'ongoing', 'qqq');
INSERT INTO `exams` VALUES (39, 57, 3, '2025-12-23 00:00:00', '2025-12-31 00:00:00', 'ongoing', '111');
INSERT INTO `exams` VALUES (40, 58, 3, '2025-12-23 00:00:00', '2025-12-31 00:00:00', 'ongoing', '111');
INSERT INTO `exams` VALUES (41, 59, 1, '2025-12-02 00:00:00', '2026-01-09 00:00:00', 'ongoing', '');

-- ----------------------------
-- Table structure for paper_questions
-- ----------------------------
DROP TABLE IF EXISTS `paper_questions`;
CREATE TABLE `paper_questions`  (
  `relation_id` int NOT NULL AUTO_INCREMENT COMMENT '关联ID',
  `paper_id` int NOT NULL COMMENT '试卷ID',
  `question_id` int NOT NULL COMMENT '题目ID',
  `question_score` int NOT NULL COMMENT '本题分值',
  `question_order` int NOT NULL COMMENT '题目顺序',
  PRIMARY KEY (`relation_id`) USING BTREE,
  UNIQUE INDEX `uq_paper_question`(`paper_id`, `question_id`) USING BTREE,
  INDEX `question_id`(`question_id`) USING BTREE,
  INDEX `idx_question_order`(`question_order`) USING BTREE,
  CONSTRAINT `paper_questions_ibfk_1` FOREIGN KEY (`paper_id`) REFERENCES `exampapers` (`paper_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `paper_questions_ibfk_2` FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 239 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '试卷题目关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of paper_questions
-- ----------------------------
INSERT INTO `paper_questions` VALUES (31, 29, 14, 7, 1);
INSERT INTO `paper_questions` VALUES (32, 29, 1, 5, 2);
INSERT INTO `paper_questions` VALUES (33, 29, 3, 8, 3);
INSERT INTO `paper_questions` VALUES (34, 30, 14, 7, 1);
INSERT INTO `paper_questions` VALUES (35, 30, 1, 5, 2);
INSERT INTO `paper_questions` VALUES (36, 30, 3, 8, 3);
INSERT INTO `paper_questions` VALUES (37, 31, 1, 5, 1);
INSERT INTO `paper_questions` VALUES (38, 31, 14, 7, 2);
INSERT INTO `paper_questions` VALUES (39, 31, 3, 8, 3);
INSERT INTO `paper_questions` VALUES (40, 32, 14, 7, 1);
INSERT INTO `paper_questions` VALUES (41, 32, 1, 5, 2);
INSERT INTO `paper_questions` VALUES (42, 32, 3, 8, 3);
INSERT INTO `paper_questions` VALUES (43, 33, 14, 7, 1);
INSERT INTO `paper_questions` VALUES (44, 33, 1, 5, 2);
INSERT INTO `paper_questions` VALUES (45, 33, 15, 9, 3);
INSERT INTO `paper_questions` VALUES (46, 33, 3, 8, 4);
INSERT INTO `paper_questions` VALUES (47, 34, 1, 5, 1);
INSERT INTO `paper_questions` VALUES (48, 34, 14, 7, 2);
INSERT INTO `paper_questions` VALUES (49, 34, 15, 9, 3);
INSERT INTO `paper_questions` VALUES (50, 34, 3, 8, 4);
INSERT INTO `paper_questions` VALUES (51, 36, 36, 6, 1);
INSERT INTO `paper_questions` VALUES (52, 36, 1, 5, 2);
INSERT INTO `paper_questions` VALUES (53, 36, 32, 5, 3);
INSERT INTO `paper_questions` VALUES (54, 36, 14, 7, 4);
INSERT INTO `paper_questions` VALUES (55, 36, 34, 5, 5);
INSERT INTO `paper_questions` VALUES (56, 36, 50, 9, 6);
INSERT INTO `paper_questions` VALUES (57, 36, 3, 8, 7);
INSERT INTO `paper_questions` VALUES (58, 36, 46, 9, 8);
INSERT INTO `paper_questions` VALUES (59, 36, 45, 8, 9);
INSERT INTO `paper_questions` VALUES (60, 36, 51, 10, 10);
INSERT INTO `paper_questions` VALUES (61, 37, 34, 5, 1);
INSERT INTO `paper_questions` VALUES (62, 37, 1, 5, 2);
INSERT INTO `paper_questions` VALUES (63, 37, 37, 5, 3);
INSERT INTO `paper_questions` VALUES (64, 37, 35, 7, 4);
INSERT INTO `paper_questions` VALUES (65, 37, 14, 7, 5);
INSERT INTO `paper_questions` VALUES (66, 37, 31, 5, 6);
INSERT INTO `paper_questions` VALUES (67, 37, 36, 6, 7);
INSERT INTO `paper_questions` VALUES (68, 37, 32, 5, 8);
INSERT INTO `paper_questions` VALUES (69, 37, 33, 6, 9);
INSERT INTO `paper_questions` VALUES (70, 37, 47, 10, 10);
INSERT INTO `paper_questions` VALUES (71, 37, 51, 10, 11);
INSERT INTO `paper_questions` VALUES (72, 37, 45, 8, 12);
INSERT INTO `paper_questions` VALUES (73, 37, 46, 9, 13);
INSERT INTO `paper_questions` VALUES (74, 37, 15, 9, 14);
INSERT INTO `paper_questions` VALUES (75, 37, 49, 7, 15);
INSERT INTO `paper_questions` VALUES (76, 37, 48, 8, 16);
INSERT INTO `paper_questions` VALUES (77, 37, 50, 9, 17);
INSERT INTO `paper_questions` VALUES (78, 37, 3, 8, 18);
INSERT INTO `paper_questions` VALUES (79, 38, 32, 5, 1);
INSERT INTO `paper_questions` VALUES (80, 38, 31, 5, 2);
INSERT INTO `paper_questions` VALUES (81, 38, 14, 7, 3);
INSERT INTO `paper_questions` VALUES (82, 38, 35, 7, 4);
INSERT INTO `paper_questions` VALUES (83, 38, 48, 8, 5);
INSERT INTO `paper_questions` VALUES (84, 38, 50, 9, 6);
INSERT INTO `paper_questions` VALUES (85, 38, 51, 10, 7);
INSERT INTO `paper_questions` VALUES (86, 38, 15, 9, 8);
INSERT INTO `paper_questions` VALUES (87, 41, 41, 9, 1);
INSERT INTO `paper_questions` VALUES (88, 41, 16, 8, 2);
INSERT INTO `paper_questions` VALUES (89, 41, 38, 6, 3);
INSERT INTO `paper_questions` VALUES (90, 41, 44, 5, 4);
INSERT INTO `paper_questions` VALUES (91, 41, 40, 7, 5);
INSERT INTO `paper_questions` VALUES (92, 42, 37, 5, 1);
INSERT INTO `paper_questions` VALUES (93, 42, 34, 5, 2);
INSERT INTO `paper_questions` VALUES (94, 42, 1, 5, 3);
INSERT INTO `paper_questions` VALUES (95, 42, 35, 7, 4);
INSERT INTO `paper_questions` VALUES (96, 42, 32, 5, 5);
INSERT INTO `paper_questions` VALUES (97, 42, 36, 6, 6);
INSERT INTO `paper_questions` VALUES (98, 42, 31, 5, 7);
INSERT INTO `paper_questions` VALUES (99, 42, 14, 7, 8);
INSERT INTO `paper_questions` VALUES (100, 42, 33, 6, 9);
INSERT INTO `paper_questions` VALUES (101, 42, 3, 8, 10);
INSERT INTO `paper_questions` VALUES (102, 42, 15, 9, 11);
INSERT INTO `paper_questions` VALUES (103, 42, 45, 8, 12);
INSERT INTO `paper_questions` VALUES (104, 42, 51, 10, 13);
INSERT INTO `paper_questions` VALUES (105, 42, 49, 7, 14);
INSERT INTO `paper_questions` VALUES (106, 42, 48, 8, 15);
INSERT INTO `paper_questions` VALUES (107, 42, 50, 9, 16);
INSERT INTO `paper_questions` VALUES (108, 42, 47, 10, 17);
INSERT INTO `paper_questions` VALUES (109, 42, 46, 9, 18);
INSERT INTO `paper_questions` VALUES (110, 42, 38, 6, 19);
INSERT INTO `paper_questions` VALUES (111, 42, 39, 8, 20);
INSERT INTO `paper_questions` VALUES (112, 42, 40, 7, 21);
INSERT INTO `paper_questions` VALUES (113, 42, 44, 5, 22);
INSERT INTO `paper_questions` VALUES (114, 42, 16, 8, 23);
INSERT INTO `paper_questions` VALUES (115, 42, 2, 10, 24);
INSERT INTO `paper_questions` VALUES (116, 42, 42, 6, 25);
INSERT INTO `paper_questions` VALUES (117, 42, 41, 9, 26);
INSERT INTO `paper_questions` VALUES (118, 42, 43, 7, 27);
INSERT INTO `paper_questions` VALUES (119, 43, 37, 5, 1);
INSERT INTO `paper_questions` VALUES (120, 43, 31, 5, 2);
INSERT INTO `paper_questions` VALUES (121, 43, 36, 6, 3);
INSERT INTO `paper_questions` VALUES (122, 43, 48, 8, 4);
INSERT INTO `paper_questions` VALUES (123, 43, 45, 8, 5);
INSERT INTO `paper_questions` VALUES (124, 43, 46, 9, 6);
INSERT INTO `paper_questions` VALUES (125, 43, 40, 7, 7);
INSERT INTO `paper_questions` VALUES (126, 43, 41, 9, 8);
INSERT INTO `paper_questions` VALUES (127, 43, 43, 7, 9);
INSERT INTO `paper_questions` VALUES (128, 44, 33, 6, 1);
INSERT INTO `paper_questions` VALUES (129, 44, 37, 5, 2);
INSERT INTO `paper_questions` VALUES (130, 44, 31, 5, 3);
INSERT INTO `paper_questions` VALUES (131, 44, 48, 8, 4);
INSERT INTO `paper_questions` VALUES (132, 44, 47, 10, 5);
INSERT INTO `paper_questions` VALUES (133, 44, 45, 8, 6);
INSERT INTO `paper_questions` VALUES (134, 44, 41, 9, 7);
INSERT INTO `paper_questions` VALUES (135, 44, 2, 10, 8);
INSERT INTO `paper_questions` VALUES (136, 44, 16, 8, 9);
INSERT INTO `paper_questions` VALUES (137, 45, 32, 5, 1);
INSERT INTO `paper_questions` VALUES (138, 45, 31, 5, 2);
INSERT INTO `paper_questions` VALUES (139, 45, 36, 6, 3);
INSERT INTO `paper_questions` VALUES (140, 45, 51, 10, 4);
INSERT INTO `paper_questions` VALUES (141, 45, 3, 8, 5);
INSERT INTO `paper_questions` VALUES (142, 45, 15, 9, 6);
INSERT INTO `paper_questions` VALUES (143, 45, 43, 7, 7);
INSERT INTO `paper_questions` VALUES (144, 45, 39, 8, 8);
INSERT INTO `paper_questions` VALUES (145, 45, 41, 9, 9);
INSERT INTO `paper_questions` VALUES (146, 46, 1, 5, 1);
INSERT INTO `paper_questions` VALUES (147, 46, 31, 5, 2);
INSERT INTO `paper_questions` VALUES (148, 46, 45, 8, 3);
INSERT INTO `paper_questions` VALUES (149, 46, 46, 9, 4);
INSERT INTO `paper_questions` VALUES (150, 47, 35, 7, 1);
INSERT INTO `paper_questions` VALUES (151, 47, 14, 7, 2);
INSERT INTO `paper_questions` VALUES (152, 47, 3, 8, 3);
INSERT INTO `paper_questions` VALUES (153, 47, 46, 9, 4);
INSERT INTO `paper_questions` VALUES (154, 47, 39, 8, 5);
INSERT INTO `paper_questions` VALUES (155, 47, 43, 7, 6);
INSERT INTO `paper_questions` VALUES (156, 48, 33, 6, 1);
INSERT INTO `paper_questions` VALUES (157, 48, 35, 7, 2);
INSERT INTO `paper_questions` VALUES (158, 48, 36, 6, 3);
INSERT INTO `paper_questions` VALUES (159, 48, 46, 9, 4);
INSERT INTO `paper_questions` VALUES (160, 48, 49, 7, 5);
INSERT INTO `paper_questions` VALUES (161, 48, 50, 9, 6);
INSERT INTO `paper_questions` VALUES (162, 48, 2, 10, 7);
INSERT INTO `paper_questions` VALUES (163, 48, 43, 7, 8);
INSERT INTO `paper_questions` VALUES (164, 48, 16, 8, 9);
INSERT INTO `paper_questions` VALUES (165, 49, 33, 6, 1);
INSERT INTO `paper_questions` VALUES (166, 49, 34, 5, 2);
INSERT INTO `paper_questions` VALUES (167, 49, 46, 9, 3);
INSERT INTO `paper_questions` VALUES (168, 49, 15, 9, 4);
INSERT INTO `paper_questions` VALUES (169, 49, 2, 10, 5);
INSERT INTO `paper_questions` VALUES (170, 49, 44, 5, 6);
INSERT INTO `paper_questions` VALUES (171, 50, 1, 5, 1);
INSERT INTO `paper_questions` VALUES (172, 50, 35, 7, 2);
INSERT INTO `paper_questions` VALUES (173, 50, 48, 8, 3);
INSERT INTO `paper_questions` VALUES (174, 50, 49, 7, 4);
INSERT INTO `paper_questions` VALUES (175, 50, 44, 5, 5);
INSERT INTO `paper_questions` VALUES (176, 50, 39, 8, 6);
INSERT INTO `paper_questions` VALUES (177, 51, 1, 5, 1);
INSERT INTO `paper_questions` VALUES (178, 51, 14, 7, 2);
INSERT INTO `paper_questions` VALUES (179, 51, 49, 7, 3);
INSERT INTO `paper_questions` VALUES (180, 51, 48, 8, 4);
INSERT INTO `paper_questions` VALUES (181, 51, 42, 6, 5);
INSERT INTO `paper_questions` VALUES (182, 51, 40, 7, 6);
INSERT INTO `paper_questions` VALUES (183, 52, 34, 5, 1);
INSERT INTO `paper_questions` VALUES (184, 52, 36, 6, 2);
INSERT INTO `paper_questions` VALUES (185, 52, 3, 8, 3);
INSERT INTO `paper_questions` VALUES (186, 52, 51, 10, 4);
INSERT INTO `paper_questions` VALUES (187, 52, 16, 8, 5);
INSERT INTO `paper_questions` VALUES (188, 52, 2, 10, 6);
INSERT INTO `paper_questions` VALUES (189, 53, 35, 7, 1);
INSERT INTO `paper_questions` VALUES (190, 53, 14, 7, 2);
INSERT INTO `paper_questions` VALUES (191, 53, 46, 9, 3);
INSERT INTO `paper_questions` VALUES (192, 53, 51, 10, 4);
INSERT INTO `paper_questions` VALUES (193, 53, 16, 8, 5);
INSERT INTO `paper_questions` VALUES (194, 53, 43, 7, 6);
INSERT INTO `paper_questions` VALUES (195, 54, 31, 5, 1);
INSERT INTO `paper_questions` VALUES (196, 54, 37, 5, 2);
INSERT INTO `paper_questions` VALUES (197, 54, 15, 9, 3);
INSERT INTO `paper_questions` VALUES (198, 54, 50, 9, 4);
INSERT INTO `paper_questions` VALUES (199, 54, 43, 7, 5);
INSERT INTO `paper_questions` VALUES (200, 54, 38, 6, 6);
INSERT INTO `paper_questions` VALUES (201, 55, 37, 5, 1);
INSERT INTO `paper_questions` VALUES (202, 55, 36, 6, 2);
INSERT INTO `paper_questions` VALUES (203, 55, 50, 9, 3);
INSERT INTO `paper_questions` VALUES (204, 55, 45, 8, 4);
INSERT INTO `paper_questions` VALUES (205, 55, 40, 7, 5);
INSERT INTO `paper_questions` VALUES (206, 55, 42, 6, 6);
INSERT INTO `paper_questions` VALUES (207, 56, 33, 6, 1);
INSERT INTO `paper_questions` VALUES (208, 56, 1, 5, 2);
INSERT INTO `paper_questions` VALUES (209, 56, 47, 10, 3);
INSERT INTO `paper_questions` VALUES (210, 56, 49, 7, 4);
INSERT INTO `paper_questions` VALUES (211, 56, 39, 8, 5);
INSERT INTO `paper_questions` VALUES (212, 56, 38, 6, 6);
INSERT INTO `paper_questions` VALUES (213, 57, 14, 7, 1);
INSERT INTO `paper_questions` VALUES (214, 57, 52, 7, 2);
INSERT INTO `paper_questions` VALUES (215, 57, 32, 5, 3);
INSERT INTO `paper_questions` VALUES (216, 57, 51, 10, 4);
INSERT INTO `paper_questions` VALUES (217, 57, 45, 8, 5);
INSERT INTO `paper_questions` VALUES (218, 57, 47, 10, 6);
INSERT INTO `paper_questions` VALUES (219, 57, 16, 8, 7);
INSERT INTO `paper_questions` VALUES (220, 57, 2, 10, 8);
INSERT INTO `paper_questions` VALUES (221, 57, 39, 8, 9);
INSERT INTO `paper_questions` VALUES (222, 58, 16, 8, 1);
INSERT INTO `paper_questions` VALUES (223, 58, 3, 8, 2);
INSERT INTO `paper_questions` VALUES (224, 58, 2, 10, 3);
INSERT INTO `paper_questions` VALUES (225, 58, 15, 9, 4);
INSERT INTO `paper_questions` VALUES (226, 58, 14, 7, 5);
INSERT INTO `paper_questions` VALUES (227, 58, 38, 6, 6);
INSERT INTO `paper_questions` VALUES (228, 58, 31, 5, 7);
INSERT INTO `paper_questions` VALUES (229, 58, 45, 8, 8);
INSERT INTO `paper_questions` VALUES (230, 58, 1, 5, 9);
INSERT INTO `paper_questions` VALUES (231, 59, 16, 8, 1);
INSERT INTO `paper_questions` VALUES (232, 59, 14, 7, 2);
INSERT INTO `paper_questions` VALUES (233, 59, 15, 9, 3);
INSERT INTO `paper_questions` VALUES (234, 59, 45, 8, 4);
INSERT INTO `paper_questions` VALUES (235, 59, 2, 10, 5);
INSERT INTO `paper_questions` VALUES (236, 59, 38, 6, 6);
INSERT INTO `paper_questions` VALUES (237, 59, 3, 8, 7);
INSERT INTO `paper_questions` VALUES (238, 59, 1, 5, 8);
INSERT INTO `paper_questions` VALUES (239, 59, 31, 5, 9);

-- ----------------------------
-- Table structure for questions
-- ----------------------------
DROP TABLE IF EXISTS `questions`;
CREATE TABLE `questions`  (
  `question_id` int NOT NULL AUTO_INCREMENT COMMENT '题目ID',
  `chapter_id` int NOT NULL COMMENT '章节ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '题目内容',
  `type` enum('single_choice','multiple_choice','true_false','fill_blank','essay') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '题目类型',
  `options` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '选项',
  `answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '正确答案',
  `base_score` int NOT NULL DEFAULT 5 COMMENT '基础分值',
  `explanation` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '题目解析',
  `difficulty` enum('easy','medium','hard') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'medium' COMMENT '难度',
  `created_by` int NOT NULL COMMENT '创建者',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`question_id`) USING BTREE,
  INDEX `chapter_id`(`chapter_id`) USING BTREE,
  INDEX `created_by`(`created_by`) USING BTREE,
  INDEX `idx_question_type`(`type`) USING BTREE,
  INDEX `idx_question_difficulty`(`difficulty`) USING BTREE,
  CONSTRAINT `questions_ibfk_1` FOREIGN KEY (`chapter_id`) REFERENCES `chapters` (`chapter_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `questions_ibfk_2` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '题目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of questions
-- ----------------------------
INSERT INTO `questions` VALUES (1, 1, '函数 f(x) = x² 的导数是？', 'single_choice', '[\"A. 2x\", \"B. x\", \"C. 2\", \"D. x²\"]', 'A', 5, '根据基本导数公式', 'easy', 2, '2025-06-17 14:44:56');
INSERT INTO `questions` VALUES (2, 1, '∫(2x dx) 等于？', 'fill_blank', NULL, 'x² + C', 10, '基本积分公式', 'easy', 2, '2025-06-17 14:44:56');
INSERT INTO `questions` VALUES (3, 1, '洛必达法则适用于哪些类型的极限？', 'multiple_choice', '[\"A. 0/0型\", \"B. ∞/∞型\", \"C. 0*∞型\", \"D. ∞-∞型\"]', 'A,B', 8, '洛必达法则适用条件', 'medium', 2, '2025-06-17 14:44:56');
INSERT INTO `questions` VALUES (14, 1, '1', 'single_choice', '[\"A.2\",\"B.3\",\"C.4\",\"D.5\"]', 'A', 7, '6', 'medium', 2, '2025-09-26 15:23:41');
INSERT INTO `questions` VALUES (15, 2, '11', 'multiple_choice', '[\"A.123\",\"B.123\",\"C.123\",\"D.123\"]', 'C,B,D,A', 9, '12323321321', 'easy', 2, '2025-10-12 11:13:49');
INSERT INTO `questions` VALUES (16, 1, '312321', 'fill_blank', NULL, '321321', 8, '321321', 'medium', 2, '2025-10-12 11:32:33');
INSERT INTO `questions` VALUES (31, 1, '函数 f(x) = 3x³ 的导数是？', 'single_choice', '[\"A. 9x²\", \"B. 3x²\", \"C. 6x\", \"D. 9x\"]', 'A', 5, '幂函数求导公式：(xⁿ)′ = nxⁿ⁻¹', 'easy', 2, '2025-10-19 09:00:00');
INSERT INTO `questions` VALUES (32, 2, '函数 y = cos(x) 的导数是？', 'single_choice', '[\"A. -sin(x)\", \"B. sin(x)\", \"C. -cos(x)\", \"D. cos(x)\"]', 'A', 5, '三角函数求导公式：(cosx)′ = -sinx', 'easy', 2, '2025-10-19 09:05:00');
INSERT INTO `questions` VALUES (33, 3, '∫eˣ dx 的结果是？', 'single_choice', '[\"A. eˣ + C\", \"B. ln|x| + C\", \"C. xeˣ + C\", \"D. eˣ\"]', 'A', 6, '指数函数积分公式：∫eˣ dx = eˣ + C', 'medium', 2, '2025-10-19 09:10:00');
INSERT INTO `questions` VALUES (34, 4, '函数 f(x) = ln(x) 的导数是？', 'single_choice', '[\"A. 1/x\", \"B. eˣ\", \"C. x\", \"D. -1/x²\"]', 'A', 5, '对数函数求导公式：(lnx)′ = 1/x', 'easy', 2, '2025-10-19 09:15:00');
INSERT INTO `questions` VALUES (35, 4, 'lim(x→∞) (1 + 1/x)ˣ = ?', 'single_choice', '[\"A. e\", \"B. 1\", \"C. 0\", \"D. ∞\"]', 'A', 7, '重要极限公式：lim(x→∞) (1 + 1/x)ˣ = e', 'medium', 2, '2025-10-19 09:20:00');
INSERT INTO `questions` VALUES (36, 3, '函数 f(x) = √x 的导数是？', 'single_choice', '[\"A. 1/(2√x)\", \"B. 2√x\", \"C. 1/√x\", \"D. √x\"]', 'A', 6, '幂函数求导：√x = x¹ᐟ²，导数为 (1/2)x⁻¹ᐟ²', 'medium', 2, '2025-10-19 09:25:00');
INSERT INTO `questions` VALUES (37, 2, '∫sin(x) dx = ?', 'single_choice', '[\"A. -cos(x) + C\", \"B. cos(x) + C\", \"C. sin(x) + C\", \"D. -sin(x) + C\"]', 'A', 5, '三角函数积分公式：∫sinx dx = -cosx + C', 'easy', 2, '2025-10-19 09:30:00');
INSERT INTO `questions` VALUES (38, 1, '函数 f(x) = 5x⁴ 的导数是 ______', 'fill_blank', NULL, '20x³', 6, '幂函数求导：5×4x³ = 20x³', 'easy', 2, '2025-10-19 10:00:00');
INSERT INTO `questions` VALUES (39, 2, '∫3x² dx = ______ + C', 'fill_blank', NULL, 'x³', 8, '幂函数积分公式：∫xⁿ dx = xⁿ⁺¹/(n+1) + C', 'medium', 2, '2025-10-19 10:05:00');
INSERT INTO `questions` VALUES (40, 3, '函数 y = tan(x) 的导数是 ______', 'fill_blank', NULL, 'sec²(x)', 7, '三角函数求导公式：(tanx)′ = sec²x', 'medium', 2, '2025-10-19 10:10:00');
INSERT INTO `questions` VALUES (41, 4, 'lim(x→0) (1 - cosx)/x² = ______', 'fill_blank', NULL, '1/2', 9, '使用洛必达法则或等价无穷小替换', 'hard', 2, '2025-10-19 10:15:00');
INSERT INTO `questions` VALUES (42, 4, '∫1/x dx = ______ + C', 'fill_blank', NULL, 'ln|x|', 6, '基本积分公式：∫1/x dx = ln|x| + C', 'easy', 2, '2025-10-19 10:20:00');
INSERT INTO `questions` VALUES (43, 3, '函数 f(x) = e²ˣ 的导数是 ______', 'fill_blank', NULL, '2e²ˣ', 7, '复合函数求导：链式法则', 'medium', 2, '2025-10-19 10:25:00');
INSERT INTO `questions` VALUES (44, 2, '∫cos(x) dx = ______ + C', 'fill_blank', NULL, 'sin(x)', 5, '三角函数积分公式：∫cosx dx = sinx + C', 'easy', 2, '2025-10-19 10:30:00');
INSERT INTO `questions` VALUES (45, 1, '下列哪些函数在定义域内可导？', 'multiple_choice', '[\"A. 多项式函数\", \"B. 指数函数\", \"C. 绝对值函数\", \"D. 分段函数\"]', 'A,B', 8, '绝对值函数在零点不可导，分段函数在分段点可能不可导', 'medium', 2, '2025-10-19 11:00:00');
INSERT INTO `questions` VALUES (46, 2, '下列哪些是求导的基本法则？', 'multiple_choice', '[\"A. 和差法则\", \"B. 积法则\", \"C. 商法则\", \"D. 链式法则\"]', 'A,B,C,D', 9, '这些都是微积分中的基本求导法则', 'medium', 2, '2025-10-19 11:05:00');
INSERT INTO `questions` VALUES (47, 3, '下列哪些积分需要使用换元法？', 'multiple_choice', '[\"A. ∫2x·eˣ² dx\", \"B. ∫sin(2x) dx\", \"C. ∫x·cos(x²) dx\", \"D. ∫eˣ dx\"]', 'A,B,C', 10, 'A、B、C都需要通过换元简化，D是基本积分公式', 'hard', 2, '2025-10-19 11:10:00');
INSERT INTO `questions` VALUES (48, 4, '下列哪些极限可以使用洛必达法则？', 'multiple_choice', '[\"A. 0/0型\", \"B. ∞/∞型\", \"C. 0×∞型\", \"D. 1^∞型\"]', 'A,B', 8, '洛必达法则主要适用于0/0和∞/∞型未定式', 'medium', 2, '2025-10-19 11:15:00');
INSERT INTO `questions` VALUES (49, 3, '下列哪些函数是初等函数？', 'multiple_choice', '[\"A. 幂函数\", \"B. 指数函数\", \"C. 对数函数\", \"D. 三角函数\"]', 'A,B,C,D', 7, '这些都是基本的初等函数类型', 'easy', 2, '2025-10-19 11:20:00');
INSERT INTO `questions` VALUES (50, 4, '下列哪些点可能是函数的极值点？', 'multiple_choice', '[\"A. 导数为零的点\", \"B. 导数不存在的点\", \"C. 区间端点\", \"D. 函数间断点\"]', 'A,B,C', 9, '极值点可能出现在驻点、不可导点和区间端点', 'hard', 2, '2025-10-19 11:25:00');
INSERT INTO `questions` VALUES (51, 2, '下列哪些积分是反常积分？', 'multiple_choice', '[\"A. ∫₁^∞ 1/x² dx\", \"B. ∫₀¹ 1/√x dx\", \"C. ∫₋₁¹ 1/x dx\", \"D. ∫₀² x dx\"]', 'A,B,C', 10, 'A是无穷区间，B、C在积分区间内有无穷间断点', 'hard', 2, '2025-10-19 11:30:00');
INSERT INTO `questions` VALUES (52, 1, '111', 'single_choice', '[\"A.22\",\"B.33\",\"C.44\",\"D.55\"]', 'A', 7, '11', 'easy', 2, '2025-12-27 03:50:56');

-- ----------------------------
-- Table structure for scores
-- ----------------------------
DROP TABLE IF EXISTS `scores`;
CREATE TABLE `scores`  (
  `score_id` int NOT NULL AUTO_INCREMENT COMMENT '成绩ID',
  `exam_id` int NOT NULL COMMENT '考试ID',
  `student_id` int NOT NULL COMMENT '学生ID',
  `total_score` int NOT NULL COMMENT '总分',
  `objective_score` int NULL DEFAULT 0 COMMENT '客观题得分',
  `subjective_score` int NULL DEFAULT 0 COMMENT '主观题得分',
  `ranking` int NULL DEFAULT NULL COMMENT '班级排名',
  `evaluated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评分时间',
  PRIMARY KEY (`score_id`) USING BTREE,
  UNIQUE INDEX `uq_exam_student`(`exam_id`, `student_id`) USING BTREE,
  INDEX `student_id`(`student_id`) USING BTREE,
  INDEX `idx_score_ranking`(`ranking`) USING BTREE,
  CONSTRAINT `scores_ibfk_1` FOREIGN KEY (`exam_id`) REFERENCES `exams` (`exam_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `scores_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '成绩表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of scores
-- ----------------------------
INSERT INTO `scores` VALUES (4, 37, 5, 41, 15, 6, 0, '2025-12-19 15:18:57');

-- ----------------------------
-- Table structure for student_answers
-- ----------------------------
DROP TABLE IF EXISTS `student_answers`;
CREATE TABLE `student_answers`  (
  `record_id` int NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `exam_id` int NOT NULL COMMENT '考试ID',
  `student_id` int NOT NULL COMMENT '学生ID',
  `question_id` int NOT NULL COMMENT '题目ID',
  `answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '学生答案',
  `is_correct` tinyint(1) NULL DEFAULT NULL COMMENT '是否正确',
  `is_reviewed` tinyint(1) NULL DEFAULT 0 COMMENT '是否已批改',
  `score` int NULL DEFAULT NULL COMMENT '得分',
  `reviewed_by` int NULL DEFAULT NULL COMMENT '批改人',
  `review_notes` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '批改评语',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '答题时间',
  PRIMARY KEY (`record_id`) USING BTREE,
  UNIQUE INDEX `uq_student_question`(`exam_id`, `student_id`, `question_id`) USING BTREE,
  INDEX `student_id`(`student_id`) USING BTREE,
  INDEX `question_id`(`question_id`) USING BTREE,
  INDEX `reviewed_by`(`reviewed_by`) USING BTREE,
  INDEX `idx_answer_reviewed`(`is_reviewed`) USING BTREE,
  CONSTRAINT `student_answers_ibfk_1` FOREIGN KEY (`exam_id`) REFERENCES `exams` (`exam_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `student_answers_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `student_answers_ibfk_3` FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `student_answers_ibfk_4` FOREIGN KEY (`reviewed_by`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '学生答卷表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_answers
-- ----------------------------
INSERT INTO `student_answers` VALUES (9, 25, 5, 31, 'A', 1, 1, 5, 25, '', '2025-11-30 17:44:44');
INSERT INTO `student_answers` VALUES (10, 25, 5, 36, 'A', 1, 1, 6, 25, '', '2025-11-30 17:44:44');
INSERT INTO `student_answers` VALUES (11, 25, 5, 37, 'A', 1, 1, 5, 25, '', '2025-11-30 17:44:44');
INSERT INTO `student_answers` VALUES (12, 25, 5, 40, '1', 0, 1, 0, 25, '', '2025-11-30 17:44:44');
INSERT INTO `student_answers` VALUES (13, 25, 5, 41, '1', 0, 1, 0, 25, '', '2025-11-30 17:44:44');
INSERT INTO `student_answers` VALUES (14, 25, 5, 43, '1', 0, 1, 0, 25, '', '2025-11-30 17:44:44');
INSERT INTO `student_answers` VALUES (15, 25, 5, 45, 'A,B', 1, 1, 8, 25, '', '2025-11-30 17:44:44');
INSERT INTO `student_answers` VALUES (16, 25, 5, 46, 'A,B,C,D', 1, 1, 9, 25, '', '2025-11-30 17:44:44');
INSERT INTO `student_answers` VALUES (17, 25, 5, 48, 'A,B', 1, 1, 8, 25, '', '2025-11-30 17:44:44');
INSERT INTO `student_answers` VALUES (19, 26, 5, 2, '1', 0, 1, 0, 25, '', '2025-11-30 23:33:06');
INSERT INTO `student_answers` VALUES (20, 26, 5, 16, '321321', 1, 1, 8, 25, '', '2025-11-30 23:33:06');
INSERT INTO `student_answers` VALUES (21, 26, 5, 31, 'A', 1, 1, 5, 25, '', '2025-11-30 23:33:06');
INSERT INTO `student_answers` VALUES (22, 26, 5, 33, 'B', 0, 1, 0, 25, '', '2025-11-30 23:33:06');
INSERT INTO `student_answers` VALUES (23, 26, 5, 37, 'A', 1, 1, 5, 25, '', '2025-11-30 23:33:06');
INSERT INTO `student_answers` VALUES (24, 26, 5, 41, '1', 0, 1, 0, 25, '', '2025-11-30 23:33:06');
INSERT INTO `student_answers` VALUES (25, 26, 5, 45, 'A,B', 1, 1, 8, 25, '', '2025-11-30 23:33:06');
INSERT INTO `student_answers` VALUES (26, 26, 5, 47, 'A,B,C,D', 0, 1, 0, 25, '', '2025-11-30 23:33:06');
INSERT INTO `student_answers` VALUES (27, 26, 5, 48, 'A', 1, 1, 2, 25, '', '2025-11-30 23:33:06');
INSERT INTO `student_answers` VALUES (28, 27, 5, 3, 'A', 1, 1, 2, 25, '', '2025-11-30 23:52:30');
INSERT INTO `student_answers` VALUES (29, 27, 5, 15, 'A', 1, 1, 2, 25, '', '2025-11-30 23:52:30');
INSERT INTO `student_answers` VALUES (30, 27, 5, 31, 'A', 1, 1, 5, 25, '', '2025-11-30 23:52:30');
INSERT INTO `student_answers` VALUES (31, 27, 5, 32, 'A', 1, 1, 5, 25, '', '2025-11-30 23:55:08');
INSERT INTO `student_answers` VALUES (32, 27, 5, 36, 'A', 1, 1, 6, 25, '', '2025-11-30 23:55:08');
INSERT INTO `student_answers` VALUES (33, 27, 5, 39, '1', 0, 1, 0, 25, '', '2025-11-30 23:55:08');
INSERT INTO `student_answers` VALUES (34, 27, 5, 41, '1', 0, 1, 0, 25, '', '2025-11-30 23:55:08');
INSERT INTO `student_answers` VALUES (35, 27, 5, 43, '1', 0, 1, 0, 25, '', '2025-11-30 23:55:08');
INSERT INTO `student_answers` VALUES (36, 27, 5, 51, 'A', 1, 1, 3, 25, '', '2025-11-30 23:55:08');
INSERT INTO `student_answers` VALUES (37, 28, 5, 1, 'A', 1, 1, 5, 25, '', '2025-11-30 23:56:44');
INSERT INTO `student_answers` VALUES (38, 28, 5, 31, 'C', 0, 1, 0, 25, '', '2025-11-30 23:57:03');
INSERT INTO `student_answers` VALUES (39, 28, 5, 45, 'A', 1, 1, 2, 25, '', '2025-11-30 23:57:03');
INSERT INTO `student_answers` VALUES (40, 28, 5, 46, 'A', 1, 1, 2, 25, '', '2025-11-30 23:57:03');
INSERT INTO `student_answers` VALUES (41, 30, 5, 2, 'x²+C', 0, 1, 0, 25, '', '2025-12-18 14:43:36');
INSERT INTO `student_answers` VALUES (42, 30, 5, 16, '321321', 1, 1, 8, 25, '', '2025-12-18 14:43:36');
INSERT INTO `student_answers` VALUES (43, 30, 5, 33, 'A', 1, 1, 6, 25, '', '2025-12-18 14:43:36');
INSERT INTO `student_answers` VALUES (44, 30, 5, 35, 'A', 1, 1, 7, 25, '', '2025-12-18 14:43:36');
INSERT INTO `student_answers` VALUES (45, 30, 5, 36, 'A', 1, 1, 6, 25, '', '2025-12-18 14:43:36');
INSERT INTO `student_answers` VALUES (46, 30, 5, 43, NULL, 0, 1, 0, 25, '', '2025-12-18 14:43:36');
INSERT INTO `student_answers` VALUES (47, 30, 5, 46, 'A', 1, 1, 2, 25, '', '2025-12-18 14:43:36');
INSERT INTO `student_answers` VALUES (48, 30, 5, 49, 'A,B,C,D', 1, 1, 7, 25, '', '2025-12-18 14:43:36');
INSERT INTO `student_answers` VALUES (49, 30, 5, 50, 'A', 1, 1, 2, 25, '', '2025-12-18 14:43:36');
INSERT INTO `student_answers` VALUES (50, 29, 5, 3, 'A,B', 1, 1, 8, 25, '', '2025-12-18 16:19:47');
INSERT INTO `student_answers` VALUES (51, 29, 5, 14, 'A', 1, 1, 7, 25, '', '2025-12-18 16:19:47');
INSERT INTO `student_answers` VALUES (52, 29, 5, 35, 'A', 1, 1, 7, 25, '', '2025-12-18 16:19:47');
INSERT INTO `student_answers` VALUES (53, 29, 5, 39, '11', 0, 1, 0, 25, '', '2025-12-18 16:20:12');
INSERT INTO `student_answers` VALUES (54, 29, 5, 43, '11', 0, 1, 0, 25, '', '2025-12-18 16:20:20');
INSERT INTO `student_answers` VALUES (55, 29, 5, 46, 'A,B', 1, 1, 2, 25, '', '2025-12-18 16:20:20');
INSERT INTO `student_answers` VALUES (56, 31, 5, 2, 'x² + C', 1, 1, 10, 25, '', '2025-12-18 16:35:28');
INSERT INTO `student_answers` VALUES (57, 31, 5, 15, 'A,B,C,D', 0, 1, 0, 25, '', '2025-12-18 16:35:38');
INSERT INTO `student_answers` VALUES (58, 31, 5, 33, 'A', 1, 1, 6, 25, '', '2025-12-18 16:35:39');
INSERT INTO `student_answers` VALUES (59, 31, 5, 34, 'D', 0, 1, 0, 25, '', '2025-12-18 16:35:39');
INSERT INTO `student_answers` VALUES (60, 31, 5, 44, '1', 0, 1, 0, 25, '', '2025-12-18 16:35:40');
INSERT INTO `student_answers` VALUES (61, 31, 5, 46, 'A', 1, 1, 2, 25, '', '2025-12-18 16:35:40');
INSERT INTO `student_answers` VALUES (62, 32, 5, 1, 'A', 1, 1, 5, 25, '', '2025-12-18 16:40:26');
INSERT INTO `student_answers` VALUES (63, 32, 5, 35, 'C', 0, 1, 0, 25, '', '2025-12-18 16:40:26');
INSERT INTO `student_answers` VALUES (64, 32, 5, 39, '1', 0, 1, 0, 25, '', '2025-12-18 16:40:26');
INSERT INTO `student_answers` VALUES (65, 32, 5, 44, 'sin(x)', 1, 1, 5, 25, '', '2025-12-18 16:40:26');
INSERT INTO `student_answers` VALUES (66, 32, 5, 48, 'B', 1, 1, 2, 25, '', '2025-12-18 16:40:26');
INSERT INTO `student_answers` VALUES (67, 32, 5, 49, 'A,B,C,D', 1, 1, 7, 25, '', '2025-12-18 16:40:26');
INSERT INTO `student_answers` VALUES (68, 33, 5, 1, 'A', 1, 1, 5, 25, '', '2025-12-18 16:47:54');
INSERT INTO `student_answers` VALUES (69, 33, 5, 14, 'A', 1, 1, 7, 25, '', '2025-12-18 16:47:54');
INSERT INTO `student_answers` VALUES (70, 33, 5, 40, '1', 0, 1, 0, 25, '', '2025-12-18 16:47:54');
INSERT INTO `student_answers` VALUES (71, 33, 5, 42, '1', 0, 1, 0, 25, '', '2025-12-18 16:47:54');
INSERT INTO `student_answers` VALUES (72, 33, 5, 48, 'A', 1, 1, 2, 25, '', '2025-12-18 16:47:54');
INSERT INTO `student_answers` VALUES (73, 33, 5, 49, 'A,B,C,D', 1, 1, 7, 25, '', '2025-12-18 16:47:54');
INSERT INTO `student_answers` VALUES (74, 35, 5, 14, 'A', 1, 1, 7, 25, '', '2025-12-18 17:21:33');
INSERT INTO `student_answers` VALUES (75, 35, 5, 16, '321321', 1, 1, 8, 25, '', '2025-12-18 17:21:33');
INSERT INTO `student_answers` VALUES (76, 35, 5, 35, 'C', 0, 1, 0, 25, '', '2025-12-18 17:21:33');
INSERT INTO `student_answers` VALUES (77, 35, 5, 43, '11', 0, 1, 0, 25, '', '2025-12-18 17:21:33');
INSERT INTO `student_answers` VALUES (78, 35, 5, 46, 'A,C,D', 1, 1, 2, 25, '', '2025-12-18 17:21:33');
INSERT INTO `student_answers` VALUES (79, 35, 5, 51, 'A,B', 1, 1, 3, 25, '', '2025-12-18 17:21:33');
INSERT INTO `student_answers` VALUES (80, 36, 5, 15, 'A,B,C,D', 0, 1, 0, 25, '', '2025-12-18 17:26:29');
INSERT INTO `student_answers` VALUES (81, 36, 5, 31, 'A', 1, 1, 5, 25, '', '2025-12-18 17:26:29');
INSERT INTO `student_answers` VALUES (82, 36, 5, 37, 'B', 0, 1, 0, 25, '', '2025-12-18 17:26:29');
INSERT INTO `student_answers` VALUES (83, 36, 5, 38, '20x³', 1, 1, 6, 25, '', '2025-12-18 17:26:29');
INSERT INTO `student_answers` VALUES (84, 36, 5, 43, '11111111111111', 0, 1, 0, 25, '', '2025-12-18 17:26:29');
INSERT INTO `student_answers` VALUES (85, 36, 5, 50, 'A', 1, 1, 2, 25, '', '2025-12-18 17:26:29');
INSERT INTO `student_answers` VALUES (86, 37, 5, 36, 'C', 0, 1, 0, 25, '', '2025-12-19 15:21:29');
INSERT INTO `student_answers` VALUES (87, 37, 5, 37, 'A', 1, 1, 5, 25, '', '2025-12-19 15:21:29');
INSERT INTO `student_answers` VALUES (88, 37, 5, 40, '1', 0, 1, 0, 25, '', '2025-12-19 15:21:29');
INSERT INTO `student_answers` VALUES (89, 37, 5, 42, 'ln|x|', 1, 1, 6, 25, '', '2025-12-19 15:21:29');
INSERT INTO `student_answers` VALUES (90, 37, 5, 45, 'A,B', 1, 1, 8, 25, '', '2025-12-19 15:21:29');
INSERT INTO `student_answers` VALUES (91, 37, 5, 50, 'C', 1, 1, 2, 25, '', '2025-12-19 15:21:29');

-- ----------------------------
-- Table structure for undetermined_users
-- ----------------------------
DROP TABLE IF EXISTS `undetermined_users`;
CREATE TABLE `undetermined_users`  (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '真实姓名',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `role` enum('admin','teacher','student') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色',
  `level` enum('admin','teacher') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '待人员级别同意',
  `class_id` varbinary(50) NOT NULL COMMENT '班级',
  `sex` int NOT NULL COMMENT '性别',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  INDEX `idx_user_role`(`role`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '待定用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undetermined_users
-- ----------------------------
INSERT INTO `undetermined_users` VALUES (13, 'ads11122223', '1231231321', 'asd', '123', '12312312312', 'student', 'teacher', 0x32, 1);
INSERT INTO `undetermined_users` VALUES (20, '花香', '123123321321', '有没有', '123@qq.com', '13992625249', 'student', 'teacher', 0x31, 0);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '真实姓名',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `role` enum('admin','teacher','student') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色',
  `class_id` int NULL DEFAULT NULL COMMENT '班级ID(学生)',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `sex` int NOT NULL COMMENT '性别',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  INDEX `idx_user_role`(`role`) USING BTREE,
  INDEX `idx_user_class`(`class_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'admin', 'admin123', '系统管理员', 'admin@school.edu', '13800138000', 'admin', NULL, '2025-06-17 14:44:56', 1);
INSERT INTO `users` VALUES (2, 'teacher_zhang', 'zhang123', '张老师', 'zhang@school.edu', '13900139000', 'teacher', NULL, '2025-06-17 14:44:56', 1);
INSERT INTO `users` VALUES (5, 'stu_li', 'li_123', '李同学', 'li@stu.edu', '15800158001', 'student', 1, '2025-06-17 14:44:56', 2);
INSERT INTO `users` VALUES (6, 'stu_zhao', 'zhao123', '赵同学', 'zhao@stu.edu', '15800158002', 'student', 2, '2025-06-17 14:44:56', 2);
INSERT INTO `users` VALUES (7, 'stu_qian', 'qian123', '钱同学', 'qian@stu.edu', '15800158003', 'student', 2, '2025-06-17 14:44:56', 2);
INSERT INTO `users` VALUES (17, '无', '无', '无', '无', '无', 'teacher', NULL, '2025-06-21 15:36:40', 1);
INSERT INTO `users` VALUES (18, 'root11', 'awdawd123', 'zs', '123', '12312313122', 'student', 1, '2025-06-23 09:33:32', 2);
INSERT INTO `users` VALUES (24, 'asd1', '123123123', '123', '123', '12312312312', 'student', 1, '2025-06-23 09:51:31', 2);
INSERT INTO `users` VALUES (25, 'root_teacher', 'root123', '无', '无', '无', 'teacher', NULL, '2025-10-28 18:18:26', 1);
INSERT INTO `users` VALUES (26, '陈某人111', 'muzichuan26', '陈子豪11', '123456@126.com', '13003635249', 'student', 1, '2025-12-27 11:45:12', 1);
INSERT INTO `users` VALUES (27, '陈某人', 'muzichuan26', '陈子豪', '123456@126.com', '13003635249', 'student', 1, '2025-12-27 11:45:30', 1);

-- ----------------------------
-- Table structure for wrong_answers
-- ----------------------------
DROP TABLE IF EXISTS `wrong_answers`;
CREATE TABLE `wrong_answers`  (
  `wrong_id` int NOT NULL AUTO_INCREMENT COMMENT '错题ID',
  `student_id` int NOT NULL COMMENT '学生ID',
  `question_id` int NOT NULL COMMENT '题目ID',
  `exam_id` int NOT NULL COMMENT '考试ID',
  `wrong_answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '错误答案',
  `correct_answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '正确答案',
  `collected_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收集时间',
  PRIMARY KEY (`wrong_id`) USING BTREE,
  INDEX `student_id`(`student_id`) USING BTREE,
  INDEX `exam_id`(`exam_id`) USING BTREE,
  INDEX `idx_wrong_question`(`question_id`) USING BTREE,
  CONSTRAINT `wrong_answers_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `wrong_answers_ibfk_2` FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `wrong_answers_ibfk_3` FOREIGN KEY (`exam_id`) REFERENCES `exams` (`exam_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '错题本表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wrong_answers
-- ----------------------------
INSERT INTO `wrong_answers` VALUES (4, 5, 31, 28, 'C', 'A', '2025-11-30 23:57:03');
INSERT INTO `wrong_answers` VALUES (5, 5, 2, 30, 'x²+C', 'x² + C', '2025-12-18 14:43:36');
INSERT INTO `wrong_answers` VALUES (6, 5, 39, 29, '11', 'x³', '2025-12-18 16:20:48');
INSERT INTO `wrong_answers` VALUES (7, 5, 43, 29, '11', '2e²ˣ', '2025-12-18 16:21:17');
INSERT INTO `wrong_answers` VALUES (8, 5, 15, 31, 'A,B,C,D', 'C,B,D,A', '2025-12-18 16:35:38');
INSERT INTO `wrong_answers` VALUES (9, 5, 34, 31, 'D', 'A', '2025-12-18 16:35:39');
INSERT INTO `wrong_answers` VALUES (10, 5, 44, 31, '1', 'sin(x)', '2025-12-18 16:35:40');
INSERT INTO `wrong_answers` VALUES (11, 5, 35, 32, 'C', 'A', '2025-12-18 16:40:26');
INSERT INTO `wrong_answers` VALUES (12, 5, 39, 32, '1', 'x³', '2025-12-18 16:40:26');
INSERT INTO `wrong_answers` VALUES (13, 5, 40, 33, '1', 'sec²(x)', '2025-12-18 16:47:54');
INSERT INTO `wrong_answers` VALUES (14, 5, 42, 33, '1', 'ln|x|', '2025-12-18 16:47:54');
INSERT INTO `wrong_answers` VALUES (15, 5, 35, 35, 'C', 'A', '2025-12-18 17:21:33');
INSERT INTO `wrong_answers` VALUES (16, 5, 43, 35, '11', '2e²ˣ', '2025-12-18 17:21:33');
INSERT INTO `wrong_answers` VALUES (17, 5, 15, 36, 'A,B,C,D', 'C,B,D,A', '2025-12-18 17:26:29');
INSERT INTO `wrong_answers` VALUES (18, 5, 37, 36, 'B', 'A', '2025-12-18 17:26:29');
INSERT INTO `wrong_answers` VALUES (19, 5, 43, 36, '11111111111111', '2e²ˣ', '2025-12-18 17:26:29');
INSERT INTO `wrong_answers` VALUES (20, 5, 36, 37, 'C', 'A', '2025-12-19 15:21:29');
INSERT INTO `wrong_answers` VALUES (21, 5, 40, 37, '1', 'sec²(x)', '2025-12-19 15:21:29');

SET FOREIGN_KEY_CHECKS = 1;
