package blog

import reactor.core.publisher.toFlux

fun main1(args: Array<String>) {
    println("Testing Filter")
    val fluxInt = arrayListOf(1, 2, 3, 9, 3, 4, 5, 2).toFlux()
    fluxInt.filter { it < 3 }
            .subscribe(::println)

    println("----MAP-------")
    fluxInt.map { it * it }
            .subscribe(::println)

    val nameFlux = arrayListOf(arrayListOf("1", "a").toFlux(), arrayListOf("b", "c", "d").toFlux(),
            arrayListOf("c", "edd").toFlux()).toFlux()

    println("----FLATMAP------")
    nameFlux.flatMap { it }
            .subscribe { println(it.count()) }
}