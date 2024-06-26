package com.vixiloc.vcashiermobile.domain.model.transactions

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class CartItems : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var variationId: Int = 0
    var grocery: Boolean = false
    var quantity: Int = 0
    var price: Int = 0
    var imageUrl: String? = null
    var name: String = ""
    var maxStock: Int = 0
}

fun CartItems.toDataClass() =
    CreateTransactionItem(id = variationId, grocery = grocery, quantity = quantity)