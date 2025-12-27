import request from "@/util/request";

export function getAllChapterId() {
  return request.get("/questions/getAllChapterId");
}
export function addTitle(data) {
  return request.post("/questions/addTitle", data);
}

export function getAllTitle(type) {
  return request.get("/questions/getAllTitleToType", {
    params: {
      type: type,
    },
  });
}
export function deleteTitle(questionId) {
  return request.delete("/questions/deleteTitle", {
    params: {
      questionId: questionId,
    },
  });
}
export function getAllQuestionByPaperId(paperId) {
  return request.get("/questions/getAllQuestionByPaperId", {
    params: {
      paperId: paperId,
    },
  });
}
export function getQuestionsByQuestionId(questionId) {
  return request.get("/questions/getQuestionsByQuestionId", {
    params: {
      questionId: questionId,
    },
  });
}
