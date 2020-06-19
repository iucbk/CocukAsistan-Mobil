package com.iucbk.cocuk_asistan.ui.`object`.item

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.firebase.ml.common.FirebaseMLException
import com.google.firebase.ml.custom.FirebaseCustomLocalModel
import com.google.firebase.ml.custom.FirebaseModelDataType
import com.google.firebase.ml.custom.FirebaseModelInputOutputOptions
import com.google.firebase.ml.custom.FirebaseModelInputs
import com.google.firebase.ml.custom.FirebaseModelInterpreter
import com.google.firebase.ml.custom.FirebaseModelInterpreterOptions
import com.iucbk.cocuk_asistan.R
import com.iucbk.cocuk_asistan.databinding.FragmentObjectImageBinding
import com.iucbk.cocuk_asistan.ui.`object`.ImageDetailFragment
import com.iucbk.cocuk_asistan.util.extension.viewBinding
import com.iucbk_cocukasistan.custom_views.LabelGraphic
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.experimental.and
import kotlin.math.max
import kotlin.math.min

class ObjectImageFragment : Fragment(R.layout.fragment_object_image) {

    private val binding by viewBinding(FragmentObjectImageBinding::bind)

    data class LabelConfidence(val label: String, val confidence: Float)

    private val labelList by lazy {
        BufferedReader(InputStreamReader(resources.assets.open(LABEL_PATH))).lineSequence().toList()
    }

    private val imageBuffer = IntArray(DIM_IMG_SIZE_X * DIM_IMG_SIZE_Y)

    private val targetedWidthHeight: Pair<Int, Int>
        get() {
            val targetWidth: Int
            val targetHeight: Int
            val maxWidthForPortraitMode = binding.imgShootedImage.width
            val maxHeightForPortraitMode = binding.imgShootedImage.height
            targetWidth = maxWidthForPortraitMode
            targetHeight = maxHeightForPortraitMode
            return Pair(targetWidth, targetHeight)
        }

    /** Input options used for our Firebase model interpreter */
    private val modelInputOutputOptions by lazy {
        val inputDims = arrayOf(DIM_BATCH_SIZE, DIM_IMG_SIZE_X, DIM_IMG_SIZE_Y, DIM_PIXEL_SIZE)
        val outputDims = arrayOf(DIM_BATCH_SIZE, labelList.size)
        FirebaseModelInputOutputOptions.Builder()
            .setInputFormat(0, FirebaseModelDataType.BYTE, inputDims.toIntArray())
            .setOutputFormat(0, FirebaseModelDataType.BYTE, outputDims.toIntArray())
            .build()
    }

    private lateinit var modelInterpreter: FirebaseModelInterpreter

    private fun createLocalModelInterpreter(): FirebaseModelInterpreter {
        val localModelName = resources.assets.list("")?.firstOrNull { it.endsWith(".tflite") }
            ?: throw(RuntimeException("Don't forget to add the tflite file to your assets folder"))
        Log.d(TAG, "Local model found: $localModelName")

        val localModel =
            FirebaseCustomLocalModel.Builder().setAssetFilePath(localModelName).build()
        val localInterpreter = FirebaseModelInterpreter.getInstance(
            FirebaseModelInterpreterOptions.Builder(localModel).build()
        )!!
        Log.d(TAG, "Local model interpreter initialized")
        return localInterpreter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = (requireActivity() as AppCompatActivity)
        activity.setSupportActionBar(binding.bottomAppBar)

        binding.fltShootPhotoAgain.setOnClickListener {
            (requireParentFragment() as ImageDetailFragment).navigateScreenToCamera()
        }

        binding.imgShootedImage.post {
            (requireParentFragment() as ImageDetailFragment).mainViewModel.imageFile.observe(
                viewLifecycleOwner,
                Observer {
                    assetsToBitmap(it)!!.runModelInference()
                })
        }
    }

    private fun assetsToBitmap(fileName: File): Bitmap? {
        return try {
            val bitmap = BitmapFactory.decodeFile(fileName.absolutePath)
            val targetedSize = targetedWidthHeight
            val targetWidth = targetedSize.first
            val maxHeight = targetedSize.second

            binding.imgShootedImage.setImageBitmap(bitmap)

            val scaleFactor = max(
                bitmap!!.width.toFloat() / targetWidth.toFloat(),
                bitmap.height.toFloat() / maxHeight.toFloat()
            )

            val resizedBitmap = Bitmap.createScaledBitmap(
                bitmap,
                (bitmap.width / scaleFactor).toInt(),
                (bitmap.height / scaleFactor).toInt(),
                true
            )
            resizedBitmap

        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                (requireParentFragment() as ImageDetailFragment).navigateScreenToHome()
                true
            }
            R.id.knowledge -> {
                (requireParentFragment() as ImageDetailFragment).binding
                    .vpObjectDetail.setCurrentItem(
                        1,
                        true
                    )
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun Bitmap.runModelInference() = this.let { image ->
        val imgData = convertBitmapToByteBuffer(image)
        try {
            val modelInputs = FirebaseModelInputs.Builder().add(imgData).build()
            modelInterpreter = createLocalModelInterpreter().also {
                it.run(modelInputs, modelInputOutputOptions).continueWith { task ->
                    task.addOnFailureListener { e ->
                        Log.e(TAG, e.localizedMessage ?: "empty")
                    }
                    task.addOnSuccessListener { _success ->
                        val inferenceOutput = _success.getOutput<Array<ByteArray>>(0)
                        val output = _success.getOutput<Array<FloatArray>>(0)
                        val probabilities = output[0]
                        Log.e(TAG, probabilities.toString())
                        val topLabels = getTopLabels(inferenceOutput)
                        binding.graphicOverlay.clear()
                        binding.graphicOverlay.add(LabelGraphic(binding.graphicOverlay, topLabels))
                        Log.d(TAG, topLabels.toString())
                    }
                }
            }

        } catch (exc: FirebaseMLException) {
            val msg = "Error running model inference"
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            Log.e(TAG, msg, exc)
        }
    }

    /** Gets the top labels in the results. */
    @Synchronized
    private fun getTopLabels(inferenceOutput: Array<ByteArray>): List<String> {
        // Since we ran inference on a single image, inference output will have a single row.
        val imageInference = inferenceOutput.first()

        // The columns of the image inference correspond to the confidence for each label.
        return labelList.mapIndexed { idx, label ->
            LabelConfidence(label, (imageInference[idx] and 0xFF.toByte()) / 255.0f)

            // Sort the results in decreasing order of confidence and return only top 3.
        }.sortedBy { it.confidence }.reversed().map { "${it.label}:${it.confidence}" }
            .subList(0, min(labelList.size, RESULTS_TO_SHOW))
    }

    /** Writes Image data into a `ByteBuffer`. */
    @Synchronized
    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val imgData = ByteBuffer.allocateDirect(
            DIM_BATCH_SIZE * DIM_IMG_SIZE_X * DIM_IMG_SIZE_Y * DIM_PIXEL_SIZE
        ).apply {
            order(ByteOrder.nativeOrder())
            rewind()
        }
        val scaledBitmap =
            Bitmap.createScaledBitmap(bitmap, DIM_IMG_SIZE_X, DIM_IMG_SIZE_Y, true)
        scaledBitmap.getPixels(
            imageBuffer, 0, scaledBitmap.width, 0, 0, scaledBitmap.width, scaledBitmap.height
        )
        // Convert the image to int points.
        var pixel = 0
        for (i in 0 until DIM_IMG_SIZE_X) {
            for (j in 0 until DIM_IMG_SIZE_Y) {
                val `val` = imageBuffer[pixel++]
                imgData.put((`val` shr 16 and 0xFF).toByte())
                imgData.put((`val` shr 8 and 0xFF).toByte())
                imgData.put((`val` and 0xFF).toByte())
            }
        }
        return imgData
    }

    companion object {
        const val TAG = "TESTUP"

        private const val LABEL_PATH = "labels.txt"

        /** Number of results to show in the UI. */
        private const val RESULTS_TO_SHOW = 3

        /** Dimensions of inputs. */
        private const val DIM_BATCH_SIZE = 1
        private const val DIM_PIXEL_SIZE = 3
        private const val DIM_IMG_SIZE_X = 224
        private const val DIM_IMG_SIZE_Y = 224

        @JvmStatic
        fun newInstance() =
            ObjectImageFragment()
    }
}
