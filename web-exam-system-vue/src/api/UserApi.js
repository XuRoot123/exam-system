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

export function showUserList() {
  return request.get("/user/getAllSchool");
}

export function showUserListPage(params) {
  return request.get("/user/getAllUserPage", {
    params: params,
  });
}
export function deleteUser(userId) {
  return request.delete("/user/deleteUser", {
    params: {
      userId,
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
export function deleteUndeterminedUser(userId) {
  return request.delete("/undeterminedUser/deleteUndeterminedUser", {
    params: {
      userId,
    },
  });
}
export function updatePassword(password, userId) {
  return request.put("/user/updatePassword", null, {
    params: {
      password: password,
      userId: userId,
    },
  });
}
export function getUserIdByClassId(classId) {
  return request.get("/user/getUserIdByClassId", {
    params: {
      classId: classId,
    },
  });
}
export function getRealNameByUserId(userId) {
  return request.get("/user/getRealNameByUserId", {
    params: {
      userId: userId,
    },
  });
}
