import request from "@/util/request";

export function addWrongAnswers(data) {
  return request.post("/wrongAnswers/addWrongAnswers", data);
}
export function getWrongAnswersByUserIdAndExamId(userId, examId) {
  return request.get("/wrongAnswers/getWrongAnswersByUserIdAndExamId", {
    params: {
      userId,
      examId,
    },
  });
}
