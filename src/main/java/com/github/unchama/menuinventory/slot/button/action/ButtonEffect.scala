package com.github.unchama.menuinventory.slot.button.action

/**
 * メニューインベントリ上のボタンが[InventoryClickEvent]に対して発火すべき作用を計算するオブジェクトへのinterface.
 */
interface ButtonEffect {

  /**
   * [event]に基づいてボタンが発生させるべき作用を計算する.
   */
  def asyncEffectOn(event: InventoryClickEvent): TargetedEffect<Player>

  companion object {
    operator def invoke(effect: ButtonEffectScope.() -> TargetedEffect<Player>): ButtonEffect = object : ButtonEffect {
      override def asyncEffectOn(event: InventoryClickEvent): TargetedEffect<Player> = effect(ButtonEffectScope(event))
    }
  }

}
