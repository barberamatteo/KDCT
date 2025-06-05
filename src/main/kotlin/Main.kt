package it.matteobarbera

import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import javax.swing.JFrame
import javax.swing.SwingUtilities


fun main() {
    val comparer = DCTCompare()
    val fastUntil10K = comparer.transformArraysUntilDim(
        dim = 500,
        fast = true
    )
    val standardUntil10K = comparer.transformArraysUntilDim(
        dim = 500,
        fast = false
    )

    SwingUtilities.invokeLater {
        val y1 = fastUntil10K
        val y2 = standardUntil10K

        val chart = comparer.createSemilogChart(y1, y2)
        val panel = ChartPanel(chart)

        JFrame("JFreeChart Semilog Example").apply {
            contentPane = panel
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            pack()
            setLocationRelativeTo(null)
            isVisible = true
        }
    }

}
