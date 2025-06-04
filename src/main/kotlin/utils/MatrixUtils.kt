package it.matteobarbera.utils

import it.matteobarbera.model.Matrix

object MatrixUtils {

    fun setMatrix(dst: Matrix, si: Int, sj: Int, mtx: Matrix) {
        for (i in 0 until mtx.rows){
            for (j in 0 until mtx.cols){
                dst[si + i, sj + j] = mtx[i, j]
            }
        }

    }
}