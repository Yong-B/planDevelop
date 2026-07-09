## 📌 API 명세서 (Plan API)
| 기능 | Method | URL | Request Body / Query Param | Response Status |
| :--- | :--- | :--- | :--- | :--- |
| **일정 생성** | `POST` | `/api/plans` | [Request Body](#1-일정-생성-request-body) | `201 Created` |
| **일정 전체 조회 (페이징)** | `GET` | `/api/plans` | [Query Parameter](#2-일정-전체-조회-query-parameter) | `200 OK` |
| **일정 선택 조회** | `GET` | `/api/plans/{id}` | *None* (Path Variable) | `200 OK` |
| **일정 수정** | `PATCH` | `/api/plans/{id}` | [Request Body](#4-일정-수정-request-body) | `200 OK` |
| **일정 삭제** | `DELETE` | `/api/plans/{id}` | [Request Body](#5-일정-삭제-request-body) | `204 No Content` |

## 📌 API 명세서 (User API)
| 기능 | Method | URL | Response Status |
| :--- | :--- | :--- | :--- |
| **회원가입** | `POST` | `/api/users` | `201 Created` |
| **로그인** | `POST` | `/api/users/login` | `200 OK` |
| **로그아웃** | `POST` | `/api/users/logout` | `204 No Content` |
| **유저 조회/수정/삭제** | `GET/PATCH/DELETE` | `/api/users/{id}` | `200 OK` / `204 No Content` |

## 📌 API 명세서 (Comment API)
| 기능 | Method | URL | Response Status |
| :--- | :--- | :--- | :--- |
| **댓글 생성** | `POST` | `/api/plans/{planId}/comments` | `201 Created` |
| **댓글 전체 조회** | `GET` | `/api/plans/{planId}/comments` | `200 OK` |


<img width="1477" height="1043" alt="image" src="https://github.com/user-attachments/assets/3c20854e-a9fe-4bcd-95c0-2a9e79786579" />


## 📌 ERD
<img width="334" height="700" alt="image" src="https://github.com/user-attachments/assets/e6790763-25db-4837-a1bd-8465b356dfd7" />

---

## 인증 및 예외처리

- **Cookie/Session 기반 로그인**: 이메일과 비밀번호로 로그인하며, 로그인 정보는 `HttpSession`에 저장됩니다. 인증이 필요한 API는 세션을 확인하여 로그인 여부를 검증합니다.
- **비밀번호 암호화**: BCrypt를 활용한 `PasswordEncoder`를 직접 구현하여, 회원가입 시 비밀번호를 평문이 아닌 해시값으로 저장합니다.
- **전역 예외처리**: `@RestControllerAdvice`와 `@ExceptionHandler`를 활용하여 Validation 오류, 존재하지 않는 리소스 조회, 비밀번호 불일치 등의 예외 상황을 일관된 형식(`ErrorResponse`)의 응답으로 클라이언트에 전달합니다.

---

## Annotation 정리
- `@RequestParam`: URL의 Query Parameter 값을 받을 때 사용합니다. 유저별 일정 조회, 페이지 번호/크기 조건 조회처럼 선택적인 검색 조건에 사용했습니다.
- `@PathVariable`: URL 경로에 포함된 값을 받을 때 사용합니다. 일정/유저/댓글 ID를 이용한 단건 조회, 수정, 삭제에 사용했습니다.
- `@RequestBody`: HTTP Body의 JSON 데이터를 DTO 객체로 변환할 때 사용합니다. 생성, 수정, 삭제, 로그인 요청 데이터를 받을 때 사용했습니다.
- `@Valid`: DTO에 선언된 Bean Validation 애노테이션(`@NotBlank`, `@Size` 등)을 검증 시점에 트리거합니다.
- `@RestControllerAdvice` / `@ExceptionHandler`: 전역 예외 처리를 위해 사용하며, Validation 예외와 커스텀 예외를 일관된 응답 형식으로 변환합니다.
