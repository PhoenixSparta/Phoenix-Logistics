# Phoenix-Logistics

[![CI](https://github.com/PhoenixSparta/Phoenix-Logistics/actions/workflows/ci.yml/badge.svg)](https://github.com/PhoenixSparta/Phoenix-Logistics/actions/workflows/ci.yml)

# Project Set-Up

## Git Hook
프리 커밋 설정으로 커밋 전 코드 포맷 검사

```
$ git config core.hookspath .githooks
```

## IntelliJ IDEA

- 테스트 설정

```
// Gradle Build and run with IntelliJ IDEA
Build, Execution, Deployment > Build Tools > Gradle > Run tests using > IntelliJ IDEA	
```

# Lint

수동으로 코드 포맷 수정

```bash
./gradlew format
```

# Convention

## Package Path

com.phoenix.logistics.{domain}

## Git Branch Strategy

- GitLab Flow
    
    
    | branch | des |
    | --- | --- |
    | main | 배포용 브랜치 |
    | release | 버전 출시용 |
    | develop | 개발 머지용 브랜치(from main branch) |
    | feature | 기능 개발용 브랜치(from develop branch) |
    | hotfix | 버그 처리 브랜치(from main branch) |
- 신규 기능 개발 브랜치 생성 시 반드시 develop에서 진행
- develop에 머지 후 featrue 브랜치 제거
- feature 하위 브랜치 네이밍
    - feat/{이슈번호}-신규기능이름
        
        EX) feat/2-회원가입
        

## Commit Message Convention

:깃모지: {commit message} 간단한 설명 (#{이슈번호})← 콜론 다음에 띄어쓰기 한번 

-상세작업내용1

-상세작업내용2

- 깃모지

| 아이콘 | 코드 | 설명 | 원문 |
| --- | --- | --- | --- |
| 🎨 | :art: | 코드의 구조/형태 개선 | Improve structure / format of the code. |
| ⚡️ | :zap: | 성능 개선 | Improve performance. |
| 🔥 | :fire: | 코드/파일 삭제 | Remove code or files. |
| 🐛 | :bug: | 버그 수정 | Fix a bug. |
| 🚑 | :ambulance: | 긴급 수정 | Critical hotfix. |
| ✨ | :sparkles: | 새 기능 | Introduce new features. |
| 📝 | :memo: | 문서 추가/수정 | Add or update documentation. |
| 💄 | :lipstick: | UI/스타일 파일 추가/수정 | Add or update the UI and style files. |
| 🎉 | :tada: | 프로젝트 시작 | Begin a project. |
| ✅ | :white_check_mark: | 테스트 추가/수정 | Add or update tests. |
| 🔒 | :lock: | 보안 이슈 수정 | Fix security issues. |
| 🔖 | :bookmark: | 릴리즈/버전 태그 | Release / Version tags. |
| 💚 | :green_heart: | CI 빌드 수정 | Fix CI Build. |
| 📌 | :pushpin: | 특정 버전 의존성 고정 | Pin dependencies to specific versions. |
| 👷 | :construction_worker: | CI 빌드 시스템 추가/수정 | Add or update CI build system. |
| 📈 | :chart_with_upwards_trend: | 분석, 추적 코드 추가/수정 | Add or update analytics or track code. |
| ♻️ | :recycle: | 코드 리팩토링 | Refactor code. |
| ➕ | :heavy_plus_sign: | 의존성 추가 | Add a dependency. |
| ➖ | :heavy_minus_sign: | 의존성 제거 | Remove a dependency. |
| 🔧 | :wrench: | 구성 파일 추가/삭제 | Add or update configuration files. |
| 🔨 | :hammer: | 개발 스크립트 추가/수정 | Add or update development scripts. |
| 🌐 | :globe_with_meridians: | 국제화/현지화 | Internationalization and localization. |
| 💩 | :poop: | 똥싼 코드 | Write bad code that needs to be improved. |
| ⏪ | :rewind: | 변경 내용 되돌리기 | Revert changes. |
| 🔀 | :twisted_rightwards_arrows: | 브랜치 합병 | Merge branches. |
| 📦 | :package: | 컴파일된 파일 추가/수정 | Add or update compiled files or packages. |
| 👽 | :alien: | 외부 API 변화로 인한 수정 | Update code due to external API changes. |
| 🚚 | :truck: | 리소스 이동, 이름 변경 | Move or rename resources 
(e.g.: files paths routes). |
| 📄 | :page_facing_up: | 라이센스 추가/수정 | Add or update license. |
| 💡 | :bulb: | 주석 추가/수정 | Add or update comments in source code. |
| 🍻 | :beers: | 술 취해서 쓴 코드 | Write code drunkenly. |
| 🗃 | :card_file_box: | 데이버베이스 관련 수정 | Perform database related changes. |
| 🔊 | :loud_sound: | 로그 추가/수정 | Add or update logs. |
| 🙈 | :see_no_evil: | .gitignore 추가/수정 | Add or update a .gitignore file. |

# 팀원 역할 분담

- **길태환 (팀장)**
    - **담당 역할**: 프로젝트 총괄, 상품 및 주문 서비스 개발, 팀 협업 관리
    - **주요 기여**: 상품 및 주문 서비스의 설계 및 구현, 팀 회의 주재 및 일정 관리, 프로젝트 진행 상황 점검 및 조율
    - **엔드포인트** (공통 /api/v1)
    <details>
    <summary>Product</summary>

    - **POST /products** : 상품 등록
    - **GET /products/{productId}** : 상품 단건 조회
    - **GET /products** : 상품 검색
    - **PUT /products/{productId}** : 상품 수정
    - **DELETE /products/{productId}** : 상품 삭제

    </details>

    <details>
    <summary>Order</summary>

    - **POST /orders** : 주문 요청
    - **GET /orders/{orderId}** : 주문 단건 조회
    - **GET /orders** : 주문 검색
    - **PUT /orders/{orderId}** : 주문 수정
    - **DELETE /orders/{orderId}** : 주문 삭제

    </details>

- **천현우 (부팀장)**
    - **담당 역할**: 허브 및 배송, 업체 서비스 개발, 테스트 환경 구축
    - **주요 기여**: 허브와 배송 로직 구현, 업체 관리 기능 개발, 테스트 시나리오 작성 및 검증, API 통합 테스트
    - **엔드포인트** (공통 /api/v1)
    <details>
    <summary>Hub</summary>

    - **POST /admin/hubs** : 허브 등록
    - **GET /hubs/{hubUuid}** : 허브 조회
    - **GET /search/hubs** : 허브 검색
    - **PUT /admin/hubs/{hubId}** : 허브 수정
    - **DELETE /admin/hubs/{hubId}** : 허브 삭제
    - **GET /hubs/link** : 허브 간 배송 경로 조회
    - **POST /admin/hubs/managers** : 허브 관리자 등록
    - **GET /admin/hubs/managers/{managerUuid}** : 허브 관리자 조회
    - **PUT /admin/hub/managers/{managerUuid}** : 허브 관리자 수정
    - **DELETE /admin/hub/managers/{managerUuid}** : 허브 관리자 삭제

    </details>

    <details>
    <summary>Delivery</summary>

    - **POST /delivery** : 배송 요청
    - **GET /delivery/{deliveryUuid}** : 배송 조회
    - **GET /search/delivery** : 내 배송 검색
    - **GET /search/staffs/delivery** : 내 담당 배송 검색
    - **GET /search/delivery/logs** : 배송 로그 검색
    - **PUT /delivery/{deliveryUuid}** : 배송 수정
    - **PUT /staffs/hubs/delivery/{deliveryUuid}** : 허브 배송 담당자 지정
    - **PUT /staffs/company/delivery/{deliveryUuid}** : 업체 배송 담당자 지정
    - **PUT /staff/delivery/{deliveryUuid}/log** : 배송 경로 기록
    - **DELETE /delivery/{deliveryUuid}** : 배송 취소

    </details>

    <details>
    <summary>Company</summary>

    - **POST /company** : 업체 등록
    - **GET /company/manufacturer/{manufacturerUuid}** : 생산 업체 조회
    - **GET /company/vendor/{vendorUuid}** : 공급 업체 조회
    - **GET /search/company** : 업체 검색
    - **PUT /company/manufacturer/{manufacturerUuid}** : 생산 업체 수정
    - **PUT /company/vendor/{vendorUuid}** : 공급 업체 수정
    - **DELETE /company/manufacturer/{manufacturerUuid}** : 생산 업체 삭제
    - **DELETE /company/vendor/{vendorUuid}** : 공급 업체 삭제

    </details>
- **전석배 (팀원)**
    - **담당 역할**: 유저 인증, 권한 관리, 모니터링 시스템 구축
    - **주요 기여**: Spring Security를 통한 권한 계층 구조 설계 및 구현, Prometheus 및 Grafana를 이용한 모니터링 환경 구성, 인가 로직 최적화 및 성능 개선
    - **엔드포인트** (공통 /api/v1)
    <details>
    <summary>Auth</summary>

    - **POST /auth/signup** : 회원 가입
    - **GET /auth/login** : 로그인
    </details>

  <details>
  <summary>User</summary>

  - **GET /users** : 사용자 정보 조회
  - **DELETE /users** : 사용자 삭제
  - **GET /admin/users** : 모든 사용자 조회 (관리자 전용)
  - **PATCH /admin/users/{user_id}/role** : 사용자 권한 변경 (관리자 전용)
  - **GET /admin/users/search** : 사용자 검색 (관리자 전용)
  </details>
    

# 서비스 구성 및 실행 방법
```
root
│
├── clients - 각 서비스 사이의 통신 수단입니다.
│   ├── client-example - 클라이언트 예시
│   └── client-user    - user Feign 
│
├── core - 실질적인 api 와 domain이 구성되는 곳입니다.
│   ├── core-api          - core 예시
│   ├── core-apigateway   - api gateway 구성
│   ├── core-auth         - 인증 구성
│   ├── core-hub-api      - 허브 구성
│   ├── core-product-api  - 상품 구성
│   └── core-user         - 유저 구성
│
├── storage - DB와의 연결성 및 Entity를 구성하는 곳입니다.
│   ├── db-core           - db 예시
│   ├── db-core-hub       - hub db
│   ├── db-core-order     - order db
│   ├── db-core-product   - product db
│   ├── db-core-redis     - redis db
│   └── db-core-user      - user db
│
├── support - 공통적으로 사용되는 모듈입니다.
│   ├── logging                 - logging 모듈
│   ├── monitoring              - prometheus & grafana
│   ├── support-api             - core-api에 적용 모듈
│   ├── support-authentication  - 인가 모듈(시큐리티 포함)
│   ├── support-domain          - core-domain에 적용 모듈
│   └── support-storage         - db에 공통 적용 모듈
│
└── tests - 공통 테스트 모듈
    └── api-docs - spring rest docs를 생성하는 모듈
```
실행 명령어
```
./gradlew :core:core-api:bootRun
```

# 프로젝트 목적/상세

### 프로젝트 목적

이 프로젝트는 물류 관리 시스템(Logistics Management System)을 구축하여, 허브 간의 효율적인 물류 운송, 주문 관리, 상품 관리, 그리고 사용자 권한 관리를 통해 물류 프로세스를 최적화하는 것을 목표로 합니다. 이 시스템은 다양한 역할의 사용자가 물류 관련 업무를 원활하게 수행할 수 있도록 지원하며, 관리자, 허브 매니저, 배송 직원 등의 사용자가 각자의 역할에 맞는 기능을 사용할 수 있도록 설계되었습니다.

### 프로젝트 상세

1. **사용자 인증 및 권한 관리 (Auth & User)**
  - **기능**: 회원 가입, 로그인, 사용자 정보 조회 및 관리
  - **목적**: 사용자의 권한을 관리하고, 각 역할에 따라 접근할 수 있는 기능을 제어하여 보안을 강화합니다.
  - **상세**:
    - 사용자 계정 생성 및 인증 (JWT 기반)
    - 관리자 전용 기능 제공 (사용자 관리, 권한 변경)

2. **상품 관리 (Product)**
  - **기능**: 상품 등록, 수정, 삭제, 검색
  - **목적**: 다양한 상품을 등록하고 관리할 수 있으며, 검색을 통해 상품 정보를 쉽게 확인할 수 있습니다.
  - **상세**:
    - 상품 등록, 수정, 삭제 기능 제공
    - 상품 목록 및 상세 조회, 검색 기능 구현

3. **주문 관리 (Order)**
  - **기능**: 주문 요청, 주문 수정 및 삭제, 주문 조회
  - **목적**: 고객의 주문을 효율적으로 관리하고, 주문 상태를 실시간으로 확인할 수 있도록 합니다.
  - **상세**:
    - 주문 생성 및 수정
    - 주문 내역 조회 및 관리

4. **허브 관리 (Hub)**
  - **기능**: 허브 등록, 수정, 삭제, 허브 간 배송 경로 관리
  - **목적**: 물류 허브 간의 연결을 통해 배송 경로를 최적화하고, 허브 간의 원활한 물류 흐름을 지원합니다.
  - **상세**:
    - 허브 등록 및 관리
    - 허브 간 배송 경로 최적화 및 경로 조회 기능

5. **배송 관리 (Delivery)**
  - **기능**: 배송 요청, 배송 상태 조회 및 수정, 배송 경로 기록
  - **목적**: 각 허브와 배송 직원이 배송 상태를 실시간으로 관리할 수 있게 하여, 배송의 투명성과 신속성을 보장합니다.
  - **상세**:
    - 배송 요청 및 담당자 지정
    - 배송 상태 관리 및 로그 기록

6. **업체 관리 (Company)**
  - **기능**: 생산 업체 및 공급 업체 등록 및 관리
  - **목적**: 다양한 업체의 정보를 관리하고, 생산 및 공급과 관련된 업무를 지원합니다.
  - **상세**:
    - 업체 등록, 수정 및 삭제
    - 업체 정보 조회 및 검색

7. 프로젝트 흐름도
   ![image (2).png](..%2F..%2F..%2F..%2F..%2FDownloads%2Fimage%20%282%29.png)
- auth 서버에 회원가입 / 로그인 요청
- 로그인 후 받은 토큰(JWT)를 이용해 api 요청
- 해당 토큰의 payload만 gateway에서 해독 후 redis에 정보 확인
- redis에 정보가 없을 경우 auth 서버에서 토큰 validation 수행
- 해당 값을 gateway에 반환 후 redis에 저장
- redis에서 검증 후 api 수행
- 각 api 에 redis에서 검증한 userId 및 role 제공
- 인가 모듈에서 userId, role을 가지고 인증 객체 생성
     

# ERD
https://dbdocs.io/sjtxm0320/TEAM4_CH4_PROJECT?view=relationships

# 기술 스택
- <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white"> : 주 언어로 사용되었습니다.
- <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white"> : 프로젝트의 백엔드 개발에 사용되었습니다
- - <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> : 프로젝트의 메인 프레임워크로 사용되었습니다.
- - <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white"> : 보안 기능을 구현하는 데 사용되었습니다.
- - <img src="https://img.shields.io/badge/Spring Cloud-6DB33F?style=for-the-badge&logo=Spring cloud&logoColor=white"> : 클라우드 환경에서의 마이크로서비스 아키텍처를 구현하는 데 사용되었습니다.
- <img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white"> : 데이터베이스 ORM(Object-Relational Mapping) 프레임워크로 사용되었습니다.
- <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=PostgreSQL&logoColor=white"> : 관계형 데이터베이스로 사용되었습니다.
- <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white"> : 캐싱 사용을 위해 사용되었습니다.
