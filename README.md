# kotlin-blackjack

## Step1 코틀린 DSL

- 자기소개(introduce) DSL을 작성합니다.
```
// DSL 예시)

introduce {
  name("김정욱")
  company("kakao")
  skills {
    soft("성실함")
    soft("능동성")
    soft("열정")
    hard("spring webflux")
    hard("kotlin")
    hard("nextjs")
    hard("typescript")
  }
  languages {
    "Korean" level 5
    "English" level 3
  }
}
```


## 블랙잭(blackjack)

### 프로그래밍 요구사항
- 모든 기능을 TDD로 구현해 단위 테스트가 존재해야 한다. 단, UI(System.out, System.in) 로직은 제외
- indent(인덴트, 들여쓰기) depth를 2를 넘지 않도록 구현한다. 1까지만 허용한다.
- 함수(또는 메서드)의 길이가 15라인을 넘어가지 않도록 구현한다.
- 기능을 구현하기 전에 README.md 파일에 구현할 기능 목록을 정리해 추가한다.
- git의 commit 단위는 앞 단계에서 README.md 파일에 정리한 기능 목록 단위로 추가한다.
- 모든 엔티티를 작게 유지한다.
- (step3) 3개 이상의 인스턴스 변수를 가진 클래스를 쓰지 않는다.
- (step3) 딜러와 플레이어에서 발생하는 중복 코드를 제거해야 한다.

### 기능 요구사항
- (step2) 딜러와 플레이어는 매 턴마다 카드를 받을지 결정하며 카드의 합이 21 또는 21에 가까운 사람이 승리
- (step2) 처음에 플레이어는 2장의 카드를 받으며 합이 21을 넘지 않을 경우 얼마든지 카드를 뽑을 수 있다
- (step2) 기본으로 카드에 있는 숫자로 계산되지만, Ace는 1 또는 11로 King/Queen/Jack은 각각 10으로 계산

- (step3) 딜러는 처음에 받은 2장의 합계가 16이하이면 반드시 1장의 카드를 추가로 받아야 하고, 17점 이상이면 추가로 받을 수 없다.
- (step3) 딜러가 21을 초과하면 그 시점까지 남아 있던 플레이어들은 가지고 있는 패에 상관 없이 승리한다.
- (step3) 게임을 완료한 후 각 플레이어별로 승패를 출력한다.


### 기능 목록
(카드)
- [x] 카드는 다이아몬드 / 하트 / 클로버 / 스페이드가 각각 13개의 끗수로 이루어져있다
- [x] King, Queen Jack 카드는 10점 / Ace 카드는 1또는 11점을 가질 수 있다 <br />
  ACE(1 or 11) / TWO(2) / THREE(3) / FOUR(4) / FIVE(5) / SIX(6) / SEVEN(7) / EIGHT(8) <br />
  / NINE(9) / TEN(10) / JACK(10) / QUEEN(10) / KING(10)
- [x] ACE를 가진 경우 1과 11일 경우를 모두 따져 승리에 가까운 조건에 최적화 되어야 한다

(블랙잭 게임 / 플레이어)
- [x] 플레이어는 '카드 리스트' / '이름' 정보를 가지고 있다
- [x] 최초 턴에 플레이어는 2장의 카드를 지급받는다
- [x] 덱에서 랜덤하게 카드를 가져오며 가져온 카드는 덱에서 제거된다
- [x] 플레이어는 자신의 턴에 카드를 뽑거나 뽑지않거나 2가지 행동을 할 수 있다
- [x] 플레이어가 카드를 받을 수 있는지 체크하고 카드를 받는다
- [x] 카드를 뽑은 후 총 합이 21이 넘으면 다음 차례부터 카드를 뽑을 수 없다
- [x] 모든 플레이어의 턴이 지나면 게임이 종료된다

(딜러)
- [x] 딜러는 처음에 받은 2장의 합계가 16이하이면 반드시 1장의 카드를 추가로 받아야 한다 (17점 이상이면 받을 수 없음)
- [x] 딜러가 21을 초과하는지 체크할 수 있어야 한다
- [x] 최종 결과는 딜러의 score를 플레이어들의 score와 각각 비교하여 결정된다(딜러: 1승1패 / 플레이어1: 1패 / 플레이어2: 1승)
- [x] 딜러가 21 score를 초과하면, 그 시점까지 남아있던 모든 플레이어는 패와 상관없이 승리한다.
- [x] 딜러가 21점 이하라면, 기존 승리조건과 동일 (BUST를 제외하고 21에 더 가까운 쪽이 승리)    

(뷰)
- [x] 게임참여 메시지를 출력합니다 : '게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)'
- [x] 게임참여 인원을 입력받습니다

- [x] 라운드를 최초에 시작하면, 플레이어에게 2장의 카드를 나누었다는 메시지를 출력합니다 : 'pobi, jason에게 2장의 나누었습니다.'
- [x] 플레이어는 카드를 받으면, 현재 받은 카드 리스트를 출력합니다 : 'pobi카드: 2하트, 8스페이드'

- [x] 라운드가 진행될 때 마다 카드를 더 받을지 물어봅니다 : 'pobi는 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)'
- [x] 카드를 더 받을지 물어보면 yes 또는 no 중에 선택하여 입력합니다 : 'y' 또는 'n'

- [x] 게임이 종료되면, 각 플레이어와 딜러의 보유한 카드 목록과 최종 점수를 출력합니다 : pobi카드: 2하트, 8스페이드, A클로버 - 결과: 21

