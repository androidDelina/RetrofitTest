package com.example.retrofitlesson

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.retrofitlesson.databinding.FragmentLoginBinding
import com.example.retrofitlesson.retrofit.AuthRequest
import com.example.retrofitlesson.retrofit.RetrofitApi
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by activityViewModels()
    private lateinit var mainApi: RetrofitApi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRetrofit()

        binding.apply {
            nextButton.setOnClickListener {
                findNavController().navigate(R.id.action_LoginFragment_to_ProductsFragment)

            }
            signInButton.setOnClickListener {
                auth(
                    AuthRequest(
                        edLogin.text.toString().trim(),
                        edPassword.text.toString().trim()
                    )
                )
            }
        }
    }

    private fun initRetrofit() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()

        mainApi = retrofit.create(RetrofitApi::class.java)
    }

    private fun auth(authRequest: AuthRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = mainApi.auth(authRequest)
            val message = response.errorBody()?.string()?.let {
                JSONObject(it).getString("message")
            }
            binding.apply {
                requireActivity().runOnUiThread {
                    error.text = message
                    val user = response.body()
                    if (user != null) {
                        Picasso.get().load(user.image).into(image)
                        firstName.text = user.firstName
                        lastName.text = user.lastName
                        nextButton.visibility = View.VISIBLE
                        signInButton.visibility = View.GONE
                        viewModel.token.value = user.token
                    }
                }
            }
        }
    }
}












