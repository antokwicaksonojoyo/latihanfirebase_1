class data_mahasiswa {
    //Deklarasi Variable
    var nim: String? = null
    var nama: String? = null
    var jurusan: String? = null
    var key: String? = null
    var jk: String? = null
    var semester: String? = null
    var ipk: String? = null
    var jenisbeasiswa: String? = null

    //Membuat Konstuktor kosong untuk membaca data snapshot
    constructor() {}

    //Konstruktor dengan beberapa parameter, untuk mendapatkan Input Data dari User
    constructor(nim: String?, nama: String?, jurusan: String?, jk: String, semester: String, ipk: String, jenisbeasiswa: String) {
        this.nim = nim
        this.nama = nama
        this.jurusan = jurusan
        this.jk = jk
        this.semester = semester
        this.ipk = ipk
        this.jenisbeasiswa = jenisbeasiswa
    }
}
