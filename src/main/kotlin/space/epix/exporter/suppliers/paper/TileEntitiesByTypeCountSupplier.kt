package space.epix.exporter.suppliers.paper

import io.prometheus.client.GaugeMetricFamily
import org.bukkit.Bukkit
import org.bukkit.World
import space.epix.exporter.PaperPrometheusExporter
import space.epix.exporter.core.Collectible
import space.epix.exporter.core.CollectibleSupplier
import space.epix.exporter.core.CollectibleType
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@Collectible(type = CollectibleType.PAPER)
class TileEntitiesByTypeCountSupplier : CollectibleSupplier {
    override fun build() {}

    override fun collect(): GaugeMetricFamily? {
        val gauge = GaugeMetricFamily("mc_tile_entities_type", "Tile entities by type count", listOf("world", "type"))
        for (world in Bukkit.getWorlds()) {
            try {
                addDataToGauge(world, gauge)
            } catch (exception: RuntimeException) {
                try {
                    Bukkit.getScheduler().callSyncMethod(PaperPrometheusExporter.instance) {
                        addDataToGauge(world, gauge)
                    }.get(50, TimeUnit.MILLISECONDS)
                } catch (ignoredTimeoutException: TimeoutException) {
                    if (PaperPrometheusExporter.instance.config.getBoolean("log.collector-failures", false)) {
                        log("Couldn't retrieve (sync) tile entity data in 50 ms window from world ${world.name}")
                    }
                } catch (otherException: Exception) {
                    if (PaperPrometheusExporter.instance.config.getBoolean("log.collector-failures", false)) {
                        log("Couldn't retrieve (sync) tile entity data from world ${world.name}: " +
                                "${otherException.javaClass.simpleName} - ${otherException.message}")
                    }
                }
            }
        }
        return gauge
    }

    private fun log(message: String) {
        PaperPrometheusExporter.instance.logger.warning(message)
    }

    private fun addDataToGauge(world: World, gauge: GaugeMetricFamily) {
        world.loadedChunks
                .flatMap { it.tileEntities.asIterable() }
                .groupBy { it.type }
                .forEach {
                    gauge.addMetric(listOf(world.name, it.key.name), it.value.size.toDouble())
                }
    }
}