package com.hansarang.android.air.ui.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDividerDecorator(private val padding: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        when(parent.getChildAdapterPosition(view)) {
            0 -> outRect.top = padding
            state.itemCount - 1 -> outRect.bottom = padding
            else -> {
                with(outRect) {
                    top = padding
                    bottom = padding
                }
            }
        }
    }
}