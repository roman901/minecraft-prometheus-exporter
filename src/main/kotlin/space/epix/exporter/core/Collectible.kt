package space.epix.exporter.core

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Collectible(val type: CollectibleType)
