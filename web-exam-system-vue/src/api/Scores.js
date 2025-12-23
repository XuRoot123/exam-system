import request from "@/util/request";
export function addScores(scores) {
  return request.post("/scores/addScores", scores);
}
export function getScoresByUserId(userId) {
  return request.get("/scores/getScoresByUserId", {
    params: {
      userId,
    },
  });
}
