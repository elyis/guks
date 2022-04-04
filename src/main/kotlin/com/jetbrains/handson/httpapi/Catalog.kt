package com.jetbrains.handson.httpapi

import kotlinx.serialization.Serializable

@Serializable
data class ItemFromCatalog(val item: String, val price: Int, var icon_name: String = "unknown.png", val item_description: String = "No description")

val tea = mutableListOf<ItemFromCatalog>(
    ItemFromCatalog("Белый",90,"white.jpg", "чай созданный из почек и самых молодых побегов чайного куста"),
    ItemFromCatalog("Зеленный",80,"green.jpg", "Он ценится гораздо выше других разновидностей. Все благодаря поразительной пользе и более тонкому эффекту"),
    ItemFromCatalog("Желтый",110,"yellow.jpg", "Тонкие ноты проявляются благодаря дополнительному этапу обработки чайного листа. Он называется томлением или обертыванием."),
    ItemFromCatalog("Улун",90,"ulun.jpg", "самая ароматная разновидность чая. Недаром сорта этой группы являются любимцами чайных церемоний, в которых ценится многообразие оттенков и нот."),
    ItemFromCatalog("Черный",90,"black.jpg", "Его приятный мягкий вкус радует миллионы людей во всем мире."),
    ItemFromCatalog("Пуэр",90,"puar.webp", "это уникальный напиток, ценность которого увеличивается с возрастом. Чем дольше он будет храниться, тем лучше и изысканнее будет его вкус.")
)

val coffee = mutableListOf<ItemFromCatalog>(
    ItemFromCatalog("Экспрессо",120, "espresso.png" ,"Самый молодой, самый современный, самый быстрый способ приготовления кофе. Недаром на своей родине, в Италии, эспрессо считают «кофейным королем»."),
    ItemFromCatalog("Капучино",150,"cappuccino.jpg","это кофе, который состоит из одной части эспрессо, одной части подогретого молока и одной части молочной пены."),
    ItemFromCatalog("Американо",110,  "americano.jpg","то кофе эспрессо, дополнительно разбавленный водой. Рецепт и название напиток получил как пренебрежительное название «не настоящего» эспрессо, придуманное итальянцами"),
    ItemFromCatalog("Латте",90,"latte.jpg","кофе готовят на основе эспрессо, мокаччино, американо с добавлением молока."),
    ItemFromCatalog("Фраппе",160,"frappe.png", "покрытый молочной пеной холодный кофейный напиток греческого происхождения."),
    ItemFromCatalog("Кофе с льдом",90,"coffee_with_ice.webp", "кофе с добавлением льда"),
)

val catalog = mutableMapOf("Чай" to tea, "Кофе" to coffee)