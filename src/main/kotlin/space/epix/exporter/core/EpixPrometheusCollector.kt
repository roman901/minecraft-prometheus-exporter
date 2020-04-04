package space.epix.exporter.core

import io.prometheus.client.Collector
import io.prometheus.client.GaugeMetricFamily
import org.reflections.Reflections
import java.util.*
import kotlin.reflect.full.findAnnotation


class EpixPrometheusCollector(private var runtime: CollectibleType) : Collector() {
    private val suppliers = ArrayList<CollectibleSupplier>()

    init {
        val reflections = Reflections("space.epix.exporter.suppliers")
        val suppliersClasses: Set<Class<*>> = reflections.getTypesAnnotatedWith(Collectible::class.java)
        suppliersClasses.forEach {
            val supplier = it.getConstructor().newInstance() as CollectibleSupplier
            val annotation = supplier::class.findAnnotation<Collectible>()
            if (annotation?.type == CollectibleType.ALL || annotation?.type == runtime) {
                supplier.build()
                suppliers.add(supplier)
            }
        }
    }

    override fun collect(): List<MetricFamilySamples> {
        val metrics = ArrayList<MetricFamilySamples>()
        suppliers.forEach {
            val gaugeMetricFamily = it.collect()
            if (gaugeMetricFamily != null) {
                metrics.add(gaugeMetricFamily)
            }
        }
        return metrics
    }

}