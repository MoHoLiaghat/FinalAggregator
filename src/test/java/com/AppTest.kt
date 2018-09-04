package com

import java.security.SecureRandom

open class AppTest {
    val random =  object : SecureRandom(){
        override fun nextInt(bound: Int): Int {
            return super.nextInt(bound) + 1
        }
    }

    val randomName get() = "Test$randomNatural"
    val randomLong get() = Math.abs(random.nextLong()) + 1
    val randomInt get() = random.nextInt()
    val randomNatural get() = Math.abs(random.nextInt()) + 1
}