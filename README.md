# 카카오뱅크 - [빅데이터분석] 데이터 엔지니어 코딩테스트

> 문항별 디렉토리를 구분하였으며 각 문항별 파일 구성은 다음과 같습니다.

## [Answer1](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/tree/master/answer1)
* [`/answer1`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/tree/master/answer1)
	* [`/answer1/answer1.sql`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer1/answer1.sql) - SQL문
	* [`/answer1/answer1_explain.json`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer1/answer1_explain.json) - SQL 실행계획 JSON Format
	* [`/answer1/answer1_explain.txt`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer1/answer1_explain.txt) - SQL 실행계획 Table Format
	* [`/answer1/answer1_result.txt`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer1/answer1_result.txt)  - SQL 실행 결과


<details><summary>결과보기</summary>

| 구분         | 월                     | 화                      | 수                     | 목                     | 금                     | 토                     | 일                  |
| ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ |
| Top1 메뉴    | 가이드 (9)             | 이체내역 (22)           | 세이프박스 (12)        | 이체내역 (5)           | 추천 (5)               | 모임통장 (10)          | 정기예금 (3)        |
| Top2 메뉴    | 세이프박스 (8)         | 추천 (20)               | 가이드 (10)            | 내카드 (4)             | 내정보 (4)             | 가이드 (7)             | 가이드 (2)          |
| Top3 메뉴    | 내신용정보 (6)         | 내카드 (19)             | 추천 (10)              | 추천 (4)               | 이체내역 (4)           | 내정보 (7)             | 내신용정보 (2)      |
| Top4 메뉴    | 내카드 (6)             | 세이프박스 (18)         | 정기예금 (9)           | 내신용정보 (3)         | 내카드 (3)             | 세이프박스 (6)         | 세이프박스 (2)      |
| Top5 메뉴    | 모임통장 (6)           | 가이드 (17)             | 내신용정보 (7)         | 세이프박스 (3)         | 모임통장 (3)           | 이체내역 (5)           | 이체내역 (2)        |
| Top6 메뉴    | 추천 (6)               | 내신용정보 (17)         | 이체내역 (7)           | 정기예금 (1)           | 세이프박스 (3)         | 추천 (5)               | 내정보 (1)          |
| Top7 메뉴    | 카드이용내역 (6)       | 정기예금 (17)           | 내정보 (4)             | 카드이용내역 (1)       | 내신용정보 (2)         | 카드이용내역 (5)       | 내카드 (1)          |
| Top8 메뉴    | 내정보 (5)             | 모임통장 (14)           | 내카드 (4)             | -                      | 가이드 (1)             | 내카드 (4)             | -                   |
| Top9 메뉴    | 이체내역 (5)           | 카드이용내역 (13)       | 모임통장 (4)           | -                      | 정기예금 (1)           | 내신용정보 (2)         | -                   |
| Top10 메뉴   | 정기예금 (5)           | 내정보 (11)             | 카드이용내역 (3)       | -                      | 카드이용내역 (1)       | 정기예금 (1)           | -                   |

</details>

---

## [Answer2](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/tree/master/answer2)
* [`/answer2`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/tree/master/answer2)
	* [`/answer2/answer2.sql`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer2/answer2.sql) - SQL문
	* [`/answer2/answer2_explain.json`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer2/answer2_explain.json) - SQL 실행계획 JSON Format
	* [`/answer2/answer2_explain.txt`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer2/answer2_explain.txt) - SQL 실행계획 Table Format
	* [`/answer2/answer2_result.txt`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer2/answer2_result.txt)  - SQL 실행 결과

<details><summary>결과보기</summary>

| 메뉴명             | 이전 메뉴명        | 접근 건수     | 비율(%)   |
| ------ | ------ | -----: | -----: |
| logout             | 가이드             |            16 |     13.55 |
| logout             | 이체내역           |            15 |     12.71 |
| logout             | 추천               |            15 |     12.71 |
| logout             | 내정보             |            14 |     11.86 |
| logout             | 모임통장           |            13 |     11.01 |
| logout             | 세이프박스         |            12 |     10.16 |
| logout             | 내카드             |            11 |      9.32 |
| logout             | 내신용정보         |            10 |      8.47 |
| logout             | 정기예금           |             6 |      5.08 |
| logout             | 카드이용내역       |             6 |      5.08 |
| 가이드             | login              |            14 |     30.43 |
| 가이드             | 세이프박스         |            10 |     21.73 |
| 가이드             | 추천               |             7 |     15.21 |
| 가이드             | 정기예금           |             4 |      8.69 |
| 가이드             | 내정보             |             3 |      6.52 |
| 가이드             | 내카드             |             2 |      4.34 |
| 가이드             | 모임통장           |             2 |      4.34 |
| 가이드             | 카드이용내역       |             2 |      4.34 |
| 가이드             | 내신용정보         |             1 |      2.17 |
| 가이드             | 이체내역           |             1 |      2.17 |
| 내신용정보         | login              |            10 |     25.64 |
| 내신용정보         | 세이프박스         |             5 |     12.82 |
| 내신용정보         | 내카드             |             4 |     10.25 |
| 내신용정보         | 모임통장           |             4 |     10.25 |
| 내신용정보         | 추천               |             4 |     10.25 |
| 내신용정보         | 가이드             |             3 |      7.69 |
| 내신용정보         | 내정보             |             3 |      7.69 |
| 내신용정보         | 이체내역           |             3 |      7.69 |
| 내신용정보         | 정기예금           |             3 |      7.69 |
| 내정보             | 세이프박스         |             7 |     21.87 |
| 내정보             | login              |             5 |     15.62 |
| 내정보             | 추천               |             5 |     15.62 |
| 내정보             | 카드이용내역       |             4 |     12.50 |
| 내정보             | 이체내역           |             3 |      9.37 |
| 내정보             | 내신용정보         |             2 |      6.25 |
| 내정보             | 내카드             |             2 |      6.25 |
| 내정보             | 모임통장           |             2 |      6.25 |
| 내정보             | 정기예금           |             2 |      6.25 |
| 내카드             | login              |            13 |     31.70 |
| 내카드             | 이체내역           |             7 |     17.07 |
| 내카드             | 정기예금           |             7 |     17.07 |
| 내카드             | 가이드             |             4 |      9.75 |
| 내카드             | 세이프박스         |             4 |      9.75 |
| 내카드             | 내신용정보         |             2 |      4.87 |
| 내카드             | 모임통장           |             2 |      4.87 |
| 내카드             | 내정보             |             1 |      2.43 |
| 내카드             | 카드이용내역       |             1 |      2.43 |
| 모임통장           | 세이프박스         |             6 |     16.21 |
| 모임통장           | 이체내역           |             5 |     13.51 |
| 모임통장           | 정기예금           |             5 |     13.51 |
| 모임통장           | login              |             4 |     10.81 |
| 모임통장           | 카드이용내역       |             4 |     10.81 |
| 모임통장           | 가이드             |             3 |      8.10 |
| 모임통장           | 내카드             |             3 |      8.10 |
| 모임통장           | 추천               |             3 |      8.10 |
| 모임통장           | 내신용정보         |             2 |      5.40 |
| 모임통장           | 내정보             |             2 |      5.40 |
| 세이프박스         | login              |            14 |     26.92 |
| 세이프박스         | 내카드             |             8 |     15.38 |
| 세이프박스         | 내신용정보         |             6 |     11.53 |
| 세이프박스         | 이체내역           |             6 |     11.53 |
| 세이프박스         | 카드이용내역       |             6 |     11.53 |
| 세이프박스         | 가이드             |             4 |      7.69 |
| 세이프박스         | 모임통장           |             3 |      5.76 |
| 세이프박스         | 내정보             |             2 |      3.84 |
| 세이프박스         | 정기예금           |             2 |      3.84 |
| 세이프박스         | 추천               |             1 |      1.92 |
| 이체내역           | login              |            16 |     32.00 |
| 이체내역           | 내신용정보         |            10 |     20.00 |
| 이체내역           | 추천               |             5 |     10.00 |
| 이체내역           | 가이드             |             4 |      8.00 |
| 이체내역           | 내카드             |             4 |      8.00 |
| 이체내역           | 내정보             |             3 |      6.00 |
| 이체내역           | 카드이용내역       |             3 |      6.00 |
| 이체내역           | 세이프박스         |             2 |      4.00 |
| 이체내역           | 정기예금           |             2 |      4.00 |
| 이체내역           | 모임통장           |             1 |      2.00 |
| 정기예금           | login              |            13 |     35.13 |
| 정기예금           | 가이드             |             7 |     18.91 |
| 정기예금           | 추천               |             7 |     18.91 |
| 정기예금           | 내신용정보         |             3 |      8.10 |
| 정기예금           | 내카드             |             3 |      8.10 |
| 정기예금           | 이체내역           |             2 |      5.40 |
| 정기예금           | 내정보             |             1 |      2.70 |
| 정기예금           | 모임통장           |             1 |      2.70 |
| 추천               | login              |            24 |     48.00 |
| 추천               | 세이프박스         |             5 |     10.00 |
| 추천               | 내카드             |             4 |      8.00 |
| 추천               | 가이드             |             3 |      6.00 |
| 추천               | 모임통장           |             3 |      6.00 |
| 추천               | 이체내역           |             3 |      6.00 |
| 추천               | 카드이용내역       |             3 |      6.00 |
| 추천               | 내신용정보         |             2 |      4.00 |
| 추천               | 정기예금           |             2 |      4.00 |
| 추천               | 내정보             |             1 |      2.00 |
| 카드이용내역       | 모임통장           |             6 |     20.68 |
| 카드이용내역       | login              |             5 |     17.24 |
| 카드이용내역       | 이체내역           |             5 |     17.24 |
| 카드이용내역       | 정기예금           |             4 |     13.79 |
| 카드이용내역       | 추천               |             3 |     10.34 |
| 카드이용내역       | 가이드             |             2 |      6.89 |
| 카드이용내역       | 내정보             |             2 |      6.89 |
| 카드이용내역       | 내신용정보         |             1 |      3.44 |
| 카드이용내역       | 세이프박스         |             1 |      3.44 |

</details>

---

## [Answer3](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/tree/master/answer3)
* [`answer3`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/tree/master/answer3)
	* [`/answer3/answer3.sql`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer3/answer3.sql) - SQL문
	* [`/answer3/answer3_explain.json`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer3/answer3_explain.json) - SQL 실행계획 JSON Format
	* [`/answer3/answer3_explain.txt`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer3/answer3_explain.txt) - SQL 실행계획 Table Format
	* [`/answer3/answer3_result.txt`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer3/answer3_result.txt)  - SQL 실행 결과

<details><summary>결과보기</summary>

| 사용자번호      | 성별   | 나이   | 지역명    | 이전지역명      | 이동통신사명       | 가입일    | 최빈메뉴     | 최근메뉴           |
| ----: | ----- | ----: | ----- | ----- | ----- | ----- | ----- | ----- |
| 001             | 여     | 17     | 포천      | 연천            | LG                 | 20190301  | 추천         | 카드이용내역       |
| 002             | 남     | 30     | 창원      | 김해            | 알뜰폰             | 20190311  | 정기예금     | 추천               |
| 003             | 여     | 45     | 천안      | 용인            | KT                 | 20190305  | 이체내역     | 이체내역           |
| 004             | 남     | 58     | 양주      | 서울            | -                  | 20190302  | 가이드       | 세이프박스         |

</details>

---

## [Answer4](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/tree/master/answer4)

> JSON에 정의한 Job테스크 내용을 기반으로 MySQL의 데이터를 HDFS로 저장 하는 어플리케이션을 구현하였으며 상세스펙은 다음과 같습니다.

### 상세스펙
* OpenJDK 1.8
* SpringBoot Framework 2.3.0
* MAVEN
* Apache Hadoop 3.3.0
* Apache Parquet 1.9.0
* MySQL 8.0.21
* lombok 1.18.4

---

### Job Task 정의 JSON Sample 

<details><summary>보기</summary>

```json
[
    {
        "name" : "menu_log_ETL_Job",
        "delay_min": 20,
        "period_hour": 24,
        "concurrency": 3,
        "delete_after_stored": true,
        "connector": "jdbc",
        "source": {
            "type": "mysql",
            "url": "jdbc:mysql://localhost:3306/kakaobank?characterEncoding=UTF-8&serverTimezone=UTC",
            "driver_class_name": "com.mysql.cj.jdbc.Driver",
            "username": "root",
            "password": "<password>",
            "table_name": "menu_log",
            "time_field": "log_tktm",
            "time_format": "yyyyMMddHHmmss",
            "begin_load_hour": 6,
            "end_load_hour": 24,
            "additional_query": "menu_nm <> 'logout'"
        },
        "target": {
            "type": "hdfs",
            "url": "hdfs://localhost:11000",
            "format": "parquet",
            "path": "/data/menu_log"
        }
    }
    
]

```
### JSON 정의에 따른 Object 클래스
* [TaskInfoVO.java](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer4/src/main/java/com/kakao/codingtest/taskinfo/vo/TaskInfoVO.java)
    * [SourceVO.java](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer4/src/main/java/com/kakao/codingtest/taskinfo/vo/SourceVO.java)
    * [TargetVO.java](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer4/src/main/java/com/kakao/codingtest/taskinfo/vo/TargetVO.java)

</details>

---
### 기능 리스트
* [Job Task정의 JSON](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer4/conf/task_info.json) 파일을 주기적(1분)으로 갱신하며 캐싱 -  [TaskInfoManager.java](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer4/src/main/java/com/kakao/codingtest/taskinfo/TaskInfoManager.java)
    * 캐싱하기 전 데이터에 대한 유효성 검사를 진행
* 매 10분 마다 각 `Job Task` 들이 동작해야 하는 시간인지를 체크하여
실제 Task를 작업하는 `Worker`를 실행시킴 - [TaskScheduler.java](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer4/src/main/java/com/kakao/codingtest/scheduler/TaskScheduler.java)
    * 작동하는 기준시각은 매일 0시를 기준으로 함.
    * `period_hour` - 동작하는 주기 시간. ex) 24시간, 48시간, 100시간 등
    * `delay_min` - ntp동기화 미적용 등에 의해 정확한 시간에 실행할경우 데이터 유실이 발생할수 있기 때문에 delay를 주는 옵션 
    * (`현재시간(timestamp)` + `timezone gap (9시간)` - `delay_min`) %
    `period_hour` <= `5분` 을 만족하면 실행.
* 쿼리의 부하를 고려하여 `period_hour` 범위의 쿼리를 여러개로 분할 -  [AWorker.java](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer4/src/main/java/com/kakao/codingtest/taskinfo/TaskInfoManager.java)
    * 데이터의 유입이 많아 지는 시간대를 지정 `begin_load_hour`, `end_load_hour`
    * 각각 6, 24 로 지정할경우
       * 06시 부터 00시까지는 각 쿼리를 20분으로 나누어 쿼리
       * 나머지는 3시간씩 나누어 쿼리.
       * 시간별 데이터분포가 극단적으로 차이날것으로 예상
* 쿼리의 추가 조건이 필요한 경우 `additional_query` 항목을 통해 추가 `where`절을 지정
* 쿼리 결과를 Parquet로 변환하여 HDFS에 저장하는 [JdbcWorker.java](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer4/src/main/java/com/kakao/codingtest/worker/JDBCWorker.java)는 ThreadPool을 이용하여 설정된 값(concurrency: int)에 따라 병렬로 실행.
* 쿼리 결과 ResultSet의 columns metadata를 추출하여 `List<Map<String, Object>>` 형태로 데이터 셋을 리턴([JdbcManager.java](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer4/src/main/java/com/kakao/codingtest/jdbc/JdbcManager.java)) 하며 이 결과를 가지고 Parquet로 변환 및 저장 ([Convert2Parquet.java](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer4/src/main/java/com/kakao/codingtest/target/Convert2Parquet.java))
* HFDS 저장시 추후 특정 시간 파일들만을 읽는 것을 고려하여 `${configure.path}/yyyyMMdd/HHmm_${random.5.char}.parquet` 형태로 저장되며 매 10분의 데이터들이 함께 모여 있음. `ex) 00시10분 ~ 00시19분 파일은 00시10분 파일에 함께 저장.`
    * Parquet 압축률 및 테스트 데이터 셋의 컬럼 갯수를 고려했을때에는 오히려 HDFS에 작은 파일이 많아질수 있어 실제 상황에 맞는 판단이 필요함.
    * 데이터의 저장목적에 따라 꼭 시간기준이 아닌 적절한 파티셔닝을 한다면 좋을 것 같음.

### 추가 구현 필요
* Apache Sqoop, Apache Spark Connector 사용에 대한 처리
* Export에 성공한 데이터(MySQL)에 대한 삭제.
* 현재 Parquet로 저장시 모두 String 문자열로 저장하고 있는데
  mysql 쿼리시 metadata를 통해 데이터의 타입을 확인하여 데이터 타입에 대한 schema 적용 필요.
* (Optional) Export해야 하는 데이터의 양을 먼저 측정하여 적정량의 데이터로 나누어 쿼리하는 기능

