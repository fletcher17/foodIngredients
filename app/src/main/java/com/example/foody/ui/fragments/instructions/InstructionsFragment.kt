package com.example.foody.ui.fragments.instructions

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.foody.dataModel.Result
import com.example.foody.databinding.FragmentInstructionsBinding
import com.example.foody.util.Constants.Companion.RECIPE_RESULT_KEY

class InstructionsFragment : Fragment() {

    private var _binding: FragmentInstructionsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInstructionsBinding.inflate(layoutInflater, container, false)

        val args = arguments
        val myBundle = if (VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
            args?.getParcelable(RECIPE_RESULT_KEY, Result::class.java)
        } else {
            args?.getParcelable(RECIPE_RESULT_KEY)
        }

        binding.instructionsWebView.webViewClient = object : WebViewClient(){}
        val webUrl = myBundle!!.sourceUrl
        binding.instructionsWebView.loadUrl(webUrl)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}