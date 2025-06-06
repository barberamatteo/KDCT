import it.matteobarbera.dct.DCT1
import it.matteobarbera.dct.DCT2
import it.matteobarbera.images.SplitterMerger
import it.matteobarbera.model.Matrix
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DCTTest {

    val dct1 = DCT1
    val dct2 = DCT2
    val splitter = SplitterMerger
    @Test
    fun dct1Test(){
        val vals = doubleArrayOf(
            231.0,
            32.0,
            233.0,
            161.0,
            24.0,
            71.0,
            140.0,
            245.0
        )
        val m = Matrix(vals, asColumnVector = true)
        val output = dct1.transform(m)

    }

    @Test
    fun dct2Test(){
        val vals = arrayOf(
            doubleArrayOf(231.0, 32.0, 233.0, 161.0, 24.0, 71.0, 140.0, 245.0),
            doubleArrayOf(247.0, 40.0, 248.0, 245.0, 124.0, 204.0, 36.0, 107.0),
            doubleArrayOf(234.0, 202.0, 245.0, 167.0, 9.0, 217.0, 239.0, 173.0),
            doubleArrayOf(193.0, 190.0, 100.0, 167.0, 43.0, 180.0, 8.0, 70.0),
            doubleArrayOf(11.0, 24.0, 210.0, 177.0, 81.0, 243.0, 8.0, 112.0),
            doubleArrayOf(97.0, 195.0, 203.0, 47.0, 125.0, 114.0, 165.0, 181.0),
            doubleArrayOf(193.0, 70.0, 174.0, 167.0, 41.0, 30.0, 127.0, 245.0),
            doubleArrayOf(87.0, 149.0, 57.0, 192.0, 65.0, 129.0, 178.0, 228.0)
        )
        val m = Matrix(vals)
        val forward = dct2.fastTransform(m)
        val backward = dct2.fastInverseTransform(forward)
        assertEquals(m, backward)
        print(backward)
    }

    @Test
    fun cutFrequenciesTest(){
        val vals = arrayOf(
            doubleArrayOf(231.0, 32.0, 233.0, 161.0, 24.0, 71.0, 140.0, 245.0),
            doubleArrayOf(247.0, 40.0, 248.0, 245.0, 124.0, 204.0, 36.0, 107.0),
            doubleArrayOf(234.0, 202.0, 245.0, 167.0, 9.0, 217.0, 239.0, 173.0),
            doubleArrayOf(193.0, 190.0, 100.0, 167.0, 43.0, 180.0, 8.0, 70.0),
            doubleArrayOf(11.0, 24.0, 210.0, 177.0, 81.0, 243.0, 8.0, 112.0),
            doubleArrayOf(97.0, 195.0, 203.0, 47.0, 125.0, 114.0, 165.0, 181.0),
            doubleArrayOf(193.0, 70.0, 174.0, 167.0, 41.0, 30.0, 127.0, 245.0),
            doubleArrayOf(87.0, 149.0, 57.0, 192.0, 65.0, 129.0, 178.0, 228.0)
        )
        val m = Matrix(vals)
        val mSplit = splitter.split(m, 4)
        assertEquals(
            mSplit.size,
            2)

        assertEquals(
            mSplit[0].size,
            2
        )

        dct2.transformBlocks(mSplit)
        dct2.cutFrequencies(mSplit, 7)
        for (blockRow in mSplit){
            for (block in blockRow){
                print("Block: $block")
            }
        }

    }
}