package br.com.carlosiwakura.Chat_Room_Android.interfaces

import br.com.carlosiwakura.Chat_Room_Android.models.Message
import java.io.Serializable
import java.util.*

class ObservableChat : Observable(), Serializable {
    var mData: MutableList<String> = mutableListOf()
        set(value) {
                value.let { field.addAll(it) ; setChanged() ; notifyObservers(value) }
        }
}