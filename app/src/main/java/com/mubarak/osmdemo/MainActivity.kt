package com.mubarak.osmdemo

import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.mubarak.osmdemo.databinding.ActivityMainBinding
import org.mapsforge.map.android.graphics.AndroidGraphicFactory
import org.mapsforge.map.android.util.AndroidUtil
import org.mapsforge.map.datastore.MapDataStore
import org.mapsforge.map.layer.renderer.TileRendererLayer
import org.mapsforge.map.reader.MapFile
import org.mapsforge.map.rendertheme.internal.MapsforgeThemes
import java.io.FileInputStream


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val getContent =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            uri?.let {
                openMap(uri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AndroidGraphicFactory.createInstance(application)

        binding.btOpenMap.setOnClickListener {
            getContent.launch(arrayOf("*/*"))
        }
    }

    private fun openMap(uri: Uri) {
        try {
            binding.mapView.mapScaleBar.isVisible = true
            binding.mapView.setBuiltInZoomControls(true)

            val tileCache = AndroidUtil.createTileCache(
                this,
                "mapcache",
                binding.mapView.model.displayModel.tileSize,
                1f,
                binding.mapView.model.frameBufferModel.overdrawFactor
            )

            val fis = contentResolver.openInputStream(uri) as FileInputStream?
            val mapDataStore: MapDataStore = MapFile(fis)
            val tileRendererLayer = TileRendererLayer(
                tileCache,
                mapDataStore,
                binding.mapView.model.mapViewPosition,
                AndroidGraphicFactory.INSTANCE
            )
            tileRendererLayer.setXmlRenderTheme(MapsforgeThemes.OSMARENDER)

            binding.mapView.layerManager.layers.add(tileRendererLayer)

//            binding.mapView.setCenter(LatLong(52.517037, 13.38886))
            binding.mapView.setZoomLevel(12.toByte())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        binding.mapView.destroyAll()
        AndroidGraphicFactory.clearResourceMemoryCache()
        super.onDestroy()
    }

}