# 블로그 검색 서비스 만들기

----

## 목차

1. 사용 기술
2. 애플리케이션 실행
3. 기능 지원 및 API 정보
4. 그외 고려한 점
---

## 1. 사용기술

+ JAVA 11
+ GRADLE 7.6.1
+ SPRING BOOT 활용
+ H2

+ 외부 라이브러리 사용
  + Webclient 테스트용
    ```
    testImplementation 'com.squareup.okhttp3:okhttp:4.0.1'
    testImplementation 'com.squareup.okhttp3:mockwebserver:4.0.1'
    ```
  + 보일러플레이트 제거용(롬복)
    ```
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    ```
---

## 2. 애플리케이션 실행

$ ./gradlew bootRun 
$ 

[소스 다운로드](https://github.com/gonghojin/api-call-test/releases/tag/v1.0)
> 다운로드 받은 파일 실행 시, java -jar gongdel-api.jar

---

## 3. 기능 지원 및 API 정보

### - 블로그 검색 : 키워드를 통해 블로그를 검색할 수 있다.

> 기본정보:
>> [GET] : localhost:8080/v1/search

> Request
>> Parameter

```
keyword	String	검색을 원하는 질의어	(Required))
sort	String	결과 문서 정렬 방식, accuracy(정확도순) 또는 recency(최신순), `기본 값 accuracy` (Optional)
page	Integer	결과 페이지 번호, 1~50 사이의 값, 기본 값 1	(Optional)
size	Integer	한 페이지에 보여질 문서 수, 1~50 사이의 값, 기본 값 10 (Optional)
```

> 예시) localhost:8080/v1/search?sort=recency&keyword=캠핑&page=1&size=1

> Reponse
>> message : 성공시, ""
> > 페이징 관련 결과값은 SPRING Pagination 사용

```
{
    "message": "",
    "data": {
        "content": [
            {
                "title": "세종 대평동 <b>캠핑</b>용품점 &#39;고릴라<b>캠핑</b>&#39;",
                "contents": "고릴라<b>캠핑</b> ✔ 세종 종합운동장로 29 JB빌딩 2층 ✔ 매일 10:00-20:00 세종 라운지엑시 카페 들린김에 같은 건물에 위치한 고릴라<b>캠핑</b> 구경했습니다. 따뜻한 봄이오면 <b>캠핑</b> 가고싶은 캠린이 부부거든요. 고릴라<b>캠핑</b>은 2월5일에 오픈해서 따끈따끈한 매장이었습니다. 카페를 먼저 가신다면 커피 마시고 2층 테라스를 통해서...",
                "url": "http://jjjj-a.tistory.com/58",
                "blogName": "째스토리",
                "thumbNail": "https://search1.kakaocdn.net/argon/130x130_85_c/7BmQHMu0r6T",
                "dateTime": "2023-03-05T01:36:12.000+09:00"
            }
        ],
        "pageable": {
            "sort": {
                "sorted": true,
                "empty": false,
                "unsorted": false
            },
            "pageNumber": 1,
            "pageSize": 1,
            "offset": 1,
            "paged": true,
            "unpaged": false
        },
        "last": false,
        "totalPages": 800,
        "totalElements": 800,
        "numberOfElements": 1,
        "first": false,
        "sort": {
            "sorted": true,
            "empty": false,
            "unsorted": false
        },
        "number": 1,
        "size": 1,
        "empty": false
    }
}
```

### - 인기 검색어 목록 : 사용자들이 많이 검색한 순서대로, 최대 10개의 검색 키워드를 제공합니다.

> 기본정보:
>> [GET] : localhost:8080/v1/ranks

> Request
>> Parameter

> 예시) localhost:8080/v1/ranks

> Reponse
>> message : 성공시, ""

```
{
    "message": "",
    "data": [
        {
            "keyword": "캠핑",
            "count": 6
        },
        {
            "keyword": "고구마",
            "count": 1
        }
        ...
    ]
}
```

### 4. 그외 고려한 점
+ 동시성 이슈가 발생할 수 있는 부분을 염두
    + 키워드 별로 검색 시, 조회 카운드 변경
        - 빈번한 충돌이 일어날 것이라고 가정하여, `Pessimistic`를 적용하여 구현(H2)
+ 카카오 블로그 검색 API 에 장애가 발생한 경우, 네이버 블로그 검색 API 를 통해 데이터 제공
