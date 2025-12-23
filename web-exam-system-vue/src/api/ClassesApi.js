import request from "@/util/request";

export function getAllClass() {
  return request.get("/classes/getAllClasses");
}
