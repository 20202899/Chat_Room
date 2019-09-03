package br.com.carlosiwakura.chat_room_androidv2.views

import android.app.ActivityOptions
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.navigation.ActivityNavigator
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import br.com.carlosiwakura.chat_room_androidv2.R
import br.com.carlosiwakura.chat_room_androidv2.activities.MainActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout

class CustomFab : FloatingActionButton {

    enum class TypeNavigation { EnterNameType, CreateChatType, ChatType }

    var typeNavigation: TypeNavigation = TypeNavigation.EnterNameType
    var navigate: NavController? = null
    lateinit var activity: MainActivity

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    init {
        setOnClickListener(this::navigate)
    }

    fun navigate(view: View) {

        val extras = FragmentNavigatorExtras()
        when (typeNavigation) {
            TypeNavigation.EnterNameType -> {

                navigate?.navigate(R.id.createChatFragment,  null, null, extras)
            }
            TypeNavigation.CreateChatType -> {
                navigate?.navigate(R.id.chat, null, null, extras)
            }
            else -> {

            }
        }
    }

}