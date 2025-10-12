package com.route.newsappc42gsunwed.ui.screens.news

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.route.domain.entities.news.ArticlesItemEntity
import com.route.domain.entities.news.SourcesItemEntity
import com.route.domain.utils.base.Resource
import com.route.newsappc42gsunwed.ui.theme.gray

@Composable
fun NewsScreen(
    categoryAPIId: String,
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel()
) {
    // 1- Base Resource Model ->
    // 2- Repository Pattern  (Clean Architecture) ->
    Column(modifier = modifier.fillMaxSize()) {
        SourcesTabRow(categoryAPIId, viewModel = viewModel)           // empty -> list
        Spacer(Modifier.height(8.dp))

        NewsLazyColumn(viewModel = viewModel) // empty -> listOf
    }
}

@Preview
@Composable
private fun NewsScreenPreview() {
    NewsScreen("")
}

//   SOLID Design Principles
@Composable
fun SourcesTabRow(
    categoryApiId: String,
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel,
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) { //
        viewModel.getSources(categoryApiId)
    }
    var selectedIndex by remember { mutableIntStateOf(-1) }
    LaunchedEffect(viewModel.sourcesResource.value) {
        if (viewModel.sourcesResource.value is Resource.Success) {
            val successResource =
                (viewModel.sourcesResource.value as Resource.Success<List<SourcesItemEntity>>).data
            viewModel.selectedSourceId.value = (successResource[0].id ?: "")
            selectedIndex = 0
        }
    }
    val selectedId = viewModel.selectedSourceId.collectAsStateWithLifecycle().value
    LaunchedEffect(selectedId) {
        if (selectedId.isNotEmpty())
            viewModel.getNewsBySourceId(selectedId)
    }
    val state = viewModel.sourcesResource.value
    when (state) {
        is Resource.Initial -> {}
        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground)
            }
        }

        is Resource.Error -> {
            if (state.errorMessage.isNotEmpty()) {
                Toast.makeText(
                    LocalContext.current,
                    state.errorMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        is Resource.Success -> {
            LazyRow(modifier) {
                itemsIndexed(state.data) { index, item ->
                    SourcesItem(item, index, selectedIndex) { clickedIndex, sourcesItem ->
                        selectedIndex = clickedIndex
                        viewModel.selectedSourceId.value = (sourcesItem.id ?: "")
                    }
                }
            }
        }
    }


}

@Composable
fun SourcesItem(
    sourcesItemDM: SourcesItemEntity,
    index: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    onSourceClickListener: (Int, SourcesItemEntity) -> Unit
) {
    if (index == selectedIndex)
        Text(
            text = sourcesItemDM.name ?: "",
            fontWeight = FontWeight.W700,
            color = MaterialTheme.colorScheme.onBackground,
            textDecoration = TextDecoration.Underline,
            modifier = modifier
                .clickable {
                    onSourceClickListener(index, sourcesItemDM)
                }
                .padding(horizontal = 4.dp, vertical = 2.dp)
        )
    else
        Text(
            text = sourcesItemDM.name ?: "",
            fontWeight = FontWeight.W500,
            color = MaterialTheme.colorScheme.onBackground,
            textDecoration = TextDecoration.None,
            modifier = modifier
                .clickable {
                    onSourceClickListener(index, sourcesItemDM)
                }
                .padding(horizontal = 4.dp, vertical = 2.dp)
        )

}

@Preview(showBackground = true)
@Composable
private fun SourcesItemPreview() {
    SourcesItem(
        sourcesItemDM = SourcesItemEntity(
            country = "EG",
            name = "Al-Jazeera",
            description = "a news sources for general information",
            language = "Arabic",
            id = "1",
            category = "General",
        ),
        index = 1,
        selectedIndex = 1,
    ) { index, sourcesItem -> }
}

@Preview
@Composable
private fun SourcesTabRowPreview() {
    SourcesTabRow("", viewModel = hiltViewModel())
}

@Composable
fun NewsLazyColumn(
    viewModel: NewsViewModel,
    modifier: Modifier = Modifier
) {
    val state = viewModel.articlesResource.value
    when (state) {
        is Resource.Initial -> {}

        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground)
            }
        }

        is Resource.Error -> {
            if (state.errorMessage.isNotEmpty()) {
                Toast.makeText(
                    LocalContext.current,
                    state.errorMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        is Resource.Success -> {
            LazyColumn(modifier) {
                items(state.data) {
                    NewsCard(it)
                }
            }
        }
    }

}

@Preview
@Composable
private fun NewsLazyColumnPreview() {
    NewsLazyColumn(
        hiltViewModel()
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsCard(articleItem: ArticlesItemEntity?, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        //     pagination : {
        //              page : 1,
//                  nextPage : 2 ,
//                  totalPages : 20 ,

        //
        //     }

    ) {
        // Image
        GlideImage(
            articleItem?.urlToImage ?: "",
            contentDescription = articleItem?.description,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )
        // Title
        Text(
            articleItem?.title ?: "",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "By : ${articleItem?.author}",
                color = gray,
                fontWeight = FontWeight.W500,
            )
            Text(
                text = articleItem?.publishedAt ?: "",
                color = gray,
                fontWeight = FontWeight.W500,
            )

        }

    }
}

@Preview
@Composable
private fun NewsCardPreview() {
    NewsCard(
        ArticlesItemEntity(
            publishedAt = "21-9-2025",
            author = "BBC News",
            description = "News Description",
            title = "News Title",
            content = " News Content",
        )
    )
}
// Room Database (Creational Design Patterns (Singleton and Builder Patterns))
// Jetpack Compose (UI , States and Recomposition)
// APIs & Networking
// MVVM UI Architecture Pattern + Observer Pattern (Mutable States)
// Kotlin Coroutines + Pagination (Mentor (Eng/ AbdAlRahman))
// SOLID Design Principles
// Repository Pattern + Clean Architecture
// 1- Dependency injection (Dagger Hilt) (Today)
// 2- E-Commerce Auth Features Part 1 & Kotlin Flows (15/10/2025)
//    Q & A (Questions & Answers )             (17/10/2025) Friday
// 3- E-Commerce Auth Features Part 2 & Kotlin Flows(19/10/2025)
// 4- (Compose) Local Composition & Token & Data Store (22/10/2025)
// 5- MVI UI Arch Pattern (26/10/2025)
// 6- Unit Testing (29/10/2025)
// 7- Q & A (Questions & Answers ) (2/11/2025)
