package space.epix.exporter.core

import io.prometheus.client.exporter.HTTPServer
import org.reflections.Reflections
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberExtensionProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.primaryConstructor

class EpixPrometheusExporter {
    lateinit var httpServer: HTTPServer
    val suppliers = ArrayList<CollectibleSupplier>()

    fun start(runtime: CollectibleType, port: Int) {
        // Scan for collectibles
        val reflections = Reflections("space.epix.exporter.suppliers")
        val suppliersClasses: Set<Class<*>> = reflections.getTypesAnnotatedWith(Collectible::class.java)
        suppliersClasses.forEach {
            val supplier = it.getConstructor().newInstance() as CollectibleSupplier
            val annotation = supplier::class.findAnnotation<Collectible>()
            if (annotation?.type == runtime) {
                supplier.build()
                suppliers.add(supplier)
            }
        }
        
        httpServer = HTTPServer(port)
    }
}