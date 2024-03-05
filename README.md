# Toy Project

Post : https://github.com/sudosoo/TakeItEasy/

Event : https://github.com/sudosoo/TakeItEasyEvent/

MemberInfo : https://github.com/sudosoo/TakeItEasyAdmin/

### Infra structure
----

<img src="/images/InfraStructure.jpg"  >
<img src="/images/InfraStructureDetail.png" width="700" >

----
### AOP 로깅 / 중복 요청 방지 / 실시간 알림 ( SSE+ kafka)
#### [ AOP Flow ] 
<img src="/images/AOPFlow.png" width="700" >

- [🚗 중복요청방지 Link(https://soobysu.tistory.com/125)]

- [🐰 실시간 알림 Link(https://soobysu.tistory.com/130)]   

- [🐻 AOP로깅 Code https://github.com/sudosoo/TakeItEasy/blob/bf9fd8d5bac5f8dc3a8aae7776e2314891aadabd/src/main/java/com/sudosoo/takeiteasy/aspect/LoggingAspect.java#L31-L71  

### MSA 서버간 Kafka Evnet 비동기 통신
- [📡 MSA 서버간 Kafka Evnet 비동기 통신 Link(https://soobysu.tistory.com/135)   
Code   
https://github.com/sudosoo/TakeItEasy/blob/083b428dc7d47ea18e5e391037c1c5449bddd65d/src/main/java/com/sudosoo/takeiteasy/kafka/KafkaProducer.java#L38-L47
  

### ELK + filebeat 를 활용한 로그적재 
<img src="/images/KibanaLog.png" width="700" >

- [🐨 ELK Stack 로그적재 Link(https://soobysu.tistory.com/category/%EA%B0%9C-%EB%B0%9C/Infra?page=3)] 

### 젠킨스 + NginX + Docker CICD 무중단 배포
<img src="/images/jenkinsStatus.png" width="700" >

- [🐻‍❄️ 젠킨스 + NginX + Docker CICD 무중단 배포 Link(https://soobysu.tistory.com/122)] 

### Redis 분산락 활용 동시성 제어 ( AOP 적용 )
- [🐮 Redis 분산락 활용 Link(https://soobysu.tistory.com/136)]

Code

https://github.com/sudosoo/TakeItEasyEvent/blob/f88d80568c9d31b8903c136c18c5b4fa76179566/src/main/kotlin/com/sudoSoo/takeItEasyEvent/aop/lock/DistributeLockAop.kt#L21-L45

### TDD 기반 테스트코드 작성 

- [🐯 Test Code (https://github.com/sudosoo/TakeItEasy/blob/b6244135e89647f393f643c0b79e5476b97f9423/src/test/java/com/sudosoo/takeiteasy/service/EventServiceImplTest.java#L76-L107)]

### Spring Batch ( Bulk insert ) 
#### 10만건 db insert [ 28분 -> 28초 성능개선 ]  
<img src="/images/defaultInSert.png" width="700" >
<img src="/images/batchInSert.png" width="700" >

- [🐥 Spring Batch ( Bulk insert )  Link(https://soobysu.tistory.com/131)
(https://github.com/sudosoo/TakeItEasy/blob/23a67017267ed0e9166b0fe0d1eeb87bff194c5d/src/main/java/com/sudosoo/takeiteasy/batch/BatchLauncherService.java#L34-L86)]

### 테이블 index 전략 ( 검색 최적화 )

- [🐷 테이블 index 전략 (검색 최적화) Link(https://soobysu.tistory.com/115) ]



### TODO 
- CQRS 패턴 적용기 (Read 캐시서버 만들기)


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
