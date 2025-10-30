import request from "@/util/request";

export function addRandomTestPaper(data) {
  return request.post("/exam/addRandomTestPaper", data);
}
