package com.jetbrains.handson.httpapi

import kotlinx.serialization.Serializable

@Serializable
data class ItemFromCatalog(val item: String, val price: Int, var icon_name: String = "unknown.png", val item_description: String = "No description")

val tea = mutableListOf<ItemFromCatalog>(
    ItemFromCatalog("Белый",90,"white.jpg"),
    ItemFromCatalog("Зеленный",80,"green.jpg"),
    ItemFromCatalog("Желтый",110,"yellow.jpg"),
    ItemFromCatalog("Улун",90,"ulun.jpg"),
    ItemFromCatalog("Черный",90,"black.jpg"),
    ItemFromCatalog("Пуэр",90,"puar.webp")
)

val coffee = mutableListOf<ItemFromCatalog>(
    ItemFromCatalog("Экспрессо",120, "espresso.png" ,"это итальянский ответ на вашу сиюминутную потребность в кофеине. " +
                                                                "Он мгновенно готовится в эспрессо машине и этот шот необходимо моментально выпить"),

    ItemFromCatalog("Капучино",150,"cappuccino.jpg","Толстый слой густой кремовой пенки вместе со сладковатым " +
            "                                                 согревающим молоком и богатым вкусом хорошо сваренного эспрессо – это абсолютное наслаждение…"),

    ItemFromCatalog("Американо",110,  "americano.jpg","Получил название “Американо”, во время 2-й Мировой войны при оккупации Италии войсками США. " +
                                                                "Американские солдаты, скучавшие по родине и черному кофе, заказывали в Итальянских кафе, " +
                                                                "непривычно крепкий для них “Эспрессо” и разбавляли его горячей водой, что бы напиток стал похож на фильтр-кофе."),

    ItemFromCatalog("Латте",90,"latte.jpg","Мы жертвуем кремом во имя молока в Капучино"),

    ItemFromCatalog("Фраппе",160,"frappe.png"),
    ItemFromCatalog("Кофе с льдом",90,"coffee_with_ice.webp"),
)

val catalog = mutableMapOf("tea" to tea, "coffee" to coffee)