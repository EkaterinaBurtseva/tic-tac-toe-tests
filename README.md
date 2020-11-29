# Environment
* Java 8+
* Gradle

# Additional plugins 
* Lombok
* Rest-assured
* TestNG
* Allure
* AssertJ

# Execution
* locally via IDE
* using Gradle
```
gradle test
```

# How to generate report
Get test report after test execution
will be located here jsonplaceholder-tests/api-tests/build/reports/allure-report

```
 gradle allureReport 
```
 or 
 ```
 allure serve allure-results
```
Latest report:
![Screenshot 2020-11-29 at 19 34 53](Screenshot 2020-11-29 at 19.34.53.png)

![Screenshot 2020-11-29 at 19 35 27](Screenshot 2020-11-29 at 19.35.27.png)

# TODO
* add BDD support (e.g Cucumber)
* extend existing coverage
* add Spring
* add more detailed logging by @Slf4j

# Troubleshooting

- make sure that JDK at least java8
- clean gradle daemon
```
gradle --stop
```
- invalidate cache and restart
- try 
```
gradle build
```



