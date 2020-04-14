package space.epix.exporter

import org.bukkit.plugin.java.JavaPlugin
import space.epix.exporter.core.CollectibleType
import space.epix.exporter.core.EpixPrometheusExporter

class PaperPrometheusExporter : JavaPlugin() {
    private lateinit var epixExporter: EpixPrometheusExporter

    override fun onEnable() {
        instance = this
        saveDefaultConfig()

        val port = config.getInt("server.port", 9123)
        epixExporter = EpixPrometheusExporter()
        epixExporter.start(CollectibleType.PAPER, port)
    }

    override fun onDisable() {
        epixExporter.stop()
    }

    companion object Plugin {
        lateinit var instance: PaperPrometheusExporter
    }
}
