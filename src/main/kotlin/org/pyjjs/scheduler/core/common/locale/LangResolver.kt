package org.pyjjs.scheduler.core.common.locale

class LangResolver(val shortLang: String) {
    val fileName: String
    init {
        fileName = String.format(LOCALE_FILE_NAME, shortLang)
    }

    companion object {
        private val LOCALE_FILE_NAME = "locale_%s.properties"
    }
}
