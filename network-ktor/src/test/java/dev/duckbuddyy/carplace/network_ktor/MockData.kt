package dev.duckbuddyy.carplace.network_ktor

import dev.duckbuddyy.carplace.model.Category
import dev.duckbuddyy.carplace.model.Location
import dev.duckbuddyy.carplace.model.Property
import dev.duckbuddyy.carplace.model.UserInfo
import dev.duckbuddyy.carplace.model.detail.DetailResponse
import dev.duckbuddyy.carplace.model.listing.ListingResponse
import dev.duckbuddyy.carplace.model.listing.ListingResponseItem

internal object MockData {
    val mockListingJson = """
        [{"id":7333920,"title":"VW BEETLE 14 TSİ RLİNE","location":{"cityName":"Adana","townName":"Seyhan"},"category":{"id":12799,"name":"otomobil/volkswagen-new-beetle-1-4-tsi-design"},"modelName":"Beetle 1.4 TSI Design DSG","price":350000,"priceFormatted":"350000 TL","date":"2020-11-30T00:00:00","dateFormatted":"30 Kasım 2020","photo":"https://arbstorage.mncdn.com/ilanfotograflari/2017/08/03/7333920/641e33f6-541f-40f3-8ba3-42960e563078_image_for_silan_7333920_{0}.jpg","properties":[{"name":"km","value":"23000"},{"name":"color","value":"Kırmızı"},{"name":"year","value":"2013"}]},{"id":16253376,"title":"ORJINAL  MUAYENE SIFIR","location":{"cityName":"İstanbul","townName":"Ümraniye"},"category":{"id":12212,"name":"otomobil/volkswagen-scirocco-1-4-tsi-sportline"},"modelName":"Scirocco 1.4 TSI Sportline Manuel","price":163000,"priceFormatted":"163000 TL","date":"2020-12-23T00:00:00","dateFormatted":"23 Aralık 2020","photo":"https://arbstorage.mncdn.com/ilanfotograflari/2020/12/23/16253376/8bef8606-dec3-47c1-9d53-a1862b110e9f_image_for_silan_16253376_{0}.jpg","properties":[{"name":"km","value":"72000"},{"name":"color","value":"Beyaz"},{"name":"year","value":"2012"}]},{"id":16251667,"title":"Fiat Linea 1.3 Multijet Active Plus 2012 Model - 114.500 km","location":{"cityName":"Burdur","townName":"Merkez"},"category":{"id":18449,"name":"otomobil/fiat-linea-1-3-multijet-active-plus"},"modelName":"Linea 1.3 MultiJet Active Plus Manuel","price":88500,"priceFormatted":"88500 TL","date":"2020-12-23T00:00:00","dateFormatted":"23 Aralık 2020","photo":"https://arbstorage.mncdn.com/ilanfotograflari/2020/12/23/16251667/82e573fd-e345-4c55-bf13-091b3de06c19_image_for_silan_16251667_{0}.jpg","properties":[{"name":"km","value":"114350"},{"name":"color","value":"Beyaz"},{"name":"year","value":"2012"}]},{"id":16249630,"title":"Sahibinden Hyundai i30 1.6 GDi Style 2012 Model ","location":{"cityName":"İstanbul","townName":"Silivri"},"category":{"id":22387,"name":"otomobil/hyundai-i30-1-6-gdi-style"},"modelName":"i30 1.6 GDI Style Manuel","price":165000,"priceFormatted":"165000 TL","date":"2020-12-22T00:00:00","dateFormatted":"22 Aralık 2020","photo":"https://arbstorage.mncdn.com/ilanfotograflari/2020/12/22/16249630/3a3180b3-52bb-40ce-acfc-f72f38b96b4b_image_for_silan_16249630_{0}.jpg","properties":[{"name":"km","value":"130000"},{"name":"color","value":"Kahverengi"},{"name":"year","value":"2012"}]},{"id":16249527,"title":"Sahibinden Renault R 19 1.6 Europa RTE 2000 Model","location":{"cityName":"Adıyaman","townName":"Merkez"},"category":{"id":80769,"name":"otomobil/renault-r-19-1-6-europa-rte"},"modelName":"R19 Europa 1.6 RT Manuel","price":52500,"priceFormatted":"52500 TL","date":"2020-12-22T00:00:00","dateFormatted":"22 Aralık 2020","photo":"https://arbstorage.mncdn.com/ilanfotograflari/2020/12/22/16249527/22da8dfe-445b-47ff-8995-94f7dc6c3439_image_for_silan_16249527_{0}.jpg","properties":[{"name":"km","value":"225000"},{"name":"color","value":""},{"name":"year","value":"2000"}]},{"id":16249454,"title":"Sahibinden Hyundai i20 1.2 MPI Jump 2014 Model ","location":{"cityName":"Sakarya","townName":"Kocaali"},"category":{"id":21757,"name":"otomobil/hyundai-i20-1-2-mpi-jump"},"modelName":"i20 1.2 MPI Jump Manuel","price":120000,"priceFormatted":"120000 TL","date":"2020-12-22T00:00:00","dateFormatted":"22 Aralık 2020","photo":"https://arbstorage.mncdn.com/ilanfotograflari/2020/12/22/16249454/0e72ce3d-00bd-41d3-b704-5b97d6522065_image_for_silan_16249454_{0}.jpg","properties":[{"name":"km","value":"55500"},{"name":"color","value":""},{"name":"year","value":"2014"}]},{"id":16248481,"title":"Sahibinden Hyundai Accent 1.3 LS 1997 Model ","location":{"cityName":"Adana","townName":"Yüreğir"},"category":{"id":21630,"name":"otomobil/hyundai-accent-1-3-ls"},"modelName":"Accent 1.3 LS Servo Manuel","price":40000,"priceFormatted":"40000 TL","date":"2020-12-22T00:00:00","dateFormatted":"22 Aralık 2020","photo":"https://arbstorage.mncdn.com/ilanfotograflari/2020/12/22/16248481/bf74824b-f7d3-4079-9b6a-eb9360ae288c_image_for_silan_16248481_{0}.jpg","properties":[{"name":"km","value":"173000"},{"name":"color","value":"Mavi"},{"name":"year","value":"1997"}]},{"id":16248342,"title":"Sahibinden Peugeot 206 1.4 Comfort 2009 Model ","location":{"cityName":"Kayseri","townName":"Kocasinan"},"category":{"id":4265,"name":"otomobil/peugeot-206-1-4-comfort"},"modelName":"206 1.4 Comfort Manuel","price":68750,"priceFormatted":"68750 TL","date":"2020-12-22T00:00:00","dateFormatted":"22 Aralık 2020","photo":"https://arbstorage.mncdn.com/ilanfotograflari/2020/12/22/16248342/5b672e6d-9920-4338-bf7d-c1558ec66ae9_image_for_silan_16248342_{0}.jpg","properties":[{"name":"km","value":"127700"},{"name":"color","value":"Gri (Gümüş)"},{"name":"year","value":"2009"}]},{"id":16247491,"title":"2019+MODEL+FİAT+EGEA+1.3 EASY+%18FATURALI+HATASIZ+BOYASIZ","location":{"cityName":"Mersin","townName":"Yenişehir"},"category":{"id":17797,"name":"otomobil/fiat-egea-1-3-multijet-easy"},"modelName":"Egea 1.3 MultiJet Easy Manuel","price":149000,"priceFormatted":"149000 TL","date":"2020-12-22T00:00:00","dateFormatted":"22 Aralık 2020","photo":"https://arbstorage.mncdn.com/ilanfotograflari/2020/12/22/16247491/6cda92c5-b65e-47e1-b7b5-8067714f5cd8_image_for_silan_16247491_{0}.jpg","properties":[{"name":"km","value":"25000"},{"name":"color","value":"Beyaz"},{"name":"year","value":"2019"}]},{"id":16246847,"title":"Sahibinden Dacia Duster 1.5 dCi Prestige 2018 Model Ankara","location":{"cityName":"Ankara","townName":"Altındağ"},"category":{"id":71803,"name":"arazi-suv-pick-up/dacia-duster-1-5-dci-prestige"},"modelName":"Duster 1.5 DCI 4x4 Prestige Manuel","price":285000,"priceFormatted":"285000 TL","date":"2020-12-22T00:00:00","dateFormatted":"22 Aralık 2020","photo":"https://arbstorage.mncdn.com/ilanfotograflari/2020/12/22/16246847/dd1a0938-364a-4791-911a-9f2c4d928406_image_for_silan_16246847_{0}.jpg","properties":[{"name":"km","value":"39000"},{"name":"color","value":""},{"name":"year","value":"2018"}]}]
    """.trimIndent()

    val mockDetailJson = """
        {"id":7333920,"title":"VW BEETLE 14 TSİ RLİNE","location":{"cityName":"Adana","townName":"Seyhan"},"category":{"id":12799,"name":"otomobil/volkswagen-new-beetle-1-4-tsi-design"},"modelName":"Beetle 1.4 TSI Design DSG","price":350000,"priceFormatted":"350000 TL","date":"2020-11-30T00:00:00","dateFormatted":"30 Kasım 2020","photos":["https://arbstorage.mncdn.com/ilanfotograflari/2017/08/03/7333920/641e33f6-541f-40f3-8ba3-42960e563078_image_for_silan_7333920_{0}.jpg","https://arbstorage.mncdn.com/ilanfotograflari/2017/08/03/7333920/e99a0838-3f70-464c-86d6-cfee827fa5ad_image_for_silan_7333920_{0}.jpg"],"properties":[{"name":"km","value":"23000"},{"name":"color","value":"Kırmızı"},{"name":"year","value":"2013"},{"name":"gear","value":"Yarı Otomatik"},{"name":"fuel","value":"Benzin"}],"text":"ARACIMIZ İLK ELDEN OLUP DARBE DEGİSEN BOYA&nbsp; HASARKAYDİ YOKTUR İC DİS RLİNE PAKET OLUP EMSALSİZ TEMİZLİKTE VE 23000 KMDEDİR DETAYLİBİLGİ İCİN ARAYİNİZ&nbsp;","userInfo":{"id":2409290,"nameSurname":"Sonia Norris","phone":"9009315760","phoneFormatted":"9009315760"}}               
    """.trimIndent()

    val mockListingObject: ListingResponse = listOf(
        ListingResponseItem(
            category = Category(
                id = 12799,
                name = "otomobil/volkswagen-new-beetle-1-4-tsi-design"
            ),
            date = "2020-11-30T00:00:00",
            dateFormatted = "30 Kasım 2020",
            id = 7333920,
            location = Location(cityName = "Adana", townName = "Seyhan"),
            modelName = "Beetle 1.4 TSI Design DSG",
            photo = "https://arbstorage.mncdn.com/ilanfotograflari/2017/08/03/7333920/641e33f6-541f-40f3-8ba3-42960e563078_image_for_silan_7333920_{0}.jpg",
            price = 350000,
            priceFormatted = "350000 TL",
            properties = listOf(
                Property(name = "km", value = "23000"),
                Property(name = "color", value = "Kırmızı"),
                Property(name = "year", value = "2013")
            ),
            title = "VW BEETLE 14 TSİ RLİNE"
        ),
        ListingResponseItem(
            category = Category(
                id = 12212,
                name = "otomobil/volkswagen-scirocco-1-4-tsi-sportline"
            ),
            date = "2020-12-23T00:00:00",
            dateFormatted = "23 Aralık 2020",
            id = 16253376,
            location = Location(cityName = "İstanbul", townName = "Ümraniye"),
            modelName = "Scirocco 1.4 TSI Sportline Manuel",
            photo = "https://arbstorage.mncdn.com/ilanfotograflari/2020/12/23/16253376/8bef8606-dec3-47c1-9d53-a1862b110e9f_image_for_silan_16253376_{0}.jpg",
            price = 163000,
            priceFormatted = "163000 TL",
            properties = listOf(
                Property(name = "km", value = "72000"),
                Property(name = "color", value = "Beyaz"),
                Property(name = "year", value = "2012")
            ),
            title = "ORJINAL  MUAYENE SIFIR"
        ),
        ListingResponseItem(
            category = Category(
                id = 18449,
                name = "otomobil/fiat-linea-1-3-multijet-active-plus"
            ),
            date = "2020-12-23T00:00:00",
            dateFormatted = "23 Aralık 2020",
            id = 16251667,
            location = Location(cityName = "Burdur", townName = "Merkez"),
            modelName = "Linea 1.3 MultiJet Active Plus Manuel",
            photo = "https://arbstorage.mncdn.com/ilanfotograflari/2020/12/23/16251667/82e573fd-e345-4c55-bf13-091b3de06c19_image_for_silan_16251667_{0}.jpg",
            price = 88500,
            priceFormatted = "88500 TL",
            properties = listOf(
                Property(name = "km", value = "114350"),
                Property(name = "color", value = "Beyaz"),
                Property(name = "year", value = "2012")
            ),
            title = "Fiat Linea 1.3 Multijet Active Plus 2012 Model - 114.500 km"
        ),
        ListingResponseItem(
            category = Category(
                id = 22387,
                name = "otomobil/hyundai-i30-1-6-gdi-style"
            ),
            date = "2020-12-22T00:00:00",
            dateFormatted = "22 Aralık 2020",
            id = 16249630,
            location = Location(cityName = "İstanbul", townName = "Silivri"),
            modelName = "i30 1.6 GDI Style Manuel",
            photo = "https://arbstorage.mncdn.com/ilanfotograflari/2020/12/22/16249630/3a3180b3-52bb-40ce-acfc-f72f38b96b4b_image_for_silan_16249630_{0}.jpg",
            price = 165000,
            priceFormatted = "165000 TL",
            properties = listOf(
                Property(name = "km", value = "130000"),
                Property(name = "color", value = "Kahverengi"),
                Property(name = "year", value = "2012")
            ),
            title = "Sahibinden Hyundai i30 1.6 GDi Style 2012 Model "
        ),
        ListingResponseItem(
            category = Category(
                id = 80769,
                name = "otomobil/renault-r-19-1-6-europa-rte"
            ),
            date = "2020-12-22T00:00:00",
            dateFormatted = "22 Aralık 2020",
            id = 16249527,
            location = Location(cityName = "Adıyaman", townName = "Merkez"),
            modelName = "R19 Europa 1.6 RT Manuel",
            photo = "https://arbstorage.mncdn.com/ilanfotograflari/2020/12/22/16249527/22da8dfe-445b-47ff-8995-94f7dc6c3439_image_for_silan_16249527_{0}.jpg",
            price = 52500,
            priceFormatted = "52500 TL",
            properties = listOf(
                Property(name = "km", value = "225000"),
                Property(name = "color", value = ""),
                Property(name = "year", value = "2000")
            ),
            title = "Sahibinden Renault R 19 1.6 Europa RTE 2000 Model"
        ),
        ListingResponseItem(
            category = Category(
                id = 21757,
                name = "otomobil/hyundai-i20-1-2-mpi-jump"
            ),
            date = "2020-12-22T00:00:00",
            dateFormatted = "22 Aralık 2020",
            id = 16249454,
            location = Location(cityName = "Sakarya", townName = "Kocaali"),
            modelName = "i20 1.2 MPI Jump Manuel",
            photo = "https://arbstorage.mncdn.com/ilanfotograflari/2020/12/22/16249454/0e72ce3d-00bd-41d3-b704-5b97d6522065_image_for_silan_16249454_{0}.jpg",
            price = 120000,
            priceFormatted = "120000 TL",
            properties = listOf(
                Property(name = "km", value = "55500"),
                Property(name = "color", value = ""),
                Property(name = "year", value = "2014")
            ),
            title = "Sahibinden Hyundai i20 1.2 MPI Jump 2014 Model "
        ),
        ListingResponseItem(
            category = Category(
                id = 21630,
                name = "otomobil/hyundai-accent-1-3-ls"
            ),
            date = "2020-12-22T00:00:00",
            dateFormatted = "22 Aralık 2020",
            id = 16248481,
            location = Location(cityName = "Adana", townName = "Yüreğir"),
            modelName = "Accent 1.3 LS Servo Manuel",
            photo = "https://arbstorage.mncdn.com/ilanfotograflari/2020/12/22/16248481/bf74824b-f7d3-4079-9b6a-eb9360ae288c_image_for_silan_16248481_{0}.jpg",
            price = 40000,
            priceFormatted = "40000 TL",
            properties = listOf(
                Property(name = "km", value = "173000"),
                Property(name = "color", value = "Mavi"),
                Property(name = "year", value = "1997")
            ),
            title = "Sahibinden Hyundai Accent 1.3 LS 1997 Model "
        ),
        ListingResponseItem(
            category = Category(
                id = 4265,
                name = "otomobil/peugeot-206-1-4-comfort"
            ),
            date = "2020-12-22T00:00:00",
            dateFormatted = "22 Aralık 2020",
            id = 16248342,
            location = Location(cityName = "Kayseri", townName = "Kocasinan"),
            modelName = "206 1.4 Comfort Manuel",
            photo = "https://arbstorage.mncdn.com/ilanfotograflari/2020/12/22/16248342/5b672e6d-9920-4338-bf7d-c1558ec66ae9_image_for_silan_16248342_{0}.jpg",
            price = 68750,
            priceFormatted = "68750 TL",
            properties = listOf(
                Property(name = "km", value = "127700"),
                Property(name = "color", value = "Gri (Gümüş)"),
                Property(name = "year", value = "2009")
            ),
            title = "Sahibinden Peugeot 206 1.4 Comfort 2009 Model "
        ),
        ListingResponseItem(
            category = Category(
                id = 17797,
                name = "otomobil/fiat-egea-1-3-multijet-easy"
            ),
            date = "2020-12-22T00:00:00",
            dateFormatted = "22 Aralık 2020",
            id = 16247491,
            location = Location(cityName = "Mersin", townName = "Yenişehir"),
            modelName = "Egea 1.3 MultiJet Easy Manuel",
            photo = "https://arbstorage.mncdn.com/ilanfotograflari/2020/12/22/16247491/6cda92c5-b65e-47e1-b7b5-8067714f5cd8_image_for_silan_16247491_{0}.jpg",
            price = 149000,
            priceFormatted = "149000 TL",
            properties = listOf(
                Property(name = "km", value = "25000"),
                Property(name = "color", value = "Beyaz"),
                Property(name = "year", value = "2019")
            ),
            title = "2019+MODEL+FİAT+EGEA+1.3 EASY+%18FATURALI+HATASIZ+BOYASIZ"
        ),
        ListingResponseItem(
            category = Category(
                id = 71803,
                name = "arazi-suv-pick-up/dacia-duster-1-5-dci-prestige"
            ),
            date = "2020-12-22T00:00:00",
            dateFormatted = "22 Aralık 2020",
            id = 16246847,
            location = Location(cityName = "Ankara", townName = "Altındağ"),
            modelName = "Duster 1.5 DCI 4x4 Prestige Manuel",
            photo = "https://arbstorage.mncdn.com/ilanfotograflari/2020/12/22/16246847/dd1a0938-364a-4791-911a-9f2c4d928406_image_for_silan_16246847_{0}.jpg",
            price = 285000,
            priceFormatted = "285000 TL",
            properties = listOf(
                Property(name = "km", value = "39000"),
                Property(name = "color", value = ""),
                Property(name = "year", value = "2018")
            ),
            title = "Sahibinden Dacia Duster 1.5 dCi Prestige 2018 Model Ankara"
        ),
    )

    val mockDetailObject = DetailResponse(
        category = Category(
            id = 12799,
            name = "otomobil/volkswagen-new-beetle-1-4-tsi-design"
        ),
        date = "2020-11-30T00:00:00",
        dateFormatted = "30 Kasım 2020",
        id = 7333920,
        location = Location(
            cityName = "Adana",
            townName = "Seyhan"
        ),
        modelName = "Beetle 1.4 TSI Design DSG",
        photos = listOf(
            "https://arbstorage.mncdn.com/ilanfotograflari/2017/08/03/7333920/641e33f6-541f-40f3-8ba3-42960e563078_image_for_silan_7333920_{0}.jpg",
            "https://arbstorage.mncdn.com/ilanfotograflari/2017/08/03/7333920/e99a0838-3f70-464c-86d6-cfee827fa5ad_image_for_silan_7333920_{0}.jpg"
        ),
        price = 350000,
        priceFormatted = "350000 TL",
        properties = listOf(
            Property(name = "km", value = "23000"),
            Property(name = "color", value = "Kırmızı"),
            Property(name = "year", value = "2013"),
            Property(name = "gear", value = "Yarı Otomatik"),
            Property(name = "fuel", value = "Benzin")
        ),
        text = "ARACIMIZ İLK ELDEN OLUP DARBE DEGİSEN BOYA&nbsp; HASARKAYDİ YOKTUR İC DİS RLİNE PAKET OLUP EMSALSİZ TEMİZLİKTE VE 23000 KMDEDİR DETAYLİBİLGİ İCİN ARAYİNİZ&nbsp;",
        title = "VW BEETLE 14 TSİ RLİNE",
        userInfo = UserInfo(
            id = 2409290,
            nameSurname = "Sonia Norris",
            phone = "9009315760",
            phoneFormatted = "9009315760"
        ),
    )
}