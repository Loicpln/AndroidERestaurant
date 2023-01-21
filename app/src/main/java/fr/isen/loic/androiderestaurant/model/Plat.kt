package fr.isen.loic.androiderestaurant.model

import java.io.Serializable

data class Plat(
    var id: Int,
    var name_fr: String,
    var name_en: String,
    var id_category: Int,
    var categ_name_fr: String,
    var categ_name_en: String,
    var images: Array<String>,
    var ingredients: Array<Ingredient>,
    var prices: Array<Prix>
) : Serializable
