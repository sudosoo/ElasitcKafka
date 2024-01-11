# Toy Project

### AOP 로깅 / 중복 요청 방지 / 실시간 알림 ( SSE+ kafka)
#### [ AOP Flow ] 
<img src="/images/AOPFlow.png" width="700" >

- [🚗 중복요청방지 Link](https://soobysu.tistory.com/125)

- [🐰 실시간 알림 Link](https://soobysu.tistory.com/130)   

- [🐻 AOP로깅 Code](https://github.com/sudosoo/TakeItEasy/blob/bf9fd8d5bac5f8dc3a8aae7776e2314891aadabd/src/main/java/com/sudosoo/takeiteasy/aspect/LoggingAspect.java#L31-L71)   

### 선착순 쿠폰 발행 ( 동시성 문제 )
- [🎮 멀티스레드 환경 동시성 문제 해결 Link](https://soobysu.tistory.com/127) 

### ELK + filebeat 를 활용한 로그적재 
<img src="/images/KibanaLog.png" width="700" >

- [🐨 ELK Stack 로그적재 Link](https://soobysu.tistory.com/category/%EA%B0%9C-%EB%B0%9C/Infra?page=3) 

### 젠킨스 + NginX + Docker CICD 무중단 배포
<img src="/images/jenkinsStatus.png" width="700" >

- [🐻‍❄️ 멀티스레드 환경 동시성 문제 해결 Link](https://soobysu.tistory.com/122) 

### TDD 기반 테스트코드 작성 

- [🐯 Test Code Link](https://github.com/sudosoo/TakeItEasy/blob/b6244135e89647f393f643c0b79e5476b97f9423/src/test/java/com/sudosoo/takeiteasy/service/EventServiceImplTest.java#L76-L107) 

### TODO
- 모듈 분리 (멀티모듈) 
- Kafka 이벤트 스트림을 활용한 RestAPI 통신
- Redis 세션스토리지 만들기 (진행중)

<details><summary>Infra Structure
</summary>
  <img src="/images/InfraStructure.png" width="700" >
  <img src="/images/InfraStructureDetail.png" width="700" >
</details>
 

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
