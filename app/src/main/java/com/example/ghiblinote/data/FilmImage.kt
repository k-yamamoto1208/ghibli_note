package com.example.ghiblinote.data

import com.example.ghiblinote.R

/**
 * 映画のIDと画像リソースIDを紐付けるenum class
 */
enum class FilmImage(val filmId: String, val iconImage: Int, val detailImage: Int) {
    CASTLE_IN_THE_SKY(
        "2baf70d1-42bb-4437-b551-e5fed5a87abe",
        R.drawable.castle_in_the_sky_image,
        R.drawable.castle_in_the_sky_detail_image
    ),
    GRAVE_OF_THE_FIREFLIES(
        "12cfb892-aac0-4c5b-94af-521852e46d6a",
        R.drawable.grave_of_the_fireflies_image,
        R.drawable.grave_of_the_fireflies_detail_image
    ),
    MY_NEIGHBOR_TOTORO(
        "58611129-2dbc-4a81-a72f-77ddfc1b1b49",
        R.drawable.my_neighbor_totoro_image,
        R.drawable.my_neighbor_totoro_detail_image
    ),
    KIKIS_DELIVERY_SERVICE(
        "ea660b10-85c4-4ae3-8a5f-41cea3648e3e",
        R.drawable.kikis_delivery_service_image,
        R.drawable.kikis_deliverly_service_detail_image
    ),
    ONLY_YESTERDAY(
        "4e236f34-b981-41c3-8c65-f8c9000b94e7",
        R.drawable.only_yesterday_image,
        R.drawable.only_yesterday_detail_image
    ),
    PORCO_ROSSO(
        "ebbb6b7c-945c-41ee-a792-de0e43191bd8",
        R.drawable.porco_rosso_image,
        R.drawable.porco_rosso_detail_image
    ),
    POM_POKO(
        "1b67aa9a-2e4a-45af-ac98-64d6ad15b16c",
        R.drawable.pom_poko_image,
        R.drawable.pom_poko_detail_image
    ),
    WHISPER_OF_THE_HEART(
        "ff24da26-a969-4f0e-ba1e-a122ead6c6e3",
        R.drawable.whisper_of_the_heart_image,
        R.drawable.whisper_of_the_heart_detail_image
    ),
    PRINCESS_MONONOKE(
        "0440483e-ca0e-4120-8c50-4c8cd9b965d6",
        R.drawable.princess_mononoke_image,
        R.drawable.princess_mononoke_detail_image
    ),
    MY_NEIGHBORS_THE_YAMADAS(
        "45204234-adfd-45cb-a505-a8e7a676b114",
        R.drawable.my_neighbors_the_yamadas_image,
        R.drawable.my_neighbors_the_yamadas_detail_image
    ),
    SPIRITED_AWAY(
        "dc2e6bd1-8156-4886-adff-b39e6043af0c",
        R.drawable.spirited_away_image,
        R.drawable.spirited_away_detail_image
    ),
    THE_CAT_RETURNS(
        "90b72513-afd4-4570-84de-a56c312fdf81",
        R.drawable.the_cat_returns_image,
        R.drawable.the_cat_returns_detail_image
    ),
    HOWLS_MOVING_CASTLE(
        "cd3d059c-09f4-4ff3-8d63-bc765a5184fa",
        R.drawable.howls_moving_castle_image,
        R.drawable.howls_moving_castle_detail_image
    ),
    TALES_OF_EARTHSEA(
        "112c1e67-726f-40b1-ac17-6974127bb9b9",
        R.drawable.tales_from_earthsea_image,
        R.drawable.tales_from_earthsea_detail_image
    ),
    PONYO(
        "758bf02e-3122-46e0-884e-67cf83df1786",
        R.drawable.ponyo_image,
        R.drawable.ponyo_detail_image
    ),
    ARRIETTY(
        "2de9426b-914a-4a06-a3a0-5e6d9d3886f6",
        R.drawable.arrietty_image,
        R.drawable.arrietty_detail_image
    ),
    FROM_UP_ON_POPPY_HILL(
        "45db04e4-304a-4933-9823-33f389e8d74d",
        R.drawable.from_up_on_poppy_hill_image,
        R.drawable.from_up_on_poppy_hill_detail_image
    ),
    THE_WIND_RISES_IMAGE(
        "67405111-37a5-438f-81cc-4666af60c800",
        R.drawable.the_wind_rises_image,
        R.drawable.the_wind_rises_detail_image
    ),
    THE_TALE_OF_PRINCESS_KAGUYA(
        "578ae244-7750-4d9f-867b-f3cd3d6fecf4",
        R.drawable.the_tale_of_the_princess_kaguya_image,
        R.drawable.the_tale_of_the_princess_kaguya_detail_image
    ),
    WHEN_MARNIE_WAS_THERE(
        "5fdfb320-2a02-49a7-94ff-5ca418cae602",
        R.drawable.when_marnie_was_there_image,
        R.drawable.when_marnie_was_there_detail_image
    ),
    THE_RED_TURTLE(
        "d868e6ec-c44a-405b-8fa6-f7f0f8cfb500",
        R.drawable.the_red_turtle_image,
        R.drawable.the_red_turtle_detail_image
    );

    companion object {
        /**
         * 映画一覧に表示する画像を映画IDから取得
         *
         * @param id 映画ID
         * @return 映画一覧に表示する画像リソースID
         */
        fun getIconImageFromId(id: String): Int {
            return values().first { it.filmId == id }.iconImage
        }

        /**
         * 詳細画面に表示する画像を映画IDから取得
         *
         * @param id 映画ID
         * @return 詳細画面に表示する画像リソースID
         */
        fun getDetailImageFromId(id: String): Int {
            return values().first { it.filmId == id }.detailImage
        }
    }
}