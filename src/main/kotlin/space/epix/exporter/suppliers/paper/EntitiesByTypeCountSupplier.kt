package space.epix.exporter.suppliers.paper

import io.prometheus.client.GaugeMetricFamily
import org.bukkit.Bukkit
import space.epix.exporter.core.Collectible
import space.epix.exporter.core.CollectibleSupplier
import space.epix.exporter.core.CollectibleType


@Collectible(type=CollectibleType.PAPER) class EntitiesByTypeCountSupplier : CollectibleSupplier {
    override fun build() { }

    override fun collect(): GaugeMetricFamily? {
        val gauge = GaugeMetricFamily("mc_entites_type", "Entities by type count", listOf("world", "type"))
        Bukkit.getWorlds().forEach { world ->
            world.entities.groupBy { it.type.name }.forEach {
                gauge.addMetric(listOf(world.name, it.key), it.value.size.toDouble())
            }
        }
        return gauge
    }
}