package fr.isen.loic.androiderestaurant.model

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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Plat

        if (id != other.id) return false
        if (name_fr != other.name_fr) return false
        if (name_en != other.name_en) return false
        if (id_category != other.id_category) return false
        if (categ_name_fr != other.categ_name_fr) return false
        if (categ_name_en != other.categ_name_en) return false
        if (!images.contentEquals(other.images)) return false
        if (!ingredients.contentEquals(other.ingredients)) return false
        if (!prices.contentEquals(other.prices)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name_fr.hashCode()
        result = 31 * result + name_en.hashCode()
        result = 31 * result + id_category
        result = 31 * result + categ_name_fr.hashCode()
        result = 31 * result + categ_name_en.hashCode()
        result = 31 * result + images.contentHashCode()
        result = 31 * result + ingredients.contentHashCode()
        result = 31 * result + prices.contentHashCode()
        return result
    }
}

