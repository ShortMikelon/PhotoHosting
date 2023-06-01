package kz.aues.photohosting

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

fun main() {
    val url = "https://example.com/bikes/balance.aspx"
    val index = url.lastIndexOf("/")
    val name = url.substring(index + 1)
    println(name)
}

