import request from "@/util/request";

export function login(user) {
  return request.post("/user/login", user);
}

export function enroll(user) {
  return request.post("/user/enroll", user);
}

export function enrollSchoolUser(user) {
  return request.post("/user/enrollSchoolUser", user);
}

export function getAllClass() {
  return request.get("/classes/getAllClasses");
}

export function showUserList() {
  return request.get("/user/getAllSchool");
}

export function showUserListPage(params) {
  return request.get("/user/getAllUserPage", {
    params: params,
  });
}
export function deleteUser(user_id) {
  return request.delete("/user/deleteUser", {
    params: {
      user_id,
    },
  });
}
export function getUserByClassId(params) {
  return request.get("/user/getUserByClassId", {
    params: params,
  });
}
export function getAllUndeterminedUserPage(params) {
  return request.get("/undeterminedUser/teacher/getAllUndeterminedUserPage", {
    params: params,
  });
}
export function deleteUndeterminedUser(user_id) {
  return request.delete("/undeterminedUser/deleteUndeterminedUser", {
    params: {
      user_id,
    },
  });
}
