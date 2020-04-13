package space.epix.exporter.suppliers.paper

import io.prometheus.client.GaugeMetricFamily
import org.bukkit.Bukkit
import space.epix.exporter.core.Collectible
import space.epix.exporter.core.CollectibleSupplier
import space.epix.exporter.core.CollectibleType


@Collectible(type = CollectibleType.PAPER)
class TileEntitiesCountSupplier : CollectibleSupplier {
    override fun build() {}

    override fun collect(): GaugeMetricFamily? {
        val gauge = GaugeMetricFamily("mc_tile_entities", "Tile entities count", listOf("world"))
        Bukkit.getWorlds().forEach { world ->
            val tileEntityCount = try {
                world.tileEntityCount.toDouble()
            } catch (throwable: Throwable) {
                world.loadedChunks.map { it.tileEntities.size }.sum().toDouble()
            }
            gauge.addMetric(listOf(world.name), tileEntityCount)
        }
        return gauge
    }
}