# Jetty의 WebApplicationInitializer 인식 테스트



예전에 스터디로 만들었던 게시판 프로젝트에 Jetty를 붙인 적이 있는데...

12.0.8로 버전업을 하면 이상하게 `WebApplicationInitializer` 클래스 설정을 읽지를 못했다.

그래서 그걸 인식을 못해서인지?

web.xml 를 찾으려다가 없다고 오류가 났었음..😅



## 스터디 게시판 프로젝트에서 나타나던 문제

```
java.lang.IllegalArgumentException: Resource must exist and cannot be a directory: file:///.../learning-spring-web-project-by-code/part-last/my-board/src/main/webapp/WEB-INF/web.xml
```

대상 프로젝트가 web.xml이 필요없는 `WebApplicationInitializer`을 구현한 설정 클래스가 있는 프로젝트인데...

위의 오류 로그 처럼 web.xml을 못찾는다고 나옴.  😂😂😂





## 예제 실행

이전 버전(`12.0.7`)으로 실행

```bash
mvn clean jetty:run
# 또는
mvn clean jetty:run -Pjetty_previous
```



최신 버전 (`12.0.12`)으로 실행

```bash
mvn clean jetty:run -Pjetty_latest
```



그런데 단순하게 예제로 뽑아낸 현재 프로젝트에서는 `WebApplicationInitializer` 설정이 문제없이 잘 인식됨. 왜 그럴까? ... 😂😂😂





## 해결

내가 착각을 했다. 😂

Jetty가 `12.0.7` 버전까지는 webApp 설정에 `descriptor`에 경로를 지정했을 때..

해당 경로에 `web.xml`이 존재하지 않더라도 그냥 넘어갔었는데..

```xml
<webApp>
  ...
  <descriptor>${web-xml-location}</descriptor>
  ...
```

`12.0.8` 버전 부터 확실하게 검사를 한다.

내가 이렇게 한 이유도 윈도우와 리눅스 환경의 `파일 업로드 설정` 분리 때문에 web.xml을 2가지로 분리했던 적이 있는데...

최종 프로젝트에서는 이미 이부분을 Java 설정으로 대체해놓은 상태라 필요없는 상황이였다.

해당 스터디 프로젝트의 jetty-ee8과 jetty-ee10 설정에서만 `descriptor` 수동 설정을 지웠음.





