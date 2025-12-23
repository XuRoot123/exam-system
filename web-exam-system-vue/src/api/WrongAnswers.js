import request from "@/util/request";

export function addWrongAnswers(data) {
  return request.post("/wrongAnswers/addWrongAnswers", data);
}
