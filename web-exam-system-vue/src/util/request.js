// axios的通用配置
import axios from "axios";

const INSTANCE = axios.create({
  baseURL: "http://localhost:7000",
  timeout: 10000,
});

// 配置请求拦截器
INSTANCE.interceptors.request.use(
  function (config) {
    // 可以在这里添加 token 到 headers 中
    const token = window.sessionStorage.getItem("token");
    if (token) {
      config.headers.Authorization = token;
    }
    return config;
  },
  function (error) {
    console.log("请求拦截...");
    return Promise.reject(error);
  }
);

// 配置响应拦截器
INSTANCE.interceptors.response.use(
  function (response) {
    console.log("响应拦截...");
    let data = response.data;
    if (data.code === 0) {
      return data;
    }
    return Promise.reject(data);
  },
  function (error) {
    return Promise.reject(error);
  }
);

export default INSTANCE;
