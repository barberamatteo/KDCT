package it.matteobarbera

import it.matteobarbera.dct.DCT2
import it.matteobarbera.images.SplitterMerger
import it.matteobarbera.images.bitmap.BitmapUtils
import it.matteobarbera.model.Matrix
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.ceil

class MainPipeline {
    private lateinit var parsedImage: Matrix
    private lateinit var splitImage: Array<Array<Matrix>>
    private var F: Int = -1
    private var d = -1
    private lateinit var mergedImage: Matrix


    fun initializeImage(path: String): MainPipeline {
        this.parsedImage = BitmapUtils.parseBitmap(path)
        return this
    }

    fun setBlockSize(F: Int): MainPipeline {
        this.F = F
        return this
    }

    fun setThreshold(d: Int): MainPipeline {
        this.d = d
        return this
    }

    fun split(): MainPipeline {
        this.splitImage = SplitterMerger.split(parsedImage, F)
        return this
    }

    fun applyDCT2(): MainPipeline {
        for (i in 0 until splitImage.size)
            for (j in 0 until splitImage[i].size)
                splitImage[i][j] < DCT2.fastTransform(splitImage[i][j])
        return this
    }

    fun cutFrequencies(): MainPipeline {
        DCT2.cutFrequencies(splitImage, d)
        return this
    }

    fun applyIDCT2AndCeil(): MainPipeline {
        for (blocksRow in splitImage){
            for (block in blocksRow){
                block < DCT2.fastInverseTransform(block)
                for (i in 0 until block.rows){
                    for (j in 0 until block.cols){
                        block[i, j] =
                            if (block[i, j] < 0)
                                0.0
                            else
                                if (block[i, j] > 255)
                                    255.0
                                else
                                    ceil(block[i, j])
                    }
                }
            }
        }
        return this
    }

    fun merge(): MainPipeline {
        this.mergedImage = SplitterMerger.merge(splitImage)
        return this
    }

    fun write(filePath: String): MainPipeline {
        ImageIO.write(
            BitmapUtils.drawBitmap(pixels = mergedImage),
            "bmp",
            File("$filePath.bmp")
        )
        return this
    }
}