import request from "@/util/request";

export function getAllChapterId() {
  return request.get("/questions/getAllChapterId");
}
export function addTitle(data) {
  return request.post("/questions/addTitle", data);
}
