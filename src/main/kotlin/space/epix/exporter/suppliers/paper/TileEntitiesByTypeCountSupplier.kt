package space.epix.exporter.suppliers.paper

import io.prometheus.client.GaugeMetricFamily
import org.bukkit.Bukkit
import space.epix.exporter.core.Collectible
import space.epix.exporter.core.CollectibleSupplier
import space.epix.exporter.core.CollectibleType

@Collectible(type = CollectibleType.PAPER)
class TileEntitiesByTypeCountSupplier : CollectibleSupplier {
    override fun build() {}

    override fun collect(): GaugeMetricFamily? {
        val gauge = GaugeMetricFamily("mc_tile_entities_type", "Tile entities by type count", listOf("world", "type"))
        Bukkit.getWorlds().forEach { world ->
            world.loadedChunks
                    .flatMap { it.tileEntities.asIterable() }
                    .groupBy { it.type }
                    .forEach {
                        gauge.addMetric(listOf(world.name, it.key.name), it.value.size.toDouble())
                    }
        }
        return gauge
    }
}