package it.matteobarbera

import edu.emory.mathcs.jtransforms.dct.DoubleDCT_1D
import edu.emory.mathcs.jtransforms.dct.DoubleDCT_2D
import kotlin.time.measureTime


fun main() {


    val ipt = doubleArrayOf(
        231.0,
        32.0,
        233.0,
        161.0,
        24.0,
        71.0,
        140.0,
        245.0
    )

    measureTime {
        val extern = DoubleDCT_1D(8)
        extern.forward(ipt, true)
        for (e in ipt)
            print("$e, ")
    }.also { println("Time = $it") }


}
