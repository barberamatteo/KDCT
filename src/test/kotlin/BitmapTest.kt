import it.matteobarbera.images.bitmap.BitmapUtils
import kotlin.test.Test

class BitmapTest {

    val bitmapUtils = BitmapUtils

    @Test
    fun bridgeTest(){
        val m = bitmapUtils.parseBitmap("src/test/resources/prova.bmp")
        print(m)

    }

}