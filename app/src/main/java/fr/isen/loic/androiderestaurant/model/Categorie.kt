package fr.isen.loic.androiderestaurant.model

import java.io.Serializable

data class Categorie(
    var name_fr: String,
    var name_en: String,
    var items: Array<Plat>
) : Serializable