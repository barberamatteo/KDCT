import it.matteobarbera.images.bitmap.BitmapUtils
import it.matteobarbera.model.Matrix
import java.io.File
import javax.imageio.ImageIO
import kotlin.test.Test

class BitmapTest {

    val bitmapUtils = BitmapUtils

    @Test
    fun bridgeTest(){
        val m = bitmapUtils.parseBitmap("src/test/resources/prova.bmp")
        print(m)

    }


    @Test
    fun drawTest(){
        val m = Matrix(
            arrayOf(
                doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                doubleArrayOf(255.0, 255.0, 255.0, 255.0, 255.0, 255.0, 255.0, 255.0, 255.0, 255.0),
                doubleArrayOf(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0),
                doubleArrayOf(255.0, 255.0, 255.0, 255.0, 255.0, 255.0, 255.0, 255.0, 255.0, 255.0),
                doubleArrayOf(255.0, 255.0, 255.0, 255.0, 255.0, 255.0, 255.0, 255.0, 255.0, 255.0),
                doubleArrayOf(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0),
                doubleArrayOf(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0),
                doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
                )
        )
        val a20x20 = BitmapUtils.parseBitmap("src/test/resources/deer.bmp")
        val img = BitmapUtils.drawBitmap(a20x20)
        ImageIO.write(img, "bmp", File("a20x20.bmp"))
    }

}