package com.example.tfg.ui.friends.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tfg.R
import com.example.tfg.model.user.User
import com.example.tfg.ui.friends.FriendsMainState
import com.example.tfg.ui.friends.FriendsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarFriendsScreen(
    viewModel: FriendsViewModel,
    state: FriendsMainState,
    navigateToProfile: (user: User) -> Unit
) {
    SearchBar(
        modifier = Modifier
            .semantics { traversalIndex = 0f }
            .fillMaxWidth(),
        inputField = {
            Row{
                if (state.expandedSearchBar) {
                    IconButton(onClick = {
                        viewModel.changeExpandedSearchBar(false)
                        viewModel.userFriendQueryChange("")
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_arrow)
                        )
                    }
                }
                SearchBarDefaults.InputField(
                    onSearch = { viewModel.searchUsers() },
                    expanded = state.expandedSearchBar,
                    onExpandedChange = { viewModel.onlyChangeExpandedSearchBar(it)  },
                    placeholder = { Text(stringResource(id = R.string.friends_search_placeholder_input)) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "") },
                    query = state.userQuery,
                    onQueryChange = { viewModel.userFriendQueryChange(it)}
                )
            }
        },
        expanded = state.expandedSearchBar,
        onExpandedChange = { viewModel.changeExpandedSearchBar(it)},
    ) {
        LazyColumn{
            items(state.queryResult){
                friendsRow(it,viewModel,navigateToProfile)
            }
        }
    }
}

@Composable
fun friendsRow(user: User, viewModel: FriendsViewModel, navigateToProfile: (user: User) -> Unit) {
    Row(Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp).clickable {
        viewModel.saveState()
        navigateToProfile(user)
    }) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Image(
                painterResource(user.profilePicture),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Text(
                user.userName,
                maxLines = 1,
                modifier = Modifier,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
            )
        }
    }
}