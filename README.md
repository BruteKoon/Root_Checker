# Root_Checker

Implement a technique to check Rooting In Android (RootBeear Clone Coding)

These are the Check Skiils 

Java checks

- checkRootManagementApps

- checkPotentiallyDangerousApps

- checkRootCloakingApps

- checkTestKeys

- checkForDangerousProps

- checkForBusyBoxBinary

- checkForSuBinary

특정 경로 (/system, /system/bin, /data)에 Su 바이너리가 있는지 이름을 비교하여 확인

- checkSuExists

Runtime.getRuntime의 exec를 통해서 외부 명령어 실행

외부 커맨드를 통해서 설치된 프로세스(su..)를 파악

- checkForRWSystem
