package com.example.retrofitlesson

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.retrofitlesson.databinding.ContentBaseBinding

class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ContentBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ContentBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}