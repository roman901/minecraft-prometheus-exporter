package space.epix.exporter.core

interface CollectibleSupplier {
    fun build()
    fun collect()
}