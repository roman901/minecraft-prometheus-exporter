package space.epix.exporter.core

import io.prometheus.client.CollectorRegistry
import io.prometheus.client.exporter.HTTPServer

class EpixPrometheusExporter {
    lateinit var httpServer: HTTPServer

    fun start(runtime: CollectibleType, port: Int) {
        val collector = EpixPrometheusCollector(runtime)
        CollectorRegistry.defaultRegistry.register(collector)
        httpServer = HTTPServer(port)
    }

    fun stop() {
        httpServer.stop()
    }
}