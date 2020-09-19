# 카카오뱅크 - [빅데이터분석] 데이터 엔지니어 코딩테스트

> 문항별 디렉토리를 구분하였으며 각 문항별 파일 구성은 다음과 같습니다.

## [Answer1](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/tree/master/answer1)
[`/answer1/answer1.sql`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer1/answer1.sql) - SQL문
[`/answer1/answer1_explain.json`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer1/answer1_explain.json) - SQL 실행계획 JSON Format
[`/answer1/answer1_explain.txt`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer1/answer1_explain.txt) - SQL 실행계획 Table Format
[`/answer1/answer1_result.txt`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer1/answer1_result.txt)  - SQL 실행 결과 JSON Format

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
---
## [Answer2](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/tree/master/answer2)
[`/answer2/answer2.sql`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer2/answer2.sql) - SQL문
[`/answer2/answer2_explain.json`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer2/answer2_explain.json) - SQL 실행계획 JSON Format
[`/answer2/answer2_explain.txt`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer2/answer2_explain.txt) - SQL 실행계획 Table Format
[`/answer2/answer2_result.txt`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer2/answer2_result.txt)  - SQL 실행 결과 JSON Format
| 메뉴명             | 이전 메뉴명        | 접근 건수     | 비율(%)   |
| ------ | ------ | ------ | ------ |
| 가이드             | 세이프박스         |            11 |     23.91 |
| 가이드             | 추천               |             9 |     19.56 |
| 가이드             | 정기예금           |             5 |     10.86 |
| 가이드             | 가이드             |             4 |      8.69 |
| 가이드             | 내정보             |             4 |      8.69 |
| 가이드             | 내신용정보         |             3 |      6.52 |
| 가이드             | 내카드             |             3 |      6.52 |
| 가이드             | 이체내역           |             3 |      6.52 |
| 가이드             | 모임통장           |             2 |      4.34 |
| 가이드             | 카드이용내역       |             2 |      4.34 |
| 내신용정보         | 세이프박스         |             7 |     17.94 |
| 내신용정보         | 내카드             |             6 |     15.38 |
| 내신용정보         | 추천               |             6 |     15.38 |
| 내신용정보         | 모임통장           |             5 |     12.82 |
| 내신용정보         | 가이드             |             4 |     10.25 |
| 내신용정보         | 내정보             |             4 |     10.25 |
| 내신용정보         | 이체내역           |             3 |      7.69 |
| 내신용정보         | 정기예금           |             3 |      7.69 |
| 내신용정보         | 내신용정보         |             1 |      2.56 |
| 내정보             | 세이프박스         |             7 |     21.87 |
| 내정보             | 추천               |             5 |     15.62 |
| 내정보             | 카드이용내역       |             5 |     15.62 |
| 내정보             | 내카드             |             3 |      9.37 |
| 내정보             | 모임통장           |             3 |      9.37 |
| 내정보             | 이체내역           |             3 |      9.37 |
| 내정보             | 가이드             |             2 |      6.25 |
| 내정보             | 내신용정보         |             2 |      6.25 |
| 내정보             | 정기예금           |             2 |      6.25 |
| 내카드             | 이체내역           |             8 |     19.51 |
| 내카드             | 정기예금           |             8 |     19.51 |
| 내카드             | 세이프박스         |             6 |     14.63 |
| 내카드             | 가이드             |             5 |     12.19 |
| 내카드             | 내정보             |             3 |      7.31 |
| 내카드             | 모임통장           |             3 |      7.31 |
| 내카드             | 내신용정보         |             2 |      4.87 |
| 내카드             | 내카드             |             2 |      4.87 |
| 내카드             | 추천               |             2 |      4.87 |
| 내카드             | 카드이용내역       |             2 |      4.87 |
| 모임통장           | 세이프박스         |             8 |     21.62 |
| 모임통장           | 이체내역           |             6 |     16.21 |
| 모임통장           | 정기예금           |             5 |     13.51 |
| 모임통장           | 카드이용내역       |             4 |     10.81 |
| 모임통장           | 가이드             |             3 |      8.10 |
| 모임통장           | 내카드             |             3 |      8.10 |
| 모임통장           | 추천               |             3 |      8.10 |
| 모임통장           | 내신용정보         |             2 |      5.40 |
| 모임통장           | 내정보             |             2 |      5.40 |
| 모임통장           | 모임통장           |             1 |      2.70 |
| 세이프박스         | 내카드             |            10 |     19.60 |
| 세이프박스         | 이체내역           |             8 |     15.68 |
| 세이프박스         | 내신용정보         |             7 |     13.72 |
| 세이프박스         | 카드이용내역       |             7 |     13.72 |
| 세이프박스         | 가이드             |             6 |     11.76 |
| 세이프박스         | 모임통장           |             5 |      9.80 |
| 세이프박스         | 내정보             |             3 |      5.88 |
| 세이프박스         | 정기예금           |             3 |      5.88 |
| 세이프박스         | 세이프박스         |             1 |      1.96 |
| 세이프박스         | 추천               |             1 |      1.96 |
| 이체내역           | 내신용정보         |            12 |     24.00 |
| 이체내역           | 내정보             |             8 |     16.00 |
| 이체내역           | 추천               |             8 |     16.00 |
| 이체내역           | 내카드             |             5 |     10.00 |
| 이체내역           | 가이드             |             4 |      8.00 |
| 이체내역           | 세이프박스         |             3 |      6.00 |
| 이체내역           | 이체내역           |             3 |      6.00 |
| 이체내역           | 정기예금           |             3 |      6.00 |
| 이체내역           | 카드이용내역       |             3 |      6.00 |
| 이체내역           | 모임통장           |             1 |      2.00 |
| 정기예금           | 추천               |             9 |     24.32 |
| 정기예금           | 가이드             |             8 |     21.62 |
| 정기예금           | 내신용정보         |             6 |     16.21 |
| 정기예금           | 모임통장           |             5 |     13.51 |
| 정기예금           | 내카드             |             3 |      8.10 |
| 정기예금           | 이체내역           |             2 |      5.40 |
| 정기예금           | 내정보             |             1 |      2.70 |
| 정기예금           | 세이프박스         |             1 |      2.70 |
| 정기예금           | 정기예금           |             1 |      2.70 |
| 정기예금           | 카드이용내역       |             1 |      2.70 |
| 추천               | 가이드             |             8 |     16.00 |
| 추천               | 이체내역           |             8 |     16.00 |
| 추천               | 세이프박스         |             7 |     14.00 |
| 추천               | 내카드             |             6 |     12.00 |
| 추천               | 모임통장           |             5 |     10.00 |
| 추천               | 내정보             |             4 |      8.00 |
| 추천               | 카드이용내역       |             4 |      8.00 |
| 추천               | 정기예금           |             3 |      6.00 |
| 추천               | 추천               |             3 |      6.00 |
| 추천               | 내신용정보         |             2 |      4.00 |
| 카드이용내역       | 모임통장           |             7 |     24.13 |
| 카드이용내역       | 이체내역           |             5 |     17.24 |
| 카드이용내역       | 정기예금           |             4 |     13.79 |
| 카드이용내역       | 추천               |             4 |     13.79 |
| 카드이용내역       | 내정보             |             3 |     10.34 |
| 카드이용내역       | 가이드             |             2 |      6.89 |
| 카드이용내역       | 내신용정보         |             2 |      6.89 |
| 카드이용내역       | 세이프박스         |             1 |      3.44 |
| 카드이용내역       | 카드이용내역       |             1 |      3.44 |
---
## [Answer3](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/tree/master/answer3)
[`/answer3/answer3.sql`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer3/answer3.sql) - SQL문
[`/answer3/answer3_explain.json`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer3/answer3_explain.json) - SQL 실행계획 JSON Format
[`/answer3/answer3_explain.txt`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer3/answer3_explain.txt) - SQL 실행계획 Table Format
[`/answer3/answer3_result.txt`](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/blob/master/answer3/answer3_result.txt)  - SQL 실행 결과 JSON Format

| 사용자번호      | 성별   | 나이   | 지역명    | 이전지역명      | 이동통신사명       | 가입일    | 최빈메뉴     | 최근메뉴           |
| ----- | ----- | ----- | ----- | ----- | ----- | ----- | ----- | ----- |
| 001             | 여     | 17     | 포천      | 연천            | LG                 | 20190301  | 추천         | 카드이용내역       |
| 002             | 남     | 30     | 창원      | 김해            | 알뜰폰             | 20190311  | 정기예금     | 추천               |
| 003             | 여     | 45     | 천안      | 용인            | KT                 | 20190305  | 이체내역     | 이체내역           |
| 004             | 남     | 58     | 양주      | 서울            | -                  | 20190302  | 가이드       | 세이프박스         |
---
## [Answer4](https://github.com/nowonjh/k_a_k_a_o_bank_coding_test/tree/master/answer4)
