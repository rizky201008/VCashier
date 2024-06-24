package com.vixiloc.vcashiermobile.data.local.realm

import com.vixiloc.vcashiermobile.domain.model.transactions.CartItems
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartItemsDaoImpl(private val realm: Realm) : CartItemsDao {
    override suspend fun getCartItems(): Flow<List<CartItems>> {
        val queryAllCarts = realm.query<CartItems>()
        return queryAllCarts.find().asFlow().map { it.list }
    }

    override suspend fun insertCartItems(cartItems: CartItems) {
        realm.write { copyToRealm(cartItems) }
    }
}