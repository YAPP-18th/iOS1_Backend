# Bucket buok

<div align="center">

![buok](https://user-images.githubusercontent.com/45676906/122062627-8af21000-ce2a-11eb-86cf-bc67d2e97ebb.png)

버킷리스트를 작성하며 나를 기록해 보세요!

미래를 ‘완성된 집’ 현재를 ‘설계도’라고 생각해 봅시다.

buok은 당신이 멋진 집을 완성할 수 있도록 도와드릴 거예요.

어떤 집을 짓고 싶은지 찾아보고, 구체적인 설계 계획을 할 수 있도록요!

생각날 때마다 미래 계획을 담을 버킷북을 만들어 보세요.

완성된 버킷북 중 나를 잘 나타내는 것을 골라 나만의 히스토리로 만들어보세요.

저희와 함께하다 보면 당신의 꿈이 이뤄져 있을 거예요.

</div>

---

<br>

## 📚 API Docs

- [API Description Link](https://github.com/YAPP-18th/iOS1_Backend/wiki)

<br>

## 📐프로젝트 전체 구성도

![스크린샷 2021-06-16 오후 2 35 32](https://user-images.githubusercontent.com/45676906/122163400-1bbeff00-ceb0-11eb-8ecc-180cf128aef8.png)

<br>

## 🛠 사용한 기술 스택

![TechStack](https://user-images.githubusercontent.com/45676906/122160183-6dfd2180-ceaa-11eb-8865-df08ec11d13f.png)

<br>

## 📂 Database Modeling

| ER Diagram |
|:---:|
|<img src="https://user-images.githubusercontent.com/69340410/122346901-6dd15480-cf84-11eb-9dc1-13858a35bc27.png" width="100%"/>|

<br>

## 프로젝트 주요 관심사

### 공통사항

- 지속적인 성능 개선
- 나쁜 냄새가 나는 코드에 대한 리팩토링

### 코드 컨벤션

- 프로젝트 전반적으로 `일관되게` 코드를 작성하려고 노력

### 성능 최적화
- DB 서버와의 통신을 최소화(당연한 이야기지만 N+1 쿼리를 지양하려고 노력)

<br>

## 프로젝트 하면서 겪은 과정들

**AccessToken, RefreshToken 과정 정리**
> Spring Security 없이 JWT 연동 <br> <br>
> [스프링부트 환경에서 JWT 연동하기](https://velog.io/@ayoung0073/springboot-jwt-without-springsecurity) - [아영](https://github.com/ayoung0073) <br>
> Redis Cache 적용하기 - [아영](https://github.com/ayoung0073)

<br>

**Session을 사용하지 않고 JWT를 사용한 이유는 무엇일까?**

> 정균(작성)

<br>

**JPA가 아니라 MyBatis를 사용한 이유**

> [프로젝트에서 JPA가 아닌 MyBatis를 선택한 이유는?](https://devlog-wjdrbs96.tistory.com/350) - [정균](https://github.com/wjdrbs96)

<br>

**FireBase Alarm Async 보내기**

> 정균(작성)

<br>

**Spring Logback으로 Slack, CloudWatch로 에러 로그 전송하기**

> [Spring Error log CloudWatch로 전송하기](https://devlog-wjdrbs96.tistory.com/329) - [정균](https://github.com/wjdrbs96) <br>
> [Spring Error log Slack으로 전송하는 법](https://devlog-wjdrbs96.tistory.com/327) - [정균](https://github.com/wjdrbs96) <br>
> [Spring Filter, Interceptor로 Request logging 하기]() - [정균](https://github.com/wjdrbs96)

<br>

**수 많은 에러들을 어떻게 관리하고 있을까?**

> [Error 클래스 상속 관계를 통한 ControllerAdvice, ExceptionHandler 처리하기](https://devlog-wjdrbs96.tistory.com/348) - [정균](https://github.com/wjdrbs96)

<br>

**이메일 인증까지의 과정**

> 비밀번호 재설정을 위한 이메일 전송부터 인증까지의 과정 <br> <br>
> [Thymeleaf을 이용한 이메일 전송하기](https://velog.io/@ayoung0073/SpringBoot-%ED%83%80%EC%9E%84%EB%A6%AC%ED%94%84%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%9D%B4%EB%A9%94%EC%9D%BC-%EC%A0%84%EC%86%A1) - [아영](https://github.com/ayoung0073) <br>
> [Redis 이용한 이메일 인증하기](https://velog.io/@ayoung0073/SpringBoot-%EC%9D%B4%EB%A9%94%EC%9D%BC-%EC%9D%B8%EC%A6%9D-%EA%B3%BC%EC%A0%95-Redis) - [아영](https://github.com/ayoung0073)

<br>

**비밀번호 암호화**

> [단방향 해시 함수와 BCrypt로 비밀번호 암호화하기](https://velog.io/@ayoung0073/Security-%EB%B9%84%EB%B0%80%EB%B2%88%ED%98%B8-%EB%8B%A8%EB%B0%A9%ED%96%A5-%EC%95%94%ED%98%B8%ED%99%94) - [아영](https://github.com/ayoung0073) <br>

<br>

**AOP로 중복로직 제거하기**

> 프로젝트에서 대부분 API에서 인가 체크가 필요했는데, AOP를 적용하여 중복 로직을 제거 <br> <br>
> [AOP를 사용하여 중복 로직 제거하기](https://devlog-wjdrbs96.tistory.com/344) - [정균](https://github.com/wjdrbs96)<br>
> [ThreadLocal로 사용자 정보 저장하기](https://velog.io/@ayoung0073/Java-ThreadLocal) - [아영](https://github.com/ayoung0073)
<br>

**yml 파일 prod, dev, local 환경 분리하기**

> [application.yml을 prod, dev, local로 분리시키기](https://devlog-wjdrbs96.tistory.com/343) - [정균](https://github.com/wjdrbs96) <br>

<br>

**이미지 리사이징**

> 버킷리스트 앱의 특성상 이미지 업로드 양이 많기 때문에 AWS Lambda를 이용하여 이미지 리사이징 기능을 추가 <br>  
> [Lambda로 Image Resize 하기](https://devlog-wjdrbs96.tistory.com/330?category=885022) - [정균](https://github.com/wjdrbs96)

<br>

**Jenkins와 CodeDeploy, Docker를 이용하여 CI/CD 구현하기**

> 무중단 자동화 배포 기능을 추가 <br> <br>
> [Jenkins CI 설정](https://velog.io/@ayoung0073/jenkins-ci) - [아영](https://github.com/ayoung0073) <br>
> [CodeDeploy, Load-Balancer 설정](https://devlog-wjdrbs96.tistory.com/345) - [정균](https://github.com/wjdrbs96)

<br>

## 브랜치 관리 전략 & 커밋 컨벤션

- [iOS 1팀 BackEnd Branch && Commit Convention](https://github.com/YAPP-18th/iOS1_Backend/blob/develop/docs/Project%20Branch%2C%20Commit%20Convetion.md)

<br>

## Contributors ✨

<table>
  <tr>
    <td align="center"><a href="https://github.com/wjdrbs96"><img src="https://avatars0.githubusercontent.com/wjdrbs96?v=4?s=100" width="100px;" alt=""/><br /><sub><b>Gyunny</b></sub></a><br /><a href="https://github.com/YAPP-18th/iOS1_Backend/commits?author=wjdrbs96" title="Code">💻</a></td>
    <td align="center"><a href="https://github.com/ayoung0073"><img src="https://avatars3.githubusercontent.com/ayoung0073?v=4?s=100" width="100px;" alt=""/><br /><sub><b>Ayoung</b></sub></a><br /><a href="https://github.com/YAPP-18th/iOS1_Backend/commits?author=ayoung0073" title="Code">💻</a></td>
  </tr>
</table>

[기여자 목록](https://github.com/YAPP-18th/iOS1_Backend/graphs/contributors) 을 확인하여 이 프로젝트에 대해 자세히 확인할 수 있습니다.

<br>

## 🔗 Repository Link

- [iOS](https://github.com/YAPP-18th/iOS1_Client)
