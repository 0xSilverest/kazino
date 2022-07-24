package com.kazino.blackjack

enum RoundState:
  case RoundStarting, Dealing, PlayerTurn, DealerTurn, RoundOver

enum GameState:
  case GameStarting, GameOnGoing, GameOver

enum PlayerDecision:
  // Stand, DoubleDown, Surrender are terminal states
  case NotPlaying, Split, Hit, Stand,  DoubleDown, Surrender, Insurance

