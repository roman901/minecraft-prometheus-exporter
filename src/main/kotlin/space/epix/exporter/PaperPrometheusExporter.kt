package space.epix.exporter

import org.bukkit.plugin.java.JavaPlugin
import space.epix.exporter.core.CollectibleType
import space.epix.exporter.core.EpixPrometheusExporter

class PaperPrometheusExporter : JavaPlugin() {
    lateinit var epixExporter: EpixPrometheusExporter

    override fun onEnable() {
        epixExporter = EpixPrometheusExporter()
        epixExporter.start(CollectibleType.PAPER, 9123) // TODO: move port to config
    }

    override fun onDisable() {
    }
}
