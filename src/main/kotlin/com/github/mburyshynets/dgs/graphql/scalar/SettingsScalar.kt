package com.github.mburyshynets.dgs.graphql.scalar

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.mburyshynets.dgs.data.Setting
import com.netflix.graphql.dgs.DgsScalar
import graphql.language.ArrayValue
import graphql.language.EnumValue
import graphql.language.StringValue
import graphql.language.Value
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingSerializeException
import java.util.EnumSet

@DgsScalar(name = "Settings")
class SettingsScalar(private val objectMapper: ObjectMapper) : Coercing<EnumSet<Setting>, List<String>> {

    override fun serialize(dataFetcherResult: Any): List<String> {
        if (dataFetcherResult is EnumSet<*>) {
            return dataFetcherResult.map { it.name }
        }
        throw CoercingSerializeException("Not a valid EnumSet<Setting>")
    }

    override fun parseValue(input: Any): EnumSet<Setting> {
        return input.toString()
            .let { objectMapper.readValue<Array<Setting>>(it) }
            .let { EnumSet.of(it.head, *it.tail) }
    }

    override fun parseLiteral(input: Any): EnumSet<Setting> {
        return when (input) {
            is ArrayValue -> input.values
                .map { extractStringValue(it) }
                .map { parseSettingItem(it) }
                .toEnumSet()

            else -> throw CoercingParseLiteralException(
                "Expected AST type 'ArrayValue' but was '${input::class.simpleName}'."
            )
        }
    }

    private fun extractStringValue(input: Value<*>): String {
        return when (input) {
            is StringValue -> {
                input.value
            }

            is EnumValue -> {
                input.name
            }

            else -> {
                throw CoercingParseLiteralException("Expected value to be a String but it was '$input'")
            }
        }
    }

    private fun parseSettingItem(input: String): Setting {
        return try {
            Setting.valueOf(input)
        } catch (e: IllegalArgumentException) {
            throw CoercingParseLiteralException(e.message)
        }
    }

    private inline fun <reified T : Enum<T>> Iterable<T>.toEnumSet(): EnumSet<T> {
        val result = EnumSet.noneOf(T::class.java)
        for (item in this) {
            result.add(item)
        }
        return result
    }

    private final inline val <T> Array<T>.head: T
        get() = get(0)

    private final inline val <reified T> Array<T>.tail: Array<T>
        get() = if (isNotEmpty()) copyOfRange(1, size - 1) else emptyArray()
}
