package com.github.unchama.menuinventory

/**
 * 入っているアイテムスタックをクリックすることで作用が引き起こされるような
 * インベントリのイミュータブルなビューを表すオブジェクトのクラス.
 *
 * @param size インベントリのサイズを決定するデータ
 * @param title インベントリのタイトル
 * @param slotLayout インベントリの各スロットのindexと[Slot]を対応付ける[IndexedSlotLayout]
 */
case class MenuInventoryView(private val size: InventorySize,
                             private val title: String,
                             internal val slotLayout: IndexedSlotLayout = IndexedSlotLayout()) {
  internal def createConfiguredInventory(holder: InventoryHolder): Inventory {
    return runBlocking {
      createInventory(holder, size, title).also {
        slotLayout.asynchronouslySetItemsOn(it)
      }
    }
  }

  def createNewSession(): MenuSession = MenuSession(this)
}