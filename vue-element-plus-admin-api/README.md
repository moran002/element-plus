# api

#### 软件架构
1. springboot3.4.0
2. mysql8.0.20 
3. redis 
4. mybatis-plus
5. jdk17 
6. sa-token
7. biz-log

#### 部署相关
1. docker 目录添加了服务器利用docker-compose 部署文件的相应配置文件
2. docker命令: docker-compose up 启动服务 docker-compose down 关闭服务

#### 功能相关
1.数据库与实体类映射 Json类型转换过程中,
@TableName(value = "sys_user", autoResultMap = true) 中必须添加autoResultMap = true
字段    @TableField(typeHandler = JacksonTypeHandler.class)
