package org.pyjjs.scheduler.core.common.locale

import java.io.IOException
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class LocaleResolver private constructor(language: LangResolver) {

    private val properties: Properties

    init {
        properties = Properties()
        val propFileName = language.fileName
        properties.load(javaClass.classLoader.getResourceAsStream(propFileName))
    }

    fun values(): Properties {
        return properties
    }

    fun getString(key: String, vararg params: Any): String {
        val rawString: String = properties.getProperty(key)
        val byteArray = rawString.toByteArray(charset("ISO-8859-1"))
        return String.format(byteArray.toString(charset("UTF-8")), *params)
    }

    companion object {
        private val instance = ConcurrentHashMap<String, LocaleResolver>()

        @Throws(LocaleNotFoundException::class)
        operator fun get(langResolver: LangResolver): LocaleResolver {
            var localeResolver: LocaleResolver? = instance[langResolver.shortLang]
            if (localeResolver == null) {
                synchronized(LocaleResolver::class.java) {
                    localeResolver = instance[langResolver.shortLang]
                    if (localeResolver == null) {
                        try {
                            localeResolver = LocaleResolver(langResolver)
                        } catch (e: IOException) {
                            throw LocaleNotFoundException(e.message ?: "Locale not found")
                        }

                        instance.put(langResolver.shortLang, localeResolver as LocaleResolver)
                    }
                }
            }
            return localeResolver as LocaleResolver
        }
    }
}
