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
        val blockSize = transformedBlocks[0][0].rows
        if (threshold > 2 * blockSize - 2){
            throw RuntimeException("Invalid threshold value: it must be between 0 and " +
                    "${2 * blockSize - 2}"
            )
        }
        if (threshold > blockSize){
            /*
             * Starting from the block matrix, setting entry by entry to 0.0 from the bottom-right corner
             */
            for (blocksRow in transformedBlocks){
                for (block in blocksRow){
                    val tmp = block.copy()
                    var delta = threshold - blockSize + 1
                    for (i in blockSize - 1 downTo delta){
                        for (j in blockSize - 1 downTo delta){
                            tmp[i, j] = 0.0
                        }
                        delta++
                    }
                    block < Matrix(tmp)
                }
            }
        } else {
            /*
             * Starting from zeros matrix, filling it entry by entry
             * to the corresponding block entry from the top-left corner
             */

            for (blocksRow in transformedBlocks) {
                for (block in blocksRow) {
                    val tmp = Matrix(blockSize, blockSize)
                    var decrementingThreshold = threshold
                    for (i in 0 until decrementingThreshold){
                        for (j in 0 until decrementingThreshold){
                            tmp[i, j] = block[i, j]
                        }
                        decrementingThreshold--
                    }
                    block < Matrix(tmp)
                }
            }
        }
    }

}