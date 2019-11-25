package com.migreat.giphy.model

data class Gif(
    val original: Original,
    val preview: Preview
) {
    data class Original(val url: String)
    data class Preview(val url: String)
}
