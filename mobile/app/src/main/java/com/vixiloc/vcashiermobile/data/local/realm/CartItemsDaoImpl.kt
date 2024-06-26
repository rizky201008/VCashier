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
        realm.write {
            val existingCartItem =
                this.query<CartItems>("variationId == $0", cartItems.variationId).find()
                    .firstOrNull()

            existingCartItem?.let {
                delete(it)
            }

            copyToRealm(cartItems)
        }
    }

    override suspend fun clearCartItems() {
        realm.write {
            val cartItems = query<CartItems>().find()
            delete(cartItems)
        }
    }

    override suspend fun updateCartItems(cartItems: CartItems) {
        realm.write {
            val liveCartItems = this.query<CartItems>("_id == $0", cartItems._id).find().first()
            liveCartItems.quantity = cartItems.quantity
            liveCartItems.price = cartItems.price
            liveCartItems.maxStock = cartItems.maxStock
        }
    }

    override suspend fun deleteCartItems(cartItems: CartItems) {
        // Open a write transaction
        realm.write {
            val cartToDelete: CartItems =
                query<CartItems>("_id == $0", cartItems._id).find().first()
            // Pass the query results to delete()
            delete(cartToDelete)
        }
    }
}