package it.matteobarbera.images.bitmap

import it.matteobarbera.model.Matrix
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

object BitmapUtils {

    fun parseBitmap(fileName: String): Matrix{
        val img = ImageIO.read(File(fileName))

        val grayImage = BufferedImage(
            img.width,
            img.height,
            BufferedImage.TYPE_BYTE_GRAY
        )
        val drawer = grayImage.graphics
        drawer.drawImage(img, 0, 0, null)
        drawer.dispose()
        val pixels =
            grayImage
                .raster
                .getDataElements(0, 0, img.width, img.height, null)
                    as ByteArray
        val toRet = Matrix(img.height, img.width)
        val numericPixels = pixels.map { (it.toInt() and 0xFF).toDouble() }.toDoubleArray()
        for (i in 0 until img.height) {
            val currentRow = Matrix(
                numericPixels.slice(i * img.width until ((i + 1) * img.width)).toDoubleArray(),
                asColumnVector = false
            )
            toRet.setRow(
                i = i,
                row = currentRow
            )
        }
        return toRet
    }

    fun drawBitmap(pixels: Matrix): BufferedImage{
        val bufferedImage = BufferedImage(
            pixels.cols,
            pixels.rows,
            BufferedImage.TYPE_BYTE_GRAY
        )
        val raster = bufferedImage.raster
        for (i in 0 until pixels.rows){
            for (j in 0 until pixels.cols){
                val pix = pixels[i, j].toInt()
                raster.setPixel(j,i, intArrayOf(pix, pix, pix))
            }
//            val row = pixels.getRow(i).vals[0].map { it.toInt().toByte() }.toByteArray()
//            raster.setDataElements(0, i, row)
        }
        return bufferedImage
    }
}