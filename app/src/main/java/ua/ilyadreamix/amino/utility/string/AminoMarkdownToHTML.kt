package ua.ilyadreamix.amino.utility.string

private object AminoMarkdownTags {
    const val BOLD = 'b'
    const val ITALIC = 'i'
    const val CENTER = 'c'
    const val UNDERLINE = 'u'
    const val STRIKE = 's'
    const val IMAGE = "img"
}

data class MarkdownInfo(
    val value: String,
    val type: Int
)

fun String.fromAminoMarkdownToHTMLMarkdown(): List<MarkdownInfo> {
    val htmlImgList = mutableListOf<MarkdownInfo>()

    this.split("\n").forEach { line ->
        if (line.startsWith("[")) {
            val aminoMarkdown = """\[(.*?)]""".toRegex().find(line)
            val aminoTags = aminoMarkdown!!.groupValues[1].lowercase()

            val lineWithoutAminoMarkdown = line.replace("""\[[^]]*]""".toRegex(), "")

            if (AminoMarkdownTags.IMAGE in aminoTags) { // Image tag - MD type 1
                val imgMarkdown = """\[(.*?)]""".toRegex().find(line)
                val imgTag = imgMarkdown!!.groupValues[1]

                htmlImgList.add(MarkdownInfo(imgTag.split("=")[1], 1))
            }
            else if (AminoMarkdownTags.CENTER in aminoTags) { // Center tag - MD type 2
                if (lineWithoutAminoMarkdown != "") {
                    var htmlLine = ""
                    val aminoTagsWithoutCenter = aminoTags.replace(
                        AminoMarkdownTags.CENTER.toString(),
                        ""
                    )

                    aminoTagsWithoutCenter.forEach { prefix ->
                        htmlLine += when (prefix) {
                            AminoMarkdownTags.BOLD -> "<b>"
                            AminoMarkdownTags.ITALIC -> "<i>"
                            AminoMarkdownTags.UNDERLINE -> "<u>"
                            AminoMarkdownTags.STRIKE -> "<s>"
                            else -> ""
                        }
                    }

                    htmlLine += lineWithoutAminoMarkdown

                    aminoTagsWithoutCenter.reversed().replace(
                        AminoMarkdownTags.CENTER.toString(),
                        ""
                    ).forEach { postfix ->
                        htmlLine += when (postfix) {
                            AminoMarkdownTags.BOLD -> "</b>"
                            AminoMarkdownTags.ITALIC -> "</i>"
                            AminoMarkdownTags.UNDERLINE -> "</u>"
                            AminoMarkdownTags.STRIKE -> "</s>"
                            else -> ""
                        }
                    }

                    htmlImgList.add(
                        MarkdownInfo(
                            value = htmlLine,
                            type = 2
                        )
                    )
                } else htmlImgList.add(
                    MarkdownInfo(
                        value = "\n",
                        type = 2
                    )
                )
            }
            else { // Default tag - MD type 0
                if (lineWithoutAminoMarkdown != "") {
                    var htmlLine = ""

                    aminoTags.forEach { prefix ->
                        htmlLine += when (prefix) {
                            AminoMarkdownTags.BOLD -> "<b>"
                            AminoMarkdownTags.ITALIC -> "<i>"
                            AminoMarkdownTags.UNDERLINE -> "<u>"
                            AminoMarkdownTags.STRIKE -> "<s>"
                            else -> ""
                        }
                    }

                    htmlLine += lineWithoutAminoMarkdown

                    aminoTags.reversed().replace(
                        AminoMarkdownTags.CENTER.toString(),
                        ""
                    ).forEach { postfix ->
                        htmlLine += when (postfix) {
                            AminoMarkdownTags.BOLD -> "</b>"
                            AminoMarkdownTags.ITALIC -> "</i>"
                            AminoMarkdownTags.UNDERLINE -> "</u>"
                            AminoMarkdownTags.STRIKE -> "</s>"
                            else -> ""
                        }
                    }

                    htmlImgList.add(
                        MarkdownInfo(
                            value = htmlLine,
                            type = 0
                        )
                    )
                } else htmlImgList.add(
                    MarkdownInfo(
                        value = "\n",
                        type = 0
                    )
                )
            }
        } else htmlImgList.add(
            MarkdownInfo(
                value = "$line\n",
                type = 0
            )
        )
    }

    return htmlImgList.distinct().toList()
}