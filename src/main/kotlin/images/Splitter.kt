package it.matteobarbera.images

import it.matteobarbera.model.Matrix

object Splitter {
    fun split(img: Matrix, blockSize: Int): Array<Array<Matrix>>{
        val blocksPerCol = img.rows / blockSize
        val blocksPerRow = img.cols / blockSize
        val toRet = Array(blocksPerCol){
            Array(blocksPerRow){
                Matrix(blockSize, blockSize)
            }
        }

        for (i in 0 until blocksPerCol){
            for (j in 0 until blocksPerRow){
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
}