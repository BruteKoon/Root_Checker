# Root_Checker

Implement a technique to check Rooting In Android (RootBeear Clone Coding)

These are the Check Skiils 

Java checks

- checkRootManagementApps

pm(Package Manager)에서 특정 패키지(루트 관련 앱)이 설치 되어있는지 확인

- checkPotentiallyDangerousApps

- checkRootCloakingApps

- checkTestKeys

- checkForDangerousProps

Runtime.getRuntime의 exec를 통해서 외부 명령어(getprop)를 실행하여 해당 출력 값을 가져옴

가져온 출력 값에서 위험 Property가 있는지 

- checkForBusyBoxBinary

특정 경로 (/system, /system/bin, /data)에 busybox 바이너리가 있는지 이름을 비교하여 확인

- checkForSuBinary

특정 경로 (/system, /system/bin, /data)에 Su 바이너리가 있는지 이름을 비교하여 확인

- checkSuExists

Runtime.getRuntime의 exec를 통해서 외부 명령어 실행

외부 커맨드를 통해서 설치된 프로세스(su..)를 파악

- checkForRWSystem
