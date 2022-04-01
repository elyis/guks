package com.jetbrains.handson.httpapi

import kotlinx.serialization.Serializable

@Serializable
data class ItemFromCatalog(val item: String, val price: Int, val item_description: String?)

val tea = mutableListOf<ItemFromCatalog>(
    ItemFromCatalog("Белый",90,""),
    ItemFromCatalog("Зеленный",80,""),
    ItemFromCatalog("Желтый",110,""),
    ItemFromCatalog("Улун",90,""),
    ItemFromCatalog("Черный",90,""),
    ItemFromCatalog("Пуэр",90,"")
)

val coffee = mutableListOf<ItemFromCatalog>(
    ItemFromCatalog("Экспрессо",120,  "это итальянский ответ на вашу сиюминутную потребность в кофеине. " +
                                                                "Он мгновенно готовится в эспрессо машине и этот шот необходимо моментально выпить"),

    ItemFromCatalog("Капучино",150,"Толстый слой густой кремовой пенки вместе со сладковатым " +
            "                                                 согревающим молоком и богатым вкусом хорошо сваренного эспрессо – это абсолютное наслаждение…"),

    ItemFromCatalog("Американо",110,  "Получил название “Американо”, во время 2-й Мировой войны при оккупации Италии войсками США. " +
                                                                "Американские солдаты, скучавшие по родине и черному кофе, заказывали в Итальянских кафе, " +
                                                                "непривычно крепкий для них “Эспрессо” и разбавляли его горячей водой, что бы напиток стал похож на фильтр-кофе."),

    ItemFromCatalog("Латте",90,"Мы жертвуем кремом во имя молока в Капучино"),

    ItemFromCatalog("Фраппе",160,""),
    ItemFromCatalog("Кофе с льдом",90,""),
)

val catalog = mutableMapOf("tea" to tea, "coffee" to coffee)