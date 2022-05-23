package com.example.sinemalar.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sinemalar.R
import com.example.sinemalar.adapter.FilmAdapter
import com.example.sinemalar.service.OmdbService
import com.example.sinemalar.utils.Constants
import com.weatherapp.models.OmdbApiData
import kotlinx.android.synthetic.main.fragment_film_searching.*
import retrofit.*


class FilmSearchingFragment : Fragment() {
    lateinit var btnSearch : ImageButton /// Search iconunun bulunduğu buton
    lateinit var etFilmName: EditText /// kullanıcının giriş yapacağı textbox
    lateinit var rcyclerview:RecyclerView //// bulunan bir ya da daha fazla OmdbApiData sınıfının gösterileceği viewer
    val filmler = arrayListOf<OmdbApiData>() ///// OmdbApiData data sınıfından oluşan bir dizi.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_film_searching, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        (activity as AppCompatActivity).supportActionBar?.show() // actionbar görünsün
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false) // actionbar backward oku kullanılmasın

        val colorDrawable = ColorDrawable(Color.parseColor("#590D22")) // 293241
        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(colorDrawable) // actionbar uygun renk seçimi

        btnSearch = view.findViewById(R.id.ibSearch)
        etFilmName = view.findViewById(R.id.etNameInput)
        rcyclerview = view.findViewById(R.id.filmList)
        (activity as AppCompatActivity).supportActionBar?.title ="Sinema"

        /// arama butonuna basıldığında
        btnSearch.setOnClickListener {
            if(etFilmName.text.isNotEmpty()){ // eğer arama kutucuğu boş değilse
                filmler.clear() // omdbApiData değişkenlerinin tutulduğu dizi temizlenir
                tvfilmError.visibility = View.GONE // Error uyarı mesajı saklanır
                pbFilmListLoading.visibility = View.VISIBLE // loading animasyonu kullanıcıya gösterilir
                getFilmData(etFilmName.text.toString()) // film dataları api den çekilmek üzere film adı uygun fonksiyona yollanır.

            }else{ // eğer arama kutucuğu boş ise
                tvfilmError.visibility = View.VISIBLE // error mesajı gösterilir
                tvfilmError.text = "You should write a movie name!!!" // hataya uygun mesaj ayarlaması yapılır.
            }
        }

    }

    override fun onResume() {
        super.onResume()
        etFilmName.setText("") // film detay sayfasına gidilip dönüldüğünde
        etFilmName.hint = "Search a movie"
    }

    private fun getFilmData(t:String){ // web service den veriyi çekme fonksiyonu.
        val retrofit: Retrofit = Retrofit.Builder() // retrofit build ediyoruz
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: OmdbService = // retrofitin uygun data sınıfı ile bağlanması.
            retrofit.create(OmdbService::class.java)
        val listCall: Call<OmdbApiData> = service.getFilm( // retrofitin apikey i kullanarak webten istekte bulunması
            apikey = Constants.APP_ID, t = t
        )
        listCall.enqueue(object : Callback<OmdbApiData> { // webden gelen bilgilerin çekilmesi
            override fun onResponse(response: Response<OmdbApiData>, retrofit: Retrofit) {

                if (response.isSuccess) { // eğer veri çekme işlemi başarıllı ise
                    filmler.add(response.body()) // dönen objeyi sınıf içerisine alma
                    // rcyclerview adapterine gelen veriyi iletme ve verinin nasıl gösterileceğini söyleme.
                    rcyclerview.apply {
                        if(response.body().response == "True"){ // eğer bir dönüş alınabildiyse
                            filmList.visibility = View.VISIBLE
                            layoutManager =LinearLayoutManager(context)
                            adapter = FilmAdapter(filmler)

                        }else{ // eğer bir dönüş alınamadıysa.
                            filmList.visibility = View.GONE // film listesini görünmez yapar
                            tvfilmError.visibility = View.VISIBLE // hata mesajını duruma uygun revize ederek gösterir.
                            tvfilmError.text = "Searched movie not found"
                        }

                        pbFilmListLoading.visibility = View.GONE // eğer arama sonuçlanmış ise loading animasyonunu kaldırır.

                    }

                } else { // responsun başarısız olma durumunda uygun hata kodları ile hatanın tespit edilmesi.
                    pbFilmListLoading.visibility = View.GONE
                    val sc = response.code()
                    when (sc) {
                        400 -> {
                            Log.e("Error 400", "Bad Request")
                            tvfilmError.text = "Bad Request"
                        }
                        404 -> {
                            Log.e("Error 404", "Not Found")
                            tvfilmError.text = "Not Found"
                        }
                        else -> {
                            Log.e("Error", "Generic Error")
                            tvfilmError.text = "Generic Error"
                        }
                    }
                }
            }
            override fun onFailure(t: Throwable) {
                Log.e("Error!!!", t.message.toString())
            }

        })
    }


}