package blackjack.controller

import blackjack.domain.BlackjackGame
import blackjack.domain.player.GamePlayers
import blackjack.domain.player.Player
import blackjack.view.input.GamePlayerBetAmountInputView
import blackjack.view.input.GamePlayerNameInputView
import blackjack.view.input.GamePlayerReceiveInputView
import blackjack.view.output.GameDealerAddCardOutputView
import blackjack.view.output.GamePlayersOutputView
import blackjack.view.output.GamePlayersResultOutputView
import blackjack.view.output.GameSharedCardOutputView
import blackjack.view.output.NewLineOutputView

class BlackjackController {
    fun start() {
        // 게임 플레이어 이름 입력
        val playerNameList = GamePlayerNameInputView().value
        NewLineOutputView()

        // 게임 플레이어별 베팅금액 입력
        val betAmounts = playerNameList.map { GamePlayerBetAmountInputView(it).value }
        NewLineOutputView()

        val players = playerNameList.zip(betAmounts.toTypedArray()).map { (name, betAmount) -> Player(name, betAmount) }
        val gamePlayers = GamePlayers(players)
        val blackjackGame = BlackjackGame(gamePlayers)
        blackjackGame.initPlayers()

        // 딜러, 플레이어가 보유한 카드 현황 출력
        GameSharedCardOutputView(gamePlayers)
        GamePlayersOutputView(gamePlayers)
        NewLineOutputView()

        // 각 플레이어마다 카드 분배 로직 수행
        gamePlayers.players.forEach { player -> dealCards(player, blackjackGame) }
        NewLineOutputView()

        // 딜러의 score 여부에 따라 추가 카드 분배 현황 출력
        GameDealerAddCardOutputView(gamePlayers.dealer)
        if (gamePlayers.dealer.isReceivable()) blackjackGame.dealCard(gamePlayers.dealer)

        // 딜러, 플레이어가 보유한 카드현황 + 최종 score 출력
        GamePlayersOutputView(gamePlayers, true)

        // 블랙잭 게임 종료(베팅 금액 계산 등등 수행)
        val gameResult = blackjackGame.finishPlayers()

        // 최종 수익 출력
        GamePlayersResultOutputView(gameResult)
    }

    private fun dealCards(player: Player, blackjackGame: BlackjackGame) {
        while (player.isReceivable()) {
            val response = GamePlayerReceiveInputView(player.name).value
            if (!response.value) {
                player.setStayStatus()
                return
            }
            blackjackGame.dealCard(player)
            GamePlayersOutputView(GamePlayers.from(player))
        }
    }
}
