package space.epix.exporter.suppliers.paper

import io.prometheus.client.GaugeMetricFamily
import org.bukkit.Bukkit
import space.epix.exporter.core.Collectible
import space.epix.exporter.core.CollectibleSupplier
import space.epix.exporter.core.CollectibleType


@Collectible(type=CollectibleType.PAPER) class MSPTSupplier : CollectibleSupplier {
    override fun build() { }

    override fun collect(): GaugeMetricFamily? {
        val gauge = GaugeMetricFamily("mc_mspt", "Milliseconds per tick", listOf())
        try {
            val tickTimes = Bukkit.getTickTimes()
            if (tickTimes.isNotEmpty()) {
                val time = tickTimes[0].toDouble().div(1_000_000)
                gauge.addMetric(listOf(), time)
            }
        } catch (ignored: Throwable) {
            // no method is available to track tick time
        }
        return gauge
    }
}