
# 자바 GUI 개인 프로젝트

## 개요
이 프로젝트는 자바 기반의 그래픽 사용자 인터페이스(GUI) 개인 프로젝트입니다. Java Swing 또는 JavaFX를 사용하여 인터페이스를 구현했으며, 사용자는 직관적인 UI 요소를 통해 애플리케이션과 상호작용할 수 있습니다.

## 주요 기능
- 자바(Swing/JavaFX)를 사용하여 제작된 인터랙티브 GUI
- 실행시 Memorygame과 OneToFiftygame 플레이 가능
- Memorygame은 난이도 설정 및 3초의 카운트 다운 후 시작. 종료시 경과시간 표시
- OneToFiftygame은 3초의 카운트 다운 후 랜덤한 숫자를 1~50까지 순차적으로 클릭하여 50번째 숫자를 눌렀을때 게임종료 후 경과시간 표시

## 프로젝트 구조
```
- `src/`
  - `Main/`
    - `Main.java`: 애플리케이션의 메인 진입점.
  - `Game/`: 게임 관련 로직을 처리하는 패키지.
  - `tools/`: 프로젝트 전반에서 사용되는 유틸리티 클래스들을 포함한 패키지.
- `bin/`: 컴파일된 클래스 파일들.
```

## 설치 방법

1. 저장소를 클론합니다:
   ```bash
   git clone https://github.com/mintchoco0001/java-GUI-PersonalProject.git
   ```
2. 프로젝트 디렉토리로 이동합니다:
   ```bash
   cd java-GUI-PersonalProject
   ```
3. 프로젝트를 컴파일합니다:
   ```bash
   javac -d bin src/Main/Main.java
   ```
4. 프로젝트를 실행합니다:
   ```bash
   java -cp bin Main.Main
   ```

## 의존성
- Java Development Kit (JDK) 8 이상

## 사용 방법
프로젝트를 컴파일하고 실행하면 GUI 창이 열립니다. 여기에서 애플리케이션의 기능을 사용할 수 있습니다.

## 기여 방법
이 저장소를 포크한 뒤 풀 리퀘스트를 제출하시면 됩니다. 모든 기여를 환영합니다!

## 라이센스
이 프로젝트는 MIT 라이센스 하에 있습니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.
