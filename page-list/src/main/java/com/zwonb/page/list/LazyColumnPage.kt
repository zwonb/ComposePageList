package com.zwonb.page.list

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun LazyColumnPage(
    dataSize: Int,
    onLoadMore: () -> Unit,
    loading: Boolean,
    noMore: Boolean,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    content: LazyListScope.() -> Unit
) {
    if (!loading && !noMore && isScrollLastIndex(state, dataSize)) {
        onLoadMore()
    }

    LazyColumn(
        modifier = modifier,
        state,
        contentPadding,
        reverseLayout,
        verticalArrangement,
        horizontalAlignment,
        flingBehavior
    ) {
        content()
        when {
            loading -> loadingBody()
            noMore -> noMoreBody()
        }
    }
}

private fun LazyListScope.noMoreBody() {
    item {
        Row(
            Modifier
                .fillMaxWidth()
                .height(24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Divider(
                modifier = Modifier.widthIn(64.dp, 96.dp),
                MaterialTheme.colors.onSurface.copy(alpha = 0.1f)
            )
            Text(
                text = "·",
                modifier = Modifier.widthIn(32.dp, 64.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
                fontWeight = FontWeight.Bold,
            )
            Divider(
                modifier = Modifier.widthIn(64.dp, 96.dp),
                MaterialTheme.colors.onSurface.copy(alpha = 0.1f)
            )
        }
    }
}

private fun LazyListScope.loadingBody() {
    item {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(Modifier.size(24.dp), strokeWidth = 2.dp)
        }
    }
}

private fun isScrollLastIndex(state: LazyListState, dataSize: Int): Boolean {
    val lastVisibleIndex = state.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
    return lastVisibleIndex != -1 && lastVisibleIndex >= dataSize - 2
}