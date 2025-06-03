package it.matteobarbera.functions

object FunctionSampler {

    fun sample(function: (Number) -> Number, samples: Int): Array<Number> {
        val middlePoints = spacedMiddlePoints(samples)
        return middlePoints.map { function(it) }.toTypedArray()
    }

    fun spacedMiddlePoints(size: Int): DoubleArray {
        return DoubleArray(size){
            ((2.0 * it + 1) / (2.0 * size))
        }
    }
}