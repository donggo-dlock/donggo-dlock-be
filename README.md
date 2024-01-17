# spring-base

## 기본 설정

---
### plugins
```groovy
id 'java'
id 'org.springframework.boot' version '3.2.0'
id 'io.spring.dependency-management' version '1.1.4'
```
JDK 17 버전 사용

### dependencies
```dependencies
implementation 'org.springframework.boot:spring-boot-starter-web'
testImplementation 'org.springframework.boot:spring-boot-starter-test'

compileOnly("org.projectlombok:lombok")
annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
annotationProcessor("org.projectlombok:lombok")
```

---

## PROJECT
[project-concept.md](document%2Fproject-concept.md)

---
## Log
[log.md](document%2Flog.md)