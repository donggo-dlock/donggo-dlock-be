# Log

---

## Logback
logback-spring.xml에 설정한 내용을 기반으로 로그를 남긴다.

- 로그 패턴 설정
- 로그 레벨 설정

## Logging Filter
LoggingFilter에서  HTTP 요청과 응답을 로그로 기록하는 필터<br>

### 주요 기능
HTTP 요청과 응답의 본문을 로그로 기록합니다.

- Request
  - 요청의 메서드, URI, 컨텐트 타입, 본문을 로그로 기록합니다.
  - multipart/form-data 타입의 요청은 본문을 로그로 남기지 않습니다.
- Response
  - 응답의 상태 코드, 컨텐트 타입, 본문을 로그로 기록합니다.
  - 응답 본문이 비어있지 않을 때만 로그를 남깁니다.
  - HTTP 상태 코드가 400 이상인 경우, 에러 로그를 남깁니다.
