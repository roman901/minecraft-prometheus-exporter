package space.epix.exporter.suppliers.paper

import io.prometheus.client.GaugeMetricFamily
import org.bukkit.Bukkit
import space.epix.exporter.core.Collectible
import space.epix.exporter.core.CollectibleSupplier
import space.epix.exporter.core.CollectibleType


@Collectible(type=CollectibleType.PAPER) class PlayersCountSupplier : CollectibleSupplier {
    override fun build() { }

    override fun collect(): GaugeMetricFamily? {
        val gauge = GaugeMetricFamily("mc_players", "Online players count", listOf())
        gauge.addMetric(listOf(), Bukkit.getOnlinePlayers().size.toDouble())
        return gauge
    }
}