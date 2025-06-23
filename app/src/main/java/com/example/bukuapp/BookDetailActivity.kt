
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bookId = intent.getIntExtra(EXTRA_BOOK_ID, -1)
        if (bookId == -1) {
            finish()
            return
        }

        val book = Book.dummyBooks.find { it.id == bookId }
        book?.let { displayBookDetails(it) }
    }

    private fun displayBookDetails(book: Book) {
        // Load book image from drawable
        findViewById<android.widget.ImageView>(R.id.bookImage).setImageResource(book.imageResId)

        // Set book details
        findViewById<android.widget.TextView>(R.id.bookTitle).text = book.title
        findViewById<android.widget.TextView>(R.id.bookAuthor).text = book.author

        // Format price in Indonesian Rupiah
        val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        findViewById<android.widget.TextView>(R.id.bookPrice).text = formatter.format(book.price)

        findViewById<android.widget.TextView>(R.id.bookDescription).text = book.description

        // Set up WhatsApp order button
        findViewById<MaterialButton>(R.id.orderButton).setOnClickListener {
            val message = "Saya ingin pesan buku ${book.title}"
            val encodedMessage = Uri.encode(message)
            val whatsappUrl = "https://wa.me/6281290104184?text=$encodedMessage"

            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(whatsappUrl)
            }
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}