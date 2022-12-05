## ⌚ Mapnote  Batch Repository 입니다

## 프로젝트 개요

### ⌛️ 프로젝트 기간

`2022/10/23` ~ `2022/12/5`

### 🛠 프로젝트 구조

```
1. 매일 특정 시간에 Quartz가 Batch Job을 실행

2. Batch Job은 다음 쿼리 작업을 진행함
- 사용자의 스케쥴 데이터
- 스케쥴의 알림 상태가 "알림 울림"이지만, 
아직 처리되지 않은 스케쥴의 알림 상태를 "알림 울리지 않음"으로 수정
```

### 🔧 사용 기술

![Java](https://img.shields.io/badge/-Java%2011-007396?style=plastic&logo=java&logoColor=white)
![SpringBoot](https://img.shields.io/badge/-Spring%20Boot%202.7.5-6DB33F?style=plastic&logo=Spring%20Boot&logoColor=white)
![SpringDataJPA](https://img.shields.io/badge/-Quartz%202.3.2-6496FF?style=plastic&logo=Spring&logoColor=white)
![SpringBatch](https://img.shields.io/badge/-Spring%20Batch%204.3.7-6D933F?style=plastic&logo=Spring&logoColor=white)
![SpringBatch](https://img.shields.io/badge/-MySQL%208.0.31-6D933F?style=plastic&logo=MySQL&logoColor=white)

### 📠 협업툴

![GitHub](https://img.shields.io/badge/-GitHub-181717?style=plastic&logo=GitHub&logoColor=white)
![Jira](https://img.shields.io/badge/-Jira-0052CC?style=plastic&logo=JiraSoftware&logoColor=white)
![Notion](https://img.shields.io/badge/-Notion-000000?style=plastic&logo=Notion&logoColor=white)

### 📃 Code Convention

- 코드 스타일
    - google code style


### 💻 개선 사항
- 스케줄러와 배치 작업의 lifecycle 이해 필요
- 현재 해피 케이스에 대한 구현만 완성된 상태. 개선 필요