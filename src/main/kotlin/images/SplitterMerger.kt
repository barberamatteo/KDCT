package it.matteobarbera.images

import it.matteobarbera.model.Matrix
import it.matteobarbera.utils.MatrixUtils

object SplitterMerger {
    fun split(img: Matrix, blockSize: Int): Array<Array<Matrix>> {
        val blocksPerCol = img.rows / blockSize
        val blocksPerRow = img.cols / blockSize
        val toRet = Array(blocksPerCol) {
            Array(blocksPerRow) {
                Matrix(blockSize, blockSize)
            }
        }

        for (i in 0 until blocksPerCol) {
            for (j in 0 until blocksPerRow) {
                toRet[i][j] = img.subMatrix(
                    blockSize * i,
                    ((i + 1) * blockSize) - 1,
                    blockSize * j,
                    ((j + 1) * blockSize) - 1
                )
            }
        }
        return toRet
    }

    fun merge(m: Array<Array<Matrix>>): Matrix {
        val blockSize = m[0][0].rows
        val toRet = Matrix(blockSize * m.size, blockSize * m[0].size)
        for (i in 0 until m.size){
            for (j in 0 until m[i].size){
                MatrixUtils.setMatrix(
                    toRet,
                        blockSize * i,
                    blockSize * j,
                    m[i][j]
                )
            }
        }
        return toRet
    }
}