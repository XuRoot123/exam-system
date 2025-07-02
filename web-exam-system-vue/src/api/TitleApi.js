import request from "@/util/request";

export function getAllChapterId() {
  return request.get("/questions/getAllChapterId");
}
