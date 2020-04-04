package space.epix.exporter.core

import io.prometheus.client.GaugeMetricFamily

interface CollectibleSupplier {
    fun build()
    fun collect(): GaugeMetricFamily?
}