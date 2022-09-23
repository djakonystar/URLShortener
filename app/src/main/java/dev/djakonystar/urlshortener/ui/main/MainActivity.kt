package dev.djakonystar.urlshortener.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import dev.djakonystar.urlshortener.core.NetworkResult
import dev.djakonystar.urlshortener.data.RequestUrl
import dev.djakonystar.urlshortener.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by lazy { MainViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            button.setOnClickListener {
                val url = editText.text.toString()

                url.also {
                    if (it.isNotEmpty() && it.startsWith("https://")) {
                        val requestUrl = RequestUrl(url = it)
                        viewModel.sendUrl(requestUrl)

                        viewModel.shortenUrl.observe(this@MainActivity) {
                            when (it) {
                                is NetworkResult.Loading -> {
                                    setLoading(true)
                                }

                                is NetworkResult.Success -> {
                                    setLoading(false)
                                    textView.text = it.data!!.resultUrl
                                }

                                is NetworkResult.Error -> {
                                    setLoading(false)
                                    Snackbar.make(
                                        button,
                                        it.message.toString(),
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                    }
                }
            }

            textView.setOnClickListener {
                // copy it text
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        binding.apply {
            progressBar.isVisible = loading
            button.isEnabled = !loading
            editText.isEnabled = !loading
        }
    }
}
