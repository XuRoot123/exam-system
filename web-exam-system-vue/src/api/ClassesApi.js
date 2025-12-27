import request from "@/util/request";

export function getAllClass() {
  return request.get("/classes/getAllClasses");
}
export function getAllClassIdByTeacherId(teacherId) {
  return request.get("/classes/getAllClassIdByTeacherId", {
    params: {
      teacherId: teacherId,
    },
  });
}
