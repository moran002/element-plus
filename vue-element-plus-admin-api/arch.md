# 架构文档

## 项目概述
本项目是一个基于 Spring Boot 3 的后端 API 服务，用于支持 Vue Element Plus 管理系统的后端功能。项目采用了现代化的技术栈，包括 MyBatis-Plus、Sa-Token、Hutool 等工具库。

## 技术栈
- **核心框架**: Spring Boot 3.5.5
- **数据库**: MySQL 8.0.28
- **ORM**: MyBatis-Plus 3.5.14
- **权限认证**: Sa-Token 1.42.0
- **工具库**: Hutool 5.8.40
- **日志**: Logback
- **构建工具**: Maven

## 项目结构
```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── moran
│   │   │           ├── MoranApiApplication.java
│   │   │           ├── conf
│   │   │           ├── controller
│   │   │           ├── convert
│   │   │           ├── enums
│   │   │           ├── mapper
│   │   │           ├── model
│   │   │           ├── service
│   │   │           └── utils
│   │   └── resources
│   │       ├── application.yml
│   │       ├── application-local.yml
│   │       ├── application-prod.yml
│   │       ├── logback-spring.xml
│   │       ├── mappers
│   │       ├── spring
│   │       └── templates
│   └── test
│       └── java
│           └── com
│               └── moran
│                   └── MoranApiApplicationTests.java
└── pom.xml
```

## 核心模块
1. **启动类**: `MoranApiApplication.java`
   - 主入口，配置了 Spring Boot 启动项和 MyBatis 扫描路径。

2. **配置文件**: `application.yml`
   - 配置了服务器端口、MyBatis-Plus 全局设置、Sa-Token 权限认证等。

3. **日志配置**: `logback-spring.xml`
   - 定义了日志输出格式、文件存储路径和日志级别。

4. **数据库访问**: `mapper` 和 `model` 目录
   - 使用 MyBatis-Plus 进行数据库操作，支持逻辑删除和自动填充字段。

5. **权限认证**: `Sa-Token`
   - 集成了 Sa-Token 进行权限认证和会话管理。

6. **工具类**: `utils` 目录
   - 包含常用的工具方法，如日期处理、字符串处理等。

## 部署与运行
1. **本地运行**:
   - 配置 `application-local.yml` 中的数据库连接信息。
   - 运行 `MoranApiApplication` 主类。

2. **生产环境**:
   - 配置 `application-prod.yml` 中的日志路径和数据库连接信息。
   - 使用 Maven 打包后部署到服务器。