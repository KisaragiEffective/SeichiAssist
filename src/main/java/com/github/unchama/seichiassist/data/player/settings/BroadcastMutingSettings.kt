package com.github.unchama.seichiassist.data.player.settings

import com.github.unchama.seichiassist.data.PlayerData
import com.github.unchama.targetedeffect.unfocusedEffect

/**
 * 全体メッセージとそれに伴う効果音の抑制をするためのプレーヤー向け設定項目の状態を表すEnum.
 */
enum class BroadcastMutingSettings {
  RECEIVE_MESSAGE_AND_SOUND, RECEIVE_MESSAGE_ONLY, MUTE_MESSAGE_AND_SOUND;

  fun nextSettingsOption(): BroadcastMutingSettings = when (this) {
    RECEIVE_MESSAGE_AND_SOUND -> RECEIVE_MESSAGE_ONLY
    RECEIVE_MESSAGE_ONLY -> MUTE_MESSAGE_AND_SOUND
    MUTE_MESSAGE_AND_SOUND -> RECEIVE_MESSAGE_AND_SOUND
  }

  fun shouldMuteMessages(): Boolean = when (this) {
    RECEIVE_MESSAGE_AND_SOUND, RECEIVE_MESSAGE_ONLY -> true
    MUTE_MESSAGE_AND_SOUND -> false
  }

  fun shouldMuteSounds(): Boolean = when (this) {
    RECEIVE_MESSAGE_AND_SOUND -> true
    RECEIVE_MESSAGE_ONLY, MUTE_MESSAGE_AND_SOUND -> false
  }

  companion object {
    fun fromBooleanSettings(displayMessages: Boolean, playSounds: Boolean): BroadcastMutingSettings =
        if (displayMessages) {
          if (playSounds) RECEIVE_MESSAGE_AND_SOUND else RECEIVE_MESSAGE_ONLY
        } else {
          MUTE_MESSAGE_AND_SOUND
        }
  }
}

// TODO PlayerDataにはこれらが入るべき
suspend fun PlayerData.getBroadcastMutingSettings(): BroadcastMutingSettings =
    BroadcastMutingSettings.fromBooleanSettings(everymessageflag, everysoundflag)

val PlayerData.toggleBroadcastMutingSettings
  get() = unfocusedEffect {
    val newSettings = getBroadcastMutingSettings().nextSettingsOption()

    everymessageflag = !newSettings.shouldMuteSounds()
    everysoundflag = !newSettings.shouldMuteMessages()
  }