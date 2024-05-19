# 智慧社区综合实训项目 - smart-community-api

智慧社区综合实训项目旨在通过构建一个模拟的智慧社区应用来提升软件开发、系统集成及数据处理能力。本项目`smart-community-api`作为小程序组服务端，提供了与智慧社区小程序相关的后端API接口服务，通过此服务可以进行用户管理、设备控制、社区信息通讯等功能。

## 功能介绍

`smart-community-api`项目为智慧社区系统提供面向服务的接口（API），其主要功能包括但不限于：

- **用户管理**：支持用户的注册、登录、信息修改及权限控制等。
- **设备控制**：提供智能设备（如门禁、监控摄像头）的远程控制与状态监测功能。
- **社区信息通讯**：包括社区通知发布、居民间消息传递、以及紧急事件报告等。

## 技术栈

本项目基于 `Spring Boot` 构建，利用了以下技术和组件：

- **Spring Boot**：作为项目的基础框架，提供了简易的、快速的应用开发体验。
- **MyBatis-Plus**：作为数据访问层框架，用于简化数据库的操作。
- **MySQL**：作为项目的后端数据库，存储所有业务数据。
- **interceptor**：拦截异常请求。
- **JWT**：用于实现无状态的用户身份验证。
- **Swagger**：提供API文档自动生成功能，便于开发和调试。

## 环境要求

- **JDK 17** 或更高版本
- **Maven 3.6** 或更高版本
- **MySQL 8.0** 或更高版本

## 快速开始

1. 克隆项目到本地
   ```shell
   git clone https://github.com/Sliesu/smart-community-api.git
   ```
2. 进入项目目录
   ```shell
   cd smart-community-api
   ```
3. 使用Maven构建项目
   ```shell
   mvn clean install
   ```
4. 运行项目
   ```shell
   java -jar target/api-demo-0.0.1-SNAPSHOT.jar
   ```

在成功运行后，可通过浏览器访问`http://localhost:8080/doc.html` 查看并测试API文档。

## 项目结构

```plaintext
main
├── java
│   └── cn.edu.njuit.api - 项目模块
│       ├── common - 通用工具和配置
│       │   ├── cache - 缓存相关的实现
│       │   ├── config - 应用配置相关类
│       │   ├── constant - 常量定义
│       │   ├── exception - 自定义异常处理
│       │   ├── handler - 异常处理器
│       │   ├── interceptor - 拦截器相关
│       │   └── result - 结果封装
│       ├── controller - 控制器层处理请求
│       ├── convert - 数据转换类
│       ├── enums - 定义枚举类型
│       ├── mappermodel - ORM映射模型
│       ├── dto - 数据传输对象
│       ├── entityquery - 实体查询相关类
│       ├── vo - 视图对象
│       └── service - 服务层，包含业务逻辑
│           └── impl - 服务层实现类
│       └── utils - 实用工具类
│       └── ShareAppApiApplication - 主应用程序启动类
└── resources
    ├── log - 日志相关配置
    ├── mapper - MyBatis映射文件
    ├── application.properties - 应用程序属性配置
    ├── application.yml - 应用程序YAML配置
    └── application-dev.yml - 开发环境配置文件
```

## 配置说明

所有的应用配置均在`src/main/resources/application.properties`文件中，您需要根据实际环境修改数据库连接等配置信息。

## 开发者

小程序组：

- 物业端 - 杨欣怡【负责人】、周添峰、张誉丹
- 业主端 - 李曦晟【负责人】、黄浩杰、何伟康

---
推送提交时注意提交格式：
commit 的类型：

- feat: 新功能、新特性
- fix: 修改 bug
- perf: 更改代码，以提高性能（在不影响代码内部行为的前提下，对程序性能进行优化）
- refactor: 代码重构（重构，在不影响代码内部行为、功能下的代码修改）
- docs: 文档修改
- style: 代码格式修改, 注意不是 css 修改（例如分号修改）
- test: 测试用例新增、修改
- build: 影响项目构建或依赖项修改
- revert: 恢复上一次提交
- ci: 持续集成相关文件修改
- chore: 其他修改（不在上述类型中的修改）
- release: 发布新版本
- workflow: 工作流相关文件修改