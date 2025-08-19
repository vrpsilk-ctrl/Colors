class FullscreenActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_NAME = "EXTRA_NAME"
        private const val EXTRA_HEX = "EXTRA_HEX"
        private const val EXTRA_COLOR = "EXTRA_COLOR"

        fun start(context: Context, item: ColorItem) {
            val intent = Intent(context, FullscreenActivity::class.java)
            intent.putExtra(EXTRA_NAME, item.name)
            intent.putExtra(EXTRA_HEX, item.hex)
            intent.putExtra(EXTRA_COLOR, item.colorRes)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)

        val name = intent.getStringExtra(EXTRA_NAME)
        val hex = intent.getStringExtra(EXTRA_HEX)
        val colorRes = intent.getIntExtra(EXTRA_COLOR, -1)

        findViewById<View>(R.id.fullscreen_container)
            .setBackgroundColor(ContextCompat.getColor(this, colorRes))
        findViewById<TextView>(R.id.tvFullName).text = name
        findViewById<TextView>(R.id.tvFullHex).text = hex
    }
}
