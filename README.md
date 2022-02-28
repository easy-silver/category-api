# category-api
온라인 쇼핑몰의 상품 카테고리 API 구현

## Spec
- Java 11
- Spring Boot 2.5.9
- Gradle
- Spring Data JPA
- H2 Database
- Lombok
- Validation
- Swagger

## How to
### install
```bash
$ git clone https://github.com/easy-silver/category-api.git
```

### Build
```bash
$ ./gradlew build
```

### Run
```bash
$ java -jar ./build/libs/category-api-0.0.1-SNAPSHOT.jar
```

## Document
http://localhost:8080/swagger-ui.html

## Note
- 카테고리 목록 조회 시 메모리 캐시를 사용하도록 구현했습니다.<br>(카테고리 등록/수정/삭제 시에는 기존 캐시를 제거하도록 처리했습니다.)
- 초기 데이터 생성을 위해 data.sql 파일에 insert SQL을 작성했습니다.
