package br.com.carlosiwakura.chat_room_androidv2.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.navigation.NavController
import br.com.carlosiwakura.chat_room_androidv2.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CustomFab : FloatingActionButton {

    enum class TypeNavigation {EnterNameType, CreateChatType, ChatType}

    var typeNavigation: TypeNavigation = TypeNavigation.EnterNameType
    var navigate: NavController? = null
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    init {
        setOnClickListener(this::navigate)
    }

    fun navigate(view: View) {
        when (typeNavigation) {
            TypeNavigation.EnterNameType -> {

                navigate?.navigate(R.id.createChatFragment)
            }
            TypeNavigation.CreateChatType -> {
                navigate?.navigate(R.id.chat)
            }
            else -> {

            }
        }
    }

}