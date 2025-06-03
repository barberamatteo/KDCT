package it.matteobarbera.dct

import it.matteobarbera.model.Matrix

object DCT1: DCT {
    override fun transform(sampledFunction: Matrix): Matrix {
        if (!sampledFunction.isColumnVector){
            throw RuntimeException("The Matrix object passed as parameter is not a column vector.")
        }
        return +(computeDCTMatrix(sampledFunction.rows) * sampledFunction)
    }
}