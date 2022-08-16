개발 가이드
===
**각 레이어별 상세 가이드는, subproject 하위의 각 레이어별 README 파일 참조**

[개발 가이드 컨플루언스 링크](https://lguplus-msa-dev.atlassian.net/wiki/spaces/LGUPLUSMSA/pages/1028784196)

## 레이어드 아키텍처 개요
+ 레이어드(계층형) 아키텍처(Layered Architecture)는 수행 역할에 따라 각 레이어(계층)*를 명시적으로 분리
+ 각 레이어 역할에 맞는 소스코드 및 의존성 구성 목표
+ Domain 레이어는 비즈니스 업무로직이 구현되는 핵심 레이어이므로, 다른 레이어의 영향을 최소한으로 받도록 구성

![img.png](img.png "레이어드 아키텍처 구성")
___
## Domain / Infrastructure 레이어 간 호출 흐름 및 의존성
### 호출 흐름 및 문제점
+ 자연스러운 호출 흐름은 Domain →  Infrastructure 레이어 방향으로 발생
  + 레이어 구성도에서 빨간 실선 화살표(호출 흐름) 확인
  + Domain 레이어가 Infrastructure 레이어에 의존
+ 문제점
  + Infrastructure 레이어의 변경이 Domain 레이어에 영향을 미침
  + 비즈니스 로직이 있는 Domain 레이어가 외부의 변화로부터 보호받지 못함
  + 전체 레이어 간 의존성 순환 발생으로 ADP(의존성 비순환 원칙) 위반
  + Presentation → Application → Domain → Infrastructure → Presentation → ...
  + ADP : “의존성 그래프에 순환이 있어서는 안된다.”
___
### 레이어 간 의존 역전 적용
+ 위 문제점 해결 방안
  + Domain 레이어는 다른 레이어를 의존하지 않도록 변경
  + Infrastructure → Domain 레이어 방향으로 의존성 방향 변경
  + 호출 흐름을 제어하기 위해 의존 역전 기법 사용 (의존성 방향 제어)
  + 레이어 구성도에서 파란 점선 화살표(레이어 의존성) 확인

+ 인터페이스를 통한 의존 역전을 적용하여 레이어 간 의존성 방향 제어
  + 레이어 구성도에서 Domain / Infrastructure 레이어 간 보라 점선 화살표(인터페이스-구현체 관계) 확인
  + 인터페이스를 통한 명세 정의는 비즈니스 로직의 필요에 따라 Domain 레이어가 책임
  + 인터페이스의 구현은 Infrastructure 레이어에서 책임
  + 의존성 방향을 변경함으로 의존성 순환이 제거되어 ADP(의존성 비순환 원칙) 준수
 
+ 레이어 간 DIP(의존 역전 원칙)를 적용하여 OCP(개방 폐쇄 원칙) 준수
  + Domain 레이어 서비스에서는 동일 레이어의 인터페이스에만 의존
    + 애플리케이션 런타임 시점에 스프링 컨테이너에서 Infrastructure 레이어의 구현체를 주입
  + 기술 스택 변경 시, Infrastructure 레이어의 구현체만 변경/교체하면 되므로 Domain 레이어에 영향 없음
___
### Infrastructure / Presentation 레이어 간 호출 흐름
#### 호출 흐름 및 문제점
+ Infrastructure 레이어에서 시작되는 로직의 호출 흐름 처리
  + 호출 흐름은 Infrastructure → Presentation 레이어 방향으로 발생
  + Infrastructure 레이어가 Presentation 레이어에 의존
  + 예시
    + Kafka 토픽 메시지를 소비(Consume)하여 로직을 시작하는 경우
    + gRPC 서버에서 클라이언트 요청을 받아 로직을 시작하는 경우

+ 문제점
  + Presentation 레이어의 변경이 Infrastructure 레이어에 영향을 미침
  + Infrastructure 레이어가 Presentation 레이어의 전체 클래스에 의존
  + Infrastructure 레이어부터 Domain 레이어까지 추이 종속성 발생
    + Infrastructure → Presentation → Application → Domain
  + 추이 종속성으로 ISP(인터페이스 분리 원칙) 및 CRP(공통 재사용 원칙) 위반
    + ISP & CRP : “필요하지 않는 것에 의존하지 말라”

#### 레이어 내 의존 역전 적용
+ 위 문제점 해결 방안
  + 의존 클래스 제한 및 추이 종속성을 해결하기 위해 의존 역전 기법 사용 (정보 은닉)
  + 레이어 구성도에서 Presentation 레이어 내 보라 점선 화살표(인터페이스-구현체 관계) 확인

+ 인터페이스를 통한 의존 역전을 적용하여 Presentation 레이어 내 정보 은닉
  + Infrastructure 레이어에서 사용할 인터페이스 정의는 Presentation 레이어가 책임
  + Infrastructure 레이어는 Presentation 레이어가 제공한 인터페이스를 통해서만 흐름 발생
    + 인터페이스는 구현체보다 변경 가능성 낮아 SDP(안정된 의존성 원칙) 준수
    + SDP : “더 안정적인 것에 의존하라.”
  + 인터페이스가 변경되지 않으면 Infrastructure 레이어에 영향 없음

+ 레이어 내 DIP(의존 역전 원칙)를 적용하여 Infrastructure 레이어 보호
  + 의존하지 않는 Presentation 레이어의 클래스 변경으로부터 Infrastructure 레이어 보호
  + 추이 종속성이 발생하지 않아, Application / Domain 레이어 변경으로부터 Infrastructure 레이어 보호
  + 인터페이스를 통해 필요한 것에만 의존하므로 ISP(인터페이스 분리 원칙) 및 CRP(공통 재사용 원칙) 준수

