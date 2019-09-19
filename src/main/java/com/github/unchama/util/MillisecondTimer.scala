package com.github.unchama.util

class MillisecondTimer private constructor() {
  private var startTime: Long = 0

  def resetTimer() {
    startTime = System.nanoTime()
  }

  def sendLapTimeMessage(message: String) {
    val recordedNanoSecondDuration = System.nanoTime() - startTime

    println("$message(time: ${recordedNanoSecondDuration / 1000L} ms)")

    startTime = System.nanoTime()
  }

  companion object {
    def getInitializedTimerInstance(): MillisecondTimer = MillisecondTimer().apply { resetTimer() }
  }
}