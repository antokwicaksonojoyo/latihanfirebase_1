package com.antok.latihanfirebase_1

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import data_mahasiswa

//Class Adapter ini Digunakan Untuk Mengatur Bagaimana Data akan Ditampilkan
class RecyclerViewAdapter( private val listMahasiswa: ArrayList<data_mahasiswa>, context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private var context: Context

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val NIM: TextView
        val Nama: TextView
        val Jurusan: TextView
        val JenisKelamin : TextView
        val Semester : TextView
        val IPK : TextView
        val JenisBeasiswa : TextView
        val ListItem: LinearLayout

        init {//Menginisialisasi View yang terpasang pada layout RecyclerView kita
            NIM = itemView.findViewById(R.id.nimx)
            Nama = itemView.findViewById(R.id.namax)
            Jurusan = itemView.findViewById(R.id.jurusanx)
            JenisKelamin = itemView.findViewById(R.id.jeniskelaminx)
            Semester = itemView.findViewById(R.id.semesterx)
            IPK = itemView.findViewById(R.id.ipkx)
            JenisBeasiswa = itemView.findViewById(R.id.jenisbeasiswax)
            ListItem = itemView.findViewById(R.id.list_item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//Membuat View untuk Menyiapkan & Memasang Layout yang digunakan pada RecyclerView
        val V: View = LayoutInflater.from(parent.getContext()).inflate( R.layout.view_design, parent, false)
        return ViewHolder(V)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Mengambil Nilai/Value pada RecyclerView berdasarkan Posisi Tertentu
        val NIM: String? = listMahasiswa.get(position).nim
        val Nama: String? = listMahasiswa.get(position).nama
        val Jurusan: String? = listMahasiswa.get(position).jurusan
        val JenisKelamin: String? = listMahasiswa.get(position).jk
        val Semester: String? = listMahasiswa.get(position).semester
        val IPK: String? = listMahasiswa.get(position).ipk
        val JenisBeasiswa: String? = listMahasiswa.get(position).jenisbeasiswa

        //Memasukan Nilai/Value kedalam View (TextView: NIM, Nama, Jurusan)
        holder.NIM.text = "NIM: $NIM"
        holder.Nama.text = "Nama: $Nama"
        holder.Jurusan.text = "Jurusan: $Jurusan"
        holder.JenisKelamin.text = "JenisKelamin: $JenisKelamin"
        holder.Semester.text = "Semester: $Semester"
        holder.IPK.text = "IPK: $IPK"
        holder.JenisBeasiswa.text = "JenisBeasiswa: $JenisBeasiswa"
        holder.ListItem.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
//Kodingan untuk fungsi Edit dan Delete, yang dibahas pada Tutorial Berikutnya.
                holder.ListItem.setOnLongClickListener { view ->
                    val action = arrayOf("Update", "Delete")
                    val alert: AlertDialog.Builder = AlertDialog.Builder(view.context)
                    alert.setItems(action, DialogInterface.OnClickListener { dialog, i ->
                        when (i) {
                            0 -> {
                                /* Berpindah Activity pada halaman layout updateData dan mengambil data pada listMahasiswa, berdasarkan posisinya untuk dikirim pada activity selanjutnya */
                                val bundle = Bundle()
                                bundle.putString("dataNIM", listMahasiswa[position].nim)
                                bundle.putString("dataNama", listMahasiswa[position].nama)
                                bundle.putString("dataJurusan", listMahasiswa[position].jurusan)
                                bundle.putString("dataJenisKelamin", listMahasiswa[position].jk)
                                bundle.putString("dataSemester", listMahasiswa[position].semester)
                                bundle.putString("dataIPK", listMahasiswa[position].ipk)
                                bundle.putString("dataJenisBeasiswa", listMahasiswa[position].jenisbeasiswa)
                                bundle.putString("getPrimaryKey", listMahasiswa[position].key)
                                val intent = Intent(view.context, UpdateData::class.java)
                                intent.putExtras(bundle)
                                context.startActivity(intent)
                            }
                            1 -> {
                                //Menggunakan interface untuk mengirim data mahasiswa, yang akan dihapus
                                listener?.onDeleteData(listMahasiswa.get(position), position)
                            }
                        }
                    })
                    alert.create()
                    alert.show()
                    true
                }

                return true
            }
        })
    }

    override fun getItemCount(): Int {
        //Menghitung Ukuran/Jumlah Data Yang Akan Ditampilkan Pada RecyclerView
        return listMahasiswa.size
    }

    //Membuat Konstruktor, untuk menerima input dari Database
    init {
        this.context = context
    }
    //Membuat Interfece
    interface dataListener {
        fun onDeleteData(data: data_mahasiswa?, position: Int)
    }

    //Deklarasi objek dari Interfece
    var listener: dataListener? = null

    //Membuat Konstruktor, untuk menerima input dari Database
    init {
        this.context = context
        this.listener = context as MyListData  //menambahkan baris ini saja
    }


}
