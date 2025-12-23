import request from "@/util/request";
export function addStudentAnswer(data) {
  return request.post("/studentAnswers/addStudentAnswers", data);
}

export function checkAnswerRecord(studentId, examId) {
  return request.get("/studentAnswers/checkAnswerRecord", {
    params: {
      studentId,
      examId,
    },
  });
}
