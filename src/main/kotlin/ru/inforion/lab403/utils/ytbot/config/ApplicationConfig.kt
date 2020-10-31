package ru.inforion.lab403.utils.ytbot.config

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ru.inforion.lab403.common.extensions.parseJson
import ru.inforion.lab403.common.extensions.toFile
import ru.inforion.lab403.utils.ytbot.youtrack.CategoryId
import java.io.File


data class ApplicationConfig constructor(
    val proxy: ProxyConfig?,
    val youtrack: YoutrackConfig,
    val projects: List<ProjectConfig>,
    val minutesGroupInterval: Int,
    val timestampFilePath: String,
    val descriptionMaxChars: Int,
    val taggedCustomFields: List<String>,
    val userCustomFields: List<String>,
    val users: Map<String, TelegramUserConfig>?,
    val activityCategories: List<CategoryId>?,
    val filterIssues: List<String>?
) {
    companion object {
        fun load(file: File): ApplicationConfig = file.inputStream().parseJson()
    }

    fun isCategoryActive(categoryId: CategoryId) =
        if (activityCategories == null) true else categoryId in activityCategories

    fun saveTimestamp(timestamp: Long) {
        File(timestampFilePath).writeText(timestamp.toString())
    }

    fun loadTimestamp(): Long {
        val file = File(timestampFilePath)
        if (!file.canRead())
            return 0
        return file.readText().toLong()
    }
}