package space.epix.exporter

import org.bukkit.plugin.java.JavaPlugin
import space.epix.exporter.core.CollectibleType
import space.epix.exporter.core.EpixPrometheusExporter

class PaperPrometheusExporter : JavaPlugin() {
    private lateinit var epixExporter: EpixPrometheusExporter

    override fun onEnable() {
        instance = this

        epixExporter = EpixPrometheusExporter()
        var success = false
        var port = config.getInt("server.port", 9123)

        while (!success) {
            try {
                epixExporter.start(CollectibleType.PAPER, port)
                success = true
            } catch (e: Exception) {
                port++
            }
        }
        logger.info("Starting exporter on port $port")
        config.set("server.port", port)
        saveDefaultConfig()
    }

    override fun onDisable() {
        epixExporter.stop()
    }

    companion object Plugin {
        lateinit var instance: PaperPrometheusExporter
    }
}
