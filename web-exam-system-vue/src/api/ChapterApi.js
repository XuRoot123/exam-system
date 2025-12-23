import request from "@/util/request";

export function getAllChapter() {
  return request.get("/chapter/getAllChapter");
}
