
# Newbie Tix Service

基于 Spring Boot 框架开发的演出票务平台后端服务，为移动端应用和管理后台提供统一的 API 接口支持。

## 技术栈

- 后端框架：Spring Boot
- 数据库：MySQL
- 缓存：Redis
- 消息队列：RabbitMQ
- 部署方式：Serverless

## 功能特性

### 演出管理

- 演出信息：支持演出基础信息的增删改查
- 场次管理：管理演出场次、时间、地点信息
- 票档管理：维护票档价格、库存等信息
- 库存控制：实时票务库存管理和锁定机制

### 订单系统

- 订单创建：支持在线选座购票
- 订单管理：订单状态流转和管理
- 支付集成：对接微信支付等支付渠道
- 退款处理：支持订单退款和票务退回

### 用户中心

- 用户管理：用户注册、登录、信息管理
- 权限控制：基于角色的权限管理系统
- 数据统计：用户行为和交易数据分析

## 项目结构

```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.newbie.tix
│   │   │       ├── config        // 配置类
│   │   │       ├── controller    // 控制器
│   │   │       ├── service       // 业务逻辑
│   │   │       ├── repository    // 数据访问
│   │   │       ├── model         // 数据模型
│   │   │       └── util          // 工具类
│   │   └── resources
│   │       ├── application.yml   // 应用配置
│   │       └── mapper            // MyBatis映射
└── pom.xml                       // 项目依赖
```

## 开发环境

- JDK >= 17
- Maven >= 3.8
- MySQL >= 8.0
- Redis >= 6.0

## 部署说明

本项目采用 Serverless 方式部署在阿里云函数计算平台，具体部署步骤：

```bash
# 安装 Serverless Devs CLI
npm install @serverless-devs/s -g

# 部署到函数计算
s deploy
```

## 相关项目

- [newbie-tix-app](https://github.com/null-object-0000/newbie-tix-app)：移动端用户应用
- [newbie-tix-manager](https://github.com/null-object-0000/newbie-tix-manager)：后台管理系统

## 系统架构

```
+-------------------+     +-------------------+     +-------------------+
|   Newbie Tix APP  |     | Newbie Tix Admin |     |  Newbie Tix API  |
| (用户移动端应用)  |     |    (管理后台)    |     |   (后端服务)     |
|  uni-app + Vue 3  |     |      Vue 3       |     |   Spring Boot    |
+--------+----------+     +--------+----------+     +--------+----------+
         |                        |                          |
         |                        |                          |
         +------------------------+--------------------------+
                            API 接口调用
```

## 开发进度

- [x] 基础框架搭建
- [x] 用户认证系统
- [x] 演出管理接口
- [x] 订单管理系统
- [ ] 支付系统集成
- [ ] 数据统计分析
- [ ] 性能优化
