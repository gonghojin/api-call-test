# 블로그 검색 서비스 만들기

----

## 목차

1. 사용 기술
2. 애플리케이션 실행
3. 기능 지원 및 API 정보

---

## 1. 사용기술

+ JAVA 11
+ GRADLE 7.6.1
+ SPRING BOOT 활용
+ H2

---

## 2. 애플리케이션 실행

$ ./gradlew build  
$ java -jar ./build/libs/kakaobank.jar

> 다운로드 받은 파일 실행 시, java -jar ./kakaobank.jar

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
> > 페이징 관련 결과값은 SPRING 사용

```
{
    "message": "",
    "data": {
        "content": [
            {
                "title": "<b>캠핑</b>용 화로대, 가정용 숯불 화로 웨버 구입 후기",
                "contents": "기웃거리다, 알고리즘의 유혹은 남편이 알리에서 화로대를 질렀다. ​ 노는데 진심인 우리! 원래는 화목난로를 사려고 했으나... 아무리 생각해도 우리는 <b>캠핑</b> 장박형은 아니고 여기저기 떠도는 스타일이라, 화목난로는 좀 과하다고 생각이 들었고, 그래서 <b>캠핑</b>용 화로대를 구입했다. ​ 기존에도 화로대도 있는데, 또...",
                "url": "https://blog.naver.com/longmami/223051646777",
                "blogName": "이틀, 두가지 삶을 담아내다",
                "thumbNail": "https://search2.kakaocdn.net/argon/130x130_85_c/4NILkAi02BY",
                "dateTime": "2023-03-21T21:32:00.000+09:00"
            }
        ],
        "pageable": {
            "sort": {
                "sorted": false,
                "empty": true,
                "unsorted": true
            },
            "pageNumber": 1,
            "pageSize": 800,
            "offset": 800,
            "paged": true,
            "unpaged": false
        },
        "last": false,
        "totalPages": 6280,
        "totalElements": 5023380,
        "number": 1,
        "sort": {
            "sorted": false,
            "empty": true,
            "unsorted": true
        },
        "first": false,
        "size": 800,
        "numberOfElements": 1,
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