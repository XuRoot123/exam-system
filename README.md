# 考试系统

## 快速开始
# 安装 Docker 后，一行命令启动所有
docker-compose up
### 1. 前置要求
- JDK 17+
- MySQL 8.0+
- Redis 6.0+

### 2. 安装步骤

#### 克隆项目
git clone https://github.com/xxx/xxx.git
cd exam-system

#### 初始化数据库
mysql -u root -p < sql/init.sql

#### 配置数据库密码
复制 application-example.yml 为 application.yml，修改密码

#### 启动 Redis
redis-server

#### 运行项目
mvn spring-boot:run

#### 访问
http://localhost:7000/swagger-ui.html

