package com.nastena.pawsitive.ui.screens.user.home

sealed interface UserHomeEvents {

    object LikeClicked : UserHomeEvents

    object DislikeClicked : UserHomeEvents

    object DetailsClicked : UserHomeEvents
    object HintClicked : UserHomeEvents

}