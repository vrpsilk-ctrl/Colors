class MainActivity : AppCompatActivity() {
    lateinit var adapter: ColorAdapter
    val colors = mutableListOf<ColorItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadColorsFromResources()

        adapter = ColorAdapter(colors) { colorItem ->
            FullscreenActivity.start(this, colorItem)
        }

        val rv = findViewById<RecyclerView>(R.id.rvColors)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        val search = findViewById<SearchView>(R.id.searchView)
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return true
            }
            override fun onQueryTextSubmit(query: String) = true
        })
    }

    private fun loadColorsFromResources() {
        val fields = R.color::class.java.declaredFields
        for (field in fields) {
            val name = field.name
            val colorRes = field.getInt(null)
            val hex = String.format("#%06X", 0xFFFFFF and ContextCompat.getColor(this, colorRes))
            colors.add(ColorItem(name.capitalize(), hex, colorRes))
        }
    }
}
