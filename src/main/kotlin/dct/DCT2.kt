package it.matteobarbera.dct

import edu.emory.mathcs.jtransforms.dct.DoubleDCT_2D
import it.matteobarbera.model.Matrix

object DCT2: DCT{
    override fun transform(sampledFunction: Matrix): Matrix {
        if (!sampledFunction.isSquare){
            throw RuntimeException(
                "The Matrix object passed as parameter is not a square matrix" +
                        " (it's a ${sampledFunction.rows}, ${sampledFunction.cols}).")
        }
        val dctMatrix = computeDCTMatrix(sampledFunction.rows)
        val coefficientMatrix = sampledFunction.copy()
        for (j in 0 until coefficientMatrix.cols){
            coefficientMatrix.setColumn(
                j = j,
                column = dctMatrix * coefficientMatrix.getColumn(j)
            )
        }

        for (i in 0 until coefficientMatrix.rows){
            coefficientMatrix.setRow(
                i = i,
                row = +(dctMatrix * (+coefficientMatrix.getRow(i)))
            )
        }
        return coefficientMatrix
    }

    fun fastTransform(sampledFunction: Matrix): Matrix {
        val toRet = sampledFunction.copy()
        DoubleDCT_2D(
            toRet.rows,
        toRet.cols
        ).forward(toRet.vals, true)
        return toRet
    }

    fun transformBlocks(blocks: Array<Array<Matrix>>){
        for (blocksRow in blocks){
            for (block in blocksRow){
                block < fastTransform(block)
            }
        }
    }

    fun cutFrequencies(transformedBlocks: Array<Array<Matrix>>, threshold: Int){
        for (blocksRow in transformedBlocks){
            for (block in blocksRow){

            }
        }
    }

}