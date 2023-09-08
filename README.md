# danggeun
1. 개요
    - 당근마켓 클론 프로젝트

2. 프로젝트 전체 구조
    - 

3. 사용 기술 및 환경
    - Springboot 3.1.2
    - Java17
    - Gradle - Groovy 
    - Naver Cloud Flatform
      - Application Server
      - Cloud DB for MySQL Server 
      - Cloud DB for Redis ( 예정 ) 

4. 코드 컨벤션 
    - Naver code convetion 및 Naver check style 사용
    - https://naver.github.io/hackday-conventions-java/
    - https://bestinu.tistory.com/64

5. 브랜치 전략
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

   - git merge 방식
     - default : squash merge
     - ~~Create a merge commit~~
     ![merge](https://github.com/f-lab-edu/danggeun/assets/75981576/d4ed6e3c-593d-4d20-8f76-b809ac3fad43)

     - Squash and merge ( 깔끔한 커밋 브랜치 처리를 위해 해당 방식 사용 -> PR 및 Merge 시 관련 commit 모아서 입력 )
     ![sqush](https://github.com/f-lab-edu/danggeun/assets/75981576/cd263117-7f0a-4115-ad47-a78a395f6894)

     - Rebase and merge ( 커밋 내용이 많다면 해당 방식으로 커밋 내역 merge 브랜치로 그대로 커밋 처리 시 사용 )
     ![rebase](https://github.com/f-lab-edu/danggeun/assets/75981576/c73e49f2-bd55-451b-a5b5-07c3f0d270a0)

6. CI/CD
    - 

7. 테스트
    - 

8. 성능 최적화
    - 

9. 화면 설계 ( 수정 예정 )
    ![‎당근마켓클론화면 _v1](https://github.com/f-lab-edu/danggeun/assets/75981576/1341e5a3-ab5e-4a2c-8915-07ce95e0108d)

11. ERD ( 수정 예정 )
    ![danggeunERD](https://github.com/f-lab-edu/danggeun/assets/75981576/4ba0b17b-c8c7-4162-9642-a4ed4f885891)

