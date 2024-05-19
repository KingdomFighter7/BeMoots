package app.android.mootsadmin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.android.mootsadmin.databinding.ActivityMainBinding
import app.android.mootsadmin.databinding.ActivityUploadBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize database reference after setting content view
        databaseReference = FirebaseDatabase.getInstance().getReference("Phone Directory")

        binding.saveButton.setOnClickListener {
            val name = binding.uploadName.text.toString()
            val operator = binding.uploadOperator.text.toString()
            val location = binding.uploadLocation.text.toString()
            val phone = binding.uploadPhone.text.toString()
            //val userId = databaseReference.push().key.toString()

            //Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()

            databaseReference.child(phone).setValue(UserData(name, operator, location, phone))
                .addOnCompleteListener {
                    if (!it.isSuccessful) {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@UploadActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    binding.uploadName.text.clear()
                    binding.uploadOperator.text.clear()
                    binding.uploadLocation.text.clear()
                    binding.uploadPhone.text.clear()


                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                    Log.e("UploadActivity", it.message.toString())
                }
        }
    }
}