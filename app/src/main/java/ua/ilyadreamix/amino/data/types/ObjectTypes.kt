package ua.ilyadreamix.amino.data.types

enum class ObjectTypes(val type: Int) {
    USER(0),
    BLOG(1),
    ITEM(2),
    COMMENT(3),
    BLOG_CATEGORY(4),
    BLOG_CATEGORY_ITEM_TAG(5),
    FEATURED_ITEM(6),
    CHAT_MESSAGE(7),

    REPUTATIONLOG_ITEM(10),
    POLL_OPTION(11),
    CHAT_THREAD(12),
    COMMUNITY(16),

    IMAGE(100),
    MUSIC(101),
    VIDEO(102),
    YOUTUBE(103)
}