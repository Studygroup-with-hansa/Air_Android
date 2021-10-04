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
        with(outRect) {
            when (parent.getChildAdapterPosition(view)) {
                0 -> {
                    top = padding * 2
                    bottom = padding
                }
                state.itemCount - 1 -> {
                    top = padding
                    bottom = padding * 2
                }
                else -> {
                    top = padding
                    bottom = padding
                }
            }
        }
    }
}