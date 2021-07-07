package com.antok.latihanfirebase_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import data_mahasiswa
import kotlinx.android.synthetic.main.activity_update_data.*

class UpdateData : AppCompatActivity() {
    //Deklarasi Variable
    private var database: DatabaseReference? = null
    private var auth: FirebaseAuth? = null
    private var cekNIM: String? = null
    private var cekNama: String? = null
    private var cekJurusan: String? = null
    private var cekJK: String? = null
    private var cekIPK: String? = null
    private var cekSemester: String? = null
    private var cekJenisBeasiswa: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)
        supportActionBar!!.title = "Update Data"

        //Mendapatkan Instance autentikasi dan Referensi dari Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        data //memanggil method "data"
        update.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                //Mendapatkan Data Mahasiswa yang akan dicek
                cekNIM = new_nim.getText().toString()
                cekNama = new_nama.getText().toString()
                cekJurusan = new_jurusan.getText().toString()
                cekJK = new_jeniskelamin.getText().toString()
                cekIPK = new_IPK.getText().toString()
                cekSemester = new_semester.getText().toString()
                cekJenisBeasiswa = new_jenisbeasiswa.getText().toString()

                //Mengecek agar tidak ada data yang kosong, saat proses update
                if (isEmpty(cekNIM!!) || isEmpty(cekNama!!) || isEmpty(cekJurusan!!) || isEmpty(cekJK!!)  || isEmpty(cekIPK!!) || isEmpty(cekSemester!!) || isEmpty(cekJenisBeasiswa!!) ) {
                    Toast.makeText(
                            this@UpdateData,
                            "Data tidak boleh ada yang kosong",
                            Toast.LENGTH_SHORT
                    ).show()
                } else {
                    /*Menjalankan proses update data.
        Method Setter digunakan untuk mendapakan data baru yang diinputkan User.*/
                    val setMahasiswa = data_mahasiswa()
                    setMahasiswa.nim = new_nim.getText().toString()
                    setMahasiswa.nama = new_nama.getText().toString()
                    setMahasiswa.jurusan = new_jurusan.getText().toString()
                    setMahasiswa.jk = new_jeniskelamin.getText().toString()
                    setMahasiswa.semester = new_semester.getText().toString()
                    setMahasiswa.jenisbeasiswa = new_jenisbeasiswa.getText().toString()
                    updateMahasiswa(setMahasiswa)
                }
            }
        })
    }

    // Mengecek apakah ada data yang kosong, sebelum diupdate
    private fun isEmpty(s: String): Boolean {
        return TextUtils.isEmpty(s)
    }

    //Menampilkan data yang akan di update
    private val data: Unit
        private get() {
            //Menampilkan data dari item yang dipilih sebelumnya
            val getNIM = intent.extras!!.getString("dataNIM")
            val getNama = intent.extras!!.getString("dataNama")
            val getJurusan = intent.extras!!.getString("dataJurusan")
            val getJK = intent.extras!!.getString("dataJK")
            val getSemester = intent.extras!!.getString("dataSemester")
            val getIPK = intent.extras!!.getString("dataIPK")
            val getJenisBeasiswa = intent.extras!!.getString("dataJenisBeasiswa")
            new_nim!!.setText(getNIM)
            new_nama!!.setText(getNama)
            new_jurusan!!.setText(getJurusan)
            new_jeniskelamin!!.setText(getJK)
            new_semester!!.setText(getSemester)
            new_IPK!!.setText(getIPK)
            new_jenisbeasiswa!!.setText(getJenisBeasiswa)
        }

    //Proses Update data yang sudah ditentukan
    private fun updateMahasiswa(mahasiswa: data_mahasiswa) {
        val userID = auth!!.uid
        val getKey = intent.extras!!.getString("getPrimaryKey")
        database!!.child("Admin")
                .child(userID!!)
                .child("Mahasiswa")
                .child(getKey!!)
                .setValue(mahasiswa)
                .addOnSuccessListener {
                    new_nim!!.setText("")
                    new_nama!!.setText("")
                    new_jurusan!!.setText("")
                    new_jeniskelamin!!.setText("")
                    new_semester!!.setText("")
                    new_IPK!!.setText("")
                    new_jenisbeasiswa.setText("")
                    Toast.makeText(this@UpdateData, "Data Berhasil diubah", Toast.LENGTH_SHORT).show()
                    intent = Intent(applicationContext, MyListData::class.java)
                    startActivity(intent)
                    finish()
                }
    }
}
