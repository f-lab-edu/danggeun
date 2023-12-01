# danggeun
## 개요
- 당근마켓 클론 프로젝트
---

## 프로젝트 전체 구조
- 아키텍처
   
   ![당근아키텍처](https://github.com/f-lab-edu/danggeun/assets/75981576/02879aba-1d48-41c5-b871-48ffbfd5e1df)
---

## 사용 기술 및 환경
- Springboot 3.1.2
- Java17
- Gradle - Groovy 
- Naver Cloud Platform
  - Danggeun Server ( Nginx + Springboot(tomcat))
  - MySQL Server 
  - Redis ( 예정 ) 
---

## 코드 컨벤션 
- Naver code convetion 및 Naver check style 사용
    - https://naver.github.io/hackday-conventions-java/
    - https://bestinu.tistory.com/64
---

## 브랜치 전략
- Main ( product )
    - 배포용  
- Develop
    - Main -> Develop
    - 기능 구현 시 해당 브랜치에서 브랜치 생성하여 기능 구현 
- Feature
    - Develop -> Feature
    - 이슈 브랜치 생성 시 명명규칙 : feature/[#이슈번호]-[관련기능] ex) feature/#64-user
    - merge 이후에도 해당 브랜치 유지
    - develop에 merge할 경우   - -no-ff 옵션을 사용하여 feature 브랜치에서 merge했다는 이력을 생성
- Hotfix
    - 버그 수정 
    
- https://gmlwjd9405.github.io/2018/05/11/types-of-git-branch.html
---

## git merge 방식
- default : squash merge
    - ~~Create a merge commit~~
    ![merge](https://github.com/f-lab-edu/danggeun/assets/75981576/d4ed6e3c-593d-4d20-8f76-b809ac3fad43)

    - Squash and merge ( 깔끔한 커밋 브랜치 처리를 위해 해당 방식 사용 -> PR 및 Merge 시 관련 commit 모아서 입력 )
    ![sqush](https://github.com/f-lab-edu/danggeun/assets/75981576/cd263117-7f0a-4115-ad47-a78a395f6894)

    - Rebase and merge ( 커밋 내용이 많다면 해당 방식으로 커밋 내역 merge 브랜치로 그대로 커밋 처리 시 사용 )
    ![rebase](https://github.com/f-lab-edu/danggeun/assets/75981576/c73e49f2-bd55-451b-a5b5-07c3f0d270a0)
---

## CI/CD #47
- CI
    - GitAction 활용 하여 main/develop 브랜치에 push or pull request 이벤트 발생 시 Build & Test 가 되도록 반영
    - Build & Test 시 코드 커버리지를 활용하여 품질 향상
    - 코드 커버리지 기준치를 정하여 충족하지 못하는 경우 Build 실패 처리
    - 코드 커버리지에 대한 정보를 pull request 의 코멘트로 제공하여 공유
- CD #55
    - CD 의 경우 main/develop 브랜치에 push 이벤트 발생 시 Jar 파일을 서버에 전달 후 Deploy    
    - 현재의 배포의 경우에는 서버가 중단이 되었다가 다시 실행이 되므로 무중단 배포가 아님.
---

## 테스트 #54
- Jacoco
    - 코드 커버리지 적용 기준 ( 미 충족 시 build 실패 )
        - 전체 커버리지 10% 로 시작
            - 구문 커버리지 : 전체 구문 중 몇줄의 구문이 실행되었는지 측정 ( 20% )
            - 결정 커버리지 : 조건문의 분기가 모두 실행되었는지를 기준으로 측정 ( 20% )
    - Pull Request 작성 시 코드 커버리지에 대한 정보를 코멘트로 등록 한다.
        - Github Actions 를 활용하여 현재 코드에 대한 커버리지 정보를 공유한다.
---

## 성능 최적화
    - 
---

## 화면 설계
    -
---

## ERD
    -

---
