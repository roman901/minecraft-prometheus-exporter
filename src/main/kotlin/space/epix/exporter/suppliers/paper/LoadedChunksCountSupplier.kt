package space.epix.exporter.suppliers.paper

import io.prometheus.client.GaugeMetricFamily
import org.bukkit.Bukkit
import space.epix.exporter.core.Collectible
import space.epix.exporter.core.CollectibleSupplier
import space.epix.exporter.core.CollectibleType


@Collectible(type=CollectibleType.PAPER) class LoadedChunksCountSupplier : CollectibleSupplier {
    override fun build() { }

    override fun collect(): GaugeMetricFamily? {
        val gauge = GaugeMetricFamily("mc_loaded_chunks", "Loaded chunks count", listOf("world"))
        Bukkit.getWorlds().forEach {
            gauge.addMetric(listOf(it.name), it.loadedChunks.size.toDouble())
        }
        return gauge
    }
}