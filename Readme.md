

## 外部工具

1. swagger 3.0 (open api 3.0) 不需要太多额外的标注，自动生成文档 ,[参考](https://www.baeldung.com/spring-rest-openapi-documentation)
[swagger 3](https://swagger.io/docs/specification/)
http://localhost:8084/testDemo/swagger-ui/index.html, http://localhost:8084/testDemo/v2/api-docs

## 关于配置
1. 【配置】是一个广泛的概念，在规范化时建议细化一下分类，例如：

2. 【运营配置】：与业务运营相关的配置，可能需要免重启更新，更新频率由业务决定
3. 【系统配置】：技术相关的配置，例如系统RUNTIME的环境、参数等配置，对某些应用可能需要重启进程才能生效
4. 【敏感信息配置】：上述文档提到的需要加密的配置信息，例如系统账号密码、用户私隐信息等

##测试