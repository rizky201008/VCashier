package com.vixiloc.vcashiermobile.domain.model.transactions

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class CartItems : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var productId: Int = 0
    var grocery: Boolean = false
    var quantity: Int = 0
}

fun CartItems.toDataClass() =
    CreateTransactionItem(id = productId, grocery = grocery, quantity = quantity)