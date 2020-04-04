package space.epix.exporter.suppliers.all

import io.prometheus.client.hotspot.DefaultExports
import space.epix.exporter.core.Collectible
import space.epix.exporter.core.CollectibleSupplier
import space.epix.exporter.core.CollectibleType

@Collectible(type=CollectibleType.ALL) class HotSpotSupplier : CollectibleSupplier {
    override fun build() {
        DefaultExports.initialize()
    }

    override fun collect() { }

}