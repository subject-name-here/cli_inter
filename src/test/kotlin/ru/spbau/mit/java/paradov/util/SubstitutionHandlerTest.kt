package ru.spbau.mit.java.paradov.util

import org.junit.Test

import org.junit.Assert.*
import ru.spbau.mit.java.paradov.scope.Scope

class SubstitutionHandlerTest {

    @Test
    fun testSubstitute1() {
        val scope = Scope()
        val s = "\$a\$b\$c\$d"
        assertEquals("", substitute(scope, s))
    }

    @Test
    fun testSubstitute2() {
        val scope = Scope()
        scope.set("a", "value")
        val s = "\$a"
        assertEquals("value", substitute(scope, s))
    }

    // Test that explains why there is a reversed sorting in substitution.
    @Test
    fun testSubstitute3() {
        val scope = Scope()
        scope.set("gg", "42")
        scope.set("g", "41")
        scope.set("gg2", "43")
        val s = "\$gg \$g \$gg2"
        assertEquals("42 41 43", substitute(scope, s))
    }

    @Test
    fun testSubstitute4() {
        val scope = Scope()
        scope.set("gg", "42")
        scope.set("gg2", "43")
        val s = "\$gg \$gg \$gg2 \$gg \$gg2"
        assertEquals("42 42 43 42 43", substitute(scope, s))
    }

    @Test
    fun testSubstitute5() {
        val scope = Scope()
        scope.set("gg", "42")
        val s = "\$gg gg \$ gg"
        assertEquals("42 gg \$ gg", substitute(scope, s))
    }
}