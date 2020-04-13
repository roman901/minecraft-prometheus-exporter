package space.epix.exporter.core

import io.prometheus.client.Collector
import org.reflections.Reflections
import space.epix.exporter.PaperPrometheusExporter
import java.util.*
import java.util.logging.Level
import kotlin.reflect.full.findAnnotation


class EpixPrometheusCollector(private var runtime: CollectibleType) : Collector() {
    private val suppliers = ArrayList<CollectibleSupplier>()
    private val logFailures = PaperPrometheusExporter.instance.config
            .getBoolean("log.collector-failures", false)

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
        suppliers.forEach { supplier ->
            try {
                supplier.collect()?.let { metrics.add(it) }
            } catch (throwable: Throwable) {
                if (logFailures) {
                    PaperPrometheusExporter.instance.logger.log(Level.SEVERE,
                            "Can't collect ${supplier.javaClass.simpleName}", throwable)
                }
            }
        }
        return metrics
    }

}