package space.epix.exporter.suppliers.paper

import io.prometheus.client.Gauge
import space.epix.exporter.core.Collectible
import space.epix.exporter.core.CollectibleSupplier
import space.epix.exporter.core.CollectibleType

@Collectible(type=CollectibleType.PAPER) class TPSSupplier : CollectibleSupplier {
    lateinit var metric: Gauge

    override fun build() {
        metric = Gauge.build()
                .name("mc_tps")
                .help("Current TPS on server")
                .register()
    }

    override fun collect() {

    }
}