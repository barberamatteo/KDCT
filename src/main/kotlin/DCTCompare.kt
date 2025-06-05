package it.matteobarbera

import it.matteobarbera.dct.DCT2
import it.matteobarbera.model.Matrix
import org.jfree.chart.ChartFactory
import org.jfree.chart.JFreeChart
import org.jfree.chart.axis.LogarithmicAxis
import org.jfree.chart.plot.XYPlot
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.measureTime

class DCTCompare {

    fun transformArraysUntilDim(dim: Int, fast: Boolean): MutableList<Duration>{
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


    fun createSemilogChart(
        fast: MutableList<Duration>,
        standard: MutableList<Duration>
    ): JFreeChart {
        require(fast.size == standard.size) {
            "Both Y-series must be the same size."
        }

        val series1 = XYSeries("fast")
        val series2 = XYSeries("standard")

        for (i in fast.indices) {
            val x = (i + 1).toDouble()  // Natural number starting from 1
            series1.add(x, fast[i].toDouble(DurationUnit.MILLISECONDS))
            series2.add(x, standard[i].toDouble(DurationUnit.MILLISECONDS))
        }

        val dataset = XYSeriesCollection().apply {
            addSeries(series1)
            addSeries(series2)
        }

        val chart = ChartFactory.createXYLineChart(
            "Semilog Y-Axis Chart",
            "Index (Natural Numbers)",
            "Y (Log Scale)",
            dataset
        )

        val plot = chart.plot as XYPlot
        plot.rangeAxis = LogarithmicAxis("Y (Log Scale)")

        return chart
    }
}