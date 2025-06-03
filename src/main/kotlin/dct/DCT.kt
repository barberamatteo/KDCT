package it.matteobarbera.dct

import it.matteobarbera.functions.FunctionSampler
import it.matteobarbera.model.Matrix
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sqrt

interface DCT {


    fun transform(sampledFunction: Matrix): Matrix


    fun computeDCTMatrix(size: Int): Matrix{

        val samples = FunctionSampler.spacedMiddlePoints(size)
        val toRet = Matrix(size, size)
        val alphaCoefficients = Matrix(size, 1)
        orthoScaleInPlace(alphaCoefficients)
        for (i in 0 until size) {
            for (j in 0 until size) {
                toRet[i, j] = alphaCoefficients[i] * cos(i * PI * samples[j])
            }
        }
        return toRet
    }
    fun orthoScaleInPlace(alphaCoefficients: Matrix){
        val size = alphaCoefficients.rows
        alphaCoefficients[0] = 1 / sqrt(size.toDouble())
        for (i in 1 until size)
            alphaCoefficients[i] = sqrt(2.0) / sqrt(size.toDouble())
    }
}