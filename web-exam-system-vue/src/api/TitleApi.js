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
export function deleteTitle(question_id) {
  return request.delete("/questions/deleteTitle", {
    params: {
      question_id: question_id,
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
