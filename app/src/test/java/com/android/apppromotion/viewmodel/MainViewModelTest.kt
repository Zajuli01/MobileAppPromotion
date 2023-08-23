package com.android.apppromotion.viewmodel

import com.android.apppromotion.model.Formats
import com.android.apppromotion.model.Img
import com.android.apppromotion.model.Medium
import com.android.apppromotion.model.PromoModel
import com.android.apppromotion.network.ApiService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModelTest {

    private lateinit var apiService: ApiService

    @Before
    fun setup() {
        // Inisialisasi Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://content.digi46.id/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Inisialisasi ApiService
        apiService = retrofit.create(ApiService::class.java)
    }

    @Test
    fun testPromosData() = runBlocking {
        // Panggil API untuk mendapatkan data promosi
        val actualPromos: List<PromoModel> = apiService.getPromos()

        // Buat data dummy yang sama
        val expectedPromos: List<PromoModel> = listOf(
            PromoModel("BNI Credit Card", "Potongan langsung (diskon) Rp 150.000,- untuk minimal transaksi Rp 1.000.000, kuota 15 transaksi pertama per hari.\n- Berlaku tiap Kamis dan Jumat.\n- Berlaku untuk pembelian Tiket Sriwijaya Air dan NAM Air di Website dan Mobile Apps Sriwijaya Air\n- Potongan harga langsung diperoleh ketika nomor Kartu BNI dimasukkan (tanpa kode promo)\n- Syarat dan ketentuan berlaku\nInfo lebih lanjut hubungi BNI Call 1500046", Img(Formats(Medium("https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/medium_bni_credit_card_banner_fitur_mbanking_small_b8d822ed1c.jpg", ".jpg")))),
            PromoModel("BNI QRIS", "Nikmati bayar tunai tanpa kartu di bni mobile banking, anda bisa scan qris pada semua merchant yang ada di merchant yang berpartisipasi pada MCC Groceries, Pharmacies, dan Minimarket.\nInfo lebih lanjut hubungi BNI Call 1500046", Img(Formats(Medium("https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/medium_bni_banner_qris_mudah_2020_small_8f0e73341a.jpg", ".jpg")))),
            PromoModel("BNI SMS Notifikasi", "Aktifkan segera sms notifikasi untuk menambah keamanan transaksimu.\nInfo lebih lanjut hubungi BNI Call 1500046", Img(Formats(Medium("https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/medium_bni_sms_notifikasi_2020_small_3f0447da34.jpg", ".jpg")))),
                    PromoModel("BNI Taplus Poin Plus", "Tukarkan BNI Rewards Point dengan voucher belanja TipTop minimal 10.000 BNI Rewards Point atau senilai Rp. 50.000,-\nPenukaran BNI Rewards Point dilakukan di mesin EDC yang ada di Customer Service TipTop. Berlaku setiap hari termasuk hari libur nasional.\nInfo lebih lanjut hubungi BNI Call 1500046", Img(Formats(Medium("https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/medium_promo_bni_point_plus_januari_2021_small_1fb82bf7c6.jpg", ".jpg")))),
        PromoModel("BNI Kartu Kredit", "Transaksi lebih leluasa karena bayarnya lebih ringan", Img(Formats(Medium("https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/medium_Cicilan_0_Top_Merchant_7b7948916b.jpg", ".jpg")))),
        PromoModel("BNI Kartu Kredit", "Menangkan undian dengan apply kartu kredit via bni mobile banking", Img(Formats(Medium("https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/medium_bni_credit_card_apply_kk_via_mobile_small_e1e8aaf172.jpg", ".jpg"))))

        // Tambahkan data dummy sesuai dengan yang diharapkan
        )

        // Bandingkan data aktual dengan data dummy
        assertEquals(expectedPromos.size, actualPromos.size)

        for (i in expectedPromos.indices) {
            val expected = expectedPromos[i]
            val actual = actualPromos[i]

            assertEquals(expected.nama, actual.nama)
            assertEquals(expected.desc, actual.desc)
            assertEquals(expected.img.formats?.medium?.url, actual.img.formats?.medium?.url)
            assertEquals(expected.img.formats?.medium?.ext, actual.img.formats?.medium?.ext)
        }
    }
}
