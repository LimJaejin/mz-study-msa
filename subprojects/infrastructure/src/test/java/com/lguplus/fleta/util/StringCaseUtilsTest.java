package com.lguplus.fleta.util;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.*;

import static org.assertj.core.api.Assertions.*;

/**
 * StringCaseUtils 유닛 테스트
 * @version 1.0
 */
class StringCaseUtilsTest {

    @Test
    void constructor() throws NoSuchMethodException {
        // Given
        Constructor<StringCaseUtils> constructor = StringCaseUtils.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        // When
        ThrowableAssert.ThrowingCallable callable = constructor::newInstance;
        // Then
        assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();
        assertThatThrownBy(callable).isInstanceOf(InvocationTargetException.class);
    }

    @ParameterizedTest
    @CsvSource({
        "aBc_DeF, AbcDef",
        "aBcDeF, ABcDeF",
        "ABCDEF, Abcdef",
        "abcdef, Abcdef",
    })
    void autoPascalCase(String given, String expected) {
        // When
        String actual = StringCaseUtils.autoPascalCase(given);
        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void camelCaseToSnakeCase() {
        // Given
        String str = "AbcDef";
        // When
        String actual = StringCaseUtils.camelCaseToSnakeCase(str);
        // Then
        assertThat(actual).isEqualTo("ABC_DEF");
    }

    @Test
    void camelCaseToSnakeCase_lowerCase() {
        // Given
        String str = "AbcDef";
        // When
        String actual = StringCaseUtils.toLowerCamelCaseToSnakeCase(str);
        // Then
        assertThat(actual).isEqualTo("abc_def");
    }
}