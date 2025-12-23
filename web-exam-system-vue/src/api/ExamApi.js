import request from "@/util/request";

export function addRandomTestPaper(data) {
  return request.post("/exam/addRandomTestPaper", data);
}
export function addTestPaper(data) {
  return request.post("/exam/addTestPaper", data);
}
export function getAllTestPaper() {
  return request.get("/exam/getAllTestPaper");
}
export function deleteExam(paperId) {
  return request.delete("/exam/deleteTestPaper", {
    params: {
      paperId: paperId,
    },
  });
}
export function getTestPaperByChapterId(chapterId, classId) {
  return request.get("/exam/getTestPaperByChapterId", {
    params: {
      chapterId: chapterId,
      classId: classId,
    },
  });
}
export function getAllTestPaperByClassId(classId) {
  return request.get("/exam/getAllTestPaperByClassId", {
    params: {
      classId: classId,
    },
  });
}
export function getExamByExamId(examId) {
  return request.get("/exam/getExamByExamId", {
    params: {
      paperId: examId,
    },
  });
}
export function getExamHistoryByChapterAndUserId(userId, chapterId) {
  return request.get("/exam/getExamHistoryByChapterAndUserId", {
    params: {
      userId: userId,
      chapterId: chapterId,
    },
  });
}
export function getExamHistoryByUserId(userId) {
  return request.get("/exam/getExamHistoryByUserId", {
    params: {
      userId: userId,
    },
  });
}
