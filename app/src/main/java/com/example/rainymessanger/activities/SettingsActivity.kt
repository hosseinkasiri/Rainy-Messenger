package com.example.rainymessanger.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.rainymessanger.R
import com.example.rainymessanger.fragments.StatusDialogFragment
import com.example.rainymessanger.helper.Toaster
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.theartofdev.edmodo.cropper.CropImage
import de.hdodenhof.circleimageview.CircleImageView
import id.zelory.compressor.Compressor
import java.io.ByteArrayOutputStream
import java.io.File

class SettingsActivity : AppCompatActivity() {

    val STATUS_DIALOG = "statusDialog"
    val REQ_GALLERY = 1

    lateinit var mDatabase: DatabaseReference
    lateinit var mCurrentUser: FirebaseUser
    lateinit var mStorage: StorageReference

    lateinit var mName: TextView
    lateinit var mStatus: TextView
    lateinit var mChangePic: Button
    lateinit var mChangeStatus: Button
    lateinit var mPicture: ImageView

    companion object{
        fun newIntent(context: Context) : Intent {
            var intent = Intent(context, SettingsActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        findViews()
        supportActionBar!!.title = "Setting"
        mCurrentUser = FirebaseAuth.getInstance().currentUser!!
        var userId = mCurrentUser.uid
        mDatabase = FirebaseDatabase.getInstance().reference.child("users").child(userId)
        getInformation()
        mStorage = FirebaseStorage.getInstance().reference

        mChangeStatus.setOnClickListener(View.OnClickListener {
            var statusDialog = StatusDialogFragment.newInstance(mStatus.text.toString())
            statusDialog.show(supportFragmentManager, STATUS_DIALOG)
        })

        mChangePic.setOnClickListener(View.OnClickListener {
            var intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "choose image"), REQ_GALLERY)
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQ_GALLERY) {
            var image = data!!.data
            CropImage.activity(image)
                    .setAspectRatio(1, 1)
                    .start(this)
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            var result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                var resultUri = result.uri
                var userId = mCurrentUser.uid
                var thumbFile = File(resultUri.path)
                var thumbBitmap = Compressor(this)
                        .setMaxWidth(200)
                        .setMaxHeight(200)
                        .setQuality(65)
                        .compressToBitmap(thumbFile)

                var byteArray = ByteArrayOutputStream()
                thumbBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray)
                var thumbByteArray: ByteArray
                thumbByteArray = byteArray.toByteArray()
                var filePath = mStorage.child("chat_profile_image")
                        .child(userId + ".jpg")
                var thumbFilePath = mStorage.child("chat_profile_image")
                        .child("thumbs")
                        .child(userId + ".jpg")

                filePath.putFile(resultUri)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                filePath.downloadUrl.addOnCompleteListener {
                                    if (it != null) {
                                        var downloadUrl = it.toString()
                                        var uploadTask: UploadTask = thumbFilePath.putBytes(thumbByteArray)

                                        uploadTask.addOnCompleteListener {
                                            thumbFilePath.downloadUrl.addOnCompleteListener {
                                                var thumbUrl = it.toString()
                                                if (it.isSuccessful) {
                                                    var updateObj = HashMap<String, Any>()
                                                    updateObj.put("image", downloadUrl)
                                                    updateObj.put("thumbImage", thumbUrl)

                                                    mDatabase.updateChildren(updateObj)
                                                            .addOnCompleteListener {
                                                                if (it.isSuccessful) {
                                                                    Toaster.makeToast(
                                                                            this,
                                                                            "profile image saved")
                                                                }
                                                            }
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }
            }
        }
    }

    private fun getInformation() {
        mDatabase.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                var displayName = snapshot.child("displayName").value
                var image = snapshot.child("image").value.toString()
                var status = snapshot.child("status").value
                var thumbImage = snapshot.child("thumbImage").value
                mName.text = displayName.toString()
                mStatus.text = status.toString()

                if (!image!!.equals("default")){
                    Glide.with(applicationContext)
                            .load(image)
                            .placeholder(R.drawable.profile_img)
                            .into(mPicture)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun findViews(){
        mName = findViewById(R.id.setting_name)
        mStatus = findViewById(R.id.setting_status)
        mChangePic = findViewById(R.id.setting_change_pic)
        mChangeStatus = findViewById(R.id.setting_change_status)
        mPicture = findViewById(R.id.circleImageView)
    }
}