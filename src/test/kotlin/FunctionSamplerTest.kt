import it.matteobarbera.functions.FunctionSampler
import kotlin.math.cos
import kotlin.math.sign
import kotlin.test.Test

class FunctionSamplerTest {
    val functionSampler = FunctionSampler
    @Test
    fun samplerLinearFunctionTest() {
        val lin = { x: Number -> x }
        for (e in functionSampler.sample(lin, 4)) {
            print("$e, ")
        }
    }
    @Test
    fun samplerCosFunctionTest() {
        val sin = { x: Number -> cos(x.toDouble()) }
        for (e in functionSampler.sample(sin, 4)) {
            print("$e, ")
        }
    }

    @Test
    fun samplerSignFunctionTest() {
        val sign = { x: Number -> sign(x.toDouble() - 0.5) }
        for (e in functionSampler.sample(sign, 4)) {
            print("$e, ")
        }
    }

    @Test
    fun spaceMiddlePointTest(){
        val size = 8
        val res = functionSampler.spacedMiddlePoints(size)
        for (i in res){
            print("$i, ")
        }
    }
}