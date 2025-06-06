package it.matteobarbera

import it.matteobarbera.dct.DCT2
import it.matteobarbera.model.Matrix
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.axis.LogarithmicAxis
import org.jfree.chart.plot.XYPlot
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import javax.swing.JFrame
import javax.swing.SwingUtilities
import edu.emory.mathcs.utils.ConcurrencyUtils
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.measureTime


fun main() {
//    val outputFast = transformArraysUntilDimAllRandom(1000, true).map { it.toInt(DurationUnit.MICROSECONDS) }.toIntArray()
//    val outputStandard = transformArraysUntilDimAllRandom(1000, false).map { it.toInt(DurationUnit.MICROSECONDS) }.toIntArray()
//    showChart(outputFast, outputStandard)
    val a = ConcurrencyUtils.setNumberOfThreads(0)
    val result = executeBothWithSameMatrix(1000)
    val res1 = result[0].map { it.toInt(DurationUnit.MICROSECONDS) }.toIntArray()
    val res2 = result[1].map { it.toInt(DurationUnit.MICROSECONDS) }.toIntArray()
    showChart(res1, res2)


}

fun transformArraysUntilDimAllRandom(dim: Int, fast: Boolean): MutableList<Duration>{
    val toRet = mutableListOf<Duration>()
    for (i in 2 until dim) {
        val arr = Array(i) {
            DoubleArray(i) {
                Random.nextInt(0, 256).toDouble()
            }
        }
        val m = Matrix(arr)
        measureTime {
            if (fast)
                DCT2.fastTransform(m)
            else
                DCT2.transform(m)
        }.also { toRet.add(it) }
    }
    return toRet
}


fun executeBothWithSameMatrix(dim: Int):Array<MutableList<Duration>>{
    val toRet = arrayOf(
        mutableListOf<Duration>(), //FAST
        mutableListOf<Duration>() //STANDARD
    )
    for (i in 2 until dim){
        val arr = Array(i) {
            DoubleArray(i) {
                Random.nextInt(0, 256).toDouble()
            }
        }
        val m = Matrix(arr)
        measureTime {
            DCT2.fastTransform(m)
        }.also { toRet[0].add(it) }

        measureTime {
            DCT2.transform(m)
        }.also { toRet[1].add(it) }

    }
        return toRet


}

fun createSemilogChart(
    fast: IntArray,
    standard: IntArray
): JFreeChart {
    require(fast.size == standard.size) {
        "Both Y-series must be the same size."
    }

    val series1 = XYSeries("fast")
    val series2 = XYSeries("standard")

    for (i in 0 until fast.size) {
        series1.add(i, fast[i])
        series2.add(i, standard[i])
    }

    val dataset = XYSeriesCollection().apply {
        addSeries(series1)
        addSeries(series2)
    }

    val chart = ChartFactory.createXYLineChart(
        "Comparison between DCT2 and fast DCT2",
        "Matrix size",
        "Execution time (µs)",
        dataset
    )

    val plot = chart.plot as XYPlot
    plot.rangeAxis = LogarithmicAxis("Execution time (µs)")

    return chart
}

fun showChart(fast: IntArray, standard: IntArray) {
    SwingUtilities.invokeLater {
        val chart = createSemilogChart(fast, standard)
        val panel = ChartPanel(chart)

        JFrame("Comparison between DCT2 and fast DCT2").apply {
            contentPane = panel
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            pack()
            setLocationRelativeTo(null)
            isVisible = true
        }
    }
}