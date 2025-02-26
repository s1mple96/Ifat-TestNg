# Ifat-TestNg 接口自动化项目

## 项目概述
Ifat-TestNg 是一个基于 TestNG 框架，使用 Groovy 语言开发的接口自动化测试项目。该项目采用了 TestNG 的数据驱动模式进行测试，利用 HttpRunner 发送请求，并集成了 Allure 测试报告，方便测试结果的展示和分析。

## 项目结构
本项目主要包含以下几个模块：

### 1. 配置文件
- `pom.xml`：Maven 项目的配置文件，管理项目的依赖和插件。
- `log4j.properties`：日志配置文件，用于配置日志的输出格式和级别。
- `testng.xml`：TestNG 的配置文件，用于定义测试套件和测试用例的执行顺序。

### 2. 测试代码
- `src/main/java`：主要的 Java 和 Groovy 源代码目录，包含测试类和测试方法。
- `src/test/java`：测试代码目录，包含单元测试和集成测试代码。

### 3. 资源文件
- `src/main/resources`：项目的资源文件目录，包含测试数据、配置文件等。

## 技术栈
### 1. TestNG
TestNG 是一个功能强大的测试框架，支持数据驱动测试、并行测试等多种测试模式。本项目使用 TestNG 来组织和执行测试用例。

### 2. Groovy
Groovy 是一种基于 JVM 的动态语言，与 Java 语法兼容。使用 Groovy 可以提高测试代码的编写效率，减少代码量。

### 3. HttpRunner
HttpRunner 是一个开源的接口自动化测试工具，支持多种请求方法（如 GET、POST 等）和数据格式（如 JSON、XML 等）。本项目使用 HttpRunner 来发送 HTTP 请求并验证响应结果。

### 4. Allure
Allure 是一个美观、强大的测试报告框架，支持多种测试框架（如 TestNG、JUnit 等）。本项目集成了 Allure，用于生成详细的测试报告，方便测试人员和开发人员查看测试结果。

## 项目配置
### 1. 依赖管理
项目使用 Maven 进行依赖管理，主要的依赖包括：
- TestNG：用于测试框架的核心功能。
- HttpRunner：用于发送 HTTP 请求。
- Allure：用于生成测试报告。
- Groovy：用于编写测试代码。

### 2. 插件配置
项目使用了以下 Maven 插件：
- `maven-compiler-plugin`：用于编译 Java 和 Groovy 代码。
- `maven-surefire-plugin`：用于执行 TestNG 测试用例。
- `gmavenplus-plugin`：用于支持 Groovy 代码的编译和执行。

## 测试数据驱动
本项目采用了 TestNG 的数据驱动模式进行测试，通过注解和数据提供者的方式，将测试数据与测试用例分离，提高了测试代码的可维护性和可扩展性。

### 示例代码
```groovy
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

class DataDrivenTest {

    @DataProvider(name = "testData")
    Object[][] dataProviderMethod() {
        return [
                ["data1", "value1"],
                ["data2", "value2"]
        ]
    }

    @Test(dataProvider = "testData")
    void testMethod(String data, String value) {
        // 测试逻辑
        println "Testing with data: $data and value: $value"
    }
}
