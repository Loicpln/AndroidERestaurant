package fr.isen.loic.androiderestaurant.model

import java.io.Serializable

data class Ingredient(
    var id: Int,
    var id_shop: Int,
    var name_fr: String,
    var name_en: String,
    var create_date: String,
    var update_date: String,
    var id_pizza: Int
) : Serializable
