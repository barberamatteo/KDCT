import it.matteobarbera.dct.DCT2
import it.matteobarbera.images.SplitterMerger
import it.matteobarbera.images.bitmap.BitmapUtils
import it.matteobarbera.model.Matrix
import org.junit.jupiter.api.Test
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.ceil

class MainUseCaseTest {

    @Test
    fun mainUseCase() {
        /*val vals = arrayOf(
            doubleArrayOf(231.0, 32.0, 233.0, 161.0, 24.0, 71.0, 140.0, 245.0),
            doubleArrayOf(247.0, 40.0, 248.0, 245.0, 124.0, 204.0, 36.0, 107.0),
            doubleArrayOf(234.0, 202.0, 245.0, 167.0, 9.0, 217.0, 239.0, 173.0),
            doubleArrayOf(193.0, 190.0, 100.0, 167.0, 43.0, 180.0, 8.0, 70.0),
            doubleArrayOf(11.0, 24.0, 210.0, 177.0, 81.0, 243.0, 8.0, 112.0),
            doubleArrayOf(97.0, 195.0, 203.0, 47.0, 125.0, 114.0, 165.0, 181.0),
            doubleArrayOf(193.0, 70.0, 174.0, 167.0, 41.0, 30.0, 127.0, 245.0),
            doubleArrayOf(87.0, 149.0, 57.0, 192.0, 65.0, 129.0, 178.0, 228.0)
        )*/

        val deer = BitmapUtils.parseBitmap("src/test/resources/20x20.bmp")
        val F = 3
        val d = 4
        val transformed = Matrix(deer)
        val split = SplitterMerger.split(transformed, F)
        for (i in 0 until split.size) {
            for (j in 0 until split[0].size) {
                split[i][j] < DCT2.fastTransform(split[i][j])
            }
        }
        DCT2.cutFrequencies(split, d)
        for (blocksRow in split){
            for (block in blocksRow){
                block < DCT2.fastInverseTransform(block)
                for (i in 0 until block.rows){
                    for (j in 0 until block.cols){
                        block[i, j] =
                            if (block[i, j] < 0)
                                0.0
                            else
                                if (block[i, j] > 255)
                                    255.0
                                else
                                    ceil(block[i, j])
                    }
                }
            }
        }

        val final = SplitterMerger.merge(split)
        ImageIO.write(
            BitmapUtils.drawBitmap(pixels = final),
            "bmp",
            File("output.bmp")
        )


    }
}