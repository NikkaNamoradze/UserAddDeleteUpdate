package com.example.useradddeleteupdate


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log.d
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.useradddeleteupdate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var userList = mutableListOf<UserInfo>()
    private val deletedUsers = mutableListOf<UserInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        listeners()

    }

    private fun listeners() {
        binding.btnAddUser.setOnClickListener {
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val age = binding.etAge.text.toString()
            val email = binding.etEmail.text.toString()

            try {
                if (!email.contains("@")) {
                    Toast.makeText(this, "email should contains @", Toast.LENGTH_SHORT).show()
                } else if (firstName.isEmpty() || lastName.isEmpty() || age.isEmpty()) {
                    Toast.makeText(this, "fill all values", Toast.LENGTH_SHORT).show()
                } else {
                    val user = UserInfo(
                        firstName = firstName,
                        lastName = lastName,
                        age = age.toInt(),
                        email = email
                    )

                    addUser(user)
                    d("deletedUsersList", "$deletedUsers")
                    d("UsersList", "$userList")
                }

            } catch (e: NumberFormatException) {
                Toast.makeText(this, "age should be number", Toast.LENGTH_SHORT).show()
            }


        }


        binding.btnDeleteUser.setOnClickListener {
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val age = binding.etAge.text.toString()
            val email = binding.etEmail.text.toString()

            try {
                if (!email.contains("@")) {
                    Toast.makeText(this, "email should contain @", Toast.LENGTH_SHORT).show()
                } else if (firstName.isEmpty() || lastName.isEmpty() || age.isEmpty()) {
                    Toast.makeText(this, "fill all values", Toast.LENGTH_SHORT).show()
                } else {
                    val user = UserInfo(
                        firstName = firstName,
                        lastName = lastName,
                        age = age.toInt(),
                        email = email
                    )
                    deleteUser(user)

                    d("deletedUsersList", "$deletedUsers")
                    d("UsersList", "$userList")
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "age should be number", Toast.LENGTH_SHORT).show()
            }


        }
        binding.btnUpdate.setOnClickListener {

            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val age = binding.etAge.text.toString()
            val email = binding.etEmail.text.toString()

            try {
                if (!email.contains("@")) {
                    Toast.makeText(this, "email should contains @", Toast.LENGTH_SHORT).show()
                } else if (firstName.isEmpty() || lastName.isEmpty() || age.isEmpty()) {
                    Toast.makeText(this, "fill all values", Toast.LENGTH_SHORT).show()
                } else {
                    val updatedUser = UserInfo(
                        firstName = firstName,
                        lastName = lastName,
                        age = age.toInt(),
                        email = email
                    )

                    updateUser(email, updatedUser)
                    d("UsersList", "$userList")
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "age should be number", Toast.LENGTH_SHORT).show()
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun addUser(userInfo: UserInfo) {
        when (userList.size) {
            0 -> {
                addUserToList(userInfo)
            }
            else -> {
                if (isAccountExist(userInfo)) {
                    binding.title.setTextColor(Colors.RED.colorCode)
                    binding.title.text = "account already exist"
                    cleaner()
                } else if (!isAccountExist(userInfo)) {
                    addUserToList(userInfo)
                    binding.title.setTextColor(Colors.GREEN.colorCode)
                    binding.title.text = "added successfully"
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun addUserToList(userInfo: UserInfo) {
        userList.add(userInfo)
        cleaner()
        binding.tvActiveUsers.text = "active users: ${userList.size}"
    }

    @SuppressLint("SetTextI18n")
    private fun deleteUser(userInfo: UserInfo) {              //უზერის ამოსაშლელად საჭიროა
        // იუზერის ყველა პარამეტრი იყოს სწორად შეყვანილი
        when (userList.size) {
            0 -> {
                binding.title.setTextColor(Colors.RED.colorCode)
                binding.title.text = "user doesn't exist"
                cleaner()
            }
            else -> {

                if (isAccountExist(userInfo)) {
                    userList.remove(userInfo)

                    binding.title.setTextColor(Colors.GREEN.colorCode)
                    binding.title.text = "user removed successfully"
                    cleaner()
                    deletedUsers.add(userInfo)
                    binding.tvActiveUsers.text = "active users: ${userList.size}"
                    binding.tvDeleteUsers.text = "deleted users: ${deletedUsers.size}"
                } else if (!isAccountExist(userInfo)) {
                    binding.title.text = "user doesn't exist"
                    binding.title.setTextColor(Colors.RED.colorCode)
                }


            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun updateUser(email: String, updatedUserInfo: UserInfo) {

        userList.forEach {
            if (it.email == email) {
                userList[userList.indexOf(it)] = updatedUserInfo
                cleaner()
                binding.title.setTextColor(Colors.GREEN.colorCode)
                binding.title.text = "user updated successfully"

            } else {
                cleaner()
                binding.title.text = "user doesn't exist"
                binding.title.setTextColor(Colors.RED.colorCode)
            }
        }

    }

    private fun isAccountExist(userInfo: UserInfo): Boolean {
        var isExist = false
        for (user in userList) {
            if (user.email == userInfo.email) {
                isExist = true
                break
            }
        }
        return isExist
    }

    private fun cleaner() {

        binding.etFirstName.text?.clear()
        binding.etLastName.text?.clear()
        binding.etAge.text?.clear()
        binding.etEmail.text?.clear()


    }

}