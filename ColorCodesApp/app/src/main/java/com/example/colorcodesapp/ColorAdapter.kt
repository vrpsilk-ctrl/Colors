class ColorAdapter(
    private val originalList: List<ColorItem>,
    private val onClick: (ColorItem) -> Unit
) : RecyclerView.Adapter<ColorAdapter.VH>(), Filterable {

    var filteredList = originalList.toMutableList()

    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameTv: TextView = itemView.findViewById(R.id.tvName)
        val hexTv: TextView = itemView.findViewById(R.id.tvHex)
        val container: View = itemView.findViewById(R.id.container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = filteredList[position]
        holder.nameTv.text = item.name
        holder.hexTv.text = item.hex
        holder.container.setBackgroundColor(
            ContextCompat.getColor(holder.container.context, item.colorRes)
        )
        holder.itemView.setOnClickListener { onClick(item) }
    }

    override fun getItemCount() = filteredList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val text = constraint?.toString()?.lowercase(Locale.getDefault()) ?: ""
                val filtered = if (text.isEmpty()) {
                    originalList
                } else {
                    originalList.filter {
                        it.name.lowercase(Locale.getDefault()).contains(text)
                            || it.hex.lowercase(Locale.getDefault()).contains(text)
                    }
                }
                return FilterResults().apply { values = filtered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = (results?.values as? List<ColorItem>)?.toMutableList() ?: mutableListOf()
                notifyDataSetChanged()
            }
        }
    }
}
