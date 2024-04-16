# Toy Project
Post(Kotlin,JAVA) : https://github.com/sudosoo/TakeItEasy/

Event(Kotlin) : https://github.com/sudosoo/TakeItEasyEvent/

MemberInfo(Kotlin) : https://github.com/sudosoo/TakeItEasyAdmin/

## Dependencies  
### Environment
- Java Version: 17
- Build Tool: Gradle
- Dependency Management: Spring.dependency-management 

### Event Sourcing
- Kafka

### Log & Search
- ELK Stack + filebeat

### Cache
- Redis

### DataBase
- H2
- PostgreSQL
  
### Libraries
- Spring Boot 3.x
- Spring Boot Starter Data JPA
- Spring Boot Starter Web
- Spring Boot Starter Validation
- Spring Boot DevTools
- Spring Boot Starter Test
- Spring Boot starter batch
- Spring RestDocs MockMvc
- Mockk
- JUnit5


### Infra structure
----
<img src="/images/InfraStructureJPG.jpg" width="700">
<img src="/images/InfraStructureDetailPNG.png" width="700" >

----
### AOP 로깅 / 중복 요청 방지 / 실시간 알림 ( SSE + kafka)
#### [ AOP Flow ] 
<img src="/images/AOPFlow.png" width="700" >

[🚗 중복요청방지 Blog Visit (https://soobysu.tistory.com/125)

[🐰 실시간 알림 Blog Visit (https://soobysu.tistory.com/130)   

[🐻 AOP로깅 Code https://github.com/sudosoo/TakeItEasy/blob/bf9fd8d5bac5f8dc3a8aae7776e2314891aadabd/src/main/java/com/sudosoo/takeiteasy/aspect/LoggingAspect.java#L31-L71

---

### MSA 서버간 Kafka Evnet 비동기 통신
[📡 MSA 서버간 Kafka Evnet 비동기 통신 Blog Visit(https://soobysu.tistory.com/135)] 
https://github.com/sudosoo/TakeItEasy/blob/083b428dc7d47ea18e5e391037c1c5449bddd65d/src/main/java/com/sudosoo/takeiteasy/kafka/KafkaProducer.java#L38-L47

---
### 모듈 분리 JAVA -> JAVA + Kotlin (책임 분리) 
[🦔 계층 별 모듈 분리 (domain JAVA , 그 외 Kotlin) ]


---
### ELK + filebeat 를 활용한 로그적재  
<img src="/images/KibanaLog.png" width="700" >

[🐨 ELK Stack 로그적재 Blog Visit (https://soobysu.tistory.com/category/%EA%B0%9C-%EB%B0%9C/Infra?page=3)

---
### 젠킨스 + NginX + Docker CICD 무중단 배포  
<img src="/images/jenkinsStatus.png" width="700" >

[🐻‍❄️ 젠킨스 + NginX + Docker CICD 무중단 배포 Blog Visit](https://soobysu.tistory.com/122) 

---
### Redis 분산락 활용 동시성 제어 ( AOP 적용 )  
[🐮 Redis 분산락 활용 Blog Visit (https://soobysu.tistory.com/136)  
  
[🚗 Visit TakeItEasyEvent Repo](https://github.com/sudosoo/TakeItEasyEvent/blob/f88d80568c9d31b8903c136c18c5b4fa76179566/src/main/kotlin/com/sudoSoo/takeItEasyEvent/aop/lock/DistributeLockAop.kt#L21-L45)

 ---
### TDD 기반 테스트코드 작성 
[🐯 Test Code https://github.com/sudosoo/TakeItEasy/blob/b6244135e89647f393f643c0b79e5476b97f9423/src/test/java/com/sudosoo/takeiteasy/service/EventServiceImplTest.java#L76-L107

---
### CQRS 패턴 적용 Read 기능 분리
[🐮 Redis를 활용한 CQRS패턴 정용기 Blog Visit (https://soobysu.tistory.com/138) 
https://github.com/sudosoo/TakeItEasy/blob/1bc94d44e2264021ec72d97224468418a5500555/src/main/java/com/sudosoo/takeiteasy/redis/RedisServiceImpl.java#L22-L106  

---
### Spring Batch ( Bulk insert )  
#### 10만건 db insert [ 28분 -> 28초 성능개선 ]   
<img src="/images/defaultInSert.png" width="700" >
<img src="/images/batchInSert.png" width="700" >

[🐥 Spring Batch ( Bulk insert ) Blog Visit (https://soobysu.tistory.com/131)
https://github.com/sudosoo/TakeItEasy/blob/23a67017267ed0e9166b0fe0d1eeb87bff194c5d/src/main/java/com/sudosoo/takeiteasy/batch/BatchLauncherService.java#L34-L86

---
### 테이블 index 전략 ( 검색 최적화 )  
[🐷 테이블 index 전략 (검색 최적화) Blog Visit (https://soobysu.tistory.com/115)

---
### Jasypt 중요 정보 암호화  
[🐵 Jasypt 중요 정보 암호화 Blog Visit (https://soobysu.tistory.com/149)
https://github.com/sudosoo/TakeItEasy/blob/35e7723caa267473cf6497b8197fb55a230d60a4/boot/src/main/resources/application.properties#L12C1-L15C65


---
### TODO 
- Bulk Update 대량의 데이터 중 몇건의 데이터 수정하기 (완료)
- 



<details><summary>Project Structure tree
</summary>
  
```
📦 
├─ .ignore
├─ HELP.md
├─ README.md
├─ build.gradle
├─ gradle
│  └─ wrapper
│     ├─ gradle-wrapper.jar
│     └─ gradle-wrapper.properties
├─ gradlew
├─ gradlew.bat
├─ images
│  ├─ AOPFlow.png
│  ├─ InfraStructure.png
│  ├─ InfraStructureDetail.png
│  ├─ KibanaLog.png
│  └─ jenkinsStatus.png
├─ settings.gradle
└─ src
   ├─ main
   │  ├─ java
   │  │  └─ com
   │  │     └─ sudosoo
   │  │        └─ takeiteasy
   │  │           ├─ TakeItEasyApplication.java
   │  │           ├─ aspect
   │  │           │  ├─ DuplicateRequestAspect.java
   │  │           │  ├─ LoggingAspect.java
   │  │           │  ├─ NotifyAspect.java
   │  │           │  ├─ logging
   │  │           │  │  ├─ LogInfo.java
   │  │           │  │  └─ RequestApiInfo.java
   │  │           │  └─ notice
   │  │           │     └─ NotifyInfo.java
   │  │           ├─ common
   │  │           │  ├─ BaseEntity.java
   │  │           │  ├─ CustomDateTimeFormat.java
   │  │           │  ├─ CustomNotify.java
   │  │           │  ├─ DateTimeFormatValidator.java
   │  │           │  └─ NotLogging.java
   │  │           ├─ config
   │  │           │  ├─ AbstractElasticsearchConfiguration.java
   │  │           │  ├─ ElkConfig.java
   │  │           │  └─ KafkaConfig.java
   │  │           ├─ controller
   │  │           │  ├─ CategoryController.java
   │  │           │  ├─ CommnetController.java
   │  │           │  ├─ CouponController.java
   │  │           │  ├─ EventController.java
   │  │           │  ├─ HeartController.java
   │  │           │  ├─ MemberController.java
   │  │           │  ├─ MessageController.java
   │  │           │  ├─ NoticeController.java
   │  │           │  ├─ PostController.java
   │  │           │  ├─ ProfileController.java
   │  │           │  └─ TestController.java
   │  │           ├─ dto
   │  │           │  ├─ category
   │  │           │  │  ├─ CategoryResponseDto.java
   │  │           │  │  └─ CreateCategoryRequestDto.java
   │  │           │  ├─ comment
   │  │           │  │  ├─ CommentResposeDto.java
   │  │           │  │  ├─ CreateCommentRequestDto.java
   │  │           │  │  └─ UpdateCommentRequestDto.java
   │  │           │  ├─ coupon
   │  │           │  │  └─ CouponIssuanceRequestDto.java
   │  │           │  ├─ event
   │  │           │  │  ├─ CreateEventRequestDto.java
   │  │           │  │  └─ EventResponseDto.java
   │  │           │  ├─ heart
   │  │           │  │  ├─ CommentHeartRequestDto.java
   │  │           │  │  └─ PostHeartRequestDto.java
   │  │           │  ├─ member
   │  │           │  │  └─ CreateMemberRequestDto.java
   │  │           │  ├─ message
   │  │           │  │  ├─ MentionRequestDto.java
   │  │           │  │  └─ MessageSendRequestDto.java
   │  │           │  ├─ notice
   │  │           │  │  ├─ NoticeRequestDto.java
   │  │           │  │  └─ NoticeResponseDto.java
   │  │           │  └─ post
   │  │           │     ├─ CreatePostRequestDto.java
   │  │           │     ├─ PostDetailResponsetDto.java
   │  │           │     ├─ PostTitleDto.java
   │  │           │     ├─ SetCategoryByPostRequestDto.java
   │  │           │     └─ UpdatePostRequestDto.java
   │  │           ├─ entity
   │  │           │  ├─ Category.java
   │  │           │  ├─ Comment.java
   │  │           │  ├─ Coupon.java
   │  │           │  ├─ Event.java
   │  │           │  ├─ Heart.java
   │  │           │  ├─ HeartType.java
   │  │           │  ├─ Member.java
   │  │           │  ├─ Message.java
   │  │           │  ├─ MessageType.java
   │  │           │  ├─ Notice.java
   │  │           │  ├─ NoticeType.java
   │  │           │  └─ Post.java
   │  │           ├─ kafka
   │  │           │  ├─ KafkaConsumer.java
   │  │           │  └─ KafkaProducer.java
   │  │           ├─ repository
   │  │           │  ├─ CategoryRepository.java
   │  │           │  ├─ CommentRepository.java
   │  │           │  ├─ CouponRepository.java
   │  │           │  ├─ EmitterRepository.java
   │  │           │  ├─ EmitterRepositoryImpl.java
   │  │           │  ├─ EventRepository.java
   │  │           │  ├─ HeartRepository.java
   │  │           │  ├─ MemberRepository.java
   │  │           │  ├─ MessageRepository.java
   │  │           │  ├─ NoticeRepository.java
   │  │           │  └─ PostRepository.java
   │  │           └─ service
   │  │              ├─ CategoryService.java
   │  │              ├─ CategoryServiceImpl.java
   │  │              ├─ CommentService.java
   │  │              ├─ CommentServiceImpl.java
   │  │              ├─ CouponService.java
   │  │              ├─ CouponServiceImpl.java
   │  │              ├─ EventService.java
   │  │              ├─ EventServiceImpl.java
   │  │              ├─ HeartService.java
   │  │              ├─ HeartServiceImpl.java
   │  │              ├─ MemberService.java
   │  │              ├─ MemberServiceImpl.java
   │  │              ├─ MessageService.java
   │  │              ├─ MessageServiceImpl.java
   │  │              ├─ NoticeService.java
   │  │              ├─ NoticeServiceImpl.java
   │  │              ├─ PostService.java
   │  │              └─ PostServiceImpl.java
   │  └─ resources
   │     ├─ .gitkeep
   │     └─ config
   │        └─ .gitkeep
   └─ test
      └─ java
         └─ com
            └─ sudosoo
               └─ takeiteasy
                  ├─ TakeItEasyApplicationTests.java
                  └─ service
                     ├─ CategoryServiceImplTest.java
                     ├─ CommentServiceImplTest.java
                     ├─ CouponServiceImplTest.java
                     ├─ EventServiceImplTest.java
                     ├─ HeartServiceImplTest.java
                     ├─ MemberServiceImplTest.java
                     └─ PostServiceImplTest.java
```
</details>
